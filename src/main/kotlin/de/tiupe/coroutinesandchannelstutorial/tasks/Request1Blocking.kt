package de.tiupe.coroutinesandchannelstutorial.tasks


import de.tiupe.coroutinesandchannelstutorial.contributors.*
import retrofit2.Response

/*
* Jeder Aufruf von getOrgReposCall und getRepoContributorsCall erzeugt ein eigenes Objekt vom Typ
*
*   Call<List<Repo>>
*
* Erst der Aufruf von execute sorgt dann dafür, dass auch ein Aufruf gegen das Backend erfolgt. Dies ist ein
* synchroner Aufruf, der den darunterliegenden Thread blockt.
*
* */

fun loadContributorsBlocking(service: GitHubService, req: RequestData) : List<User> {
    // Reading of all Repositories
    val repos = service
        .getOrgReposCall(req.org)
        .execute() // Executes request and blocks the current thread
        .also { logRepos(req, it) }
        .bodyList()

    // for each Repository fetch the list of contributors
    return repos.flatMap { repo ->
        service
            .getRepoContributorsCall(req.org, repo.name)
            .execute() // Executes request and blocks the current thread
            .also { logUsers(repo, it) }
            .bodyList()
    }.aggregate()
}

fun <T> Response<List<T>>.bodyList(): List<T> {
    return body() ?: listOf()
}