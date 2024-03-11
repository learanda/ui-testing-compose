package com.cursokotlin.composetesting.components

import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.swipeUp
import org.junit.Rule
import org.junit.Test

class MyComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myFirstTest() {
        composeTestRule.setContent {
            MyComponent()
        }

        //FINDER (3 formas de encontrar el componente)
        // 1- busca a través del texto
        composeTestRule.onNodeWithText("LAN", ignoreCase = true)
        // 2- busca a través del tag
        composeTestRule.onNodeWithTag("component1")
        // 3- busca con el content description
        composeTestRule.onNodeWithContentDescription("image1")

        // abarca todos los nodos con ese tag
        composeTestRule.onAllNodesWithTag("component1")
        // abarca todos los nodos que contengan ese texto o simbolo
        composeTestRule.onAllNodesWithText(":")
        // abarca todos los mismos content description, serviría para varios iconos por ejemplo, cosas visuales
        composeTestRule.onAllNodesWithContentDescription("visualIcon")

        //ACTIONS
        composeTestRule.onNodeWithText("LAN", ignoreCase = true).performClick()
        composeTestRule.onAllNodesWithText("L").onFirst().performClick()
        composeTestRule.onNodeWithText("Lan").performTouchInput {
            longClick()
            doubleClick()
            swipeUp()
            swipeLeft()
            swipeDown()
            swipeRight()
        }
        composeTestRule.onNodeWithText("L").performScrollTo() // Esto hace scroll dentro del componente. Si el componente está en un scroll, no funciona, funciona si tiene un scroll
        //composeTestRule.onNodeWithText("L").performScrollToNode() // Es el ideal para scrollear hasta un nodo
        composeTestRule.onNodeWithText("L").performImeAction() // Es el boton de acción de cuando escribimos, como el enter
        composeTestRule.onNodeWithText("L").performTextClearance() // Borra el texto de un TextField
        composeTestRule.onNodeWithText("L").performTextInput("Texto") // Le va a escribir el texto al componente
        composeTestRule.onNodeWithText("L").performTextReplacement("Otro texto") // Agarra el texto del nodo y lo reemplaza por el otro

        // tambien se pueden encadenar Actions, en algunos casos no se puede
        composeTestRule.onNodeWithText("L").performScrollTo().performClick().performTextInput("Texto")


        //ASSERTIONS
        composeTestRule.onNodeWithText("L").assertExists()
        composeTestRule.onNodeWithText("L").assertDoesNotExist()
        composeTestRule.onNodeWithText("L").assertContentDescriptionEquals("LAN")
        composeTestRule.onNodeWithText("L").assertContentDescriptionContains("N")
        composeTestRule.onNodeWithText("L").assertIsDisplayed()
        composeTestRule.onNodeWithText("L").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("L").assertIsEnabled()
        composeTestRule.onNodeWithText("L").assertIsNotEnabled()
        composeTestRule.onNodeWithText("L").assertIsSelected()
        composeTestRule.onNodeWithText("L").assertIsNotSelected()
        composeTestRule.onNodeWithText("L").assertIsFocused()
        composeTestRule.onNodeWithText("L").assertIsNotFocused()
        composeTestRule.onNodeWithText("L").assertIsOn() // cuando es checked
        composeTestRule.onNodeWithText("L").assertIsOff()
        composeTestRule.onNodeWithText("L").assertTextEquals("")
        composeTestRule.onNodeWithText("L").assertTextContains("L")
    }

    @Test
    fun whenComponentStart_thenVerifyContentIsAris() {
        composeTestRule.setContent {
            MyComponent()
        }

        composeTestRule.onNodeWithText("laninsky", ignoreCase = true).assertExists()
        composeTestRule.onNodeWithTag("component1").assertTextContains("Laninsky")
    }

    @Test
    fun whenNameIsAdded_thenVerifyTextContainsGreeting() {
        composeTestRule.setContent {
            MyComponent()
        }

        composeTestRule.onNodeWithTag("component1").performTextReplacement("Laninsky")
        composeTestRule.onNodeWithTag("component2").assertTextEquals("Te llamas Laninsky")

        // Si quisiera usar el performTextInput primero hay que hacer un clearance
        composeTestRule.onNodeWithTag("component1").performTextClearance()
        composeTestRule.onNodeWithTag("component1").performTextInput("Laninsky")
    }
}