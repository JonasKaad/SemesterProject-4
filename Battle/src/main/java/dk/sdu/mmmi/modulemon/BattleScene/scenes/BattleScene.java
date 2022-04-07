package dk.sdu.mmmi.modulemon.BattleScene.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dk.sdu.mmmi.modulemon.BattleScene.*;
import dk.sdu.mmmi.modulemon.common.drawing.OSGiFileHandle;
import dk.sdu.mmmi.modulemon.common.drawing.Position;
import dk.sdu.mmmi.modulemon.common.drawing.Rectangle;

public class BattleScene {

    private Image _backdrop;
    private Image _playerBase;
    private Image _enemyBase;
    private String _playerMonsterSpritePath = "";
    private String _enemyMonsterSpritePath = "";
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
    private Position _healthIndicatorPosition;
    private Color _healthIndicatorColor;
    private String _healthIndicatorText;
    private Position _actionBoxPosition;
    private Position _textBoxPosition;
    private Rectangle _textBoxRect;
    private Rectangle _actionBoxRect;
    private Rectangle _enemyHealthRect;
    private Rectangle _playerHealthRect;
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
        _backdrop = new Image(new Texture(new OSGiFileHandle("/battleart/backdrop1.png", this.getClass())));
        _playerBase = new Image(new Texture(new OSGiFileHandle("/battleart/playerbase1.png", this.getClass())));
        _enemyBase = new Image(new Texture(new OSGiFileHandle("/battleart/enemybase1.png", this.getClass())));
        _textBoxRect =  new Rectangle(-100,-100,  0,0); // All these values are dynamically calculated anyway
        _actionBoxRect = new Rectangle(-100,-100, BattleSceneDefaults.actionBoxWidth(), BattleSceneDefaults.actionBoxHeight());
        _enemyHealthRect = new Rectangle(-100,-100, BattleSceneDefaults.enemyHealthBoxWidth(), BattleSceneDefaults.enemyHealthBoxHeight());
        _playerHealthRect = new Rectangle(-100,-100, BattleSceneDefaults.playerHealthBoxWidth(), BattleSceneDefaults.playerHealthBoxHeight());
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        resetPositions();
    }

    public void draw(float dt) {
        //DRAW THE IMAGES

        int textBoxHeight = 100;
        int borderWidth = 2;
        spriteBatch.begin();

        _backdropPosition.updatePosition(_backdrop);
        _playerBasePosition.updatePosition(_playerBase);
        _enemyBasePosition.updatePosition(_enemyBase);
        if(_playerMonster != null) {
            _playerMonster.setOrigin(_playerMonster.getImageWidth() / 2, _playerMonster.getImageHeight() / 2);
            _playerMonster.setRotation(_playerMonsterRotation);
            _playerMonsterPosition.updatePosition(_playerMonster);
        }
        if(_enemyMonster != null) {
            _enemyMonster.setOrigin(_enemyMonster.getImageWidth() / 2, _enemyMonster.getImageHeight() / 2);
            _enemyMonster.setRotation(_enemyMonsterRotation);
            _enemyMonsterPosition.updatePosition(_enemyMonster);
        }

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
        _enemyHealthRect.setPosition(_enemyHealthBoxPosition);
        _enemyHealthRect.draw(shapeRenderer, dt);

        _playerHealthRect.setPosition(_playerHealthBoxPosition);
        _playerHealthRect.draw(shapeRenderer, dt);

        //Action box
        if (actions.length > 0) {
            _actionBoxRect.setPosition(_actionBoxPosition);
            _actionBoxRect.setBorderColor(new Color(0, 0, 0, _actionBoxAlpha));
            _actionBoxRect.setFillColor(new Color(1,1,1, _actionBoxAlpha));
            _actionBoxRect.draw(shapeRenderer, dt);
        }

        //Text box
        _textBoxRect.setPosition(_textBoxPosition);
        _textBoxRect.setWidth(this.gameWidth-40);
        _textBoxRect.setHeight(textBoxHeight);
        _textBoxRect.draw(shapeRenderer, dt);

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
            textUtils.drawNormalRoboto(spriteBatch, _healthIndicatorText, _healthIndicatorColor, _healthIndicatorPosition.getX(), _healthIndicatorPosition.getY());

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

    public String getTextToDisplay() {
        return this.textToDisplay;
    }

    public void setEnemySprite(String spritePath, Class reference) {
        if(!spritePath.equalsIgnoreCase(this._enemyMonsterSpritePath)) {
            this._enemyMonster = new Image(new Texture(new OSGiFileHandle(spritePath, reference)));
            this._enemyMonsterSpritePath = spritePath;
        }
    }

    public void setEnemyMonsterName(String enemyMonsterName) {
        this.enemyMonsterName = enemyMonsterName;
    }

    public void setEnemyHP(String enemyHP) {
        this.enemyHP = enemyHP;
    }

    public void setPlayerSprite(String spritePath, Class reference) {
        if(!spritePath.equalsIgnoreCase(this._playerMonsterSpritePath)) {
            this._playerMonster = new Image(new Texture(new OSGiFileHandle(spritePath, reference)));
            this._playerMonsterSpritePath = spritePath;
        }
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

    public Rectangle getEnemyBoxRect(){
        return this._enemyHealthRect;
    }

    public void setEnemyBoxRectStyle(Class<? extends Rectangle> clazz){
        try {
            _enemyHealthRect = (Rectangle) clazz.getDeclaredConstructors()[0].newInstance(100,-100, BattleSceneDefaults.enemyHealthBoxWidth(), BattleSceneDefaults.enemyHealthBoxHeight());
        } catch (Exception _) {
            System.out.println("[WARNING] Failed to change rectangles.");
        }
    }

    public Position getPlayerHealthBoxPosition() {
        return _playerHealthBoxPosition;
    }

    public void setPlayerHealthBoxPosition(Position _playerHealthBoxPosition) {
        this._playerHealthBoxPosition = _playerHealthBoxPosition;
    }

    public Rectangle getPlayerBoxRect(){
        return this._playerHealthRect;
    }

    public void setPlayerBoxRectStyle(Class<? extends Rectangle> clazz){
        try {
            _playerHealthRect = (Rectangle) clazz.getDeclaredConstructors()[0].newInstance(100,-100, BattleSceneDefaults.playerHealthBoxWidth(), BattleSceneDefaults.playerHealthBoxHeight());
        } catch (Exception _) {
            System.out.println("[WARNING] Failed to change rectangles.");
        }
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

    public Rectangle getActionBoxRect(){
        return this._actionBoxRect;
    }

    public void setActionBoxRectStyle(Class<? extends Rectangle> clazz){
        try {
            _actionBoxRect = (Rectangle) clazz.getDeclaredConstructors()[0].newInstance(100,-100, BattleSceneDefaults.actionBoxWidth(), BattleSceneDefaults.actionBoxHeight());
        } catch (Exception _) {
            System.out.println("[WARNING] Failed to change rectangles.");
        }
    }

    public Position getHealthIndicator() {
        return _healthIndicatorPosition;
    }

    public void setHealthIndicatorPosition(Position _healthIndicator) {
        this._healthIndicatorPosition = _healthIndicator;
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

    public Rectangle getTextBoxRect(){
        return this._textBoxRect;
    }

    public void setTextBoxRectStyle(Class<? extends Rectangle> clazz){
        try {
            // Arguments for this one, does not matter as it is calculated on the fly in draw()
            _textBoxRect = (Rectangle) clazz.getDeclaredConstructors()[0].newInstance(100,-100, 0, 0);
        } catch (Exception _) {
            System.out.println("[WARNING] Failed to change rectangles.");
        }
    }

    public void resetPositions() {
        _backdropPosition = BattleSceneDefaults.backdropPosition();
        _playerBasePosition = BattleSceneDefaults.playerBasePosition();
        _enemyBasePosition = BattleSceneDefaults.enemyBasePosition();
        _enemyMonsterPosition = BattleSceneDefaults.enemyMonsterPosition();
        _playerMonsterPosition =BattleSceneDefaults.playerMonsterPosition();
        _enemyHealthBoxPosition = BattleSceneDefaults.enemyHealthBoxPosition();
        _playerHealthBoxPosition = BattleSceneDefaults.playerHealthBoxPosition();
        _actionBoxPosition = BattleSceneDefaults.actionBoxPosition(this.gameWidth);
        _healthIndicatorPosition = BattleSceneDefaults.healthIndicatorPosition();
        _textBoxPosition = BattleSceneDefaults.textBoxPosition();
        _healthIndicatorColor = BattleSceneDefaults.healthIndicatorColor();
        _enemyMonsterRotation = BattleSceneDefaults.enemyMonsterRotation();
        _playerMonsterRotation = BattleSceneDefaults.playerMonsterRotation();
    }
}
