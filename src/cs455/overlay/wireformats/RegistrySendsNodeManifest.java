package cs455.overlay.wireformats;

import cs455.overlay.routing.RoutingTable;

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

  public RoutingTable getRoutingTable() {
    return routingTable;
  }

  public int[] getTablesInNetwork() {
    return tablesInNetwork;
  }
}
