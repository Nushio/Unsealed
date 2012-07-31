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

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.SoundManager.UnsealedSound;
import net.k3rnel.unsealed.utils.LRUCache;
import net.k3rnel.unsealed.utils.LRUCache.CacheEntryRemovedListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the sound effects.
 * Written by Gustavo Steigert, Originally licensed Apache 2.0 and relicensed with his permission
 * https://code.google.com/p/steigert-libgdx/source/browse/tags/post-20120709/tyrian-game/src/com/blogspot/steigert/tyrian/services/SoundManager.java
 */
public class SoundManager implements CacheEntryRemovedListener<UnsealedSound,Sound>, Disposable {

    /**
     * The available sound files.
     */
    public enum UnsealedSound
    {
        CLICK( "sfx/enchant.ogg" ),
        FIRE( "sfx/Fire.ogg" )
        ;

        private final String fileName;

        private UnsealedSound( String fileName ) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    /**
     * The volume to be set on the sound.
     */
    private float volume = 1f;

    /**
     * Whether the sound is enabled.
     */
    private boolean enabled = true;

    /**
     * The sound cache.
     */
    private final LRUCache<UnsealedSound,Sound> soundCache;

    /**
     * Creates the sound manager.
     */
    public SoundManager() {
        soundCache = new LRUCache<SoundManager.UnsealedSound,Sound>( 10 );
        soundCache.setEntryRemovedListener( this );
    }

    /**
     * Plays the specified sound.
     */
    public void play(UnsealedSound sound ) {
        // check if the sound is enabled
        if(!enabled ){
            return;
        }

        // try and get the sound from the cache
        Sound soundToPlay = soundCache.get( sound );
        if( soundToPlay == null ) {
            FileHandle soundFile = Gdx.files.internal( sound.getFileName() );
            soundToPlay = Gdx.audio.newSound( soundFile );
            soundCache.add( sound, soundToPlay );
        }

        // play the sound
        Gdx.app.log( Unsealed.LOG, "Playing sound: " + sound.name() );
        soundToPlay.play( volume );
    }

    /**
     * Sets the sound volume which must be inside the range [0,1].
     */
    public void setVolume(float volume) {
        Gdx.app.log( Unsealed.LOG, "Adjusting sound volume to: " + volume );

        // check and set the new volume
        if( volume < 0 || volume > 1f ) {
            throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
        }
        this.volume = volume;
    }

    /**
     * Enables or disabled the sound.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // EntryRemovedListener implementation

    @Override
    public void notifyEntryRemoved(UnsealedSound key, Sound value) {
        Gdx.app.log( Unsealed.LOG, "Disposing sound: " + key.name() );
        value.dispose();
    }

    /**
     * Disposes the sound manager.
     */
    public void dispose() {
        Gdx.app.log( Unsealed.LOG, "Disposing sound manager" );
        for( Sound sound : soundCache.retrieveAll() ) {
            sound.stop();
            sound.dispose();
        }
    }
}