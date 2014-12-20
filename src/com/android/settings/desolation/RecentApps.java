/*
 * Copyright (C) 2015 DesolationROM Project
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

package com.android.settings.desolation;

import android.os.Bundle;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class RecentApps extends SettingsPreferenceFragment
        implements OnPreferenceChangeListener {

    private static final String RECENT_APPS_LOCATION = "recents_clear_all_location";

    private ListPreference mRecentAppsLocation;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.recent_apps);

        ContentResolver resolver = getActivity().getContentResolver();
        PreferenceScreen prefSet = getPreferenceScreen();

        mRecentAppsLocation = (ListPreference)
                prefSet.findPreference(RECENT_APPS_LOCATION);
        int location = Settings.System.getIntForUser(resolver,
                Settings.System.RECENTS_CLEAR_ALL_LOCATION, 3,
                UserHandle.USER_CURRENT);
        mRecentAppsLocation.setValue(String.valueOf(location));
        mRecentAppsLocation.setSummary(mRecentAppsLocation.getEntry());
        mRecentAppsLocation.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mRecentAppsLocation) {
            int location = Integer.valueOf((String) objValue);
            int index = mRecentAppsLocation.findIndexOfValue((String) objValue);
            Settings.System.putIntForUser(getActivity().getContentResolver(),
                    Settings.System.RECENTS_CLEAR_ALL_LOCATION, location,
                    UserHandle.USER_CURRENT);
            mRecentAppsLocation.setSummary(mRecentAppsLocation.getEntries()[index]);
            return true;
        }
        return false;
    }
}
