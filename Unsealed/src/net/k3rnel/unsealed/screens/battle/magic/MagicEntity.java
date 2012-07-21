package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.k3rnel.unsealed.screens.battle.BattleEntity;

public class MagicEntity extends BattleEntity {
    
    int speed;
    BattleEntity entity;
    
    public MagicEntity(int speed, BattleEntity entity) {
        super();
        this.speed = speed;
        this.entity = entity;
        setPosition(entity.getX(),entity.getY());
        setGridX(entity.getGridX());
        setGridY(entity.getGridY());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        this.setGridX(getGridX()+speed);
        
    }
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
       
        
    }
}
