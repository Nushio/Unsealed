package net.k3rnel.unsealed.story.characters;

import net.k3rnel.unsealed.story.helpers.MapCharacter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Snake extends MapCharacter {

    public Snake(TextureAtlas atlas) {
        super(atlas);
        createPlayer();
    }

    public void createPlayer() {
        // Get our character images to create animations.
        TextureRegion[][] tmp= getAtlas().findRegion("char-sprites/snake_spritesheet").split(32,32);
      
        setWidth(32);
        setHeight(32);

        //Create our animations

        Animation walkUp = new Animation(0.2f, new TextureRegion[]{ tmp[0][0], tmp[0][1], tmp[0][2]});
        Animation walkLeft = new Animation(0.2f, new TextureRegion[]{ tmp[1][0], tmp[1][1], tmp[1][2]});
        Animation walkDown = new Animation(0.2f, new TextureRegion[]{ tmp[2][0], tmp[2][1], tmp[2][2]});
        Animation walkRight = new Animation(0.2f, new TextureRegion[]{ tmp[3][0], tmp[3][1], tmp[3][2]});

        //Set the playmode
        
        walkUp.setPlayMode(Animation.LOOP);
        walkLeft.setPlayMode(Animation.LOOP);
        walkDown.setPlayMode(Animation.LOOP);
        walkRight.setPlayMode(Animation.LOOP);


        animations.put("walk_up", walkUp);
        animations.put("walk_left", walkLeft);
        animations.put("walk_down", walkDown);
        animations.put("walk_right", walkRight);
    }


}
