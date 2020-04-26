package com.example.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackmysleepquality.database.SleepDatabaseDao
import com.example.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.Job

class SleepDetailViewModel(private val sleepNightKey: Long = 0L,
dataSource: SleepDatabaseDao): ViewModel() {
    val database = dataSource

    private val viewModelJob = Job()
    private val night = MediatorLiveData<SleepNight>()
    fun getNight() = night

    init{
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
    get() = _navigateToSleepTracker

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigating(){
        _navigateToSleepTracker.value = null
    }

    fun onClose(){
        _navigateToSleepTracker.value = true
    }

}