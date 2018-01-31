package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RegistryRequestsTaskInitiateTest {

  private RegistryRequestsTaskInitiate rrti;
  private final int packetsToSend = 150000;

  @Before
  public void setup() throws Exception {
    rrti = new RegistryRequestsTaskInitiate(packetsToSend);
  }

  @Test
  public void nullConstructorTest(){
    rrti = new RegistryRequestsTaskInitiate();
    assertEquals(rrti.getPacketsToSend(), -1);
  }
  @Test
  public void getBytes() throws Exception {
    byte[] bytes = rrti.getBytes();
    RegistryRequestsTaskInitiate byteBuild = new RegistryRequestsTaskInitiate(bytes);
    assertEquals(byteBuild.toString(), buildString());
  }

  @Test
  public void getPacketsToSend() throws Exception {
    assertEquals(rrti.getPacketsToSend(), packetsToSend);
  }

  private String buildString() {
    return "Packets to send: " + packetsToSend;
  }
}