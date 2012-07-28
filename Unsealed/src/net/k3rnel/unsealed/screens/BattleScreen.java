package net.k3rnel.unsealed.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.PressedListener;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHUD;
import net.k3rnel.unsealed.battle.BattleHero;
import net.k3rnel.unsealed.battle.magic.MagicEntity;
import net.k3rnel.unsealed.screens.AbstractScreen;
import net.k3rnel.unsealed.story.helpers.MapCharacter;
import net.k3rnel.unsealed.story.helpers.StyledTable;
import net.k3rnel.unsealed.story.helpers.TextBox;

public class BattleScreen extends AbstractScreen {


    //    private Image background;
    private Image battleoverlay;

    TiledMap tileMap;
    TileAtlas tileAtlas;
    TileMapRenderer tileMapRenderer;
    OrthographicCamera camera;

    public BattleGrid grid;
    private BattleHUD hud;

    public static BattleHero hero;

    private Label roundLabel;

    private Button restartButton;

    public static int round = 0;
    public static int bonus = 1;

    public boolean scriptedBattle;
    public int act;
    public float stateTime;

    List<MapCharacter> characters;
    MapCharacter tmpChar;

    public TextBox dialog;
    StyledTable.TableStyle textBoxStyle;

    List<MagicEntity> magics = new ArrayList<MagicEntity>();

    String mapname;

    public boolean disableInput = false;

    public BattleScreen(Unsealed game, boolean scriptedBattle, String mapname) {
        super(game);
        if(scriptedBattle){
            this.scriptedBattle = true;
            act = 0;
        }
        this.mapname = mapname;
    }
    @Override
    protected boolean isGameScreen() {
        return false;
    }
    @Override
    public void show() {
        super.show();

        atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );

        // Load the tmx file into map
        tileMap = TiledLoader.createMap(Gdx.files.internal("assets/map-atlases/"+mapname+".tmx"));

        // Load the tiles into atlas
        tileAtlas = new TileAtlas(tileMap, Gdx.files.internal("assets/map-atlases/"));


        // Create the renderer
        tileMapRenderer = new TileMapRenderer(tileMap, tileAtlas, tileMap.width, tileMap.height);

        // Create the camera
        camera = new OrthographicCamera(MENU_VIEWPORT_WIDTH,MENU_VIEWPORT_HEIGHT);
        camera.position.set(this.stage.getWidth() / 2, this.stage.getHeight() / 2, 0);

        AtlasRegion atlasRegion = atlas.findRegion( "battle/ui/field-3x3" );
        battleoverlay = new Image(atlasRegion);
        battleoverlay.setX( stage.getWidth()/2 - battleoverlay.getWidth()/2 );
        battleoverlay.setY( stage.getHeight()/2 -battleoverlay.getHeight()- 25); 
        stage.addActor(battleoverlay);

        stateTime = 0;

        characters = new ArrayList<MapCharacter>();

