package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.OneRowTableBuilder;
import com.kotcrab.vis.ui.building.utilities.Alignment;
import com.kotcrab.vis.ui.building.utilities.CellWidget;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import inf112.app.game.RoboRally;

public class CourseSelector implements Screen {
    RoboRally game;
    protected Stage stage;
    Skin skin;

    OrthographicCamera menuCamera;
    OrthographicCamera mapCamera;
    OrthogonalTiledMapRenderer mapRenderer;

    Viewport menuViewport;
    Viewport mapViewport;

    VisWindow window;

    public CourseSelector(final RoboRally game) {
        this.game = game;
        this.skin = game.skin;

        menuCamera = new OrthographicCamera();
        menuViewport = new ScreenViewport(menuCamera);
        menuViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        menuViewport.apply();

        mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, Gdx.graphics.getWidth()/2f
                ,Gdx.graphics.getHeight()/1.6f);


        mapViewport = new ScreenViewport(mapCamera);
        mapViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mapViewport.apply();

        this.stage = new Stage(menuViewport);
        Gdx.input.setInputProcessor(this.stage);

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load("assets/testMap.tmx");

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/9f);
        mapRenderer.setView(mapCamera);

    }

    @Override
    public void show() {
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        window = new VisWindow("Select Course");
        window.setMovable(false);
        window.setModal(true);
        centerWindowTable();

        OneRowTableBuilder builder = new OneRowTableBuilder();
        VisImageButton leftArrow = new VisImageButton("arrow-left");
        leftArrow.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return false;
            }
        });
        VisImageButton rightArrow = new VisImageButton("arrow-right");
        rightArrow.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                return false;
            }
        });
        VisImageTextButton selectButton = new VisImageTextButton("Select","default");
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
        });
        VisImageTextButton returnButton = new VisImageTextButton("Return","default");
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        builder.append(CellWidget.of(leftArrow).align(Alignment.LEFT).expandX().wrap());
        builder.append(CellWidget.of(selectButton).align(Alignment.BOTTOM).expandY().wrap());
        builder.append(CellWidget.of(returnButton).align(Alignment.BOTTOM).expandY().wrap());
        builder.append(CellWidget.of(rightArrow).align(Alignment.RIGHT).expandX().wrap());

        Table table = builder.build();
        window.add(table).expand().fill();
        stage.addActor(window);

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuCamera.update();
        mapCamera.update();
        game.batch.setProjectionMatrix(menuCamera.combined);

        game.batch.begin();
        game.batch.draw(game.backgroundImg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();

        stage.act();
        stage.draw();
        mapRenderer.render();
    }

    @Override
    public void resize(int x, int y) {
        menuCamera.position.set((float) x / 2, y / 2.0f, 0.0f);
        menuViewport.update(x, y, true);
        centerWindowTable();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();

        VisUI.dispose();
        game.backgroundImg.dispose();
        game.atlas.dispose();
    }

    private void centerWindowTable(){
        window.setHeight(getWindowHeight());
        window.setWidth(getWindowWidth());
        window.setPosition((stage.getWidth() - window.getWidth()) / 2F
               , (stage.getHeight() - window.getHeight()) / 3.2F);
    }

    private float getWindowHeight(){
        return Gdx.graphics.getHeight()*(2.1f/3f);
    }
    private float getWindowWidth(){
        return Gdx.graphics.getWidth()*(3/4f);

    }
}
