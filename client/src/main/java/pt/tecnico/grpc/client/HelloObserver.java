package pt.tecnico.grpc.client;

import io.grpc.stub.StreamObserver;
import pt.tecnico.grpc.client.ResponseCollector;

public class HelloObserver<R> implements StreamObserver<R> {

    private final io.grpc.ManagedChannel channel;
    private final ResponseCollector responseCollector;

    public HelloObserver(io.grpc.ManagedChannel channel, ResponseCollector responseCollector) {
        this.channel = channel;
        this.responseCollector = responseCollector;
    }

    @Override
    public void onNext(R r) {
        System.out.println("Received response: " + r+"\n");
        //? add response to the collector
        responseCollector.addResponse(r.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error receiving response: " + throwable.getMessage()+"\n");
        //? add error message to the collector
        responseCollector.addResponse("Error: " + throwable.getMessage());
        //? shutdown the channel on error
        channel.shutdownNow();
    }

    @Override
    public void onCompleted() {
        System.out.println("Response stream completed!\n");
        //? shutdown the channel when done
        channel.shutdownNow();
    }

}
