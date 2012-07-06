package net.k3rnel.unsealed.domain;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.OrderedMap;

/**
 * The player's profile.
 * <p>
 * This class is used to store the game progress, and is persisted to the file
 * system when the game exists.
 * 
 * @see ProfileManager
 */
public class Profile implements Serializable {

    public Profile() {

    }

    @Override
    public void read(Json json, OrderedMap<String,Object> jsonData){

    }

    @Override
    public void write(Json json) {

    }
}
