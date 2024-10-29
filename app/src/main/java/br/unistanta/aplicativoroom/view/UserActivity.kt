package br.unistanta.aplicativoroom.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.unistanta.aplicativoroom.R
import br.unistanta.aplicativoroom.dao.UserDao
import br.unistanta.aplicativoroom.database.AppDatabase
import br.unistanta.aplicativoroom.databinding.ActivityUserBinding
import br.unistanta.aplicativoroom.model.User
import coil.load
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    lateinit var uid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-exercicio-salvar"
        ).fallbackToDestructiveMigration()
            .build()
        userDao = db.userDao()

        uid = intent.getStringExtra("uid").toString()
        colocaInformacoes(uid)

        binding.btnAtualizar.setOnClickListener{
            lifecycleScope.launch {
                val dados = userDao.update(getUsuario())
                if(dados>0){
                    mostraToast("Dados Atualizados com sucesso")
                    colocaInformacoes(uid)
                }else{
                    mostraToast("Erro ao atualizar os dados")
                }
            }
        }

        binding.btnDeletar.setOnClickListener{
            lifecycleScope.launch {
                val dados = userDao.delete(getUsuario())
                if(dados>0){
                    mostraToast("Dados deletado com sucesso")
                    onBackPressed()
                }else{
                    mostraToast("Erro ao deletar os dados")
                }
            }
        }

    }

    private fun mostraToast(mensagem:String){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    private fun colocaInformacoes(uid: String?) {
        lifecycleScope.launch {
            uid?.let {
                val user = userDao.getUser(it).first()
                binding.edtName.setText(user.name.toString())
                binding.edtEmail.setText(user.email.toString())
                binding.edtPassword.setText(user.password.toString())
                binding.edtPhoto.setText(user.photo.toString())

                binding.imageView.load(user.photo) {
                    crossfade(true)
                    error(R.drawable.ic_error)
                }

            }
        }
    }

    private fun getUsuario():User{
        return User(
            uid!!.toInt(),
            binding.edtName.text.toString(),
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString(),
                    binding.edtPhoto.text.toString()
        )
    }

}