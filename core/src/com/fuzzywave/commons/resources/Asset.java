package com.fuzzywave.commons.resources;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Asset implements Json.Serializable {
    public Class<?> type;
    public String path;
    public AssetLoaderParameters parameters;

    @Override
    public void write(Json json) {
        json.writeValue("assetType", type.getName());
        json.writeValue("path", path);
        json.writeValue("parameters", parameters);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        try {
            type = Class.forName(jsonData.get("type").asString());
        } catch (Exception e) {
            type = null;
        }

        path = jsonData.get("path").asString();

        if (type != null && type == com.badlogic.gdx.graphics.g2d.BitmapFont.class) {
            parameters = new com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter();
            ((BitmapFontLoader.BitmapFontParameter) parameters).minFilter = Texture.TextureFilter.Linear;
            ((BitmapFontLoader.BitmapFontParameter) parameters).magFilter = Texture.TextureFilter.Linear;
        }
    }
}
