package com.canonicalexamples.stockportfolio.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.stockportfolio.R
import com.canonicalexamples.stockportfolio.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */


class LoginFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        val passEdit = view.findViewById<EditText>(R.id.pass)



        view.findViewById<Button>(R.id.login_button).setOnClickListener {

            val database by lazy { PasswordDatabase.getInstance(requireContext()) }


            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {

                val password = passEdit.text.toString()
                val hashed: Int = password.hashCode()

                database.passwordDao.apply {


                    if(this.count().isEmpty()){
                        this.create(password = Password(value = hashed))
                    }
                    else{

                        var pass:Password? = this.get(pass = hashed)

                        if (pass == null){
                            Log.d("contraseña nula", "contraseña nula")
                        }

                        else{
                            findNavController().navigate(R.id.action_loginFragment_to_FirstFragment)
                        }
                    }

                }

            }

        }
    }
}
