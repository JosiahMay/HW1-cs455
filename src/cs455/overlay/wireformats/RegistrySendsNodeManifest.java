package cs455.overlay.wireformats;

import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.routing.RoutingTable;
import java.io.IOException;
import java.util.Arrays;

public class RegistrySendsNodeManifest extends Event implements Protocol {

  private final RoutingTable routingTable;
  private final int[] tablesInNetwork;

  public RegistrySendsNodeManifest(){
    this(new RoutingTable(), new int[0] );
  }

  public RegistrySendsNodeManifest(RoutingTable table, int[] tablesInNetwork){
    super(REGISTRY_SENDS_NODE_MANIFEST);
    this.routingTable = table;
    this.tablesInNetwork = tablesInNetwork;
  }

  public RegistrySendsNodeManifest(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

    //Setup RoutingTable
    routingTable = makeRoutingTable();
    // Find all nodes in network
    tablesInNetwork = findNodesInNetwork();

    teardownDataInputStream();
  }

  private int[] findNodesInNetwork() throws IOException {
    int numberOfIds = din.readInt();
    int[] rt = new int[numberOfIds];
    for (int i = 0; i < numberOfIds; i++) {
      rt[i] = din.readInt();
    }
    return rt;
  }

  private RoutingTable makeRoutingTable() throws IOException {
    int numOfEntries = din.readInt();
    RoutingEntry[] entries = new RoutingEntry[numOfEntries];
    for (int i = 0; i < numOfEntries; i++) {
      entries[i] = readEntry();
    }
    return new RoutingTable(entries);
  }

  private RoutingEntry readEntry() throws IOException {
    int id = din.readInt();
    String ipAddress = readByteString();
    int port = din.readInt();
    return new RoutingEntry(id, ipAddress, port);

  }

  @Override
  public byte[] getBytes() throws IOException {
    byte[] marshalledBytes;
    setupDataOutputStream();

    dout.writeInt(messageType);
    // Write the Entries in the Routing table
    writeEntries();

    // Write the Id's in the network
    dout.writeInt(tablesInNetwork.length);
    for (int i: tablesInNetwork) {
      dout.writeInt(i);
    }
    // Save data to byte array
    dout.flush();
    marshalledBytes = baOutputStream.toByteArray();

    // Close connections
    teardownDataOutputStream();

    return marshalledBytes;
  }

  private void writeEntries() throws IOException {
    RoutingEntry[] entries = routingTable.getEntries();
    dout.writeInt(entries.length); // Size of routing table
    for (RoutingEntry e:entries) {
      dout.writeInt(e.getNodeId()); //add nodeId
      writeByteString(e.getIpAddress()); // add ipAddress
      dout.writeInt(e.getPortNumber()); // add port
    }

  }

  public RoutingTable getRoutingTable() {
    return routingTable;
  }

  public int[] getTablesInNetwork() {
    return tablesInNetwork;
  }

  @Override
  public String toString(){
    return "Routing Table:" + routingTable + "\n\nNodes in network: "
        + Arrays.toString(tablesInNetwork);
  }
}
