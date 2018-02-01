package cs455.overlay.wireformats;

import java.io.IOException;

public class OverlayNodeReportsTaskFinished extends Event implements Protocol{

  private final int idNum;
  private final int portNum;
  private final String ipAddress;

  public OverlayNodeReportsTaskFinished() {
    this("0.0.0.0", -1, -1);
  }

  public OverlayNodeReportsTaskFinished(String ipAddress, int portNum, int idNum) {
    super(OVERLAY_NODE_REPORTS_TASK_FINISHED);
    this.ipAddress = ipAddress;
    this.idNum = idNum;
    this.portNum = portNum;
  }

  public OverlayNodeReportsTaskFinished(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

    this.ipAddress = readByteString();
    this.portNum = din.readInt();
    this.idNum = din.readInt();

    teardownDataInputStream();
  }

  @Override
  public byte[] getBytes() throws IOException{
    setupDataOutputStream();

    dout.writeInt(messageType);
    writeByteString(ipAddress);
    dout.writeInt(portNum);
    dout.writeInt(idNum);


    return getOutputByteArray();
  }

  public int getIdNum() {
    return idNum;
  }

  public int getPortNum() {
    return portNum;
  }

  public String getIpAddress() {
    return ipAddress;
  }
}
