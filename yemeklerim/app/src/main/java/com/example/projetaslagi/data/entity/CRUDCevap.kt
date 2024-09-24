package com.example.projetaslagi.data.entity

data class CRUDCevap(
    val success: Int,  // 1 ise başarılı, 0 ise başarısız
    val message: String? = null
)
