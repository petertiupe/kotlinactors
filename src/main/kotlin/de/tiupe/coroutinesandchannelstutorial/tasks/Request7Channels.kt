package de.tiupe.coroutinesandchannelstutorial.tasks

import de.tiupe.coroutinesandchannelstutorial.contributors.GitHubService
import de.tiupe.coroutinesandchannelstutorial.contributors.RequestData
import de.tiupe.coroutinesandchannelstutorial.contributors.User
import kotlinx.coroutines.coroutineScope

suspend fun loadContributorsChannels(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
    coroutineScope {
        TODO()
    }
}
