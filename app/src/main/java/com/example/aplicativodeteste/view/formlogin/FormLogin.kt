package com.example.aplicativodeteste.view.formlogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplicativodeteste.R
import com.example.aplicativodeteste.databinding.ActivityFormLoginBinding
import com.example.aplicativodeteste.view.formcadastro.FormCadastro
import com.example.aplicativodeteste.view.telaprincipal.TelaPrincipal
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btEntrar.setOnClickListener {view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                    if (autenticacao.isSuccessful){
                        //função criada para poder navegar entre as telas
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view, "Erro ao logar!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        //finish para poder finalizar a ultima tela que você estava
        finish()
    }
    //Ira verificar se o usuario ja esta conectado no sistema, assim não precisa ir para a tela de login novamente
    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null){
            navegarTelaPrincipal()
        }
    }
}