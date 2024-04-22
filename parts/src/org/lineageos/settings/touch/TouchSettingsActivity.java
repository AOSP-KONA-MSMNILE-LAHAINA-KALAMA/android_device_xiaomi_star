/*
 * Copyright (C) 2024 Project 404
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

import android.app.Fragment;
import android.os.Bundle;
import com.android.settingslib.collapsingtoolbar.CollapsingToolbarBaseActivity;
import com.android.settingslib.widget.R;

public class TouchSettingsActivity extends CollapsingToolbarBaseActivity {
    private static final String TAG = "High Touch Polling Rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment touchSettingsFragment = new TouchSettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, touchSettingsFragment, TAG)
                .commit();
    }
}
