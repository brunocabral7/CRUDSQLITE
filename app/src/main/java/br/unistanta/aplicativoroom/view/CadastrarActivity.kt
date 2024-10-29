package br.unistanta.aplicativoroom.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.unistanta.aplicativoroom.R
import br.unistanta.aplicativoroom.dao.UserDao
import br.unistanta.aplicativoroom.database.AppDatabase
import br.unistanta.aplicativoroom.databinding.ActivityCadastrarBinding
import br.unistanta.aplicativoroom.model.User
import kotlinx.coroutines.launch

class CadastrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarBinding
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)
        binding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-exercicio-salvar"
        ).fallbackToDestructiveMigration()
            .build()
        userDao = db.userDao()

        binding.btnCadastrar.setOnClickListener{
            lifecycleScope.launch {


                val user=User(0,
                    binding.edtName.text.toString(),
                    binding.edtPassword.text.toString(),
                    binding.edtEmail.text.toString(),
                    binding.edtPhoto.text.toString()
                    )

                val dados = userDao.insert(user)



                if(dados>0){
                    mostraToast("Dados Cadastrados com sucesso")
                    onBackPressed()
                }else{
                    mostraToast("Erro ao cadastrar os dados")
                }

            }
        }
    }

    private fun mostraToast(mensagem:String){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }


}