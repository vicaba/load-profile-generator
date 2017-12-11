package example.stream.test;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import example.stream.test.Printer.Greeting;

public class Greeter /*extends AbstractActor*/ {
  /*
   * Define como se ha de construir el actor. Si se quiere crear un Greeter,
   * esto forzará a que solo se acepte un Greeter con un String message y un ActorRef printerActor en su constructor.
   * No aceptará un constructor sin parámetros.
   */
  public static Props props(String message, ActorRef printerActor) {
    //return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    return null;
  }

  /*
   * Todas las clases asociadas con el mensaje (WhoToGreet y Greet) deben
   * ser static para facilitar saber que tipo de mensaje el Actor espera y tratara.
   */
  public static class WhoToGreet {
    public final String who;

    public WhoToGreet(String who) {
      this.who = who;
    }
  }

  public static class Greet {
    public Greet() {}
  }

  /*
   * Lo que sea relacionado con la creacion del mensaje ha de ser immutable, por eso usamos final.
   * Aparte de cuando se usa el constructor, nunca más se podrá cambiar.
   */
  private final String message; // Mensaje predeterminado a añadir al mensaje final.
  private final ActorRef
      printerActor; // Referencia al Actor que tiene la responsabilidad de sacar los mensajes fuera.
  private String greeting = "";

  public Greeter(String message, ActorRef printerActor) {
    this.message = message;
    this.printerActor = printerActor;
  }

  /*
   * Dependiendo de lo que reciba, creará un Receive después de aplicar los cambios:
   * - Si recibe un WhoToGreet, modificará el mensaje de bienvenida con el mensaje
   * y el nombre de quien ha de recibir este mensaje.
   * - Si recibe un Greet, enviará al printerActor un Greeting y una referencia a si mismo
   * para que el printerActor sepa quien lo ha enviado.
   */
  /*@Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(
            WhoToGreet.class,
            wtg -> {
              this.greeting = message + ", " + wtg.who;
            })
        .match(
            Greet.class,
            x -> {
              printerActor.tell(new Greeting(greeting), getSelf());
            })
        .build();
  }
  */
}
