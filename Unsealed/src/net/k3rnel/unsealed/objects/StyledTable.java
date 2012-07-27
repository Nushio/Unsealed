package net.k3rnel.unsealed.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class StyledTable extends Table {
	
	protected TableStyle style;
	
	public StyledTable (TableStyle style) {
		this.style = style;
	}
	
	/*protected static TableStyle getDefaultTexture() {
		FileHandle handle = Gdx.files.internal("data/dialogue_box.png");
		if (! handle.exists())
			return new TableStyle();
		
		NinePatch patch = new NinePatch(new Texture(handle), 16, 16, 16, 16);
		TableStyle style = new TableStyle();
		style.background = patch;
		return style;
	}*/
	
	public TableStyle getStyle() {
		return style;
	}

	public void setStyle(TableStyle style) {
		this.style = style;
	}

	public static class TableStyle {
		public Drawable background;
		public BitmapFont font;
		public Color fontColor;
		public int padX;
		public int padY;
		
		public TableStyle() {
			this.background = null;
			this.font = new BitmapFont(); //default font
			this.fontColor = new Color(Color.WHITE);
			this.padX = 0;
			this.padY = 0;
		}
	}

}
