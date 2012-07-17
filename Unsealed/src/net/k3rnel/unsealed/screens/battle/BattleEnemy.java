package net.k3rnel.unsealed.screens.battle;

import java.util.HashMap;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BattleEnemy extends Actor {

    public static final int stateIdle = 0;
    public static final int stateAttacking = 1;
    public static final int stateAltAttacking = 2;
    public static final int stateHit = 3;

    public final HashMap<String, Animation> animations;
    private Animation currentAnimation;
    private float stateTime;
    private int state;

    private int hp;
    private int mana;


    private int gridX;
    private int gridY;

    /**
     * Grid position of the enemy
     * @param x
     * @param y
     */
    public BattleEnemy(int hp, int x, int y) {
        this.animations = new HashMap<String, Animation>();
        this.currentAnimation = null;

        this.hp = hp;
        this.gridX = x;
        this.gridY = y;       

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
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        
        updateAnimations();
    }

    private void updateAnimations() {
        stateTime = 0;

        switch(state) {
            case stateIdle:
                currentAnimation = animations.get("idle");
                break;
            case stateAttacking:
                currentAnimation = animations.get("attacking");
                break;
            case stateAltAttacking:
                currentAnimation = animations.get("altattacking");
                break;
            case stateHit:
                currentAnimation = animations.get("hit");
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
