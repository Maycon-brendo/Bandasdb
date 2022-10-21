package com.example.bandasdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bandasdb.database.MetalRepositoty
import com.example.bandasdb.models.Band
import com.example.bandasdb.models.Musician
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val metalRepositoty = MetalRepositoty.get()

    fun insertBand(band: Band) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.insertBand(band)
        }
    }

    private val _bands: MutableStateFlow<List<Band>> = MutableStateFlow(emptyList())
    val bands: StateFlow<List<Band>>
    get() = _bands.asStateFlow()

    fun insertMusician(musician: Musician) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.insertMusician(musician)
        }
    }

    private val _musicians: MutableStateFlow<List<Musician>> = MutableStateFlow(emptyList())
    val musicians: StateFlow<List<Musician>>
        get() = _musicians.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.getAllBands().collect {
                _bands.value = it
            }

//            metalRepositoty.getAllMusicians().collect {
//                _musicians.value = it
//            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.getAllMusicians().collect {
                _musicians.value = it
            }
        }
    }

}