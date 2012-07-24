package net.k3rnel.unsealed.screens.battle;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.k3rnel.unsealed.screens.BattleScreen;
import net.k3rnel.unsealed.screens.battle.enemies.Clam;
import net.k3rnel.unsealed.screens.battle.enemies.Ghost;
import net.k3rnel.unsealed.screens.battle.enemies.Terrex;

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

    public static BattleEntity[][] grid;

    public static Array<BattleEnemy> enemies;
    public static Array<BattleHero> heroes;

    public static List<Vector2> unusedPositions;

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
        grid = new BattleEntity[sizeX][sizeY];
        random = new Random(System.currentTimeMillis());
        heroes = new Array<BattleHero>(3);
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
        unusedPositions = new ArrayList<Vector2>();
        // SizeX / 2 because we don't want enemies spawning on the hero-side.
        for(int x = sizeX/2; x<sizeX;x++){
            for(int y = 0; y<sizeY;y++){
                unusedPositions.add(new Vector2(x,y));
            }
        }
        cam = new OrthographicCamera(this.width, this.height);            
        cam.position.set(this.width / 2, this.height / 2, 0);
        cam.zoom = 1f;
        this.setCamera(cam);

    }

    public boolean assignEntity(BattleEntity entity){
        if(grid[entity.getGridXInt()][entity.getGridYInt()]==null){
            if(entity.getGridXInt()>sizeX/2)
                unusedPositions.remove(new Vector2(entity.getGridXInt(),entity.getGridYInt()));
            grid[entity.getGridXInt()][entity.getGridYInt()] = entity;
            this.addActor(entity);
            if(entity instanceof BattleHero){
                BattleHero hero = (BattleHero)entity;
                heroes.add(hero);
                this.addActor(hero.getShield());
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
    public static boolean moveEntity(BattleEntity entity, Vector2 pos){
        return moveEntity(entity,(int)pos.x,(int)pos.y);
    }
    public static boolean moveEntity(BattleEntity entity, int newX, int newY){
        if(grid[newX][newY]==null){
           
            if(entity instanceof BattleEnemy){
                if(entity.getGridXInt()>sizeX/2){
                    unusedPositions.add(new Vector2(entity.getGridXInt(),entity.getGridYInt()));
                    unusedPositions.remove(new Vector2(newX,newY));
                }else{
                    unusedPositions.remove(new Vector2(newX,newY));
                }
            }
            grid[entity.getGridXInt()][entity.getGridYInt()] = null;
            grid[newX][newY] = entity;
            entity.setGridX(newX); 
            entity.setGridY(newY);

            return true;
        }else{
            return false;
        }
    }
    public Array<BattleEnemy> getEnemies(){
        return enemies;
    }
    public static Vector2 getUnusedPosition(){
        return unusedPositions.get(BattleGrid.random.nextInt(unusedPositions.size()));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for(int i = 0; i<heroes.size; i++){
            
            //TODO: Clean up below. Move it to the hero act method. 
            BattleHero hero = heroes.get(i);
            if(hero.getHp()<=0){
                grid[hero.getGridXInt()][hero.getGridYInt()] = null;
                heroes.removeIndex(i);
                hero.remove();
                i--;
                checkState();
            }
        }
    }

    @Override
    public void draw() {
        super.draw();

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
        unusedPositions = new ArrayList<Vector2>();
        // SizeX / 2 because we don't want enemies spawning on the hero-side.
        for(int x = sizeX/2; x<sizeX;x++){
            for(int y = 0; y<sizeY;y++){
                unusedPositions.add(new Vector2(x,y));
            }
        }
        for(int i = 0; i < random.nextInt(4)+1; i++){
            spawnPoint = getUnusedPosition();
            if(spawnPoint!=null){
                if(BattleScreen.round < 3){
                    if(random.nextInt(100)<60)
                        enemy = new Clam(getAtlas(),(int)spawnPoint.x,(int)spawnPoint.y);
                    else
                        enemy = new Ghost(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
//                else if(spawnPoint.y==1)
                }else{
                    if(random.nextInt(100)<30){
                        enemy = new Ghost(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
                    }else{
                        enemy = new Terrex(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
                    }
                }
                
//                else
//                  enemy = new Terrex(getAtlas(), (int)spawnPoint.x,(int)spawnPoint.y);
                assignEntity(enemy);
                timer.scheduleTask(enemy.nextTask(), random.nextInt(5));
                if(random.nextInt(bonus)/3>2&&enemies.size<9){
                    i--;
                }
                if(random.nextInt(bonus)/3>3){
                    BattleScreen.hero.setHp(BattleScreen.hero.getHp()+30);
                }
            }
        }
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
        grid = new BattleEntity[sizeX][sizeY];
        random = new Random(System.currentTimeMillis());
        heroes = new Array<BattleHero>(3);
        this.getActors().clear();
        
    }
}
