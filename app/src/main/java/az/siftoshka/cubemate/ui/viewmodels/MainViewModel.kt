package az.siftoshka.cubemate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import az.siftoshka.cubemate.db.MainRepository
import az.siftoshka.cubemate.db.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val resultsByTime = mainRepository.getAllResultsByTime().asLiveData(viewModelScope.coroutineContext)
    val resultsByDate = mainRepository.getAllResultsByDate().asLiveData(viewModelScope.coroutineContext)
    val resultsByType = mainRepository.getAllResultsByType().asLiveData(viewModelScope.coroutineContext)
    val avgResult = mainRepository.getAvgResult().asLiveData(viewModelScope.coroutineContext)
    val bestResult = mainRepository.getBestResult().asLiveData(viewModelScope.coroutineContext)
    val recentResult = mainRepository.getRecentResult().asLiveData(viewModelScope.coroutineContext)

    fun insertResult(result: Result) = viewModelScope.launch { mainRepository.insertResult(result) }

    fun deleteResult(result: Result) = viewModelScope.launch { mainRepository.deleteResult(result) }
}