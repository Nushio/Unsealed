package net.k3rnel.unsealed.objects;

import java.util.List;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BattleHUD extends Stage {

    private float width, height;

    private SpriteBatch batch;

    private TextureAtlas atlas;

    private Image lifebar;
    TextureRegion[][] lifebarTextures;
    
    private Image leftTrigger;
    private Image rightTrigger;
    private Image dPad;
    private Image aButton;
    private Image bButton;
    private Image xButton;
    private Image yButton;

    TextureRegion[][] manasphere; 
    private Image manasphere1;
    private Image manasphere2;
    private Image manasphere3;
    private Image manasphere4;
    private Image manasphere5;
    private Image manasphere6;
    
    private Image background;
    private Image battleoverlay;

    private List<Object>[][] grid;
    
    
    public BattleHUD(float width, float height) {
        this.width = width;
        this.height = height;

        init();
    }

    private void init() {
        //TODO: Non-hardcoded field sizes
        atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
        AtlasRegion atlasRegion = atlas.findRegion( "battle/battlemap1" );
        background = new Image(atlasRegion);

        this.addActor(background);

        atlasRegion = atlas.findRegion( "battle/field-3x3" );
        battleoverlay = new Image(atlasRegion);
        battleoverlay.setX( this.width/2 - (battleoverlay.getWidth()*battleoverlay.getScaleX())/2 );
        battleoverlay.setY( this.height/2 -(battleoverlay.getHeight()*battleoverlay.getScaleY())/2 - 75); 

        this.addActor(battleoverlay);

        atlasRegion = atlas.findRegion("battle/lifebar");
        lifebarTextures = atlasRegion.split(363,23);
        lifebar = new Image(lifebarTextures[0][0]);
        lifebar.setX( this.width/2 - lifebar.getWidth()/2 );
        lifebar.setY(this.height-lifebar.getHeight());
        this.addActor(lifebar);

        atlasRegion = atlas.findRegion("battle/lefttrigger");
        leftTrigger = new Image(atlasRegion);
        leftTrigger.setPosition(0, this.height-leftTrigger.getHeight());
        this.addActor(leftTrigger);

        atlasRegion = atlas.findRegion("battle/righttrigger");
        rightTrigger = new Image(atlasRegion);
        rightTrigger.setPosition(this.width-rightTrigger.getWidth(), this.height-rightTrigger.getHeight());
        this.addActor(rightTrigger);

        atlasRegion = atlas.findRegion("battle/blue_facebutton1");
        aButton = new Image(atlasRegion);
        aButton.setX( this.width - (aButton.getWidth()*aButton.getScaleX()) );
        aButton.setY( this.height -(aButton.getHeight()*aButton.getScaleY()+ 100) ); 
        this.addActor(aButton);

        xButton = new Image(atlasRegion);
        xButton.setX( this.width - (xButton.getWidth()*xButton.getScaleX()) -160 );
        xButton.setY( this.height -(xButton.getHeight()*xButton.getScaleY()+ 100) );
        this.addActor(xButton);

        bButton = new Image(atlasRegion);
        bButton.setX( this.width - (bButton.getWidth()*bButton.getScaleX()) -80 );
        bButton.setY( this.height -(bButton.getHeight()*bButton.getScaleY()+ 160) ); 
        this.addActor(bButton);

        atlasRegion = atlas.findRegion("battle/blue_facebutton2");
        yButton = new Image(atlasRegion);
        yButton.setX( this.width - (yButton.getWidth()*yButton.getScaleX()) -84 );
        yButton.setY( this.height -(yButton.getHeight()*yButton.getScaleY()+ 60) ); 
        this.addActor(yButton);
        
        atlasRegion = atlas.findRegion("battle/dpad_alt");
        dPad = new Image(atlasRegion);
        dPad.setX((dPad.getWidth()*dPad.getScaleX()) - 80);
        dPad.setY( this.height -(dPad.getHeight()*dPad.getScaleY()+ 100) ); 
        
        this.addActor(dPad);
        
        atlasRegion = atlas.findRegion("battle/mana_sphere");
        manasphere = atlasRegion.split(32,43);
        manasphere1 = new Image(manasphere[0][0]);
        manasphere1.setX( this.width/2 - manasphere1.getWidth()/2 -125 );
        manasphere1.setY(this.height-manasphere1.getHeight()-26);
        this.addActor(manasphere1);
        
        manasphere2 = new Image(manasphere[0][0]);
        manasphere2.setX( this.width/2 - manasphere2.getWidth()/2 -75 );
        manasphere2.setY(this.height-manasphere2.getHeight()-26);
        this.addActor(manasphere2);
        
        manasphere3 = new Image(manasphere[0][0]);
        manasphere3.setX( this.width/2 - manasphere3.getWidth()/2 - 25 );
        manasphere3.setY(this.height-manasphere3.getHeight()-26);
        this.addActor(manasphere3);
        
        manasphere4 = new Image(manasphere[0][0]);
        manasphere4.setX( this.width/2 - manasphere4.getWidth()/2 + 25 );
        manasphere4.setY(this.height-manasphere4.getHeight()-26);
        this.addActor(manasphere4);
        
        manasphere5 = new Image(manasphere[0][0]);
        manasphere5.setX( this.width/2 - manasphere5.getWidth()/2 + 75 );
        manasphere5.setY(this.height-manasphere5.getHeight()-26);
        this.addActor(manasphere5);
        
        manasphere6 = new Image(manasphere[0][0]);
        manasphere6.setX( this.width/2 - manasphere6.getWidth()/2 + 125 );
        manasphere6.setY(this.height-manasphere6.getHeight()-26);
        this.addActor(manasphere6);
    }

    @Override
    public void draw() {
        super.draw();
         
        
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log(Unsealed.LOG, "TOUCHDOWN!: "+screenX+"/"+screenY+"/"+pointer+"/"+button);
      
        return super.touchDown(screenX, screenY, pointer, button);
    }
    public void dispose () {
        if(atlas!=null) atlas.dispose();
        if(batch!=null) batch.dispose();
    }
}
