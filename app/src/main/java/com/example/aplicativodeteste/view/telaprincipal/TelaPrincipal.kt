package com.example.aplicativodeteste.view.telaprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.aplicativodeteste.R
import com.example.aplicativodeteste.databinding.ActivityTelaPrincipalBinding
import com.example.aplicativodeteste.view.formlogin.FormLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPrincipalBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }

        binding.btGravarDadosDB.setOnClickListener {

            val usuariosMap = hashMapOf(
                "nome" to "Maria",
                "sobrenome" to "da Silva",
                "idade" to 25
            )

            db.collection("Usuários").document("Otavio")
                .set(usuariosMap).addOnCompleteListener {
                    Log.d("db", "Sucesso ao salvar os dados do usuário!")
                }.addOnFailureListener {

                }
        }

        binding.btLerDadosDB.setOnClickListener {
            db.collection("Usuários").document("Otavio")
                .addSnapshotListener { documento, error ->
                    if (documento != null){
                        binding.txtResultadoNome.text = documento.getString("nome")
                        binding.txtResultadoSobrenome.text = documento.getString("sobrenome")
                        binding.txtResultadoIdade.text = documento.getLong("idade").toString()
                    }
                }
        }

        binding.btAtualizarDadosDB.setOnClickListener {
            db.collection("Usuários").document("Otavio")
                .update("sobrenome","Menezes","idade", 25).addOnCompleteListener {
                    Log.d("db_update", "Sucesso ao atualizar os dados do usuário!")
                }
        }

        binding.btDeletarDadosDB.setOnClickListener {
            db.collection("Usuários").document("Maria")
                .delete().addOnCompleteListener {
                    Log.d("db_delete", "Sucesso ao deletar os dados do usuário!")
                }
        }
    }
}