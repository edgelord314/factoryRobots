package inf112.app.game;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import inf112.app.map.Map;

public class GameButtonActor extends Actor implements IActor {

    private TiledMapTileLayer.Cell buttonUp;
    private TiledMapTileLayer.Cell buttonDown;
    private TiledMapTileLayer.Cell active;
    private TiledMapTileLayer layer;
    private int x,y;


    public GameButtonActor(TiledMapTileLayer.Cell cell, TiledMapTileLayer layer, String type, int x, int y){
        buttonUp = cell;
        active = cell;
        this.layer = layer;
        int index = "powerdown".equals(type) ? 1 : 3;
        TiledMapTileLayer buttons = (TiledMapTileLayer) Map.getInstance().getGameButtons().getLayers().get(0);
        buttonDown = buttons.getCell(index,0);
        this.x = x;
        this.y = y;
    }

    @Override
    public TiledMapTileLayer.Cell getCell() {
        return active;
    }

    @Override
    public void clickAction() {
        layer.setCell(x,y,buttonDown);
    }


}
