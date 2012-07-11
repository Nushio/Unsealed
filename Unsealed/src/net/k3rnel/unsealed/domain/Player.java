package net.k3rnel.unsealed.domain;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.OrderedMap;

public class Player implements Serializable {
    
    private int character;
    private String name;
    private Stats stats;
    
    
    public Player() {
        
    }
    
    /**
     * @return the character
     */
    public int getCharacter() {
        return character;
    }

    /**
     * @param character the character to set
     */
    public void setCharacter(int character) {
        this.character = character;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public void read( Json json, OrderedMap<String,Object> jsonData ) {
      
    }

    @Override
    public void write(Json json ) {
        
    }

}
