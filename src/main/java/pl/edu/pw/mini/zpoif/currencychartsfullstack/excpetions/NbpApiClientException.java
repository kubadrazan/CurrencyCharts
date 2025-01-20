package pl.edu.pw.mini.zpoif.currencychartsfullstack.excpetions;

public class NbpApiClientException extends RuntimeException {

    public NbpApiClientException() {}

    public NbpApiClientException(String message) {
        super(message);
    }

    public NbpApiClientException(Throwable cause) {
        super(cause);
    }

    public NbpApiClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public NbpApiClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
