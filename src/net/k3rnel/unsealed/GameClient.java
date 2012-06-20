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
package net.k3rnel.unsealed;

import it.marteEngine.ME;

import net.k3rnel.unsealed.worlds.GameWorld;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Game Client. 
 * Handles different Game States, like Menus, Options, Walking on Sunshine and Battling
 * @author Nushio
 *
 */
public class GameClient extends StateBasedGame {
    
    /**
     * Sets the Game Name
     */
    public GameClient() {
        super("Unsealed: Whispers of Wisdom");
    }

    /**
     * Initializes the States. Each "Window" we use is its own state. The Menu is a state, and so on. 
     */
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new GameWorld(0));
    }

    /**
     * Runs the actual game. 
     * @param argv
     */
    public static void main(String[] argv) {
        try {
            ME.keyToggleDebug = Input.KEY_1;
            AppGameContainer container = new AppGameContainer(
                    new GameClient());
            container.setDisplayMode(800, 600, false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
