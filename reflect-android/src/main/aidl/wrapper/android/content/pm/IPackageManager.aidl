// IPackageManager.aidl
package wrapper.android.content.pm;

/*
**
** Copyright 2007, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.InstantAppInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.InstallSourceInfo;
import android.content.pm.IOnChecksumsReadyListener;
//import android.content.pm.IPackageInstaller;
//import android.content.pm.IPackageDeleteObserver;
//import android.content.pm.IPackageDeleteObserver2;
//import android.content.pm.IPackageDataObserver;
//import android.content.pm.IPackageMoveObserver;
//import android.content.pm.IPackageStatsObserver;
import android.content.pm.IntentFilterVerificationInfo;
import android.content.pm.InstrumentationInfo;
//import android.content.pm.KeySet;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
import android.content.pm.PackageManager.ComponentEnabledSetting;
//import android.content.pm.ParceledListSlice;
import wrapper.replace.PParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
//import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserInfo;
//import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.VersionedPackage;
//import android.content.pm.dex.IArtManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.content.IntentSender;

interface IPackageManager {
    void checkPackageStartable(String packageName, int userId);

    PackageInfo getPackageInfo(String packageName, long flags, int userId);

    PackageInfo getPackageInfoVersioned(in VersionedPackage versionedPackage,
                                        long flags, int userId);

    int getPackageUid(String packageName, long flags, int userId);

    int[] getPackageGids(String packageName, long flags, int userId);

    ApplicationInfo getApplicationInfo(String packageName, long flags, int userId);

    int getTargetSdkVersion(String packageName);

    ActivityInfo getActivityInfo(in ComponentName className, long flags, int userId);

    boolean activitySupportsIntent(in ComponentName className, in Intent intent,
                                   String resolvedType);

    ActivityInfo getReceiverInfo(in ComponentName className, long flags, int userId);

    ServiceInfo getServiceInfo(in ComponentName className, long flags, int userId);

    ProviderInfo getProviderInfo(in ComponentName className, long flags, int userId);

    boolean isProtectedBroadcast(String actionName);

    List<String> getAllPackages();

    String[] getNamesForUids(in int[]uids);

    int getPrivateFlagsForUid(int uid);

    ResolveInfo resolveIntent(in Intent intent, String resolvedType, long flags, int userId);

    ResolveInfo findPersistentPreferredActivity(in Intent intent, int userId);

    boolean canForwardTo(in Intent intent, String resolvedType, int sourceUserId, int targetUserId);

    PParceledListSlice queryIntentActivities(in Intent intent,
                                            String resolvedType, long flags, int userId);

    PParceledListSlice queryIntentActivityOptions(
            in ComponentName caller, in Intent[] specifics,
            in String[] specificTypes, in Intent intent,
            String resolvedType, long flags, int userId);

    PParceledListSlice queryIntentReceivers(in Intent intent,
                                           String resolvedType, long flags, int userId);

    ResolveInfo resolveService(in Intent intent,
                               String resolvedType, long flags, int userId);

    PParceledListSlice queryIntentServices(in Intent intent,
                                          String resolvedType, long flags, int userId);

    PParceledListSlice queryIntentContentProviders(in Intent intent,
                                                  String resolvedType, long flags, int userId);

    PParceledListSlice getInstalledPackages(long flags, in int userId);

    PParceledListSlice getPackagesHoldingPermissions(in String[] permissions,
                                                    long flags, int userId);

    PParceledListSlice getInstalledApplications(long flags, int userId);

    PParceledListSlice getPersistentApplications(int flags);

    ProviderInfo resolveContentProvider(String name, long flags, int userId);

    PParceledListSlice queryContentProviders(
            String processName, int uid, long flags, String metaDataKey);

    void finishPackageInstall(int token, boolean didLaunch);

    void setApplicationCategoryHint(String packageName, int categoryHint, String callerPackageName);

    /**
     * @deprecated rawr, don't call AIDL methods directly!
     */
//    void deletePackageAsUser(in String packageName, int versionCode,
//                             IPackageDeleteObserver observer, int userId, int flags);
//
//    void deletePackageVersioned(in VersionedPackage versionedPackage,
//                                IPackageDeleteObserver2 observer, int userId, int flags);
//
//    void deleteExistingPackageAsUser(in VersionedPackage versionedPackage,
//                                     IPackageDeleteObserver2 observer, int userId);

    InstallSourceInfo getInstallSourceInfo(in String packageName);

    void resetApplicationPreferences(int userId);

    void addPreferredActivity(in IntentFilter filter, int match,
                              in ComponentName[] set, in ComponentName activity, int userId, boolean removeExisting);

    void addPersistentPreferredActivity(in IntentFilter filter, in ComponentName activity, int userId);

    void clearPackagePersistentPreferredActivities(String packageName, int userId);

    void addCrossProfileIntentFilter(in IntentFilter intentFilter, String ownerPackage,
                                     int sourceUserId, int targetUserId, int flags);

    void clearCrossProfileIntentFilters(int sourceUserId, String ownerPackage);

    String[] setDistractingPackageRestrictionsAsUser(in String[] packageNames, int restrictionFlags,
                                                     int userId);

