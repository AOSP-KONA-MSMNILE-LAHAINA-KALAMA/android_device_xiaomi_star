/*
 * Copyright (C) 2015 The CyanogenMod Project
 *               2017-2019 The LineageOS Project
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

package org.lineageos.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.dirac.DiracUtils;
import org.lineageos.settings.utils.FileUtils;
import org.lineageos.settings.touch.TouchUtils;

public class BootCompletedReceiver extends BroadcastReceiver {

    private static final boolean DEBUG = false;
    private static final String TAG = "XiaomiParts";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (DEBUG) Log.d(TAG, "Received boot completed intent");
        DiracUtils.initialize(context);

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        boolean highPollingRateEnabled = sharedPreference.getBoolean(TouchUtils.TOUCH_POLLING_RATE_KEY, false);
        if (!TouchUtils.setTouchStatus(highPollingRateEnabled ? TouchUtils.HIGH_POLLING_RATE_ON : TouchUtils.HIGH_POLLING_RATE_OFF)) {
            Log.e(TAG, "Failed to set High touch poling rate node on boot!");
        }
    }
}
