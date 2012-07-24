package net.k3rnel.unsealed;

import net.k3rnel.unsealed.objects.Map;
import net.k3rnel.unsealed.screens.MenuScreen;
import net.k3rnel.unsealed.screens.OptionsScreen;
import net.k3rnel.unsealed.screens.SplashScreen;
import net.k3rnel.unsealed.services.MusicManager;
import net.k3rnel.unsealed.services.PreferencesManager;
import net.k3rnel.unsealed.services.ProfileManager;
import net.k3rnel.unsealed.services.SoundManager;
import net.k3rnel.unsealed.utils.MapLoader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.FPSLogger;

public class Unsealed extends Game {

    // Useful for Logs and stuff.
    public static final String LOG = Unsealed.class.getSimpleName();

    // whether we are in development mode
    public static final boolean DEBUG = true;
    
    // Gets the current FPS. Useful for debugging. 
    private FPSLogger fpsLogger;

    // services
    private PreferencesManager preferencesManager;
    private ProfileManager profileManager;
    private MusicManager musicManager;
    private SoundManager soundManager;
    //Loads teh maps
    private AssetManager assetManager;
    
    //The instance to our game.
    private static Unsealed instance;
    
    public Unsealed(){
    	instance = this;
		assetManager = new AssetManager();
		assetManager.setLoader(Map.class, new MapLoader(new InternalFileHandleResolver()));    	
    }
    
    // Service getters
    public PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
    
    public SplashScreen getSplashScreen() {
        return new SplashScreen(this);
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

        // create the profile manager
        profileManager = new ProfileManager();
        profileManager.retrieveProfile();

        // create the helper objects
        fpsLogger = new FPSLogger();

    }

    @Override
    public void resize( int width, int height ) {
        super.resize( width, height );
        Gdx.app.log( Unsealed.LOG, "Resizing game to: " + width + " x " + height );

        // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
            if( DEBUG ) {
                setScreen( new MenuScreen ( this ) );
            } else {
                setScreen( new SplashScreen( this ) );
            }
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

        // persist the profile, because we don't know if the player will come
        // back to the game
        profileManager.persist();
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