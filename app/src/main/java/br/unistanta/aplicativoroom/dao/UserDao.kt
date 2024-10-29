package br.unistanta.aplicativoroom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.unistanta.aplicativoroom.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid = :value1")
    suspend fun getUser(value1:String): List<User>

    @Update
    suspend fun update(users: User):Int

    @Delete
    suspend fun delete(users: User):Int

    @Insert
    suspend fun insert(user: User):Long

    @Query ("SELECT * FROM user WHERE email = :value1 AND password = :value2")
    suspend fun login(value1:String, value2:String): List<User>
}
