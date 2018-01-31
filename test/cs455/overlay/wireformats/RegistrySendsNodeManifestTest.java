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
  public void nullConstructorTest() throws Exception {
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
  @Test
  public void getBytes() throws Exception {
    RoutingEntry[] entry = new RoutingEntry[1];
    entry[0] = RoutingTestSetups.populateEntry();
    rsnm = new RegistrySendsNodeManifest(new RoutingTable(entry), getIds(entry));
    byte[] bytes = rsnm.getBytes();
    RegistrySendsNodeManifest byteBuild = new RegistrySendsNodeManifest(bytes);
    assertEquals(byteBuild.toString(), toStringTest());

  }


  private String toStringTest() throws Exception {
    return "Routing Table:\nEntry 1\n" + RoutingTestSetups.entryString() + "\n\nNodes in network: ["
        + RoutingTestSetups.idStart +"]";
  }

  private int[] getIds(RoutingEntry[] entries){
    int[] rt = new int[entries.length];
    for (int i = 0; i < entries.length; i++) {
      rt[i] = entries[i].getNodeId();
    }
    return rt;
  }
}