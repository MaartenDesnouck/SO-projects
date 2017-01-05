/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import othello.OthelloController;
import othello.OthelloModel;
import static org.junit.Assert.*;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidBoardSizeException;

/**
 *
 * @author maartendesnouck
 */
public class OthelloControllerTest {
    
    public OthelloControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test (expected=BoardIndexOutOfBoundsException.class)
    public void testIsValidMoveException() throws Exception {
        OthelloModel model = new OthelloModel(4);
        OthelloController cont = new OthelloController(model);
        cont.isValidMove(-34, 0);
    }
    
    /**
     * Test of isValidMove method, of class OthelloController.
     */
    @Test
    public void testIsValidMove() throws Exception {
        OthelloModel model = new OthelloModel(4);
        OthelloController cont = new OthelloController(model);
        assertTrue(cont.isValidMove(1, 0));
        assertTrue(cont.isValidMove(3, 2));
        assertTrue(cont.isValidMove(2, 3));
        assertTrue(cont.isValidMove(0, 1));
        assertFalse(cont.isValidMove(0, 0));
        assertFalse(cont.isValidMove(0, 2));
        assertFalse(cont.isValidMove(0, 3));
        assertFalse(cont.isValidMove(1, 1));
        assertFalse(cont.isValidMove(1, 2));
        assertFalse(cont.isValidMove(1, 3));
        assertFalse(cont.isValidMove(2, 0));
        assertFalse(cont.isValidMove(2, 1));
        assertFalse(cont.isValidMove(2, 2));
        assertFalse(cont.isValidMove(3, 0));
        assertFalse(cont.isValidMove(3, 1));
        assertFalse(cont.isValidMove(3, 3));
    }

    /**
     * Test of doMove method, of class OthelloController.
     */
    @Test
    public void testDoMove() throws Exception {
        OthelloModel model = new OthelloModel(4);
        OthelloController cont = new OthelloController(model);
        cont.doMove(1, 0);
        int[][] bord = {{0,-1,0,0},{0,-1,-1,0},{0,-1,1,0},{0,0,0,0}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                assertEquals(bord[j][i],model.getState(i, j));
            }
        }
        assertEquals(1,model.getTurn());
        
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        cont.doMove(3, 2);
        bord = new int[][] {{0,0,0,0},{0,1,-1,0},{0,-1,-1,-1},{0,0,0,0}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                assertEquals(bord[j][i],model.getState(i, j));
            }
        }
        assertEquals(1,model.getTurn());
        
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        cont.doMove(2, 3);
        bord = new int[][] {{0,0,0,0},{0,1,-1,0},{0,-1,-1,0},{0,0,-1,0}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                assertEquals(bord[j][i],model.getState(i, j));
            }
        }
        assertEquals(1,model.getTurn());
        
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        cont.doMove(0,1);
        bord = new int[][] {{0,0,0,0},{-1,-1,-1,0},{0,-1,1,0},{0,0,0,0}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                assertEquals(bord[j][i],model.getState(i, j));
            }
        }
        assertEquals(1,model.getTurn());
    }

    /**
     * Test of isFinished method, of class OthelloController.
     */
    @Test
    public void testIsFinished() throws InvalidBoardSizeException, BoardIndexOutOfBoundsException {
        OthelloModel model = new OthelloModel(4);
        OthelloController cont = new OthelloController(model);
        
        //Not finished
        assertEquals(0,cont.isFinished());
        
        //Black win, board full
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                model.setState(i, j, -1);
            }
        }
        assertEquals(-1,cont.isFinished());
        
        //Black win, board not full
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        int[][] bord = {{0,0,0,0},{0,0,0,0},{-1,1,-1,-1},{0,0,0,0}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                model.setState(i, j, bord[j][i]);
            }
        }
        assertEquals(-1,cont.isFinished());
        
        //White win, board full
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                model.setState(i, j, 1);
            }
        }
        assertEquals(1,cont.isFinished());
        
        //White win, board not full
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        bord = new int[][] {{0,0,0,0},{0,0,0,0},{1,-1,1,1},{0,0,0,0}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                model.setState(i, j, bord[j][i]);
            }
        }
        assertEquals(1,cont.isFinished());
        
        //Tie, board full
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        bord = new int[][] {{1,1,-1,-1},{1,1,-1,-1},{1,1,-1,-1},{1,1,-1,-1}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                model.setState(i, j, bord[j][i]);
            }
        }
        assertEquals(2,cont.isFinished());
        
        //Tie, board not full
        model = new OthelloModel(4);
        cont = new OthelloController(model);
        bord = new int[][] {{1,0,0,-1},{1,0,0,-1},{1,0,0,-1},{1,0,0,-1}};
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                model.setState(i, j, bord[j][i]);
            }
        }
        assertEquals(2,cont.isFinished());
    }
    
}
