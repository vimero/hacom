package pe.com.hacom.oms.adapter.cloudhopper;


import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;

import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.type.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.hacom.oms.application.port.out.SmsSenderPort;
import pe.com.hacom.oms.infrastructure.properties.SmppProperties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmppSmsSenderAdapter implements SmsSenderPort {

    private final SmppProperties smppProperties;

    private final DefaultSmppClient smppClient = new DefaultSmppClient();
    private SmppSession session;

    @PostConstruct
    public void init() {
        try {
            SmppSessionConfiguration config = new SmppSessionConfiguration();
            config.setType(SmppBindType.TRANSCEIVER);
            config.setHost(smppProperties.getHost());
            config.setPort(smppProperties.getPort());
            config.setSystemId(smppProperties.getSystemId());
            config.setPassword(smppProperties.getPassword());


            this.session = smppClient.bind(config);
            log.info("✅ SMPP session established");
        } catch (Exception e) {
            log.error("Failed to establish SMPP session", e);
        }
    }

    @Override
    public void sendSms(String to, String message) {
        try {
            if (session == null || !session.isBound()) {
                log.warn("SMPP session not ready");
                return;
            }

            SubmitSm submit = new SubmitSm();
            submit.setSourceAddress(new Address((byte) 0x03, (byte) 0x00, "OMS"));
            submit.setDestAddress(new Address((byte) 0x01, (byte) 0x01, to));
            submit.setShortMessage(message.getBytes());

            SubmitSmResp resp = session.submit(submit, 3000);
            log.info("📨 SMS sent to {} with msgId: {}", to, resp.getMessageId());
        } catch (Exception e) {
            log.error("Failed to send SMS to {}", to, e);
        }
    }

    @PreDestroy
    public void shutdown() {
        if (session != null) {
            session.unbind(3000);
            session.destroy();
            log.info("SMPP session closed");
        }
        smppClient.destroy();
    }
}
