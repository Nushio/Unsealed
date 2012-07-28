package net.k3rnel.unsealed.story.characters;

import net.k3rnel.unsealed.story.helpers.MapCharacter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Pixie extends MapCharacter {

    public Pixie() {
        super();
        createPlayer();
    }
    
    public void createPlayer() {
        // Get our character images to create animations.
        TextureRegion[][] tmp = getAtlas().findRegion("char-sprites/pixie_spritesheet").split(38,38);
        
        setWidth(38);
        setHeight(38);
        
        //Create our animations
        Animation animation = new Animation(1f, new TextureRegion[]{ tmp[0][4] });
        animation.setPlayMode(Animation.LOOP_PINGPONG);
        animations.put("frozen", animation);

        animation = new Animation(0.1f, new TextureRegion[]{ tmp[0][0], tmp[0][1], tmp[0][2], tmp[0][3],tmp[0][2],tmp[0][1],tmp[0][0] });
        animation.setPlayMode(Animation.LOOP);
        animations.put("animated", animation);
    }
    
    @Override
    public void updateAnimation() {
        if(isWalking())
            setCurrentAnim("animated");
        else
            setCurrentAnim("frozen");
    }
  
   
}
