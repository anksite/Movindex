package com.anksite.movindex

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class DialogCustom(val context: Context) {
    var onClickPositive: (() -> Unit)? = null
    var onClickNegative: (() -> Unit)? = null

    var mIcon = 0
    var mTitle = ""
    var mMessage = ""
    var mCancelable = true
    var mTextPositive = "Oke"
    var mTextNegative = ""

    fun setIcon(icon: Int): DialogCustom {
        mIcon = icon
        return this
    }

    fun setTitle(title: String): DialogCustom {
        mTitle = title
        return this
    }

    fun setMessage(message: String): DialogCustom {
        mMessage = message
        return this
    }

    fun setCancelable(cancelable: Boolean): DialogCustom {
        mCancelable = cancelable
        return this
    }

    fun setTextPositive(textPositive: String): DialogCustom {
        mTextPositive = textPositive
        return this
    }

    fun setTextNegative(textNegative: String): DialogCustom {
        mTextNegative = textNegative
        return this
    }

    fun setOnPositiveListener(listener: (() -> Unit)): DialogCustom {
        onClickPositive = listener
        return this
    }

    fun setOnNegativeListener(listener: (() -> Unit)): DialogCustom {
        onClickNegative = listener
        return this
    }

    fun show() {
        val dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_custom)
        }

        val ivIcon = dialog.findViewById<ImageView>(R.id.ivIcon)
        val tv_title = dialog.findViewById<TextView>(R.id.tv_title)
        val tv_message = dialog.findViewById<TextView>(R.id.tv_message)
        val b_ok = dialog.findViewById<Button>(R.id.b_ok)
        val b_cancel = dialog.findViewById<Button>(R.id.b_cancel)

        if(mIcon!=0){
            ivIcon.setImageDrawable(ContextCompat.getDrawable(context, mIcon))
        }

        if (mTextNegative != "") {
            b_cancel.text = mTextNegative
            b_cancel.visibility = View.VISIBLE
        }

        tv_title.text = mTitle
        tv_message.text = mMessage
        b_ok.text = mTextPositive
        dialog.setCancelable(mCancelable)

        if (!(context as Activity).isFinishing) {
            dialog.show()
        }

        b_ok.setOnClickListener {
            onClickPositive?.invoke()
            dialog.cancel()
        }

        b_cancel.setOnClickListener {
            onClickNegative?.invoke()
            dialog.cancel()
        }
    }
}
