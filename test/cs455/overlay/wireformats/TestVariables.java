package cs455.overlay.wireformats;

public interface TestVariables {

  int packetDestination = 100;
  int packetSource = 5;
  int packetPayload = 123513;
  int[] packetTraceRoute = {5,12,48};

  int packetSent = 1594;
  long sumOfPacketSent = 1232548496;
  int packetsRelayed = 15646546;
  int packetsReceived = 135499;
  long sumOfPacketsReceived = 12315469;

  int[] packetInfo = {packetSent, packetsRelayed, packetsReceived};
  long[] sumOfPackets = {sumOfPacketSent, sumOfPacketsReceived};
}
