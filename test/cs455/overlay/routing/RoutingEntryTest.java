package cs455.overlay.routing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoutingEntryTest {

  private RoutingEntry re;
  private int idNum = 100;
  private int port = 8453;
  private String ipAddress = "192.01.01.04";

  @Before
  public void setup() throws Exception {
    re = new RoutingEntry(idNum, ipAddress, port);
  }

  @Test
  public void getNodeId() throws Exception {
    assertEquals(re.getNodeId(), idNum);
  }

  @Test
  public void getPortNumber() throws Exception {
    assertEquals(re.getPortNumber(), port);
  }

  @Test
  public void getIpAddress() throws Exception {
    assertEquals(re.getIpAddress(), ipAddress);
  }

  @Test
  public void testNullConstructor() throws Exception {
    re = new RoutingEntry();
    assertEquals(re.getNodeId(), -1);
  }
}