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
package net.k3rnel.unsealed.story.chapter2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.AbstractChapter;
import net.k3rnel.unsealed.story.MapCharacter;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Whisperer;

public class Chapter2_4 extends AbstractChapter {


    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_4(Unsealed game) {
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
        
        tmpChar = new Whisperer(getAtlas());
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.setPosition(1350,1090);
        tmpChar.setVisible(false);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
       
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);

        game.getMusicManager().play( UnsealedMusic.DESERT );
        
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
                        dialog.setText("Lidia: Altera is a very big place after all.");
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
                        dialog.setText("Whisperer: Ryuken is up ahead. It looks deserted.");
                        dialog.setVisible(true);
                        break;
                    case 13:
                        dialog.setText("Lidia: Strange, I guess the rumours Mimi heard were correct.");
                        break;
                    case 14:
                        dialog.setText("Lidia: The Guardian is there though. I can sense it.");
                        break;
                    case 15:
                        dialog.setText("Whisperer: Be careful. The creatures have begun to behave more violent.\n" +
                        		"It looks like they're scared of something. ");
                        break;
                    case 16:
                        dialog.setVisible(false);
                        act = 17;
                        character.setWalking(true);
                        break;
                    case 18:
                        character.setWalking(true);
                        if(character.getX()>1300){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            act = 19;
                        }
                        break;
                    case 19:
                        if(character.getY()<1200){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            act = 20;
                        }
                        break;
                    case 20:
                        game.setScreen(new Chapter2_5(game));
                        break;
                }
            }
            if(character instanceof Whisperer){
                switch(act){
                    case 12:
                        character.getColor().a = 0;
                        character.setWalking(false);
                        actions = sequence(fadeIn(0.95f),delay(0.75f),run(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }));
                        character.addAction(actions);
                        character.setWalking(false);
                        character.setVisible(true);
                        break;
                case 15:
                    dialog.setVisible(false);
                    actions = sequence(fadeOut(0.95f),delay(0.75f),run(new Runnable() {
                        @Override
                        public void run() {
                            setAct(18);
                        }
                    }));
                    character.addAction(actions);
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
