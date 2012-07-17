package net.k3rnel.unsealed.screens.battle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
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

    private Image lifebar;
    private TextureRegion[][] lifebarTextures;

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

    public boolean[][] grid;

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

        grid = new boolean[6][3];

        hero = new BattleHero();
        atlasRegion = atlas.findRegion("battle/lidia");
        TextureRegion[][] lidia = atlasRegion.split(112,134);
        Animation waiting = new Animation(1f, lidia[0][0]);
        waiting.setPlayMode(Animation.NORMAL);
        hero.animations.put("waiting", waiting);
        Animation blocking = new Animation(1f,lidia[0][1]);
        blocking.setPlayMode(Animation.NORMAL);
        hero.animations.put("blocking",blocking);
        TextureRegion[] attackFrames = new TextureRegion[8]; 
        attackFrames[0] = lidia[0][1];
        attackFrames[1] = lidia[0][2];
        attackFrames[2] = lidia[0][3];
        attackFrames[3] = lidia[0][4];
        attackFrames[4] = lidia[0][5];
        attackFrames[5] = lidia[0][6];
        attackFrames[6] = lidia[0][7];
        attackFrames[7] = lidia[0][4];
        Animation attacking = new Animation(0.1f,attackFrames);
        attacking.setPlayMode(Animation.NORMAL);
        hero.animations.put("attacking",attacking);
        attackFrames = new TextureRegion[3]; 
        attackFrames[0] = lidia[0][4];
        attackFrames[1] = lidia[0][2];
        attackFrames[2] = lidia[0][0];
        Animation release = new Animation(0.1f,attackFrames);
        release.setPlayMode(Animation.NORMAL);
        hero.animations.put("released",release);
        hero.setState(0);
        hero.setPosition(220,150);
        this.addActor(hero);
        grid[1][1] = true;

        atlasRegion = atlas.findRegion("battle/lifebar");
        lifebarTextures = atlasRegion.split(363,23);
        lifebar = new Image(lifebarTextures[0][0]);
        lifebar.setX( this.width/2 - lifebar.getWidth()/2 );
        lifebar.setY(this.height-lifebar.getHeight());
        this.addActor(lifebar);

        atlasRegion = atlas.findRegion("battle/lefttrigger");
        TextureRegion[][] textures  = atlasRegion.split(181,57);
        leftTrigger = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        leftTrigger.setPosition(0, this.height-leftTrigger.getHeight());
        leftTrigger.addListener(new PressedListener() {
            @Override
            public boolean touchDown(ActorEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(4,true); 
                return true;
            }

            @Override
            public void touchUp(ActorEvent event, float x, float y, int pointer, int button) {
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
            public boolean touchDown(ActorEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(5,true); 
                return true;
            }
            @Override
            public void touchUp(ActorEvent event, float x, float y, int pointer, int button) {
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
            public boolean touchDown(ActorEvent event, float x, float y, int pointer, int button) {
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
            public boolean touchDown(ActorEvent event, float x, float y, int pointer, int button) {
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
            public boolean touchDown(ActorEvent event, float x, float y, int pointer, int button) {
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
            public boolean touchDown(ActorEvent event, float x, float y, int pointer, int button) {
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
        manasphere1.setY(this.height-manasphere1.getHeight()-26);
        this.addActor(manasphere1);

        manasphere2 = new Image(manasphere[0][0]);
        manasphere2.setX( this.width/2 - manasphere2.getWidth()/2 -75 );
        manasphere2.setY(this.height-manasphere2.getHeight()-26);
        this.addActor(manasphere2);

        manasphere3 = new Image(manasphere[0][0]);
        manasphere3.setX( this.width/2 - manasphere3.getWidth()/2 - 25 );
        manasphere3.setY(this.height-manasphere3.getHeight()-26);
        this.addActor(manasphere3);

        manasphere4 = new Image(manasphere[0][0]);
        manasphere4.setX( this.width/2 - manasphere4.getWidth()/2 + 25 );
        manasphere4.setY(this.height-manasphere4.getHeight()-26);
        this.addActor(manasphere4);

        manasphere5 = new Image(manasphere[0][0]);
        manasphere5.setX( this.width/2 - manasphere5.getWidth()/2 + 75 );
        manasphere5.setY(this.height-manasphere5.getHeight()-26);
        this.addActor(manasphere5);

        manasphere6 = new Image(manasphere[0][0]);
        manasphere6.setX( this.width/2 - manasphere6.getWidth()/2 + 125 );
        manasphere6.setY(this.height-manasphere6.getHeight()-26);
        this.addActor(manasphere6);

        timer = new Timer();
        timer.scheduleTask(new Task() {

            @Override
            public void run() {
                if(hero.getState()==3){
                    if(hero.getMana()>0)
                        hero.setMana(hero.getMana()-2);
                    else
                        hero.setState(0);
                }else if(hero.getMana()<30)
                    hero.setMana(hero.getMana()+1);

            }
        }, 0f, 1f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hero.act(delta);
        if(hero.getState()==1&&hero.waitingOnAnimation){
            Gdx.app.log(Unsealed.LOG,"Change Anim!");
            hero.waitingOnAnimation = false;
            hero.setState(4);
        }
        for(BattleEnemy enemy : enemies){
            enemy.act(delta);
        }
    }
    @Override
    public void draw() {
        super.draw();

        for(BattleEnemy enemy : enemies){
            int x = (int)((battleoverlay.getWidth()/2)/3);
            int y = (int)((battleoverlay.getHeight())/3);
//            Gdx.app.log(Unsealed.LOG, "Blah: "+enemy.getGridX()+"/"+enemy.getGridY());
//            Gdx.app.log(Unsealed.LOG, "Bleh: "+x+"/"+y);
            enemy.setPosition(battleoverlay.getX()+battleoverlay.getWidth()/2 + 20 + x * (enemy.getGridX()-3) ,battleoverlay.getY()+battleoverlay.getHeight()  - 38*(enemy.getGridY()+1));

        }

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
                    if(!grid[hero.getGridX()][hero.getGridY()-1]){
                        hero.setY(hero.getY()+40);
                        grid[hero.getGridX()][hero.getGridY()] =  false;
                        grid[hero.getGridX()][hero.getGridY()-1] = true;
                        hero.setGridY(hero.getGridY()-1);
                    }

                break;
            }
            case 1:{ // Down
                if((hero.getGridY()+1<3))
                    if(!grid[hero.getGridX()][hero.getGridY()+1]){
                        hero.setY(hero.getY()-40);
                        grid[hero.getGridX()][hero.getGridY()] =  false;
                        grid[hero.getGridX()][hero.getGridY()+1] = true;
                        hero.setGridY(hero.getGridY()+1);
                    }
                break;
            }
            case 2:{ // Left
                if((hero.getGridX()-1>-1))
                    if(!grid[hero.getGridX()-1][hero.getGridY()]){
                        hero.setX(hero.getX()-88);
                        grid[hero.getGridX()][hero.getGridY()] =  false;
                        grid[hero.getGridX()-1][hero.getGridY()] = true;
                        hero.setGridX(hero.getGridX()-1);
                    }
                break;
            }
            case 3:{ // Right
                if((hero.getGridX()+1<3))
                    if(!grid[hero.getGridX()+1][hero.getGridY()]){
                        hero.setX(hero.getX()+88);
                        grid[hero.getGridX()][hero.getGridY()] =  false;
                        grid[hero.getGridX()+1][hero.getGridY()] = true;
                        hero.setGridX(hero.getGridX()+1);
                    }
                break;
            }
            case 4:{ // Shield
                if(pressed){
                    if(hero.getState()!=3){
                        hero.setState(3);
                        hero.setMana(hero.getMana()-2);
                    }
                }else{
                    hero.setState(0);
                }
                break;
            }
            case 5:{ // Attack
                if(pressed)
                    hero.setState(1);
                else
                    hero.isCharging = false;
                break;
            }
        }
    }

    public void spawnEnemies() {

        Random random = new Random(new Date().getTime());
        for(int i = 0; i < random.nextInt(2)+1; i++){
            BattleEnemy clam = new BattleEnemy(100,random.nextInt(3)+3,random.nextInt(3));
            AtlasRegion atlasRegion = atlas.findRegion( "battle/clam" );
            TextureRegion[][] spriteSheet = atlasRegion.split(41, 48);
            TextureRegion[] frames = new TextureRegion[2];
            frames[0] = spriteSheet[0][0];
            frames[1] = spriteSheet[0][1];
            Animation idle = new Animation(0.2f,frames);
            idle.setPlayMode(Animation.LOOP);
            clam.animations.put("idle",idle);
            frames = new TextureRegion[6];
            frames[0] = spriteSheet[0][2];
            frames[1] = spriteSheet[0][3];
            frames[2] = spriteSheet[0][4];
            frames[3] = spriteSheet[0][5];
            frames[4] = spriteSheet[0][6];
            frames[5] = spriteSheet[0][0];
            Animation attacking = new Animation(0.2f,frames);
            idle.setPlayMode(Animation.NORMAL);
            clam.animations.put("attacking",attacking);
            int x = (int)((battleoverlay.getWidth()/2)/6);
            int y = (int)((battleoverlay.getHeight())/3);
            clam.setPosition(x*clam.getGridX(),y*clam.getGridY());
            clam.setState(0);
            grid[clam.getGridX()][clam.getGridY()] = true;
            this.addActor(clam);
            enemies.add(clam);
        }


    }
}
