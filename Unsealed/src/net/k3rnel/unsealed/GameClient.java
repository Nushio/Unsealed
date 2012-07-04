package net.k3rnel.unsealed;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class GameClient implements ApplicationListener {
	
    //Constants!
    public static boolean touchControl;
    public static boolean keyBoardControl;
    private static final int MAINMENU = 0;

    
	@Override
	public void create() {	
	    //If it's Android, it returns > 0
	    if (Gdx.app.getVersion()>0) {
            keyBoardControl = false;
            touchControl = false;
	    } else {
            keyBoardControl = true;
            touchControl = true;                    
	    }

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
