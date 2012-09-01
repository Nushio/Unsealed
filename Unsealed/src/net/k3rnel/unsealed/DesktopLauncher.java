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
package net.k3rnel.unsealed;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * This class simply creates a desktop LWJGL application.
 */
public class DesktopLauncher {
    public static void main( String[] args ) {
        // create the listener that will receive the application events
        ApplicationListener listener = new Unsealed();

        // define the window's title
        String title = "Unsealed: Whispers of Wisdom (v1.1.1)";

        // define the window's size
        int width = 800, height = 480;

        // whether to use OpenGL ES 2.0
        boolean useOpenGLES2 = true;

        try{
            // create the game
            new LwjglApplication( listener, title, width, height, useOpenGLES2 );
        }catch(Exception e){
            useOpenGLES2 = false;
            try{
                new LwjglApplication( listener, title+" (OpenGL 1.0)", width, height, useOpenGLES2 );
            }catch(Exception ee){
                System.out.println("Sorry! Apparently you can't run this game. ");
            }
        }
    }
}
