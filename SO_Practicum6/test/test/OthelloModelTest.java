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
import othello.OthelloModel;
import static org.junit.Assert.*;
import othello.exception.*;

/**
 *
 * @author maartendesnouck
 */
public class OthelloModelTest {
    
    public OthelloModelTest() {
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

    @Test
    public void testOthelloModelNotNull() throws InvalidBoardSizeException{
        OthelloModel model = new OthelloModel(6);
        assertNotNull(model);
    }
    
    @Test (expected=InvalidBoardSizeException.class)
    public void testOthelloModelNegative() throws InvalidBoardSizeException {
        OthelloModel model = new OthelloModel(-1);
    }
    
    @Test (expected=InvalidBoardSizeException.class)
    public void testOthelloModelZero() throws InvalidBoardSizeException {
        OthelloModel model = new OthelloModel(0);
    }
    
    @Test (expected=InvalidBoardSizeException.class)
    public void testOthelloModelOdd() throws InvalidBoardSizeException {
        OthelloModel model = new OthelloModel(3);
    }
    
    /**
     * Test of getSize method, of class OthelloModel.
     */
    @Test
    public void testGetSize() throws InvalidBoardSizeException {
        OthelloModel model = new OthelloModel(6);
        assertEquals(6,model.getSize());
    }
    
    /**
     * Test of getState method, of class OthelloModel.
     */
    @Test
    public void testGetState() throws InvalidBoardSizeException, BoardIndexOutOfBoundsException {
        OthelloModel model = new OthelloModel(6);
        model.setState(3,5,-1);
        assertEquals(-1,model.getState(3, 5));
        model.setState(1,5,0);
        assertEquals(0,model.getState(1, 5));
        model.setState(3,2,1);
        assertEquals(1,model.getState(3, 2));
    }

    /**
     * Test of setState method, of class OthelloModel.
     */
    @Test (expected=BoardIndexOutOfBoundsException.class)
    public void testSetStateException() throws BoardIndexOutOfBoundsException, InvalidBoardSizeException {
        OthelloModel model = new OthelloModel(6);
        model.setState(-234, 6, 0);
    }

    /**
     * Test of inBounds method, of class OthelloModel.
     */
    @Test
    public void testInBounds() throws InvalidBoardSizeException {
        OthelloModel model = new OthelloModel(6);
        int[] waarden = {-245,-1,0,3,5,6,245};
        for(int i : waarden){
            for(int j : waarden){
                if((i==0 || i==3 || i==5)&&(j==0||j==3||j==5)){
                    assertTrue(model.inBounds(i,j));
                }else{
                    assertFalse(model.inBounds(i,j));
                }
            }
        }
    }

    /**
     * Test of getTurn method, of class OthelloModel.
     */
    @Test
    public void testGetTurn() throws InvalidBoardSizeException {
        OthelloModel model = new OthelloModel(6);
        model.setTurn(-1);
        assertEquals(-1,model.getTurn());
        model.setTurn(1);
        assertEquals(1,model.getTurn());
    }

    /**
     * Test of equals method, of class OthelloModel.
     */
    @Test
    public void testEquals() throws InvalidBoardSizeException, BoardIndexOutOfBoundsException {
        OthelloModel model1 = new OthelloModel(6);
        model1.setState(3,5,-1);
        model1.setState(2,5,1);
        model1.setTurn(-1);
        assertFalse(model1.equals(null));
        assertFalse(model1.equals(new Integer(6)));
        OthelloModel model2 = new OthelloModel(4);
        assertFalse(model1.equals(model2));
        model2 = new OthelloModel(6);
        model2.setState(2,5,1);
        model2.setTurn(-1);
        assertFalse(model1.equals(model2));
        model2 = new OthelloModel(6);
        model2.setState(3,5,-1);
        model2.setState(2,5,1);
        model2.setTurn(1);
        assertFalse(model1.equals(model2));
        model2 = new OthelloModel(6);
        model2.setState(3,5,-1);
        model2.setState(2,5,1);
        model2.setTurn(-1);
        assertTrue(model1.equals(model2));
    }
    
}
