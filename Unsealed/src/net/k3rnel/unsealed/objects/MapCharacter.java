package net.k3rnel.unsealed.objects;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MapCharacter extends MapActor {
	public static final int dirUp = 1;
	public static final int dirDown = 2;
	public static final int dirLeft = 3;
	public static final int dirRight = 4;
	
	public HashMap<String, Animation> animations;
	private Animation currentAnimation;
	private float animTime;
	private int direction;
	private boolean walking;
	private float speed;
	
	public MapCharacter() {
		this.animations = new HashMap<String, Animation>();
		this.direction = dirDown;
		this.walking = false;
		updateAnimation();
	}
	
	@Override
	public void act(float delta) {
	    super.act(delta);
	    animTime += delta;
        
        if(currentAnimation != null)
            this.setDrawable(new Image(currentAnimation.getKeyFrame(animTime)).getDrawable());

	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
	    super.draw(batch, parentAlpha);
	}
	
	@Override
	public void moved(int tileX, int tileY) {
		int dx = tileX - getTileX();
		int dy = tileY - getTileY();
		
		if(dy > 0) {
			setDirection(dirDown);
		} else if(dy < 0) {
			setDirection(dirUp);
		} else if(dx > 0) {
			setDirection(dirRight);
		} else if(dx < 0) {
			setDirection(dirLeft);
		}
	}

	@Override
	public void moveStarted() {
		setWalking(true);
	}

	@Override
	public void moveStopped() {
		setWalking(false);
	}

	public HashMap<String, Animation> getAnimations() {
		return animations;
	}
	
	private void setCurrentAnim(String name) {
		currentAnimation = animations.get(name);
		animTime = 0;
	}
	
	protected TextureRegion getKeyFrame() {
		if(currentAnimation == null)
			return null;
		
		return currentAnimation.getKeyFrame(animTime);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		if(this.direction != direction) {
			this.direction = direction;
			updateAnimation();
		}
	}

	public boolean isWalking() {
		return walking;
	}

	public void setWalking(boolean walking) {
		if(this.walking != walking) {
			this.walking = walking;
			updateAnimation();
		}
	}
	
	private void updateAnimation() {
		String animPrefix = "stand";
		
		if(walking)
			animPrefix = "walk";
		
		switch(direction) {
			case dirUp:
				setCurrentAnim(animPrefix + "_up");
				break;
				
			case dirDown:
				setCurrentAnim(animPrefix + "_down");
				break;
				
			case dirLeft:
				setCurrentAnim(animPrefix + "_left");
				break;
				
			case dirRight:
				setCurrentAnim(animPrefix + "_right");
				break;
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}