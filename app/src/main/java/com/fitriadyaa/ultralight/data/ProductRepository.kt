package com.fitriadyaa.ultralight.data

import com.fitriadyaa.ultralight.model.OrderProduct
import com.fitriadyaa.ultralight.model.ProductResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val orderProduct = mutableListOf<OrderProduct>()

    init {
        if (orderProduct.isEmpty()) {
            ProductResources.dummyProduct.forEach {
                orderProduct.add(OrderProduct(it, 0))
            }
        }
    }

    fun getAllProduct(): Flow<List<OrderProduct>> {
        return flowOf(orderProduct)
    }

    fun getOrderProductById(productId: Long): OrderProduct {
        return orderProduct.firstOrNull {
            it.product.id == productId
        } ?: throw NoSuchElementException("OrderProduct with ID $productId not found.")
    }

    fun searchProducts(query: String): Flow<List<OrderProduct>> {
        return flow {
            val searchResults = orderProduct.filter {
                it.product.title.contains(query, ignoreCase = true)
            }
            emit(searchResults)
        }
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(): ProductRepository =
            instance ?: synchronized(this) {
                ProductRepository().apply {
                    instance = this
                }
            }
    }
}
