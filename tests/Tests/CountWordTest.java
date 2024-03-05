package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Controller.Controller;
import MapReduce.CountWordMap;
import MapReduce.MapReduce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountWordTest {

    private Controller controller; 
    private List<String> input; 
    
    @BeforeEach
    void setUp() {
    	controller = new Controller(3, 300); 
    	controller.registerAction("mapFunction",new CountWordMap(), 128);
    	input = new ArrayList<>(); 
    	String[] filesName = {"MobyDick.txt", "Romeo&Juliet.txt", "Frankenstein.txt", "HistoryTomJones.txt",
                "MyLife.txt", "AliceAdventures.txt", "Dracula.txt", "Sherlock.txt", "ThePrince.txt", 
                "TheRepublic.txt", 
              }; 
    	
    	 for (int i=0; i< filesName.length; i++){
             String text = readFromFile(filesName[i]); 
             input.add(text); 
         }
    }  
    
    
    @Test
    void test() {
        
        List<Map<String, Integer>> mapResults = controller.invoke("mapFunction", input); 

        assertEquals(1374, mapResults.get(0).get("totalWords"),"Conteo de palabras incorrecte per MobyDick");
        assertEquals(283, mapResults.get(1).get("totalWords"), "Conteo de palabras incorrecte per Romeo&Juliet");
        assertEquals(174, mapResults.get(2).get("totalWords"), "Conteo de palabras incorrecte per Frankenstein"); 
        assertEquals(248, mapResults.get(3).get("totalWords"), "Conteo de palabras incorrecte per HistoryTomJones"); 
        assertEquals(205, mapResults.get(4).get("totalWords"), "Conteo de palabras incorrecte per MyLife"); 
        assertEquals(174, mapResults.get(5).get("totalWords"), "Conteo de palabras incorrecte per AliceAdventures"); 
        assertEquals(383, mapResults.get(6).get("totalWords"), "Conteo de palabras incorrecte per Dracula"); 
        assertEquals(222, mapResults.get(7).get("totalWords"), "Conteo de palabras incorrecte per Sherlock");  
        assertEquals(137, mapResults.get(8).get("totalWords"), "Conteo de palabras incorrecte per ThePrince"); 
        assertEquals(163, mapResults.get(9).get("totalWords"), "Conteo de palabras incorrecte per TheRepublic"); 
        
        controller.registerAction("reduceMap", new MapReduce(), 128);

        List<Map<String, Integer>> wordCountResult = controller.invoke("reduceMap", Arrays.asList(mapResults));
        
        assertEquals(3363, wordCountResult.get(0).get("totalWords"),"Conteo de palabras incorrecte."); 
    }
    
    public static String readFromFile(String fileName){
        try {
            Path path = Paths.get(fileName); 

            byte[] bytes = Files.readAllBytes(path);

            String content = new String(bytes);

            return content; 
        } catch (IOException e) {
            e.printStackTrace();
            return null; 
        } 
    }

}