package com.android.stitch.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.android.stitch.R
import com.android.stitch.activity.AboutActivity
import com.android.stitch.activity.IntroActivity
import com.android.stitch.activity.LoginActivity
import com.android.stitch.activity.RiwayatActivity
import com.android.stitch.helper.SharedPref

/**
 * A simple [Fragment] subclass.
 */
class AkunFragment : Fragment() {
    lateinit var sp:SharedPref
    lateinit var btnLogout:TextView
    lateinit var btnPenjahit: RelativeLayout
    lateinit var btnTentang: RelativeLayout
    lateinit var btnKeranjang: RelativeLayout
    lateinit var btnMap: RelativeLayout
    lateinit var tvNama:TextView
    lateinit var tvEmail:TextView
    lateinit var tvPhone:TextView
    lateinit var btnRiwayat: RelativeLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)
        val fragmentKeranjang = KeranjangFragment()
        val fragmentPenjahit = PenjahitFragment()
        val fragmentMap = MapFragment()

        btnKeranjang.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, fragmentKeranjang, KeranjangFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
            }
        }

        btnPenjahit.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, fragmentPenjahit, PenjahitFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
            }
        }

        btnMap.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, fragmentMap, MapFragment::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
            }
        }

        sp = SharedPref(requireActivity())
        mainButton()
        setData()
        return view
    }

    fun mainButton(){
        btnLogout.setOnClickListener {
            sp.setStatusLogin(false)
            Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show()
        }

        btnTentang.setOnClickListener {
            startActivity(Intent(requireActivity(), AboutActivity::class.java))
        }

        btnRiwayat.setOnClickListener {
            startActivity(Intent(requireActivity(), RiwayatActivity::class.java))
        }
    }

    fun setData(){

        if(sp.getUser() == null){
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return 
        }
        val user = sp.getUser()!!
        tvNama.text = user.name
        tvEmail.text = user.email
        tvPhone.text = user.phone
    }

    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.txtnama)
        tvEmail = view.findViewById(R.id.txtemail)
        tvPhone = view.findViewById(R.id.txtphone)
        btnKeranjang = view.findViewById(R.id.btn_keranjangakun)
        btnTentang = view.findViewById(R.id.btn_tentang)
        btnMap = view.findViewById(R.id.btn_lihatmap)
        btnPenjahit = view.findViewById(R.id.btn_listpenjahit)
        btnRiwayat = view.findViewById(R.id.btn_riwayat)

    }

}