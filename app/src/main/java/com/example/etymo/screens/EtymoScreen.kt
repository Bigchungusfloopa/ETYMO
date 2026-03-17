package com.example.etymo.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.etymo.data.EtymoMockData
import com.example.etymo.domain.NodeOrigin
import com.example.etymo.domain.WordNode
import com.example.etymo.ui.components.LeafNodeCard
import com.example.etymo.ui.components.NodeInfoDialog
import com.example.etymo.ui.components.RootNodeCard
import com.example.etymo.ui.theme.EtymoDark
import com.example.etymo.ui.theme.EtymoOffWhite
import com.example.etymo.ui.theme.EtymoYellow
import kotlin.math.roundToInt

// ── Card half-dimensions in DP ──────────────────────────────────────────────
// Root:  180dp wide × 140dp tall  →  half = 90dp, 70dp
// Leaf:  150dp wide × 100dp tall  →  half = 75dp, 50dp
private val ROOT_HALF_W_DP = 90f   // dp
private val ROOT_HALF_H_DP = 70f   // dp
private val LEAF_HALF_W_DP = 75f   // dp
private val LEAF_HALF_H_DP = 50f   // dp

@Composable
fun EtymoScreen() {
    val nodes = EtymoMockData.nodes
    val connections = EtymoMockData.connections

    // ── Pinch-zoom / pan state ───────────────────────────────────────────────
    var scale by remember { mutableFloatStateOf(0.45f) }
    var panOffset by remember { mutableStateOf(Offset(60f, 200f)) }   // px, initial so tree is roughly centered

    var selectedNode by remember { mutableStateOf<WordNode?>(null) }
    var selectedLeafId by remember { mutableStateOf<String?>(null) }
    var expandedFilter by remember { mutableStateOf(false) }
    var activeFilter by remember { mutableStateOf<NodeOrigin?>(null) }

    // Convert device pixels per dp once, so all coordinate math is consistent
    val density = LocalDensity.current.density

    // Compute px values for card halves
    val rootHalfWPx = ROOT_HALF_W_DP * density
    val rootHalfHPx = ROOT_HALF_H_DP * density
    val leafHalfWPx = LEAF_HALF_W_DP * density
    val leafHalfHPx = LEAF_HALF_H_DP * density

    // Map node id → origin for filtering
    val nodeOriginMap = remember(nodes) {
        val roots = nodes.filterIsInstance<WordNode.RootNode>().associate { it.id to it.origin }
        val leafOrigins = connections.mapNotNull { c -> roots[c.rootId]?.let { c.leafId to it } }.toMap()
        roots + leafOrigins
    }

    // ── Helper: logical dp → pixel ──────────────────────────────────────────
    fun dpToPx(dp: Float) = dp * density

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EtymoOffWhite)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val prevScale = scale
                    scale = (scale * zoom).coerceIn(0.15f, 3f)
                    // Zoom toward the pinch centroid
                    val scaleChange = scale / prevScale
                    panOffset = centroid + (panOffset - centroid) * scaleChange + pan
                }
            }
    ) {

        // ── 1. Bezier branch lines ───────────────────────────────────────────
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = panOffset.x,
                    translationY = panOffset.y,
                    transformOrigin = TransformOrigin(0f, 0f)
                )
        ) {
            val posMap = nodes.associate { it.id to Offset(dpToPx(it.x), dpToPx(it.y)) }

            for (conn in connections) {
                val rootPos = posMap[conn.rootId] ?: continue
                val leafPos = posMap[conn.leafId] ?: continue

                val origin = nodeOriginMap[conn.rootId]
                val filteredOut = activeFilter != null && activeFilter != origin
                val highlighted = conn.leafId == selectedLeafId && !filteredOut

                val lineAlpha = when {
                    filteredOut -> 0.04f
                    highlighted -> 0.95f
                    else        -> 0.45f
                }
                val lineColor = (origin?.color ?: EtymoDark).copy(alpha = lineAlpha)
                val lineWidth = if (highlighted) 10f else 5f

                // Start from bottom-center of root card, end at top-center of leaf card
                val sx = rootPos.x                      // center X of root
                val sy = rootPos.y + rootHalfHPx        // bottom edge of root card
                val ex = leafPos.x                      // center X of leaf
                val ey = leafPos.y - leafHalfHPx        // top edge of leaf card
                val midY = (sy + ey) / 2f

                val path = Path().apply {
                    moveTo(sx, sy)
                    cubicTo(sx, midY, ex, midY, ex, ey)
                }
                drawPath(path, color = lineColor, style = Stroke(width = lineWidth))
            }
        }

        // ── 2. Node cards (above lines, same transform) ──────────────────────
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = panOffset.x,
                    translationY = panOffset.y,
                    transformOrigin = TransformOrigin(0f, 0f)
                )
        ) {
            for (node in nodes) {
                val origin = nodeOriginMap[node.id]
                val filteredOut = activeFilter != null && activeFilter != origin

                // Determine pixel-center of this node
                val cxPx = dpToPx(node.x)
                val cyPx = dpToPx(node.y)
                val hw = if (node is WordNode.RootNode) rootHalfWPx else leafHalfWPx
                val hh = if (node is WordNode.RootNode) rootHalfHPx else leafHalfHPx

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = (cxPx - hw).roundToInt(),
                                y = (cyPx - hh).roundToInt()
                            )
                        }
                        .graphicsLayer(alpha = if (filteredOut) 0.08f else 1f)
                ) {
                    when (node) {
                        is WordNode.RootNode -> RootNodeCard(node = node, onClick = { selectedNode = node })
                        is WordNode.LeafNode -> LeafNodeCard(
                            node = node,
                            onClick = { selectedNode = node; selectedLeafId = node.id }
                        )
                    }
                }
            }
        }

        // ── 3. Dialog overlay ────────────────────────────────────────────────
        selectedNode?.let { node ->
            NodeInfoDialog(node = node, onDismiss = {
                selectedNode = null
                selectedLeafId = null
            })
        }

        // ── 4. Filter FAB ────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 120.dp)
        ) {
            FloatingActionButton(
                onClick = { expandedFilter = true },
                containerColor = EtymoDark,
                contentColor = EtymoYellow,
                shape = CircleShape
            ) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter")
            }

            DropdownMenu(expanded = expandedFilter, onDismissRequest = { expandedFilter = false }) {
                DropdownMenuItem(
                    text = { Text("Show All", fontWeight = FontWeight.Bold) },
                    onClick = { activeFilter = null; expandedFilter = false }
                )
                NodeOrigin.entries.forEach { origin ->
                    DropdownMenuItem(
                        text = { Text(origin.name) },
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .background(origin.color, CircleShape)
                            )
                        },
                        onClick = { activeFilter = origin; expandedFilter = false }
                    )
                }
            }
        }
    }
}
