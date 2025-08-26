package co.com.tanos.model.user.gateways;

import co.com.tanos.model.user.User;

public interface LoggerGateway {

    void info(String message, User user);
    void info(String message, String value);
    void error(String message, Throwable e);
}
