/**
 * Unsealed: Whispers of Wisdom. 
 * 
 * Copyright (C) 2012 - Juan 'Nushio' Rodriguez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 of 
 * the License as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package net.k3rnel.unsealed.battle;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.enemies.Bee;
import net.k3rnel.unsealed.battle.enemies.Clam;
import net.k3rnel.unsealed.battle.enemies.Dummy;
import net.k3rnel.unsealed.battle.enemies.Ghost;
import net.k3rnel.unsealed.battle.enemies.Snake;
import net.k3rnel.unsealed.battle.enemies.Terrex;
import net.k3rnel.unsealed.screens.BattleScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class BattleGrid extends Stage {

    public final static int battleSetup = 0;
    public final static int battleStarted = 1;
    public final static int battleWon = 2;
    public final static int battleLost = 3;

    private float width, height;

    protected TextureAtlas atlas;


    public static int sizeX;
    public static int sizeY;

    private static BattleEntity[][] grid;

    public static Array<BattleEnemy> enemies;
    public static Array<BattleHero> heroes;

    private static List<Vector2> unusedPositions;
    public static int battleState;

    private BattleEnemy tmpBattleEnemy;
    private BattleHero tmpBattleHero;
    Vector2 tmpSpawnPoint;
    public static Timer timer;

    public static Random random;

    private OrthographicCamera cam;


    public BattleGrid(TextureAtlas atlas, float width, float height, int sizeX, int sizeY) {
        this.width = width;
        this.height = height;
        this.atlas = atlas;
        setViewport(this.width, this.height, true);
        timer = new Timer();
        BattleGrid.sizeX = sizeX;
        BattleGrid.sizeY = sizeY;
        grid = new BattleEntity[sizeX][sizeY];
        random = new Random(System.currentTimeMillis());
        heroes = new Array<BattleHero>(3);
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
        cam = new OrthographicCamera(this.width, this.height);            
        cam.position.set(this.width / 2, this.height / 2, 0);
        cam.zoom = 1f;
        this.setCamera(cam);

    }

    public static void clearGrid(int x, int y){
        if(x<sizeX&&x>=0&&y<sizeY&&y>=0){
            grid[x][y] = null;
        }
    }

    public boolean assignEntity(BattleEntity entity){
        if(checkGrid(entity.getGridXInt(),entity.getGridYInt())==null){
            grid[entity.getGridXInt()][entity.getGridYInt()] = entity;
            if(entity instanceof BattleHero){
                BattleHero hero = (BattleHero)entity;
                heroes.add(hero);
                timer.scheduleTask(hero.nextTask(), 0f, 1.2f);
            }
            if(entity instanceof BattleEnemy){
                enemies.add((BattleEnemy)entity);
            }
            return true;
        }else{
            return false;
        }
    }
    public static boolean moveEntity(BattleEntity entity, Vector2 pos){
        if(pos!=null)
            return moveEntity(entity,(int)pos.x,(int)pos.y);
        else
            return false;
    }
    public static boolean moveEntity(BattleEntity entity, int newX, int newY){
        if(checkGrid(newX,newY)==null){
            if(grid[entity.getGridXInt()][entity.getGridYInt()]!=null){
                if(grid[entity.getGridXInt()][entity.getGridYInt()]!=entity){

                }else{
                    grid[entity.getGridXInt()][entity.getGridYInt()] = null;
                }
            }
            grid[newX][newY] = entity;
            entity.setGrid(newX,newY);

            return true;
        }else{
            return false;
        }
    }
    public Array<BattleEnemy> getEnemies(){
        return enemies;
    }
    /**
     * There's probably a bazillion better ways to do it, but this one works. 
     * @return
     */
    public static Vector2 getUnusedPosition(){
        unusedPositions = new ArrayList<Vector2>();
        for(int x = sizeX/2; x<sizeX;x++){
            for(int y = 0; y<sizeY;y++){
                if(checkGrid(x,y)==null){
                    unusedPositions.add(new Vector2(x,y));
                }
            }
        }
        if(unusedPositions.size()>0)
            return unusedPositions.get(random.nextInt(unusedPositions.size()));
        else
            return null;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for(int x = 0; x<sizeX;x++){
            for(int y = 0;y<sizeY;y++){
                if(grid[x][y]!=null){
                    grid[x][y].act(delta);
                    if(grid[x][y]!=null){
                        if(grid[x][y].getHp()<=0){
                            if(grid[x][y] instanceof BattleHero){
                                tmpBattleHero = (BattleHero)grid[x][y];
                                clearGrid(x,y);
                                heroes.removeValue(tmpBattleHero, false);
                                checkState();
                            }
                            if(grid[x][y] instanceof BattleEnemy){
                                tmpBattleEnemy = (BattleEnemy)grid[x][y];
                                if(tmpBattleEnemy instanceof Dummy){
                                    if(tmpBattleEnemy.currentAnimation.isAnimationFinished(tmpBattleEnemy.stateTime)){
                                        clearGrid(x,y);
                                        enemies.removeValue(tmpBattleEnemy,false);
                                        checkState();
                                    }
                                }else{
                                    clearGrid(x,y);
                                    enemies.removeValue(tmpBattleEnemy,false);
                                    checkState();
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void draw() {
        super.draw();
        this.getSpriteBatch().begin();
        for(int x = 0; x<sizeX;x++){
            for(int y = 0;y<sizeY;y++){
                if(grid[x][y]!=null){
                    grid[x][y].draw(this.getSpriteBatch(),1);
                }
            }
        }
        if(BattleScreen.hero.getHp()>0)
            BattleScreen.hero.draw(this.getSpriteBatch(), 1);

        this.getSpriteBatch().end();
    }

    public static int checkState() {
        if(enemies.size==0){
            battleState = battleWon;
        }
        if(heroes.size==0){
            battleState = battleLost;
        }
        return battleState;
    }

    public void spawnEnemies(BattleEnemy... spawnies){
        BattleEntity enemy;
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
        for(int i = 0; i < spawnies.length; i++){
            enemy = spawnies[i];
            assignEntity(enemy);
            timer.scheduleTask(enemy.nextTask(), random.nextInt(5));
        }
        Gdx.app.log(Unsealed.LOG, "Spawned "+enemies.size+" enemies");
        battleState = BattleGrid.battleStarted;

    }
    public void spawnEnemies(boolean schedule,BattleEnemy... spawnies){
        BattleEntity enemy;
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
        for(int i = 0; i < spawnies.length; i++){
            enemy = spawnies[i];
            assignEntity(enemy);
            if(schedule)
                timer.scheduleTask(enemy.nextTask(), random.nextInt(5));
        }
        Gdx.app.log(Unsealed.LOG, "Spawned "+enemies.size+" enemies");
        battleState = BattleGrid.battleStarted;

    }
    public void spawnEnemies(int speed, BattleEnemy... spawnies ){
        BattleEntity enemy;
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
        for(int i = 0; i < spawnies.length; i++){
            enemy = spawnies[i];
            assignEntity(enemy);
            timer.scheduleTask(enemy.nextTask(), random.nextInt(speed));
        }
        Gdx.app.log(Unsealed.LOG, "Spawned "+enemies.size+" enemies");
        battleState = BattleGrid.battleStarted;

    }
    public void spawnEnemies(int bonus) {
        BattleScreen.round++;

      
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);

        for(int i = 0; i < random.nextInt(4)+1; i++){
            tmpSpawnPoint = getUnusedPosition();
            if(tmpSpawnPoint!=null){
                if(BattleScreen.round < 4){
                    if(random.nextInt(100)<40)
                        tmpBattleEnemy = new Clam(getAtlas(),50, (int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    else 
                        tmpBattleEnemy = new Ghost(getAtlas(), 80,(int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);

                }else if(BattleScreen.round>4&&BattleScreen.round<7){
                    if(random.nextInt(100)<40)
                        tmpBattleEnemy = new Snake(getAtlas(),70,(int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    else if(random.nextInt(100)<50)
                        tmpBattleEnemy = new Bee(getAtlas(),50,(int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    else
                        tmpBattleEnemy = new Terrex(getAtlas(),100, (int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                }else{
                    if(random.nextInt(100)<30){
                        tmpBattleEnemy = new Clam(getAtlas(), 60,(int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    }else if(random.nextInt(100)<40){
                        tmpBattleEnemy = new Terrex(getAtlas(), 130,(int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    }else if(random.nextInt(100)<40){
                        tmpBattleEnemy = new Ghost(getAtlas(), 80,(int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    }else if(random.nextInt(100)<40){
                        tmpBattleEnemy = new Snake(getAtlas(), 80,(int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    }else{
                        tmpBattleEnemy = new Bee(getAtlas(),90, (int)tmpSpawnPoint.x,(int)tmpSpawnPoint.y);
                    }
                }
                //                
                //                else
                //Override below to only spawn this type of enemy
                //                  enemy = new Bee(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
                assignEntity(tmpBattleEnemy);
                timer.scheduleTask(tmpBattleEnemy.nextTask(), random.nextInt(5));

                if(random.nextInt(bonus+1)/3>2&&enemies.size<9){
                    i--;
                }

            }

        }
        if(random.nextInt(bonus)/3>3){
            BattleScreen.hero.setHp(BattleScreen.hero.getHp()+30);
        }
        Gdx.app.log(Unsealed.LOG, "Spawned "+enemies.size+" enemies");
        BattleScreen.bonus++;
        battleState = BattleGrid.battleStarted;
    }

    public TextureAtlas getAtlas() {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
        }
        return atlas;
    }

    public void reset() {
        timer = new Timer();
        grid = new BattleEntity[sizeX][sizeY];
        random = new Random(System.currentTimeMillis());
        heroes = new Array<BattleHero>(3);

    }
    //    public static boolean assignOnGrid(int x, int y,BattleEntity entity){
    //        if(x<sizeX&&x>=0&&y<sizeY&&y>=0){
    //            if(entity!=null)
    //                grid[x][y] = entity;
    //            else
    //                grid[x][y] = null;
    //            return true;
    //        }else{
    //            return false;
    //        }
    //    }
    public static BattleEntity checkGrid(int x, int y){
        if(x<sizeX&&x>=0&&y<sizeY&&y>=0){
            if(grid[x][y] instanceof BattleHero){
                if(grid[x][y].isVisible()){
                    return grid[x][y];
                }else{
                    grid[x][y] = null;
                    return null;
                }
            }else if(grid[x][y] instanceof BattleEnemy){
                if(grid[x][y].isVisible()){
                    return grid[x][y];
                }else{
                    grid[x][y] = null;
                    return null;
                }
            }else{
                grid[x][y] = null;
                return null;
            }
        }else{
            return null;
        }
    }
}
