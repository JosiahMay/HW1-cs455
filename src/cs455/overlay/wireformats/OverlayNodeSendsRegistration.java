package cs455.overlay.wireformats;

import java.io.IOException;

public class OverlayNodeSendsRegistration extends Event implements Protocol{

  private final String ipAddress;
  private final int port;

  public OverlayNodeSendsRegistration(){
    this("No address", -1);
  }

  public OverlayNodeSendsRegistration(String ipAddress, int port){
    super(OVERLAY_NODE_SENDS_REGISTRATION);
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public OverlayNodeSendsRegistration(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

    // Read Ip address
    int ipAddressLength = din.readInt();
    byte[] ipAddressInBytes = new byte[ipAddressLength];
    din.readFully(ipAddressInBytes);
    ipAddress = new String(ipAddressInBytes);

    // Read Port
    port = din.readInt();
    // Close Input streams
    teardownDataInputStream();
  }


  @Override
  public byte[] getBytes() throws IOException {
    byte[] marshalledBytes;
    setupDataOutputStream();

    dout.writeInt(messageType);
    // Write the IP address
    dout.writeInt(ipAddress.length()); // size of IP address
    byte[] ipInBytes = ipAddress.getBytes();
    dout.write(ipInBytes);
    // Write port
    dout.writeInt(port);
    // Save data to byte array
    dout.flush();
    marshalledBytes = baOutputStream.toByteArray();

    // Close connections
    teardownDataOutputStream();

    return marshalledBytes;
  }


  public String getIpAddress() {
    return ipAddress;
  }

  public int getPort() {
    return port;
  }

  @Override
  public String toString(){
    return "Event Type: " + getType()
        + "\nIP Address: " + getIpAddress()
        + "\nPort: " + getPort();
  }
}
