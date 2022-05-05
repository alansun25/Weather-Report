package com.example.weatherreport.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weatherreport.City
import com.example.weatherreport.databinding.ActivityDialogBinding

class CityDialog : DialogFragment() {
    interface CityHandler {
        fun cityAdded(item: City)
    }

    private lateinit var cityHandler: CityHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CityHandler){
            cityHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the CityHandler interface.")
        }
    }

    private lateinit var binding: ActivityDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        binding = ActivityDialogBinding.inflate(requireActivity().layoutInflater)
        dialogBuilder.setView(binding.root)

        dialogBuilder.setPositiveButton("Add") {
                dialog, which ->
        }

        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            if (binding.etName.text.isEmpty()) {
                binding.etName.error = "Please enter a city name."
            } else {
                handleAdd()
                dialog.dismiss()
            }
        }
    }

    private fun handleAdd() {
        cityHandler.cityAdded(
            City(binding.etName.text.toString())
        )
    }
}