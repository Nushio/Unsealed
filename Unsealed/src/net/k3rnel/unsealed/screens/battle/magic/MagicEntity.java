package net.k3rnel.unsealed.screens.battle.magic;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.BattleEntity;

public class MagicEntity extends BattleEntity {
    
    int speed;
    
    public MagicEntity(int speed, BattleEntity entity) {
        this.speed = speed;
        setPosition(entity.getX(),entity.getY());
        setGridX(entity.getGridX());
        setGridY(entity.getGridY());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        this.stateTime+=delta;
        Gdx.app.log(Unsealed.LOG, "Fireball"+getX());
        this.setX(getX()+speed);
        int blah = (int)((this.getX()-160)/80);
        Gdx.app.log(Unsealed.LOG, "Grid"+blah);
        this.setGridX(blah);
       
        if(this.getX()>650){
            this.getParent().removeActor(this);
        }
        
    }
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }
        batch.draw(currentAnimation.getKeyFrame(stateTime), getX(), getY());
        
    }
}
