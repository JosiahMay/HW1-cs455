package cs455.overlay.wireformats;

import java.io.IOException;

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

  public OverlayNodeSendsDeregistration(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

    //Get Attributes
    this.ipAddress = readByteString();
    this.portNumber = din.readInt();
    this.IdNumber = din.readInt();

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
    // Write port and IdNumber
    dout.writeInt(portNumber);
    dout.writeInt(IdNumber);

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
