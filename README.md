# Getting Started
  * Library
  * Design Pattern
  * Code Doc

## Library
* Retrofit
* Lottie
* datastore
* koin

## Design Pattern
* MVVM

## Code Doc
Code use MVVM pattern Model View View Model and use koin Depedency Injection and there repository for take data from network then give to viewModel from viewModel continue to view in viewmodel there sealed class define state when loading ,success and failed get data 
### ex Repository 
```
repo 
interface {
suspend fun AuthRepo() : Resource<AuthModel>
}

repo implementation
class AuthRepoImpl(private val services: Services) : AuthRepo { 
    override suspend fun login(requestBody: AuthRequest): Resource<AuthModel> {
        return try {
            val response = services.login(requestBody)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Failed(message = response.message())
            }
        } catch (e: Exception) {
            Resource.Failed(e.message ?: "An Occured Error")
        }
    }
}

```
### ex viewmodel
```
sealed class Login
data class Success(val data: AuthModel) : Login()
object Loading : Login()
data class FailedMessage(val errorMessage: String) : Login()

class LoginViewModel(private val repo: AuthRepo) : ViewModel() {

    val loginState = MutableLiveData<Login>()

    fun login(request: AuthRequest) = viewModelScope.launch {
        loginState.postValue(Loading)
        when (val result = repo.login(request)) {
            is Resource.Success -> loginState.postValue(result.data?.let { Success(it) })
            is Resource.Failed -> loginState.postValue(result.message?.let { FailedMessage(it) })
        }
    }
}

```
### state  implementation in view
```
when (state) {
                is Loading -> {
                    bind?.loginBtn?.setState(ButtonState.LOADING)
                    bind?.registrationBtn?.isEnabled = false
                }
                is Success -> {
                    state.data.token?.let {
                        CoroutineScope(Dispatchers.Default).launch {
                            dataStore.setToken(it)
                            dataStore.setUserName(bind?.usernameEdt?.text.toString())
                        }
                        navigateToTransaction(this)
                        Toast.makeText(this@LoginActivity, "success", Toast.LENGTH_SHORT).show()
                    }
                }
                is FailedMessage -> {
                    bind?.registrationBtn?.isEnabled = true
                    bind?.loginBtn?.setState(ButtonState.ENABLED)
                    Toast.makeText(this, "Failed Login", Toast.LENGTH_SHORT)
                        .show()
                }
            }
   ```         


