package de.tiupe.coroutinesandchannelstutorial.contributors

import de.tiupe.coroutinesandchannelstutorial.contributors.ContributorsUI
import de.tiupe.coroutinesandchannelstutorial.contributors.setDefaultFontSize


// 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
@ExperimentalStdlibApi
fun main() {
    setDefaultFontSize(18f)
    ContributorsUI().apply {
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}