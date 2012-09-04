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
import net.k3rnel.unsealed.screens.ChapterSelectScreen;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.AbstractChapter;
import net.k3rnel.unsealed.story.MapCharacter;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Penguin;

public class Chapter2_10 extends AbstractChapter {

    final int[] overLayers = {6, 12 };

    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_10(Unsealed game) {
        super(game);
        mapname="Asia Town";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.DOJO );

        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1100,1400);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Penguin(getAtlas());
        tmpChar.updateAnimation();
        tmpChar.setPosition(1100,1600);
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
                        dialog.setText("Lidia: Is anyone there?");
                        dialog.setVisible(true);
                        break;
                    case 2:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        act = 3;
                        break;
                    case 3:
                        if(character.getY()<1690){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            act = 4;
                        }
                        break;
                    case 4:
                        character.setWalking(false);
                        dialog.setText("Lidia: Another Guardian! ");
                        dialog.setVisible(true);
                        break;
                    case 5:
                        dialog.setText("Lidia: Oh Guardian of Ryuken. Awaken from your slumber, and protect these lands!\n" +
                                "I, Lidia Terius, UNSEAL YOU!");

                        break;
                    case 6:
                        dialog.setVisible(false);
                        act = 7;
                        break;
                }
            }
            if(character instanceof Penguin){
                character.setPosition(1120,1760);
                switch(act){
                    case 7:
                        actions = sequence(fadeOut(0.75f),
                                run(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAct(8);
                                    }
                                }),fadeIn(0.75f));
                        character.addAction(actions);
                        break;
                    case 8:
                        character.setWalking(true);
                        setAct(9);
                        break;

                    case 9:
                        dialog.setText("Penguin: *Squawk*!\n" +
                                "Thank you, Traveler! I am Sho, Guardian of Ryuken!\n");
                        dialog.setVisible(true);
                        break;
                    case 10:
                        dialog.setText("Sho: With my release I should be able to exorcise the restless spirits here.\n");
                        break;
                    case 11:
                        dialog.setText("Lidia: I am glad to hear that. Now that the area is safe, everyone can return to Ryuken.");
                        break;
                    case 12:
                        dialog.setText("Sho: As a token of appreciation, let me teach you our ancient technique:\n" +
                                "The Rising Dragon Punch");
                        break;
                    case 13:
                        dialog.setText("Lidia learned the Rising Dragon Punch!");
                        break;
                    case 14:
                        dialog.setText("Lidia: Thank you master Sho. I'll go unseal the final guardian.");
                        break;
                    case 15:
                        dialog.setText("Sho: That means you are heading towards Malus Grove.\n" +
                        		"You will have to pass through the Terrex Mountains to reach the final guardian.");
                        break;
                    case 16:
                        dialog.setText("Sho: Xios will be waiting, so be careful child. Wind and fire be with you!");
                        break;
                    case 17:
                        dialog.setVisible(false);
                        act = 20;
                    case 20:
                        game.setScreen(new ChapterSelectScreen(game));
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
