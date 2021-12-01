package engine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.awt.Component;
import java.awt.Point;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import parser.LevelCreator;
import tiles.TileType;
import ui.GameFrame;

public class GameEngineTest {

    private static final int ZERO = 0;
    private static final int ONE = 1;

    GameEngine gameEngine;

    @Before
    public void setUp() throws Exception {
        LevelCreator levelCreator = Mockito.mock(LevelCreator.class);
        gameEngine = new GameEngine(levelCreator);
        int level = 1;
        Mockito.verify(levelCreator, Mockito.times(level))
                .createLevel(gameEngine, level);
    }

    @Test
    public void run() {
        GameFrame gameFrame = Mockito.mock(GameFrame.class);
        Component component = Mockito.mock(Component.class);
        Mockito.when(gameFrame.getComponents())
                .thenReturn(new Component[] { component });
        gameEngine.run(gameFrame);
        Mockito.verify(component, Mockito.times(1)).repaint();
    }

    @Test
    public void add_and_get_tile() {
        TileType tileType = TileType.PASSABLE;
        gameEngine.addTile(ZERO, ONE, TileType.PASSABLE);
        TileType actual = gameEngine.getTileFromCoordinates(ZERO, ONE);
        assertThat(actual, equalTo(tileType));
    }

    @Test
    public void set_and_get_horizontal_dimension() {
        gameEngine.setLevelHorizontalDimension(ONE);
        int actual = gameEngine.getLevelHorizontalDimension();
        assertThat(actual, equalTo(ONE));
    }

    @Test
    public void set_and_get_vertical_dimension() {
        gameEngine.setLevelVerticalDimension(ONE);
        int actual = gameEngine.getLevelVerticalDimension();
        assertThat(actual, equalTo(ONE));
    }

    @Test
    public void add_and_get_player_coordinates() {
        TileType tileType = TileType.PLAYER;
        gameEngine.addTile(ZERO, ONE, tileType);
        int actualX = gameEngine.getPlayerXCoordinate();
        int actualY = gameEngine.getPlayerYCoordinate();
        assertThat(actualX, equalTo(ZERO));
        assertThat(actualY, equalTo(ONE));
    }

    @Test
    public void add_and_get_portal_coordinates() {
        TileType tileType = TileType.PORTAL;
        gameEngine.addTile(ZERO, ONE, tileType);
        int actualX = gameEngine.getPortalXCoordinate();
        int actualY = gameEngine.getPortalYCoordinate();
        assertThat(actualX, equalTo(ZERO));
        assertThat(actualY, equalTo(ONE));
    }

    // Better naming convention needed
    @Test
    public void get_closets_coordinates_after_portal_one() {
        Point pointOne = new Point(0, 1);
        Point pointTwo = new Point(0, 2);
        Point pointThree = new Point(0, 3);

        gameEngine.portals.put(pointOne, TileType.PORTAL);
        gameEngine.portals.put(pointTwo, TileType.PORTAL);
        gameEngine.portals.put(pointThree, TileType.PORTAL);

        Point closetPoint = gameEngine.closestPortal(0, 3);
        System.out.println(closetPoint);

        assertEquals(closetPoint, pointTwo);
    }

    // Better naming convention needed
    @Test
    public void get_coordinates_after_portal_two() {
        Point pointOne = new Point(1, 0);
        Point pointTwo = new Point(2, 0);
        Point pointThree = new Point(3, 0);

        gameEngine.portals.put(pointOne, TileType.PORTAL);
        gameEngine.portals.put(pointTwo, TileType.PORTAL);
        gameEngine.portals.put(pointThree, TileType.PORTAL);

        Point closetPoint = gameEngine.closestPortal(3, 0);
        System.out.println(closetPoint);

        assertEquals(closetPoint, pointTwo);
    }

    // Better naming convention needed
    @Test
    public void get_coordinates_after_portal_three() {
        Point pointOne = new Point(1, 1);
        Point pointTwo = new Point(2, 2);
        Point pointThree = new Point(3, 3);

        gameEngine.portals.put(pointOne, TileType.PORTAL);
        gameEngine.portals.put(pointTwo, TileType.PORTAL);
        gameEngine.portals.put(pointThree, TileType.PORTAL);

        Point closetPoint = gameEngine.closestPortal(3, 0);
        // System.out.println(closetPoint);

        assertEquals(closetPoint, pointOne);
    }

    // Better naming convention needed
    @Test
    public void get_coordinates_after_portal_four() {
        Point pointOne = new Point(1, 1);
        Point pointTwo = new Point(2, 2);
        Point pointThree = new Point(3, 3);

        gameEngine.portals.put(pointOne, TileType.PORTAL);
        gameEngine.portals.put(pointTwo, TileType.PORTAL);
        gameEngine.portals.put(pointThree, TileType.PORTAL);

        Point closetPoint = gameEngine.closestPortal(0, 3);
        System.out.println(closetPoint);

        assertEquals(closetPoint, pointOne);
    }

    // Better naming convention needed
    @Test
    public void get_coordinates_after_portal_five() {
        Point pointOne = new Point(4, 1);
        Point pointTwo = new Point(4, 2);
        Point pointThree = new Point(4, 3);

        gameEngine.portals.put(pointOne, TileType.PORTAL);
        gameEngine.portals.put(pointTwo, TileType.PORTAL);
        gameEngine.portals.put(pointThree, TileType.PORTAL);

        Point closetPoint = gameEngine.closestPortal(0, 3);
        System.out.println(closetPoint);

        assertEquals(closetPoint, pointThree);
    }

    // Better naming convention needed
    @Test
    public void get_coordinates_after_portal_six() {
        Point pointOne = new Point(1, 4);
        Point pointTwo = new Point(2, 4);
        Point pointThree = new Point(3, 4);

        gameEngine.portals.put(pointOne, TileType.PORTAL);
        gameEngine.portals.put(pointTwo, TileType.PORTAL);
        gameEngine.portals.put(pointThree, TileType.PORTAL);

        Point closetPoint = gameEngine.closestPortal(0, 3);
        System.out.println(closetPoint);

        assertEquals(closetPoint, pointOne);
    }

    @Test
    public void set_and_get_exit() {
        boolean exit = true;
        gameEngine.setExit(exit);
        boolean actual = gameEngine.isExit();
        assertThat(actual, equalTo(exit));
    }

}
