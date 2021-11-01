package de.tiupe.coroutinesandchannelstutorial.contributors


import de.tiupe.coroutinesandchannelstutorial.tasks.*
import kotlinx.coroutines.*
import java.awt.event.ActionListener
import javax.swing.SwingUtilities
import kotlin.coroutines.CoroutineContext

enum class Variant {
    BLOCKING,         // Request1Blocking
    BACKGROUND,       // Request2Background
    CALLBACKS,        // Request3Callbacks
    SUSPEND,          // Request4Coroutine
    CONCURRENT,       // Request5Concurrent
    NOT_CANCELLABLE,  // Request6NotCancellable
    PROGRESS,         // Request6Progress
    CHANNELS          // Request7Channels
}

interface Contributors: CoroutineScope {

    val job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun init() {
        // Start a new loading on 'load' click
        addLoadListener {
            saveParams()
            loadContributors()
        }

        // Save preferences and exit on closing the window
        addOnWindowClosingListener {
            job.cancel()
            saveParams()
            System.exit(0)
        }

        // Load stored params (user & password values)
        loadInitialParams()
    }

    fun loadContributors() {
        val (username, password, org, _) = getParams()
        val req = RequestData(username, password, org)

        clearResults()
        val service = createGitHubService(req.username, req.password)

        val startTime = System.currentTimeMillis()
        when (getSelectedVariant()) {
            Variant.BLOCKING -> { // Blocking UI thread
                val users = loadContributorsBlocking(service, req)
                // Wenn diese Stelle erreicht ist, hat man die vollständige Liste aller User...
                // Hier erfolgt der Aufruf zur aktualisierung der Oberfläche.
                updateResults(users, startTime)
            }
            // Hier wird der Aufruf der GIT-Hub-Api mit einem Callback versehen, der als Lambda mit an den Aufruf
            // gegeben wird. Für den Aufruf wird ein neuer Thread in der gerufenen Funktion erzeugt.
            // Da dieselbe Funktion genutzt wird, wie bei dem blockierenden Thread, ist natürlich auch der Aufruf
            // der Api nicht parallelisiert und nur die Oberfläche ist nicht blockiert. Der Update-Results-Aufruf
            // steht nun in dem Callback und nicht mehr als Aufruf in dem Hauptthread. Der Unterschied zu vorher besteht
            // darin, dass die Oberfläche nun nicht mehr blockierend ist, sondern weiter bedient werden kann.

            // Durch den Aufruf der Swing-Utilities invokeLater Funktion wird sichergestellt, dass diese Funktion
            // auf dem Hauptthread aufgerufen wird.
            Variant.BACKGROUND -> { // Blocking a background thread
                loadContributorsBackground(service, req) { users ->
                    SwingUtilities.invokeLater {
                        updateResults(users, startTime)
                    }
                }
            }

            // Hier wird nun das Retrofit-Callback-API genutzt. Jeder Aufruf der Repos wird in zwei Teile separiert.
            // An dieser Stelle muss man in dem Aufruf für eine Synchronisation sorgen. Dies geschieht hier mit einem
            // Count-Down-Latch, einer Count-Down Sperre / Riegel. Da auch die Retrofit-API mit asynchronen Methoden-
            // Aufrufen arbeitet, landet man hier sehr schnell bei dem Synchronisierungsproblem.
            Variant.CALLBACKS -> { // Using callbacks
                loadContributorsCallbacks(service, req) { users ->
                    SwingUtilities.invokeLater {
                        updateResults(users, startTime)
                    }
                }
            }
            /* Die Retrofit-Bibliothek bringt schon Support für die Koroutinen mit. Der API-Call arbeitet ab jetzt nicht
             * mehr mit dem Call-Objekt, sondern als suspend-Function.
             *
             * Die Tatsache, dass hier ein launch aufgerufen werden kann, ist schon allein dadurch bedingt, dass das
             * hier betrachtete Interface selbst den CoroutineScope erweitert.
             *
             * Die hier implementierten suspend-Funktionen arbeiten synchron, d.h. die Requests werden alle nacheinander
             * abgearbeitet. Suspend-Funktionen sorgen nicht automatisch für eine parallele Verarbeitung, sie lassen
             * lediglich zu, dass der Thread in der Suspend-Phase neue Aufgaben übernehmen kann.
             *
             * */
            Variant.SUSPEND -> { // Using coroutines
                launch {
                    val users = loadContributorsSuspend(service, req)
                    updateResults(users, startTime)
                }.setUpCancellation()
            }

            /* Koroutinen sind im Vergleich zu Threads recht billig, jedes Mal, wenn man einen neuen Prozess starten
             * möchte, kann man eine neue Koroutine mit einem der Koroutine-Builder starten.
             *
             * Mit awaitAll kann man auf alle Ergebnisse von async-Aufrufen warten.
             *
             *  */
            Variant.CONCURRENT -> { // Performing requests concurrently
                launch {
                    val users = loadContributorsConcurrent(service, req)
                    updateResults(users, startTime)
                }.setUpCancellation()
            }
            Variant.NOT_CANCELLABLE -> { // Performing requests in a non-cancellable way
                launch {
                    val users = loadContributorsNotCancellable(service, req)
                    updateResults(users, startTime)
                }.setUpCancellation()
            }
            Variant.PROGRESS -> { // Showing progress
                launch(Dispatchers.Default) {
                    loadContributorsProgress(service, req) { users, completed ->
                        withContext(Dispatchers.Main) {
                            updateResults(users, startTime, completed)
                        }
                    }
                }.setUpCancellation()
            }
            Variant.CHANNELS -> {  // Performing requests concurrently and showing progress
                launch(Dispatchers.Default) {
                    loadContributorsChannels(service, req) { users, completed ->
                        withContext(Dispatchers.Main) {
                            updateResults(users, startTime, completed)
                        }
                    }
                }.setUpCancellation()
            }
        }
    }

