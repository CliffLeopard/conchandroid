package com.cliff.datastore

import android.content.pm.PackageInfo
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val packageInfos: DataStore<PackageInfo>,
) {
    val infos = packageInfos.data.map {
        it
    }
}