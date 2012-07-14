package net.k3rnel.unsealed.objects;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameCharacter extends MapObject {
	
    private Map<AnimationType, Animation> animations;

	/**
	 * 
	 * @param mapX
	 * @param mapY
	 * @param mapName
	 */
	public GameCharacter(TextureAtlas spriteAtlas, int mapX, int mapY, String mapName) {
		super(mapX, mapY, mapName);
		
		//Initialize the map which contains the animations for each animation type.
		animations = new HashMap<AnimationType, Animation>();
		
        // Get our character images to create animations.
        TextureRegion[][] tmp = spriteAtlas.findRegion("char-sprites/male_spritesheet").split(64, 64);
        
        //Fill our map containing all animations with the animations.
        animations.put(AnimationType.STAND_UP, new Animation(1f, new TextureRegion[]{ tmp[0][0] }));
        animations.put(AnimationType.STAND_LEFT, new Animation(1f, new TextureRegion[]{ tmp[1][0] }));
        animations.put(AnimationType.STAND_DOWN, new Animation(1f, new TextureRegion[]{ tmp[2][0] }));
        animations.put(AnimationType.STAND_RIGHT, new Animation(1f, new TextureRegion[]{ tmp[3][0] }));
        
        animations.put(AnimationType.WALK_UP, new Animation(1.5f, new TextureRegion[]{ tmp[0][1], tmp[0][2], tmp[0][3], tmp[0][4], tmp[0][5], tmp[0][6], tmp[0][7], tmp[0][8] }));
        animations.put(AnimationType.WALK_LEFT, new Animation(1.5f, new TextureRegion[]{ tmp[1][1], tmp[1][2], tmp[1][3], tmp[1][4], tmp[1][5], tmp[1][6], tmp[1][7], tmp[1][8] }));
        animations.put(AnimationType.WALK_DOWN, new Animation(1.5f, new TextureRegion[]{ tmp[2][1], tmp[2][2], tmp[2][3], tmp[2][4], tmp[2][5], tmp[2][6], tmp[2][7], tmp[2][8] }));
        animations.put(AnimationType.WALK_RIGHT, new Animation(1.5f, new TextureRegion[]{ tmp[3][1], tmp[3][2], tmp[3][3], tmp[3][4], tmp[3][5], tmp[3][6], tmp[3][7], tmp[3][8] }));
	}
	
	/**
	 * The types of animation a player character can have when walking in the overworld.
	 * 
	 * @author Drawig
	 */
	public enum AnimationType {
		WALK_LEFT,
		WALK_UP,
		WALK_RIGHT,
		WALK_DOWN,
		STAND_LEFT,
		STAND_UP,
		STAND_RIGHT,
		STAND_DOWN
	}
}