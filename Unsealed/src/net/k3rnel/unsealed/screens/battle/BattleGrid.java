package net.k3rnel.unsealed.screens.battle;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.BattleScreen;
import net.k3rnel.unsealed.screens.battle.enemies.Clam;
import net.k3rnel.unsealed.screens.battle.enemies.Ghost;
import net.k3rnel.unsealed.screens.battle.enemies.Snake;
import net.k3rnel.unsealed.screens.battle.enemies.Terrex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private static BattleEntity[][] grids;

    public static Array<BattleEnemy> enemies;
    public static Array<BattleHero> heroes;

    private static List<Vector2> unusedPositions;
    public static int battleState;

    public static Timer timer;

    public static Random random;

    private OrthographicCamera cam;
    
    
    public BattleGrid(float width, float height, int sizeX, int sizeY) {
        this.width = width;
        this.height = height;
        setViewport(this.width, this.height, true);
        timer = new Timer();
        BattleGrid.sizeX = sizeX;
        BattleGrid.sizeY = sizeY;
        grids = new BattleEntity[sizeX][sizeY];
        random = new Random(System.currentTimeMillis());
        heroes = new Array<BattleHero>(3);
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
        cam = new OrthographicCamera(this.width, this.height);            
        cam.position.set(this.width / 2, this.height / 2, 0);
        cam.zoom = 1f;
        this.setCamera(cam);

    }

    public boolean assignEntity(BattleEntity entity){
        if(checkGrid(entity.getGridXInt(),entity.getGridYInt())==null){
            assignOnGrid(entity.getGridXInt(),entity.getGridYInt(),entity);
            if(entity instanceof BattleHero){
                BattleHero hero = (BattleHero)entity;
                heroes.add(hero);
                timer.scheduleTask(hero.nextTask(), 0f, 1.5f);
            }
            if(entity instanceof BattleEnemy){
                enemies.add((BattleEnemy)entity);
            }
            return true;
        }else{
            return false;
        }
    }
    public  boolean moveEntity(BattleEntity entity, Vector2 pos){
        if(pos!=null)
            return moveEntity(entity,(int)pos.x,(int)pos.y);
        else
            return false;
    }
    public  boolean moveEntity(BattleEntity entity, int newX, int newY){
        if(checkGrid(newX,newY)==null){
            assignOnGrid(newX,newY,entity);
            assignOnGrid(entity.getGridXInt(),entity.getGridYInt(),null);
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
        for(int i = 0; i<heroes.size; i++){
            
            //TODO: Clean up below. Move it to the hero act method. 
            BattleHero hero = heroes.get(i);
            hero.act(delta);
            if(hero.getHp()<=0){
                assignOnGrid(hero.getGridXInt(),hero.getGridYInt(), null);
                heroes.removeIndex(i);
                i--;
                checkState();
            }
        }
        for(int i = 0; i<enemies.size; i++){
            
            //TODO: Clean up below. Move it to the hero act method. 
            BattleEnemy enemy = enemies.get(i);
            enemy.act(delta);
            if(enemy.getHp()<=0){
                assignOnGrid(enemy.getGridXInt(),enemy.getGridYInt(), null);
                enemies.removeIndex(i);
                i--;
                checkState();
            }
        }
    }

    
    @Override
    public void draw() {
        super.draw();
        this.getSpriteBatch().begin();
        for(int i = 0; i<heroes.size; i++){
            //TODO: Clean up below. Move it to the hero act method. 
            BattleHero hero = heroes.get(i);
            hero.draw(this.getSpriteBatch(), 1);
            if(hero.getHp()<=0){
                assignOnGrid(hero.getGridXInt(),hero.getGridYInt(), null);
                heroes.removeIndex(i);
                hero.remove();
                i--;
                checkState();
            }
        }
        for(int i = 0; i<enemies.size; i++){
            //TODO: Clean up below. Move it to the hero act method. 
            BattleEnemy enemy = enemies.get(i);
            enemy.draw(this.getSpriteBatch(), 1);
            if(enemy.getHp()<=0){
                assignOnGrid(enemy.getGridXInt(),enemy.getGridYInt(), null);
                enemies.removeIndex(i);
                enemy.remove();
                i--;
                checkState();
            }
        }
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
    public void spawnEnemies(int bonus) {
        BattleScreen.round++;
        
        Vector2 spawnPoint;
        BattleEntity enemy;
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
       
        for(int i = 0; i < random.nextInt(8)+1; i++){
            spawnPoint = getUnusedPosition();
            if(spawnPoint!=null){
//                if(BattleScreen.round < 3){
//                    if(random.nextInt(100)<60)
//                        enemy = new Clam(getAtlas(),(int)spawnPoint.x,(int)spawnPoint.y);
//                    else
//                        enemy = new Ghost(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
//                }else{
//                    if(random.nextInt(100)<30){
//                        enemy = new Snake(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
//                    }else{
//                        enemy = new Terrex(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
//                    }
//                }
                
//                else
                //Override below to only spawn this type of enemy
                  enemy = new Ghost(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
                assignEntity(enemy);
                timer.scheduleTask(enemy.nextTask(), random.nextInt(5));
                
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
        reorderActors();
    }

    public void reorderActors(){

    }
    public TextureAtlas getAtlas() {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
        }
        return atlas;
    }

    public void reset() {
        timer = new Timer();
        grids = new BattleEntity[sizeX][sizeY];
        random = new Random(System.currentTimeMillis());
        heroes = new Array<BattleHero>(3);
        
    }
    public  boolean assignOnGrid(int x, int y,BattleEntity entity){
        if(x<sizeX&&x>=0&&y<sizeY&&y>=0){
            grids[x][y] = entity;
            return true;
        }else{
            return false;
        }
    }
    public static BattleEntity checkGrid(int x, int y){
        if(x<sizeX&&x>=0&&y<sizeY&&y>=0){
            return grids[x][y];
        }else{
            return null;
        }
    }
}
