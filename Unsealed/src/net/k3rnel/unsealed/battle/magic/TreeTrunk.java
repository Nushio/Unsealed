package net.k3rnel.unsealed.battle.magic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;

public class TreeTrunk extends MagicEntity {

    public TreeTrunk(TextureAtlas atlas, int x, int y,BattleEntity entity) {
        super(4.5f,0,entity);

        AtlasRegion atlasRegion = atlas.findRegion("battle/entities/treetrunk");
        TextureRegion[][] spriteSheet = atlasRegion.split(21,91);
        TextureRegion[] frames = new TextureRegion[8];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[0][2];
        frames[3] = spriteSheet[0][3];
        frames[4] = spriteSheet[0][4];
        frames[5] = spriteSheet[0][5];
        frames[6] = spriteSheet[0][6];
        frames[7] = spriteSheet[0][7];
        Animation animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        this.setState(BattleEntity.stateAttacking);
        this.setHeight(91);this.setWidth(21);
        offsetX = -13;
        offsetY = 15;
        this.setGridX(x, true);
        this.setGridY(y);
       

    }
    @Override
    public void act(float delta){
        stateTime+=delta;
        if(this.currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }
        this.setDrawable(new Image(this.currentAnimation.getKeyFrame(this.stateTime)).getDrawable());
        if(this.getState()==BattleEntity.stateAttacking){
            if(currentAnimation.isAnimationFinished(stateTime)){
                if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                    BattleEntity hero = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                    if(hero instanceof BattleHero){
                        if(hero.setHp(hero.getHp()-40)){
                        }else{
                        }
                    }
                }
                destroyMe=true;
            }
        }



    }
}


