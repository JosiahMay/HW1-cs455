package cs455.overlay.routing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RoutingTableTest {

  private RoutingEntry[] entries;
  private RoutingTable rt;
  private int numOfEntries = 5;
  private int idStart = 100;
  private int portStart = 5000;
  private String ipStart = "192.01.01.";

  @Before
  public void setup() throws Exception {
    rt = new RoutingTable(populateEntries());
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
    assertEquals(entries[0].getNodeId(), idStart);
  }



  private RoutingEntry[] populateEntries() {
    int id = idStart;
    int port = portStart;
    String ip = ipStart;
    RoutingEntry[] rt = new RoutingEntry[numOfEntries];
    for (int i = 0; i < numOfEntries; i++) {
      rt[i] = new RoutingEntry(id++,ip +i, port++);
    }
    return rt;
  }
}