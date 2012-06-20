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
package net.k3rnel.unsealed.worlds;

import net.k3rnel.unsealed.actors.LPCActor;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMapPlus;

import it.marteEngine.CameraFollowStyle;
import it.marteEngine.World;

/**
 * Displays the Map on screen as well as the character. 
 * @author Nushio
 *
 */
public class GameWorld extends World {
    private Vector2f playerSpeed;
    private LPCActor player;
    TiledMapPlus map;
    
   
    /**
     * Creates a new GamePlay object
     * @param id
     */
    public GameWorld(int id) {
        super(id);
        playerSpeed = new Vector2f(2, 2);
    }
    
    /**
     * Initializes the GamePlay. First called when the game launches. 
     */
    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        super.init(container, game);
        //Loads the beach map
        map = new TiledMapPlus("res/maps/0.0.tmx");
        setWidth(map.getWidth()*32);//32 is the TileSize. 
        setHeight(map.getHeight()*32);
        

        player = new LPCActor(250, 300, "res/sprites/soldier.png");
        
        add(player);
        
        //Creates the Camera-Follow code. 
        camera.follow(player, CameraFollowStyle.TOPDOWN);
        player.mySpeed.set(playerSpeed.x, playerSpeed.y);
        camera.setSpeed(playerSpeed.x * 2, playerSpeed.y * 2);
    }
    
    /**
     * Listens to game input and moves the character
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        super.update(container, game, delta);
        Input input = container.getInput();
        scroll(input);
            
    }
  
    /**
     * Handles the game input logic. In this case, the camera movement. 
     * @param input
     */
    
    private void scroll(Input input) {
        // Scroll 30 pixels
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            camera.scroll(30, 0);
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            camera.scroll(-30, 0);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            camera.scroll(0, -30);
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            camera.scroll(0, 30);
        }
    }

    /**
     * Renders the objects on screen. 
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        map.render(-camera.getX(), -camera.getY());
        super.render(container, game, g);
    }
}
