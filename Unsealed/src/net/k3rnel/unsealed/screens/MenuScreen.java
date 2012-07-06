package net.k3rnel.unsealed.screens;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.TableLayout;
import com.badlogic.gdx.utils.Scaling;

public class MenuScreen extends AbstractScreen {
    
    private Table table;

    public MenuScreen( Unsealed game ) {
        super( game );
    }

    @Override
    public void show() {
        super.show();

        // retrieve the custom skin for our 2D widgets
        Skin skin = super.getSkin();

        // create the table actor and add it to the stage
        table = new Table( skin );
        table.width = stage.width();
        table.height = stage.height();
        stage.addActor( table );

        // retrieve the table's layout
        TableLayout layout = table.getTableLayout();
        AtlasRegion splashRegion = getAtlas().findRegion( "menulogo" );
        Image logo = new Image(splashRegion, Scaling.stretch);
        layout.register("unsealed",logo);
        // register the button "continue game"
        TextButton continueButton = new TextButton( "Continue", skin );
        continueButton.visible=false;
        continueButton.setClickListener( new ClickListener() {
            @Override
            public void click( Actor actor, float x, float y ) {
//                game.setScreen( new StartGameScreen( game ) );
            }
        } );
        layout.register( "continueButton", continueButton );
        
        // register the button "new game"
        TextButton newGameButton = new TextButton( "New game", skin );
        newGameButton.setClickListener( new ClickListener() {
            @Override
            public void click( Actor actor, float x, float y ) {
//                game.setScreen( new StartGameScreen( game ) );
            }
        } );
        layout.register( "newGameButton", newGameButton );

        // register the button "options"
        TextButton optionsButton = new TextButton( "Options", skin );
        optionsButton.setClickListener( new ClickListener() {
            @Override
            public void click( Actor actor, float x, float y ) {
                game.setScreen( new OptionsScreen( game ) );
            }
        } );
        layout.register( "optionsButton", optionsButton );

        // finally, parse the layout descriptor
        layout.parse( Gdx.files.internal( "layout-descriptors/menu-screen.txt" ).readString() );
    }
}
