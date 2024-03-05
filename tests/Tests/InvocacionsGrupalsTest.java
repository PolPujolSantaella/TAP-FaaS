package Tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import Controller.Controller;


class InvocacionsGrupalsTest {

	private static List<Map<String, Integer>> input = Arrays.asList(
	        Map.of("x", 2, "y", 3),
	        Map.of("x", 9, "y", 1),
	        Map.of("x", 8, "y", 8),
	        Map.of("x", 3, "y", 8),
	        Map.of("x", 5, "y", 9)
	    );
	
	private static List<Integer> SolucioSuma = Arrays.asList(5, 10, 16, 11, 14);
	private static Function<Map<String, Integer>, Integer> fSuma = x -> x.get("x") + x.get("y");
	
	@Test
	void test() {
		Controller controller = new Controller (4, 1024); 
		
		controller.registerAction("addAction", fSuma, 128);
		List<Integer> result = controller.invoke("addAction", input); 
		
		assertEquals(SolucioSuma, result, "Suma Incorrecte"); 
	}

}
