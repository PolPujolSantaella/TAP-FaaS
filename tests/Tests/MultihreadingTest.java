package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import Controller.Controller;
import ResultFuture.ResultFuture;

class MultihreadingTest {

     private static Function <Map<String, Integer>, Integer> sleep = s -> {
		try {
				int seconds = s.get("text"); 
				Thread.sleep(Duration.ofSeconds(seconds).toMillis());
				return 1; 
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		};

    @Test
	void test() throws InterruptedException, ExecutionException {
    	
        Controller controller = new Controller(4, 1024);

		controller.registerAction("sleepAction", sleep, 250);

		Instant startTimer = Instant.now();

		ResultFuture<Integer> fut1 = controller.invoke_async("sleepAction", 5); 
		ResultFuture<Integer> fut2 = controller.invoke_async("sleepAction", 5);
		ResultFuture<Integer> fut3 = controller.invoke_async("sleepAction", 5);
		fut1.get();
		fut2.get();
		fut3.get();

		Instant endTimer = Instant.now(); // Registra el tiempo de finalización

		controller.shutdown(); 

		long TempsTrigat = Duration.between(startTimer, endTimer).toSeconds();

		assertEquals(TempsTrigat, 5, "Temps d'execució no és 5"); 
    }
}