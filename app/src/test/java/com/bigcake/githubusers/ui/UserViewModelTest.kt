package com.bigcake.githubusers.ui

import com.bigcake.githubusers.domain.entity.Result
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.domain.usecase.GetUserByPage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(UserViewModelTestExtension::class)
class UserViewModelTest {
    @MockK
    private lateinit var mockGetUserByPage: GetUserByPage

    private lateinit var subjectViewModel: UserViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        // Inject mock use case
        subjectViewModel = UserViewModel(mockGetUserByPage)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @DisplayName("Should filter correct number of users if login exists")
    @Test
    fun filterExistUserLogin(testDispatcher: TestDispatcher) = runTest {
        // Given
        givenDummyUsers()

        // When
        val loginFilterText = "ob"
        subjectViewModel.onLoginFilterTextChanged(loginFilterText)

        // Then
        coVerify { mockGetUserByPage.invoke(0, any()) }
        Assertions.assertEquals(1, subjectViewModel.state.filteredUsers.size)
        Assertions.assertEquals(5, subjectViewModel.state.filteredUsers[0].id)
        Assertions.assertEquals(loginFilterText, subjectViewModel.state.loginFilterText)
    }

    @DisplayName("Should filter zero user if login doesn't exist")
    @Test
    fun filterNonExistUserLogin(testDispatcher: TestDispatcher) = runTest {
        // Given
        givenDummyUsers()

        // When
        val loginFilterText = "omg"
        subjectViewModel.onLoginFilterTextChanged(loginFilterText)

        // Then
        coVerify { mockGetUserByPage.invoke(0, any()) }
        Assertions.assertTrue(subjectViewModel.state.filteredUsers.isEmpty())
        Assertions.assertEquals(loginFilterText, subjectViewModel.state.loginFilterText)
    }

    private fun givenDummyUsers() {
        coEvery { mockGetUserByPage.invoke(any(), any()) } returns flow {
            emit(Result.Page(5, DUMMY_USERS))
        }
        subjectViewModel.loadUsers()
    }

    companion object {
        private val DUMMY_USERS = listOf(
            User(1, "mojombo", "", false),
            User(2, "defunkt", "", false),
            User(3, "pjhyett", "", false),
            User(4, "wycats", "", false),
            User(5, "ezmobius", "", false)
        )
    }
}