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
package net.k3rnel.unsealed.battle;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class BattleHUD extends Stage {

    private float width, height;
    private TextureAtlas atlas;

    public Button leftTrigger;
    public Button rightTrigger;
    private Image dPad;
    public Button dPadDown;
    public Button dPadUp;
    public Button dPadLeft;
    public Button dPadRight;
    TextureRegion[][] spells; 
    public ImageButton aButton;
    public ImageButton bButton;
    public ImageButton xButton;
    public ImageButton yButton;
    public TextureRegion[][] blankButton;
    
    TextureRegion[][] manasphere; 
    private Image manasphere1;
    private Image manasphere2;
    private Image manasphere3;
    private Image manasphere4;
    private Image manasphere5;
    private Image manasphere6;

    public BattleHUD(float width, float height) {
        this.width = width;
        this.height = height;
        setViewport(this.width, this.height, true);
        init();
    }
    private void init() {
        //TODO: Non-hardcoded field sizes
        atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
       
        AtlasRegion atlasRegion = atlas.findRegion("battle/ui/blue_facebutton1");
        TextureRegion[][] textures = atlasRegion.split(83,92);

        aButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        aButton.setSize(83,92);
        aButton.setX( this.getWidth() - (aButton.getWidth()*aButton.getScaleX()) );
        aButton.setY( this.getHeight() -(aButton.getHeight()*aButton.getScaleY()+ 100) ); 
        this.addActor(aButton);
        //
        textures = atlasRegion.split(83,92);
        blankButton = textures;
        xButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        xButton.setSize(83,92);
        xButton.setX( this.getWidth() - (xButton.getWidth()*xButton.getScaleX()) -160 );
        xButton.setY( this.getHeight() -(xButton.getHeight()*xButton.getScaleY()+ 100) );
        this.addActor(xButton);
        //
        textures = atlasRegion.split(83,92);
        bButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        bButton.setX( this.getWidth() - (bButton.getWidth()*bButton.getScaleX()) -80 );
        bButton.setY( this.getHeight() -(bButton.getHeight()*bButton.getScaleY()+ 160) ); 
        this.addActor(bButton);

        atlasRegion = atlas.findRegion("battle/ui/blue_facebutton2");
        textures = atlasRegion.split(75,74);
        yButton = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
        textures = atlas.findRegion("battle/ui/spells").split(64,64);
        Image actor = new Image(textures[2][1]);
        actor.setY(0);
        yButton.addActor(actor);
        yButton.setX( this.getWidth() - (yButton.getWidth()*yButton.getScaleX()) -84 );
        yButton.setY( this.getHeight() -(yButton.getHeight()*yButton.getScaleY()+ 60) );
        this.addActor(yButton);
        if(Gdx.app.getVersion()>1||Unsealed.DEBUG==true){
            atlasRegion = atlas.findRegion("battle/ui/lefttrigger");
            textures  = atlasRegion.split(181,57);

            leftTrigger = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
            leftTrigger.setPosition(0, this.getHeight()-leftTrigger.getHeight());

            this.addActor(leftTrigger);

            atlasRegion = atlas.findRegion("battle/ui/righttrigger");
            textures = atlasRegion.split(181,57);
            rightTrigger = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
            rightTrigger.setPosition(this.getWidth()-rightTrigger.getWidth(), this.getHeight()-rightTrigger.getHeight());

            this.addActor(rightTrigger);

            atlasRegion = atlas.findRegion("battle/ui/dpad_alt");
            dPad = new Image(new Image(atlasRegion).getDrawable());
            dPad.setX((dPad.getWidth()*dPad.getScaleX()) - 80);
            dPad.setY( this.getHeight() -(dPad.getHeight()*dPad.getScaleY()+ 100) ); 
            this.addActor(dPad);

            atlasRegion = atlas.findRegion("battle/ui/dpad_down");
            textures = atlasRegion.split(60,43);
            dPadDown = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
            dPadDown.setPosition(84,this.getHeight()-227); 

            this.addActor(dPadDown);

            atlasRegion = atlas.findRegion("battle/ui/dpad_up");
            textures = atlasRegion.split(60,43);
            dPadUp = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[1][0]).getDrawable());
            dPadUp.setPosition(84,this.getHeight()-143);

            this.addActor(dPadUp);

            atlasRegion = atlas.findRegion("battle/ui/dpad_left");
            textures = atlasRegion.split(43,60);
            dPadLeft = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[0][1]).getDrawable());
            dPadLeft.setPosition(50,this.getHeight()-194);  

            this.addActor(dPadLeft);

            atlasRegion = atlas.findRegion("battle/ui/dpad_right");
            textures = atlasRegion.split(43,60);
            dPadRight = new ImageButton(new Image(textures[0][0]).getDrawable(),new Image(textures[0][1]).getDrawable());
            dPadRight.setPosition(138,this.getHeight()-194);

            this.addActor(dPadRight);
        }
        atlasRegion = atlas.findRegion("battle/ui/mana_sphere");
        manasphere = atlasRegion.split(32,43);
        manasphere1 = new Image(manasphere[0][0]);
        manasphere1.setX( this.getWidth()/2 - manasphere1.getWidth()/2 -125 );
        manasphere1.setY(this.getHeight()-manasphere1.getHeight()-10);
        this.addActor(manasphere1);

        manasphere2 = new Image(manasphere[0][0]);
        manasphere2.setX( this.getWidth()/2 - manasphere2.getWidth()/2 -75 );
        manasphere2.setY(this.getHeight()-manasphere2.getHeight()-10);
        this.addActor(manasphere2);

        manasphere3 = new Image(manasphere[0][0]);
        manasphere3.setX( this.getWidth()/2 - manasphere3.getWidth()/2 - 25 );
        manasphere3.setY(this.getHeight()-manasphere3.getHeight()-10);
        this.addActor(manasphere3);

        manasphere4 = new Image(manasphere[0][0]);
        manasphere4.setX( this.getWidth()/2 - manasphere4.getWidth()/2 + 25 );
        manasphere4.setY(this.getHeight()-manasphere4.getHeight()-10);
        this.addActor(manasphere4);

        manasphere5 = new Image(manasphere[0][0]);
        manasphere5.setX( this.getWidth()/2 - manasphere5.getWidth()/2 + 75 );
        manasphere5.setY(this.getHeight()-manasphere5.getHeight()-10);
        this.addActor(manasphere5);

        manasphere6 = new Image(manasphere[0][0]);
        manasphere6.setX( this.getWidth()/2 - manasphere6.getWidth()/2 + 125 );
        manasphere6.setY(this.getHeight()-manasphere6.getHeight()-10);
        this.addActor(manasphere6);
    }

    public void fillMana(BattleHero hero){
        int fillSize = hero.getMana()%5;
        int manaBars = hero.getMana()/5;
        switch(manaBars){
            case 0:
                manasphere1.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 1:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 2:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 3:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][0]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 4:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][0]).getDrawable());
                break;
            case 5:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][fillSize]).getDrawable());
                break;
            case 6:
                manasphere1.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere2.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere3.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere4.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere5.setDrawable(new Image(manasphere[0][5]).getDrawable());
                manasphere6.setDrawable(new Image(manasphere[0][5]).getDrawable());
                break;
        }
    }
}
