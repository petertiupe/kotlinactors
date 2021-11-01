package de.tiupe.coroutinesandchannelstutorial.tasks

import de.tiupe.coroutinesandchannelstutorial.contributors.*

suspend fun loadContributorsProgress(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
    TODO()
}
