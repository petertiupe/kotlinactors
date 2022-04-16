package de.tiupe.coroutinesandchannelstutorial.tasks

import de.tiupe.coroutinesandchannelstutorial.contributors.*


// Da hier jetzt Coroutinen verwendet werden, ist es wichtig, die Funktion als suspend-Function zu deklarieren.
suspend fun loadContributorsSuspend(service: GitHubService, req: RequestData): List<User> {
    // hier kann man jetzt den Code durchgängig schreiben und braucht keine Callbacks mehr.
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    return repos.flatMap { repo ->
        service.getRepoContributors(req.org, repo.name)
            .also { logUsers(repo, it) }
            .bodyList()
    }.aggregate()
}
