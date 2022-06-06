package com.dinhcuong.airfly.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.dinhcuong.airfly.Activity.MainActivity
import com.dinhcuong.airfly.Activity.SettingActivity
import com.dinhcuong.airfly.Adapter.SettingViewPagerAdapter
import com.dinhcuong.airfly.R
import com.dinhcuong.airfly.Storage.SharedPrefManager
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingAccountFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting_account, container, false);

        val sharedPreferences =
            context?.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val accountName = sharedPreferences?.getString("name", "John")
        val accountEmail = sharedPreferences?.getString("email", "test@ledinhcuong.com")
        val accountPhone = sharedPreferences?.getString("phone", "+XXXXXXXXXXX")

        val textAccountName = view.findViewById<TextView>(R.id.account_name)
        val textAccountEmail = view.findViewById<TextView>(R.id.account_email)
        val textAccountPhone = view.findViewById<TextView>(R.id.account_phone)

        textAccountName.text = accountName
        textAccountEmail.text = accountEmail
        textAccountPhone.text = accountPhone

        val buttonLogout = view.findViewById<Button>(R.id.button_logout)
        buttonLogout.setOnClickListener {
            context?.let { SharedPrefManager.getInstance(it).clear() }
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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
         * @return A new instance of fragment SettingAccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingAccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}