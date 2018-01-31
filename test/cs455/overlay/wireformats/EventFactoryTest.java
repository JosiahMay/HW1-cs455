package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import org.junit.Test;

public class EventFactoryTest {

  @Test (expected = IllegalArgumentException.class)
  public void invaildEventType() throws Exception {
    ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
    DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
    dout.writeInt(-1);
    dout.flush();
    Event event = EventFactory.getEvent(baOutputStream.toByteArray());
  }

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

  @Test
  public void OverlayNodeSendsDeregistration()throws Exception {
    Event event = EventFactory.getEvent(new OverlayNodeSendsDeregistration().getBytes());
    assertEquals(event.getType(), Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION);
  }

  @Test
  public void RegistryReportsDeregistrationStatus() throws Exception {
    Event event = EventFactory.getEvent(new RegistryReportsDeregistrationStatus().getBytes());
    assertEquals(event.getType(), Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS);
  }

  @Test
  public void RegistrySendsNodeManifest() throws Exception {
    Event event = EventFactory.getEvent(new RegistrySendsNodeManifest().getBytes());
    assertEquals(event.getType(), Protocol.REGISTRY_SENDS_NODE_MANIFEST);
  }
}