package com.fuzzywave.commons.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;
import com.badlogic.gdx.utils.ObjectMap;
import com.fuzzywave.commons.GameApplication;
import com.fuzzywave.commons.game.GameContainer;

public class ResourceManager implements Disposable, AssetErrorListener{

    private AssetManager manager;
    private ObjectMap<String, Array<Asset>> groups;

    public ResourceManager(String assetFile) {

        manager = new AssetManager();
        manager.setErrorListener(this);
        manager.setLoader(BitmapFont.class, new BitmapFontLoader(new InternalFileHandleResolver()));
        manager.setLoader(ShaderProgram.class, new ShaderAssetLoader(new InternalFileHandleResolver()));

        loadGroups(assetFile);
    }

    public AssetLoader<?, ?> getLoader(Class<?> type) {
        return manager.getLoader(type);
    }

    public void loadGroup(String groupName) {
        GameApplication.logger.info("[ResourceManager] loading group, " + groupName);

        Array<Asset> assets = groups.get(groupName, null);

        if (assets != null) {
            for (Asset asset : assets) {
                GameApplication.logger.info("[ResourceManager] loading, " + asset.path);
                manager.load(asset.path, asset.type, asset.parameters);
            }
        } else {
            GameApplication.logger.error("[ResourceManager] failed to load group, " + groupName);
        }
    }

    public void unloadGroup(String groupName) {
        GameApplication.logger.info("[ResourceManager] unloading group, " + groupName);

        Array<Asset> assets = groups.get(groupName, null);

        if (assets != null) {
            for (Asset asset : assets) {
                if (manager.isLoaded(asset.path, asset.type)) {
                    manager.unload(asset.path);
                }
            }
        } else {
            GameApplication.logger.error("[ResourceManager] failed to unload group, " + groupName);
        }
    }

    public synchronized <T> T get(String fileName) {
        return manager.get(fileName);
    }

    public synchronized <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }

    public <T> boolean isLoaded(String fileName, Class<T> type) {
        return manager.isLoaded(fileName, type);
    }

    public boolean update() {
        return manager.update();
    }

    public void finishLoading() {
        manager.finishLoading();
    }

    public float getProgress() {
        return manager.getProgress();
    }

    @Override
    public void dispose() {
        GameApplication.logger.info("[ResourceManager] disposing...");
        manager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        GameApplication.logger.error("[ResourceManager] Error on loading, " + asset.fileName + " message: " + throwable.getMessage());
    }

    private void loadGroups(String assetFile) {
        groups = new ObjectMap<String, Array<Asset>>();

        GameApplication.logger.info("[ResourceManager] loading file, " + assetFile);

        try {
            Json json = new Json();
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(Gdx.files.internal(assetFile));

            JsonIterator groupIt = root.iterator();

            while (groupIt.hasNext()) {
                JsonValue groupValue = groupIt.next();

                if (groups.containsKey(groupValue.name)) {
                    GameApplication.logger.info("[ResourceManager] Group, " + groupValue.name + " already exists, skipping");
                    continue;
                }

                GameApplication.logger.info("[ResourceManager] registering group " + groupValue.name);

                Array<Asset> assets = new Array<Asset>();

                JsonIterator assetIt = groupValue.iterator();

                while (assetIt.hasNext()) {
                    JsonValue assetValue = assetIt.next();

                    Asset asset = json.fromJson(Asset.class, assetValue.toString());
                    assets.add(asset);
                }

                groups.put(groupValue.name, assets);
            }
        } catch (Exception e) {
            GameApplication.logger.error("[ResourceManager] Error on loading file, " + assetFile + " " + e.getMessage());
        }
    }


}
