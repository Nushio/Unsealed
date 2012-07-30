package net.k3rnel.unsealed.story.characters;

import net.k3rnel.unsealed.story.helpers.MapCharacter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Kid extends MapCharacter {

    public int kid;

    public Kid(TextureAtlas atlas, int kid) {
        super(atlas);
        this.kid = kid;
        createPlayer(kid);
    }

    public void createPlayer(int kid) {
        // Get our character images to create animations.
        TextureRegion[][] tmp;
        switch(kid){
            case 0:
                tmp = getAtlas().findRegion("char-sprites/abi_spritesheet").split(48,48);
                break;
            case 1:
                tmp = getAtlas().findRegion("char-sprites/george_spritesheet").split(48,48);
                break;
            case 2:
                tmp = getAtlas().findRegion("char-sprites/tiny_spritesheet").split(48,48);
                break;
            default:
                tmp = getAtlas().findRegion("char-sprites/whitey_spritesheet").split(48,48);
                break;
        }

        setWidth(48);
        setHeight(48);

        //Create our animations
        Animation standUp = new Animation(1f, new TextureRegion[]{ tmp[0][2] });
        Animation standLeft = new Animation(1f, new TextureRegion[]{ tmp[0][1] });
        Animation standDown = new Animation(1f, new TextureRegion[]{ tmp[0][0] });
        Animation standRight = new Animation(1f, new TextureRegion[]{ tmp[0][3] });

        Animation walkUp = new Animation(0.1f, new TextureRegion[]{ tmp[1][2], tmp[2][2], tmp[3][2], tmp[2][2] });
        Animation walkLeft = new Animation(0.1f, new TextureRegion[]{ tmp[1][1], tmp[2][1], tmp[3][1], tmp[2][1] });
        Animation walkDown = new Animation(0.1f, new TextureRegion[]{ tmp[1][0], tmp[2][0], tmp[3][0], tmp[2][0] });
        Animation walkRight = new Animation(0.1f, new TextureRegion[]{ tmp[1][3], tmp[2][3], tmp[3][3], tmp[2][3] });

        //Set the playmode
        standUp.setPlayMode(Animation.LOOP);
        standLeft.setPlayMode(Animation.LOOP);
        standDown.setPlayMode(Animation.LOOP);
        standRight.setPlayMode(Animation.LOOP);

        walkUp.setPlayMode(Animation.LOOP);
        walkLeft.setPlayMode(Animation.LOOP);
        walkDown.setPlayMode(Animation.LOOP);
        walkRight.setPlayMode(Animation.LOOP);

        //Fill our map containing all animations with the animations.
        animations.put("stand_up", standUp);
        animations.put("stand_left", standLeft);
        animations.put("stand_down", standDown);
        animations.put("stand_right", standRight);

        animations.put("walk_up", walkUp);
        animations.put("walk_left", walkLeft);
        animations.put("walk_down", walkDown);
        animations.put("walk_right", walkRight);
    }


}
