package net.k3rnel.unsealed.screens.battle;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BattleEntity extends Actor {
    
    private int hp;
    public Label hpLabel;
    public Image hpBar;
    public TextureRegion[][] hpBarTextures;
    
    private int gridX;
    private int gridY;
    private Skin skin;
    protected TextureAtlas atlas;
    
    public static final int stateIdle = 0;
    public static final int stateAttacking = 1;
    public static final int stateAltAttacking = 2;
    public static final int stateCharging = 3;
    public static final int stateBlocking = 4;
    public static final int statePassThrough = 5;
    public static final int stateHit = 6;
    
    

    public final HashMap<String, Animation> animations;
    public Animation currentAnimation;
    public float stateTime;
    private int state;
    
    public BattleEntity() {
        this.animations = new HashMap<String, Animation>();
        this.currentAnimation = null;

        hpLabel = new Label("",getSkin());
        hpLabel.setColor(Color.WHITE);
        AtlasRegion region = getAtlas().findRegion("battle/enemy-lifebar");
        hpBarTextures = region.split(106,19);
        
    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        hpBar.draw(batch,1);
        hpLabel.draw(batch,1);
    }
    
    public TextureAtlas getAtlas() {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
        }
        return atlas;
    }

    protected Skin getSkin() {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }
    
    /**
     * @return the gridX
     */
    public int getGridX() {
        return gridX;
    }
    /**
     * @param gridX the gridX to set
     */
    public void setGridX(int gridX) {
        this.gridX = gridX;
    }
    /**
     * @return the gridY
     */
    public int getGridY() {
        return gridY;
    }
    /**
     * @param gridY the gridY to set
     */
    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public boolean setHp(int hp) {
        if(hp>0){
            this.hp = hp;
            this.hpLabel.setText(hp+"");
            this.hpBar = new Image(hpBarTextures[0][0]);
            return false;
        }else{
            hp = 0;
            SequenceAction actions = sequence(fadeOut(0.75f));
            this.addAction(actions);
            return true;
        }
    }

    /**
     * @return the hpLabel
     */
    public Label getHpLabel() {
        return hpLabel;
    }

    /**
     * @param hpLabel the hpLabel to set
     */
    public void setHpLabel(Label hpLabel) {
        this.hpLabel = hpLabel;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        
        updateAnimations();
    }
    private void updateAnimations() {
        stateTime = 0;

        switch(state) {
            case stateIdle:
                currentAnimation = animations.get("idle");
                break;
            case stateAttacking:
                currentAnimation = animations.get("attacking");
                break;
            case stateAltAttacking:
                currentAnimation = animations.get("altattacking");
                break;
            case stateCharging:
                currentAnimation = animations.get("charging");
                break;
            case stateHit:
                currentAnimation = animations.get("hit");
                break;
            case stateBlocking:
                currentAnimation = animations.get("blocking");
                break;
            case statePassThrough:
                currentAnimation = animations.get("passthrough");
                break;
        }
    }
    

    /**
     * @return the animations
     */
    public HashMap<String, Animation> getAnimations() {
        return animations;
    }
}
