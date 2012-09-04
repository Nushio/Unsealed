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
package net.k3rnel.unsealed.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;


/**
 * Packs single images into image atlases.
 */
public class TextureAtlasGenerator {
    private static final String INPUT_DIR = "raw-resources/images";
    private static final String OUTPUT_DIR = "../Unsealed/assets/image-atlases";
    private static final String PACK_FILE = "pages-info";

    public static void main(String[] args ) {
        // create the packing's settings
        Settings settings = new Settings();

        // adjust the padding settings
        settings.paddingX = 0;
        settings.edgePadding = false;

        // set the maximum dimension of each image atlas
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;

        // pack the images
        TexturePacker2.process( settings, INPUT_DIR, OUTPUT_DIR, PACK_FILE );
    }
}
