package com.example.groupphase.presentation.screens.result_screen

import androidx.lifecycle.ViewModel
import com.example.groupphase.common.Resource
import com.example.groupphase.domain.use_case.simulation_use_cases.GetAllSimulationUseCase
import com.example.groupphase.domain.use_case.simulation_use_cases.GetSimulationByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getSimulationByIdUseCase: GetSimulationByIdUseCase,
    private val getAllSimulationUseCase: GetAllSimulationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ResultState())
    val state = _state

    private val ioScope = CoroutineScope(Dispatchers.IO)

    init {
        getAllSimulations()
    }

    private fun getAllSimulations() {
        _state.value = state.value.copy(isLoading = true)
        getAllSimulationUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        simulations = result.data ?: listOf()
                    )
                }
                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Something went wrong.."
                    )
                }
            }
        }.launchIn(ioScope)
    }

    fun getSimulationById(id: Long) {
        _state.value = state.value.copy(isLoading = true)
        getSimulationByIdUseCase(id).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        success = true,
                        currentSimulation = result.data
                    )
                }
                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Something went wrong.."
                    )
                }
            }
        }.launchIn(ioScope)
    }
}