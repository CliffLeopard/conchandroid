package com.cliff.conch.box.service.origin

import android.app.PictureInPictureUiState
import android.app.assist.AssistContent
import android.app.assist.AssistStructure
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ConfigurationInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import com.cliff.hidden.HiddenApi
import com.orhanobut.logger.Logger
import reflect.android.app.ActivityThread
import reflect.android.app.ActivityThread__Functions.currentActivityThread
import wrapper.android.app.IActivityTaskManager
import wrapper.replace.PIAT
import wrapper.replace.PProfilerInfo

open class OriginATMS : IActivityTaskManager.Stub() {
    private val originATMS by lazy {
        OriginServerManager.getOriginATMS()
    }
    private val activityThread by lazy {
        ActivityThread.currentActivityThread()
    }
    private val applicationThread by lazy {
        activityThread?.getApplicationThread()
    }

    override fun startActivity(
        caller: PIAT?,
        callingPackage: String?,
        callingFeatureId: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        pProfilerInfo: PProfilerInfo?,
        options: Bundle?
    ): Int {
        Logger.i("originATMS:originATMS:${originATMS.javaClass.canonicalName}")
        return HiddenApi.invoke(
            originATMS::class.java,
            originATMS,
            "startActivity",
            applicationThread,
            callingPackage,
            callingFeatureId,
            intent,
            resolvedType,
            resultTo,
            resultWho,
            requestCode,
            flags,
            null,
            options
        ) as Int
    }

