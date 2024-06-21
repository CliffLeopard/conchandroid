// IActivityTaskManager.aidl
package wrapper.android.app;
// Declare any non-default types here with import statements

/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.app.ApplicationErrorReport;
import wrapper.replace.PIAT;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.PictureInPictureUiState;
import wrapper.replace.PProfilerInfo;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.WorkSource;
import android.window.BackNavigationInfo;
import java.util.List;

interface IActivityTaskManager {
    int startActivity(in PIAT caller, in String callingPackage,
            in String callingFeatureId, in Intent intent, in String resolvedType,
            in IBinder resultTo, in String resultWho, int requestCode,
            int flags, in PProfilerInfo pProfilerInfo, in Bundle options);
    int startActivities(in PIAT caller, in String callingPackage,
            in String callingFeatureId, in Intent[] intents, in String[] resolvedTypes,
            in IBinder resultTo, in Bundle options, int userId);
    int startActivityAsUser(in PIAT caller, in String callingPackage,
            in String callingFeatureId, in Intent intent, in String resolvedType,
            in IBinder resultTo, in String resultWho, int requestCode, int flags,
            in PProfilerInfo pProfilerInfo, in Bundle options, int userId);
//    boolean startNextMatchingActivity(in IBinder callingActivity,
//            in Intent intent, in Bundle options);
//    boolean startDreamActivity(in Intent intent);
//    int startActivityWithConfig(in PIAT caller, in String callingPackage,
//            in String callingFeatureId, in Intent intent, in String resolvedType,
//            in IBinder resultTo, in String resultWho, int requestCode, int startFlags,
//            in Configuration newConfig, in Bundle options, int userId);
//    String getVoiceInteractorPackageName(in IBinder callingVoiceInteractor);
//    int startAssistantActivity(in String callingPackage, in String callingFeatureId, int callingPid,
//            int callingUid, in Intent intent, in String resolvedType, in Bundle options, int userId);
//    int startActivityFromRecents(int taskId, in Bundle options);
//    int startActivityAsCaller(in PIAT caller, in String callingPackage,
//            in Intent intent, in String resolvedType, in IBinder resultTo, in String resultWho,
//            int requestCode, int flags, in PProfilerInfo pProfilerInfo, in Bundle options,
//            boolean ignoreTargetSecurity, int userId);
//    boolean isActivityStartAllowedOnDisplay(int displayId, in Intent intent, in String resolvedType,
//            int userId);
//    void unhandledBack();
//    int getFrontActivityScreenCompatMode();
//    void setFrontActivityScreenCompatMode(int mode);
//    void setFocusedTask(int taskId);
//    boolean removeTask(int taskId);
//    void removeAllVisibleRecentTasks();
//    void moveTaskToFront(in PIAT app, in String callingPackage, int task,
//            int flags, in Bundle options);
//    boolean isTopActivityImmersive();
//    void reportAssistContextExtras(in IBinder assistToken, in Bundle extras,
//            in AssistStructure structure, in AssistContent content, in Uri referrer);
//    void setFocusedRootTask(int taskId);
//    Rect getTaskBounds(int taskId);
//    void cancelRecentsAnimation(boolean restoreHomeRootTaskPosition);
//    void updateLockTaskPackages(int userId, in String[] packages);
//    boolean isInLockTaskMode();
//    int getLockTaskModeState();
//    List<IBinder> getAppTasks(in String callingPackage);
//    void startSystemLockTaskMode(int taskId);
//    void stopSystemLockTaskMode();
//    Point getAppTaskThumbnailSize();
//
//    oneway void releaseSomeActivities(in PIAT app);
//    Bitmap getTaskDescriptionIcon(in String filename, int userId);
//    void setTaskResizeable(int taskId, int resizeableMode);
//
//    boolean resizeTask(int taskId, in Rect bounds, int resizeMode);
//    void moveRootTaskToDisplay(int taskId, int displayId);
//    void moveTaskToRootTask(int taskId, int rootTaskId, boolean toTop);
//    void removeRootTasksInWindowingModes(in int[] windowingModes);
//    void removeRootTasksWithActivityTypes(in int[] activityTypes);
//    void setLockScreenShown(boolean showingKeyguard, boolean showingAod);
//    Bundle getAssistContextExtras(int requestType);
//    boolean isAssistDataAllowedOnCurrentActivity();
//    void keyguardGoingAway(int flags);
//    void suppressResizeConfigChanges(boolean suppress);
//    void setSplitScreenResizing(boolean resizing);
//    boolean supportsLocalVoiceInteraction();
//    ConfigurationInfo getDeviceConfigurationInfo();
//    void cancelTaskWindowTransition(int taskId);
//    int getLastResumedActivityUserId();
//
//    boolean updateConfiguration(in Configuration values);
//    void updateLockTaskFeatures(int userId, int flags);
//    void alwaysShowUnsupportedCompileSdkWarning(in ComponentName activity);
//
//    void setVrThread(int tid);
//    void setPersistentVrThread(int tid);
//    void stopAppSwitches();
//    void resumeAppSwitches();
//
//    int getPackageScreenCompatMode(in String packageName);
//    void setPackageScreenCompatMode(in String packageName, int mode);
//    boolean getPackageAskScreenCompat(in String packageName);
//    void setPackageAskScreenCompat(in String packageName, boolean ask);
//    void clearLaunchParamsForPackages(in List<String> packageNames);
//    void onPictureInPictureStateChanged(in PictureInPictureUiState pipState);
//    void detachNavigationBarFromApp(in IBinder transition);
//    void setRunningRemoteTransitionDelegate(in PIAT caller);



//import android.app.ActivityManager;
//import android.app.ActivityTaskManager;
//import android.app.ContentProviderHolder;
//import android.app.GrantedUriPermission;
//import android.app.IActivityClientController;
//import android.app.IActivityController;
//import android.app.IAppTask;
//import android.app.IAssistDataReceiver;
//import android.app.IInstrumentationWatcher;
//import android.app.IProcessObserver;
//import android.app.IServiceConnection;
//import android.app.IStopUserCallback;
//import android.app.ITaskStackListener;
//import android.app.IUiAutomationConnection;
//import android.app.IUidObserver;
//import android.app.IUserSwitchObserver;
//import android.app.PProfilerInfo;
//import android.app.WaitResult;
//import android.content.IIntentReceiver;
//import android.content.IIntentSender;
//import android.content.pm.IPackageDataObserver;
//import android.content.pm.ParceledListSlice;
//import android.graphics.GraphicBuffer;
//import android.os.Debug;
//import android.os.IProgressListener;
//import android.os.StrictMode;
//import android.service.voice.IVoiceInteractionSession;
//import android.view.IRecentsAnimationRunner;
//import android.view.IRemoteAnimationRunner;
//import android.view.IWindowFocusObserver;
//import android.view.RemoteAnimationDefinition;
//import android.view.RemoteAnimationAdapter;
//import android.window.BackAnimationAdaptor;
//import android.window.IWindowOrganizerController;
//import android.window.SplashScreenView;
//import com.android.internal.app.IVoiceInteractor;
//import com.android.internal.os.IResultReceiver;

//    int startActivityIntentSender(in PIAT caller,
//            in IIntentSender target, in IBinder whitelistToken, in Intent fillInIntent,
//            in String resolvedType, in IBinder resultTo, in String resultWho, int requestCode,
//            int flagsMask, int flagsValues, in Bundle options);
//    WaitResult startActivityAndWait(in PIAT caller, in String callingPackage,
//            in String callingFeatureId, in Intent intent, in String resolvedType,
//            in IBinder resultTo, in String resultWho, int requestCode, int flags,
//            in PProfilerInfo PProfilerInfo, in Bundle options, int userId);
//    int startVoiceActivity(in String callingPackage, in String callingFeatureId, int callingPid,
//            int callingUid, in Intent intent, in String resolvedType,
//            in IVoiceInteractionSession session, in IVoiceInteractor interactor, int flags,
//            in PProfilerInfo PProfilerInfo, in Bundle options, int userId);
//    @JavaPassthrough(annotation="@android.annotation.RequiresPermission(android.Manifest.permission.MANAGE_GAME_ACTIVITY)")
//    int startActivityFromGameSession(PIAT caller, in String callingPackage,
//            in String callingFeatureId, int callingPid, int callingUid, in Intent intent,
//            int taskId, int userId);
//    void startRecentsActivity(in Intent intent, in long eventTime,
//            in IRecentsAnimationRunner recentsAnimationRunner);
/** Returns an interface to control the activity related operations. */
//    IActivityClientController getActivityClientController();
//    List<ActivityManager.RunningTaskInfo> getTasks(int maxNum, boolean filterOnlyVisibleRecents,
//            boolean keepIntentExtra, int displayId);
//    ParceledListSlice<ActivityManager.RecentTaskInfo> getRecentTasks(int maxNum, int flags,
//            int userId);

//    ActivityManager.TaskDescription getTaskDescription(int taskId);
//    ActivityTaskManager.RootTaskInfo getFocusedRootTaskInfo();
//    void finishVoiceTask(in IVoiceInteractionSession session);
//    int addAppTask(in IBinder activityToken, in Intent intent,
//            in ActivityManager.TaskDescription description, in Bitmap thumbnail);
//    void registerTaskStackListener(in ITaskStackListener listener);
//    void unregisterTaskStackListener(in ITaskStackListener listener);
//    List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfos();
//    ActivityTaskManager.RootTaskInfo getRootTaskInfo(int windowingMode, int activityType);
//    List<ActivityTaskManager.RootTaskInfo> getAllRootTaskInfosOnDisplay(int displayId);
//    ActivityTaskManager.RootTaskInfo getRootTaskInfoOnDisplay(int windowingMode, int activityType, int displayId);
//    boolean requestAssistContextExtras(int requestType, in IAssistDataReceiver receiver,
//            in Bundle receiverExtras, in IBinder activityToken,
//            boolean focused, boolean newSessionId);
//    boolean requestAutofillData(in IAssistDataReceiver receiver, in Bundle receiverExtras,
//            in IBinder activityToken, int flags);

//    boolean requestAssistDataForTask(in IAssistDataReceiver receiver, int taskId,
//            in String callingPackageName);

//    IWindowOrganizerController getWindowOrganizerController();
//    android.window.TaskSnapshot getTaskSnapshot(
//            int taskId, boolean isLowResolution, boolean takeSnapshotIfNeeded);

//    android.window.TaskSnapshot takeTaskSnapshot(int taskId);
//    void registerRemoteAnimationForNextActivityStart(in String packageName,
//            in RemoteAnimationAdapter adapter, in IBinder launchCookie);
//    void registerRemoteAnimationsForDisplay(int displayId, in RemoteAnimationDefinition definition);

//    void setActivityController(in IActivityController watcher, boolean imAMonkey);
//    void setVoiceKeepAwake(in IVoiceInteractionSession session, boolean keepAwake);
//    void onSplashScreenViewCopyFinished(int taskId,
//            in SplashScreenView.SplashScreenViewParcelable material);

//    android.window.BackNavigationInfo startBackNavigation(in boolean requestAnimation,
//            in IWindowFocusObserver focusObserver, in BackAnimationAdaptor adaptor);
}
