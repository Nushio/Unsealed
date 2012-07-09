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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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
        table = new Table( skin );
        table.debug();

        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.pad(4);
        
        final Label optionsLabel = new Label("Options", skin);
        table.add(optionsLabel).colspan(3).align(Align.center);
        table.row();
        
        final Label soundEffectsLabel = new Label("Sound Effects", skin);
        final CheckBox soundEffectsCheckbox = new CheckBox("", skin );
        soundEffectsCheckbox.setChecked( game.getPreferencesManager().isSoundEnabled() );
        soundEffectsCheckbox.addListener( new ClickListener() {
            @Override
            public void clicked(ActorEvent event, float x, float y ) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferencesManager().setSoundEnabled( enabled );
                game.getSoundManager().setEnabled( enabled );
                game.getSoundManager().play( UnsealedSound.CLICK );
            }
        } );
        //colspan:2 align:left
        table.add(soundEffectsLabel).align(Align.left);
        table.add(soundEffectsCheckbox).padLeft(5).colspan(2).align(Align.left);
        table.row();
        final Label musicLabel = new Label("Music", skin);
        final CheckBox musicCheckbox = new CheckBox("", skin );
        musicCheckbox.setChecked( game.getPreferencesManager().isMusicEnabled() );
        musicCheckbox.addListener( new ClickListener() {
            @Override
            public void clicked(ActorEvent event, float x, float y ) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferencesManager().setMusicEnabled( enabled );
                game.getMusicManager().setEnabled( enabled );
                game.getSoundManager().play( UnsealedSound.CLICK );

                // if the music is now enabled, start playing the menu music
                if( enabled ) game.getMusicManager().play( UnsealedMusic.MENU );
            }
        } );
        table.add(musicLabel).align(Align.left);
        table.add(musicCheckbox).padLeft(5).colspan(2).align(Align.left);
        
        table.row();
        
        final Label volumeLabel = new Label("Volume",skin);
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
        table.add(volumeLabel).align(Align.left);
        table.add(volumeSlider).padLeft(5).align(Align.left);
        

        volumeValue = new Label("Volume", skin );
        updateVolumeLabel();
        table.add(volumeValue);
        
        table.row();
        TextButton backButton = new TextButton( "Back to Main Menu", skin );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(ActorEvent event, float x, float y ) {
                game.getSoundManager().play( UnsealedSound.CLICK );
                game.setScreen( new MenuScreen( game ) );
            }
        } );
        //colspan:3 width:40% minwidth:100 maxwidth:250 height:12% minheight:30 maxheight:50 spacingTop:20
        table.add(backButton).colspan(3).minWidth(100).maxWidth(250).height(.12f).minHeight(30).maxHeight(50).spaceTop(20);
        stage.addActor( table );
        
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
