/**
 * Unsealed: Whispers of Wisdom. 
 * 
 * Copyright (C) 2012 - Juan 'Nushio' Rodriguez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 of 
 * the License as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package net.k3rnel.unsealed.story.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Written by davexunit
 *
 */
public class StyledTable extends Table {
	
	protected TableStyle style;
	
	public StyledTable (TableStyle style) {
		this.style = style;
	}
	
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
