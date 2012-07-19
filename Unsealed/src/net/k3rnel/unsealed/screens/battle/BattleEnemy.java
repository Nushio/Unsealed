package net.k3rnel.unsealed.screens.battle;


import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleEnemy extends BattleEntity {

    /**
     * Grid position of the enemy
     * @param x
     * @param y
     */
    public BattleEnemy(int hp, int x, int y) {
      
        setHp(hp);
        
        setGridX(x);
        setGridY(y);       

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.stateTime+=delta;

    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        if(this.currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }
        batch.draw(this.currentAnimation.getKeyFrame(this.stateTime), getX(), getY());
       
    }

    
}
