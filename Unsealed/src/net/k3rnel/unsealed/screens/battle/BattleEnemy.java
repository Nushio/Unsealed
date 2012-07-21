package net.k3rnel.unsealed.screens.battle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleEnemy extends BattleEntity {

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

    public void idle(){

    }

}
