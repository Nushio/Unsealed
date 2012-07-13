package net.k3rnel.unsealed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

import net.k3rnel.unsealed.Unsealed;

public class OverworldScreen extends AbstractScreen {

    TileMapRenderer tileMapRenderer;
    private OrthographicCamera camera;
    TiledMap map;
    TileAtlas atlas;
    
    public OverworldScreen(Unsealed game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        

        // Load the tmx file into map
        map = TiledLoader.createMap(Gdx.files.internal("assets/map-atlases/DungeonTest.tmx"));

        // Load the tiles into atlas
        atlas = new TileAtlas(map, Gdx.files.internal("assets/map-atlases/"));

        // Create the renderer
        tileMapRenderer = new TileMapRenderer(map, atlas, map.width, map.height);

        // Create the camera
        camera = new OrthographicCamera(MENU_VIEWPORT_WIDTH,MENU_VIEWPORT_HEIGHT);
        
        stage.setCamera(camera);
        
    }
    
    @Override
    public void render(float delta){
        super.render(delta);
       
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        tileMapRenderer.render(camera);
    }
    
    @Override
    public void dispose() {
        
    }
}
