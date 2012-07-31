package net.k3rnel.unsealed.battle.skills;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.k3rnel.unsealed.battle.BattleEntity;


public class ThunderClaw extends AbstractSkill {

     public ThunderClaw(TextureAtlas atlas) {
        super(atlas);
        id = 6;
        manaCost = 5;
        this.setY(18);
        this.setX(5);
        stance = BattleEntity.stateAttacking;
        TextureRegion[][] spells =  atlasRegion.split(64,64);
        this.setDrawable(new Image(spells[0][3]).getDrawable());
    }
   
}
