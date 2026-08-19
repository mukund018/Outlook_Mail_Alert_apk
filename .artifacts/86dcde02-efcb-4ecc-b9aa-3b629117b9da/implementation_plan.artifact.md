# Implementation Plan - Notification Timer & UI Upgrade

This plan adds a configurable time window for notifications and modernizes the app's UI using Material 3 components.

## User Review Required

> [!IMPORTANT]
> The "Only ring between 12 AM and 6 AM" feature will be configurable in the UI. By default, it will be set to this range.

## Proposed Changes

### Data Layer
#### [NEW] [TimePreferences.kt](file:///C:/Users/user/AndroidStudioProjects/MyApplication2/app/src/main/java/com/example/outlookringalert/TimePreferences.kt)
A helper class using `SharedPreferences` to store and retrieve the active ringing hours.

### Notification Logic
#### [MODIFY] [OutlookNotificationListener.kt](file:///C:/Users/user/AndroidStudioProjects/MyApplication2/app/src/main/java/com/example/outlookringalert/OutlookNotificationListener.kt)
- Update `onNotificationPosted` to check `TimePreferences` before calling `triggerCallAlert()`.
- Add logic to handle time ranges that cross midnight (e.g., 10 PM to 2 AM).

### UI Layer
#### [MODIFY] [activity_main.xml](file:///C:/Users/user/AndroidStudioProjects/MyApplication2/app/src/main/res/layout/activity_main.xml)
- Completely redesign the layout using Material 3 `MaterialCardView`, `Button`, and `TextView`.
- Add a section to display and change the "Active Hours".

#### [MODIFY] [MainActivity.kt](file:///C:/Users/user/AndroidStudioProjects/MyApplication2/app/src/main/java/com/example/outlookringalert/MainActivity.kt)
- Link the UI components to `TimePreferences`.
- Implement `TimePickerDialog` for selecting start/end times.
- Improve the permission flow status display.

---

## Edge Case Scenarios & UX Suggestions

1.  **Midnight Crossover**: The timer must correctly handle ranges like 11 PM to 5 AM. I will implement a check that works regardless of whether the end time is "before" the start time numerically.
2.  **Notification Listener Service Status**: If the user hasn't granted "Notification Access", the app does nothing. I'll add a prominent status indicator in the UI.
3.  **Battery Optimization**: On many Android devices, the `NotificationListenerService` might be killed to save battery. I'll add a button to open battery optimization settings.
4.  **System "Do Not Disturb"**: Should the app ring even if the phone is on DND? This is a "Critical Alert" style behavior. I'll stick to the user's requested window but notify them that system volume still applies.
5.  **Stop Action**: Ensure the "Stop Ringing" notification action is easily accessible and works even if the app is in the background.

## Verification Plan

### Automated Tests
- Unit test for the `isTimeInRange` logic (especially for midnight crossover).

### Manual Verification
1.  Set timer to include current time -> Send a test Outlook notification (or simulate) -> Verify it rings.
2.  Set timer to exclude current time -> Send a test notification -> Verify it stays silent.
3.  Test cross-midnight range (e.g., 11 PM to 1 AM).
4.  Verify "Stop" button silences the ringtone.
