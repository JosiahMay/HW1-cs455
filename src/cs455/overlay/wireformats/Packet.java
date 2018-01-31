package cs455.overlay.wireformats;

import java.util.Arrays;

public class Packet {

  private final int destination;
  private final int source;
  private final int payload;
  private final int[] traceRoute;

  public Packet (int destination, int source, int payload, int[] traceRoute){
    this.destination = destination;
    this.source = source;
    this.payload = payload;
    this.traceRoute = traceRoute;
  }

  public int getDestination() {
    return destination;
  }

  public int getSource() {
    return source;
  }

  public int getPayload() {
    return payload;
  }

  public int[] getTraceRoute() {
    return traceRoute;
  }

  @Override
  public String toString() {
    return "Destination: " + destination
        + "\nSource: " + source
        + "\nPayload: " + payload
        + "\nTraceRoute: " + Arrays.toString(traceRoute);
  }
}
