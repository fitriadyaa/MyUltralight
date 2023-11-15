package com.fitriadyaa.ultralight.di

import com.fitriadyaa.ultralight.data.ProductRepository

object Injection {
    fun provideRepository(): ProductRepository {
        return ProductRepository.getInstance()
    }
}