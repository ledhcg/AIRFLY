package com.dinhcuong.airfly.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.dinhcuong.airfly.R
import com.dinhcuong.airfly.Storage.SharedPrefManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingThemeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingThemeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting_theme, container, false)
        val sharedPreferences =
            context?.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val nameImageBackground = sharedPreferences?.getString("theme",
            SharedPrefManager.DEFAULT_THEME
        )
        when(nameImageBackground){
            "background_theme1" -> context?.let { checkTheme(view, it, "theme1") }
            "background_theme2" -> context?.let { checkTheme(view, it, "theme2") }
            "background_theme3" -> context?.let { checkTheme(view, it, "theme3") }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingThemeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingThemeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun radioButtonHandle(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            // Check which radio button was clicked
            when (view.getId()) {
                R.id.theme1 -> {
                    if(checked){
                        context?.let { SharedPrefManager.getInstance(it).changeBackground("background_theme1") }
                        context?.let { uncheck(view, it, "theme2", "theme3") }
                    }
                }
                R.id.theme2 -> {
                    if (checked) {
                        context?.let { SharedPrefManager.getInstance(it).changeBackground("background_theme2") }
                        context?.let { uncheck(view, it, "theme1", "theme3") }
                    }
                }
                R.id.theme3 -> {
                    if (checked){
                        context?.let { SharedPrefManager.getInstance(it).changeBackground("background_theme3") }
                        context?.let { uncheck(view, it, "theme1", "theme2") }
                    }
                }
            }
        }
    }

    fun uncheck(view: View, context: Context, idThemeA: String, idThemeB: String){
        val radioThemeA = view.findViewById<RadioButton>(resources.getIdentifier(
            idThemeA, "id",
            context.packageName,
        ))
        val radioThemeB = view.findViewById<RadioButton>(resources.getIdentifier(
            idThemeB, "id",
            context.packageName,
        ))
        radioThemeA.isChecked = false
        radioThemeB.isChecked = false
    }

    fun checkTheme(view: View, context: Context, idTheme: String){
        val radioTheme = view.findViewById<RadioButton>(resources.getIdentifier(
            idTheme, "id",
            context.packageName,
        ))
        radioTheme.isChecked = true
    }
}