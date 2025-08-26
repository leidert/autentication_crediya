package co.com.tanos.r2dbc;

import co.com.tanos.model.user.User;
import co.com.tanos.model.user.gateways.LoggerGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggerGatewayAdapter implements LoggerGateway {


    @Override
    public void info(String message, User user) {

    }

    @Override
    public void info(String message, String value) {
        log.info("{} - {}", message, value);
    }

    @Override
    public void error(String message, Throwable e) {
        log.error(message, e.getMessage());
    }
}
