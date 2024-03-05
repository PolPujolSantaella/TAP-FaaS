package Tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.Controller;
import Decorator.MemoizationDecorator;
import Decorator.TimerDecorator;

class DecoratorTest {
    
	private Controller controller; 
	private List<Map<String, Integer>> inputFact; 
	private Function<Map<String, Integer>, Integer> factorial; 
	
	@BeforeEach
	void setUp() {
		controller = new Controller(4, 1024); 
		
		inputFact = Arrays.asList(
				Map.of("n", 5),
				Map.of("n", 5),
				Map.of("n", 3)
		); 
		
		factorial = x -> {
			int n = x.get("n"); 
			return (n == 0 || n== 1) ? 1: n * factorial.apply(Map.of("n", n-1)); 
		}; 
	}

    @Test
	void test(){
		Function<Map<String, Integer>, Integer> decorator = new TimerDecorator(new MemoizationDecorator(factorial)); 
		controller.registerAction("factorialAction", decorator, 256);

		List<Integer> resultFacts = controller.invoke("factorialAction", inputFact); 

		System.out.println("Result: "+ resultFacts); 
		
		assertEquals(120, resultFacts.get(0), "Factorial de 5 incorrecte");
        assertEquals(120, resultFacts.get(1), "Factorial de 5 incorrecte (memoization)");
        assertEquals(6, resultFacts.get(2), "Factorial de 3 incorrecte");

    }
}