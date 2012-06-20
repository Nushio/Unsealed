/**
 * This file is part of Unsealed: Whispers of Wisdom
 * 
 *  Unsealed is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Unsealed is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with Unsealed.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.k3rnel.unsealed.actors;

import it.marteEngine.ME;
import it.marteEngine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 * Represents an LPC Sprite, from the Liberated Pixel Cup
 * @author Nushio
 *
 */
public class LPCActor extends Entity {

    public static final String NAME = "player";

    private static final int HEIGHT = 64;
    private static final int WIDTH = 64;

    public static final String STAND_DOWN = "stand_down";
    public static final String STAND_UP = "stand_up";
    public static final String STAND_RIGHT = "stand_right";
    public static final String STAND_LEFT = "stand_left";

    public Vector2f mySpeed = new Vector2f(2, 2);

    public boolean attacking = false;

    /**
     * 
     * @param x - Where it will be initially placed
     * @param y - Where it will be initially placed
     * @param filename - File location
     */
    public LPCActor(float x, float y, String filename) {
        super(x, y);

        // set id
        name = NAME;

        // load spriteSheet
        if (filename != null)
            setupAnimations(filename);

        // player rendered above everything
        depth = ME.Z_LEVEL_TOP;

        // define labels for the key
        defineControls();

        // define collision box and type
        //We made his hit box centered to his feet. 
        setHitBox(20, 48, 28, 16);
        addType(NAME);
        
    }

    /**
     * Binds WASD keys and arrows to Movement
     */
    private void defineControls() {
        bindToKey(ME.WALK_UP, Input.KEY_UP, Input.KEY_W);
        bindToKey(ME.WALK_DOWN, Input.KEY_DOWN, Input.KEY_S);
        bindToKey(ME.WALK_LEFT, Input.KEY_LEFT, Input.KEY_A);
        bindToKey(ME.WALK_RIGHT, Input.KEY_RIGHT, Input.KEY_D);
    }

    /**
     * Sets up animations of the spritesheets. 
     * @param filename
     */
    public void setupAnimations(String filename) {
        try {
            setGraphic(new SpriteSheet(filename, WIDTH, HEIGHT));
            duration = 150;
            addAnimation(STAND_UP, false, 0, 0);
            addAnimation(STAND_LEFT, false, 1, 0);
            addAnimation(STAND_DOWN, false, 2, 0);
            addAnimation(STAND_RIGHT, false, 3, 0);
            
            
            // Intentionally removed the starting "standing" position when walking
            addAnimation(ME.WALK_UP, true, 0, 1, 2, 3, 4, 5, 6, 7, 8);
            addAnimation(ME.WALK_LEFT, true, 1, 1, 2, 3, 4, 5, 6, 7, 8);
            addAnimation(ME.WALK_DOWN, true, 2, 1, 2, 3, 4, 5, 6, 7, 8);
            addAnimation(ME.WALK_RIGHT, true, 3, 1, 2, 3, 4, 5, 6, 7, 8);
            
            //Starting Animation
            setAnim(STAND_DOWN);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * What it does whenever the game's cycle passes through the update method. 
     */
    @Override
    public void update(GameContainer container, int delta)
            throws SlickException {
        super.update(container, delta);

        // movements
        updateMovements();
    }

    /**
     * Updates the on-screen character by moving him in the direction he moved. 
     */
    private void updateMovements() {
        boolean horizontalMovement = true;
        boolean verticalMovement = true;

        if (check(ME.WALK_UP)) {
            setAnim(ME.WALK_UP);

            moveUp();
        } else if (check(ME.WALK_DOWN)) {
            setAnim(ME.WALK_DOWN);

            moveDown();
        } else
            verticalMovement = false;

        if (check(ME.WALK_RIGHT)) {
            setAnim(ME.WALK_RIGHT);

            moveRight();
        } else if (check(ME.WALK_LEFT)) {
            setAnim(ME.WALK_LEFT);

            moveLeft();
        } else
            horizontalMovement = false;

        if (!horizontalMovement && !verticalMovement) {
            if (isCurrentAnim(ME.WALK_DOWN)) {
                setAnim(STAND_DOWN);
            } else if (isCurrentAnim(ME.WALK_UP)) {
                setAnim(STAND_UP);
            } else if (isCurrentAnim(ME.WALK_RIGHT)) {
                setAnim(STAND_RIGHT);
            } else if (isCurrentAnim(ME.WALK_LEFT)) {
                setAnim(STAND_LEFT);
            }
        }
    }

    public void moveLeft() {
        if (collide(SOLID, x - mySpeed.x, y) == null) {
            x -= mySpeed.x;
        }
    }

    public void moveRight() {
        if (collide(SOLID, x + mySpeed.x, y) == null) {
            x += mySpeed.x;
        }
    }

    public void moveDown() {
        if (collide(SOLID, x, y + mySpeed.y) == null) {
            y += mySpeed.y;
        }
    }

    public void moveUp() {
        if (collide(SOLID, x, y - mySpeed.y) == null) {
            y -= mySpeed.y;
        }
    }

    /**
     * Displays the character on-screen. 
     */
    @Override
    public void render(GameContainer container, Graphics g)
            throws SlickException {
        super.render(container, g);
    }

    public boolean isRightMoving() {
        return isCurrentAnim(ME.WALK_RIGHT);
    }

    public boolean isLeftMoving() {
        return isCurrentAnim(ME.WALK_LEFT);
    }

    public boolean isUpMoving() {
        return isCurrentAnim(ME.WALK_UP);
    }

    public boolean isDownMoving() {
        return isCurrentAnim(ME.WALK_DOWN);
    }

    public boolean isRightStanding() {
        return isCurrentAnim(LPCActor.STAND_RIGHT);
    }

    public boolean isLeftStanding() {
        return isCurrentAnim(LPCActor.STAND_LEFT);
    }

    public boolean isUpStanding() {
        return isCurrentAnim(LPCActor.STAND_UP);
    }

    public boolean isDownStanding() {
        return isCurrentAnim(LPCActor.STAND_DOWN);
    }


}
