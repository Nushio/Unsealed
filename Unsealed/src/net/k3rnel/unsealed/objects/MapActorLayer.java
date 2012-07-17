package net.k3rnel.unsealed.objects;

import java.util.LinkedList;

public class MapActorLayer {
	
	private LinkedList<MapActor> actors;
	
	public MapActorLayer() {
		this.actors = new LinkedList<MapActor>();
	}

	public MapActor checkCollision(MapActor actor, int x, int y) {
		for(MapActor other: actors) {
			if(actor == other)
				continue;
			
			if(x == other.getTileX() &&
			   y == other.getTileY() && 
			   (actor.getGroup() & other.getCollisionGroup()) >= 1) {
				return actor;
			}
		}
		
		return null;
	}
	
	public LinkedList<MapActor> getAll(int x, int y) {
		LinkedList<MapActor> list = new LinkedList<MapActor>();
		
		for(MapActor actor: actors) {
			if(actor.getTileX() == x && actor.getTileY() == y)
				list.add(actor);
		}
		
		return list;
	}
	
	public MapActor getFirst(int x, int y) {
		for(MapActor actor: actors) {
			if(actor.getTileX() == x && actor.getTileY() == y)
				return actor;
		}
		
		return null;
	}
	
	public boolean add(MapActor actor) {
		// Don't add duplicates
		if(actors.contains(actor))
			return false;
		
		actors.add(actor);
		
		return true;
	}
	
	public void remove(MapActor actor) {
		actors.remove(actor);
	}
	
	public void clear() {
		for(MapActor actor: actors)
			actor.setMap(null);
				
		actors.clear();
	}
}