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

package org.lineageos.settings.hbm;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;

import org.lineageos.settings.R;

public class HbmSettingsFragment extends PreferenceFragment {
    private SwitchPreference mHbmPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.hbm_settings);
        mHbmPreference = (SwitchPreference) findPreference(HbmUtils.HBM_KEY);

        if (mHbmPreference != null) {
            if (!HbmUtils.getHbmSupportStatus().equals(HbmUtils.HBM_NOT_SUPPORT)) {
                mHbmPreference.setEnabled(true);
                mHbmPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        if (HbmUtils.HBM_KEY.equals(preference.getKey())) {
                            if (!HbmUtils.setHbmStatus((boolean) newValue ? HbmUtils.HBM_ON : HbmUtils.HBM_OFF)) {
                                Log.e(TAG, "Failed to set HBM node!");
                                Toast.makeText(getContext(), R.string.hbm_failed, Toast.LENGTH_SHORT).show();
                                mHbmPreference.setChecked(false);
                            }
                        }
                        return true;
                    }
                });
            } else {
                mHbmPreference.setEnabled(false);
            }
        }
    }

    private static final String TAG = "HbmSettingsFragment";
}
