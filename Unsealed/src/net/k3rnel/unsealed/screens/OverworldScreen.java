package net.k3rnel.unsealed.screens;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.objects.Map;
import net.k3rnel.unsealed.objects.MapActor;
import net.k3rnel.unsealed.objects.MapCharacter;
import net.k3rnel.unsealed.utils.MapLoader;

public class OverworldScreen extends AbstractScreen {
	
	public static final int stateLoading = 0;	
	public static final int stateWalk = 1;	
	public static final int stateDialog = 2;

    TileMapRenderer tileMapRenderer;
    private OrthographicCamera camera;
    TiledMap map;
    TileAtlas atlas;
    
	private int state;
    
    private MapCharacter player;
    private String mapFileName = "";
    private Map gameMap;
    private int spawnX, spawnY;
    
    public OverworldScreen(Unsealed game) {
        super(game);
        
        //TODO: Load Player from last save game/take gender into account.
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
    
    public MapCharacter createPlayer() {
		//Initialize the map which contains the animations for each animation type.
		HashMap<String, Animation> animations = new HashMap<String, Animation>();
		
        // Get our character images to create animations.
        TextureRegion[][] tmp = this.getAtlas().findRegion("char-sprites/male_spritesheet").split(64, 64);
        
        //Create our animations
        Animation standUp = new Animation(1f, new TextureRegion[]{ tmp[0][0] });
        Animation standLeft = new Animation(1f, new TextureRegion[]{ tmp[1][0] });
        Animation standDown = new Animation(1f, new TextureRegion[]{ tmp[2][0] });
        Animation standRight = new Animation(1f, new TextureRegion[]{ tmp[3][0] });
        
        Animation walkUp = new Animation(1.5f, new TextureRegion[]{ tmp[0][1], tmp[0][2], tmp[0][3], tmp[0][4], tmp[0][5], tmp[0][6], tmp[0][7], tmp[0][8] });
        Animation walkLeft = new Animation(1.5f, new TextureRegion[]{ tmp[1][1], tmp[1][2], tmp[1][3], tmp[1][4], tmp[1][5], tmp[1][6], tmp[1][7], tmp[1][8] });
        Animation walkDown = new Animation(1.5f, new TextureRegion[]{ tmp[2][1], tmp[2][2], tmp[2][3], tmp[2][4], tmp[2][5], tmp[2][6], tmp[2][7], tmp[2][8] });
        Animation walkRight = new Animation(1.5f, new TextureRegion[]{ tmp[3][1], tmp[3][2], tmp[3][3], tmp[3][4], tmp[3][5], tmp[3][6], tmp[3][7], tmp[3][8] });
        
        //Set the playmode
        standUp.setPlayMode(Animation.LOOP_PINGPONG);
        standLeft.setPlayMode(Animation.LOOP_PINGPONG);
        standDown.setPlayMode(Animation.LOOP_PINGPONG);
        standRight.setPlayMode(Animation.LOOP_PINGPONG);
        
        walkUp.setPlayMode(Animation.LOOP_PINGPONG);
        walkLeft.setPlayMode(Animation.LOOP_PINGPONG);
        walkDown.setPlayMode(Animation.LOOP_PINGPONG);
        walkRight.setPlayMode(Animation.LOOP_PINGPONG);
        
        //Fill our map containing all animations with the animations.
        animations.put("stand_up", standUp);
        animations.put("stand_left", standLeft);
        animations.put("stand_down", standDown);
        animations.put("stand_right", standRight);
        
        animations.put("walk_up", walkUp);
        animations.put("walk_left", walkLeft);
        animations.put("walk_down", walkDown);
        animations.put("walk_right", walkRight);
        
        //Create the player and make him collide
        MapCharacter player = new MapCharacter(animations);
        
        player.setGroup(MapActor.groupPlayer);
        player.setCollisionGroup(MapActor.groupNPC);
        
        return player;
    }
    
    public void loadMap(String fileName, int spawnX, int spawnY) {
    	float width = Gdx.graphics.getWidth();
    	float height = Gdx.graphics.getHeight();
    	
    	mapFileName = fileName;
    	
    	spawnX = spawnX;
    	spawnY = spawnY;
    	
    	state = stateLoading;
    	
    	//TODO: Check if path is correct
    	Unsealed.getInstance().getAssetManager().load(fileName, Map.class, new MapLoader.MapParameter("raw-resources/maps", width, height));
    }
    
    public void showMap() {
    	
    }
}