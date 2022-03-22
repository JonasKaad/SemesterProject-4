package dk.sdu.mmmi.modulemon.BattleScene.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dk.sdu.mmmi.modulemon.BattleScene.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.BattleScene.Position;
import dk.sdu.mmmi.modulemon.BattleScene.TextUtils;

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
    private float _playerMonsterRotation;
    private Position _enemyMonsterPosition;
    private float _enemyMonsterRotation;
    private Position _enemyHealthBoxPosition;
    private Position _playerHealthBoxPosition;
    private Position _healthIndicator;
    private Color _healthIndicatorColor;
    private String _healthIndicatorText;
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

    private int gameWidth;
    private int gameHeight;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private TextUtils textUtils = TextUtils.getInstance();

    public BattleScene() {
        _backdrop = new Image(new Texture(new OSGiFileHandle("/battleart/backdrop1.png")));
        _playerBase = new Image(new Texture(new OSGiFileHandle("/battleart/playerbase.png")));
        _enemyBase = new Image(new Texture(new OSGiFileHandle("/battleart/enemybase.png")));
        spriteBatch = new SpriteBatch();
        //spriteBatch.setProjectionMatrix(Game.cam.combined);
        shapeRenderer = new ShapeRenderer();
        resetPositions();
    }

    public void draw() {
        //DRAW THE IMAGES

        int textBoxHeight = 100;
        int borderWidth = 2;
        spriteBatch.begin();

        _backdropPosition.updatePosition(_backdrop);
        _playerBasePosition.updatePosition(_playerBase);
        _enemyBasePosition.updatePosition(_enemyBase);
        _playerMonster.setOrigin(_playerMonster.getImageWidth() / 2, _playerMonster.getImageHeight() / 2);
        _playerMonster.setRotation(_playerMonsterRotation);
        _playerMonsterPosition.updatePosition(_playerMonster);
        _enemyMonster.setOrigin(_enemyMonster.getImageWidth() / 2, _enemyMonster.getImageHeight() / 2);
        _enemyMonster.setRotation(_enemyMonsterRotation);
        _enemyMonsterPosition.updatePosition(_enemyMonster);

        _backdrop.setSize(this.gameWidth, this.gameHeight);

        _backdrop.draw(spriteBatch, 1);
        _playerBase.draw(spriteBatch, 1);
        _enemyBase.draw(spriteBatch, 1);
        if (_playerMonster != null)
            _playerMonster.draw(spriteBatch, 1);

        if (_enemyMonster != null)
            _enemyMonster.draw(spriteBatch, 1);

        spriteBatch.end();


        //DRAW THE BOXES
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.WHITE);

        Gdx.gl.glEnable(GL20.GL_BLEND); //Alows for opacity
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //HP Box

        //shapeRenderer.rect(
        DrawingUtils.borderedRect(
                shapeRenderer,
                _enemyHealthBoxPosition.getX(),
                _enemyHealthBoxPosition.getY(),
                300, 100,
                Color.BLACK, Color.WHITE,
                borderWidth
        );

        DrawingUtils.borderedRect(
                shapeRenderer,
                _playerHealthBoxPosition.getX(),
                _playerHealthBoxPosition.getY(),
                350, 100,
                Color.BLACK, Color.WHITE,
                borderWidth
        );

        //Action box
        if (actions.length > 0) {
            shapeRenderer.setColor(1, 1, 1, _actionBoxAlpha);
            DrawingUtils.borderedRect(
                    shapeRenderer,
                    _actionBoxPosition.getX(),
                    _actionBoxPosition.getY(),
                    250, 200,
                    new Color(0, 0, 0, _actionBoxAlpha),
                    new Color(1, 1, 1, _actionBoxAlpha),
                    borderWidth
            );
            shapeRenderer.setColor(Color.WHITE);
        }

        //Text box
        DrawingUtils.borderedRect(
                shapeRenderer,
                20,
                20,
                this.gameWidth - 40, textBoxHeight,
                Color.BLACK, Color.WHITE,
                borderWidth
        );

        shapeRenderer.end();


        //START DRAWING TEXT ON THE BOXES
        spriteBatch.begin();

        //Health box
        textUtils.drawNormalRoboto(spriteBatch, "Opponent: " + this.enemyMonsterName, Color.BLACK, _enemyHealthBoxPosition.getX() + 10, _enemyHealthBoxPosition.getY() + 85);
        textUtils.drawNormalRoboto(spriteBatch, "HP: " + this.enemyHP, Color.BLACK, _enemyHealthBoxPosition.getX() + 10, _enemyHealthBoxPosition.getY() + 55);
        textUtils.drawNormalRoboto(spriteBatch, "Your monster: " + this.playerMonsterName, Color.BLACK, _playerHealthBoxPosition.getX() + 10, _playerHealthBoxPosition.getY() + 85);
        textUtils.drawNormalRoboto(spriteBatch, "HP: " + this.playerHP, Color.BLACK, _playerHealthBoxPosition.getX() + 10, _playerHealthBoxPosition.getY() + 55);

        //Draw health indicator
        if (_healthIndicatorText != null && !_healthIndicatorText.isEmpty())
            textUtils.drawNormalRoboto(spriteBatch, _healthIndicatorText, _healthIndicatorColor, _healthIndicator.getX(), _healthIndicator.getY());

        //Action box
        int topActionTextOffset = 150;
        if (actions.length > 0) {
            Color actionTextColor = new Color(0, 0, 0, _actionBoxAlpha);
            textUtils.drawNormalRoboto(spriteBatch, actionTitle, actionTextColor, _actionBoxPosition.getX() + 10, _actionBoxPosition.getY() + 186);

            for (int i = 0; i < actions.length; i++) {
                textUtils.drawSmallRoboto(spriteBatch, actions[i].toString(), actionTextColor, _actionBoxPosition.getX() + 60, _actionBoxPosition.getY() + topActionTextOffset - (i * 30));
            }
        }

        // Text box
        if (!textToDisplay.isEmpty())
            textUtils.drawNormalRoboto(spriteBatch, textToDisplay, Color.BLACK, 30, textBoxHeight);

        spriteBatch.end();

        // ACTION SELECTOR TRIANGLE

        if (actions.length > 0) {

            Gdx.gl.glEnable(GL20.GL_BLEND); //Alows for opacity
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, _actionBoxAlpha);

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
                    _actionBoxPosition.getX() + 45, _actionBoxPosition.getY() + triangleHeight / 2f + renderHeight,
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

    public void setEnemySprite(Texture frontSprite) {
        this._enemyMonster = new Image(frontSprite);
    }

    public void setEnemyMonsterName(String enemyMonsterName) {
        this.enemyMonsterName = enemyMonsterName;
    }

    public void setEnemyHP(String enemyHP) {
        this.enemyHP = enemyHP;
    }

    public void setPlayerSprite(Texture frontSprite) {
        this._playerMonster = new Image(frontSprite);
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

    public int getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
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

    public float getPlayerMonsterRotation() {
        return _playerMonsterRotation;
    }

    public void setPlayerMonsterRotation(float playerMonsterRotation) {
        this._playerMonsterRotation = playerMonsterRotation;
    }

    public float getEnemyMonsterRotation() {
        return _enemyMonsterRotation;
    }

    public void setEnemyMonsterRotation(float enemyMonsterRotation) {
        this._enemyMonsterRotation = enemyMonsterRotation;
    }

    public Position getEnemyHealthBoxPosition() {
        return _enemyHealthBoxPosition;
    }

    public void setEnemyHealthBoxPosition(Position _enemyHealthBoxPosition) {
        this._enemyHealthBoxPosition = _enemyHealthBoxPosition;
    }

    public Position getPlayerHealthBoxPosition() {
        return _playerHealthBoxPosition;
    }

    public void setPlayerHealthBoxPosition(Position _playerHealthBoxPosition) {
        this._playerHealthBoxPosition = _playerHealthBoxPosition;
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

    public Position getHealthIndicator() {
        return _healthIndicator;
    }

    public void setHealthIndicatorPosition(Position _healthIndicator) {
        this._healthIndicator = _healthIndicator;
    }

    public Color getHealthIndicatorColor() {
        return _healthIndicatorColor;
    }

    public void setHealthIndicatorColor(Color _healthIndicatorColor) {
        this._healthIndicatorColor = _healthIndicatorColor;
    }

    public void setHealthIndicatorText(String healthIndicatorText) {
        this._healthIndicatorText = healthIndicatorText;
    }

    public String getHealthIndicatorText() {
        return this._healthIndicatorText;
    }

    public void resetPositions() {
        _backdropPosition = new Position(0, 0);
        _playerBasePosition = new Position(200, 120);
        _enemyBasePosition = new Position(800, 400);
        _enemyMonsterPosition = new Position(850, 400);
        _playerMonsterPosition = new Position(300, 80);
        _enemyHealthBoxPosition = new Position(480, 550);
        _playerHealthBoxPosition = new Position(100, 300);
        _actionBoxPosition = new Position(this.gameWidth - 300, 135);
        _healthIndicator = new Position(-100, -100);
        _healthIndicatorColor = Color.RED;
        _enemyMonsterRotation = 0f;
        _playerMonsterRotation = 0f;
    }
}
