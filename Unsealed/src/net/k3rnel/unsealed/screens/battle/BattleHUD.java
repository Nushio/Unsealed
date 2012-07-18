package net.k3rnel.unsealed.screens.battle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.enemies.Clam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.PressedListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class BattleHUD extends Stage {

    private float width, height;

    private SpriteBatch batch;

    private TextureAtlas atlas;

    private Button leftTrigger;
    private Button rightTrigger;
    private Image dPad;
    private Button dPadDown;
    private Button dPadUp;
    private Button dPadLeft;
    private Button dPadRight;
    private Button aButton;
    private Button bButton;
    private Button xButton;
    private Button yButton;

    TextureRegion[][] manasphere; 
    private Image manasphere1;
    private Image manasphere2;
    private Image manasphere3;
    private Image manasphere4;
    private Image manasphere5;
    private Image manasphere6;

    private Image background;
    private Image battleoverlay;

    private BattleEntity[][] grid;

    public BattleHero hero;
    public List<BattleEnemy> enemies;

    private Timer timer;

    public BattleHUD(float width, float height, TextureAtlas atlas) {
        this.width = width;
        this.height = height;
        this.atlas = atlas;
        setViewport(this.width, this.height, true);
        init();
    }

    private void init() {
        //TODO: Non-hardcoded field sizes
        atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
        enemies = new ArrayList<BattleEnemy>();
        int battlemap = new Random().nextInt(6);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/battlemap"+battlemap );
        background = new Image(atlasRegion);
        background.setSize(width, height);

        this.addActor(background);

        atlasRegion = atlas.findRegion( "battle/field-3x3" );
        battleoverlay = new Image(atlasRegion);
        battleoverlay.setX( this.width/2 - (battleoverlay.getWidth()*battleoverlay.getScaleX())/2 );
        battleoverlay.setY( this.height/2 -(battleoverlay.getHeight()*battleoverlay.getScaleY())/2 - 75); 
        this.addActor(battleoverlay);

        grid = new BattleEntity[6][3];

        hero = new BattleHero(100);
        this.addActor(hero);
        grid[1][1] = hero;

        atlasRegion = atlas.findRegion("battle/lefttrigger");
        TextureRegion[][] textures  = atlasRegion.split(181,57);
        leftTrigger = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        leftTrigger.setPosition(0, this.height-leftTrigger.getHeight());
        leftTrigger.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(4,true); 
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                buttonPress(4,false); 
            }
        });
        this.addActor(leftTrigger);

        atlasRegion = atlas.findRegion("battle/righttrigger");
        textures = atlasRegion.split(181,57);
        rightTrigger = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        rightTrigger.setPosition(this.width-rightTrigger.getWidth(), this.height-rightTrigger.getHeight());
        rightTrigger.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(5,true); 
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                buttonPress(5,false); 
            }
        });
        this.addActor(rightTrigger);

        atlasRegion = atlas.findRegion("battle/blue_facebutton1");
        textures = atlasRegion.split(83,92);
        aButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        aButton.setX( this.width - (aButton.getWidth()*aButton.getScaleX()) );
        aButton.setY( this.height -(aButton.getHeight()*aButton.getScaleY()+ 100) ); 
        this.addActor(aButton);

        textures = atlasRegion.split(83,92);
        xButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        xButton.setX( this.width - (xButton.getWidth()*xButton.getScaleX()) -160 );
        xButton.setY( this.height -(xButton.getHeight()*xButton.getScaleY()+ 100) );
        this.addActor(xButton);

        textures = atlasRegion.split(83,92);
        bButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        bButton.setX( this.width - (bButton.getWidth()*bButton.getScaleX()) -80 );
        bButton.setY( this.height -(bButton.getHeight()*bButton.getScaleY()+ 160) ); 
        this.addActor(bButton);

        atlasRegion = atlas.findRegion("battle/blue_facebutton2");
        textures = atlasRegion.split(75,74);
        yButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        yButton.setX( this.width - (yButton.getWidth()*yButton.getScaleX()) -84 );
        yButton.setY( this.height -(yButton.getHeight()*yButton.getScaleY()+ 60) );
        this.addActor(yButton);

        atlasRegion = atlas.findRegion("battle/dpad_alt");
        dPad = new Image(new Image(atlasRegion).getDrawable());
        dPad.setX((dPad.getWidth()*dPad.getScaleX()) - 80);
        dPad.setY( this.height -(dPad.getHeight()*dPad.getScaleY()+ 100) ); 
        this.addActor(dPad);

        atlasRegion = atlas.findRegion("battle/dpad_down");
        textures = atlasRegion.split(60,43);
        dPadDown = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        dPadDown.setPosition(84,this.height-227); 
        dPadDown.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(1,true); 
                return true;
            }
        });
        this.addActor(dPadDown);

        atlasRegion = atlas.findRegion("battle/dpad_up");
        textures = atlasRegion.split(60,43);
        dPadUp = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        dPadUp.setPosition(84,this.height-143);
        dPadUp.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(0,true); 
                return true;
            }
        });
        this.addActor(dPadUp);

        atlasRegion = atlas.findRegion("battle/dpad_left");
        textures = atlasRegion.split(43,60);
        dPadLeft = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[0][1]).getDrawable());
        dPadLeft.setPosition(50,this.height-194);  
        dPadLeft.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(2,true); 
                return true;
            }
        });
        this.addActor(dPadLeft);

        atlasRegion = atlas.findRegion("battle/dpad_right");
        textures = atlasRegion.split(43,60);
        dPadRight = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[0][1]).getDrawable());
        dPadRight.setPosition(138,this.height-194);
        dPadRight.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(3,true); 
                return true;
            }
        });
        this.addActor(dPadRight);

        atlasRegion = atlas.findRegion("battle/mana_sphere");
        manasphere = atlasRegion.split(32,43);
        manasphere1 = new Image(manasphere[0][0]);
        manasphere1.setX( this.width/2 - manasphere1.getWidth()/2 -125 );
        manasphere1.setY(this.height-manasphere1.getHeight()-10);
        this.addActor(manasphere1);

        manasphere2 = new Image(manasphere[0][0]);
        manasphere2.setX( this.width/2 - manasphere2.getWidth()/2 -75 );
        manasphere2.setY(this.height-manasphere2.getHeight()-10);
        this.addActor(manasphere2);

        manasphere3 = new Image(manasphere[0][0]);
        manasphere3.setX( this.width/2 - manasphere3.getWidth()/2 - 25 );
        manasphere3.setY(this.height-manasphere3.getHeight()-10);
        this.addActor(manasphere3);

        manasphere4 = new Image(manasphere[0][0]);
        manasphere4.setX( this.width/2 - manasphere4.getWidth()/2 + 25 );
        manasphere4.setY(this.height-manasphere4.getHeight()-10);
        this.addActor(manasphere4);

        manasphere5 = new Image(manasphere[0][0]);
        manasphere5.setX( this.width/2 - manasphere5.getWidth()/2 + 75 );
        manasphere5.setY(this.height-manasphere5.getHeight()-10);
        this.addActor(manasphere5);

        manasphere6 = new Image(manasphere[0][0]);
        manasphere6.setX( this.width/2 - manasphere6.getWidth()/2 + 125 );
        manasphere6.setY(this.height-manasphere6.getHeight()-10);
        this.addActor(manasphere6);

        timer = new Timer();
        timer.scheduleTask(new Task() {

            @Override
            public void run() {
                if(hero.getState()==BattleEntity.stateBlocking){
                    if(hero.getMana()>=2){
                        hero.setMana(hero.getMana()-2);
                    }else{
                        hero.setMana(0);
                        hero.setState(BattleEntity.stateIdle);
                    }
                }else if(hero.getMana()<30)
                    hero.setMana(hero.getMana()+1);

            }
        }, 0f, 1.5f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hero.act(delta);
        if(hero.getState()==BattleEntity.stateAttacking&&hero.waitingOnAnimation){
            Gdx.app.log(Unsealed.LOG,"Check Collision!");
            for(int i = 3; i < 6; i++){
                if(grid[i][hero.getGridY()]!=null){
                    if(grid[i][hero.getGridY()].setHp(grid[i][hero.getGridY()].getHp()-25)){
                        this.getActors().removeValue(grid[i][hero.getGridY()], false);
                        grid[i][hero.getGridY()] = null;
                        checkVictory();
                    }
                    i = 6;
                }
            }
            hero.waitingOnAnimation = false;
            hero.setState(BattleEntity.stateIdle);
        }
        for(BattleEnemy enemy : enemies){
            enemy.act(delta);
        }
    }
    private void checkVictory() {
        boolean youwin = true;
        for(int x = 3; x< 6; x++){
            for (int y = 0; y < 3 ; y++){
                if(grid[x][y]!=null){
                    youwin = false;
                }
            }
        }
        if(youwin){
            Gdx.app.log(Unsealed.LOG, "How exciting! You have defeated the evil, unmovable, innocent clams!");
            spawnEnemies();
        }

    }

    @Override
    public void draw() {
        super.draw();
        for(BattleEnemy enemy : enemies){
            int x = (int)((battleoverlay.getWidth()/2)/3);
            //            int y = (int)((battleoverlay.getHeight())/6);
            //            Gdx.app.log(Unsealed.LOG, "Blah: "+enemy.getGridX()+"/"+battleoverlay.getHeight());
            //            Gdx.app.log(Unsealed.LOG, "Bleh: "+x+"/"+y);
            enemy.setPosition(battleoverlay.getX()+battleoverlay.getWidth()/2 + 20 + x * (enemy.getGridX()-3) ,battleoverlay.getY()+battleoverlay.getHeight()  - 38*(enemy.getGridY()+1));
            enemy.hpLabel.setPosition(battleoverlay.getX()+battleoverlay.getWidth()/2 + 20 + x * (enemy.getGridX()-3) + 5 ,battleoverlay.getY()+battleoverlay.getHeight()  - 38*(enemy.getGridY()+1)-10);
            enemy.hpBar.setPosition(battleoverlay.getX()+battleoverlay.getWidth()/2 + 20 + x * (enemy.getGridX()-3) -30,battleoverlay.getY()+battleoverlay.getHeight()  - 38*(enemy.getGridY()+1)-15);

        }
        hero.hpLabel.setPosition(hero.getX()+32,hero.getY()+hero.getHeight()+128);
        hero.hpBar.setPosition(hero.getX(),hero.getY()+hero.getHeight()+118);

        int fillSize = hero.getMana()%5;
        int manaBars = hero.getMana()/5;
        switch(manaBars){
            case 0:
                manasphere1.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 1:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 2:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 3:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 4:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 5:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                break;
            case 6:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][5]).getDrawable());
                break;
        }

    }

    public void dispose () {
        if(atlas!=null) atlas.dispose();
        if(batch!=null) batch.dispose();
    }

    /**
     * 0 = up, 1 = down, 2 = left, 3 = right
     * 4 = shield, 5 = attack
     * @param direction
     */
    public void buttonPress(int button,boolean pressed){
        switch(button){
            //TODO: Hero moves at a fixed pixel rate (A.k.a. magic numbers). It should instead move based on screen width.
            case 0:{ // Up
                if((hero.getGridY()-1>-1))
                    if(grid[hero.getGridX()][hero.getGridY()-1]==null){
                        hero.setY(hero.getY()+40);
                        grid[hero.getGridX()][hero.getGridY()] =  null;
                        grid[hero.getGridX()][hero.getGridY()-1] = hero;
                        hero.setGridY(hero.getGridY()-1);
                    }

                break;
            }
            case 1:{ // Down
                if((hero.getGridY()+1<3))
                    if(grid[hero.getGridX()][hero.getGridY()+1]==null){
                        hero.setY(hero.getY()-40);
                        grid[hero.getGridX()][hero.getGridY()] =  null;
                        grid[hero.getGridX()][hero.getGridY()+1] = hero;
                        hero.setGridY(hero.getGridY()+1);
                    }
                break;
            }
            case 2:{ // Left
                if((hero.getGridX()-1>-1))
                    if(grid[hero.getGridX()-1][hero.getGridY()]==null){
                        hero.setX(hero.getX()-88);
                        grid[hero.getGridX()][hero.getGridY()] =  null;
                        grid[hero.getGridX()-1][hero.getGridY()] = hero;
                        hero.setGridX(hero.getGridX()-1);
                    }
                break;
            }
            case 3:{ // Right
                if((hero.getGridX()+1<3))
                    if(grid[hero.getGridX()+1][hero.getGridY()]==null){
                        hero.setX(hero.getX()+88);
                        grid[hero.getGridX()][hero.getGridY()] =  null;
                        grid[hero.getGridX()+1][hero.getGridY()] = hero;
                        hero.setGridX(hero.getGridX()+1);
                    }
                break;
            }
            case 4:{ // Shield
                if(pressed){
                    if(hero.getState()!=BattleEntity.stateBlocking){
                        hero.setState(BattleEntity.stateBlocking);
                        hero.setMana(hero.getMana()-2);
                    }
                }else{
                    hero.setState(BattleEntity.stateIdle);
                }
                break;
            }
            case 5:{ // Attack
                if(pressed){
                    hero.setMana(hero.getMana()-1);
                    hero.setState(BattleEntity.stateAttacking);
                }else
                    hero.isCharging = false;
                break;
            }
        }
    }

    public void spawnEnemies() {
        Random random = new Random(new Date().getTime());
        //TODO: Allow using numbers > 9. Currently loops to infinity... and beyond!
        for(int i = 0; i < random.nextInt(5)+4; i++){
            int x = random.nextInt(3)+3;
            int y = random.nextInt(3);
            while(grid[x][y]!=null){
                x = random.nextInt(3)+3;
                y = random.nextInt(3);
            }
            Clam clam = new Clam(100,x,y);
            grid[clam.getGridX()][clam.getGridY()] = clam;
            this.addActor(clam);
            enemies.add(clam);
        }
        reorderActors();

    }
    public void reorderActors(){
        List<BattleEntity> entities = new ArrayList<BattleEntity>();
        for(Actor actor : this.getActors()){

            if(actor instanceof BattleEntity){
                BattleEntity entity = (BattleEntity)actor;
                entities.add(entity);
                Gdx.app.log(Unsealed.LOG, "Entity: "+entity.getGridX()+"/"+entity.getGridY());
            }
        }
    }
}
