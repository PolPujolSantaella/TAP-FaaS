package Tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import Controller.Controller;
import PolicyManager.BigGroup;
import PolicyManager.GreedyGroup;
import PolicyManager.UniformGroup;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobin;

class PolicyManagerTest {

    private static int memoryRead; 

    String [] textFiles = {"file1.txt", "file2.txt", "file3.txt"}; 
    
    public static List<Map<String, Integer>> readFromFile(String fileName){
		List<Map<String, Integer>> result = new ArrayList<>(); 

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
			String firstline = br.readLine();
			String[] functionInfo = firstline.split(","); 

			if (functionInfo.length == 1){
				memoryRead = Integer.parseInt(functionInfo[0]); 

				String line; 

				while((line = br.readLine()) != null){
					String[] parts = line.split(",");

					if (parts.length == 2){
						try{
							int x = Integer.parseInt(parts[0]); 
							int y = Integer.parseInt(parts[1]); 

							Map<String, Integer> map = new HashMap<>(); 
							map.put("x", x); 
							map.put("y", y); 
							result.add(map); 
						} catch (NumberFormatException e){
							System.err.println("Error al convertir numeros"); 
						}
					} else{
						System.err.println("Format incorrecte en la linea: "+ line); 
					}
				}
			}
			} catch (IOException e){
				e.printStackTrace(); 
			}

		return result; 
	}

    private static Function<Map<String, Integer>, Integer> fSuma = x -> x.get("x") + x.get("y");
    private static Function<Map<String, Integer>, Integer> fResta = x -> x.get("x") - x.get("y");
	private static Function<Map<String, Integer>, Integer> fMult  = x -> x.get("x") * x.get("y");


    public static void run(List<Map<String, Integer>> input, int memory, PolicyManager policy){
        Controller controller = new Controller(4, 1024); 
        controller.setStrategy(policy);

        controller.registerAction("addAction", fSuma, memory); 
        controller.registerAction("subAction", fResta, memory); 
		controller.registerAction("multAction", fMult, memory);

        controller.invoke("addAction", input); 
        controller.invoke("subAction", input); 
		controller.invoke("multAction", input); 

        System.out.println("Eficiencia "+ policy.getClass() + ": "); 

        controller.eficienciaPolicyManager();
        System.out.println("--------------------"); 
    }

    @Test
	void test() {
        for (int i = 0; i < textFiles.length; i++){
			List<Map<String, Integer>> input = readFromFile(textFiles[i]); 
			System.out.println("--------------------"); 
			System.out.println("Fitxer "+ (i+1)+ ":");
			System.out.println("--------------------");

            run(input, memoryRead, new RoundRobin()); 
            run(input, memoryRead, new GreedyGroup()); 
            run(input, memoryRead, new UniformGroup(6)); 
            run(input, memoryRead, new BigGroup(3, input.size()));  
        }
    }

    
}