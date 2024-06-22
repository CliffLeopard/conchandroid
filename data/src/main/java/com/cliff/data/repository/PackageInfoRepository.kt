package com.cliff.data.repository

import android.content.pm.PackageInfo
import kotlinx.coroutines.flow.Flow

interface PackageInfoRepository {
    val userData: Flow<PackageInfo>
}