package engine;

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngine {

    private boolean exit;
    private final LevelCreator levelCreator;
    private final Map<Point, TileType> tiles = new HashMap<>();
    public final Map<Point, TileType> portals = new HashMap<>();
    private int levelHorizontalDimension;
    private int levelVerticalDimension;
    private Point player;
    private Point portal;
    private final int level;

    public GameEngine(LevelCreator levelCreator) {
        exit = false;
        level = 1;
        this.levelCreator = levelCreator;
        this.levelCreator.createLevel(this, level);
    }

    public void run(GameFrame gameFrame) {
        for (Component component : gameFrame.getComponents()) {
            component.repaint();
        }
    }

    public void addTile(int x, int y, TileType tileType) {
        if (tileType.equals(TileType.PLAYER)) {
            setPlayer(x, y);
            tiles.put(new Point(x, y), TileType.PASSABLE);

        }
        else if (tileType.equals(TileType.PORTAL)) {
            tiles.put(new Point(x, y), TileType.PORTAL);
            portals.put(new Point(x, y), TileType.PORTAL);
            setPortal(x, y);
        }
        else {
            tiles.put(new Point(x, y), tileType);
        }
    }

    public void setLevelHorizontalDimension(int levelHorizontalDimension) {
        this.levelHorizontalDimension = levelHorizontalDimension;
    }

    public void setLevelVerticalDimension(int levelVerticalDimension) {
        this.levelVerticalDimension = levelVerticalDimension;
    }

    public int getLevelHorizontalDimension() {
        return levelHorizontalDimension;
    }

    public int getLevelVerticalDimension() {
        return levelVerticalDimension;
    }

    public TileType getTileFromCoordinates(int x, int y) {
        return tiles.get(new Point(x, y));
    }

    private void setPlayer(int x, int y) {
        player = new Point(x, y);
    }

    private void setPortal(int x, int y) {
        portal = new Point(x, y);
    }

    public int getPlayerXCoordinate() {
        return (int) player.getX();
    }

    public int getPlayerYCoordinate() {
        return (int) player.getY();
    }

    public void keyLeft() {
        movePlayerIfPassable(getPlayerXCoordinate() - 1,
                getPlayerYCoordinate());

    }

    public void keyRight() {
        movePlayerIfPassable(getPlayerXCoordinate() + 1,
                getPlayerYCoordinate());

    }

    public void keyUp() {
        movePlayerIfPassable(getPlayerXCoordinate(),
                getPlayerYCoordinate() - 1);

    }

    public void keyDown() {
        movePlayerIfPassable(getPlayerXCoordinate(),
                getPlayerYCoordinate() + 1);

    }

    public void movePlayerIfPassable(int xCoordinate, int yCoordinate) {
        TileType nextLocation = getTileFromCoordinates(xCoordinate,
                yCoordinate);
        if (nextLocation.equals(TileType.PASSABLE)) {
            setPlayer(xCoordinate, yCoordinate);
        }

        if (nextLocation.equals(TileType.PORTAL)) {

            Point nextPortal = closestPortal(xCoordinate, yCoordinate);
            int x = (int) nextPortal.getX();
            int y = (int) nextPortal.getY();

            setPlayer(x, y);
        }
    }

    public Point closestPortal(int xCoordinate, int yCoordinate) {
        Point point = null;
        Point closetPoint = null;
        Double distance = null;

        for (int i = 0; i < portals.size(); i++) {

            Set<Point> portal = portals.keySet();
            Object[] locations = portal.toArray();
            point = (Point) locations[i];
            System.out.println(point);

            if (xCoordinate == point.getX() && yCoordinate == point.getY()) {

            }
            else {

                double pointDistance = Point.distance(xCoordinate, yCoordinate,
                        point.getX(), point.getY());

                if (distance == null || pointDistance < distance) {
                    distance = pointDistance;
                    closetPoint = point;
                }

            }
        }

        return closetPoint;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public boolean isExit() {
        return exit;
    }

    public int getPortalXCoordinate() {
        // TODO Auto-generated method stub
        return (int) portal.getX();
    }

    public int getPortalYCoordinate() {
        // TODO Auto-generated method stub
        return (int) portal.getY();
    }
}
