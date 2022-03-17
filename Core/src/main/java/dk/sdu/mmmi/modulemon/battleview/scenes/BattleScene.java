package dk.sdu.mmmi.modulemon.battleview.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dk.sdu.mmmi.modulemon.battleview.Position;
import dk.sdu.mmmi.modulemon.battleview.TextUtils;
import dk.sdu.mmmi.modulemon.main.Game;
import org.lwjgl.opengl.GL11;

public class BattleScene {

    private Image _backdrop;
    private Image _playerBase;
    private Image _enemyBase;
    private Image _playerMonster;
    private Image _enemyMonster;

    private Position _backdropPosition;
    private Position _playerBasePosition;
    private Position _enemyBasePosition;
    private Position _playerMonsterPosition;
    private Position _enemyMonsterPosition;
    private Position _healthBoxPosition;
    private Position _actionBoxPosition;
    private float _actionBoxAlpha = 1;

    private String enemyMonsterName;
    private String enemyHP;
    private String playerMonsterName;
    private String playerHP;
    private String textToDisplay = "";

    private String actionTitle = "";
    private Object[] actions = new Object[0];
    private int selectedActionIndex;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private TextUtils textUtils = TextUtils.getInstance();

    public BattleScene() {
        _backdrop = new Image(new Texture(Gdx.files.internal("assets/battleart/backdrop1.png")));
        _playerBase = new Image(new Texture(Gdx.files.internal("assets/battleart/playerbase.png")));
        _enemyBase = new Image(new Texture(Gdx.files.internal("assets/battleart/enemybase.png")));
        _playerMonster = new Image(new Texture(Gdx.files.internal("assets/monsters/001b.png")));
        _enemyMonster = new Image(new Texture(Gdx.files.internal("assets/monsters/001.png")));
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(Game.cam.combined);
        shapeRenderer = new ShapeRenderer();
        resetPositions();
    }

    public void draw() {
        //DRAW THE IMAGES

        int textBoxHeight = 100;
        spriteBatch.begin();

        _backdropPosition.updatePosition(_backdrop);
        _playerBasePosition.updatePosition(_playerBase);
        _enemyBasePosition.updatePosition(_enemyBase);
        _playerMonsterPosition.updatePosition(_playerMonster);
        _enemyMonsterPosition.updatePosition(_enemyMonster);

        _backdrop.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        _backdrop.draw(spriteBatch, 1);
        _playerBase.draw(spriteBatch, 1);
        _enemyBase.draw(spriteBatch, 1);
        _playerMonster.draw(spriteBatch, 1);
        _enemyMonster.draw(spriteBatch, 1);

        spriteBatch.end();


        //DRAW THE BOXES
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.WHITE);

        Gdx.gl.glEnable(GL20.GL_BLEND); //Alows for opacity
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //HP Box
        shapeRenderer.rect(
                _healthBoxPosition.getX(),
                _healthBoxPosition.getY(),
                300, 400
        );

        //Action box
        if (actions.length > 0) {
            shapeRenderer.setColor(1, 1, 1, _actionBoxAlpha);
            shapeRenderer.rect(
                    _actionBoxPosition.getX(),
                    _actionBoxPosition.getY(),
                    250, 200
            );
            shapeRenderer.setColor(Color.WHITE);
        }

        //Text box
        shapeRenderer.rect(
                20,
                20,
                Game.WIDTH - 40, textBoxHeight
        );

        shapeRenderer.end();


        //START DRAWING TEXT ON THE BOXES
        spriteBatch.begin();

        //Health box
        textUtils.drawNormalRoboto(spriteBatch, "Opponent: " + this.enemyMonsterName, Color.BLACK, _healthBoxPosition.getX() + 10, _healthBoxPosition.getY() + 386);
        textUtils.drawNormalRoboto(spriteBatch, "HP: " + this.enemyHP, Color.BLACK, _healthBoxPosition.getX() + 10, _healthBoxPosition.getY() + 356);
        textUtils.drawNormalRoboto(spriteBatch, "Your monster: " + this.playerMonsterName, Color.BLACK, _healthBoxPosition.getX() + 10, _healthBoxPosition.getY() + 55);
        textUtils.drawNormalRoboto(spriteBatch, "HP: " + this.playerHP, Color.BLACK, _healthBoxPosition.getX() + 10, _healthBoxPosition.getY() + 25);


        //Action box
        int topActionTextOffset = 150;
        if (actions.length > 0) {
            Color actionTextColor = new Color(0,0,0, _actionBoxAlpha);
            textUtils.drawNormalRoboto(spriteBatch, actionTitle, actionTextColor, _actionBoxPosition.getX() + 10, _actionBoxPosition.getY() + 186);

            for (int i = 0; i < actions.length; i++) {
                textUtils.drawSmallRoboto(spriteBatch, actions[i].toString(), actionTextColor, _actionBoxPosition.getX() + 60, _actionBoxPosition.getY() + topActionTextOffset - (i * 30));
            }
        }

