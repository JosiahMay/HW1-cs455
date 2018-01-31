package cs455.overlay.routing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoutingTableTest {

  private RoutingEntry[] entries;
  private RoutingTable rt;

  @Before
  public void setup() throws Exception {
    rt = new RoutingTable(RoutingTestSetups.populateEntries());
  }

  @Test
  public void nullConstructorTest() throws Exception {
    rt = new RoutingTable();
    entries = rt.getEntries();
    assertEquals(entries.length, 0);
  }

  @Test
  public void getEntries() throws Exception {
    entries = rt.getEntries();
    assertEquals(entries[0].getNodeId(), RoutingTestSetups.idStart);
  }

}