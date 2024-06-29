package com.cliff.data.repository

import android.content.pm.PackageInfo
import com.cliff.datastore.PreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class OfflinePackageInfoRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : PackageInfoRepository {
    override val packageInfos: Flow<PackageInfo> = preferencesDataSource.infos
}