package net.k3rnel.unsealed.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

/**
 * Packs single images into image atlases.
 */
public class TyrianTexturePacker
{
    private static final String INPUT_DIR = "raw-resources/images";
    private static final String OUTPUT_DIR = "../Unsealed/assets/image-atlases";
    private static final String PACK_FILE = "pages-info";

    public static void main(
        String[] args )
    {
        // create the packing's settings
        Settings settings = new Settings();

        // adjust the padding settings
        settings.padding = 2;
        settings.edgePadding = false;

        // set the maximum dimension of each image atlas
        settings.maxWidth = 512;
        settings.maxWidth = 512;

        // don't repack a group when no changes were made to it
        settings.incremental = true;

        // pack the images
        TexturePacker.process( settings, INPUT_DIR, OUTPUT_DIR, PACK_FILE );
    }
}
