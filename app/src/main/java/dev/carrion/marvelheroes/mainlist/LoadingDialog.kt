package dev.carrion.marvelheroes.mainlist

import android.app.Dialog
import android.content.Context
import android.view.Window
import dev.carrion.marvelheroes.R

class LoadingDialog(val context: Context) {
    private val dialog = Dialog(context)

    init {
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
    }

    fun showDialog(){
        dialog.show()
    }

    fun hideDialog(){
        dialog.dismiss()
    }
}