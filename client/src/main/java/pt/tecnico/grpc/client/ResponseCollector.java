package pt.tecnico.grpc.client;

import java.util.ArrayList;
import java.util.List;

public class ResponseCollector {
    
    final List<String> responses = new ArrayList<>();

    public void addResponse(String response) {
        responses.add(response);
        //? print the number of responses collected so far
        System.out.println("Collected " + responses.size() + " responses so far.");
        //? notify waiting threads that a new response has been collected
        synchronized (responses) {
            responses.notifyAll();
        }
    }

    public void waitUntilAllReceived(int expectedResponses){
        synchronized (responses) {
            while (responses.size() < expectedResponses) {
                try {
                    responses.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
