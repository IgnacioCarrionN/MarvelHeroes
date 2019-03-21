package dev.carrion.marvelheroes.ui.mainlist

import android.app.Dialog
import android.content.Context
import dev.carrion.marvelheroes.R


/**
 * Loading Dialog class
 *
 * This class has the logic to create, show and hide a loading dialog.
 *
 * @constructor Context to create the dialog.
 *
 * @author Ignacio Carrion
 */
class LoadingDialog(val context: Context) {

    private val dialog = Dialog(context)

    /**
     *  Initialize the dialog
     */
    init {
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
    }

    /**
     * Shows dialog
     */
    fun showDialog(){
        dialog.show()
    }

    /**
     * Hides dialog
     */
    fun hideDialog(){
        dialog.dismiss()
    }
}