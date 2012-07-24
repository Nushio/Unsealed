package net.k3rnel.unsealed.objects;

public class WarpTile extends MapActor {
	
	private String mapFile;
	private int spawnX, spawnY;
	
	public WarpTile() {
		this.mapFile = null;
		this.spawnX = 0;
		this.spawnY = 0;
	}

	public String getMapFile() {
		return mapFile;
	}

	public void setMapFile(String mapFile) {
		this.mapFile = mapFile;
	}
	
	public int getSpawnX() {
		return spawnX;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}

	public void setWarp(int spawnX, int spawnY) {
		this.spawnX = spawnX;
		this.spawnY = spawnY;
	}
}