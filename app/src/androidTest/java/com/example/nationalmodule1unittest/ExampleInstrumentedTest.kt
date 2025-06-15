package com.example.nationalmodule1unittest

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class StartTesting {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private val delayTime = 1000L

    val cards = mutableListOf(
        "Hello" to "你好",
        "World" to "世界",
        "Sofia" to "育華",
        "Anna" to "恩恩",
        "Eason" to "尹陞"
    )

    private fun delay() {
        Thread.sleep(delayTime)
    }

    @Test
    fun `1 Show the card list screen after the app launched`() {
        rule.onNodeWithTag("home_screen").assertExists()
        delay()
    }

    @Test
    fun `2 Nav to the new card screen and add cards`() {
        rule.onNodeWithTag("add_card").performClick()
        rule.onNodeWithTag("new_card_screen").assertExists()
        delay()
    }

    @Test
    fun `3 Add some cards`() {
        cards.forEach { (en, tw) ->
            rule.onNodeWithTag("add_card").performClick()
            rule.onNodeWithTag("new_card_screen").assertExists()
            rule.onNodeWithTag("en_input").performTextInput(en)
            rule.onNodeWithTag("tw_input").performTextInput(tw)
            rule.onNodeWithTag("submit_button").performClick()
        }
        delay()
    }

    @Test
    fun `4 Check Card list data`() {
        cards.forEachIndexed { index, (en, tw) ->
            val cardNode =
                rule.onAllNodesWithTag("edit_card", useUnmergedTree = true)[index].onChildren()
            cardNode.filter(hasTestTag("en_text"))[0].assert(hasText(en)) // same as filter to one
            cardNode.filterToOne(hasTestTag("tw_text")).assert(hasText(tw))
//            val text = cardNode.filterToOne(hasTestTag("tw_text")).fetchSemanticsNode().config[SemanticsProperties.Text][0].text
//            Log.d("test",text)
        }
        delay()
    }

    @Test
    fun `5 edit card data and check the data update`() {
        cards.forEachIndexed { index, (en, tw) ->
            rule.onAllNodesWithTag("edit_card", useUnmergedTree = true)[index].performClick()

            rule.onNodeWithTag("edit_card_screen").assertExists()

//            rule.onNodeWithTag("en_input", useUnmergedTree = true).assertTextContains(en)
//            rule.onNodeWithTag("tw_input", useUnmergedTree = true).assertTextContains(tw)

            cards[index] = "$en edited" to "$tw edited"

            rule.onNodeWithTag("en_input").performTextInput("$en edited")
            rule.onNodeWithTag("tw_input").performTextInput("$tw edited")

            rule.onNodeWithTag("submit_button").performClick()
        }
        `4 Check Card list data`()
        delay()
    }

    @Test
    fun `6 Check card learning status`() {
        cards.forEachIndexed { index, (en, tw) ->
            rule.onAllNodesWithTag(
                "card_learning_button",
                useUnmergedTree = true
            )[index].performClick()
            val learningTab = rule.onNodeWithTag("學習中")
            val allTab = rule.onNodeWithTag("所有")
            learningTab.performClick()
            val learningCard =
                rule.onAllNodesWithTag("edit_card", useUnmergedTree = true).fetchSemanticsNodes()
//            learningCard.forEachIndexed {learningIndex,learningItem ->
//                learningItem.config[SemanticsProperties.Text][0].text
//            }
            allTab.performClick()
        }
        delay()
    }

    @Test
    fun `7 Check card review pager`() {

    }
}