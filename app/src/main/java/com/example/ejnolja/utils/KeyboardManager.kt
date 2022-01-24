package com.example.ejnolja.utils
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.collections.ArrayList

object KeyboardManager {
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Activity.showKeyboard() {
        showKeyboard(currentFocus ?: View(this))
    }

    fun Context.showKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    // Randonm
    fun passcodeShuffle() : ArrayList<Int>
    {
        val passcodeRand = arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        passcodeRand.shuffle()

        return passcodeRand
    }
}