/**
 * Unsealed: Whispers of Wisdom. 
 * 
 * Copyright (C) 2012 - Juan 'Nushio' Rodriguez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 of 
 * the License as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package net.k3rnel.unsealed.battle;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;


import java.util.ArrayList;
import java.util.List;

import net.k3rnel.unsealed.battle.magic.MagicEntity;
import net.k3rnel.unsealed.battle.magic.Shield;
import net.k3rnel.unsealed.battle.skills.AbstractSkill;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class BattleHero extends BattleEntity {

    protected int mana;

    protected Shield shield;

    public List<MagicEntity> magics;
    protected MagicEntity tmpMagic;
    public int magicType;

    protected TextureAtlas atlas;

    private AbstractSkill skill1;
    private AbstractSkill skill2;
    private AbstractSkill skill3;
    private AbstractSkill skill4;
    private AbstractSkill skill5;
    private AbstractSkill skill6;
    private AbstractSkill tmpSkill;

    protected boolean hit = false;

    public BattleHero(TextureAtlas atlas, int hp, int x, int y) {
        this.atlas = atlas;
        magics = new ArrayList<MagicEntity>();
      

        setHp(hp);
        mana = 0;
        setGridX(x,false);
        setGridY(y); 
    }

    @Override
    public void act(float delta) {
        super.act(delta);

              

    }
    public void reset(){
        this.getActions().clear();
        this.actions = sequence( color(Color.WHITE) );
        this.addAction(actions);
        this.setStatus(BattleEntity.statusNormal);
        this.setState(BattleEntity.stateIdle);
        magics = new ArrayList<MagicEntity>();
    }
   

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(int i = 0; i< magics.size(); i++){
            magics.get(i).draw(batch,parentAlpha);
        }       
        if(this.getState()==BattleEntity.stateBlocking)
            shield.draw(batch, parentAlpha);
    }

    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }
    /**
     * @param mana the mana to set
     */
    public void setMana(int mana) {
        this.mana = mana;
        if(this.mana<0)
            this.mana = 0;
        if(this.mana > 30)
            this.mana = 30;
    }
  
    /**
     * @return the skill1
     */
    public AbstractSkill getSkill1() {
        return skill1;
    }

    /**
     * @param skill1 the skill1 to set
     */
    public void setSkill1(AbstractSkill skill1) {
        this.skill1 = skill1;
    }

    /**
     * @return the skill2
     */
    public AbstractSkill getSkill2() {
        return skill2;
    }

    /**
     * @param skill2 the skill2 to set
     */
    public void setSkill2(AbstractSkill skill2) {
        this.skill2 = skill2;
    }

    /**
     * @return the skill3
     */
    public AbstractSkill getSkill3() {
        return skill3;
    }

    /**
     * @param skill3 the skill3 to set
     */
    public void setSkill3(AbstractSkill skill3) {
        this.skill3 = skill3;
    }

    /**
     * @return the skill4
     */
    public AbstractSkill getSkill4() {
        return skill4;
    }

    /**
     * @param skill4 the skill4 to set
     */
    public void setSkill4(AbstractSkill skill4) {
        this.skill4 = skill4;
    }

    /**
     * @return the skill5
     */
    public AbstractSkill getSkill5() {
        return skill5;
    }

    /**
     * @param skill5 the skill5 to set
     */
    public void setSkill5(AbstractSkill skill5) {
        this.skill5 = skill5;
    }

    /**
     * @return the skill6
     */
    public AbstractSkill getSkill6() {
        return skill6;
    }

    /**
     * @param skill6 the skill6 to set
     */
    public void setSkill6(AbstractSkill skill6) {
        this.skill6 = skill6;
    }

    public void skillSwitch() {

        tmpSkill = skill1;
        skill1 = skill4;
        skill4 = tmpSkill;

        tmpSkill = skill2;
        skill2 = skill5;
        skill5 = tmpSkill;

        tmpSkill = skill3;
        skill3 = skill6;
        skill6 = tmpSkill;

    }    
}
