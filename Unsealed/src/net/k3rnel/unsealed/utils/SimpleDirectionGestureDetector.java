package net.k3rnel.unsealed.utils;

import com.badlogic.gdx.input.GestureDetector;
/**  
 * @author lycying@gmail.com
 */
public class SimpleDirectionGestureDetector extends GestureDetector{
    public interface UnsealedDirectionListener{
        void onLeftRight();
        void onRightRight();
        void onUpRight();
        void onDownRight();
        void onTapRight();
        
        void onLeftLeft();
        void onRightLeft();
        void onUpLeft();
        void onDownLeft();
        void onTapLeft();
    }

    public SimpleDirectionGestureDetector(UnsealedDirectionListener unsealedDirectionListener) {
        super(new DirectionGestureListener(unsealedDirectionListener));
    }
    private static class  DirectionGestureListener extends GestureAdapter {
        UnsealedDirectionListener directionListener;
        boolean right;
        public DirectionGestureListener(UnsealedDirectionListener directionListener){
            this.directionListener = directionListener;
        }
        
        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            if(x>400){
                right = true;
            }else{
               right = false;
            }
            return super.touchDown(x, y, pointer, button);
        }
        @Override
        public boolean tap(float x, float y, int count, int button) {
            if(right){
                directionListener.onTapRight();
            }else{
                directionListener.onTapLeft();
            }
            return super.tap(x, y, count, button);
        }
        @Override
        public boolean longPress(float x, float y) {
            if(!right)
                directionListener.onTapLeft();
            return super.longPress(x, y);
        }
        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                    if(right)
                        directionListener.onRightRight();
                    else
                        directionListener.onRightLeft();
                }else{
                    if(right)
                        directionListener.onLeftRight();
                    else
                        directionListener.onLeftLeft();
                }
            }else{
                if(velocityY>0){
                    if(right)
                        directionListener.onDownRight();
                    else
                        directionListener.onDownLeft();
                }else{             
                    if(right)
                        directionListener.onUpRight();
                    else
                        directionListener.onUpLeft();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }
    }
}
