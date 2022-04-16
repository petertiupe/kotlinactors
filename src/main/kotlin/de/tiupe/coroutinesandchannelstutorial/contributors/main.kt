package de.tiupe.coroutinesandchannelstutorial.contributors

import de.tiupe.coroutinesandchannelstutorial.contributors.ContributorsUI
import de.tiupe.coroutinesandchannelstutorial.contributors.setDefaultFontSize



@ExperimentalStdlibApi
fun main() {
    setDefaultFontSize(18f)
    ContributorsUI().apply {
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}