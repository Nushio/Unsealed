package net.k3rnel.unsealed.screens;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Shows the splash screen for a few seconds before loading the actual game.
 */
public class SplashScreen extends AbstractScreen {
    private Image splashImage;

    public SplashScreen( Unsealed game ) {
        super( game );
    }

    @Override
    public void show() {
        super.show();

        // retrieve the splash image's region from the atlas
        AtlasRegion splashRegion = getAtlas().findRegion( "splash-screen/splash" );

        // here we create the splash image actor; its size is set when the
        // resize() method gets called
        splashImage = new Image( splashRegion);
        splashImage.setWidth(stage.getWidth());
        splashImage.setHeight(stage.getHeight());

        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        splashImage.getColor().a = 0f;
        
        // configure the fade-in/out effect on the splash image
        SequenceAction actions = Actions.sequence(
                Actions.fadeIn(0.75f), 
                Actions.delay(1.75f, Actions.fadeOut(0.75f)),
                        // when the image is faded out, move on to the next screen
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen( new MenuScreen( game ) );
                                
                            }
                        }));
        splashImage.addAction(actions);

        // and finally we add the actor to the stage
        stage.addActor( splashImage );
        
        
    }
    
    
}
