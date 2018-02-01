package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegistryRequestsTrafficSummaryTest {

  private RegistryRequestsTrafficSummary rrts = new RegistryRequestsTrafficSummary();
  @Test
  public void getBytes() throws Exception {
    byte[] bytes = rrts.getBytes();
    RegistryRequestsTrafficSummary byteBuild = new RegistryRequestsTrafficSummary(bytes);
    assertEquals(byteBuild.getType(), Protocol.REGISTRY_REQUESTS_TRAFFIC_SUMMARY);
  }

}