package de.tiupe.coroutinesandchannelstutorial.tasks


import de.tiupe.coroutinesandchannelstutorial.contributors.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

fun loadContributorsCallbacks(service: GitHubService, req: RequestData, updateResults: (List<User>) -> Unit) {
    service.getOrgReposCall(req.org).onResponse { responseRepos ->
        logRepos(req, responseRepos)
        val repos = responseRepos.bodyList()
        val allUsers = mutableListOf<User>()
        // Hier wird mit einem Count-Down-Latch gearbeitet. Ein Latch ist ein Riegel oder eine Klinke. Man kann hier
        // damit dafür sorgen, dass ein Zähler sauber verarbeitet wird. Erst mit diesem Latch kommt es zur richtigen
        // Anzeige der Daten, da die RepoContributor-Calls asynchron ablaufen, schafft man es nur so, für die
        // Synchronisation zu sorgen.
        val countDownLatch = CountDownLatch(repos.size)
        for (repo in repos) {
            service.getRepoContributorsCall(req.org, repo.name).onResponse { responseUsers ->
                // processing Repository
                countDownLatch.countDown()
                logUsers(repo, responseUsers)
                val users = responseUsers.bodyList()
                allUsers += users
            }
        }
        countDownLatch.await()
        updateResults(allUsers.aggregate())
    }
}

inline fun <T> Call<T>.onResponse(crossinline callback: (Response<T>) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            callback(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            log.error("Call failed", t)
        }
    })
}
