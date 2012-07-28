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
