package Tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import Controller.Controller;
import Reflection.Action;
import Reflection.ActionProxy;

class ReflectionTest {
    

    private static List<Map<String, Integer>> input = Arrays.asList(
        Map.of("x", 2, "y", 3),
        Map.of("x", 9, "y", 1),
        Map.of("x", 8, "y", 8),
        Map.of("x", 3, "y", 8),
        Map.of("x", 5, "y", 9)
    );

    private static Function<Map<String, Integer>, Integer> fSuma = x -> x.get("x") + x.get("y");

    @Test
    void test() {
         Controller controller = new Controller(4, 1024);
        ActionProxy actionProxy = new ActionProxy(controller); 

        controller.registerAction("addAction", fSuma, 128);

        Action myAction = actionProxy.createActionProxy(Action.class, "addAction"); 
        
        List<Integer> result = myAction.execute(input); 

        System.out.println("Resultat: "+ result); 

    }


}