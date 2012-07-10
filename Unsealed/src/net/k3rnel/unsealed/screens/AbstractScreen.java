package net.k3rnel.unsealed.screens;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * The base class for all game screens.
 */
public abstract class AbstractScreen extends InputAdapter implements Screen {
    
    // the fixed viewport dimensions (ratio: 1.6)
    public static final int GAME_VIEWPORT_WIDTH = 400, GAME_VIEWPORT_HEIGHT = 240;
    public static final int MENU_VIEWPORT_WIDTH = 800, MENU_VIEWPORT_HEIGHT = 480;

    protected final Unsealed game;
    protected final Stage stage;

    private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;

    public AbstractScreen( Unsealed game ) {
        this.game = game;
        int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
        int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
        this.stage = new Stage( width, height, true );
    }

    protected String getName() {
        return getClass().getSimpleName();
    }

    protected boolean isGameScreen() {
        return false;
    }

    // Lazily loaded collaborators
    public BitmapFont getFont() {
        if( font == null ) {
            font = new BitmapFont();
        }
        return font;
    }

    public SpriteBatch getBatch() {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
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
//            FileHandle textureFile = Gdx.files.internal( "skin/uiskin.png" );
            skin = new Skin( skinFile );
//            skin.load(skinFile);
        }
        return skin;
    }

    protected Table getTable() {
        if( table == null ) {
            table = new Table( getSkin() );
            table.setFillParent( true );
            if( Unsealed.DEBUG ) {
                table.debug();
            }
            stage.addActor( table );
        }
        return table;
    }

    // Screen implementation

    @Override
    public void show() {
        Gdx.app.log( Unsealed.LOG, "Showing screen: " + getName() );

        // set the stage as the input processor
        Gdx.input.setInputProcessor( stage );
    }

    @Override
    public void resize( int width, int height ) {
        Gdx.app.log( Unsealed.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );
    }

    @Override
    public void render( float delta ) {
        // (1) process the game logic

        // update the actors
        stage.act( delta );

        // (2) draw the result

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();
        
        // draw the table debug lines
        Table.drawDebug( stage );

    }

    @Override
    public void hide() {
        Gdx.app.log( Unsealed.LOG, "Hiding screen: " + getName() );

        // dispose the screen when leaving the screen;
        // note that the dipose() method is not called automatically by the
        // framework, so we must figure out when it's appropriate to call it
        dispose();
    }

    @Override
    public void pause() {
        Gdx.app.log( Unsealed.LOG, "Pausing screen: " + getName() );
    }

    @Override
    public void resume() {
        Gdx.app.log( Unsealed.LOG, "Resuming screen: " + getName() );
    }

    @Override
    public void dispose() {
        Gdx.app.log( Unsealed.LOG, "Disposing screen: " + getName() );

        // as the collaborators are lazily loaded, they may be null
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
        if( atlas != null ) atlas.dispose();
//        if( stage != null ) stage.dispose();
    }

    public boolean keyUp(int keycode) {
        return false;
    }
}
