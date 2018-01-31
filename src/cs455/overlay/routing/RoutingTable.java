package cs455.overlay.routing;

public class RoutingTable {

  private RoutingEntry[] entries;

  public RoutingTable(){
    this(new RoutingEntry[0]);
  }

  public RoutingTable(RoutingEntry[] entries){
    this.entries = entries;
  }

  public RoutingEntry[] getEntries() {
    return entries;
  }
}
