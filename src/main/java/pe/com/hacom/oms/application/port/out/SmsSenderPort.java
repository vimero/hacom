package pe.com.hacom.oms.application.port.out;

public interface SmsSenderPort {

    void sendSms(String to, String message);

}
