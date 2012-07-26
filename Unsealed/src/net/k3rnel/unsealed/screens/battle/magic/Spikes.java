package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.screens.battle.BattleEnemy;
import net.k3rnel.unsealed.screens.battle.BattleEntity;
import net.k3rnel.unsealed.screens.battle.BattleGrid;

public class Spikes extends MagicEntity {

    public Spikes(TextureAtlas atlas,int type, BattleEntity entity) {
        super(0.02f,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion("battle/entities/spikes");
        TextureRegion[][] spriteSheet = atlasRegion.split(64,64);
        TextureRegion[] frames = new TextureRegion[9];
        frames[0] = spriteSheet[type][0];
        frames[1] = spriteSheet[type][1];
        frames[2] = spriteSheet[type][2];
        frames[3] = spriteSheet[type][3];
        frames[4] = spriteSheet[type][4];
        frames[5] = spriteSheet[type][5];
        frames[6] = spriteSheet[type][6];
        frames[7] = spriteSheet[type][7];
        frames[8] = spriteSheet[type][8];
        Animation animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.LOOP_PINGPONG);
        this.animations.put("attacking",animation);
        this.setState(BattleEntity.stateAttacking);
        this.setHeight(64);this.setWidth(64);
        offsetX = -(int)entity.getWidth();
        offsetY = 0;
        this.setGrid(entity.getGridX(),entity.getGridY());

    }
    @Override
    public void act(float delta){
        super.act(delta);
        if(this.getX() > 650){
            destroyMe=true;
        }else{
            if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                BattleEntity enemy = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                if(enemy instanceof BattleEnemy){
                    if(enemy.getState() == BattleEntity.stateBlocking){
                        enemy.setState(BattleEntity.stateIdle);
                        if(enemy.setHp(enemy.getHp()-10)){
                            enemy.remove();
                            BattleGrid.assignOnGrid(enemy.getGridXInt(),enemy.getGridYInt(),null);
                            BattleGrid.checkState();
                            destroyMe=true;
                        }else{
                            destroyMe=true;
                        }
                    }else if(enemy.setHp(enemy.getHp()-30)){
                        enemy.remove();
                        BattleGrid.assignOnGrid(enemy.getGridXInt(),enemy.getGridYInt(),null);
                        BattleGrid.checkState();
                        destroyMe=true;
                    }else{
                        destroyMe=true;
                    }
                    destroyMe=true;
                }
            }
        }
    }

}

