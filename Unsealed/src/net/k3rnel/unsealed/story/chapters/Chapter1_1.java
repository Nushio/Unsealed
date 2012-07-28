package net.k3rnel.unsealed.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

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

        tmpChar = new Lidia();
        tmpChar.setPosition(115,140);
        tmpChar.updateAnimation();
        actions = new SequenceAction();
        characters.add(tmpChar);
        tmpChar = new Whisperer();
        tmpChar.updateAnimation();
        tmpChar.addAction(fadeIn(0.75f));
        tmpChar.setPosition(291,470);
        tmpChar.setVisible(false);
        characters.add(tmpChar);
        tmpChar = new Pixie();
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
                    case 11:
                        if(character.getY()<470){
                            character.setWalking(true);
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            setAct(12);
                        }
                        break;
                    case 12:
                        if(character.getX()>70){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(13);
                        }
                        break;
                    case 13:
                        if(character.getY()<580){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            setAct(14);
                        }
                        break;
                    case 14:
                        dialog.setText("Lidia: You poor thing....");
                        dialog.setVisible(true);
                        break;
                    case 15:
                        dialog.setVisible(false);
                        setAct(16);
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
                        dialog.setText("Lidia: I was wondering when you'd show up. \n" +
                                "I still don't understand why the citizens don't fight for the use of magic.");
                        dialog.setVisible(true);
                        break;
                    case 6:
                        dialog.setText("Whisperer: It's been 3 generations since Pixies disappeared.  ");
                        break;
                    case 7:
                        dialog.setText("Lidia: You mean locked up? They didn't didn't leave!\n" +
                                "\n" +
                                "Didn't you try and talk to them about it? ");
                        break;
                    case 8:
                        dialog.setText("Whisperer: They didn't want to listen");
                        break;
                    case 9:
                        dialog.setText("Lidia: You could've shouted...");
                        break;
                    case 10:
                        dialog.setVisible(false);
                        actions = sequence(fadeOut(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(11);
                            }
                        }));
                        character.addAction(actions);
                        break;
                }
            }
            if(character instanceof Pixie){
                switch(act){
                    case 16:
                        actions = sequence(fadeOut(0.75f),
                                run(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAct(17);
                                    }
                                }),fadeIn(0.75f));
                        character.addAction(actions);
                        break;
                    case 17:
                        character.setWalking(true);
                        setAct(18);
                        break;
                    case 18:
                        if(character.getX()>31){
                            character.setX(character.getX()-1);

                        }else if(character.getY()>530){
                            character.setY(character.getY()-1);
                        }else{
                            setAct(19);
                        }
                        break;
                    case 19:
                        if(character.getX()<141){
                            character.setX(character.getX()+1);
                        }else if(character.getY()<660){
                            character.setY(character.getY()+1);
                        }else{
                            setAct(20);
                        }
                        break;
                    case 20:
                        dialog.setText("Pixie: Thank you, foreign one.");
                        dialog.setVisible(true);
                        break;
                    case 21:
                        dialog.setVisible(false);
                        if(character.getY()<900){
                            character.setY(character.getY()+3);
                        }else{
                            setAct(22);
                        }
                        break;
                    case 22:
                        dialog.setText("Lidia: It's time to meet the neighbors...");
                        dialog.setVisible(true);
                        break;
                    case 23:
                        game.setScreen( new Chapter1_2( game ) );
                        break;
                }
            }
            character.act(delta);
            if(character.isVisible())
                character.draw(stage.getSpriteBatch(), 1);
        }
        stage.getSpriteBatch().end();
    }   

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if(dialog.isVisible()){
            setAct(act+1);
        }
        return super.touchUp(x, y, pointer, button);
    }
}
