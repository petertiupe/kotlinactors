package de.tiupe.coroutinesandchannelstutorial.tasks


import de.tiupe.coroutinesandchannelstutorial.contributors.*
import kotlinx.coroutines.*

suspend fun loadContributorsConcurrent(service: GitHubService, req: RequestData): List<User> = coroutineScope {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()


    // In dem for-Loop wird für jedes Repository ein asynchroner Aufruf gestartet, mit dem die User zu den
    // entsprechenden Repos gelesen werden. awaitAll läuft dann erst weiter, wenn alle Requests durchgelaufen sind.
    // Alle Requests laufen in diesem Fall natürlich auf demselben Scope, d. h. nach einem Request gegen den Server
    // kann einfach ein weiterer Thread aufgerufen werden
    val deferreds: List<Deferred<List<User>>> = repos.map { repo ->
        // Durch die Übergabe des Dispatchers werden die einzelnen Aufrufe an unterschiedliche Threads delegiert.
        // Mit der auskommentierten Zeile läuft alles in einem Thread. Der von mir gemessene Unterschied der Laufzeiten
        // ist jedoch sehr klein in diesem Beispiel.

        // async {
        async(Dispatchers.Default) {
           service.getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList()
        }
    }
    deferreds.awaitAll().flatten().aggregate()
}