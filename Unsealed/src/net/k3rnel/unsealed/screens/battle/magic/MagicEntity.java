package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.k3rnel.unsealed.screens.battle.BattleEntity;

public class MagicEntity extends BattleEntity {
    
    public float speedX;
    public float speedY;
    public boolean isArc = false;
    public float initialPos = 0;
    public float maxDistance = 1;
    BattleEntity entity;
    
    public MagicEntity(float speedX, float speedY, BattleEntity entity) {
        super();
        this.speedX = speedX;
        this.speedY = speedY;
        this.entity = entity;
        setPosition(entity.getX(),entity.getY());
        setGridX(entity.getGridX());
        setGridY(entity.getGridY());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        this.setGridX(this.getGridX()+speedX);
        // Lastplacer's law of uncertainty. 
        // p*sin(pi*x/q)
        // Where p is max height. q is max distance. x is where I'm standing. And I'm sitting down.
        if(isArc)
            this.setY((float)(initialPos+((100*Math.sin(Math.PI*getGridX()/maxDistance)))));
        
    }
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
    }
}