    private enum class LoadingStatus { COMPLETED, CANCELED, IN_PROGRESS }

    private fun clearResults() {
        updateContributors(listOf())
        updateLoadingStatus(LoadingStatus.IN_PROGRESS)
        setActionsStatus(newLoadingEnabled = false)
    }

    private fun updateResults(
        users: List<User>,
        startTime: Long,
        completed: Boolean = true
    ) {
        updateContributors(users)
        updateLoadingStatus(if (completed) LoadingStatus.COMPLETED else LoadingStatus.IN_PROGRESS, startTime)
        if (completed) {
            setActionsStatus(newLoadingEnabled = true)
        }
    }

    private fun updateLoadingStatus(
        status: LoadingStatus,
        startTime: Long? = null
    ) {
        val time = if (startTime != null) {
            val time = System.currentTimeMillis() - startTime
            "${(time / 1000)}.${time % 1000 / 100} sec"
        } else ""

        val text = "Loading status: " +
                when (status) {
                    LoadingStatus.COMPLETED -> "completed in $time"
                    LoadingStatus.IN_PROGRESS -> "in progress $time"
                    LoadingStatus.CANCELED -> "canceled"
                }
        setLoadingStatus(text, status == LoadingStatus.IN_PROGRESS)
    }

    private fun Job.setUpCancellation() {
        // make active the 'cancel' button
        setActionsStatus(newLoadingEnabled = false, cancellationEnabled = true)

        val loadingJob = this

        // cancel the loading job if the 'cancel' button was clicked
        val listener = ActionListener {
            loadingJob.cancel()
            updateLoadingStatus(LoadingStatus.CANCELED)
        }
        addCancelListener(listener)

        // update the status and remove the listener after the loading job is completed
        launch {
            loadingJob.join()
            setActionsStatus(newLoadingEnabled = true)
            removeCancelListener(listener)
        }
    }

    fun loadInitialParams() {
        setParams(loadStoredParams())
    }

    fun saveParams() {
        val params = getParams()
        if (params.username.isEmpty() && params.password.isEmpty()) {
            removeStoredParams()
        }
        else {
            saveParams(params)
        }
    }

    fun getSelectedVariant(): Variant

    fun updateContributors(users: List<User>)

    fun setLoadingStatus(text: String, iconRunning: Boolean)

    fun setActionsStatus(newLoadingEnabled: Boolean, cancellationEnabled: Boolean = false)

    fun addCancelListener(listener: ActionListener)

    fun removeCancelListener(listener: ActionListener)

    fun addLoadListener(listener: () -> Unit)

    fun addOnWindowClosingListener(listener: () -> Unit)

    fun setParams(params: Params)

    fun getParams(): Params
}
