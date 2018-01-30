package cs455.overlay.wireformats;

public class OverlayNodeSendsDeregistration extends Event implements Protocol{

  private final String ipAddress;
  private final int portNumber;
  private final int IdNumber;

  public OverlayNodeSendsDeregistration(){
    this("No address", -1, -1);
  }

  public OverlayNodeSendsDeregistration(String ipAddress, int portNumber, int IdNumber){
    super(OVERLAY_NODE_SENDS_DEREGISTRATION);
    this.ipAddress = ipAddress;
    this.portNumber = portNumber;
    this.IdNumber = IdNumber;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public int getPortNumber() {
    return portNumber;
  }

  public int getIdNumber() {
    return IdNumber;
  }

  @Override
  public String toString(){
    return "Event type: " + getType()
        + "\nID Number: " + getIdNumber()
        + "\nIP Address: " + getIpAddress()
        + "\nPort Number: " + getPortNumber();
  }
}
