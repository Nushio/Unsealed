package net.k3rnel.unsealed.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.LevelSelectScreen;
import net.k3rnel.unsealed.story.characters.FireLionMap;
import net.k3rnel.unsealed.story.characters.Kid;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter1_7 extends AbstractChapter {


    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1_7(Unsealed game) {
        super(game);
        mapname="TownOneNight";
    }

    @Override
    public void show() {
        super.show();

        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1524,1608);
        tmpChar.setDirection(MapCharacter.dirLeft);
        tmpChar.updateAnimation();
        characters.add(tmpChar);

        tmpChar = new Kid(getAtlas(),0);
        tmpChar.setPosition(1024,1608);
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.setWalking(true);
        characters.add(tmpChar);

        tmpChar = new FireLionMap(getAtlas());
        tmpChar.setPosition(1350,1590);
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
                        dialog.setText("Lidia: It's always hard to say goodbye.\n");
                        dialog.setVisible(true);
                        break;
                    case 1:
                        dialog.setText("Mimi: Wait! Don't go!");
                        act = 2;
                        break;
                    case 26:
                        character.setDirection(MapCharacter.dirRight);
                        character.setWalking(true);
                        act = 27;
                        break;
                    case 27:
                        if(character.getX()<1730){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            actions = sequence(fadeOut(0.85f));
                            character.addAction(actions);
                            act = 28;
                        }
                        break;
                    case 28:
                        game.setScreen( new LevelSelectScreen( game ) );
                        break;
                }
            }
            if(character instanceof Kid){
                switch(act){
                    case 2:
                        if(character.getX()<1480){
                            character.setX(character.getX()+2);
                        }else{
                            character.setWalking(false);
                        }
                        break;
                    case 3:
                        if(character.getX()!=1480){
                            act = 2;
                        }else{
                           act = 4;
                        }
                        break;
                    case 4:
                        dialog.setText("Lidia: Sorry, but I already stayed here too long");
                        break;
                    case 5:
                        dialog.setText("Mimi: Thank you for everything you've done.\n" +
                        		"You really changed everyone in town.");
                        break;
                    case 6:
                        dialog.setText("Lidia: I'm glad to hear that. ^_^\n" +
                        		"Just don't forget the rules!");
                        break;
                    case 7:
                        dialog.setText("Mimi: I know! I know! The Four Freedoms of Magic!");
                        break;
                    case 8:
                        dialog.setText("Mimi: The Freedom to Cast any Spell. \n" +
                        		"The Freedom to study the Spell, and weave it into something new!\n" +
                        		"The Freedom to teach others how to do the Spell\n" +
                        		"And the Freedom to let others pass on the knowledge");
                        break;
                    case 9:
                        dialog.setText("Mimi: We know about the Pixies now. \n" +
                        		"We'll make sure they aren't forgotten. We'll fight for them!");
                        break;
                    case 10:
                        dialog.setText("Lidia: Then my job here is done.");
                        break;
                    case 11:
                        dialog.setText("Mimi: Before you go, there's one thing I'd like to show you... ");
                        break;
                    case 12:
                        dialog.setVisible(false);
                        character.setDirection(MapCharacter.dirLeft);
                        character.setWalking(true);
                        act = 13;
                        break;
                    case 13:
                        if(character.getX()>1300){
                            character.setX(character.getX()-1);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            character.setWalking(false);
                            actions = sequence(color(Color.RED),delay(0.3f),color(Color.WHITE));
                            character.addAction(actions);
                            act = 14;
                        }
                        break;
                    case 15:
                        actions = sequence(delay(0.6f),color(Color.RED),delay(0.3f),color(Color.WHITE));
                        character.addAction(actions);
                        act = 16;
                        break;
                    case 17:
                        actions = sequence(delay(0.6f),color(Color.RED),delay(0.3f),color(Color.WHITE));
                        character.addAction(actions);
                        act = 18;
                        break;
                    case 19:
                        actions = sequence(delay(0.6f),color(Color.RED),delay(0.3f),color(Color.WHITE));
                        character.addAction(actions);
                        act = 20;
                        break;
                    case 21:
                        dialog.setText("Lidia: Wow! Great job!");
                        dialog.setVisible(true);
                        break;
                    case 22:
                        dialog.setText("Mimi: Thank you! I've been working very hard!\n" +
                        		"I wanted to give you something in return for all the things you've done for us");
                        break;
                    case 23:
                        dialog.setText("Lidia learned Fire Lion! You can now use that Skill in battle");
                        break;
                    case 24:
                        dialog.setText("Lidia: Thank you Mimi, I'll never forget your town");
                        break;
                    case 25:
                        dialog.setVisible(false);
                        act = 26;
                        break;
                }
            }
            if(character instanceof FireLionMap){
                switch(act){
                    case 14:
                        character.setVisible(true);
                        if(character.getX()<1450){
                            character.setX(character.getX()+2);
                        }else{
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirDown);
                            setAct(15);
                            character.setPosition(1350,1590);
                        }
                        break;
                    case 16:
                        character.setVisible(true);
                        if(character.getX()<1450){
                            character.setX(character.getX()+2);
                        }else{
                            character.setPosition(1350,1590);
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirLeft);
                            setAct(17);
                        }
                        break;
                    case 18:
                        character.setVisible(true);
                        if(character.getX()<1450){
                            character.setX(character.getX()+2);
                        }else{
                            character.setPosition(1350,1590);
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirRight);
                            setAct(19);
                        }
                        break;
                    case 20:
                        character.setVisible(true);
                        if(character.getX()<1450){
                            character.setX(character.getX()+1);
                        }else{
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirRight);
                            setAct(21);
                        }
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
