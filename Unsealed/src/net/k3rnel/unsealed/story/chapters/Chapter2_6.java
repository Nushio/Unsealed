package net.k3rnel.unsealed.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Shura;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter2_6 extends AbstractChapter {

    final int[] overLayers = { 12 };

    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_6(Unsealed game) {
        super(game);
        mapname="Asia Town";
    }

    @Override
    public void show() {
        super.show();

        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1100,100);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirUp);
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
                        centerCamera(character);
                        character.getColor().a = 0;
                        character.setWalking(false);
                        actions = sequence(fadeIn(0.95f),delay(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(1);
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 1:
                        dialog.setText("Lidia: Finally made it to Hikari. ");
                        dialog.setVisible(true);
                       break;
                    case 2:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        act = 3;
                        break;
                    case 3:
                        if(character.getY()<800){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            character.setWalking(false);
                            act = 4;
                        }
                        break;
                    case 4:
                        dialog.setText("Lidia: Everything looks gorgeous!");
                        dialog.setVisible(true);
                        break;
                    case 5:
                        dialog.setVisible(false);
                        character.setDirection(MapCharacter.dirUp);
                        character.setWalking(true);
                        act = 6;
                        break;
                    case 6:
                        if(character.getY()<1600){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            camera.zoom = 2f;
                            camera.update();
                            character.setDirection(MapCharacter.dirUp);
                            character.setWalking(false);
                            act = 6;
                            centerCamera(character);
//                            character.setX(character.getX()-1);
                        }
                        break;
                        
                   
                }
            }
           
            character.act(delta);
            if(character.isVisible())
                character.draw(stage.getSpriteBatch(), 1);
        }
        stage.getSpriteBatch().end();
        tileMapRenderer.render(camera,overLayers);
        if(dialog.isVisible()){
            hud.act(delta);
            hud.draw();
        }
        
    }   

 
}
