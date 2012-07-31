package net.k3rnel.unsealed.battle.magic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;

public class TreeRoot extends MagicEntity {
    
    public TreeRoot(TextureAtlas atlas, float speed, BattleEntity entity) {
        super(speed,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/treeroot" );
        TextureRegion[][] spriteSheet = atlasRegion.split(88, 88);
        TextureRegion[] frames = new TextureRegion[8];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[0][2];
        frames[3] = spriteSheet[0][3];
        frames[4] = spriteSheet[0][4];
        frames[5] = spriteSheet[0][5];
        frames[6] = spriteSheet[0][6];
        frames[7] = spriteSheet[0][7];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);

        this.setHeight(88);this.setWidth(88);
        offsetX = (int)entity.getWidth()-320;
        offsetY = 30;

        this.setGrid(entity.getGridXInt()-1,entity.getGridYInt());
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getGridX()<0)
            destroyMe=true;
        for(BattleHero hero : BattleGrid.heroes){
            if(hero.getGridYInt() == this.getGridYInt() && hero.getGridXInt() == this.getGridXInt()){
                Gdx.app.log(Unsealed.LOG,"SMACK!");
                if(hero.getState()==BattleEntity.stateBlocking){
                    hero.setHp(hero.getHp()-30);
                }else{
                    if( hero.setHp(hero.getHp()-30)){
                        Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                        hero.setHp(0);
                    }else{
                        hero.setStatus(BattleEntity.statusPoisoned);
                    }
                }
                destroyMe=true;
            }
        }
    }
}
