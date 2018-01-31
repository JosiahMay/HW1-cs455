package cs455.overlay.wireformats;

import java.io.IOException;

public class RegistryReportsDeregistrationStatus extends MessageReportStatus implements Protocol {


  public RegistryReportsDeregistrationStatus() {
    this(-1, "Constructed from null constructor");

  }

  public RegistryReportsDeregistrationStatus(int idNum, String message){
    super(REGISTRY_REPORTS_DEREGISTRATION_STATUS, idNum, message);

  }

  public RegistryReportsDeregistrationStatus(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

  }

}
