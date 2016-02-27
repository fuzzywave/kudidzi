package com.fuzzywave.commons.graphics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

public class Graphics {

    private Color color, backgroundColor, tint, defaultTint;
    private SpriteBatch spriteBatch;
    private ShapeTextureCache colorTextureCache;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private BitmapFont font;
    private ShaderProgram defaultShader;

    private float translationX, translationY;
    private float scaleX, scaleY;
    private float rotation, rotationX, rotationY;
    private float currentWidth, currentHeight;

    private int defaultBlendSrcFunc = GL20.GL_SRC_ALPHA, defaultBlendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
    private int lineHeight;
    private boolean rendering, renderingShapes;
    private Rectangle clip;

    public Graphics(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;

        defaultTint = spriteBatch.getColor();
        if (defaultTint != null) {
            font = new BitmapFont(true);
        }

        lineHeight = 1;
        color = Color.WHITE;
        backgroundColor = Color.BLACK;
        colorTextureCache = new ShapeTextureCache();

        translationX = 0;
        translationY = 0;
        scaleX = 1f;
        scaleY = 1f;
        rotation = 0f;
        rotationX = 0f;
        rotationY = 0f;

		/* Create Ortho camera so that 0,0 is in top-left */
        camera = new OrthographicCamera();
    }

    /**
     * Called by mini2Dx before rendering begins
     *
     * @param gameWidth  The current game window width
     * @param gameHeight The current game window height
     */
    public void preRender(int gameWidth, int gameHeight) {
        this.currentWidth = gameWidth;
        this.currentHeight = gameHeight;

        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);

        rendering = false;

