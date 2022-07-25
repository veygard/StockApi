package com.veygard.stockapi.presentation.screens.error

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.veygard.stockapi.R
import com.veygard.stockapi.databinding.FragmentErrorBinding
import com.veygard.stockapi.databinding.FragmentStockItemBinding

class ErrorFragment : Fragment() {
    private val binding: FragmentErrorBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reconnectButtonListener()
    }

    private fun reconnectButtonListener() {
        binding.reconnectButton.setOnClickListener {
            if(isInternetAvailable()){
                findNavController().popBackStack()
            }else{
                Toast.makeText(requireContext(), getString(R.string.critical_error_no_internet_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        var status = false
        requireContext().let {
            val cm =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null) {
                status = true
            }
        }
        return status
    }
}