//    String[] setPackagesSuspendedAsUser(in String[] packageNames, boolean suspended,
//                                        in PersistableBundle appExtras, in PersistableBundle launcherExtras,
//                                        in SuspendDialogInfo dialogInfo, String callingPackage, int userId);

    String[] getUnsuspendablePackagesForUser(in String[] packageNames, int userId);

    boolean isPackageSuspendedForUser(String packageName, int userId);

    Bundle getSuspendedPackageAppExtras(String packageName, int userId);

    byte[] getPreferredActivityBackup(int userId);

    void restorePreferredActivities(in byte[]backup, int userId);

    byte[] getDefaultAppsBackup(int userId);

    void restoreDefaultApps(in byte[]backup, int userId);

    byte[] getDomainVerificationBackup(int userId);

    void restoreDomainVerification(in byte[]backup, int userId);

    void setHomeActivity(in ComponentName className, int userId);

    void overrideLabelAndIcon(in ComponentName componentName, String nonLocalizedLabel,
                              int icon, int userId);

    void restoreLabelAndIcon(in ComponentName componentName, int userId);

    void setComponentEnabledSettings(in List<ComponentEnabledSetting>settings, int userId);

    void logAppProcessStartIfNeeded(String packageName, String processName, int uid, String seinfo, String apkFile, int pid);

    void flushPackageRestrictionsAsUser(in int userId);

//    void freeStorageAndNotify(in String volumeUuid, in long freeStorageSize,
//                              int storageFlags, IPackageDataObserver observer);

    void freeStorage(in String volumeUuid, in long freeStorageSize,
                     int storageFlags, in IntentSender pi);

//    void deleteApplicationCacheFilesAsUser(in String packageName, int userId, IPackageDataObserver observer);

//    void clearApplicationUserData(in String packageName, IPackageDataObserver observer, int userId);

    void clearApplicationProfileData(in String packageName);

//    void getPackageSizeInfo(in String packageName, int userHandle, IPackageStatsObserver observer);

    PParceledListSlice getSystemAvailableFeatures();

    boolean hasSystemFeature(String name, int version);

    void enterSafeMode();

    oneway

    void notifyPackageUse(String packageName, int reason);

    oneway

    void notifyDexLoad(String loadingPackageName,
                       in Map<String, String>classLoaderContextMap, String loaderIsa);

    oneway

    void registerDexModule(in String packageName, in String dexModulePath,
                           in boolean isSharedModule, IDexModuleRegisterCallback callback);

    boolean performDexOptMode(String packageName, boolean checkProfiles,
                              String targetCompilerFilter, boolean force, boolean bootComplete, String splitName);

    boolean performDexOptSecondary(String packageName,
                                   String targetCompilerFilter, boolean force);

    void dumpProfiles(String packageName, boolean dumpClassesAndMethods);

    void forceDexOpt(String packageName);

    void reconcileSecondaryDexFiles(String packageName);

    int getMoveStatus(int moveId);

//    void registerMoveCallback(in IPackageMoveObserver callback);

//    void unregisterMoveCallback(in IPackageMoveObserver callback);

    int movePackage(in String packageName, in String volumeUuid);

    int movePrimaryStorage(in String volumeUuid);

    boolean setInstallLocation(int loc);

    int installExistingPackageAsUser(String packageName, int userId, int installFlags,
                                     int installReason, in List<String>whiteListedPermissions);

    void verifyPendingInstall(int id, int verificationCode);

    void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay);

    /**
     * @deprecated
     */
    void verifyIntentFilter(int id, int verificationCode, in List<String>failedDomains);

    /**
     * @deprecated
     */
    int getIntentVerificationStatus(String packageName, int userId);

    /**
     * @deprecated
     */
    boolean updateIntentVerificationStatus(String packageName, int status, int userId);

    /**
     * @deprecated
     */
    PParceledListSlice getIntentFilterVerifications(String packageName);

    PParceledListSlice getAllIntentFilters(String packageName);