    override fun startActivities(
        caller: PIAT?,
        callingPackage: String?,
        callingFeatureId: String?,
        intents: Array<out Intent>?,
        resolvedTypes: Array<out String>?,
        resultTo: IBinder?,
        options: Bundle?,
        userId: Int
    ): Int {
        Logger.i("OriginATMS:startActivities")
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startActivities",
            applicationThread,
            callingPackage,
            callingFeatureId,
            intents,
            resolvedTypes,
            resultTo,
            options,
            userId
        ) as Int
    }

    override fun startActivityAsUser(
        caller: PIAT?,
        callingPackage: String?,
        callingFeatureId: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        pProfilerInfo: PProfilerInfo?,
        options: Bundle?,
        userId: Int
    ): Int {
        Logger.i("OriginATMS:startActivityAsUser")
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startActivityAsUser",
            applicationThread,
            callingPackage,
            callingFeatureId,
            intent,
            resolvedType,
            resultTo,
            resultWho,
            requestCode,
            flags,
            pProfilerInfo,
            options,
            userId
        ) as Int
    }

    override fun startNextMatchingActivity(
        callingActivity: IBinder?,
        intent: Intent?,
        options: Bundle?
    ): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startNextMatchingActivity",
            callingActivity,
            intent,
            options
        ) as Boolean
    }

    override fun startDreamActivity(intent: Intent?): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startDreamActivity",
            intent
        ) as Boolean
    }

    override fun startActivityWithConfig(
        caller: PIAT?,
        callingPackage: String?,
        callingFeatureId: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        startFlags: Int,
        newConfig: Configuration?,
        options: Bundle?,
        userId: Int
    ): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startActivityWithConfig",
            applicationThread,
            callingPackage,
            callingFeatureId,
            intent,
            resolvedType,
            resultTo,
            resultWho,
            requestCode,
            startFlags,
            newConfig,
            options,
            userId
        ) as Int
    }

    override fun getVoiceInteractorPackageName(callingVoiceInteractor: IBinder?): String {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getVoiceInteractorPackageName",
            callingVoiceInteractor
        ) as String
    }

    override fun startAssistantActivity(
        callingPackage: String?,
        callingFeatureId: String?,
        callingPid: Int,
        callingUid: Int,
        intent: Intent?,
        resolvedType: String?,
        options: Bundle?,
        userId: Int
    ): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startAssistantActivity",
            callingPackage,
            callingFeatureId,
            callingPid,
            callingUid,
            intent,
            resolvedType,
            options,
            userId
        ) as Int
    }

    override fun startActivityFromRecents(taskId: Int, options: Bundle?): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startActivityFromRecents",
            taskId,
            options
        ) as Int
    }

    override fun startActivityAsCaller(
        caller: PIAT?,
        callingPackage: String?,
        intent: Intent?,
        resolvedType: String?,
        resultTo: IBinder?,
        resultWho: String?,
        requestCode: Int,
        flags: Int,
        pProfilerInfo: PProfilerInfo?,
        options: Bundle?,
        ignoreTargetSecurity: Boolean,
        userId: Int
    ): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startActivityAsCaller",
            applicationThread,
            callingPackage,
            intent,
            resolvedType,
            resultTo,
            resultWho,
            requestCode,
            flags,
            pProfilerInfo,
            options,
            ignoreTargetSecurity,
            userId
        ) as Int
    }

    override fun isActivityStartAllowedOnDisplay(
        displayId: Int,
        intent: Intent?,
        resolvedType: String?,
        userId: Int
    ): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "isActivityStartAllowedOnDisplay",
            displayId,
            intent,
            resolvedType,
            userId
        ) as Boolean
    }

    override fun unhandledBack() {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "unhandledBack"
        )
    }

    override fun getFrontActivityScreenCompatMode(): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getFrontActivityScreenCompatMode"
        ) as Int
    }

    override fun setFrontActivityScreenCompatMode(mode: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setFrontActivityScreenCompatMode",
            mode
        )
    }

    override fun setFocusedTask(taskId: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setFocusedTask",
            taskId
        )
    }

    override fun removeTask(taskId: Int): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "removeTask",
            taskId
        ) as Boolean
    }

    override fun removeAllVisibleRecentTasks() {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "removeAllVisibleRecentTasks"
        )
    }

    override fun moveTaskToFront(
        app: PIAT?,
        callingPackage: String?,
        task: Int,
        flags: Int,
        options: Bundle?
    ) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "moveTaskToFront",
            applicationThread,
            callingPackage,
            task,
            flags,
            options
        )
    }

    override fun isTopActivityImmersive(): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "isTopActivityImmersive"
        ) as Boolean
    }

    override fun reportAssistContextExtras(
        assistToken: IBinder?,
        extras: Bundle?,
        structure: AssistStructure?,
        content: AssistContent?,
        referrer: Uri?
    ) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "reportAssistContextExtras",
            assistToken,
            extras,
            structure,
            content,
            referrer
        )
    }

    override fun setFocusedRootTask(taskId: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setFocusedRootTask",
            taskId
        )
    }

    override fun getTaskBounds(taskId: Int): Rect {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getTaskBounds",
            taskId
        ) as Rect
    }

    override fun cancelRecentsAnimation(restoreHomeRootTaskPosition: Boolean) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "cancelRecentsAnimation",
            restoreHomeRootTaskPosition
        )
    }

    override fun updateLockTaskPackages(userId: Int, packages: Array<out String>?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "updateLockTaskPackages",
            userId,
            packages
        )
    }

    override fun isInLockTaskMode(): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "isInLockTaskMode"
        ) as Boolean
    }

    override fun getLockTaskModeState(): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getLockTaskModeState"
        ) as Int
    }

    override fun getAppTasks(callingPackage: String?): MutableList<IBinder>? {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getAppTasks",
            callingPackage
        ) as? MutableList<IBinder>
    }

    override fun startSystemLockTaskMode(taskId: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "startSystemLockTaskMode",
            taskId
        )
    }

    override fun stopSystemLockTaskMode() {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "stopSystemLockTaskMode"
        )
    }

    override fun getAppTaskThumbnailSize(): Point {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getAppTaskThumbnailSize"
        ) as Point
    }

    override fun releaseSomeActivities(app: PIAT?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "releaseSomeActivities",
            applicationThread
        )
    }

    override fun getTaskDescriptionIcon(filename: String?, userId: Int): Bitmap {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getTaskDescriptionIcon",
            filename,
            userId
        ) as Bitmap
    }

    override fun setTaskResizeable(taskId: Int, resizeableMode: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setTaskResizeable",
            taskId,
            resizeableMode
        )
    }

    override fun resizeTask(taskId: Int, bounds: Rect?, resizeMode: Int): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "resizeTask",
            taskId,
            bounds,
            resizeMode
        ) as Boolean
    }

    override fun moveRootTaskToDisplay(taskId: Int, displayId: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "moveRootTaskToDisplay",
            taskId,
            displayId
        )
    }

    override fun moveTaskToRootTask(taskId: Int, rootTaskId: Int, toTop: Boolean) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "moveTaskToRootTask",
            taskId,
            rootTaskId,
            toTop
        )
    }

    override fun removeRootTasksInWindowingModes(windowingModes: IntArray?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "removeRootTasksInWindowingModes",
            windowingModes
        )
    }

    override fun removeRootTasksWithActivityTypes(activityTypes: IntArray?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "removeRootTasksWithActivityTypes",
            activityTypes
        )
    }

    override fun setLockScreenShown(showingKeyguard: Boolean, showingAod: Boolean) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setLockScreenShown",
            showingKeyguard,
            showingAod
        )
    }

    override fun getAssistContextExtras(requestType: Int): Bundle {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getAssistContextExtras",
            requestType
        ) as Bundle
    }

    override fun isAssistDataAllowedOnCurrentActivity(): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "isAssistDataAllowedOnCurrentActivity"
        ) as Boolean
    }

    override fun keyguardGoingAway(flags: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "keyguardGoingAway",
            flags
        )
    }

    override fun suppressResizeConfigChanges(suppress: Boolean) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "suppressResizeConfigChanges",
            suppress
        )
    }

    override fun setSplitScreenResizing(resizing: Boolean) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setSplitScreenResizing",
            resizing
        )
    }

    override fun supportsLocalVoiceInteraction(): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "supportsLocalVoiceInteraction"
        ) as Boolean
    }

    override fun getDeviceConfigurationInfo(): ConfigurationInfo {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getDeviceConfigurationInfo"
        ) as ConfigurationInfo
    }

    override fun cancelTaskWindowTransition(taskId: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "cancelTaskWindowTransition",
            taskId
        )
    }

    override fun getLastResumedActivityUserId(): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getLastResumedActivityUserId"
        ) as Int
    }

    override fun updateConfiguration(values: Configuration?): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "updateConfiguration",
            values
        ) as Boolean
    }

    override fun updateLockTaskFeatures(userId: Int, flags: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "updateLockTaskFeatures",
            userId,
            flags
        )
    }

    override fun alwaysShowUnsupportedCompileSdkWarning(activity: ComponentName?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "alwaysShowUnsupportedCompileSdkWarning",
            activity
        )
    }

    override fun setVrThread(tid: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setVrThread",
            tid
        )
    }

    override fun setPersistentVrThread(tid: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setPersistentVrThread",
            tid
        )
    }

    override fun stopAppSwitches() {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "stopAppSwitches"
        )
    }

    override fun resumeAppSwitches() {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "resumeAppSwitches"
        )
    }

    override fun getPackageScreenCompatMode(packageName: String?): Int {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getPackageScreenCompatMode",
            packageName
        ) as Int
    }

    override fun setPackageScreenCompatMode(packageName: String?, mode: Int) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setPackageScreenCompatMode",
            packageName,
            mode
        )
    }

    override fun getPackageAskScreenCompat(packageName: String?): Boolean {
        return HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "getPackageAskScreenCompat",
            packageName
        ) as Boolean
    }

    override fun setPackageAskScreenCompat(packageName: String?, ask: Boolean) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setPackageAskScreenCompat",
            packageName,
            ask
        )
    }

    override fun clearLaunchParamsForPackages(packageNames: MutableList<String>?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "clearLaunchParamsForPackages",
            packageNames
        )
    }

    override fun onPictureInPictureStateChanged(pipState: PictureInPictureUiState?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "onPictureInPictureStateChanged",
            pipState
        )
    }

    override fun detachNavigationBarFromApp(transition: IBinder?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "detachNavigationBarFromApp",
            transition
        )
    }

    override fun setRunningRemoteTransitionDelegate(caller: PIAT?) {
        HiddenApi.invoke(
            originATMS.javaClass,
            originATMS,
            "setRunningRemoteTransitionDelegate",
            applicationThread
        )
    }
}