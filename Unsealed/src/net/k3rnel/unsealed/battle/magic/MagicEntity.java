package net.k3rnel.unsealed.battle.magic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.k3rnel.unsealed.battle.BattleEntity;

public class MagicEntity extends BattleEntity {
    
    public float speedX;
    public float speedY;
    public boolean isArc = false;
    public float initialPos = 0;
    public float maxDistance = 1;
    BattleEntity entity;
    public boolean destroyMe = false;
    
    public MagicEntity(float speedX, float speedY, BattleEntity entity) {
        super();
        this.speedX = speedX;
        this.speedY = speedY;
        this.entity = entity;
        setPosition(entity.getX(),entity.getY());
        setGridX(entity.getGridXInt(),false);
        setGridY(entity.getGridYInt());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        this.setX(this.getX()+speedX);
//        this.setGridX((this.getX()+1)*65+150 - this.getWidth() - offsetX);
        setGridX((this.getX()-200)/65,false);
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
