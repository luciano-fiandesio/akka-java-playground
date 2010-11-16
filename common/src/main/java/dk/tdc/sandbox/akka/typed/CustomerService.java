package dk.tdc.sandbox.akka.typed;


import akka.dispatch.Future;

public interface CustomerService {

    Future<String> getCustomer(String id);
}
