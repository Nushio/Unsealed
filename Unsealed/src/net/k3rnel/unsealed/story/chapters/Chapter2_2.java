package net.k3rnel.unsealed.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Shura;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter2_2 extends AbstractChapter {


    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_2(Unsealed game) {
        super(game);
        mapname="RouteOne";
    }

    @Override
    public void show() {
        super.show();

        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1906,1030);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirLeft);
        characters.add(tmpChar);
        
        tmpChar = new Shura(getAtlas());
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.setPosition(1350,1090);
        tmpChar.updateAnimation();
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
                        character.setWalking(false);
                        break;
                    case 1:
                        dialog.setText("Lidia: That was a long trip through the desert");
                        dialog.setVisible(true);
                        break;
                    case 2:
                        dialog.setText("Lidia: Altera seems to have very few grasslands.");
                        break;
                    case 3:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        if(character.getX()>1760){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            act = 4;
                        }
                        break;
                    case 4:
                        if(character.getY()<1073){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 5;
                        }
                        break;
                    case 5:
                        if(character.getX()>1710){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 6;
                        }
                        break;
                    case 6:
                        if(character.getX()>1685){
                            character.setX(character.getX()-1);
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 7;
                        }
                        break;
                    case 7:
                        if(character.getX()>1600){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 8;
                        }
                        break;
                    case 8:
                        if(character.getX()>1575){
                            character.setX(character.getX()-1);
                            character.setY(character.getY()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 9;
                        }
                        break;
                    case 9:
                        if(character.getX()>1505){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 10;
                        }
                        break;
                    case 10:
                        if(character.getX()>1490){
                            character.setX(character.getX()-1);
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 11;
                        }
                        break;
                    case 11:
                        if(character.getX()>1480){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            character.setWalking(false);
                            act = 12;
                        }
                        break;
                    case 12:
                        dialog.setText("Lidia: I didn't expect to meet you so soon Shura!\n" +
                        		"Didn't you say you were heading for the Terrex Mountain?\n");
                        dialog.setVisible(true);
                        break;
                    case 13:
                        dialog.setText("Shura: Well, it's none of your business.");
                        break;
                    case 14:
                        dialog.setText("Lidia: You're lost, aren't you? \n" +
                        		"Hah, I bet you're lost!");
                        break;
                    case 15:
                        dialog.setText("Shura: Oh, shut it, Terius. I'm hunting Clams now");
                        break;
                    case 16:
                        dialog.setText("Lidia: Alright, well, I'm heading for Hikari, would you like to join me?");
                        break;
                    case 17:
                        dialog.setText("Shura: Are you expecting another angry mob, like the one at Marblehead?\n" +
                        		"Should I get my pitchfork and torch and join the crowd before you arrive?");
                        break;
                    case 18:
                        dialog.setText("Lidia: I'm not expecting any angry mobs. The town is deserted. \n" +
                        		"I hear it's been taken over by Ghosts!");
                        break;
                    case 19:
                        dialog.setText("Shura: Whatever. Just don't expect me to bail you out like last time, OK?");
                        break;
                    case 20:
                        dialog.setText("Lidia: You should've seen the folk at New Lion Town\n" +
                        		"They really enjoyed casting spells, and do appreciate what I've done");
                        break;
                    case 21:
                        dialog.setText("Shura: Still teaching them the Four Freedoms? You're crazier than I thought.");
                        break;
                    case 22:
                        dialog.setText("Lidia: The Four Freedoms are what keeps everyone in line at Distropolis\n" +
                        		"They're the reason I became a Traveler!");
                        break;
                    case 23:
                        dialog.setText("Shura: That, and the voice inside your head...");
                        break;
                    case 24:
                        dialog.setText("Lidia: ....She is real... ");
                        break;
                    case 25:
                        dialog.setText("Shura: Listen, I'm sure she is, but I'd rather not discuss wild theories. \n" +
                        		"Catch you later!");
                        break;
                    case 27:
                        dialog.setVisible(true);
                        dialog.setText("Lidia: *SIGH*");
                        break;
                    case 28:
                        dialog.setVisible(false);
                        act = 29;
                        character.setWalking(true);
                        break;
                    case 29:
                        if(character.getX()>1300){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            act = 30;
                        }
                        break;
                    case 30:
                        if(character.getY()<1200){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            act = 31;
                        }
                        break;
                    case 31:
                        game.setScreen(new Chapter2_3(game));
                        break;
                }
            }
            if(character instanceof Shura){
                switch(act){
                case 26:
                    dialog.setVisible(false);
                    character.setDirection(MapCharacter.dirDown);
                    character.setWalking(true);
                    if(character.getY()>900){
                        character.setY(character.getY()-2);
                    }else{
                        act = 27;
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
