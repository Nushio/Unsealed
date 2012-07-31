/**
 * Unsealed: Whispers of Wisdom. 
 * 
 * Copyright (C) 2012 - Juan 'Nushio' Rodriguez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 of 
 * the License as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package net.k3rnel.unsealed.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Handles the game preferences.
 * Written by Gustavo Steigert, Originally licensed Apache 2.0 and relicensed with his permission
 * https://code.google.com/p/steigert-libgdx/source/browse/tags/post-20120709/tyrian-game/src/com/blogspot/steigert/tyrian/services/SoundManager.java
 */
public class PreferencesManager {
    
    // constants
    private static final String PREF_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREFS_NAME = "unsealed";

    public PreferencesManager() {
    }

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences( PREFS_NAME );
    }

    public boolean isSoundEnabled() {
        return getPrefs().getBoolean( PREF_SOUND_ENABLED, true );
    }

    public void setSoundEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean( PREF_SOUND_ENABLED, soundEffectsEnabled );
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean( PREF_MUSIC_ENABLED, true );
    }

    public void setMusicEnabled( boolean musicEnabled ) {
        getPrefs().putBoolean( PREF_MUSIC_ENABLED, musicEnabled );
        getPrefs().flush();
    }

    public float getVolume() {
        return getPrefs().getFloat( PREF_VOLUME, 0.5f );
    }

    public void setVolume(float volume) {
        getPrefs().putFloat( PREF_VOLUME, volume );
        getPrefs().flush();
    }
}