        hud = new BattleHUD(this.stage.getWidth(), stage.getHeight());
        hud.xButton.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(6,true); 
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                buttonPress(6,false); 
            }
        });
        hud.bButton.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(7,true); 
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                buttonPress(7,false); 
            }
        });

        hud.aButton.addListener(new PressedListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                buttonPress(8,true); 
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                buttonPress(8,false); 
            }
        });
        if(Gdx.app.getVersion()>0||Unsealed.DEBUG==true){
            hud.leftTrigger.addListener(new PressedListener() {
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
            hud.rightTrigger.addListener(new PressedListener() {
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


            hud.dPadDown.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(1,true); 
                    return true;
                }
            });

            hud.dPadUp.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(0,true); 
                    return true;
                }
            });

            hud.dPadLeft.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(2,true); 
                    return true;
                }
            });

            hud.dPadRight.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(3,true); 
                    return true;
                }
            });
        }
        grid = new BattleGrid(this.stage.getWidth(), stage.getHeight(),6,3);

        hero = new BattleHero(getAtlas(),150);
        hero.setGrid(1,1);
        grid.assignEntity(hero);     

        if(!scriptedBattle)
            grid.spawnEnemies(bonus);

        roundLabel = new Label("Round "+round,getSkin());
        roundLabel.setX(350);
        roundLabel.setY(350);
        this.stage.addActor(roundLabel);

        restartButton = new TextButton("Restart?", getSkin());
        restartButton.setY(250);
        restartButton.setX(320);
        restartButton.setWidth(100);
        restartButton.setHeight(50);
        restartButton.setVisible(false);
        restartButton.setDisabled(true);
        restartButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent arg0, float arg1, float arg2) {
                hero = new BattleHero(getAtlas(),150);
                hero.setGrid(1,1);
                hero.reset();
                grid.reset();
                grid.assignEntity(hero);     
                bonus = 1;
                round = 1;

                restartButton.setVisible(false);
                restartButton.setDisabled(true);
                grid.spawnEnemies(bonus);

            }
        });
        this.stage.addActor(restartButton);

        NinePatch patch = getAtlas().createPatch("maps/dialog-box");

        textBoxStyle = new StyledTable.TableStyle();
        textBoxStyle.background = new NinePatchDrawable(patch);
        textBoxStyle.font = new BitmapFont();
        textBoxStyle.padX = 8;
        textBoxStyle.padY = 4;


        dialog = new TextBox("", textBoxStyle);
        dialog.setWidth(Gdx.graphics.getWidth());
        dialog.setHeight(Gdx.graphics.getHeight() / 8);
        dialog.setVisible(false);

        hud.addActor(dialog);
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage,hud));
    } 

    public void checkScene(float delta){

    }

    @Override
    public void render(float delta) {
        // (1) process the game logic

        // update the actors
        stage.act( delta );

        // (2) draw the result

        if(scriptedBattle)
            checkScene(delta);

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        camera.position.set(1300,1220, 0);
        camera.update();
        tileMapRenderer.render(camera);

        stage.draw();
        roundLabel.setText("Round "+round+"");

        for(MapCharacter character : characters){
            if(character.isVisible())
                character.draw(stage.getSpriteBatch(), 1);
        }

        if(!scriptedBattle)
            if(BattleGrid.checkState() == BattleGrid.battleWon){
                bonus++;
                grid.spawnEnemies(bonus);
            }else if(BattleGrid.checkState() == BattleGrid.battleLost){
                roundLabel.setX(330);
                roundLabel.setText("You Lost at Round "+round+"!");
                restartButton.setVisible(true);

            }

        grid.act(delta);
        hud.act(delta);
        hud.fillMana(hero);

        grid.draw();
        hud.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!disableInput)
            switch(keycode) {
                case Input.Keys.RIGHT:
                    buttonPress(3,true);
                    return true;
                case Input.Keys.D:
                    buttonPress(3,true);
                    return true;
                case Input.Keys.LEFT:
                    buttonPress(2,true);
                    return true;
                case Input.Keys.A:
                    buttonPress(2,true);
                    return true;
                case Input.Keys.UP:
                    buttonPress(0,true);
                    return true;
                case Input.Keys.W:
                    buttonPress(0,true);
                    return true;
                case Input.Keys.DOWN:
                    buttonPress(1,true);
                    return true;
                case Input.Keys.S:
                    buttonPress(1,true);
                    return true;
                case Input.Keys.U:
                    buttonPress(4,true);
                    return true;
                case Input.Keys.O:
                    buttonPress(5,true);
                    return true;
                case Input.Keys.J:
                    buttonPress(6,true);
                    return true;
                case Input.Keys.K:
                    buttonPress(7,true);
                    return true;
                case Input.Keys.L:
                    buttonPress(8,true);
                    return true;
            }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        if(!disableInput)
        switch(keycode) {
            case Input.Keys.U:
                buttonPress(4,false);
                return true;
            case Input.Keys.O:
                buttonPress(5,false);
                return true;
            case Input.Keys.J:
                buttonPress(6,false);
                return true;
            case Input.Keys.K:
                buttonPress(7,false);
                return true;

            case Input.Keys.L:
                buttonPress(8,false);
                return true;
            case Input.Keys.BACK:
                game.setScreen(new MenuScreen(game));
                return true;
            case Input.Keys.ESCAPE:
                game.setScreen(new MenuScreen(game));
                return true;
        }
        return false;
    }

    /**
     * 0 = up, 1 = down, 2 = left, 3 = right
     * 4 = shield, 5 = attack
     * 6 = Skill1. 7 = Skill2. 8 = Skill3.
     * @param direction
     */
    public void buttonPress(int button,boolean pressed){
        if(!disableInput)
        switch(button){
            //TODO: Hero moves at a fixed pixel rate (A.k.a. magic numbers). It should instead move based on screen width.
            case 0:{ // Up
                if(hero.getState()!=BattleEntity.stateBlocking){
                    if((hero.getGridYInt()-1>-1)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt(), hero.getGridYInt()-1);

                    }
                }
                break;
            }
            case 1:{ // Down
                if(hero.getState()!=BattleEntity.stateBlocking){
                    if((hero.getGridYInt()+1<3)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt(), hero.getGridYInt()+1);

                    }
                }
                break;
            }
            case 2:{ // Left
                if(hero.getState()!=BattleEntity.stateBlocking){
                    if((hero.getGridXInt()-1>-1)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt()-1, hero.getGridYInt());

                    }
                }
                break;
            }
            case 3:{ // Right
                if(hero.getState()!=BattleEntity.stateBlocking){
                    if((hero.getGridXInt()+1<3)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt()+1, hero.getGridYInt());
                    }
                }
                break;
            }
            case 4:{ // Shield
                if(pressed){
                    if(hero.getState()!=BattleEntity.stateBlocking){
                        hero.setState(BattleEntity.stateBlocking);

                        hero.setMana(hero.getMana()-1);
                    }
                }else{
                    hero.setState(BattleEntity.stateIdle);
                }
                break;
            }
            case 5:{ // Attack
                if(pressed){

                }else{
                    if(hero.getState()==BattleEntity.stateIdle){
                        if(hero.getMana()>=1){
                            hero.magicType=0;
                            hero.setMana(hero.getMana()-1);
                            hero.setState(BattleEntity.stateAttacking);
                        }
                    }
                }
                break;
            }
            case 6:{ // Skill1
                if(pressed){
                    if(hero.getState()==BattleEntity.stateIdle){
                        if(hero.getMana()>=5){
                            hero.setMana(hero.getMana()-5);
                            hero.setState(BattleEntity.stateAltAttacking);
                        }
                    }
                }else{

                }
                break;
            }
            case 7:{ // Skill2
                if(pressed){
                    if(hero.getState()==BattleEntity.stateIdle){
                        if(hero.getMana()>=5){
                            hero.setMana(hero.getMana()-5);
                            hero.magicType=2;
                            hero.setState(BattleEntity.stateAttacking);
                        }
                    }
                }else{

                }
                break;
            }
            case 8:{ // Skill3
                if(pressed){
                    if(hero.getState()==BattleEntity.stateIdle){
                        if(hero.getMana()>=5){
                            hero.setMana(hero.getMana()-5);
                            hero.magicType=1;
                            hero.setState(BattleEntity.stateAttacking);
                        }
                    }
                }else{

                }
                break;
            }
        }
    }
}
