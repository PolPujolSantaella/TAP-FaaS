package Tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import Controller.Controller;
import Observer.Metric;

class ObserverTest {

    @SuppressWarnings("unchecked")
	private static List<Map<String, Integer>> input = Arrays.asList(new Map[]{
		Map.of("x", 2, "y", 3),
		Map.of("x", 9, "y", 1),
		Map.of("x", 8, "y", 8),
		Map.of("x", 3, "y", 8), 
		Map.of("x", 5, "y", 9), 
	});
    
    private static Function<Map<String, Integer>, Integer> fSuma = x -> x.get("x") + x.get("y");
	private static Function<Map<String, Integer>, Integer> fResta = x -> x.get("x") - x.get("y");
    
    @Test
	void test() {

        Controller controller = new Controller(4, 1024); 

		controller.registerAction("addAction", fSuma, 128);
		controller.registerAction("subAction", fResta, 64);

		controller.invoke("addAction", input); 
		controller.invoke("subAction", input);

		List<Metric> metrics = controller.getMetrics(); 

		controller.CalculMetrics(metrics);
    }

}