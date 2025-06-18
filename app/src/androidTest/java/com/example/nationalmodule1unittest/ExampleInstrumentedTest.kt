package com.example.nationalmodule1unittest

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertTextEquals
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
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

val testCard = mutableListOf(
    "Hello" to "你好",
    "World" to "世界",
    "Sofia" to "育華",
    "Anna" to "恩恩",
    "Eason" to "尹陞"
)

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class StartTesting {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private val delayTime = 1000L


    private fun delay() {
        Thread.sleep(delayTime)
    }

//    @Ignore
    @Test
    fun `1 Show the card list screen after the app launched`() {
        rule.onNodeWithTag("home_screen").assertExists()
        delay()
    }

//    @Ignore
    @Test
    fun `2 Nav to the new card screen and add cards`() {
        rule.onNodeWithTag("add_card").performClick()
        rule.onNodeWithTag("new_card_screen").assertExists()
        delay()
    }

    @Test
    fun `3 Add some cards`() {
        testCard.forEach { (en, tw) ->
            rule.onNodeWithTag("add_card").performClick()
            rule.onNodeWithTag("new_card_screen").assertExists()
            rule.onNodeWithTag("en_input").performTextInput(en)
            rule.onNodeWithTag("tw_input").performTextInput(tw)
            rule.onNodeWithTag("submit_button").performClick()
        }
        delay()
    }

//    @Ignore
    @Test
    fun `4 Check Card list data`() {
        testCard.forEachIndexed { index, (en, tw) ->
            val cardNode =
                rule.onAllNodesWithTag("edit_card", useUnmergedTree = true)[index].onChildren()
            cardNode.filter(hasTestTag("en_text"))[0].assert(hasText(en)) // same as filter to one
            cardNode.filterToOne(hasTestTag("tw_text")).assert(hasText(tw))
//            val text = cardNode.filterToOne(hasTestTag("tw_text")).fetchSemanticsNode().config[SemanticsProperties.Text][0].text
//            Log.d("test",text)
        }
        delay()
    }

//    @Ignore
    @Test
    fun `5 edit card data and check the data update`() {
        val tempTestCards = mutableStateListOf<Pair<String, String>>()

        testCard.forEachIndexed { index, (en, tw) ->
            rule.onAllNodesWithTag("edit_card", useUnmergedTree = true)[index].performClick()

            rule.onNodeWithTag("edit_card_screen").assertExists()

//            rule.onNodeWithTag("en_input", useUnmergedTree = true).assertTextContains(en)
//            rule.onNodeWithTag("tw_input", useUnmergedTree = true).assertTextContains(tw)


            rule.onNodeWithTag("en_input").performTextInput("$en edited")
            rule.onNodeWithTag("tw_input").performTextInput("$tw edited")

            testCard[index] = "$en edited" to "$tw edited"

            rule.onNodeWithTag("submit_button").performClick()
        }
        `4 Check Card list data`()
        delay()
    }

//    @Ignore
    @Test
    fun `6 Check card learning status`() {
        testCard.forEach {
            Log.d("test", "${it.first}   ${it.second}")
        }
        testCard.forEachIndexed { index, _ ->
            rule.onAllNodesWithTag(
                "card_learning_button",
                useUnmergedTree = true
            )[index].performClick()

            rule.onNodeWithTag("學習中").performClick()

            val learningCard =
                rule.onAllNodesWithTag("edit_card", useUnmergedTree = true)

            learningCard.fetchSemanticsNodes().forEachIndexed { learningIndex, _ ->
                learningCard[learningIndex].onChildren().filterToOne(hasTestTag("en_text"))
                    .assert(hasText(testCard[learningIndex].first))
            }

            rule.onNodeWithTag("所有").performClick()
        }
        delay()
    }

    @Test
    fun `7 Check card review pager`() {
        rule.onNodeWithTag("nav_item_${Screens.輪轉單字卡.name}")
            .performClick()
        rule.onNodeWithTag("card_screen").assertExists()
        testCard.forEachIndexed { index, (en, tw) ->
//            val parent = rule.onNodeWithTag("pager_card_$index")
//
//            val twText = parent.onChildren().filterToOne(hasTestTag("tw_text"))
//            val enText = parent.onChildren().filterToOne(hasTestTag("en_text"))
//            val chineseCard = parent.onChildren().filterToOne(hasTestTag("chinese_card"))
//            val englishCard = parent.onChildren().filterToOne(hasTestTag("english_card"))
//
//            twText.assert(hasText(tw))
//            chineseCard.performClick()
//            rule.waitForIdle()
//            enText.assert(hasText(en))
//            englishCard.performClick()
//            rule.waitForIdle()

            rule.onAllNodesWithTag("tw_text", useUnmergedTree = true)[0].assertTextEquals(tw)
            rule.onAllNodesWithTag("chinese_card", useUnmergedTree = true)[0].performClick()
            rule.waitForIdle()
            rule.onAllNodesWithTag("en_text", useUnmergedTree = true)[0].assertTextEquals(en)
            rule.onAllNodesWithTag("english_card", useUnmergedTree = true)[0].performClick()
            rule.waitForIdle()

            rule.onNodeWithTag("horizontal_pager_tag", useUnmergedTree = true).performTouchInput {
                swipeLeft()
                rule.waitForIdle()
            }
        }
        delay()
    }
}