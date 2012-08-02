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

package net.k3rnel.unsealed;

import net.k3rnel.unsealed.screens.MenuScreen;
import net.k3rnel.unsealed.screens.OptionsScreen;
import net.k3rnel.unsealed.screens.QuickTutorialScreen;
import net.k3rnel.unsealed.screens.SplashScreen;
import net.k3rnel.unsealed.services.MusicManager;
import net.k3rnel.unsealed.services.PreferencesManager;
import net.k3rnel.unsealed.services.SoundManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;

public class Unsealed extends Game {

    // Useful for Logs and stuff.
    public static final String LOG = Unsealed.class.getSimpleName();

    // whether we are in development mode
    public static final boolean DEBUG = false;
    
    // Gets the current FPS. Useful for debugging. 
    private FPSLogger fpsLogger;

    // services
    private PreferencesManager preferencesManager;
    private MusicManager musicManager;
    private SoundManager soundManager;
    //Loads teh maps
    private AssetManager assetManager;
    
    //The instance to our game.
    private static Unsealed instance;
    
    public Unsealed(){
    	instance = this;
		assetManager = new AssetManager(); 	
    }
    
    // Service getters
    public PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
    
    public QuickTutorialScreen getSplashScreen() {
        return new QuickTutorialScreen(this);
    }

    public MenuScreen getMenuScreen() {
        return new MenuScreen(this);
    }

    public OptionsScreen getOptionsScreen(){
        return new OptionsScreen(this);
    }
    
    public AssetManager getAssetManager() {
    	return assetManager;
    }

    @Override
    public void create() {	
        Gdx.app.log( Unsealed.LOG, "Creating game on " + Gdx.app.getType() );

        // create the preferences manager
        preferencesManager = new PreferencesManager();

        // create the music manager
        musicManager = new MusicManager();
        musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( preferencesManager.isMusicEnabled() );

        // create the sound manager
        soundManager = new SoundManager();
        soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( preferencesManager.isSoundEnabled() );

        // create the helper objects
        if(Unsealed.DEBUG)
            fpsLogger = new FPSLogger();

    }

    @Override
    public void resize( int width, int height ) {
        super.resize( width, height );
        Gdx.app.log( Unsealed.LOG, "Resizing game to: " + width + " x " + height );

        // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
            if(Unsealed.DEBUG)
                setScreen( new MenuScreen ( this ) );
            else
                setScreen( new SplashScreen( this ) );
        }
    }

    @Override
    public void render() {
        super.render();

        // output the current FPS
        if( DEBUG ) {
            fpsLogger.log();
        }
    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.log( Unsealed.LOG, "Pausing game" );

    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.log( Unsealed.LOG, "Resuming game" );
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen( screen );
        Gdx.app.log( Unsealed.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.log( Unsealed.LOG, "Disposing game" );

        // dipose some services
        musicManager.dispose();
        soundManager.dispose();
    }
    
    public static Unsealed getInstance() {
    	return instance;
    }
}