/*
 * Copyright (C) 2018,2020 The LineageOS Project
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

package org.lineageos.settings.dirac;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.SystemProperties;
import android.preference.PreferenceManager;

public final class DiracUtils {

    protected static DiracSound mDiracSound;
    private static boolean mInitialized;
    private static Context mContext;

    public static void initialize(Context context) {
        if (!mInitialized) {
            mContext = context;
            mDiracSound = new DiracSound(0, 0);
            mInitialized = true;

            // Restore selected scene
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            String scene = sharedPrefs.getString(DiracSettingsFragment.PREF_SCENARIO, "0" /* None */);
            setScenario(Integer.parseInt(scene));
        }
    }

    protected static void setMusic(boolean enable) {
        if (enable) {
            SystemProperties.set("persist.vendor.audio.misound.disable", "false");
            mDiracSound.setMusic(1);
        } else {
            mDiracSound.setMusic(0);
            SystemProperties.set("persist.vendor.audio.misound.disable", "true");
        }
    }

    protected static boolean isDiracEnabled() {
        return mDiracSound != null && mDiracSound.getMusic() == 1;
    }

    protected static void setLevel(String preset) {
        String[] level = preset.split("\\s*,\\s*");

        for (int band = 0; band <= level.length - 1; band++) {
            mDiracSound.setLevel(band, Float.valueOf(level[band]));
        }
    }

    protected static void setHeadsetType(int paramInt) {
        mDiracSound.setHeadsetType(paramInt);
    }

    protected static boolean getHifiMode() {
        AudioManager audioManager = mContext.getSystemService(AudioManager.class);
        return audioManager.getParameters("hifi_mode").contains("true");
    }

    protected static void setHifiMode(int paramInt) {
        AudioManager audioManager = mContext.getSystemService(AudioManager.class);
        audioManager.setParameters("hifi_mode=" + (paramInt == 1 ? true : false));
        mDiracSound.setHifiMode(paramInt);
    }

    protected static void setScenario(int paramInt) {
        mDiracSound.setScenario(paramInt);
    }
}
