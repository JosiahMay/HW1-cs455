package cs455.overlay.routing;

public class RoutingEntry {

  private final int nodeId;
  private final int portNumber;
  private final String ipAddress;

  public RoutingEntry(){
    this(-1, "0.0.0.0", -1);
  }

  public RoutingEntry(int nodeId, String ipAddress, int portNumber){
    this.nodeId = nodeId;
    this.ipAddress = ipAddress;
    this.portNumber = portNumber;
  }

  public int getNodeId() {
    return nodeId;
  }

  public int getPortNumber() {
    return portNumber;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  @Override
  public String toString() {
    return "Node Id: " + nodeId
        + "\nIP Address: " + ipAddress
        + "\nPort Number: " + portNumber;
  }
}
