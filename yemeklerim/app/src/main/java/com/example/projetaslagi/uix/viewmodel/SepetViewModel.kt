package com.example.projetaslagi.uix.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetaslagi.data.entity.Sepet
import com.example.projetaslagi.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SepetViewModel @Inject constructor(
    private val yrepo: YemeklerRepository
) : ViewModel() {

    val sepetListesi = MutableLiveData<List<Sepet>>()



    fun sil(sepet_yemek_id: Int, kullaniciAdi: String) {
        viewModelScope.launch {
            yrepo.sil(sepet_yemek_id, kullaniciAdi)

            // Sepeti yeniden güncelle
            ara(kullaniciAdi)
        }
    }



    fun ara(kullaniciAdi: String) {
        viewModelScope.launch {
            val sepetYemekleri = yrepo.ara(kullaniciAdi)
            sepetListesi.value = grupYemekleri(sepetYemekleri)
        }
    }





    private fun grupYemekleri(sepetYemekleri: List<Sepet>): List<Sepet> {

        return sepetYemekleri.groupBy { it.yemek_adi }
            .map { entry ->
                val yemek = entry.value.first()
                val toplamAdet = entry.value.sumOf { it.yemek_siparis_adet }
                yemek.copy(yemek_siparis_adet = toplamAdet)
            }
    }









}

