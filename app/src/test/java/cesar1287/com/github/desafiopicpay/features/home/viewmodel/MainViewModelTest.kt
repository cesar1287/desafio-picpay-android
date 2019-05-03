package cesar1287.com.github.desafiopicpay.features.home.viewmodel

import android.app.Application
import cesar1287.com.github.desafiopicpay.core.model.User
import com.nhaarman.mockitokotlin2.spy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        val application = Application()
        mainViewModel = spy(MainViewModel(application))
    }

    @Test
    fun `verificar o caso em que nenhuma letra da match então ele não deveria retornar nada`() {
        val user = User(
            id = 1002,
            img = "img",
            name = "teste",
            username = "username"
        )

        val usersList = listOf(user)
        val expectedList = mainViewModel.doSearch(usersList, "te", 2)

        assertNotEquals(expectedList, usersList)
    }

    @Test
    fun `verificar o caso em que ele deveria retornar somente um user`() {
        val user = User(
            id = 1002,
            img = "img",
            name = "teste",
            username = "username"
        )

        val user1 = User(
            id = 1003,
            img = "img",
            name = "teste",
            username = "teste"
        )

        val usersList = listOf(user, user1)
        val expectedList = mainViewModel.doSearch(usersList, "te", 2)

        assertEquals(expectedList, listOf(user1))
    }

    @Test
    fun `verificar o caso em que ele deveria retornar a lista toda pois o usuário limpou o campo de busca`() {
        val user = User(
            id = 1002,
            img = "img",
            name = "teste",
            username = "username"
        )

        val user1 = User(
            id = 1003,
            img = "img",
            name = "teste",
            username = "teste"
        )

        val usersList = listOf(user, user1)
        val expectedList = mainViewModel.doSearch(usersList, "te", 0)

        assertEquals(expectedList, usersList)
    }
}