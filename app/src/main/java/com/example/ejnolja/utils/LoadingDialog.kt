package com.example.ejnolja.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.ejnolja.R

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    //    window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.view_dialog_loading)
    }
}