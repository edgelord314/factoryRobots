package inf112.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inf112.app.cards.CardSlot;

import java.util.ArrayList;

public class TiledMapStage extends Stage {
    private TiledMap tiledMap;
    private TiledMapTileLayer cardLayer;
    private CardUI cardUI;
    private TiledMapTileLayer buttonLayer;
    private float cardHeight = 1.5f;
    private float cardWidth = 1f;

    private float heightRatio;
    private float widthRatio;

    private ArrayList<IActor> actors;

    public TiledMapStage(){
        cardUI = CardUI.getInstance();
        tiledMap = cardUI.getTiles();

        cardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Cards");
        buttonLayer = (TiledMapTileLayer) cardUI.getTiles().getLayers().get("Buttons");

        createActor(cardLayer);
        instantiateButtons(buttonLayer);

        heightRatio = 1.5f/Gdx.graphics.getHeight();
        widthRatio = 1f/Gdx.graphics.getWidth();

        actors = new ArrayList<>();
    }
    // # TODO: Refresh rather than create new actors
    private void createActor(TiledMapTileLayer layer){
        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                CardSlot slot = cardUI.getSlotFromCoordinates(x,y);
                CardSlotActor actor = new CardSlotActor(cell, slot);
                actor.setBounds(x*(cardWidth), y*(cardHeight), cardWidth, cardHeight);  //height 1.5 since that is the cards ratio (400x600)
                addActor(actor);                                            //*1.5f to compensate the stretch downward
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }

    private void instantiateButtons(TiledMapTileLayer layer){
        boolean done = false;
        int x = layer.getWidth() - 1;
        String type = "powerdown";
        while(!done){
            done = x == layer.getWidth() - 2;
            TiledMapTileLayer.Cell cell = layer.getCell(x, 0);
            GameButtonActor actor = new GameButtonActor(cell,layer,type,x,0);
            actor.setBounds(x*cardWidth,0,cardWidth,cardHeight);
            addActor(actor);
            EventListener eventListener = new TiledMapClickListener(actor);
            actor.addListener(eventListener);
            x--;
            type = "lockIn";
        }

    }

    @Override
    public void act() {
        //Reset list of actors
        getActors().clear();
        //Refreshed list based on tiledmap
        createActor(cardLayer);
        instantiateButtons(buttonLayer);
        super.act();
    }

    public void resize(int width, int height){
        cardWidth = width*widthRatio;
        cardHeight = height*heightRatio;
    }

}
