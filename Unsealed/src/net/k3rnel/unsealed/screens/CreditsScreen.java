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
package net.k3rnel.unsealed.screens;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Shows the splash screen for a few seconds before loading the actual game.
 */
public class CreditsScreen extends AbstractScreen {
    private Image splashImage;

    public CreditsScreen( Unsealed game ) {
        super( game );
    }

    @Override
    public void show() {
        super.show();
        game.getMusicManager().play( UnsealedMusic.CREDITS );
        // retrieve the splash image's region from the atlas
        AtlasRegion splashRegion = getAtlas().findRegion( "splash-screen/credits" );

        // here we create the splash image actor; its size is set when the
        // resize() method gets called
        splashImage = new Image( splashRegion);
        splashImage.setWidth(stage.getWidth());
        splashImage.setHeight(stage.getHeight());

        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        splashImage.getColor().a = 0f;
        
        // configure the fade-in/out effect on the splash image
        SequenceAction actions = sequence(fadeIn(0.75f), delay(15f), fadeOut(0.75f),
                        // when the image is faded out, move on to the next screen
                        run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen( new TYFPScreen( game) );
                                
                            }
                        }));
        splashImage.addAction(actions);

        // and finally we add the actor to the stage
        stage.addActor( splashImage );
        
        
    }
    
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
