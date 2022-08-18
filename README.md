# GitHubUsers
A simple project fetching user list and user profile from https://api.github.com/
Please add the following line into local.property file for higher rate limit ([Creating Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token))
```
# local.properties
accessToken="Your GitHub Access Token"
```

### Techniques
- Pattern - CLEAN, MVVM
- Dependency injection - Hilt
- UI - Jetpack Compose
- Navigation - Navigation
- Web API - Retrofit, Moshi
- Unit test - JUnit5

### Features
- Infinite user list loading (paginated)
- Filter by login

### Unit tests
- LinkHeaderHelperTest - tests GitHub Link Header parsing
- UserViewModelTest - tests filtering function in user list viewmodel
    - Test case #1
        - Given: Dummy users
        - When: Filter with login text that filters exactly 1 user
        - Then:
            1. The filtered user list in view model should contain only 1 user
            2. The user id matches that of the target user
            3. The filter text is updated
    - Test case #2
        - Given: Dummy users
        - When: Filter with login text that filters 0 user
        - Then:
            1. The filtered user list in view model should be empty
            2. The filter text is updated

### To-dos
- Refine theming
- Screen transition
- Filter UX
