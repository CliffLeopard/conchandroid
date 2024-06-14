package com.cliff.conch.box.service.server

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
import com.cliff.conch.box.scene.ActivityThread
import com.cliff.wrapper.service.IActivityTaskManager
import com.cliff.wrapper.service.PIAT
import com.cliff.wrapper.service.PProfilerInfo
import org.lsposed.hiddenapibypass.HiddenApiBypass

open class OriginATMS : IActivityTaskManager.Stub() {
    private val originATMS by lazy {
        OriginServerManager.getOriginATMS()
    }
    private val activityThread by lazy {
        ActivityThread.currentActivityThread.call()
    }
    private val applicationThread by lazy {
        ActivityThread.mAppThread[activityThread]
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
        return HiddenApiBypass.invoke(
            originATMS.javaClass, originATMS, "startActivity",
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
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
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
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun startNextMatchingActivity(
        callingActivity: IBinder?,
        intent: Intent?,
        options: Bundle?
    ): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun startDreamActivity(intent: Intent?): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
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
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun getVoiceInteractorPackageName(callingVoiceInteractor: IBinder?): String {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
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
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun startActivityFromRecents(taskId: Int, options: Bundle?): Int {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
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
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun isActivityStartAllowedOnDisplay(
        displayId: Int,
        intent: Intent?,
        resolvedType: String?,
        userId: Int
    ): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun unhandledBack() {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getFrontActivityScreenCompatMode(): Int {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun setFrontActivityScreenCompatMode(mode: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun setFocusedTask(taskId: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun removeTask(taskId: Int): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun removeAllVisibleRecentTasks() {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun moveTaskToFront(
        app: PIAT?,
        callingPackage: String?,
        task: Int,
        flags: Int,
        options: Bundle?
    ) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun isTopActivityImmersive(): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun reportAssistContextExtras(
        assistToken: IBinder?,
        extras: Bundle?,
        structure: AssistStructure?,
        content: AssistContent?,
        referrer: Uri?
    ) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun setFocusedRootTask(taskId: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getTaskBounds(taskId: Int): Rect {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Rect
    }

    override fun cancelRecentsAnimation(restoreHomeRootTaskPosition: Boolean) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun updateLockTaskPackages(userId: Int, packages: Array<out String>?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun isInLockTaskMode(): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun getLockTaskModeState(): Int {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun getAppTasks(callingPackage: String?): MutableList<IBinder>? {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as? MutableList<IBinder>
    }

    override fun startSystemLockTaskMode(taskId: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun stopSystemLockTaskMode() {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getAppTaskThumbnailSize(): Point {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Point
    }

    override fun releaseSomeActivities(app: PIAT?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getTaskDescriptionIcon(filename: String?, userId: Int): Bitmap {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Bitmap
    }

    override fun setTaskResizeable(taskId: Int, resizeableMode: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun resizeTask(taskId: Int, bounds: Rect?, resizeMode: Int): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun moveRootTaskToDisplay(taskId: Int, displayId: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun moveTaskToRootTask(taskId: Int, rootTaskId: Int, toTop: Boolean) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun removeRootTasksInWindowingModes(windowingModes: IntArray?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun removeRootTasksWithActivityTypes(activityTypes: IntArray?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun setLockScreenShown(showingKeyguard: Boolean, showingAod: Boolean) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getAssistContextExtras(requestType: Int): Bundle {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Bundle
    }

    override fun isAssistDataAllowedOnCurrentActivity(): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun keyguardGoingAway(flags: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun suppressResizeConfigChanges(suppress: Boolean) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun setSplitScreenResizing(resizing: Boolean) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun supportsLocalVoiceInteraction(): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun getDeviceConfigurationInfo(): ConfigurationInfo {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as ConfigurationInfo
    }

    override fun cancelTaskWindowTransition(taskId: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getLastResumedActivityUserId(): Int {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun updateConfiguration(values: Configuration?): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun updateLockTaskFeatures(userId: Int, flags: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun alwaysShowUnsupportedCompileSdkWarning(activity: ComponentName?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun setVrThread(tid: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun setPersistentVrThread(tid: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun stopAppSwitches() {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun resumeAppSwitches() {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getPackageScreenCompatMode(packageName: String?): Int {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Int
    }

    override fun setPackageScreenCompatMode(packageName: String?, mode: Int) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun getPackageAskScreenCompat(packageName: String?): Boolean {
        return HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        ) as Boolean
    }

    override fun setPackageAskScreenCompat(packageName: String?, ask: Boolean) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun clearLaunchParamsForPackages(packageNames: MutableList<String>?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun onPictureInPictureStateChanged(pipState: PictureInPictureUiState?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun detachNavigationBarFromApp(transition: IBinder?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }

    override fun setRunningRemoteTransitionDelegate(caller: PIAT?) {
        HiddenApiBypass.invoke(
            originATMS.javaClass,
            originATMS,
            ""
        )
    }
}