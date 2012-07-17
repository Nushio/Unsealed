package net.k3rnel.unsealed.objects;
import java.util.Comparator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class Map implements Disposable {
	private String name;
	private Stage stage;
	private TiledMap map;
	private TileAtlas tileAtlas;
	private TileMapRenderer renderer;
	private OrthographicCamera camera;
	private MapActorLayer actors;
	private int[] underLayers;
	private int[] overLayers;
	private MapListener listener;
	private MapActorComparator actorComparator;
	private boolean stageDirty;
	
	public Map(FileHandle mapFile, FileHandle atlasDir, float viewportWidth, float viewportHeight) {
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		stage = new Stage(viewportWidth, viewportHeight, false);
		stage.setCamera(camera);
		map = TiledLoader.createMap(mapFile);
		tileAtlas = new TileAtlas(map, atlasDir);
		renderer = new TileMapRenderer(map, tileAtlas, map.width, map.height);
		actors = new MapActorLayer();
		actorComparator = new MapActorComparator();
		stageDirty = false;
		loadObjects();
	}
	
	private void loadObjects() {
		for(TiledObjectGroup g: map.objectGroups) {
			Gdx.app.log("map", g.name);
		}
	}
	
	public void draw() {
		if(stageDirty) {
			stage.getActors().sort(actorComparator);
			stageDirty = false;
		}
		
		if(underLayers == null || overLayers == null) {
			renderer.render();
			stage.draw();
		} else {
			renderer.render(camera, underLayers);
			stage.draw();
			renderer.render(camera, overLayers);
		}
	}
	
	public void dispose() {
		clearActors();
		renderer.dispose();
		tileAtlas.dispose();
		stage.dispose();
	}
	
	public void act(float delta) {
		stage.act(delta);
	}
	
	public boolean addActor(MapActor actor) {
		if(actor.getMap() != null)
			return false;
		
		if(actors.add(actor)) {
			stage.addActor(actor);
			actor.setMap(this);
			
			return true;
		}
		
		return false;
	}
	
	public boolean checkMapCollision(int x, int y) {
		int tile = map.layers.get(5).tiles[y][x];
		String property = map.getTileProperty(tile, "collidable");
		boolean collidable = false;
		
		if(property != null)
			collidable = property.equals("true") ? true: false;
		
		return collidable;
	}
	
	public MapActor checkActorCollision(MapActor actor, int x, int y) {
		return actors.checkCollision(actor, x, y);
	}
	
	public LinkedList<MapActor> getActors(int x, int y) {
		return actors.getAll(x, y);
	}
	
	public MapActor getFirstActor(int x, int y) {
		return actors.getFirst(x, y);
	}
	
	public void removeActor(MapActor actor) {
		actors.remove(actor);
		actor.remove();
		actor.setMap(null);
	}
	
	public void clearActors() {
		actors.clear();
	}
	
	public boolean moveActor(MapActor actor, int tileX, int tileY) {
		if(actor.getMap() != this)
			return false;
		
		if(tileX < 0 || tileX >= map.width || tileY < 0 || tileY >= map.height)
			return false;
		
		MapActor collideActor = checkActorCollision(actor, tileX, tileY);
		
		if(collideActor != null) {
			if(listener != null)
				listener.collided(actor, collideActor);
			
			return false;
		}
		
		if(!checkMapCollision(tileX, tileY) || !actor.getMapCollidable()) {
			actor.setTilePos(tileX, tileY);
			stageDirty = true;
			
			for(MapActor overlapActor: actors.getAll(actor.getTileX(), actor.getTileY())) {
				if(listener != null && overlapActor != actor)
					listener.overlapped(actor, overlapActor);
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean warpActor(MapActor actor, int tileX, int tileY) {
		if(moveActor(actor, tileX, tileY)) {
			actor.setPosition(tileX * map.tileWidth, getMapHeightUnits() - tileY * map.tileHeight - map.tileHeight);
			return true;
		}
		
		return false;
	}
	
	public int getWidth() {
		return map.width;
	}
	
	public int getHeight() {
		return map.height;
	}
	
	public int getTileWidth() {
		return map.tileWidth;
	}
	
	public int getTileHeight() {
		return map.tileHeight;
	}
	
	public int getMapWidthUnits() {
		return renderer.getMapWidthUnits();
	}
	
	public int getMapHeightUnits() {
		return renderer.getMapHeightUnits();
	}
	
	public int[] getUnderLayers() {
		return underLayers;
	}

	public void setUnderLayers(int[] underLayers) {
		this.underLayers = underLayers;
	}

	public int[] getOverLayers() {
		return overLayers;
	}

	public void setOverLayers(int[] overLayers) {
		this.overLayers = overLayers;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
		this.stage.setCamera(camera);
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public void screenToMapCoordinates(Vector2 pos) {
		stage.screenToStageCoordinates(pos);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MapListener getMapListener() {
		return listener;
	}

	public void setMapListener(MapListener listener) {
		this.listener = listener;
	}
	
	private static class MapActorComparator implements Comparator<Actor> {
		@Override
		public int compare(Actor arg0, Actor arg1) {
			MapActor actor0 = (MapActor) arg0;
			MapActor actor1 = (MapActor) arg1;
			return (int) (actor0.getTileY() - actor1.getTileY());
		}
		
	}
}