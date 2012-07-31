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
package net.k3rnel.unsealed.story.chapters;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.AbstractScreen;
import net.k3rnel.unsealed.story.helpers.MapCharacter;
import net.k3rnel.unsealed.story.helpers.StyledTable;
import net.k3rnel.unsealed.story.helpers.TextBox;

public class AbstractChapter extends AbstractScreen {

    TiledMap tileMap;
    TileAtlas tileAtlas;
    TileMapRenderer tileMapRenderer;
    OrthographicCamera camera;
    List<MapCharacter> characters;
    MapCharacter tmpChar;
    TextBox dialog;
    StyledTable.TableStyle textBoxStyle;
    protected int act;
    SequenceAction actions;
    String mapname;
    Stage hud = new Stage();
    public AbstractChapter(Unsealed game) {
        super(game);

    }
    public void show() {
        super.show();
        act = 0;
        // Load the tmx file into map
        tileMap = TiledLoader.createMap(Gdx.files.internal("map-atlases/"+mapname+".tmx"));

        // Load the tiles into atlas
        tileAtlas = new TileAtlas(tileMap, Gdx.files.internal("map-atlases/"));

        // Create the renderer
        tileMapRenderer = new TileMapRenderer(tileMap, tileAtlas, tileMap.width, tileMap.height);

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

        stage.setCamera(camera);

        dialog = new TextBox("", textBoxStyle);
        dialog.setWidth(Gdx.graphics.getWidth());
        dialog.setHeight(Gdx.graphics.getHeight() / 4);
        dialog.setVisible(false);

        hud.addActor(dialog);
        Gdx.input.setInputProcessor(new InputMultiplexer(this,hud) );
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        tileMapRenderer.render(camera);
//        textBoxStyle.font.setScale(1.2f);
//        textBoxStyle.font.setScale(2f);
//        dialog = new TextBox("", textBoxStyle);
//        dialog.setWidth(Gdx.graphics.getWidth());
//        dialog.setHeight(Gdx.graphics.getHeight() / 4);
    }

    public void centerCamera(MapCharacter character) {
        float x = character.getX();
        float y = character.getY();
        float halfW = (MENU_VIEWPORT_WIDTH*camera.zoom) / 2;
        float halfH = (MENU_VIEWPORT_HEIGHT*camera.zoom) / 2;
        float mapW = tileMap.width*tileMap.tileWidth;
        float mapH = tileMap.height*tileMap.tileHeight;

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

    public void setAct(int act){
        this.act = act;
    }
    
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if(dialog.isVisible()){
            setAct(act+1);
        }
        return super.touchUp(x, y, pointer, button);
    }
    @Override
    public boolean keyUp(int keycode) {
        if(dialog.isVisible()){
            switch(keycode) {
                case Input.Keys.U:
                    setAct(act+1);
                    return true;
                case Input.Keys.O:
                    setAct(act+1);
                    return true;
                case Input.Keys.J:
                    setAct(act+1);
                    return true;
                case Input.Keys.K:
                    setAct(act+1);
                    return true;
                case Input.Keys.L:
                    setAct(act+1);
                    return true;
                case Input.Keys.SPACE:
                    setAct(act+1);
                    return true;
                case Input.Keys.I:
                    setAct(act+1);
                    return true;
            }
        return false;
        }
        return super.keyUp(keycode);
    }
}
