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

import android.util.Log;

import org.lineageos.settings.utils.FileUtils;

public class HbmUtils {
    private static final String TAG = "HbmUtils";
    public static final String HBM_KEY = "hbm";
    public static final String HBM_NODE = "/sys/devices/virtual/mi_display/disp_feature/disp-DSI-0/disp_param";
    public static final String HBM_ON = "01 01";
    public static final String HBM_OFF = "01 00";
    public static final String HBM_NOT_SUPPORT = "0";
    public static final String HBM_SUPPORT = "1";

    public static String getHbmSupportStatus() {
        if (FileUtils.fileExists(HBM_NODE)) {
            return HBM_SUPPORT;
        } else {
            Log.e(TAG, "Kernel do not support HBM!");
            return HBM_NOT_SUPPORT;
        }
    }

    public static boolean setHbmStatus(String status) {
        return FileUtils.writeLine(HBM_NODE, status);
    }

    public static String getHbmStatus() {
        if (FileUtils.fileExists(HBM_NODE)) {
            String line = FileUtils.readOneLine(HBM_NODE);
            if (line != null) {
                switch (line) {
                    case HBM_ON:
                        return HBM_ON;
                    case HBM_OFF:
                        return HBM_OFF;
                }
            } else {
                Log.e(TAG, "Failed to read HBM status from file!");
            }
        } else {
            Log.e(TAG, "HBM is not supported on this device!");
        }
        return HBM_NOT_SUPPORT;
    }
}
