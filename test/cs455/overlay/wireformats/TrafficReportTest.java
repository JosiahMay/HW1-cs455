package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TrafficReportTest implements TestVariables{

  private TrafficReport tr;

  @Before
  public void setUp() throws Exception {
    tr = new TrafficReport(packetInfo, sumOfPackets);
  }

  @Test
  public void getPacketSent() throws Exception {
    assertEquals(tr.getPacketSent(), packetSent);
  }

  @Test
  public void getSumOfPacketSent() throws Exception {
    assertEquals(tr.getSumOfPacketSent(), sumOfPacketSent);
  }

  @Test
  public void getPacketsRelayed() throws Exception {
    assertEquals(tr.getPacketsRelayed(), packetsRelayed);
  }

  @Test
  public void getPacketsReceived() throws Exception {
    assertEquals(tr.getPacketsReceived(), packetsReceived);
  }

  @Test
  public void getSumOfPacketsReceived() throws Exception {
    assertEquals(tr.getSumOfPacketsReceived(), sumOfPacketsReceived);
  }

  @Test
  public void nullConstructor(){
    tr = new TrafficReport();
    assertEquals(tr.getPacketSent(), 0);
  }
}