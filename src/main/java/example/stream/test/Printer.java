package example.stream.test;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Printer /*extends AbstractActor*/ {

  public static Props props() {
    //return Props.create(Printer.class, Printer::new);
    return null;
  }

  public static class Greeting {
    public final String message;

    public Greeting(String message) {
      this.message = message;
    }
  }

  //private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  public Printer() {}

  /*
  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Greeting.class, greeting -> log.info(greeting.message)).build();
  }
  */
}
