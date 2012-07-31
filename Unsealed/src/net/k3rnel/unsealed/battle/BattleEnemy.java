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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleEnemy extends BattleEntity {

    public int rounds = 0;
    public boolean hit = false;
    /**
     * Grid position of the enemy
     * @param x
     * @param y
     */
    public BattleEnemy(int hp, int x, int y) {
        super();
        setHp(hp);

        setGrid(x,y);       

    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);


    }
    @Override
    public void setState(int state) {
        super.setState(state);
        rounds = 0;
    }

}
