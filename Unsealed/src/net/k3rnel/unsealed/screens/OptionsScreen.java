package net.k3rnel.unsealed.screens;

import java.util.Locale;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.services.SoundManager.UnsealedSound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen extends AbstractScreen {
    
    private Table table;
    private Label volumeValue;

    public OptionsScreen( Unsealed game ) {
        super( game );
    }

    @Override
    public void show() {
        super.show();

        // retrieve the custom skin for our 2D widgets
        Skin skin = super.getSkin();

        // create the table actor and add it to the stage
        table = super.getTable();

        table.defaults().spaceBottom( 30 );
        table.columnDefaults( 0 ).padRight( 20 );
        table.add( "Options" ).colspan( 3 );
        
        final CheckBox soundEffectsCheckbox = new CheckBox("", skin );
        soundEffectsCheckbox.setChecked( game.getPreferencesManager().isSoundEnabled() );
        soundEffectsCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor ) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferencesManager().setSoundEnabled( enabled );
                game.getSoundManager().setEnabled( enabled );
                game.getSoundManager().play( UnsealedSound.CLICK );
            }
        } );
        table.row();
        table.add( "Sound Effects" );
        table.add( soundEffectsCheckbox ).colspan( 2 ).left();

        final CheckBox musicCheckbox = new CheckBox("", skin );
        musicCheckbox.setChecked( game.getPreferencesManager().isMusicEnabled() );
        musicCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor ) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferencesManager().setMusicEnabled( enabled );
                game.getMusicManager().setEnabled( enabled );
                game.getSoundManager().play( UnsealedSound.CLICK );

                // if the music is now enabled, start playing the menu music
                if( enabled ) game.getMusicManager().play( UnsealedMusic.MENU );
            }
        } );
        table.row();
        table.add( "Music" );
        table.add( musicCheckbox ).colspan( 2 ).left();

        
        // range is [0.0,1.0]; step is 0.1f
        final Slider volumeSlider = new Slider( 0f, 1f, 0.1f, skin );
        volumeSlider.setValue( game.getPreferencesManager().getVolume() );
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getPreferencesManager().setVolume( volumeSlider.getValue() );
                game.getMusicManager().setVolume( volumeSlider.getValue() );
                game.getSoundManager().setVolume( volumeSlider.getValue() );
                updateVolumeLabel();
            }
        } );
     // create the volume label
        volumeValue = new Label( "", getSkin() );
        updateVolumeLabel();

        // add the volume row
        table.row();
        table.add( "Volume" );
        table.add( volumeSlider );
        table.add( volumeValue ).width( 40 );

        
        TextButton backButton = new TextButton( "Back to Main Menu", skin );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(ActorEvent event, float x, float y ) {
                game.getSoundManager().play( UnsealedSound.CLICK );
                game.setScreen( new MenuScreen( game ) );
            }
        } );
        table.row();
        table.add( backButton ).size( 250, 60 ).colspan( 3 );
        
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage));
    }

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel() {
        float volume = ( game.getPreferencesManager().getVolume() * 100 );
        volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
        Gdx.app.log( Unsealed.LOG, "Volume Value: " + volume );
    }
    
    /**
     * On key press
     */
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.BACK:
                game.setScreen(new MenuScreen(game));
                return true;
            case Input.Keys.ESCAPE:
                game.setScreen(new MenuScreen(game));
                return true;
                
        }
        return false;
    }
}
