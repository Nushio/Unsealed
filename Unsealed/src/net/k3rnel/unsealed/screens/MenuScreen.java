package net.k3rnel.unsealed.screens;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
        table = super.getTable();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.pad(10).defaults().spaceBottom(10).space(5);
        table.row().fill().expandX();
        AtlasRegion splashRegion = getAtlas().findRegion( "splash-screen/menulogo" );
        Image logo = new Image(splashRegion);
        table.add(logo).fill(false);
        table.row();
        table.pad(10).defaults().spaceBottom(10);
        TextButton continueButton = new TextButton( "Continue", skin );
        continueButton.setVisible(false);
        continueButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y ) {
//                game.setScreen( new StartGameScreen( game ) );
            }
        } );
        table.add(continueButton).size( 300, 60 ).uniform().spaceBottom(10);
        table.row();
        table.pad(10).defaults().spaceBottom(10);
        TextButton newGameButton = new TextButton( "New game", skin );
        newGameButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y ) {
                //TODO: Move it back to char select screen
//                game.setScreen( new CharSelectScreen( game ) );
                if(Unsealed.DEBUG)
                    game.setScreen( new BattleScreen( game ) );
                else
                    game.setScreen( new SplashScreen( game ) );
            }
        } );
        table.add(newGameButton).size( 300, 60 ).uniform().spaceBottom(10);
        table.row();
        table.pad(10).defaults().spaceBottom(10);
//        TextButton optionsButton = new TextButton( "Options", skin );
//        optionsButton.setDisabled(true);
//        optionsButton.addListener( new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y ) {
//                game.setScreen( new OptionsScreen( game ) );
//            }
//        } );
//        table.add(optionsButton).size( 300, 60 ).uniform().spaceBottom(10);
    }
}
