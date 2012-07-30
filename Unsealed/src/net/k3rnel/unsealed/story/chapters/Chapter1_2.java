package net.k3rnel.unsealed.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.story.characters.FireLionMap;
import net.k3rnel.unsealed.story.characters.Kid;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter1_2 extends AbstractChapter {


    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1_2(Unsealed game) {
        super(game);
        mapname="TownOne";
    }

    @Override
    public void show() {
        super.show();

        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1924,1608);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        
        tmpChar = new Kid(getAtlas(),0);
        tmpChar.setPosition(1040,970);
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        
        tmpChar = new Kid(getAtlas(),1);
        tmpChar.setDirection(MapCharacter.dirUp);
        tmpChar.setPosition(1100,900);
        characters.add(tmpChar);
        
        tmpChar = new Kid(getAtlas(),2);
        tmpChar.setPosition(1000,930);
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        
        tmpChar = new FireLionMap(getAtlas());
        tmpChar.setPosition(1080,950);
        tmpChar.setDirection(MapCharacter.dirUp);
        tmpChar.updateAnimation();
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
                        centerCamera(character);
                        character.setDirection(MapCharacter.dirLeft);
                        character.getColor().a = 0;
                        character.setWalking(false);
                        
                        actions = sequence(fadeIn(0.75f),delay(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(1);
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 1:
                        dialog.setText("Lidia: So this is New Lion? I wonder if anyone has noticed any new skills they can use...");
                        dialog.setVisible(true);
                        break;
                    case 2:
                        character.setWalking(true);
                        if(character.getX()>1050){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            dialog.setText("Lidia: Hmm, there are some kids nearby. Better check them out.");
                        }
                        break;
                    case 3:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        character.setDirection(MapCharacter.dirRight);
                        setAct(4);
                        break;
                    case 4:
                        if(character.getX()<1140){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirDown);
                            setAct(5);
                        }
                        break;
                    case 5:
                        if(character.getY()>1000){
                            character.setY(character.getY()-1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            setAct(6);
                        }
                    case 6:
                        if(character.getY()==1000){
                            dialog.setText("Mimi: Watch this! Watch this!");
                            dialog.setVisible(true);
                        }
                        break;
                }
                
            }
            if(character instanceof Kid){
                if(((Kid)character).kid==0){
                    switch(act){
                        case 7:
                            dialog.setVisible(false);
                            actions = sequence(color(Color.RED),delay(0.3f),color(Color.WHITE));
                            character.addAction(actions);
                            setAct(8);
                            break;
                    }
                }
                if(((Kid)character).kid==1){
                   
                }
                if(((Kid)character).kid==2){
                }
            }
            if(character instanceof FireLionMap){
                switch(act){
                    case 8:
                        character.setVisible(true);
                        if(character.getX()<1130){
                            character.setX(character.getX()+2);
                        }else{
                            character.setVisible(false);
                            setAct(9);
                        }
                        break;
                    case 9:
                        dialog.setText("George: Wow! That's amazing!");
                        dialog.setVisible(true);
                        break;
                    case 10:
                        dialog.setText("Maria: Do it again! Do it again!");
                        break;
                    case 11:
                        dialog.setText("Lidia: Wait!\n" +
                        		"Magic can be dangerous, specially at first!");
                        break;
                    case 12:
                        dialog.setText("George: Who are you? Where did you come from?");
                        break;
                    case 13:
                        dialog.setText("Mimi: Look at those clothes! She's not from around here!");
                        break;
                    case 14:
                        dialog.setText("Lidia: You can call me Lidia, and you're right. I'm a Traveller.\n" +
                        		"We travel through the lands, guiding those that want to hear us");
                        break;
                    case 15:
                        dialog.setText("Maria: Can you teach us how to do magic?\n" +
                        		"Mimi: Yeah, can you? Pleeeeeeasseeeeeee!");
                        break;
                    case 16:
                        dialog.setText("Lidia: Sure. Stand back, everyone.");
                        break;
                    case 17:
                        dialog.setVisible(false);
                        setAct(18);
                        break;
                    case 18:
                        game.setScreen( new Chapter1_3( game ) );
                        break;
                }
            }
            character.act(delta);
            if(character.isVisible())
                character.draw(stage.getSpriteBatch(), 1);
        }
        stage.getSpriteBatch().end();
        
        if(dialog.isVisible()){
            hud.act(delta);
            hud.draw();
        }
    }   

}
