package net.k3rnel.unsealed.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.jogl.JoglApplication;
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

        //We need this so that libgdx can use the Gdx.app methods. 
        JoglApplication jogl = new JoglApplication(new ApplicationListener() {
            @Override
            public void create() {
            }

            @Override
            public void dispose() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void render() {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void resume() {
            }
        }, "", 0, 0, false);
        
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
        //We exit the program. Duh.
        jogl.exit();
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
