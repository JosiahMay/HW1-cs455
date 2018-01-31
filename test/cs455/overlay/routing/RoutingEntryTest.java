package cs455.overlay.routing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoutingEntryTest {

  private RoutingEntry re;

  @Before
  public void setup() throws Exception {
    re = RoutingTestSetups.populateEntry();
  }

  @Test
  public void getNodeId() throws Exception {
    assertEquals(re.getNodeId(), RoutingTestSetups.idStart);
  }

  @Test
  public void getPortNumber() throws Exception {
    assertEquals(re.getPortNumber(), RoutingTestSetups.portStart);
  }

  @Test
  public void getIpAddress() throws Exception {
    assertEquals(re.getIpAddress(), RoutingTestSetups.ipStart+1);
  }

  @Test
  public void testNullConstructor() throws Exception {
    re = new RoutingEntry();
    assertEquals(re.getNodeId(), -1);
  }
}