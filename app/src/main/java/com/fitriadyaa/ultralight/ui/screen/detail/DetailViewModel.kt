package com.fitriadyaa.ultralight.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitriadyaa.ultralight.data.ProductRepository
import com.fitriadyaa.ultralight.model.OrderProduct
import com.fitriadyaa.ultralight.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<OrderProduct>>(UiState.Loading)
    val uiState: StateFlow<UiState<OrderProduct>> = _uiState

    fun getProductById(productId: Long) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val orderProduct = productRepository.getOrderProductById(productId)
                _uiState.value = UiState.Success(orderProduct)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
