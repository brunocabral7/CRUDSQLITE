package br.unistanta.aplicativoroom.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.unistanta.aplicativoroom.dao.UserDao
import br.unistanta.aplicativoroom.database.AppDatabase
import br.unistanta.aplicativoroom.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db:AppDatabase
    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-exercicio-salvar"
        ).fallbackToDestructiveMigration()
            .build()
        userDao = db.userDao()

        binding.btnCadastrar.setOnClickListener{
            val intent = Intent(this@MainActivity, CadastrarActivity::class.java)

            startActivity(intent)

        }


        binding.btnLogin.setOnClickListener{
            val email = binding.edtFname.text.toString()
            val password = binding.edtLname.text.toString()
           lifecycleScope.launch {
               val list = userDao.login(email,password)
               if(list.isNotEmpty()){
                   val intent = Intent(this@MainActivity, UserActivity::class.java)
                    val uid= list.first().uid
                   intent.putExtra("uid",uid.toString())

                   startActivity(intent)

                   Log.i("Login","Logado")
                   mostraToast("Logado Com Sucesso")
               }else{
                   Log.i("Login","Usuario Inexistente")
                   mostraToast("Erro ao Logar")
               }

           }
        }
    }
    private fun mostraToast(mensagem:String){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }
}