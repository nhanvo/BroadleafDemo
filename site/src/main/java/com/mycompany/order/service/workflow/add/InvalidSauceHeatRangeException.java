package com.mycompany.order.service.workflow.add;

public class InvalidSauceHeatRangeException extends RuntimeException {

    private static final long serialVersionUID = -8828778897245219116L;

    public InvalidSauceHeatRangeException() {
        super();
    }

    public InvalidSauceHeatRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSauceHeatRangeException(String message) {
        super(message);
    }

    public InvalidSauceHeatRangeException(Throwable cause) {
        super(cause);
    }

}
