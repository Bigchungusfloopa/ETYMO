package com.example.etymo.data

import com.example.etymo.domain.NodeOrigin
import com.example.etymo.domain.WordConnection
import com.example.etymo.domain.WordNode

/**
 * All positions are in LOGICAL DP UNITS (1 unit = 1 dp).
 * EtymoScreen multiplies every value by LocalDensity before placing
 * cards (Modifier.offset) and drawing canvas paths, so layout is
 * always density-independent and nodes never overlap.
 *
 * Grid layout (dp):
 *   Root row  : Y = 0
 *   Leaf row  : Y = 320   (leaves clear the ≈140dp root card plus 180dp of branch space)
 *   Sibling X : 260dp apart (> 180dp root card width)
 *   Cluster   : each family separated by ≥ 260dp of clear margin
 *
 * Cluster X-centres (dp):
 *   Ananás  :    0   → leaves at –260,   0, +260
 *   Chave   :  900   → leaves at  760, 1040
 *   Dhyāna  : 1800   → leaves at 1540, 1800, 2060
 *   Dunyā   : 2700   → leaves at 2440, 2700, 2960
 *   Kari    : 3600   → leaves at 3340, 3600, 3860
 */
object EtymoMockData {

    // ── TREE 1a : Portuguese – Ananás ────────────────────────────────────────
    val ananasRoot = WordNode.RootNode(
        id = "root_ananas", text = "Ananás", translation = "Pineapple",
        x = 0f, y = 0f, origin = NodeOrigin.PORTUGUESE,
        historyBox = "Pineapples are native to South America! Portuguese explorers brought them " +
            "to Indian shores in the 1500s. Because no Indian language had a word for " +
            "it, almost everyone just adopted the Portuguese word."
    )
    val ananasHindi = WordNode.LeafNode(
        id = "leaf_ananas_hi", text = "Ananas (अनानास)", translation = "Pineapple",
        x = -260f, y = 320f, language = "Hindi",
        triviaPop = "Adopted perfectly into Hindi."
    )
    val ananasMarathi = WordNode.LeafNode(
        id = "leaf_ananas_mr", text = "Ananas (अननस)", translation = "Pineapple",
        x = 0f, y = 320f, language = "Marathi",
        triviaPop = "Kept the exact same phonetic structure."
    )
    val ananasBengali = WordNode.LeafNode(
        id = "leaf_ananas_bn", text = "Anarosh (আনারস)", translation = "Pineapple",
        x = 260f, y = 320f, language = "Bengali",
        triviaPop = "Softened the ending to fit the Bengali phonetic flow."
    )

    // ── TREE 1b : Portuguese – Chave ─────────────────────────────────────────
    val chaveRoot = WordNode.RootNode(
        id = "root_chave", text = "Chave", translation = "Key",
        x = 900f, y = 0f, origin = NodeOrigin.PORTUGUESE,
        historyBox = "Meaning 'key'. Before the Portuguese arrived with modern mechanical locks, " +
            "Indians used different locking mechanisms. The new locks brought a new universal word."
    )
    val chaveHindiBengali = WordNode.LeafNode(
        id = "leaf_chave_hi", text = "Chabi (चाबी / চাবি)", translation = "Key",
        x = 760f, y = 320f, language = "Hindi / Bengali",
        triviaPop = "The 'v' sound morphed into a hard 'b'."
    )
    val chaveMarathiKannada = WordNode.LeafNode(
        id = "leaf_chave_mr", text = "Chavi (चावी / ಚಾವಿ)", translation = "Key",
        x = 1040f, y = 320f, language = "Marathi / Kannada",
        triviaPop = "Retained the original Portuguese 'v' sound!"
    )

    // ── TREE 2a : Sanskrit – Dhyāna ──────────────────────────────────────────
    val dhyanaRoot = WordNode.RootNode(
        id = "root_dhyana", text = "Dhyāna (ध्यान)", translation = "Meditation",
        x = 1800f, y = 0f, origin = NodeOrigin.SANSKRIT,
        historyBox = "Meaning 'contemplation' or 'meditation,' this word originated in ancient " +
            "Vedic texts and traveled alongside Buddhism across the Himalayas and oceans."
    )
    val dhyanaHindi = WordNode.LeafNode(
        id = "leaf_dhyana_hi", text = "Dhyan (ध्यान)", translation = "Focus / Care",
        x = 1540f, y = 320f, language = "Hindi / Marathi",
        triviaPop = "Used daily to mean 'focus' or 'care' (e.g., 'Dhyan se' = Carefully)."
    )
    val dhyanaBengali = WordNode.LeafNode(
        id = "leaf_dhyana_bn", text = "Dhan (ধ্যান)", translation = "Meditation",
        x = 1800f, y = 320f, language = "Bengali",
        triviaPop = "The 'y' sound softens, giving it a unique, rounded pronunciation."
    )
    val dhyanaJapanese = WordNode.LeafNode(
        id = "leaf_dhyana_ja", text = "Zen (禅)", translation = "Zen meditation",
        x = 2060f, y = 320f, language = "Japanese",
        triviaPop = "Mind-Blown! Dhyāna → (China) Chan → (Japan) Zen. When you say 'Zen' you are saying 'Dhyāna' in a different font!"
    )

