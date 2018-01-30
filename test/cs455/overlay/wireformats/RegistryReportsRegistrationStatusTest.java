package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class RegistryReportsRegistrationStatusTest {

  private RegistryReportsRegistrationStatus rrs;
  private int idNum = 10;
  private int overlayNum = 3;

  @Before
  public void setup(){
    rrs = new RegistryReportsRegistrationStatus(idNum,  overlayString());
  }

  @Test
  public void getMessage() throws Exception {
    assertEquals(rrs.getMessage(), overlayString());
  }

  @Test
  public void getId() throws Exception {
    assertEquals(rrs.getId(), 10);
  }

  @Test
  public void testNullId(){
    rrs =  new RegistryReportsRegistrationStatus();
    assertEquals(rrs.getId(), -1);
  }

  @Test
  public void testNullMessage(){
    rrs =  new RegistryReportsRegistrationStatus();
    assertEquals(rrs.getMessage(), "Constructed from null constructor");
  }

  @Test
  public void testBytes() throws IOException {
    byte[] bytes = rrs.getBytes();
    RegistryReportsRegistrationStatus byteBuild = new RegistryReportsRegistrationStatus(bytes);
    assertEquals(byteBuild.getMessage(), overlayString());
  }

  @Test
  public void toStringTest() {
    assertEquals(rrs.toString(),
        "Event type: 3\nIdNum: " + idNum + "\nMessage: " + overlayString());
  }

  private String overlayString(){
    return "Registration request successful. The number of messaging "
        + "nodes currently constituting the overlay is (" + overlayNum +")";
  }

}