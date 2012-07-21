package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.BattleEntity;
import net.k3rnel.unsealed.screens.battle.BattleHero;

public class PeaDart extends MagicEntity {
    
    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public PeaDart(TextureAtlas atlas, BattleEntity entity) {
        super(-1,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/fireball" );
        TextureRegion[][] spriteSheet = atlasRegion.split(34, 25);
        TextureRegion[] frames = new TextureRegion[3];
        frames[0] = spriteSheet[3][0];
        frames[1] = spriteSheet[3][1];
        frames[2] = spriteSheet[3][2];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);
        this.setGrid(entity.getGridX()+1,entity.getGridY());

        this.setHeight(30);this.setWidth(30);
        offsetX = (int)entity.getWidth();
        offsetY = 30;
    }
    
    @Override
    public void act(float delta) {
        // TODO Auto-generated method stub
        super.act(delta);
        if(this.getGridX()<0)
            this.setVisible(false);
    }
    @Override
    public boolean action(Array<BattleHero> heroes, float delta) {
        for(BattleHero hero : heroes){
            if(hero.getGridY() == this.getGridY() && hero.getGridX() == this.getGridX()){
                Gdx.app.log(Unsealed.LOG,"SMACK!");
                if(hero.getState()==BattleEntity.stateBlocking){
                    hero.setHp(hero.getHp()-5);
                }else{
                    if( hero.setHp(hero.getHp()-10)){
                        Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                        hero.setHp(0);
                    }
                }
                this.setVisible(false);
            }
        }
        return false;
    }
}