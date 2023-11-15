package com.fitriadyaa.ultralight.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitriadyaa.ultralight.data.ProductRepository
import com.fitriadyaa.ultralight.model.OrderProduct
import com.fitriadyaa.ultralight.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (
    private val productRepository: ProductRepository
): ViewModel(){
    private val _uiState: MutableStateFlow<UiState<List<OrderProduct>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderProduct>>>
        get() = _uiState

    fun getAllProduct() {
        viewModelScope.launch {
            productRepository.getAllProduct()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderProduct ->
                    _uiState.value = UiState.Success(orderProduct)
                }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            productRepository.searchProducts(query)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { searchedProducts ->
                    _uiState.value = UiState.Success(searchedProducts)
                }
        }
    }
}