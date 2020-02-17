package com.gb.agcheb.myapptestkotlin.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gb.agcheb.myapptestkotlin.R

class LogoutDialog : DialogFragment() {
    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(context)
                    .setTitle(getString(R.string.logout_dialog_title))
                    .setMessage(getString(R.string.logout_dialog_message))
                    .setPositiveButton(getString(R.string.logout_dialog_response_ok)) { dialogInterface, i -> (activity as LogoutListener).onLogout()}
                    .setNegativeButton(getString(R.string.logout_dialog_response_no)) { dialogInterface, i -> dismiss()}
                    .create()

    interface LogoutListener {
        fun onLogout()
    }
}