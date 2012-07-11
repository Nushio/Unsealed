package net.k3rnel.unsealed.domain;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.OrderedMap;

public class Stats implements Serializable {
    
    public enum Type {FIRE, WATER, EARTH, AIR, NONE};
    private Type type;
    private int level;
    private int hp;
    private int attack;
    private int rapid;
    private int charge;
    private int defense;
    private int weight;
    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }
    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }
    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }
    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
    /**
     * @return the attack
     */
    public int getAttack() {
        return attack;
    }
    /**
     * @param attack the attack to set
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }
    /**
     * @return the rapid
     */
    public int getRapid() {
        return rapid;
    }
    /**
     * @param rapid the rapid to set
     */
    public void setRapid(int rapid) {
        this.rapid = rapid;
    }
    /**
     * @return the charge
     */
    public int getCharge() {
        return charge;
    }
    /**
     * @param charge the charge to set
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }
    /**
     * @return the defense
     */
    public int getDefense() {
        return defense;
    }
    /**
     * @param defense the defense to set
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }
    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }
    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    @Override
    public void read( Json json, OrderedMap<String,Object> jsonData ) {
        
    }

    @Override
    public void write(Json json ) {
        
    }

}
