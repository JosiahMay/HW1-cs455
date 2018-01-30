package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventFactoryTest {

  @Test
  public void RegistryReportsRegistrationStatus() throws Exception {
    Event event = EventFactory.getEvent(new RegistryReportsRegistrationStatus().getBytes());
    assertEquals(event.getType(), Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS);
  }

  @Test
  public void OverlayNodeSendsRegistration() throws Exception {
    Event event = EventFactory.getEvent(new OverlayNodeSendsRegistration().getBytes());
    assertEquals(event.getType(), Protocol.OVERLAY_NODE_SENDS_REGISTRATION);
  }
}