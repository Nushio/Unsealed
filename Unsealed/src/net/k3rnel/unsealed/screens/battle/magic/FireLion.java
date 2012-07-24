package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.BattleEnemy;
import net.k3rnel.unsealed.screens.battle.BattleEntity;
import net.k3rnel.unsealed.screens.battle.BattleGrid;

public class FireLion extends MagicEntity {

    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public FireLion(TextureAtlas atlas, float speed, BattleEntity entity) {
        super(speed,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/firelion" );
        TextureRegion[][] spriteSheet = atlasRegion.split(64, 64);
        TextureRegion[] frames = new TextureRegion[1];
        frames[0] = spriteSheet[0][2];
        Animation animation = new Animation(1f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[5];
        frames[0] = spriteSheet[1][0];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[1][2];
        frames[3] = spriteSheet[2][2];
        frames[4] = spriteSheet[2][3];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);

        frames = new TextureRegion[4];
        frames[0] = spriteSheet[3][0];
        frames[1] = spriteSheet[3][1];
        frames[2] = spriteSheet[3][2];
        frames[3] = spriteSheet[3][3];
        animation = new Animation(0.5f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",animation);
        this.setState(BattleEntity.stateIdle);
        this.setGrid(entity.getGridX()+1,entity.getGridY());

        this.setHeight(64);this.setWidth(64);
        offsetX = (int)entity.getWidth();
        offsetY = -30;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getGridXInt() > 5){
            destroyMe=true;
        }else{
            if(this.getState()==BattleEntity.stateIdle){
                if(this.getGridXInt()<5){
                    if(BattleGrid.grid[this.getGridXInt()+1][this.getGridYInt()]!=null){
                        this.speedX = 0.1f;
                        this.setGridX(getGridX()+1);
                        this.setState(stateAttacking);
                    }
                }else{
                    destroyMe=true;  
                }
            }else if(this.getState()==stateAttacking){
                if(BattleGrid.grid[this.getGridXInt()][this.getGridYInt()]!=null){
                    BattleEntity entity = BattleGrid.grid[this.getGridXInt()][this.getGridYInt()];
                    if(entity instanceof BattleEnemy){
                        if(entity.getState() == BattleEntity.stateBlocking){
                            Gdx.app.log(Unsealed.LOG, "You hit me but it didn't hurt! Haha");
                            this.setState(stateAltAttacking);
                        }else{
                            if(entity.setHp(entity.getHp()-(int)maxDistance*10)){
                                entity.remove();
                                BattleGrid.unusedPositions.add(new Vector2(entity.getGridXInt(),entity.getGridYInt()));
                                BattleGrid.enemies.removeValue((BattleEnemy)entity,false);
                                BattleGrid.grid[this.getGridXInt()][this.getGridYInt()] = null;
                                BattleGrid.checkState();
                                if(this.getGridXInt()+1<5){
                                    if(BattleGrid.grid[this.getGridXInt()+1][this.getGridYInt()]==null)
                                        this.setState(stateAltAttacking);
                                }else
                                    this.setState(stateAltAttacking);
                            }else{
                                this.setState(stateAltAttacking);
                            }
                        }
                        maxDistance--;
                    }
                }
            }else{
                this.setGridX(this.getGridX()-speedX);
                if(BattleGrid.grid[this.getGridXInt()][this.getGridYInt()]!=null){
                    BattleEntity entity = BattleGrid.grid[this.getGridXInt()][this.getGridYInt()];
                    if(entity instanceof BattleEnemy){
                        if(entity.getState() == BattleEntity.stateBlocking){
                            Gdx.app.log(Unsealed.LOG, "You hit me but it didn't hurt! Haha");
                            maxDistance = entity.getHp()/10 - maxDistance;
                        }else{
                            if(entity.getHp()-maxDistance*10<0){
                                maxDistance = -1*((entity.getHp()-maxDistance*10)/10);
                            }else{
                                if(entity.setHp(entity.getHp()-(int)maxDistance*10)){
                                    entity.remove();
                                    BattleGrid.unusedPositions.add(new Vector2(entity.getGridXInt(),entity.getGridYInt()));
                                    BattleGrid.enemies.removeValue((BattleEnemy)entity,false);
                                    BattleGrid.grid[this.getGridXInt()][this.getGridYInt()] = null;
                                    BattleGrid.checkState();
                                    maxDistance = 0;
                                }
                            }
                        }
                    }
                }
                maxDistance--;
            }
            if(maxDistance<=0){
                destroyMe=true;
            }
        }
    }
}
