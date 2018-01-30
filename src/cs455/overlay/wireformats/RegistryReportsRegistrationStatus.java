package cs455.overlay.wireformats;

public class RegistryReportsRegistrationStatus extends Event {

  private final int id;
  private final String message;

  public RegistryReportsRegistrationStatus() {
    this(-1, 0, "Constructed from null constructor");

  }

  public RegistryReportsRegistrationStatus(int idNum, int overlayNum, String message){
    super(Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS);
    this.id = idNum;
    this.message = setupMessage(message, overlayNum);

  }

  private String setupMessage(String message, int overlayNum) {
    String rt;
    if(id < 0){
      rt = message;
    } else {
      rt = "Registration request successful. The number of messaging nodes currently constituting "
          + "the overlay is (" + overlayNum + ")";
    }
    return rt;
  }


  public String getMessage() {
    return message;
  }

  public int getId(){
    return id;
  }
}
