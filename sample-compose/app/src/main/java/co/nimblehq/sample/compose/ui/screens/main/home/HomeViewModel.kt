package co.nimblehq.sample.compose.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.sample.compose.domain.usecase.GetModelsUseCase
import co.nimblehq.sample.compose.domain.usecase.IsFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.domain.usecase.UpdateFirstTimeLaunchPreferencesUseCase
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.model.toUiModel
import co.nimblehq.sample.compose.ui.base.BaseViewModel
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getModelsUseCase: GetModelsUseCase,
    isFirstTimeLaunchPreferencesUseCase: IsFirstTimeLaunchPreferencesUseCase,
    private val updateFirstTimeLaunchPreferencesUseCase: UpdateFirstTimeLaunchPreferencesUseCase,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel() {

    private val _uiModels = MutableStateFlow<List<UiModel>>(emptyList())
    val uiModels = _uiModels.asStateFlow()

    private val _isFirstTimeLaunch = MutableStateFlow(false)
    val isFirstTimeLaunch = _isFirstTimeLaunch.asStateFlow()

    init {
        getModelsUseCase()
            .injectLoading()
            .onEach { result ->
                val uiModels = result.map { it.toUiModel() }
                _uiModels.emit(uiModels)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)

        isFirstTimeLaunchPreferencesUseCase()
            .onEach { isFirstTimeLaunch ->
                _isFirstTimeLaunch.emit(isFirstTimeLaunch)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e -> _error.emit(e) }
            .launchIn(viewModelScope)
    }

    fun onFirstTimeLaunch() {
        launch(dispatchersProvider.io) {
            updateFirstTimeLaunchPreferencesUseCase(false)
            _isFirstTimeLaunch.emit(false)
        }
    }

    fun navigateToSecond(uiModel: UiModel) {
        launch { _navigator.emit(MainDestination.Second.createRoute(uiModel.id)) }
    }

    fun navigateToThird(uiModel: UiModel) {
        launch { _navigator.emit(MainDestination.Third.addParcel(uiModel)) }
    }
}
