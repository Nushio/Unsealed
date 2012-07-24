package net.k3rnel.unsealed.utils;

import net.k3rnel.unsealed.objects.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.utils.Array;

public class MapLoader extends SynchronousAssetLoader<Map, MapLoader.MapParameter> {
	
	public MapLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public Map load(AssetManager assetManager, String fileName, MapParameter parameter) {
		return new Map(resolve(fileName), resolve(parameter.atlasDir), parameter.viewportWidth, parameter.viewportHeight);
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, MapParameter parameter) {
		return null;
	}

	public static class MapParameter extends AssetLoaderParameters<Map> {
		public final String atlasDir;
		public final float viewportWidth;
		public final float viewportHeight;
		
		public MapParameter(String atlasDir, float viewportWidth, float viewportHeight) {
			this.atlasDir = atlasDir;
			this.viewportWidth = viewportWidth;
			this.viewportHeight = viewportHeight;
		}
	}
}
