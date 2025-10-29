package com.yadhuchoudhary.yadhu_choudhary_myruns3

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.yadhuchoudhary.yadhu_choudhary_myruns3.R

class MyRunsDialogFragment : DialogFragment() {

    interface DialogListener {
        fun onDialogPositiveClick(dialogId: Int, value: String)
    }

    private var listener: DialogListener? = null
    private var dialogId: Int = 0
    private var title: String = ""
    private var initialValue: String = ""

    companion object {
        private const val ARG_DIALOG_ID = "dialog_id"
        private const val ARG_TITLE = "title"
        private const val ARG_INITIAL_VALUE = "initial_value"

        fun newInstance(dialogId: Int, title: String, initialValue: String = ""): MyRunsDialogFragment {
            val fragment = MyRunsDialogFragment()
            val args = Bundle().apply {
                putInt(ARG_DIALOG_ID, dialogId)
                putString(ARG_TITLE, title)
                putString(ARG_INITIAL_VALUE, initialValue)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement DialogListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dialogId = it.getInt(ARG_DIALOG_ID)
            title = it.getString(ARG_TITLE, "")
            initialValue = it.getString(ARG_INITIAL_VALUE, "")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_input, null)

        val editText = view.findViewById<EditText>(R.id.edit_text_dialog)
        editText.setText(initialValue)

        builder.setView(view)
            .setTitle(title)
            .setPositiveButton("OK") { _, _ ->
                val value = editText.text.toString()
                listener?.onDialogPositiveClick(dialogId, value)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}