package net.k3rnel.unsealed.screens.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.objects.MapCharacter;
import net.k3rnel.unsealed.objects.MapWalkAction;
import net.k3rnel.unsealed.objects.StyledTable;
import net.k3rnel.unsealed.screens.AbstractScreen;

public class Chapter1 extends AbstractScreen {

    TiledMap map;
    TileAtlas atlas;
    TileMapRenderer tileMapRenderer;
    OrthographicCamera camera;
    List<MapCharacter> characters;
    MapCharacter tmpChar;
    private StyledTable.TableStyle textBoxStyle;
    protected int act;
    SequenceAction actions;
    
    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1(Unsealed game) {
        super(game);

    }
    @Override
    public void show() {
        super.show();
        // Load the tmx file into map
        map = TiledLoader.createMap(Gdx.files.internal("assets/map-atlases/RouteOneDungeon.tmx"));
        
        act = 0;
        // Load the tiles into atlas
        atlas = new TileAtlas(map, Gdx.files.internal("assets/map-atlases/"));

        // Create the renderer
        tileMapRenderer = new TileMapRenderer(map, atlas, map.width, map.height);

        // Create the camera
        camera = new OrthographicCamera(MENU_VIEWPORT_WIDTH,MENU_VIEWPORT_HEIGHT);
        camera.position.set(this.stage.getWidth() / 2, this.stage.getHeight() / 2, 0);
        
        NinePatch patch = getAtlas().createPatch("maps/dialog-box");
        
        textBoxStyle = new StyledTable.TableStyle();
        textBoxStyle.background = new NinePatchDrawable(patch);
        textBoxStyle.font = new BitmapFont();
        textBoxStyle.padX = 8;
        textBoxStyle.padY = 4;
        
        characters = new ArrayList<MapCharacter>();
        tmpChar = new Lidia();
        tmpChar.setPosition(115,140);
        tmpChar.setDirection(MapCharacter.dirDown);
        actions = new SequenceAction();
        characters.add(tmpChar);
        tmpChar = new Whisperer();
        tmpChar.setDirection(MapCharacter.dirDown);
        tmpChar.addAction(fadeIn(0.75f));
        tmpChar.setPosition(1330,440);
        tmpChar.setVisible(false);
        characters.add(tmpChar);
        stage.setCamera(camera);
    }
    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        super.render(delta);
        tileMapRenderer.render(camera);
        stage.getSpriteBatch().begin();
        //This is probably the bestest "Scene Director" ever made. 
        //Valve should totally hire me. 
        for(MapCharacter character : characters){
            if(character instanceof Lidia){
                switch(act){
                    case 0:
                        actions = sequence(fadeIn(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(1);
                            }
                        }));
                        character.addAction(actions);
                        character.setWalking(true);
                        break;
                    case 1:
                        if(character.getY()>70){
                            character.setY(character.getY()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            setAct(2);
                        }
                        break;
                    case 2:
                        if(character.getX()<491){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(3);
                        }
                        break;
                    case 3:
                        if(character.getY()<340){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            setAct(4);
                        }
                        break;
                    case 4:
                        if(character.getX()<770){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(5);
                        }
                        break;
                    case 5:
                        if(character.getY()<400){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            setAct(6);
                        }
                        break;
                    case 6:
                        if(character.getX()<1050){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(7);
                        }
                        break;
                    case 7:
                        if(character.getY()<430){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            setAct(8);
                        }
                        break;
                    case 8:
                        if(character.getX()<1220){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirDown);
                            setAct(9);
                        }
                        break;
                    case 9:
                        if(character.getY()>410){
                            character.setY(character.getY()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            setAct(10);
                        }
                        break;
                    case 10:
                        if(character.getX()<1330){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                           
                            character.setDirection(MapCharacter.dirUp);
                            character.setWalking(false);
                            setAct(11);
                        }
                        break;
                    
                }
            }
            character.act(delta);
            character.draw(stage.getSpriteBatch(), 1);
        }
        stage.getSpriteBatch().end();
    }
    
    public void centerCamera(MapCharacter character) {
        float x = character.getX();
        float y = character.getY();
        float halfW = Gdx.graphics.getWidth() / 2;
        float halfH = Gdx.graphics.getHeight() / 2;
        float mapW = map.width*map.tileWidth;
        float mapH = map.height*map.tileHeight;

        if (x < halfW)
            x = halfW;
        else if (x > mapW - halfW)
            x = mapW - halfW;

        if (y < halfH) {
            y = halfH;
        } else if (y > mapH - halfH) {
            y = mapH - halfH;
        }
        camera.position.set(x, y, 0);
        camera.update();
    }
    
    private void setAct(int act){
        this.act = act;
    }
}
