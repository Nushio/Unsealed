package net.k3rnel.unsealed.objects;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The base class for all map objects like players, NPCs and whatever else we want
 * to place on the map.
 * 
 * @author Drawig
 */
public abstract class MapObject extends Actor {
	
	/** The x-position of this map object on the map. */
	protected int mapX;
	/** The y-position of this map object on the map. */
	protected int mapY;
	/** The name of the map this map object is on. */
	protected String mapName;
	
	/**
	 * Constructs a new MapObject which is on the given map
	 * on the given position.
	 * 
	 * @param mapX The x-position of this map object on the map.
	 * @param mapY The y-position of this map object on the map.
	 * @param mapName The name of the map this map object is on.
	 */
	public MapObject(int mapX, int mapY, String mapName) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.mapName = mapName;
	}
	
	/**
	 * Retrieves the x-position of this map object on the map.
	 * 
	 * @return The x-position of this map object on the map.
	 */
	public int getMapX() {
		return mapX;
	}
	
	/**
	 * Retrieves the y-position of this map object on the map.
	 * 
	 * @return The y-position of this map object on the map.
	 */	
	public int getMapY() {
		return mapY;
	}
	
	/**
	 * Retrieves the name of the map this map object is on.
	 * 
	 * @return The name of the map this map object is on.
	 */
	public String getMapName() {
		return mapName;
	}
}
