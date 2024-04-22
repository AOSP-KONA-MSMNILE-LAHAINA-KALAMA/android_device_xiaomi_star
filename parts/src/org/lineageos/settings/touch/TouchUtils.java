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

import android.util.Log;

import org.lineageos.settings.utils.FileUtils;

public class TouchUtils {
    private static final String TAG = "TouchUtils";
    public static final String TOUCH_POLLING_RATE_KEY = "touch_polling_rate";
    public static final String TOUCH_POLLING_RATE_NODE = "/sys/devices/virtual/touch/touch_dev/bump_sample_rate";
    public static final String HIGH_POLLING_RATE_ON = "1";
    public static final String HIGH_POLLING_RATE_OFF = "0";
    public static final String HIGH_POLLING_RATE_NOT_SUPPORT = "-1";

    public static String getTouchStatus() {
        if (FileUtils.fileExists(TOUCH_POLLING_RATE_NODE)) {
            String status = FileUtils.readOneLine(TOUCH_POLLING_RATE_NODE);
            if (HIGH_POLLING_RATE_ON.equals(status)) {
                return HIGH_POLLING_RATE_ON;
            } else if (HIGH_POLLING_RATE_OFF.equals(status)) {
                return HIGH_POLLING_RATE_OFF;
            }
        } else {
            Log.e(TAG, "Kernel do not support High touch polling rate!");
        }
        return HIGH_POLLING_RATE_NOT_SUPPORT;
    }

    public static boolean setTouchStatus(String status) {
        return FileUtils.writeLine(TOUCH_POLLING_RATE_NODE, status);
    }
}
