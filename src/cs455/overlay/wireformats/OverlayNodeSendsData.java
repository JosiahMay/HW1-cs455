package cs455.overlay.wireformats;

import java.io.IOException;

public class OverlayNodeSendsData extends Event implements Protocol{

  private final Packet packet;

  public OverlayNodeSendsData() {
    this(new Packet(-1,-1,-1, new int[0]));
  }

  public OverlayNodeSendsData(Packet packet) {
    super(OVERLAY_NODE_SENDS_DATA);
    this.packet = packet;
  }

  public OverlayNodeSendsData(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

    this.packet = readPacket();

    teardownDataInputStream();
  }

  @Override
  public byte[] getBytes() throws IOException {
    setupDataOutputStream();

    //MessageType
    dout.writeInt(messageType);

    writePacket();

    return getOutputByteArray();

  }

  public Packet getPacket(){
    return packet;
  }

  private void writePacket() throws IOException {
    // Packet info
    dout.writeInt(packet.getDestination());
    dout.writeInt(packet.getSource());
    dout.writeInt(packet.getPayload());

    // Trace Route
    int[] route = packet.getTraceRoute();
    dout.writeInt(route.length);
    for (int aRoute : route) {
      dout.writeInt(aRoute);
    }

  }

  private Packet readPacket() throws IOException {
    int destination = din.readInt();
    int source = din.readInt();
    int payload = din.readInt();
    int[] traceRoute = readTraceRoute();

    return new Packet(destination, source, payload, traceRoute);
  }

  private int[] readTraceRoute() throws IOException {
    int routeSize = din.readInt();
    int[] route = new int[routeSize];
    for (int i = 0; i < routeSize; i++) {
      route[i] = din.readInt();
    }
    return route;
  }

  @Override
  public String toString(){
    return "Event Type: " + messageType
        + "\nPacket:\n" + packet.toString();
  }

}
