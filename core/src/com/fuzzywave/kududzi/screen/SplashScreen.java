package com.fuzzywave.kududzi.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.fuzzywave.commons.GameApplication;
import com.fuzzywave.commons.game.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.resources.ResourceManager;
import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.ScreenManager;
import com.fuzzywave.commons.screen.transition.FadeInTransition;
import com.fuzzywave.commons.screen.transition.FadeOutTransition;
import com.fuzzywave.commons.screen.transition.Transition;

public class SplashScreen implements Screen {

    private final int SCENE_WIDTH = 720;
    private final int SCENE_HEIGHT = 1280;
    private final float SPLASH_MIN_TIME = 2.0f;
    private final Color color1 = Color.valueOf("fe8c00");
    private final Color color2 = Color.valueOf("f83600");
    private final float graphSmoothing = 1 / 8f;
    private final float graphScale = 5f;
    private final float logoSmoothing = 1 / 8f;
    private final float logoScale = 5f;
    private ResourceManager resourceManager;
    private OrthographicCamera camera;
    private Viewport viewport;
    private float totalTime;

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

    public SplashScreen(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    private void loadResourcesRequiredOnStartup() {
        GameApplication.logger.info("Loading splash screen resources...");
        resourceManager.loadGroup("splash_screen");
        resourceManager.finishLoading();

        logoFont = resourceManager.get("data/splash_screen/FUZZYWAVE.fnt");
        graphFont = resourceManager.get("data/splash_screen/GRAPH.fnt");
        graphFont.getData().setScale(graphScale);
        loadingFont = resourceManager.get("data/splash_screen/LOADING.fnt");
        loadingFont.getData().setScale(logoScale);

        distanceFieldShaderProgram = resourceManager.get("data/splash_screen/distancefield");

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

    private void queueResourcesForLoading() {
        GameApplication.logger.info("Queing other resources...");
        // TODO Load all assets
    }

    @Override
    public void init(GameContainer gc) {

        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, this.camera);
        this.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.glyphLayout = new GlyphLayout();

        loadResourcesRequiredOnStartup();
        queueResourcesForLoading();
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends Screen> screenManager, float delta) {
        boolean loadingFinished = resourceManager.update();

        totalTime += delta;
        interpolationAlpha = Math.min(totalTime / SPLASH_MIN_TIME, 1.0f);

        if ((interpolationAlpha >= 1.0f) && (loadingFinished)) {
            GameApplication.logger.info("Exiting splash screen...");
            screenManager.enterGameScreen(Screens.GAME_SCREEN_ID,
                    new FadeOutTransition(),
                    new FadeInTransition());
        }
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
        // Do nothing.
    }

    @Override
    public void render(GameContainer gc, Graphics g) {

        g.setProjectionMatrix(camera.combined);

        drawBackground(g);

        g.spriteBatch.begin();
        g.setShader(distanceFieldShaderProgram);

        //drawLogo(g, 0, 360);
        drawGraph(g, -graphTextWidth / 2, graphTextHeight);
        drawLoading(g, 0, 0);

        g.clearShader();
        g.spriteBatch.end();
    }

    private void drawBackground(Graphics g) {
        g.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        g.shapeRenderer.rect(-this.viewport.getWorldWidth() / 2,
                -this.viewport.getWorldHeight() / 2,
                this.viewport.getWorldWidth(),
                this.viewport.getWorldHeight(),
                color1,
                color2,
                color1,
                color2);
        g.shapeRenderer.end();
    }

    private void drawLogo(Graphics g, int x, int y) {
        final float logoSmoothing = 1 / 8f;
        final float logoScale = 3f;

        logoFont.getData().setScale(logoScale);
        setSmoothing(logoSmoothing / logoScale);

        glyphLayout.setText(logoFont, logoText, logoFont.getColor(), 0, Align.center, false);
        logoFont.draw(g.spriteBatch, glyphLayout, x, y);
    }

    private void drawGraph(Graphics g, float x, float y) {

        setSmoothing(graphSmoothing / graphScale);
        int graphStringLength = Math.round(Interpolation.linear.apply(.0f,
                graphTopText.length(),
                interpolationAlpha));

        glyphLayout.setText(graphFont, graphTopText, 0, graphStringLength, graphFont.getColor(), 0, Align.left, false, null);
        graphFont.draw(g.spriteBatch, glyphLayout, x, y);

        y -= glyphLayout.height * 4;

        glyphLayout.setText(graphFont, graphBottomText, 0, graphStringLength, graphFont.getColor(), 0, Align.left, false, null);
        graphFont.draw(g.spriteBatch, glyphLayout, x, y);

    }

    private void drawLoading(Graphics g, float x, float y) {
        // TODO draw loading
    }

    public void setSmoothing(float smoothing) {
        // TODO util'e tasi bunu.
        float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
        distanceFieldShaderProgram.setUniformf("u_lower", 0.5f - delta);
        distanceFieldShaderProgram.setUniformf("u_upper", 0.5f + delta);
    }

    @Override
    public void onResize(int width, int height) {
        GameApplication.logger.debug("onResize called on SplashScreen " + width + " " + height);
        viewport.update(width, height);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDispose() {
        resourceManager.unloadGroup("splash_screen");
    }

    @Override
    public void onRestart() {

    }

    @Override
    public int getId() {
        return Screens.SPLASH_SCREEN_ID;
    }

    @Override
    public void preTransitionIn(Transition transitionIn) {

    }

    @Override
    public void postTransitionIn(Transition transitionIn) {

    }

    @Override
    public void preTransitionOut(Transition transitionOut) {

    }

    @Override
    public void postTransitionOut(Transition transitionOut) {

    }
}
