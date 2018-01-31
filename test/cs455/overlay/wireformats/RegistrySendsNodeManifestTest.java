package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.routing.RoutingTable;
import cs455.overlay.routing.RoutingTestSetups;
import org.junit.Before;
import org.junit.Test;

public class RegistrySendsNodeManifestTest {

  private RegistrySendsNodeManifest rsnm;

  @Before
  public void setup(){
    RoutingEntry[] entries = RoutingTestSetups.populateEntries();
    RoutingTable table = new RoutingTable(entries);
    rsnm = new RegistrySendsNodeManifest(table, getIds(entries));
  }

  @Test
  public void nullConstuctorTest() throws Exception {
    rsnm = new RegistrySendsNodeManifest();
    assertEquals(rsnm.getTablesInNetwork().length, 0);
  }

  @Test
  public void getRoutingTable() throws Exception {
    RoutingTable table =  rsnm.getRoutingTable();
    assertEquals(table.getEntries().length, RoutingTestSetups.numOfEntries);
  }

  @Test
  public void getTablesInNetwork() throws Exception {
    assertEquals(rsnm.getTablesInNetwork().length, RoutingTestSetups.numOfEntries);
  }

  public void getType() throws Exception {
    assertEquals(rsnm.getType(), Protocol.REGISTRY_SENDS_NODE_MANIFEST);
  }

  private int[] getIds(RoutingEntry[] entries){
    int[] rt = new int[entries.length];
    for (int i = 0; i < entries.length; i++) {
      rt[i] = entries[i].getNodeId();
    }
    return rt;
  }
}