package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RegistryReportsRegistrationStatusTest {

  private RegistryReportsRegistrationStatus rrs;

  @Before
  public void setup(){
    rrs = new RegistryReportsRegistrationStatus(10, 3, "Success");
  }

  @Test
  public void getMessage() throws Exception {
    assertEquals(rrs.getMessage(), "Registration request successful. The number of messaging "
        + "nodes currently constituting the overlay is (3)");
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

}