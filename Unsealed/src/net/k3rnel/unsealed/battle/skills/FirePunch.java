package net.k3rnel.unsealed.battle.skills;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.k3rnel.unsealed.battle.BattleEntity;


public class FirePunch extends AbstractSkill {

     public FirePunch(TextureAtlas atlas) {
        super(atlas);
        id = 1;
        manaCost=5;
        stance = BattleEntity.stateAltAttacking;
        TextureRegion[][] spells =  atlasRegion.split(64,64);
        this.setDrawable(new Image(spells[2][0]).getDrawable());
    }
   
}
