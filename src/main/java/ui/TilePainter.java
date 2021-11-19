package ui;

import java.awt.Graphics;

import engine.GameEngine;
import tiles.TileType;
import values.TileColorMap;

public class TilePainter {

	void paintTiles(Graphics graphics, GameEngine game, int tileWidth, int tileHeight) {
		for (int x = 0; x < game.getLevelHorizontalDimension(); x++) {
			for (int y = 0; y < game.getLevelVerticalDimension(); y++) {
				TileType tileType = game.getTileFromCoordinates(x, y);
				paintTile(graphics, tileWidth, tileHeight, x, y, tileType);
			}
		}
	}

	void paintPlayer(Graphics graphics, int x, int y, int tileWidth, int tileHeight, TileType tileType) {
		paintTile(graphics, tileWidth, tileHeight, x, y, tileType);
	}

	void paintDoor(Graphics graphics, int x, int y, int tileWidth, int tileHeight, TileType door) {
		paintTile(graphics, tileWidth, tileHeight, x, y, door);

	}

	void paintPassableBridgeTile(Graphics graphics, int x, int y, int tileWidth, int tileHeight,
			TileType passableBridge) {
		paintTile(graphics, tileWidth, tileHeight, x, y, passableBridge);

	}

	void paintNotPassableBridgeTile(Graphics graphics, int x, int y, int tileWidth, int tileHeight,
			TileType notPassableBridge) {
		paintTile(graphics, tileWidth, tileHeight, x, y, notPassableBridge);

	}

	private void paintTile(Graphics graphics, int tileWidth, int tileHeight, int x, int y, TileType tileType) {
		handleTile(graphics, tileType);
		graphics.fillRect(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
	}

	private void handleTile(Graphics graphics, TileType tileType) {
		graphics.setColor(TileColorMap.get(tileType));
	}

}
