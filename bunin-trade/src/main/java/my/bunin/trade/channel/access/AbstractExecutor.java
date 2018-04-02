package my.bunin.trade.channel.access;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.channel.access.bean.*;

@Slf4j
public abstract class AbstractExecutor implements Executor {

    protected String format(Request request) {

        switch (request.getType()) {
            case TRANSACTION:
                return format((TransactionRequest) request);
            case TRANSACTION_QUERY:
                return format((TransactionQueryRequest) request);
            case IDENTITY:
                return format((IdentityRequest) request);
            case IDENTITY_QUERY:
                return format((IdentityQueryRequest) request);
            case RECONCILIATIONS:
                return format((ReconciliationsRequest) request);
            default:
                throw new UnsupportedOperationException("Unsupported request type was used.");
        }
    }

    protected String format(TransactionRequest request) {
        throw new UnsupportedOperationException("transaction request was unsupported.");
    }

    protected String format(TransactionQueryRequest request) {
        throw new UnsupportedOperationException("transaction query request was unsupported.");
    }

    protected String format(IdentityRequest request) {
        throw new UnsupportedOperationException("identity request was unsupported.");
    }

    protected String format(IdentityQueryRequest request) {
        throw new UnsupportedOperationException("identity query request was unsupported.");
    }

    protected String format(ReconciliationsRequest request) {
        throw new UnsupportedOperationException("reconciliations request was unsupported.");
    }

    protected Response parse(Request request, String responseContent) {

        switch (request.getType()) {
            case TRANSACTION:
                return parse((TransactionRequest) request, responseContent);
            case TRANSACTION_QUERY:
                return parse((TransactionQueryRequest) request, responseContent);
            case IDENTITY:
                return parse((IdentityRequest) request, responseContent);
            case IDENTITY_QUERY:
                return parse((IdentityQueryRequest) request, responseContent);
            case RECONCILIATIONS:
                return parse((ReconciliationsRequest) request, responseContent);
            default:
                throw new UnsupportedOperationException("Unsupported request type was used.");
        }
    }

    protected TransactionResponse parse(TransactionRequest request, String responseContent) {
        throw new UnsupportedOperationException("transaction request was unsupported.");
    }

    protected TransactionQueryResponse parse(TransactionQueryRequest request, String responseContent) {
        throw new UnsupportedOperationException("transaction query request was unsupported.");
    }

    protected IdentityResponse parse(IdentityRequest request, String responseContent) {
        throw new UnsupportedOperationException("identity request was unsupported.");
    }

    protected IdentityQueryResponse parse(IdentityQueryRequest request, String responseContent) {
        throw new UnsupportedOperationException("identity query request was unsupported.");
    }

    protected ReconciliationsResponse parse(ReconciliationsRequest request, String responseContent) {
        throw new UnsupportedOperationException("reconciliations request was unsupported.");
    }


}
