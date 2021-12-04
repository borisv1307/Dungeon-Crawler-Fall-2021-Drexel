package ui;

import java.awt.Color;
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

	void paintPlayer(Graphics graphics, int x, int y, GameEngine game, int tileWidth, int tileHeight) {
		paintPlayerTile(graphics, x, y, tileWidth, tileHeight, game);
	}

	private void paintPlayerTile(Graphics graphics, int x, int y, int tileWidth, int tileHeight, GameEngine game) {
		paintTile(graphics, tileWidth, tileHeight, x, y, TileType.PLAYER);

		int maxPlayerHealth = game.getMaxPlayerHealth();
		int currentPlayerHealth = game.getPlayerHealth();
		int currentMissingPlayerHealth = maxPlayerHealth - currentPlayerHealth;

		int tileSegmentWidth = tileWidth / maxPlayerHealth;

		for (int currentHealthSegment = 1; currentHealthSegment <= currentMissingPlayerHealth; currentHealthSegment++) {
			graphics.setColor(Color.RED);
			int currentTileSegentWidth = tileSegmentWidth * currentHealthSegment;
			graphics.fillRect(x * tileWidth, y * tileHeight, currentTileSegentWidth, tileHeight);
		}
	}

	private void paintTile(Graphics graphics, int tileWidth, int tileHeight, int x, int y, TileType tileType) {
		handleTile(graphics, tileType);
		graphics.fillRect(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
	}

	private void handleTile(Graphics graphics, TileType tileType) {
		graphics.setColor(TileColorMap.get(tileType));
	}

}
