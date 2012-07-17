package net.k3rnel.unsealed.screens.battle;

import java.util.HashMap;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BattleHero extends Actor {

    public static final int stateIdle = 0;
    public static final int stateAttacking = 1;
    public static final int stateCharging = 2;
    public static final int stateBlocking = 3;
    public static final int stateReleased = 4;

    public final HashMap<String, Animation> animations;
    public boolean waitingOnAnimation = false;
    public boolean isCharging = false;
    private Animation currentAnimation;
    private float stateTime;
    private int state;

    private int hp;
    private int mana;


    private int gridX;
    private int gridY;

    public BattleHero() {
        this.animations = new HashMap<String, Animation>();
        this.currentAnimation = null;

        hp = 100;
        mana = 0;
        gridX = 1;
        gridY = 1;       

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;

    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if(currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }

        batch.draw(currentAnimation.getKeyFrame(stateTime), getX(), getY());
        if(this.state == 1&&currentAnimation.isAnimationFinished(stateTime)){
            waitingOnAnimation = true;
            if(isCharging)
                setState(4);
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        Gdx.app.log(Unsealed.LOG,"Changed State to "+state);
        updateAnimations();
    }

    private void updateAnimations() {
        stateTime = 0;

        switch(state) {
            case stateIdle:
                currentAnimation = animations.get("waiting");
                break;
            case stateAttacking:
                isCharging = true;
                currentAnimation = animations.get("attacking");
                break;
            case stateCharging:
                currentAnimation = animations.get("charging");
                break;
            case stateBlocking:
                currentAnimation = animations.get("blocking");
                break;
            case stateReleased:
                currentAnimation = animations.get("released");
                break;
        }
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }
    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }
    /**
     * @param mana the mana to set
     */
    public void setMana(int mana) {
        this.mana = mana;
        if(this.mana<0)
            this.mana = 0;
        if(this.mana > 30)
            this.mana = 30;
    }

    /**
     * @return the gridX
     */
    public int getGridX() {
        return gridX;
    }

    /**
     * @param gridX the gridX to set
     */
    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    /**
     * @return the gridY
     */
    public int getGridY() {
        return gridY;
    }

    /**
     * @param gridY the gridY to set
     */
    public void setGridY(int gridY) {
        this.gridY = gridY;
    }
    
}
