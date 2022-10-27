package co.app.studyteam.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import co.app.studyteam.databinding.DialogFragmentBinding

class DialogName(
    private val onSubmitClickListener: (String) -> Unit,
    private val showText: (String) -> Unit

):DialogFragment(){

    private lateinit var binding: DialogFragmentBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.createDialogButton.setOnClickListener {
            onSubmitClickListener.invoke(binding.nameDialogText2.text.toString())
            showText.invoke(binding.activitySelectedText.text.toString())

        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        return dialog
    }

}