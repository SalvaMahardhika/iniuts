package com.example.utsmobileed

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.userDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetUser_returnsCorrectUser() = runBlocking {
        val user = User(
            id = 0,
            name = "John Doe",
            email = "john@example.com",
            password = "123456",
            phone = "08123456789",
            address = "Jl. Mawar No.1"
        )
        dao.register(user)

        val result = dao.login("john@example.com", "123456")
        Assert.assertNotNull(result)
        Assert.assertEquals("John Doe", result?.name)
    }

    @Test
    fun insertMultipleUsers_andGetAll() = runBlocking {
        val user1 = User(
            id = 0,
            name = "Alice",
            email = "alice@example.com",
            password = "alice123",
            phone = "08211223344",
            address = "Jl. Melati No.2"
        )
        val user2 = User(
            id = 0,
            name = "Bob",
            email = "bob@example.com",
            password = "bob123",
            phone = "08333445566",
            address = "Jl. Kenanga No.3"
        )

        dao.register(user1)
        dao.register(user2)

        val result1 = dao.login("alice@example.com", "alice123")
        val result2 = dao.login("bob@example.com", "bob123")

        Assert.assertNotNull(result1)
        Assert.assertEquals("Alice", result1?.name)

        Assert.assertNotNull(result2)
        Assert.assertEquals("Bob", result2?.name)
    }

    @Test
    fun updateUser_updatesCorrectly() = runBlocking {
        val user = User(
            id = 0,
            name = "Charlie",
            email = "charlie@example.com",
            password = "charlie123",
            phone = "08445566778",
            address = "Jl. Anggrek No.4"
        )
        val id = dao.register(user)  // Simpan ID hasil insert

        // Ambil user dari database dengan ID yang didapat
        val insertedUser = dao.login("charlie@example.com", "charlie123")!!

        // Buat user baru dengan data update dan ID yang valid
        val updatedUser = insertedUser.copy(
            id = id.toInt(),
            name = "Charlie Updated",
            password = "newpass123"
        )
        dao.update(updatedUser)

        // Cek update dengan login menggunakan password baru
        val result = dao.login("charlie@example.com", "newpass123")
        Assert.assertNotNull(result)
        Assert.assertEquals("Charlie Updated", result?.name)
        Assert.assertEquals("newpass123", result?.password)
        Assert.assertEquals("08445566778", result?.phone)
        Assert.assertEquals("Jl. Anggrek No.4", result?.address)
    }
}
