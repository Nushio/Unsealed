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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the background music.
 * <p>
 * Only one music may be playing at a given time.
 * Written by Gustavo Steigert, Originally licensed Apache 2.0 and relicensed with his permission
 * https://code.google.com/p/steigert-libgdx/source/browse/tags/post-20120709/tyrian-game/src/com/blogspot/steigert/tyrian/services/MusicManager.java
 */
public class MusicManager implements Disposable {
    
    /**
     * The available music files.
     */
    public enum UnsealedMusic {
        MENU( "music/05 - Opening Theme.ogg" ),
        CAVE( "music/alienblues.ogg" ),
        DESERT( "music/alienblues.ogg" ),
        TOWN( "music/04 - Theme of Love_0.ogg" ),
        BATTLE( "music/hold the line_1.ogg" ),
        DOJO( "music/14 - Tragedy.ogg" ),
        GRASS("music/The Adventure Begins 8-bit remix.ogg"),
        VICTORY( "music/victory-theme.ogg" ),
        BOSSBATTLE( "music/07 - Overworld.ogg" );

        private String fileName;
        private Music musicResource;

        private UnsealedMusic(String fileName ) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

        public Music getMusicResource() {
            return musicResource;
        }

        public void setMusicResource(Music musicBeingPlayed) {
            this.musicResource = musicBeingPlayed;
        }
    }

    /**
     * Holds the music currently being played, if any.
     */
    private UnsealedMusic musicBeingPlayed;

    /**
     * The volume to be set on the music.
     */
    private float volume = 1f;

    /**
     * Whether the music is enabled.
     */
    private boolean enabled = true;

    /**
     * Creates the music manager.
     */
    public MusicManager() {
        
    }

    /**
     * Plays the given music (starts the streaming).
     * <p>
     * If there is already a music being played it is stopped automatically.
     */
    public void play(UnsealedMusic music) {
        // check if the music is enabled
        if( ! enabled ){
            return;
        }

        // check if the given music is already being played
        if( musicBeingPlayed == music ){
            return;
        }

        // do some logging
        Gdx.app.log( Unsealed.LOG, "Playing music: " + music.name() );

        // stop any music being played
        stop();

        // start streaming the new music
        FileHandle musicFile = Gdx.files.internal( music.getFileName() );
        Music musicResource = Gdx.audio.newMusic( musicFile );
        musicResource.setVolume( volume );
        musicResource.setLooping( true );
        musicResource.play();

        // set the music being played
        musicBeingPlayed = music;
        musicBeingPlayed.setMusicResource( musicResource );
    }

    /**
     * Stops and disposes the current music being played, if any.
     */
    public void stop() {
        if( musicBeingPlayed != null ) {
            Gdx.app.log( Unsealed.LOG, "Stopping current music" );
            Music musicResource = musicBeingPlayed.getMusicResource();
            musicResource.stop();
            musicResource.dispose();
            musicBeingPlayed = null;
        }
    }

    /**
     * Sets the music volume which must be inside the range [0,1].
     */
    public void setVolume(float volume) {
        Gdx.app.log( Unsealed.LOG, "Adjusting music volume to: " + volume );

        // check and set the new volume
        if( volume < 0 || volume > 1f ) {
            throw new IllegalArgumentException( "The volume must be inside the range: [0,1]" );
        }
        this.volume = volume;

        // if there is a music being played, change its volume
        if( musicBeingPlayed != null ) {
            musicBeingPlayed.getMusicResource().setVolume( volume );
        }
    }

    /**
     * Enables or disabled the music.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        // if the music is being deactivated, stop any music being played
        if( ! enabled ) {
            stop();
        }
    }

    /**
     * Disposes the music manager.
     */
    public void dispose() {
        Gdx.app.log( Unsealed.LOG, "Disposing music manager" );
        stop();
    }
}
