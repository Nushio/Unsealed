package net.k3rnel.unsealed.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Pixie;
import net.k3rnel.unsealed.story.characters.Whisperer;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter1_1 extends AbstractChapter {


    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1_1(Unsealed game) {
        super(game);
        mapname="RouteOneDungeon";
    }

    @Override
    public void show() {
        super.show();

        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(115,140);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        tmpChar = new Whisperer(getAtlas());
        tmpChar.updateAnimation();
        tmpChar.addAction(fadeIn(0.75f));
        tmpChar.setPosition(291,470);
        tmpChar.setVisible(false);
        characters.add(tmpChar);
        tmpChar = new Pixie(getAtlas());
        tmpChar.updateAnimation();
        tmpChar.setPosition(82,660);
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
                        character.getColor().a = 0;
                        character.setWalking(false);
                        actions = sequence(fadeIn(0.75f),delay(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(1);
                            }
                        }));
                        character.addAction(actions);
                        character.setWalking(true);
                        break;
                    case 1:
                        if(character.getY()>120){
                            character.setY(character.getY()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            setAct(2);
                        }
                        break;
                    case 2:
                        if(character.getX()<291){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(3);
                        }
                        break;
                    case 3:
                        if(character.getY()<400){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            character.setWalking(false);
                            setAct(4);
                        }
                        break;
                    case 15:
                        if(character.getY()<470){
                            character.setWalking(true);
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            setAct(16);
                        }
                        break;
                    case 16:
                        if(character.getX()>70){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(17);
                        }
                        break;
                    case 17:
                        if(character.getY()<580){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            setAct(18);
                        }
                        break;
                    case 18:
                        dialog.setText("Lidia: You poor thing....\n" +
                        		"Awaken from your slumber. Let the seal be broken.\n" +
                        		"I, Lidia Terious, Unseal you!");
                        dialog.setVisible(true);
                        break;
                    case 19:
                        dialog.setVisible(false);
                        setAct(20);
                        break;

                }
            }
            if(character instanceof Whisperer){
                switch(act){
                    case 4:
                        character.getColor().a = 0;
                        actions = sequence(fadeIn(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(5);
                            }
                        }));
                        character.addAction(actions);
                        character.setVisible(true);
                        break;
                    case 5:
                        dialog.setText("Lidia: I was wondering when you'd show up. ");
                        dialog.setVisible(true);
                        break;
                    case 6:
                        dialog.setText("Whisperer: You know I'm just a Whisper away from being summoned");
                        break;
                    case 7:
                        dialog.setText("Lidia: The next Pixie is up ahead, I can sense it.");
                        break;
                    case 8:
                        dialog.setText("Whisperer: It is calling out for you. It's been sealed for too long. ");
                        break;
                    case 9:
                        dialog.setText("Lidia: If I was sealed for 300 years I wouldn't just whisper for help... I'd shout too.");
                        break;
                    case 10:
                        dialog.setText("Lidia: It's incredible how the locals won't do anything to save the Pixies.\n" +
                        		"It's always up to a Traveler to set things right");
                        break;
                    case 11:
                        dialog.setText("Whisperer: The Free Spellweaver Foundation doesn't grant just great powers...");
                        break;
                    case 12:
                        dialog.setText("Lidia: They also give us the opportunity to use them to help others, I know. ");
                        break;
                    case 13:
                        dialog.setText("Whisperer: The Pixie awaits. We can continue the discussion later");
                        break;
                    case 14:
                        dialog.setVisible(false);
                        actions = sequence(fadeOut(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(15);
                            }
                        }));
                        character.addAction(actions);
                        break;
                }
            }
            if(character instanceof Pixie){
                switch(act){
                    case 20:
                        actions = sequence(fadeOut(0.75f),
                                run(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAct(21);
                                    }
                                }),fadeIn(0.75f));
                        character.addAction(actions);
                        break;
                    case 21:
                        character.setWalking(true);
                        setAct(22);
                        break;
                    case 22:
                        if(character.getX()>31){
                            character.setX(character.getX()-3);

                        }else if(character.getY()>530){
                            character.setY(character.getY()-3);
                        }else{
                            setAct(23);
                        }
                        break;
                    case 23:
                        if(character.getX()<141){
                            character.setX(character.getX()+3);
                        }else if(character.getY()<660){
                            character.setY(character.getY()+3);
                        }else{
                            setAct(24);
                        }
                        break;
                    case 24:
                        dialog.setText("Pixie: Wheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee!" +
                        		"Thank you, Traveler!");
                        dialog.setVisible(true);
                        break;
                    case 25:
                        dialog.setText("Pixie: I will restore their Magic to everyone in the area.\n" +
                        		"I'm Freeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee!!!!");
                        dialog.setVisible(true);
                        break;
                    case 26:
                        dialog.setVisible(false);
                        if(character.getY()<900){
                            character.setY(character.getY()+5);
                        }else{
                            setAct(27);
                        }
                        break;
                    case 27:
                        dialog.setText("Lidia: I guess it's time to head to the town");
                        dialog.setVisible(true);
                        break;
                    case 28:
                        game.setScreen( new Chapter1_2( game ) );
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
