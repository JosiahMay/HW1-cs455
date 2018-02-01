package cs455.overlay.wireformats;

import java.io.IOException;

public class RegistryRequestsTrafficSummary extends Event implements Protocol{

  public RegistryRequestsTrafficSummary() {
    super(REGISTRY_REQUESTS_TRAFFIC_SUMMARY);
  }

  public RegistryRequestsTrafficSummary(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);
    teardownDataInputStream();
  }

  @Override
  public byte[] getBytes() throws IOException{
    setupDataOutputStream();
    dout.writeInt(messageType);
    return getOutputByteArray();
  }
}
