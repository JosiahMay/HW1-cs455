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

  @Override
  public String toString(){
    StringBuilder rt = new StringBuilder();
    int entryNum = 1;
    for (RoutingEntry e:entries) {
      rt.append("\nEntry ").append(entryNum).append("\n").append(e).append("");
    }
    return rt.toString();
  }
}
