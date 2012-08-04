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


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

import java.util.HashMap;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer.Task;

public class BattleEntity extends Image {

    private int hp;
    public Label hpLabel;
  
    public float gridX;
    private float gridY;
    private Skin skin;
  
    public static final int stateIdle = 0;
    public static final int stateAttacking = 1;
    public static final int stateAltAttacking = 2;
    public static final int stateCharging = 3;
    public static final int stateBlocking = 4;
    public static final int statePassThrough = 5;
    public static final int stateHit = 6;
    public static final int stateMoving = 7;
    public static final int stateInvulnerable = 8;

    
    public static final int statusNormal = 0;
    public static final int statusBurned = 1;
    public static final int statusPoisoned = 2;
    public static final int statusStunned = 3;
    public static final int statusFrozen = 4;
    
    private int status=statusNormal;

    public final HashMap<String, Animation> animations;
    public Animation currentAnimation;
    public float stateTime;
    private int state;
    protected Task currentTask;

    public int offsetX = 0, offsetY = 0;
    

    public boolean canBeMoved;
    public SequenceAction actions;

    public BattleEntity() {
        this.animations = new HashMap<String, Animation>();
        this.currentAnimation = null;
        canBeMoved=true;
        hpLabel = new Label("",getSkin());
        hpLabel.setColor(Color.WHITE);

    }
    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime+=delta;
        if(this.currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }
        if(getStatus()!=BattleEntity.statusStunned)
            this.setDrawable(new Image(this.currentAnimation.getKeyFrame(this.stateTime)).getDrawable());
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //        hpBar.draw(batch,parentAlpha);
        hpLabel.draw(batch,parentAlpha);
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
    public float getGridX() {
        return gridX;
    }
    /**
     * @return the gridX
     */
    public int getGridXInt() {
        return (int)gridX;
    }
    public void setGrid(float x, float y){
        setGridX(x,true);
        setGridY(y);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        hpLabel.setX(x+(this.hpLabel.getWidth()/2)+10+offsetX);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        hpLabel.setY(y-1);
    }
    /**
     * @param gridX the gridX to set
     */
    public void setGridX(float gridX, boolean reset) {
        
        this.gridX = gridX;
//        Gdx.app.log(Unsealed.LOG, "GridX:"+gridX);
        if(reset)
            setX((this.gridX+1)*65+150 - offsetX);
    }
    /**
     * @return the gridY
     */
    public float getGridY() {
        return gridY;
    }
    /**
     * @return the gridY
     */
    public int getGridYInt() {
        return (int)gridY;
    }
    /**
     * @param gridY the gridY to set
     */
    public void setGridY(float gridY) {
        this.gridY = gridY;
//        Gdx.app.log(Unsealed.LOG, "GridY:"+gridY);
        setY((gridY+1)*-40 + 230 - offsetY);
//        setY();
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     * returns true if dead
     */
    public boolean setHp(int hp) {
        if(hp>0){
            this.hp = hp;
            this.hpLabel.setText(hp+"");
            //            this.hpBar = new Image(hpBarTextures[0][0]);
            SequenceAction actions = sequence(fadeOut(0.05f),
                    delay(0.005f),fadeIn(1f), run(new Runnable() {
                        public void run() {
                            Gdx.app.log(Unsealed.LOG, "I'm hurt");
                        }
                    }));
            this.addAction(actions);
            return false;
        }else{
            this.hp = 0;            
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
            case stateMoving:
                currentAnimation = animations.get("moving");
                break;
        }
    }

    public Task nextTask(){
        currentTask = new Task(){

            @Override
            public void run() {
                // Do something;

            }

        };
        return currentTask;
    }
    /**
     * @return the animations
     */
    public HashMap<String, Animation> getAnimations() {
        return animations;
    }
    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
        switch(this.status){
            case BattleEntity.statusNormal:
                break;
            case BattleEntity.statusBurned:
                actions =     
                    
                sequence(color(Color.RED), delay(0.05f),
                        color(Color.ORANGE),delay(0.05f),
                        color(Color.RED),delay(0.05f),
                        color(Color.ORANGE),delay(0.05f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-10);
                            }
                        }),
                        color(Color.RED), delay(0.05f),
                        color(Color.ORANGE),delay(0.05f),
                        color(Color.RED),delay(0.05f),
                        color(Color.ORANGE),delay(0.05f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-10);
                            }
                        }),
                        color(Color.RED), delay(0.05f),
                        color(Color.ORANGE),delay(0.05f),
                        color(Color.RED),delay(0.05f),
                        color(Color.ORANGE),delay(0.05f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-10);
                            }
                        }),
                        color(Color.WHITE), run(new Runnable() {
                            
                            @Override
                            public void run() {
                               setState(BattleEntity.statusNormal);
                            }
                        }));
                this.addAction( actions ) ;
                break;
            case BattleEntity.statusStunned: 
                actions =     
                
                sequence(color(Color.YELLOW), delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.YELLOW),delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.YELLOW), delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.YELLOW),delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.WHITE),delay(0.1f),
                        run(new Runnable() {
                            
                            @Override
                            public void run() {
                               setStatus(BattleEntity.statusNormal);
                            }
                        }));
                this.addAction( actions ) ;
                break;
            case BattleEntity.statusFrozen:
                this.status = statusStunned;
                actions =     
                        
                        sequence(color(Color.BLUE), delay(0.05f),
                                color(Color.CLEAR),delay(0.05f),
                                color(Color.BLUE),delay(0.05f),
                                color(Color.CLEAR),delay(0.05f),
                                run(new Runnable() {
                                    @Override
                                    public void run() {
                                        setHp(getHp()-10);
                                    }
                                }),
                                color(Color.BLUE), delay(0.05f),
                                color(Color.CLEAR),delay(0.05f),
                                color(Color.BLUE),delay(0.05f),
                                color(Color.CLEAR),delay(0.05f),
                                run(new Runnable() {
                                    @Override
                                    public void run() {
                                        setHp(getHp()-10);
                                    }
                                }),
                                color(Color.BLUE), delay(0.05f),
                                color(Color.CLEAR),delay(0.05f),
                                color(Color.BLUE),delay(0.05f),
                                color(Color.CLEAR),delay(0.05f),
                                run(new Runnable() {
                                    @Override
                                    public void run() {
                                        setHp(getHp()-10);
                                    }
                                }),
                                color(Color.WHITE), run(new Runnable() {
                                    @Override
                                    public void run() {
                                       setState(BattleEntity.statusNormal);
                                    }
                                }));
                        this.addAction( actions ) ;
                break;
            case BattleEntity.statusPoisoned:
                actions =     
                sequence(color(Color.MAGENTA), delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.MAGENTA),delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.WHITE),
                        fadeIn(0.3f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-5);
                            }
                        }),
                        
                        color(Color.MAGENTA), delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.MAGENTA),delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.WHITE),
                        fadeIn(0.3f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-5);
                            }
                        }),
                        color(Color.MAGENTA), delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.MAGENTA),delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.WHITE),
                        fadeIn(0.3f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-5);
                            }
                        }),
                        color(Color.MAGENTA), delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.MAGENTA),delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.WHITE),
                        fadeIn(0.3f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-5);
                            }
                        }),
                        color(Color.MAGENTA), delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.MAGENTA),delay(0.2f),
                        color(Color.GREEN),delay(0.2f),
                        color(Color.WHITE),
                        fadeIn(0.3f),
                        run(new Runnable() {
                            @Override
                            public void run() {
                                setHp(getHp()-5);
                            }
                        }),
                        color(Color.WHITE), run(new Runnable() {
                            
                            @Override
                            public void run() {
                               setState(BattleEntity.statusNormal);
                            }
                        }));
                this.getActions().clear();
                this.addAction( actions ) ;
                break;
        }
    }
    
}
