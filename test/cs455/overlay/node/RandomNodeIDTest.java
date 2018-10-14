package cs455.overlay.node;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class RandomNodeIDTest {

  @BeforeClass
  public static void setUp(){
    RandomNodeID.seedArray(2);
  }


  @Test
  public void test() throws Exception {
    int i = RandomNodeID.getIdNumber();
    RandomNodeID.getIdNumber();
    RandomNodeID.returnIdNumber(i);
    assertEquals(i, RandomNodeID.getIdNumber());
  }


}