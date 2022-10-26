package com.example.bandasdb.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bandasdb.database.MetalRepositoty
import com.example.bandasdb.models.Band
import com.example.bandasdb.models.BandMusician
import com.example.bandasdb.models.Musician
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val TAG = "band"

    private val metalRepositoty = MetalRepositoty.get()

    private val _bands: MutableStateFlow<List<Band>> = MutableStateFlow(emptyList())
    val bands: StateFlow<List<Band>>
    get() = _bands.asStateFlow()


    // saves a selected band for edition
    private val _selectedBandId = MutableLiveData<Long>(0L)
    val selectedBandId: LiveData<Long> = _selectedBandId
    fun setSelectedBandId(value: Long) {
        _selectedBandId.setValue(value)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CRUD Band //////////////////////////////////////////////////////////////////////////////////

    fun insertBand(band: Band) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.insertBand(band)
        }
    }

    suspend fun getBandById(id: Long): Band {
        val receivedBand = viewModelScope.async(Dispatchers.IO) {
            return@async metalRepositoty.getBandById(id)
        }
        return receivedBand.await()
    }

    fun updateBand(band: Band) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.updateBand(band)
        }
    }

    fun deleteBand(band: Band) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.deleteBand(band)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private val _musicians: MutableStateFlow<List<Musician>> = MutableStateFlow(emptyList())
    val musicians: StateFlow<List<Musician>>
        get() = _musicians.asStateFlow()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Musician CRUD  //////////////////////////////////////////////////////////////////////////////////
    fun insertMusician(musician: Musician) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.insertMusician(musician)
        }
    }

    fun getMusicianById(id: Long): Musician {
        var musician: Musician? = null
        val job = viewModelScope.launch(Dispatchers.IO) {
            val musicianAsync = async {
                metalRepositoty.getMusicianById(id)
            }
            musician = musicianAsync.await()
        }
        return musician ?: Musician()
    }

    fun updateMusician(musician: Musician) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.updateMusician(musician)
        }
    }

    fun deleteMusician(musician: Musician) {
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.deleteMusician(musician)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CRUD BandMusician ///////////////////////////////////////////////////////////////////////////

    fun insertBandMusician(bandMusician: BandMusician){
        viewModelScope.launch(Dispatchers.IO) {
            metalRepositoty.insertBandMusician(bandMusician)
        }
    }

    suspend fun getBandMusicianByBandIdAndMusicianId(bandId: Long, musicianId: Long): BandMusician{
        val bandMusician = viewModelScope.async(Dispatchers.IO){
            metalRepositoty.getBandMusicianByBandIdAndMusicianId(bandId, musicianId)
        }
        return bandMusician.await()
    }

    fun deleteBandMusician(bandId: Long, musicianId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            val bandMusician = getBandMusicianByBandIdAndMusicianId(bandId, musicianId)
            metalRepositoty.deleteBandMusician(bandMusician)
        }


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////


    // viewModel initialier

    init {
        collectMusicians()
        collectBands()
    }
//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            metalRepositoty.getAllBands().collect {
//                _bands.value = it
//            }
//        }
//    }
//
//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            metalRepositoty.getAllMusicians().collect {
//                _musicians.value = it
//            }
//        }
//    }

    fun collectMusicians() {
        viewModelScope.launch {
            metalRepositoty.getAllMusicians().collect {
                _musicians.value = it
            }
        }
    }

    fun collectBands() {
        viewModelScope.launch {
            metalRepositoty.getAllBands().collect {
                _bands.value = it
            }
        }
    }

    // StateFlow e SharedFlow
    // https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
    // receives bands from database as StateFlow and stays the last Search made
    private val _musiciansByName: MutableStateFlow<List<Musician>> = MutableStateFlow(emptyList())
    val musiciansByName: StateFlow<List<Musician>>
        get() = _musiciansByName.asStateFlow()

    // Searchng with "%${input}%" will return the ones that contains input in the name
    fun collectMusiciansByName(input: String) {
        viewModelScope.launch {
            metalRepositoty.getMusicianByName("%${input}%").collect {
                _musiciansByName.value = it
                it.forEach {
                    Log.i(TAG, "Musician: ${it.name}")
                }
            }
        }
    }

    // StateFlow e SharedFlow
    // https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
    // Recebe turmas do banco de dados como StateFlow e permanece a última pesquisa feita
    private val _musiciansInBand: MutableStateFlow<List<Musician>> = MutableStateFlow(emptyList())
    val musiciansInBand: StateFlow<List<Musician>>
        get() = _musiciansInBand.asStateFlow()

    // Pesquisando com "%${input}%" vai retornar os que contém input no nome
    fun collectMusiciansInBand(input: Long) {
        viewModelScope.launch {
            selectedBandId.value?.let { bandId ->
                metalRepositoty.getMusiciansFromBand(bandId).collect { listBand ->
                    _musiciansInBand.value = listBand
                }
            }
        }
    }
}