package dk.sdu.mmmi.modulemon.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.modulemon.CommonMap.Data.EntityParts.MonsterTeamPart;
import dk.sdu.mmmi.modulemon.CommonMonster.IMonster;
import dk.sdu.mmmi.modulemon.common.AssetLoader;
import dk.sdu.mmmi.modulemon.common.data.GameData;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;
import dk.sdu.mmmi.modulemon.common.drawing.TextUtils;


import java.util.ArrayList;
import java.util.List;

public class MonsterTeam {
    private static AssetLoader loader = AssetLoader.getInstance();
    public static void drawTeamOptions(GameData gameData, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, TextUtils textUtils, Rectangle teamActionMenu, Rectangle monsterTeamMenu, String[] teamActions){
            //Drawing options menu box
            shapeRenderer.setColor(Color.WHITE);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            teamActionMenu.setX(monsterTeamMenu.getX() + monsterTeamMenu.getWidth() + 20);
            teamActionMenu.setY(monsterTeamMenu.getY() + monsterTeamMenu.getHeight() - teamActionMenu.getHeight());
            teamActionMenu.draw(shapeRenderer, gameData.getDelta());
            shapeRenderer.end();

            //Drawing options menu text
            spriteBatch.begin();

            textUtils.drawNormalRoboto(
                    spriteBatch,
                    "Choose Action",
                    Color.BLACK,
                    teamActionMenu.getX() + 19,
                    teamActionMenu.getY() + teamActionMenu.getHeight() - 10);

            //Drawing options
            for (int i = 0; i < teamActions.length; i++) {
                textUtils.drawSmallRoboto(spriteBatch, teamActions[i], Color.BLACK, teamActionMenu.getX() + 42, teamActionMenu.getY() + (teamActionMenu.getHeight() * 2 / 3f) - (i * 40));
            }

            spriteBatch.end();
    }
    public static void drawMonsterTeam(GameData gameData, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, TextUtils textUtils, Camera cam, boolean showSwitchingText, Rectangle monsterTeamMenu, List<IMonster> monsterTeam, Rectangle[] monsterRectangles){



        //Drawing monster menu box
        shapeRenderer.setColor(Color.WHITE);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        monsterTeamMenu.setX(cam.position.x - cam.viewportWidth / 2.6f);
        monsterTeamMenu.setY(cam.position.y - cam.viewportHeight / 3.2f);
        monsterTeamMenu.draw(shapeRenderer, gameData.getDelta());
        shapeRenderer.end();


        //Drawing monster menu text
        spriteBatch.begin();



        textUtils.drawNormalRoboto(
                spriteBatch,
                "Your Team",
                Color.BLACK,
                monsterTeamMenu.getX() + 135,
                monsterTeamMenu.getY() + monsterTeamMenu.getHeight() - 10);

        // Draws the text telling the player how to change order/switch monsters
        if(showSwitchingText){
            textUtils.drawSmallRoboto(
                    spriteBatch,
                    "Select two Monsters to switch their order",
                    Color.BLACK,
                    monsterTeamMenu.getX() + 50,
                    monsterTeamMenu.getY() + monsterTeamMenu.getHeight() - 35);
        }

        //Drawing Names and HP

        for (int i = 0; i < monsterTeam.size(); i++) {

            IMonster currentMonster = monsterTeam.get(i);
            String spritePath = currentMonster.getFrontSprite();
            Class reference = currentMonster.getClass();
            Texture texture = loader.getTextureAsset(spritePath, reference);
            spriteBatch.draw(texture,monsterTeamMenu.getX() + 42, monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.6f) - (i * 80), 70, 70);
            textUtils.drawSmallRoboto(spriteBatch, "Name: \t" + currentMonster.getName(), Color.BLACK, monsterTeamMenu.getX() + 42+ 110, monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.3f) - (i * (80)));
            textUtils.drawSmallRoboto(spriteBatch, "HP: \t" + currentMonster.getHitPoints() + " / " + currentMonster.getMaxHitPoints(), Color.BLACK, monsterTeamMenu.getX() + 42 + 110, monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.45f) - (i * (80)));

        }
        spriteBatch.end();

                    /*
                    For some reason it needs two different for loops, otherwise it won't draw all the stuff.
                     */

        // Drawing boxes around Monsters
        shapeRenderer.begin();
        for (int i = 0; i < monsterTeam.size(); i++) {
            monsterRectangles[i].setX(monsterTeamMenu.getX() + 40);
            monsterRectangles[i].setY(monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() * 2 / 2.6f) - (i * 80));
            monsterRectangles[i].draw(shapeRenderer, gameData.getDelta());
            Gdx.gl20.glLineWidth(2);
        }
        shapeRenderer.end();
    }
    public static void drawSummary(GameData gameData, ShapeRenderer shapeRenderer, SpriteBatch spriteBatch, TextUtils textUtils, Rectangle summaryMenu, Rectangle monsterTeamMenu, MonsterTeamPart mtp, int selectedOptionIndexMonsterTeam) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        summaryMenu.setX(monsterTeamMenu.getX() + 10 );
        summaryMenu.setY(monsterTeamMenu.getY() + monsterTeamMenu.getHeight() - summaryMenu.getHeight() - 20);
        summaryMenu.draw(shapeRenderer, gameData.getDelta());
        shapeRenderer.end();

        //Drawing summary text
        spriteBatch.begin();
        IMonster currentMonster = mtp.getMonsterTeam().get(selectedOptionIndexMonsterTeam);
        String name = currentMonster.getName();

        textUtils.drawNormalRoboto(
                spriteBatch,
                name,
                Color.BLACK,
                summaryMenu.getX() + ((summaryMenu.getWidth() / 2) - ((summaryMenu.getWidth() / 2) / 2 / 2)) + 10,
                summaryMenu.getY() + summaryMenu.getHeight() - 10);

        //Drawing image
        String spritePath = currentMonster.getFrontSprite();
        Class reference = currentMonster.getClass();
        Texture texture = loader.getTextureAsset(spritePath, reference);
        spriteBatch.draw(texture, summaryMenu.getX() + 6, monsterTeamMenu.getY() + (monsterTeamMenu.getHeight() / 2) + 20, 160, 160);

        // Creating a list of stats needed in order. HP, Attack, Defence, Speed, Type
        List<String> stats = new ArrayList<>();
        stats.add("HP: " + currentMonster.getHitPoints() + " / " + currentMonster.getMaxHitPoints());
        stats.addAll(currentMonster.getStats());

        // Positional counter such that the following text can be drawn accordingly
        int posCounter = 0;
        //Drawing stats
        textUtils.drawSmallBoldRoboto(spriteBatch, "Stats:", Color.BLACK, summaryMenu.getX() + 170, summaryMenu.getY() + (summaryMenu.getHeight() * 2 / 2.4f));
        for (int i = 0; i < stats.size(); i++) {
            textUtils.drawSmallRoboto(spriteBatch, stats.get(i), Color.BLACK, summaryMenu.getX() + 170, summaryMenu.getY() + (summaryMenu.getHeight() * 2 / 2.4f) - ((i+1) * 20));
            posCounter = (i+1) * 20;
        }

        //Drawing moves

        // String manipulation to make the enum value only uppercase on first letter.
        // And to get the moves formatted in desired form
        List<String> moves = new ArrayList<>();
        for (int i = 0; i < currentMonster.getMoves().size(); i++) {
            moves.add(currentMonster.getMoves().get(i).getSummaryScreenDescription());
        }
        //Drawing the "Moves:" text first.
        textUtils.drawSmallBoldRoboto(spriteBatch, "Moves:", Color.BLACK, summaryMenu.getX() + 170, summaryMenu.getY() + (summaryMenu.getHeight() * 2 / 2.4f) - posCounter-30);
        //Drawing all moves that the monster has.
        for (int i = 0; i < moves.size(); i++) {
            textUtils.drawSmallRoboto(spriteBatch, moves.get(i), Color.BLACK, summaryMenu.getX() + 170, summaryMenu.getY() + (summaryMenu.getHeight() * 2 / 2.4f) - posCounter - 30 - ((i+1) * 20) );
        }
        spriteBatch.end();
    }
    public static void drawMonsterTeamTriangle(ShapeRenderer shapeRenderer, Color switchIndicatorColor, Rectangle monsterTeamMenu, int selectedOptionIndexMonsterTeam, List<IMonster> monsterTeam){

        int triangleHeight = 20;
        int heightBetweenOptions = 40;
        int normalTextHeight = 24;
        int actionTopTextHeight = (int) (monsterTeamMenu.getHeight() * 2 / 2.6f) + 80;
        int offsetFromActionHeadToFirstAction = 8;

        if (monsterTeam.size()==0) return;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(switchIndicatorColor);

        selectedOptionIndexMonsterTeam = selectedOptionIndexMonsterTeam % monsterTeam.size();

        int renderHeight = actionTopTextHeight - triangleHeight - normalTextHeight - offsetFromActionHeadToFirstAction;
        renderHeight = renderHeight + selectedOptionIndexMonsterTeam * -heightBetweenOptions * 2;

        shapeRenderer.triangle(
                monsterTeamMenu.getX() + 15, monsterTeamMenu.getY() + renderHeight,
                monsterTeamMenu.getX() + 30, monsterTeamMenu.getY() + triangleHeight / 2f + renderHeight,
                monsterTeamMenu.getX() + 15, monsterTeamMenu.getY() + triangleHeight + renderHeight
        );
        shapeRenderer.end();
    }
    public static void drawTeamOptionsTriangle(ShapeRenderer shapeRenderer, Rectangle teamActionMenu, int teamOptionIndex, String[] teamActions){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        int triangleHeight = 20;
        int heightBetweenOptions = 20;
        int normalTextHeight = 24;
        int actionTopTextHeight = (int) (teamActionMenu.getHeight() * 2 / 3f) + 40;
        int offsetFromActionHeadToFirstAction = 10;

        teamOptionIndex = teamOptionIndex % teamActions.length;

        int renderHeight = actionTopTextHeight - triangleHeight - normalTextHeight - offsetFromActionHeadToFirstAction;
        renderHeight = renderHeight + teamOptionIndex * -heightBetweenOptions * 2;

        shapeRenderer.triangle(
                teamActionMenu.getX() + 15, teamActionMenu.getY() + renderHeight,
                teamActionMenu.getX() + 30, teamActionMenu.getY() + triangleHeight / 2f + renderHeight,
                teamActionMenu.getX() + 15, teamActionMenu.getY() + triangleHeight + renderHeight
        );
        shapeRenderer.end();
    }
    public static void drawDefaultTriangle(ShapeRenderer shapeRenderer, Rectangle pauseMenu, int selectedOptionIndex, String[] pauseActions){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        int triangleHeight = 20;
        int heightBetweenOptions = 20;
        int normalTextHeight = 24;
        int actionTopTextHeight = (int) (pauseMenu.getHeight() * 2 / 3f) + 40;
        int offsetFromActionHeadToFirstAction = 10;

        selectedOptionIndex = selectedOptionIndex % pauseActions.length;

        int renderHeight = actionTopTextHeight - triangleHeight - normalTextHeight - offsetFromActionHeadToFirstAction;
        renderHeight = renderHeight + selectedOptionIndex * -heightBetweenOptions * 2;

        shapeRenderer.triangle(
                pauseMenu.getX() + 15, pauseMenu.getY() + renderHeight,
                pauseMenu.getX() + 30, pauseMenu.getY() + triangleHeight / 2f + renderHeight,
                pauseMenu.getX() + 15, pauseMenu.getY() + triangleHeight + renderHeight
        );
        shapeRenderer.end();
    }

}
