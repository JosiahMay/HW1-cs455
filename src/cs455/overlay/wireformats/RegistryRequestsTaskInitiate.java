package cs455.overlay.wireformats;

import java.io.IOException;

public class RegistryRequestsTaskInitiate extends Event implements Protocol{

  private final int packetsToSend;

  public RegistryRequestsTaskInitiate() {
    this(-1);
  }

  public RegistryRequestsTaskInitiate(int packetsToSend) {
    super(REGISTRY_REQUESTS_TASK_INITIATE);
    this.packetsToSend = packetsToSend;
  }

  public RegistryRequestsTaskInitiate(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

    this.packetsToSend = din.readInt();
    teardownDataInputStream();
  }

  @Override
  public byte[] getBytes() throws IOException{
    setupDataOutputStream();

    dout.writeInt(messageType);
    dout.writeInt(getPacketsToSend()); // add number of packets

    return getOutputByteArray();
  }

  public int getPacketsToSend() {
    return packetsToSend;
  }

  @Override
  public String toString(){
    return "Packets to send: " + getPacketsToSend();
  }
}
