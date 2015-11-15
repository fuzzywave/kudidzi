package com.fuzzywave.kududzi.gamestates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fuzzywave.commons.gamestate.GameStateImpl;
import com.fuzzywave.commons.util.Parameters;
import com.fuzzywave.kududzi.KududziGame;

public class SplashGameState extends GameStateImpl {

    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;

    private static final float DT = 0.01f;
    private static final float SPLASH_MIN_TIME = 2.0f;
    private static final Color COLOR1 = Color.valueOf("fe8c00");
    private static final Color COLOR2 = Color.valueOf("f83600");

    private final float graphSmoothing = 1 / 8f;
    private final float graphScale = 5f;

    private final float logoSmoothing = 1 / 8f;
    private final float logoScale = 5f;

    private float totalTime;
    private Camera camera;
    private Viewport viewport;
    private BitmapFont logoFont;
    private BitmapFont graphFont;
    private BitmapFont loadingFont;
    private ShaderProgram distanceFieldShaderProgram;
    private String logoText;
    private String graphTopText;
    private String graphBottomText;
    private String loadingText;

    private float graphTextWidth;
    private float graphTextHeight;

    private float logoTextWidth;
    private float logoTextHeight;

    private float interpolationAlpha;
    private GlyphLayout glyphLayout;

    @Override
    public void init() {
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, this.camera);
        this.glyphLayout = new GlyphLayout();

        KududziGame.resourceManager.loadGroup("splash_screen");
        KududziGame.resourceManager.finishLoading();

        // TODO Load all assets
        //KududziGame.resourceManager.loadGroup();

        logoFont = KududziGame.resourceManager.get("data/splash_screen/FUZZYWAVE.fnt");
        graphFont = KududziGame.resourceManager.get("data/splash_screen/GRAPH.fnt");
        graphFont.getData().setScale(graphScale);
        loadingFont = KududziGame.resourceManager.get("data/splash_screen/LOADING.fnt");
        loadingFont.getData().setScale(logoScale);

        distanceFieldShaderProgram = KududziGame.resourceManager.get("data/splash_screen/distancefield");

        logoText = "FUZZYWAVE\nGames";
        graphTopText = "qwertyuiop[][poiuytrewqqwertyuiop[][poiuytrewq";
        graphBottomText = "WERTYYTREWQQWERTYYTREWQQWERTYYTREWQQWERTYYTREW";
        loadingText = "Loading...";

        glyphLayout.setText(graphFont, graphTopText, 0, graphTopText.length(), graphFont.getColor(), 0, Align.left, false, null);
        graphTextWidth = glyphLayout.width;
        graphTextHeight = glyphLayout.height;

        glyphLayout.setText(logoFont, loadingText, 0, loadingText.length(), logoFont.getColor(), 0, Align.left, false, null);
        logoTextWidth = glyphLayout.width;
        logoTextHeight = glyphLayout.height;

    }

    @Override
    public void dispose() {
        KududziGame.resourceManager.unloadGroup("splash_screen");
    }

    @Override
    public void resume() {
        totalTime = 0;
    }

    @Override
    public void update() {
        boolean loadingFinished = KududziGame.resourceManager.update();

        totalTime += delta;
        interpolationAlpha = Math.min(totalTime / SPLASH_MIN_TIME, 1.0f);

        if ((interpolationAlpha >= 1.0f) && (loadingFinished)) {
            // TODO switch screen.
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        KududziGame.spriteBatch.setProjectionMatrix(this.camera.combined);
        KududziGame.shapeRenderer.setProjectionMatrix(this.camera.combined);

        drawBackground();

        KududziGame.spriteBatch.begin();
        KududziGame.spriteBatch.setShader(distanceFieldShaderProgram);

        //drawLogo(0,360);
        drawGraph(-graphTextWidth / 2, graphTextHeight);
        drawLoading(0, 0);
        KududziGame.spriteBatch.setShader(null);
        KududziGame.spriteBatch.end();
    }

    private void drawBackground() {
        KududziGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        KududziGame.shapeRenderer.rect(-this.viewport.getWorldWidth() / 2,
                -this.viewport.getWorldHeight() / 2,
                this.viewport.getWorldWidth(),
                this.viewport.getWorldHeight(),
                SplashGameState.COLOR1,
                SplashGameState.COLOR2,
                SplashGameState.COLOR1,
                SplashGameState.COLOR2);
        KududziGame.shapeRenderer.end();
    }

    private void drawLogo(int x, int y) {
        final float logoSmoothing = 1 / 8f;
        final float logoScale = 3f;

        logoFont.getData().setScale(logoScale);
        setSmoothing(logoSmoothing / logoScale);

        glyphLayout.setText(logoFont, logoText, logoFont.getColor(), 0, Align.center, false);
        logoFont.draw(KududziGame.spriteBatch, glyphLayout, x, y);
    }

    private void drawGraph(float x, float y) {

        setSmoothing(graphSmoothing / graphScale);
        int graphStringLength = Math.round(Interpolation.linear.apply(.0f,
                graphTopText.length(),
                interpolationAlpha));

        glyphLayout.setText(graphFont, graphTopText, 0, graphStringLength, graphFont.getColor(), 0, Align.left, false, null);
        graphFont.draw(KududziGame.spriteBatch, glyphLayout, x, y);

        y -= glyphLayout.height * 4;

        glyphLayout.setText(graphFont, graphBottomText, 0, graphStringLength, graphFont.getColor(), 0, Align.left, false, null);
        graphFont.draw(KududziGame.spriteBatch, glyphLayout, x, y);

    }

    private void drawLoading(float x, float y) {
        // TODO draw loading
    }

    public void setSmoothing(float smoothing) {
        // TODO util'e tasi bunu.
        float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
        distanceFieldShaderProgram.setUniformf("u_lower", 0.5f - delta);
        distanceFieldShaderProgram.setUniformf("u_upper", 0.5f + delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.viewport.update(width, height);
    }

    @Override
    public Parameters getParameters() {
        return null;
    }
}
