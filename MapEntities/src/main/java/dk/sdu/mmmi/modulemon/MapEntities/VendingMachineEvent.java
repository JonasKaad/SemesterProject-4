package dk.sdu.mmmi.modulemon.MapEntities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.modulemon.CommonMap.Data.Entity;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMap.IMapEvent;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.data.GameKeys;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.drawing.TextUtils;

import java.util.List;
import java.util.UUID;

public class VendingMachineEvent implements IMapEvent {
    private Entity entity;
    private List<IMonster> monsters;
    private int monIndex;
    private TextUtils textUtils = TextUtils.getInstance();
    private boolean hasChosenMonster;
    private Rectangle chooseMonMenu;
    private UUID machineUuid;
    private boolean eventDone;

    public VendingMachineEvent(Entity entity, List<IMonster> monsters, UUID machineUuid){
        if(entity == null){
            throw new IllegalArgumentException("entity is null");
        }
        this.entity = entity;
        this.monsters = monsters;
        this.monIndex = 0;
        this.chooseMonMenu = new Rectangle(100, 100, 500, 250);
        this.machineUuid = machineUuid;
        eventDone = false;
    }

    @Override
    public boolean isEventDone() {
        return eventDone;
    }

    @Override
    public void start(GameData gameData) {
    }

    @Override
    public void update(GameData gameData) {
        VendingMachineVisitedPart visitedPart = this.entity.getPart(VendingMachineVisitedPart.class);
        if(visitedPart != null){
            hasChosenMonster = visitedPart.getUuids().contains(this.machineUuid);
        }
    }

    @Override
    public void draw(GameData gameData, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        int monAmount = monsters.size();
        //Drawing pause menu box
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(gameData.getCamera().combined);
        shapeRenderer.setColor(Color.WHITE);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        chooseMonMenu.setX(gameData.getCamera().position.x - 250);
        chooseMonMenu.setY(gameData.getCamera().position.y - 50);
        chooseMonMenu.draw(shapeRenderer, gameData.getDelta());
        shapeRenderer.end();

        // Drawing text
        spriteBatch.setProjectionMatrix(gameData.getCamera().combined);
        spriteBatch.begin();
        if(hasChosenMonster) {
            textUtils.drawNormalRoboto(
                    spriteBatch,
                    "You have already chosen a MoN",
                    Color.BLACK,
                    chooseMonMenu.getX() + chooseMonMenu.getWidth() / 6,
                    chooseMonMenu.getY() + chooseMonMenu.getHeight() - 100
            );
            textUtils.drawSmallRoboto(spriteBatch,
                    "Ok",
                    Color.BLACK,
                    chooseMonMenu.getX() + 50,
                    chooseMonMenu.getY() + 50);
            spriteBatch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.triangle(
                    chooseMonMenu.getX() + 25, chooseMonMenu.getY() + 35,
                    chooseMonMenu.getX() + 40, chooseMonMenu.getY() + 45,
                    chooseMonMenu.getX() + 25, chooseMonMenu.getY() + 55);
            shapeRenderer.end();
        }else if(monsters.isEmpty() || entity.getPart(MonsterTeamPart.class) == null){ //Null check on monsterteampart, as that would happen if monsters are unloaded on map
            textUtils.drawNormalRoboto(
                    spriteBatch,
                    "This machine contains no monsters.",
                    Color.BLACK,
                    chooseMonMenu.getX() + chooseMonMenu.getWidth() / 6.5f,
                    chooseMonMenu.getY() + chooseMonMenu.getHeight() - 100
            );
            spriteBatch.end();
        } else {
            textUtils.drawBigRoboto(spriteBatch,
                    "Choose Your MoN",
                    Color.BLACK,
                    chooseMonMenu.getX() + chooseMonMenu.getWidth()/4,
                    chooseMonMenu.getY() + chooseMonMenu.getHeight() - 50);
            int distanceBetweenOptions = 40;
            int monStartx = 150;
            for(int i = 0; i < monAmount; i++){
                String monName = monsters.get(i).getName();
                textUtils.drawSmallRoboto(spriteBatch,
                        monName,
                        Color.BLACK,
                        chooseMonMenu.getX() + monStartx + (84 * i),
                        chooseMonMenu.getY() + 50);
                Texture currentSprite = AssetLoader.getInstance().getTextureAsset(monsters.get(i).getFrontSprite(), monsters.get(i).getClass());
                spriteBatch.draw(currentSprite,
                        chooseMonMenu.getX() + monStartx + (80 * i),
                        chooseMonMenu.getY() + 60,
                        50, 50);
            }

            spriteBatch.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);

            int triangleHeight = 20;

            monIndex = monIndex % monAmount;

            //int renderHeight = chooseWidth - triangleHeight - normalTextHeight;
            int renderHeight = monStartx + monIndex * distanceBetweenOptions * 2;

            shapeRenderer.triangle(
                    chooseMonMenu.getX() + renderHeight, chooseMonMenu.getY() + 15,
                    chooseMonMenu.getX() + triangleHeight / 2f + renderHeight, chooseMonMenu.getY() + 30,
                    chooseMonMenu.getX() + triangleHeight + renderHeight, chooseMonMenu.getY() + 15
            );
            shapeRenderer.end();
        }
    }

    @Override
    public void handleInput(GameData gameData) {
        if (hasChosenMonster){
            if (gameData.getKeys().isDown(GameKeys.ACTION)){
                eventDone = true;
            }
        }

        if(gameData.getKeys().isPressed(GameKeys.RIGHT)){
            if(monIndex == monsters.size()){
                monIndex = 0;
            } else {
                monIndex++;
            }
        } else if (gameData.getKeys().isPressed(GameKeys.LEFT)){
            if(monIndex == 0){
                monIndex = monsters.size();
            } else {
                monIndex--;
            }
        } else if (gameData.getKeys().isDown(GameKeys.ACTION)){
            if(monsters.isEmpty()){
                eventDone = true;
                return;
            }

            VendingMachineVisitedPart visitedPart = entity.getPart(VendingMachineVisitedPart.class);
            if(visitedPart == null) {
                visitedPart = new VendingMachineVisitedPart();
                entity.add(visitedPart);
            }
            if(visitedPart.getUuids().contains(this.machineUuid))
                return;

            MonsterTeamPart mtp = entity.getPart(MonsterTeamPart.class);
            if(mtp == null){
                eventDone = true;
                System.out.println("[Warning] Player does not have a monsterTeamPart");
                return;
            }
            mtp.getMonsterTeam().add(monsters.get(monIndex));
            visitedPart.addUuid(this.machineUuid);
            eventDone = true;
        }
    }
}
