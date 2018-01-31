package cs455.overlay.wireformats;

import java.io.IOException;

public class RegistryReportsRegistrationStatus extends MessageReportStatus implements Protocol{


  public RegistryReportsRegistrationStatus() {
    this(-1, "Constructed from null constructor");

  }

  public RegistryReportsRegistrationStatus(int idNum, String message){
    super(REGISTRY_REPORTS_REGISTRATION_STATUS, idNum, message);

  }

  public RegistryReportsRegistrationStatus(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

  }

}