//    VerifierDeviceIdentity getVerifierDeviceIdentity();

    boolean isFirstBoot();

    boolean isOnlyCoreApps();

    boolean isDeviceUpgrading();

    boolean getApplicationHiddenSettingAsUser(String packageName, int userId);

    void setSystemAppHiddenUntilInstalled(String packageName, boolean hidden);

    boolean setSystemAppInstallState(String packageName, boolean installed, int userId);

    boolean setBlockUninstallForUser(String packageName, boolean blockUninstall, int userId);

//    KeySet getKeySetByAlias(String packageName, String alias);

//    KeySet getSigningKeySet(String packageName);

//    boolean isPackageSignedByKeySet(String packageName, in KeySet ks);

//    boolean isPackageSignedByKeySetExactly(String packageName, in KeySet ks);

    String getSdkSandboxPackageName();

    PParceledListSlice getInstantApps(int userId);

    byte[] getInstantAppCookie(String packageName, int userId);

    boolean setInstantAppCookie(String packageName, in byte[]cookie, int userId);

    Bitmap getInstantAppIcon(String packageName, int userId);

    boolean isInstantApp(String packageName, int userId);

    boolean setRequiredForSystemUser(String packageName, boolean systemUserApp);

    void setUpdateAvailable(String packageName, boolean updateAvaialble);

    ChangedPackages getChangedPackages(int sequenceNumber, int userId);

    boolean isPackageDeviceAdminOnAnyUser(String packageName);

    int getInstallReason(String packageName, int userId);

    PParceledListSlice getSharedLibraries(in String packageName, long flags, int userId);

    PParceledListSlice getDeclaredSharedLibraries(in String packageName, long flags, int userId);

    boolean canRequestPackageInstalls(String packageName, int userId);

    void deletePreloadsFileCache();

    ComponentName getInstantAppResolverComponent();

    ComponentName getInstantAppResolverSettingsComponent();

    ComponentName getInstantAppInstallerComponent();

    String getInstantAppAndroidId(String packageName, int userId);

//    IArtManager getArtManager();

    void setHarmfulAppWarning(String packageName, CharSequence warning, int userId);

    CharSequence getHarmfulAppWarning(String packageName, int userId);

    boolean hasSigningCertificate(String packageName, in byte[]signingCertificate, int flags);

    boolean hasUidSigningCertificate(int uid, in byte[]signingCertificate, int flags);

    String getDefaultTextClassifierPackageName();

    String getSystemTextClassifierPackageName();

    String getAttentionServicePackageName();

    String getRotationResolverPackageName();

    String getWellbeingPackageName();

    String getAppPredictionServicePackageName();

    String getSystemCaptionsServicePackageName();

    String getSetupWizardPackageName();

    String getIncidentReportApproverPackageName();

    String getContentCaptureServicePackageName();

    boolean isPackageStateProtected(String packageName, int userId);

    void sendDeviceCustomizationReadyBroadcast();

    List<ModuleInfo> getInstalledModules(int flags);

    ModuleInfo getModuleInfo(String packageName, int flags);

    int getRuntimePermissionsVersion(int userId);

    void setRuntimePermissionsVersion(int version, int userId);

    void notifyPackagesReplacedReceived(in String[] packages);

    void requestPackageChecksums(in String packageName, boolean includeSplits, int optional, int required, in List trustedInstallers, in IOnChecksumsReadyListener onChecksumsReadyListener, int userId);

    IntentSender getLaunchIntentSenderForPackage(String packageName, String callingPackage,
                                                 String featureId, int userId);

    int checkUidPermission(String permName, int uid);

    void setMimeGroup(String packageName, String group, in List<String>mimeTypes);

    String getSplashScreenTheme(String packageName, int userId);

    void setSplashScreenTheme(String packageName, String themeName, int userId);

    List<String> getMimeGroup(String packageName, String group);

    boolean isAutoRevokeWhitelisted(String packageName);

    void makeProviderVisible(int recipientAppId, String visibleAuthority);

//    @JavaPassthrough(annotation = "@android.annotation.RequiresPermission(android.Manifest"
//            + ".permission.MAKE_UID_VISIBLE)")
//    void makeUidVisible(int recipientAppId, int visibleUid);

    IBinder getHoldLockToken();

    void holdLock(in IBinder token, in int durationMs);

//    PackageManager.Property getProperty(String propertyName, String packageName, String className);

    PParceledListSlice queryProperty(String propertyName, int componentType);

    void setKeepUninstalledPackages(in List<String>packageList);

    boolean canPackageQuery(String sourcePackageName, String targetPackageName, int userId);


