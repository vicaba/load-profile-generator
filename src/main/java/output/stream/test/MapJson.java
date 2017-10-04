package output.stream.test;

import akka.japi.Function;
import akka.stream.*;
import akka.stream.stage.AbstractInHandler;
import akka.stream.stage.AbstractOutHandler;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;

public class MapJson<A, B> extends GraphStage<FlowShape<A, B>> {

    private final Function<A, B> f;

    public MapJson(Function<A, B> f) {
        this.f = f;
    }

    private final Inlet<A> input = Inlet.create("FilterJson.in");
    private final Outlet<B> output = Outlet.create("FilterJson.out");

    @Override
    public FlowShape<A, B> shape() {
        return new FlowShape<>(input, output);
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(shape()) {


            {
                setHandler(input, new AbstractInHandler() {
                    @Override
                    public void onPush() throws Exception {
                        push(output, f.apply(grab(input)));
                    }
                });

                setHandler(output, new AbstractOutHandler() {
                    @Override
                    public void onPull() throws Exception {
                        pull(input);
                    }
                });
            }
        };
    }



}
