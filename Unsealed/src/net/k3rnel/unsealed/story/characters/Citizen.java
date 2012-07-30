package net.k3rnel.unsealed.story.characters;

import net.k3rnel.unsealed.story.helpers.MapCharacter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Citizen extends MapCharacter {

    public int citizen;

    public Citizen(TextureAtlas atlas, int citizen) {
        super(atlas);
        this.citizen = citizen;
        createPlayer(citizen);
    }

    public void createPlayer(int citizen) {
        // Get our character images to create animations.
        TextureRegion[][] tmp;
        tmp = getAtlas().findRegion("char-sprites/citizen_"+citizen+"_spritesheet").split(64,64);

        setWidth(64);
        setHeight(64);

        //Create our animations
        Animation standUp = new Animation(1f, new TextureRegion[]{ tmp[0][0] });
        Animation standLeft = new Animation(1f, new TextureRegion[]{ tmp[1][0] });
        Animation standDown = new Animation(1f, new TextureRegion[]{ tmp[2][0] });
        Animation standRight = new Animation(1f, new TextureRegion[]{ tmp[3][0] });
        
        Animation walkUp = new Animation(0.1f, new TextureRegion[]{ tmp[0][1], tmp[0][2], tmp[0][3], tmp[0][4], tmp[0][5], tmp[0][6], tmp[0][7], tmp[0][8] });
        Animation walkLeft = new Animation(0.1f, new TextureRegion[]{ tmp[1][1], tmp[1][2], tmp[1][3], tmp[1][4], tmp[1][5], tmp[1][6], tmp[1][7], tmp[1][8] });
        Animation walkDown = new Animation(0.1f, new TextureRegion[]{ tmp[2][1], tmp[2][2], tmp[2][3], tmp[2][4], tmp[2][5], tmp[2][6], tmp[2][7], tmp[2][8] });
        Animation walkRight = new Animation(0.1f, new TextureRegion[]{ tmp[3][1], tmp[3][2], tmp[3][3], tmp[3][4], tmp[3][5], tmp[3][6], tmp[3][7], tmp[3][8] });
      
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
