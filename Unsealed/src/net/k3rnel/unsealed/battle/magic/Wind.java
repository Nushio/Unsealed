package net.k3rnel.unsealed.battle.magic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;

public class Wind extends MagicEntity {

    public Wind(TextureAtlas atlas, BattleEntity entity) {
        super(4.5f,0,entity);

        AtlasRegion atlasRegion = atlas.findRegion("battle/entities/tornado");
        TextureRegion[][] spriteSheet = atlasRegion.split(64,64);
        TextureRegion[] frames = new TextureRegion[8];
        frames[0] = spriteSheet[1][0];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[1][2];
        frames[3] = spriteSheet[1][3];
        frames[4] = spriteSheet[2][0];
        frames[5] = spriteSheet[2][1];
        frames[6] = spriteSheet[2][2];
        frames[7] = spriteSheet[2][3];
        Animation animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.LOOP_PINGPONG);
        this.animations.put("attacking",animation);
        this.setState(BattleEntity.stateAttacking);
        this.setHeight(64);this.setWidth(64);
        offsetX = -(int)entity.getWidth()/2;
        offsetY = 10;
        this.setGridY(entity.getGridY());
        this.setX(entity.getX()+offsetX);

    }
    @Override
    public void act(float delta){
        super.act(delta);
        if(this.getX() > 550){
            destroyMe=true;
        }else{
            if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                BattleEntity enemy = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                if(enemy instanceof BattleEnemy){
                    if(enemy.getState() == BattleEntity.stateBlocking){
                        if(enemy.setHp(enemy.getHp()-10)){

                        }else{
                            enemy.setState(BattleEntity.stateIdle);
                            if(enemy.getGridXInt()-1>=3){
                                if(BattleGrid.checkGrid(enemy.getGridXInt()-1,enemy.getGridYInt())==null){
                                    BattleGrid.moveEntity(enemy, enemy.getGridXInt()-1, enemy.getGridYInt());
                                }
                            }
                        }
                    }else{
                        if(enemy.setHp(enemy.getHp()-20)){

                        }else{
                            enemy.setState(BattleEntity.stateIdle);
                            if(enemy.getGridXInt()-1>=3){
                                if(BattleGrid.checkGrid(enemy.getGridXInt()-1,enemy.getGridYInt())==null){
                                    BattleGrid.moveEntity(enemy, enemy.getGridXInt()-1, enemy.getGridYInt());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


