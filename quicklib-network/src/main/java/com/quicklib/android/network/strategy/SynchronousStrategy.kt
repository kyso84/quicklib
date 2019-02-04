package com.quicklib.android.network.strategy

import androidx.annotation.MainThread
import androidx.lifecycle.MediatorLiveData
import com.quicklib.android.network.DataStatus
import com.quicklib.android.network.DataWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class SynchronousStrategy<T>(mainScope: CoroutineScope = CoroutineScope(Dispatchers.Main), liveData: MediatorLiveData<DataWrapper<T>> = MediatorLiveData()) : DataStrategy<T>(mainScope = mainScope, liveData = liveData) {
    override fun start(): Job = askData()

    private fun askData() = mainScope.launch {
        try {
            liveData.value = DataWrapper(value = getData(), status = DataStatus.SUCCESS, strategy = this@SynchronousStrategy::class)
        } catch (e: Throwable) {
            liveData.value = DataWrapper(error = e, status = DataStatus.ERROR, strategy = this@SynchronousStrategy::class)
        }
    }

    @MainThread
    abstract fun getData(): T
}