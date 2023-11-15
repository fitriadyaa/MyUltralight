package com.fitriadyaa.ultralight.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitriadyaa.ultralight.data.ProductRepository
import com.fitriadyaa.ultralight.ui.screen.detail.DetailViewModel
import com.fitriadyaa.ultralight.ui.screen.home.HomeViewModel

class ViewModelFactory(private val productRepository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(productRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}