package net.k3rnel.unsealed.battle.magic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;

public class Blast extends MagicEntity {

    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public Blast(TextureAtlas atlas, int color, float speedX, BattleEntity entity) {
        super(speedX,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/fireball" );
        TextureRegion[][] spriteSheet = atlasRegion.split(34, 25);
        TextureRegion[] frames = new TextureRegion[3];
        frames[0] = spriteSheet[color][0];
        frames[1] = spriteSheet[color][1];
        frames[2] = spriteSheet[color][2];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);
        this.setGrid(entity.getGridXInt()+1,entity.getGridYInt());
        this.entity = entity;
        this.setHeight(30);this.setWidth(30);
        offsetX = -(int)entity.getWidth();
        offsetY = 30;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getX() > 550){
            destroyMe=true;
        }else{
            if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                BattleEntity entity = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                if(entity instanceof BattleEnemy){
                    if(entity.getState() == BattleEntity.stateBlocking){
                        Gdx.app.log(Unsealed.LOG, "You hit me but it didn't hurt! Haha");
                    }else{
                        if(entity.setHp(entity.getHp()-10)){
                            BattleGrid.enemies.removeValue((BattleEnemy)entity,false);
                            BattleGrid.clearGrid(this.getGridXInt(),this.getGridYInt());
                            BattleGrid.checkState();
                        }
                    }
                    destroyMe=true;
                }
            }
        }
    }
}
