package com.fuzzywave.commons.graphics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class Graphics {

    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;
    private Color backgroundColor;
    private ShaderProgram defaultShader;

    public Graphics(SpriteBatch spriteBatch,
                    ShapeRenderer shapeRenderer) {
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
        this.backgroundColor = Color.BLACK;
        this.defaultShader = SpriteBatch.createDefaultShader();
    }

    /**
     * Called before rendering begins
     */
    public void preRender() {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);
    }

    /**
     * Called after rendering.
     */
    public void postRender() {

    }

    public void setProjectionMatrix(Matrix4 projection) {
        spriteBatch.setProjectionMatrix(projection);
        shapeRenderer.setProjectionMatrix(projection);
    }

    public void setShader(ShaderProgram shader){
        this.spriteBatch.setShader(shader);
    }

    public void clearShader(){
        this.spriteBatch.setShader(defaultShader);
    }

}
