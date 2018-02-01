package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OverlayNodeReportsTrafficSummaryTest implements TestVariables{

  private OverlayNodeReportsTrafficSummary onrts;


  @Before
  public void setUp() throws Exception {
    onrts = new OverlayNodeReportsTrafficSummary(idNum, new TrafficReport(packetInfo, sumOfPackets));
  }

  @Test
  public void getBytes() throws Exception {
    byte[] bytes = onrts.getBytes();
    OverlayNodeReportsTrafficSummary bytebuild = new OverlayNodeReportsTrafficSummary(bytes);
    assertEquals(bytebuild.getIdNum(), idNum);
  }

  @Test
  public void nullConstructor(){
    onrts = new OverlayNodeReportsTrafficSummary();
    assertEquals(onrts.getIdNum(), -1);
  }
  @Test
  public void getIdNum() throws Exception {
    assertEquals(onrts.getIdNum(), idNum);
  }

  @Test
  public void getReport() throws Exception {
    assertEquals(onrts.getReport().getPacketSent(), packetSent);
  }

}