package example.stream.test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;

class PrintMyActorRefActor extends AbstractActor {

    /*
     * Para crear hijos de actores si es necesario, se usa getContext en el actor.
     * El actor responderá con esta función si se le hace un tell hacia este.
     */
  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .matchEquals(
            "printit",
            p -> {
              ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
              System.out.println("Second: " + secondRef);
            })
        .build();
  }
}

public class ActorHierarchyExperiments {
  public ActorHierarchyExperiments() {
    ActorSystem system = ActorSystem.create("testHierarchy");
    try {

      ActorRef firstRef = system.actorOf(Props.create(PrintMyActorRefActor.class), "first-actor");
      System.out.println("First: " + firstRef);
      firstRef.tell("printit", ActorRef.noSender());

      System.out.println(">>> Press ENTER to exit <<<");

      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      system.terminate();
    }
  }
}
