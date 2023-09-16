package com.anksite.movindex

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window

class DialogLoading(val context: Context) {
    val mDialog = Dialog(context).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
    }

    fun show() {
        mDialog.show()
    }

    fun cancel() {
        mDialog.cancel()
    }
}
