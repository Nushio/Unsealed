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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Shura;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter2_3 extends AbstractChapter {

  
    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_3(Unsealed game) {
        super(game);
        mapname="RouteTwo";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.DESERT );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(800,1600);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirRight);
        characters.add(tmpChar);
        
        tmpChar = new Shura(getAtlas());
        tmpChar.setPosition(1860,1960);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirDown);
        tmpChar.setVisible(false);
        characters.add(tmpChar);
        camera.zoom = 2f;
        camera.update();
       
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
                        character.setWalking(true);
                        break;
                    case 1:
                        if(character.getX()<801){
                            character.setX(character.getX()+2);
                            centerCamera(character);
                        }else{
                            //TODO Move till X = 800
//                            camera.zoom = 2.0f;
//                            camera.update();
                            act = 2;
//                        }
                        }
                       break;
                   
                    case 2:
                        if(character.getX()<1201){
                            character.setX(character.getX()+2);
                            centerCamera(character);
                        }else{
                            camera.zoom = 1.8f;
                            camera.update();
                            act = 3;
//                        }
                        }
                        break;
                    case 3:
                        if(character.getX()<1401){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            act = 4;
//                        }
                        }
                        break;
                    case 18:
                        if(character.getX()<2201){
                            character.setWalking(true);
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            act = 19;
//                        }
                        }
                        break;
                    case 19:
                        game.setScreen(new Chapter2_4(game));
                        break;
                   
                }
            }
            if(character instanceof Shura){
                switch(act){
                    case 4:
                        character.setVisible(true);
                        if(character.getY()>1800){
                            character.setWalking(true);
                            character.setY(character.getY()-1);
                        }else{
                            camera.zoom = 1.6f;
                            camera.update();
                            act = 5;
                        }
                        break;
                    case 5:
                        if(character.getY()>1600){
                            character.setY(character.getY()-1);
                        }else{
                            camera.zoom = 1.3f;
                            camera.update();
                            act = 6;
                            character.setDirection(MapCharacter.dirLeft);
                        }
                        break;
                    case 6:
                        if(character.getX()>1600){
                            character.setX(character.getX()-1);
                        }else{
                            camera.zoom = 1.0f;
                            camera.update();
                            act = 7;
                            character.setWalking(false);
                        }
                        break;
                    case 7:
                        dialog.setText("Lidia: Shura! I'm glad to see you! \n" +
                                "After Marblehead... We didn't part in the best of terms... ");
                        dialog.setVisible(true);
                        break;
                    case 8:
                        dialog.setText("Shura: The best of terms? You nearly got us killed over a boy!\n" +
                        		"You just had to tell him we're Spellweavers, didn't you? ");
                        break;
                    case 9:
                        dialog.setText("Lidia: That was what we set out to do, remember?\n" +
                        		"Unseal the Guardians? Teach them about Spellweaving?");
                        break;
                    case 10:
                        dialog.setText("Shura: Falling in love was also part of the mission?");
                        break;
                    case 11:
                        dialog.setText("Lidia: I... You.. You're right. \n" +
                        		"I'm sorry... I should've known better...");
                        break;
                    case 12:
                        dialog.setText("Lidia: Will you forgive me? \n" +
                        		"We can't undo the past, but we can move on and learn from our mistakes.");
                        break;
                    case 13:
                        dialog.setText("Shura: I can forgive you, but I won't join you. \n" +
                        		"You need to learn take care of yourself and cool down sometimes.\n" +
                        		"Let me share this new Spell I weaved...");
                        break;
                    case 14:
                        dialog.setText("Lidia learned Ice Prison!");
                        break;
                    case 15:
                        dialog.setText("Shura: Alright, put it to good use. I'll see you later!");
                        break;
                    case 16:
                        dialog.setVisible(false);
                        character.setDirection(MapCharacter.dirRight);
                        character.setWalking(true);
                        act = 17;
                        break;
                    case 17:
                        if(character.getX()<1800){
                            character.setX(character.getX()+2.5f);
                        }else{
                          act = 18;
                          character.setVisible(false);
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
