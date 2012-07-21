package net.k3rnel.unsealed.screens.battle;


import java.util.Random;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.enemies.Clam;
import net.k3rnel.unsealed.screens.battle.magic.Blast;
import net.k3rnel.unsealed.screens.battle.magic.MagicEntity;
import net.k3rnel.unsealed.screens.battle.magic.Shield;

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

    
    public int sizeX;
    public int sizeY;

    private BattleEntity[][] grid;

    private Array<BattleEnemy> enemies;
    private Array<BattleHero> heroes;

    private Array<Vector2> unusedPositions;

    private int battleState;

    private Timer timer;

    private Random random;

    private OrthographicCamera cam;
    
    
    public BattleGrid(float width, float height, int sizeX, int sizeY) {
        this.width = width;
        this.height = height;
        setViewport(this.width, this.height, true);
        timer = new Timer();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        grid = new BattleEntity[sizeX][sizeY];
        random = new Random(System.currentTimeMillis());
        heroes = new Array<BattleHero>(3);
        enemies = new Array<BattleEnemy>((sizeX/2)*sizeY);
        unusedPositions = new Array<Vector2>();
        // SizeX / 2 because we don't want enemies spawning on the hero-side.
        for(int x = sizeX/2; x<sizeX;x++){
            for(int y = 0; y<sizeY;y++){
                unusedPositions.add(new Vector2(x,y));
            }
        }
        unusedPositions.shuffle();
        cam = new OrthographicCamera(this.width, this.height);            
        cam.position.set(this.width / 2, this.height / 2, 0);
        cam.zoom = 0.76f;
        this.setCamera(cam);

    }

    public boolean assignEntity(BattleEntity entity){
        if(grid[entity.getGridX()][entity.getGridY()]==null){
            if(entity.getGridX()>sizeX/2)
                unusedPositions.removeValue(new Vector2(entity.getGridX(),entity.getGridY()),false);
            grid[entity.getGridX()][entity.getGridY()] = entity;
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
    public boolean moveEntity(BattleEntity entity, int newX, int newY){
        if(grid[newX][newY]==null){
            if(entity.getGridX()>sizeX/2){
                unusedPositions.add(new Vector2(entity.getGridX(),entity.getGridY()));
                unusedPositions.removeValue(new Vector2(newX,newY),false);
            }
            grid[entity.getGridX()][entity.getGridY()] = null;
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
    public Vector2 getUnusedPosition(){
        return unusedPositions.pop();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for(int i = 0; i<heroes.size; i++){
            BattleHero hero = heroes.get(i);
            if(hero.getHp()<=0){
                grid[hero.getGridX()][hero.getGridY()] = null;
                heroes.removeIndex(i);
                hero.remove();
                i--;
                checkState();
            }
            if(hero.isShooting){
                hero.isShooting = false;
                hero.showBlast(true);
            }
            if(hero.getBlast().isVisible()){
                if(hero.getBlast().getGridX() >= 6){
                    hero.showBlast(false);
                }else{
                    if(grid[hero.getBlast().getGridX()][hero.getBlast().getGridY()]!=null){
                        BattleEntity entity = grid[hero.getBlast().getGridX()][hero.getBlast().getGridY()];
                        if(entity instanceof BattleEnemy){
                            if(entity.getState() == BattleEntity.stateBlocking){
                                Gdx.app.log(Unsealed.LOG, "You hit me but it didn't hurt! Haha");
                            }else{
                                if(grid[hero.getBlast().getGridX()][hero.getBlast().getGridY()].setHp(grid[hero.getBlast().getGridX()][hero.getBlast().getGridY()].getHp()-15)){
                                    this.getActors().removeValue(grid[hero.getBlast().getGridX()][hero.getBlast().getGridY()], false);
                                    unusedPositions.add(new Vector2(entity.getGridX(),entity.getGridY()));
                                    enemies.removeValue((BattleEnemy)grid[hero.getBlast().getGridX()][hero.getBlast().getGridY()],false);
                                    grid[hero.getBlast().getGridX()][hero.getBlast().getGridY()] = null;
                                    checkState();
                                }
                            }
                            hero.showBlast(false);
                        }
                    }
                }
            }
        }
         
               
            
        //TODO Clean up below. Move to Enemy.act();
        BattleEntity enemy;
        for(int x=3;x<6;x++){
            for(int y = 0;y < 3; y++){
                if(grid[x][y]!=null){
                    enemy = grid[x][y];
                    if(enemy.action(heroes, delta)){
                        Gdx.app.log(Unsealed.LOG,"Rescheduling!");
                        timer.scheduleTask(enemy.nextTask(),random.nextInt(4));
                    }
                }
            }
        }
    }

    @Override
    public void draw() {
        super.draw();

    }

    public int checkState() {
        if(enemies.size==0){
            this.battleState = battleWon;
        }
        if(heroes.size==0){
            this.battleState = battleLost;
        }
        return this.battleState;
    }
    public void spawnEnemies(int bonus) {
        bonus += random.nextInt(3);
        if(bonus > 9)
            bonus = 9;
        Vector2 spawnPoint;
        Clam clam;
        for(int i = 0; i < bonus; i++){
            spawnPoint = getUnusedPosition();
            if(spawnPoint!=null){
                clam = new Clam(getAtlas(),(int)spawnPoint.x,(int)spawnPoint.y);
                
                assignEntity(clam);
                timer.scheduleTask(clam.nextTask(), random.nextInt(5));
            }
        }
        this.battleState = BattleGrid.battleStarted;
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
}
