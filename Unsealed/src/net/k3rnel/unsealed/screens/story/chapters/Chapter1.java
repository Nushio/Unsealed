package net.k3rnel.unsealed.screens.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.objects.MapCharacter;
import net.k3rnel.unsealed.screens.story.characters.Lidia;
import net.k3rnel.unsealed.screens.story.characters.Whisperer;

public class Chapter1 extends AbstractChapter {

      
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
    }
    @Override
    public void render(float delta) {
        super.render(delta);
     
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
}
