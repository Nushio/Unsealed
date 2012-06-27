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
import org.newdawn.slick.tiled.Layer;
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
        map = new TiledMapPlus("res/maps/beach.tmx");
        setWidth(map.getWidth()*32);//32 is the TileSize. 
        setHeight(map.getHeight()*32);
        

        //Loads the Soldier Sprite
        player = new LPCActor(250, 300, "res/sprites/soldier.png");
        
        add(player);
        
        //Creates the Camera-Follow code. 
        
        player.mySpeed.set(playerSpeed.x, playerSpeed.y);
        camera.setSpeed(playerSpeed.x * 2, playerSpeed.y * 2);
        camera.follow(player, CameraFollowStyle.LOCKON);
    }
    
    /**
     * Listens to game input and moves the character
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        super.update(container, game, delta);
        //Grabs the player input, usable for moving the character
        Input input = container.getInput();
        scroll(input,delta);
            
    }
  
    /**
     * Handles the game input logic. In this case, the camera movement. 
     * @param input
     */
    
    private void scroll(Input input,int delta) {
        // Scroll by delta
        
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            camera.setPosition(delta, 0);
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            camera.setPosition(-delta, 0);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            camera.setPosition(0, -delta);
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            camera.setPosition(0, delta);
        }
        camera.follow(player, CameraFollowStyle.TOPDOWN_TIGHT);
    }

    /**
     * Renders the objects on screen. 
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {

        // determine cameraX and cameraY
        g.translate(-camera.getX(), -camera.getY());
        
        // Draw the map!
        drawLayers(camera.getX(), camera.getY(), 800, 600);
        
        // draw player at its real position
        g.translate(camera.getX(), camera.getY());

        super.render(container, game, g);
    }
    
  //Draws only the necessary tiles for a given area
    private void drawLayers(int x, int y, int w, int h ){
        int tileOffsetX = (-1*x%32);
        int tileOffsetY = (-1*y%32);
        int tileIndexX  = x/32;
        int tileIndexY  = y/32;
        map.render(x + tileOffsetX,y+ tileOffsetY, tileIndexX, tileIndexY,
                (w - tileOffsetX)/32 + 2,
                (h - tileOffsetY)/32 + 2,true);
    }
}
