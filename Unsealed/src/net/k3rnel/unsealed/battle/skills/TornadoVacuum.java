package net.k3rnel.unsealed.battle.skills;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.k3rnel.unsealed.battle.BattleEntity;


public class TornadoVacuum extends AbstractSkill {

     public TornadoVacuum(TextureAtlas atlas) {
        super(atlas);
        id = 4;
        manaCost = 5;
        this.setY(18);
        this.setX(7);
        stance = BattleEntity.stateAttacking;
        TextureRegion[][] spells =  atlasRegion.split(64,64);
        this.setDrawable(new Image(spells[1][1]).getDrawable());
    }
   
}