    // ── TREE 3a : Persian – Dunyā ────────────────────────────────────────────
    val dunyaRoot = WordNode.RootNode(
        id = "root_dunya", text = "Dunyā (دنیا)", translation = "The World",
        x = 2700f, y = 0f, origin = NodeOrigin.PERSIAN,
        historyBox = "Originally Arabic for 'the closer world' (vs the afterlife), popularized " +
            "across the subcontinent by Persian-speaking rulers and Sufi poets."
    )
    val dunyaHindi = WordNode.LeafNode(
        id = "leaf_dunya_hi", text = "Duniya (दुनिया)", translation = "World",
        x = 2440f, y = 320f, language = "Hindi / Marathi",
        triviaPop = "The most poetic and commonly used word for 'the world'."
    )
    val dunyaBengali = WordNode.LeafNode(
        id = "leaf_dunya_bn", text = "Duniya (দুনিয়া)", translation = "World",
        x = 2700f, y = 320f, language = "Bengali",
        triviaPop = "Fully integrated into Bengali poetry and folk music."
    )
    val dunyaKannada = WordNode.LeafNode(
        id = "leaf_dunya_kn", text = "Duniya (ದುನಿಯಾ)", translation = "World",
        x = 2960f, y = 320f, language = "Kannada",
        triviaPop = "So popular one of the biggest Kannada blockbuster movies is simply named 'Duniya'."
    )

    // ── TREE 4a : Dravidian – Kari ───────────────────────────────────────────
    val kariRoot = WordNode.RootNode(
        id = "root_kari", text = "Kari (கறி)", translation = "Spiced Sauce",
        x = 3600f, y = 0f, origin = NodeOrigin.DRAVIDIAN,
        historyBox = "Originating in ancient Tamil, meaning 'blackened' or 'spiced sauce/meat'. " +
            "The British misunderstood it, and accidentally exported it to the whole world."
    )
    val kariTamil = WordNode.LeafNode(
        id = "leaf_kari_ta", text = "Kari (கறி)", translation = "Meat dish",
        x = 3340f, y = 320f, language = "Tamil",
        triviaPop = "Still used locally to refer to meat or heavily spiced side dishes."
    )
    val kariHindi = WordNode.LeafNode(
        id = "leaf_kari_hi", text = "Kari (कढ़ी)", translation = "Yogurt curry",
        x = 3600f, y = 320f, language = "Hindi",
        triviaPop = "Evolved into a specific yoghurt-and-besan-based dish in North India."
    )
    val kariJapanese = WordNode.LeafNode(
        id = "leaf_kari_ja", text = "Karē (カレー)", translation = "Curry",
        x = 3860f, y = 320f, language = "Japanese",
        triviaPop = "The British Royal Navy brought 'Curry' powder to the Japanese Navy in the 1800s. Today, 'Karē Raisu' is a national dish in Japan!"
    )

    // ── Assembly ──────────────────────────────────────────────────────────────
    val nodes: List<WordNode> = listOf(
        ananasRoot, ananasHindi, ananasMarathi, ananasBengali,
        chaveRoot, chaveHindiBengali, chaveMarathiKannada,
        dhyanaRoot, dhyanaHindi, dhyanaBengali, dhyanaJapanese,
        dunyaRoot, dunyaHindi, dunyaBengali, dunyaKannada,
        kariRoot, kariTamil, kariHindi, kariJapanese
    )

    val connections: List<WordConnection> = listOf(
        WordConnection(ananasRoot.id, ananasHindi.id),
        WordConnection(ananasRoot.id, ananasMarathi.id),
        WordConnection(ananasRoot.id, ananasBengali.id),
        WordConnection(chaveRoot.id, chaveHindiBengali.id),
        WordConnection(chaveRoot.id, chaveMarathiKannada.id),
        WordConnection(dhyanaRoot.id, dhyanaHindi.id),
        WordConnection(dhyanaRoot.id, dhyanaBengali.id),
        WordConnection(dhyanaRoot.id, dhyanaJapanese.id),
        WordConnection(dunyaRoot.id, dunyaHindi.id),
        WordConnection(dunyaRoot.id, dunyaBengali.id),
        WordConnection(dunyaRoot.id, dunyaKannada.id),
        WordConnection(kariRoot.id, kariTamil.id),
        WordConnection(kariRoot.id, kariHindi.id),
        WordConnection(kariRoot.id, kariJapanese.id),
    )
}