        // Text box
        if(!textToDisplay.isEmpty())
            textUtils.drawNormalRoboto(spriteBatch, textToDisplay, Color.BLACK, 30, textBoxHeight);

        spriteBatch.end();

        // ACTION SELECTOR TRIANGLE

        if (actions.length > 0) {

            Gdx.gl.glEnable(GL20.GL_BLEND); //Alows for opacity
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0,0,0, _actionBoxAlpha);

            //int renderHeight = (topActionTextOffset - ((selectedActionIndex + 1) * 25));
            int triangleHeight = 20;
            int smallTextHeight = 15;
            int normalTextHeight = 24;
            int actionTopTextHeight = 186;
            int offsetFromActionHeadToFirstAction = 8;


            int renderHeight = actionTopTextHeight - triangleHeight - normalTextHeight - offsetFromActionHeadToFirstAction;
            renderHeight = renderHeight + selectedActionIndex * -smallTextHeight * 2;

            shapeRenderer.triangle(
                    _actionBoxPosition.getX() + 30, _actionBoxPosition.getY() + renderHeight,
                    _actionBoxPosition.getX() + 45, _actionBoxPosition.getY() + triangleHeight/2f + renderHeight,
                    _actionBoxPosition.getX() + 30, _actionBoxPosition.getY() + triangleHeight + renderHeight
            );
            shapeRenderer.end();

        }
        //Cleanup for opacity
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /* VISUAL SHENANIGANS */

    public void setTextToDisplay(String textToDisplay) {
        this.textToDisplay = textToDisplay;
    }

    public void setEnemyMonsterName(String enemyMonsterName) {
        this.enemyMonsterName = enemyMonsterName;
    }

    public void setEnemyHP(String enemyHP) {
        this.enemyHP = enemyHP;
    }

    public void setPlayerMonsterName(String playerMonsterName) {
        this.playerMonsterName = playerMonsterName;
    }

    public void setPlayerHP(String playerHP) {
        this.playerHP = playerHP;
    }

    public void setActions(Object[] actions) {
        this.actions = actions;
    }

    public Object[] getActions() {
        return this.actions;
    }

    public void setSelectedActionIndex(int selectedActionIndex) {
        this.selectedActionIndex = selectedActionIndex;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    /* POSITION SHENANIGANS */

    public Position getBackdropPosition() {
        return _backdropPosition;
    }

    public void setBackdropPosition(Position _backdropPosition) {
        this._backdropPosition = _backdropPosition;
    }

    public Position getPayerBasePosition() {
        return _playerBasePosition;
    }

    public void setPlayerBasePosition(Position _playerBasePosition) {
        this._playerBasePosition = _playerBasePosition;
    }

    public Position getEnemyBasePosition() {
        return _enemyBasePosition;
    }

    public void setEnemyBasePosition(Position _enemyBasePosition) {
        this._enemyBasePosition = _enemyBasePosition;
    }

    public Position getPlayerMonsterPosition() {
        return _playerMonsterPosition;
    }

    public void setPlayerMonsterPosition(Position _playerMonsterPosition) {
        this._playerMonsterPosition = _playerMonsterPosition;
    }

    public Position getEnemyMonsterPosition() {
        return _enemyMonsterPosition;
    }

    public void setEnemyMonsterPosition(Position _enemyMonsterPosition) {
        this._enemyMonsterPosition = _enemyMonsterPosition;
    }

    public Position getHealthBoxPosition() {
        return _healthBoxPosition;
    }

    public Position getActionBoxPosition() {
        return _actionBoxPosition;
    }

    public void setActionBoxPosition(Position _actionBoxPosition) {
        this._actionBoxPosition = _actionBoxPosition;
    }

    public float getActionBoxAlpha() {
        return _actionBoxAlpha;
    }

    public void setActionBoxAlpha(float _actionBoxOpacity) {
        this._actionBoxAlpha = _actionBoxOpacity;
    }

    public void setHealthBoxPosition(Position _healthBoxPosition) {
        this._healthBoxPosition = _healthBoxPosition;
    }

    private void resetPositions() {
        _backdropPosition = new Position(0, 0);
        _playerBasePosition = new Position(200, 80);
        _enemyBasePosition = new Position(800, 400);
        _enemyMonsterPosition = new Position(850, 400);
        _playerMonsterPosition = new Position(300, 80);
        _healthBoxPosition = new Position(100, 300);
        _actionBoxPosition = new Position(Game.WIDTH - 300, 125);
    }


}