//    @UnsupportedAppUsage(trackingBug = 171933273)
//    boolean isPackageAvailable(String packageName, int userId);
//
//    @UnsupportedAppUsage
//    String[] currentToCanonicalPackageNames(in String[] names);
//
//    @UnsupportedAppUsage
//    String[] canonicalToCurrentPackageNames(in String[] names);
//
//    @UnsupportedAppUsage
//    int checkSignatures(String pkg1, String pkg2);
//
//    @UnsupportedAppUsage
//    int checkUidSignatures(int uid1, int uid2);
//
//    @UnsupportedAppUsage
//    String[] getPackagesForUid(int uid);
//
//    @UnsupportedAppUsage
//    String getNameForUid(int uid);
//
//    @UnsupportedAppUsage
//    int getUidForSharedUser(String sharedUserName);
//
//    @UnsupportedAppUsage
//    int getFlagsForUid(int uid);
//
//    @UnsupportedAppUsage
//    boolean isUidPrivileged(int uid);
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    void querySyncProviders(inout List<String>outNames,
//                            inout List<ProviderInfo>outInfo);
//
//    @UnsupportedAppUsage
//    InstrumentationInfo getInstrumentationInfo(
//            in ComponentName className, int flags);
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    ParceledListSlice queryInstrumentation(
//            String targetPackage, int flags);
//
//
//    @UnsupportedAppUsage
//    void setInstallerPackageName(in String targetPackage, in String installerPackageName);
//
//
//    @UnsupportedAppUsage
//    String getInstallerPackageName(in String packageName);
//
//    @UnsupportedAppUsage
//    ResolveInfo getLastChosenActivity(in Intent intent,
//                                      String resolvedType, int flags);
//
//    @UnsupportedAppUsage
//    void setLastChosenActivity(in Intent intent, String resolvedType, int flags,
//                               in IntentFilter filter, int match, in ComponentName activity);
//
//    @UnsupportedAppUsage
//    void replacePreferredActivity(in IntentFilter filter, int match,
//                                  in ComponentName[] set, in ComponentName activity, int userId);
//
//    @UnsupportedAppUsage
//    void clearPackagePreferredActivities(String packageName);
//
//    @UnsupportedAppUsage
//    int getPreferredActivities(out List<IntentFilter>outFilters,
//                               out List<ComponentName>outActivities, String packageName);
//
//
//    @UnsupportedAppUsage
//    ComponentName getHomeActivities(out List<ResolveInfo>outHomeCandidates);
//
//    @UnsupportedAppUsage
//    void setComponentEnabledSetting(in ComponentName componentName,
//                                    in int newState, in int flags, int userId);
//
//
//    @UnsupportedAppUsage
//    int getComponentEnabledSetting(in ComponentName componentName, int userId);
//
//    @UnsupportedAppUsage
//    void setApplicationEnabledSetting(in String packageName, in int newState, int flags,
//                                      int userId, String callingPackage);
//
//    @UnsupportedAppUsage
//    int getApplicationEnabledSetting(in String packageName, int userId);
//
//    @UnsupportedAppUsage
//    void setPackageStoppedState(String packageName, boolean stopped, int userId);
//
//
//    @UnsupportedAppUsage
//    void deleteApplicationCacheFiles(in String packageName, IPackageDataObserver observer);
//
//
//    @UnsupportedAppUsage
//    String[] getSystemSharedLibraryNames();
//
//    @UnsupportedAppUsage
//    boolean isSafeMode();
//
//    @UnsupportedAppUsage
//    boolean hasSystemUidErrors();
//
//    @UnsupportedAppUsage
//    int getInstallLocation();
//
//
//    @UnsupportedAppUsage
//    boolean isStorageLow();
//
//    @UnsupportedAppUsage
//    boolean setApplicationHiddenSettingAsUser(String packageName, boolean hidden, int userId);
//
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    IPackageInstaller getPackageInstaller();
//
//    @UnsupportedAppUsage
//    boolean getBlockUninstallForUser(String packageName, int userId);
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    String getPermissionControllerPackageName();
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    String getServicesSystemSharedLibraryPackageName();
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    String getSharedSystemSharedLibraryPackageName();
//
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    String[] getAppOpPermissionPackages(String permissionName);
//
//    @UnsupportedAppUsage
//    PermissionGroupInfo getPermissionGroupInfo(String name, int flags);
//
//    @UnsupportedAppUsage
//    boolean addPermission(in PermissionInfo info);
//
//    @UnsupportedAppUsage
//    boolean addPermissionAsync(in PermissionInfo info);
//
//    @UnsupportedAppUsage
//    void removePermission(String name);
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    int checkPermission(String permName, String pkgName, int userId);
//
//    @UnsupportedAppUsage(maxTargetSdk = 30, trackingBug = 170729553)
//    void grantRuntimePermission(String packageName, String permissionName, int userId);
}
