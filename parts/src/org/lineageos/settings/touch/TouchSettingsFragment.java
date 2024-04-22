/*
 * Copyright (C) 2024 Paranoid Android
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

package org.lineageos.settings.touch;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;

import org.lineageos.settings.R;

public class TouchSettingsFragment extends PreferenceFragment {
    private static final String TAG = "TouchSettingsFragment";
    private SwitchPreference mTouchPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.touch_polling_rate_settings);
        mTouchPreference = (SwitchPreference) findPreference(TouchUtils.TOUCH_POLLING_RATE_KEY);
        if (mTouchPreference != null) {
            if (!TouchUtils.getTouchStatus().equals(TouchUtils.HIGH_POLLING_RATE_NOT_SUPPORT)) {
                mTouchPreference.setEnabled(true);
                mTouchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object any) {
                        if (TouchUtils.TOUCH_POLLING_RATE_KEY.equals(preference.getKey())) {
                            if (!TouchUtils.setTouchStatus((boolean) any ? TouchUtils.HIGH_POLLING_RATE_ON : TouchUtils.HIGH_POLLING_RATE_OFF)) {
                                Log.e(TAG, "Failed to set High touch poling rate node!");
                            }
                        }
                        return true;
                    }
                });
            } else {
                mTouchPreference.setEnabled(false);
            }
        }
    }
}
