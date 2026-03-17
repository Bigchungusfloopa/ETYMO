package com.example.etymo.domain

import androidx.compose.ui.graphics.Color
import com.example.etymo.ui.theme.*

enum class NodeOrigin(val color: Color) {
    SANSKRIT(EtymoOrange),
    PERSIAN(EtymoGreen),
    DRAVIDIAN(EtymoBlue),
    PORTUGUESE(EtymoPurple)
}

/**
 * Positions are stored as LOGICAL DP values (x, y).
 * EtymoScreen converts them to pixels via LocalDensity before rendering.
 */
sealed class WordNode {
    abstract val id: String
    abstract val text: String
    abstract val translation: String
    abstract val x: Float   // logical dp
    abstract val y: Float   // logical dp

    data class RootNode(
        override val id: String,
        override val text: String,
        override val translation: String,
        override val x: Float,
        override val y: Float,
        val origin: NodeOrigin,
        val historyBox: String
    ) : WordNode()

    data class LeafNode(
        override val id: String,
        override val text: String,
        override val translation: String,
        override val x: Float,
        override val y: Float,
        val language: String,
        val triviaPop: String
    ) : WordNode()
}

data class WordConnection(val rootId: String, val leafId: String)
