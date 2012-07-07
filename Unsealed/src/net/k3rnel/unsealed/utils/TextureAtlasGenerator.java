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
        settings.maxWidth = 512;
        settings.maxWidth = 512;

        // pack the images
        TexturePacker2.process( settings, INPUT_DIR, OUTPUT_DIR, PACK_FILE );
    }
}
