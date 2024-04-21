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

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class HbmTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();

        Tile tile = getQsTile();
        if (tile == null)
            return;

        if (tile.getState() == Tile.STATE_ACTIVE) {
            setHbmStatus(HbmUtils.HBM_OFF);
            tile.setState(Tile.STATE_INACTIVE);
        } else {
            setHbmStatus(HbmUtils.HBM_ON);
            tile.setState(Tile.STATE_ACTIVE);
        }
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();

        Tile tile = getQsTile();
        if (tile == null)
            return;

        String hbmStatus = getHbmStatus();

        tile.setState(hbmStatus.equals(HbmUtils.HBM_ON) ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    private String getHbmStatus() {
        return HbmUtils.getHbmStatus();
    }

    private void setHbmStatus(String status) {
        HbmUtils.setHbmStatus(status);
    }
}
