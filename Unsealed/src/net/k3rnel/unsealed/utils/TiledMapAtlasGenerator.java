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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.tiledmappacker.TiledMapPacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

/**
 * Packs tiled maps into atlases.
 * Fair warning: I have issues running this program in 64Bit Java on Linux. 
 * Your mileage may vary. 
 */

@SuppressWarnings("deprecation")
public class TiledMapAtlasGenerator {
    private static final String INPUT_DIR = "raw-resources/maps";
    private static final String OUTPUT_DIR = "assets/map-atlases/";

    public static void main(String[] args ) {
        // create the packing's settings
        Settings settings = new Settings();
        settings.maxHeight = 1024;
        settings.maxWidth = 1024;
        
        //Creates temp file variables
        File inputDir = new File(INPUT_DIR);
        File outputDir = new File(OUTPUT_DIR);
        try {
            //We delete the old files, because otherwise, we'd get cluttered with pngs
            deleteRecursive(outputDir);
            //We remake the folders
            outputDir.mkdirs();
            //We pack the tiles in a warm little blanket.
            new TiledMapPacker().processMap(inputDir,  outputDir, settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Taken from ApacheCommons. Licensed APL 2.0. 
     * No need to import an entire jar for just this method
     * 
     * By default File#delete fails for non-empty directories, it works like "rm". 
     * We need something a little more brutual - this does the equivalent of "rm -r"
     * @param path Root File Path
     * @return true iff the file and all sub files/directories have been removed
     * @throws FileNotFoundException
     */
    public static boolean deleteRecursive(File path) throws FileNotFoundException{
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
                ret = ret && deleteRecursive(f);
            }
        }
        return ret && path.delete();
    }
}
