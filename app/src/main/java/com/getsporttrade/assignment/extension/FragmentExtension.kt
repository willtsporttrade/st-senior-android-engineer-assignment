package com.getsporttrade.assignment.extension

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Shows the Snackbar on Fragment
 *
 * @param text message text
 * @param view [View] container view
 */
fun Fragment.showSnackbar(text: String, view: View = requireView()) =
    Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()