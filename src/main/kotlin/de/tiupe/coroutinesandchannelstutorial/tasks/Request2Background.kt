package de.tiupe.coroutinesandchannelstutorial.tasks

import de.tiupe.coroutinesandchannelstutorial.contributors.GitHubService
import de.tiupe.coroutinesandchannelstutorial.contributors.RequestData
import de.tiupe.coroutinesandchannelstutorial.contributors.User
import kotlin.concurrent.thread

fun loadContributorsBackground(service: GitHubService, req: RequestData, updateResults: (List<User>) -> Unit) {
    thread {
        val loadedUsers: List<User> = loadContributorsBlocking(service, req)
        // Wichtig ist natürlich, dass der Callback auch aufgerufen wird, sonst passiert an der Oberfläche gar nichts.
        updateResults(loadedUsers)
    }
}