        if (defaultShader == null) {
            defaultShader = SpriteBatch.createDefaultShader();
        }
    }

    /**
     * Called by mini2Dx after rendering
     */
    public void postRender() {
        endRendering();
        resetTransformations();
        clearShaderProgram();
        clearBlendFunction();
    }

    /**
     * Renders a line segment to the window in the current {@link Color} with
     * the set line height
     *
     * @param x1 X coordinate of point A
     * @param y1 Y coordinate of point A
     * @param x2 X coordinate of point B
     * @param y2 Y coordinate of point B
     */
    public void drawLineSegment(float x1, float y1, float x2, float y2) {
        beginRendering();
        endRendering();

		/* TODO: Move all shape rendering over to using ShapeRenderer */
        renderingShapes = true;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(color);
        shapeRenderer.rectLine(x1, y1, x2, y2, lineHeight);
        shapeRenderer.end();

        beginRendering();
    }

    /**
     * Renders a rectangle to the window in the current {@link Color} with the
     * set line height
     *
     * @param x      The x coordinate to render at
     * @param y      The y coordinate to render at
     * @param width  The width of the rectangle
     * @param height The height of the rectangle
     */
    public void drawRect(float x, float y, float width, float height) {
        beginRendering();

        int roundWidth = MathUtils.round(width);
        int roundHeight = MathUtils.round(height);

        spriteBatch.draw(colorTextureCache.getRectangleTexture(color, roundWidth, roundHeight, getLineHeight()), x, y,
                0, 0, roundWidth, roundHeight, 1f, 1f, 0, 0, 0, roundWidth, roundHeight, false, false);
    }

    /**
     * Fills a rectangle to the window in the current {@link Color}
     *
     * @param x      The x coordinate to render at
     * @param y      The y coordinate to render at
     * @param width  The width of the rectangle
     * @param height The height of the rectangle
     */
    public void fillRect(float x, float y, float width, float height) {
        beginRendering();

        spriteBatch.draw(colorTextureCache.getFilledRectangleTexture(color), x, y, 0, 0, width, height, 1f, 1f, 0, 0, 0,
                1, 1, false, false);
    }

    /**
     * Draws a circle to the window in the current {@link Color} with the set
     * line height
     *
     * @param centerX The x coordinate of the center of the circle
     * @param centerY The y coordinate of the center of the circle
     * @param radius  The radius of the circle
     */
    public void drawCircle(float centerX, float centerY, int radius) {
        beginRendering();

        float renderX = (centerX - radius);
        float renderY = (centerY - radius);

        Texture texture = colorTextureCache.getCircleTexture(color, radius, getLineHeight());
        spriteBatch.draw(texture, renderX, renderY, 0, 0, texture.getWidth(), texture.getHeight(), 1f, 1f, 0, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }

    /**
     * Fills a circle to the window in the current {@link Color}
     *
     * @param centerX The x coordinate of the center of the circle
     * @param centerY The y coordinate of the center of the circle
     * @param radius  The radius of the circle
     */
    public void fillCircle(float centerX, float centerY, int radius) {
        Texture texture = colorTextureCache.getFilledCircleTexture(color, radius);

        float renderX = (centerX - radius);
        float renderY = (centerY - radius);

        beginRendering();
        spriteBatch.draw(texture, renderX, renderY, 0, 0, texture.getWidth(), texture.getHeight(), 1f, 1f, 0, 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }

    /**
     * Draws a string to the window
     *
     * @param text The {@link String} to draw
     * @param x    The x coordinate to draw at
     * @param y    The y coordinate to draw at
     */
    public void drawString(String text, float x, float y) {
        if (font == null) {
            return;
        }
        beginRendering();
        font.setColor(color);
        font.draw(spriteBatch, text, x, y);
    }

    /**
     * Draws a string to the window, automatically wrapping it within a
     * specified width
     *
     * @param text        The {@link String} to draw
     * @param x           The x coordinate to draw at
     * @param y           The y coordinate to draw at
     * @param targetWidth The width to render the {@link String} at. Note: The string
     *                    will automatically wrapped if it is longer.
     */
    public void drawString(String text, float x, float y, float targetWidth) {
        drawString(text, x, y, targetWidth, Align.left);
    }

    /**
     * Draws a string to the window, automatically wrapping it within a
     * specified width and aligning it to the left, center or right of the width
     *
     * @param text            The {@link String} to draw
     * @param x               The x coordinate to draw at
     * @param y               The y coordinate to draw at
     * @param targetWidth     The width to render the {@link String} at. Note: The string
     *                        will automatically wrapped if it is longer.
     * @param horizontalAlign The horizontal alignment. Note: Use {@link Align} to retrieve
     *                        the appropriate value.
     */
    public void drawString(String text, float x, float y, float targetWidth, int horizontalAlign) {
        if (font == null) {
            return;
        }
        beginRendering();
        font.setColor(color);
        font.draw(spriteBatch, text, x, y, targetWidth, horizontalAlign, true);
    }

    /**
     * Draws a texture to this graphics context
     *
     * @param texture The {@link Texture} to draw
     * @param x       The x coordinate to draw at
     * @param y       The y coordinate to draw at
     */
    public void drawTexture(Texture texture, float x, float y) {
        drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
    }

    /**
     * Draws a texture to this graphics context
     *
     * @param texture The {@link Texture} to draw
     * @param x       The x coordinate to draw at
     * @param y       The y coordinate to draw at
     * @param flipY   True if the texture should be flipped vertically
     */
    public void drawTexture(Texture texture, float x, float y, boolean flipY) {
        drawTexture(texture, x, y, texture.getWidth(), texture.getHeight(), flipY);
    }

    /**
     * Draws a texture to this graphics context
     *
     * @param texture The {@link Texture} to draw
     * @param x       The x coordinate to draw at
     * @param y       The y coordinate to draw at
     * @param width   The width to draw the texture (this can stretch/shrink the
     *                texture if not matching the texture's width)
     * @param height  The height to draw the texture (this can stretch/shrink the
     *                texture if not matching the texture's height)
     */
    public void drawTexture(Texture texture, float x, float y, float width, float height) {
        drawTexture(texture, x, y, width, height, true);
    }

    /**
     * @param texture The {@link Texture} to draw
     * @param x       The x coordinate to draw at
     * @param y       The y coordinate to draw at
     * @param width   The width to draw the texture (this can stretch/shrink the
     *                texture if not matching the texture's width)
     * @param height  The height to draw the texture (this can stretch/shrink the
     *                texture if not matching the texture's height)
     * @param flipY   True if the texture should be flipped vertically
     */
    public void drawTexture(Texture texture, float x, float y, float width, float height, boolean flipY) {
        beginRendering();
        spriteBatch.draw(texture, x, y, 0, 0, width, height, 1f, 1f, 0, 0, 0, texture.getWidth(), texture.getHeight(),
                false, flipY);
    }

    /**
     * Draws a texture region to this graphics context
     *
     * @param textureRegion The {@link TextureRegion} to draw
     * @param x             The x coordinate to draw at
     * @param y             The y coordinate to draw at
     */
    public void drawTextureRegion(TextureRegion textureRegion, float x, float y) {
        drawTextureRegion(textureRegion, x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    /**
     * Draws a texture region to this graphics context
     *
     * @param textureRegion The {@link TextureRegion} to draw
     * @param x             The x coordinate to draw at
     * @param y             The y coordinate to draw at
     * @param width         The width to draw the region (this can stretch/shrink the
     *                      texture if not matching the region's width)
     * @param height        The height to draw the region (this can stretch/shrink the
     *                      texture if not matching the region's height)
     */
    public void drawTextureRegion(TextureRegion textureRegion, float x, float y, float width, float height) {
        beginRendering();
        spriteBatch.draw(textureRegion, x, y, 0f, 0f, width, height, 1f, 1f, 0f);
    }

    /**
     * Draws an instance of {@link Shape}
     *
     * @param shape The implementation of {@link Shape} to draw
     */
    public void drawShape(Shape shape) {
        shape.draw(this);
    }

    /**
     * Fills an instance of {@link Shape}
     *
     * @param shape The implementation of {@link Shape} to fill
     */
    public void fillShape(Shape shape) {
        shape.fill(this);
    }


    /**
     * Rotates the canvas by the provided degrees around the provided point
     *
     * @param degrees The degree value in a clockwise direction
     * @param x       The x coordinate to rotate around
     * @param y       The y coordinate to rotate around
     */
    public void rotate(float degrees, float x, float y) {
        if (rendering) {
            endRendering();
        }

        this.rotation += degrees;
        this.rotation = this.rotation % 360f;
        this.rotationX = x;
        this.rotationY = y;
    }

    /**
     * Scales the canvas (multiplies scale value)
     *
     * @param scaleX Scaling along the X axis
     * @param scaleY Scaling along the Y axis
     */
    public void scale(float scaleX, float scaleY) {
        if (rendering) {
            endRendering();
        }

        this.scaleX *= scaleX;
        this.scaleY *= scaleY;
    }

    /**
     * Sets the canvas scale
     *
     * @param scaleX Scaling along the X axis
     * @param scaleY Scaling along the Y axis
     */
    public void setScale(float scaleX, float scaleY) {
        if (rendering) {
            endRendering();
        }

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    /**
     * Resets scaling back to default values
     */
    public void clearScaling() {
        if (rendering) {
            endRendering();
        }

        scaleX = 1f;
        scaleY = 1f;
    }

    /**
     * Moves the graphics context by a certain amount of the X and Y axis
     *
     * @param translateX The x axis translation
     * @param translateY The y axis translation
     */
    public void translate(float translateX, float translateY) {
        if (rendering) {
            endRendering();
        }

        this.translationX += translateX;
        this.translationY += translateY;
    }

    /**
     * Sets the translation coordinates
     *
     * @param translateX The x axis translation
     * @param translateY The y axis translation
     */
    public void setTranslation(float translateX, float translateY) {
        if (rendering) {
            endRendering();
        }

        this.translationX = translateX;
        this.translationY = translateY;
    }

    /**
     * Sets the graphics context clip. Only pixels within this area will be
     * rendered
     *
     * @param x      The x coordinate the clip begins at
     * @param y      The y coordinate the clip begins at
     * @param width  The width of the clip
     * @param height The height of the clip
     */
    public void setClip(float x, float y, float width, float height) {
        if (rendering) {
            endRendering();
        }

        clip = new Rectangle(x, y, width, height);
    }

    /**
     * Sets the graphics context clip. Only pixels within this area will be
     * rendered
     *
     * @param clip The clip area
     */
    public void setClip(Rectangle clip) {
        if (rendering) {
            endRendering();
        }

        this.clip = clip;
    }

    /**
     * Removes the applied clip
     */
    public Rectangle removeClip() {
        if (rendering) {
            endRendering();
        }

        Rectangle result = clip;
        clip = null;
        return result;
    }

    /**
     * Removes the tinting {@link Color}
     */
    public void removeTint() {
        setTint(defaultTint);
    }

    /**
     * Enables blending during rendering
     */
    public void enableBlending() {
        spriteBatch.enableBlending();
    }

    /**
     * Disables blending during rendering
     */
    public void disableBlending() {
        spriteBatch.disableBlending();
    }

    /**
     * Returns the currently applied {@link ShaderProgram}
     *
     * @return
     */
    public ShaderProgram getShaderProgram() {
        return spriteBatch.getShader();
    }

    /**
     * Applies a {@link ShaderProgram} to this instance
     *
     * @param shaderProgram The {@link ShaderProgram} to apply
     */
    public void setShaderProgram(ShaderProgram shaderProgram) {
        spriteBatch.setShader(shaderProgram);
    }

    /**
     * Clears the {@link ShaderProgram} applied to this instance
     */
    public void clearShaderProgram() {
        spriteBatch.setShader(defaultShader);
    }

    /**
     * Sets the blend function to be applied
     * <p/>
     * <a href=
     * "http://lessie2d.tumblr.com/post/28673280483/opengl-blend-function-cheat-sheet-well-this-is"
     * >OpenGL Blend Function Cheatsheet</a>
     *
     * @param srcFunc Source GL function
     * @param dstFunc Destination GL function
     */
    public void setBlendFunction(int srcFunc, int dstFunc) {
        spriteBatch.setBlendFunction(srcFunc, dstFunc);
    }

    /**
     * Resets the blend function to its default
     */
    public void clearBlendFunction() {
        spriteBatch.setBlendFunction(defaultBlendSrcFunc, defaultBlendDstFunc);
    }

    /**
     * Immediately flushes everything rendered rather than waiting until the end
     * of rendering
     */
    public void flush() {
        spriteBatch.flush();
    }

    /**
     * This method allows for translation, scaling, etc. to be set before the
     * {@link SpriteBatch} begins
     */
    private void beginRendering() {
        if (!rendering) {
            applyTransformations();
            spriteBatch.begin();
            Gdx.gl.glClearStencil(0);
            Gdx.gl.glClear(GL20.GL_STENCIL_BUFFER_BIT);
            if (clip != null) {
                Gdx.gl.glEnable(GL20.GL_STENCIL_TEST);
                Gdx.gl.glColorMask(false, false, false, false);
                Gdx.gl.glDepthMask(false);
                Gdx.gl.glStencilFunc(GL20.GL_ALWAYS, 1, 1);
                Gdx.gl.glStencilOp(GL20.GL_REPLACE, GL20.GL_REPLACE, GL20.GL_REPLACE);

                spriteBatch.draw(colorTextureCache.getFilledRectangleTexture(Color.WHITE), clip.getX(), clip.getY(), 0f,
                        0f, clip.getWidth(), clip.getHeight(), 1f, 1f, 0, 0, 0, 1, 1, false, false);
                spriteBatch.end();

                Gdx.gl.glColorMask(true, true, true, true);
                Gdx.gl.glDepthMask(true);
                Gdx.gl.glStencilFunc(GL20.GL_EQUAL, 1, 1);
                Gdx.gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);

                spriteBatch.begin();
            }

            rendering = true;
        }
    }

    /**
     * Ends rendering
     */
    private void endRendering() {
        if (rendering) {
            undoTransformations();
            spriteBatch.end();
            if (renderingShapes) {
                shapeRenderer.end();
            }

            if (clip != null) {
                Gdx.gl.glDisable(GL20.GL_STENCIL_TEST);
            }
        }
        rendering = false;
        renderingShapes = false;
    }

    /**
     * Applies all translation, scaling and rotation to the {@link SpriteBatch}
     */
    private void applyTransformations() {
        float viewportWidth = MathUtils.round(currentWidth / scaleX);
        float viewportHeight = MathUtils.round(currentHeight / scaleY);

        camera.setToOrtho(true, viewportWidth, viewportHeight);

        if (translationX != 0f || translationY != 0f) {
            camera.translate(translationX, translationY);
        }
        camera.update();

        if (rotation != 0f) {
            camera.rotateAround(new Vector3(rotationX, rotationY, 0), new Vector3(0, 0, 1), -rotation);
        }
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    /**
     * Cleans up all translations, scaling and rotation
     */
    private void undoTransformations() {

        if (rotation != 0f) {
            camera.rotateAround(new Vector3(rotationX, rotationY, 0), new Vector3(0, 0, 1), rotation);
        }
        camera.update();

        if (translationX != 0f || translationY != 0f) {
            camera.translate(-translationX, -translationY);
        }
        camera.update();
    }

    /**
     * Resets transformation values
     */
    private void resetTransformations() {
        this.translationX = 0;
        this.translationY = 0;
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.rotation = 0f;
        this.rotationX = 0f;
        this.rotationY = 0f;
    }

    /**
     * Returns the line height used
     *
     * @return A value greater than 0
     */
    public int getLineHeight() {
        return lineHeight;
    }

    /**
     * Sets the line height to be used
     *
     * @param lineHeight A value greater than 0
     */
    public void setLineHeight(int lineHeight) {
        if (lineHeight > 0) {
            this.lineHeight = lineHeight;
        }
    }

    /**
     * Returns the {@link Color} to draw shapes with
     *
     * @return A non-null value
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the {@link Color} to draw shapes with
     *
     * @param color
     */
    public void setColor(Color color) {
        if (color == null) {
            return;
        }
        this.color = color;
    }

    /**
     * Returns the background {@link Color}
     *
     * @return A non-null value
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background {@link Color} to be used
     */
    public void setBackgroundColor(Color backgroundColor) {
        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
        }
    }

    /**
     * Returns the {@link BitmapFont} to draw {@link String}s with
     *
     * @return 15pt Arial font by default unless setFont() is called
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * Sets the {@link BitmapFont} to draw {@link String}s with
     *
     * @param font A non-null instance of {@link BitmapFont}
     */
    public void setFont(BitmapFont font) {
        if (font != null) {
            if (rendering) {
                endRendering();
            }

            this.font = font;
        }
    }

    /**
     * Returns the {@link Color} tint being applied to all draw operations
     *
     * @return
     */
    public Color getTint() {
        return tint;
    }

    /**
     * Sets the {@link Color} to apply to draw operations
     *
     * @param tint The {@link Color} to tint with
     */
    public void setTint(Color tint) {
        if (rendering) {
            endRendering();
        }

        this.tint = tint;
        spriteBatch.setColor(tint);
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getTranslationX() {
        return translationX;
    }

    public float getTranslationY() {
        return translationY;
    }

    public float getRotation() {
        return rotation;
    }

    public float getRotationX() {
        return rotationX;
    }

    public float getRotationY() {
        return rotationY;
    }

    public Matrix4 getProjectionMatrix() {
        return camera.combined.cpy();
    }

    public float getCurrentWidth() {
        return currentWidth;
    }

    public float getCurrentHeight() {
        return currentHeight;
    }

    @Override
    public String toString() {
        return "Graphics [color=" + color + ", backgroundColor=" + backgroundColor + ", tint=" + tint
                + ", translationX=" + translationX + ", translationY=" + translationY + ", scaleX=" + scaleX
                + ", scaleY=" + scaleY + ", rotation=" + rotation + ", rotationX=" + rotationX + ", rotationY="
                + rotationY + ", currentWidth=" + currentWidth + ", currentHeight=" + currentHeight + ", lineHeight="
                + lineHeight + "]";
    }
}
