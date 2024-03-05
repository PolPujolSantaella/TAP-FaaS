package MapReduce;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Classe que implementa la funció de mapeig per comptar el número total de paraules en un text.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class CountWordMap implements Function<String, Map<String, Integer>> {

	/**
	 * Constructor CountWordMap
	 */
	public CountWordMap() {
		// Constructor Buit
	}
	/**
	 * Aplica la funció de mapeig per comptar el número total de paraules en un text.
	 * Neteja el text, passa les paraules a minúscules i compta la quantitat total de paraules.
	 * 
	 * @param text Text del qual es comptaran les paraules.
	 * @return Diccionari amb la clau "totalWords" i el valor corresponent al número total de paraules.
	 */
    @Override
    public Map<String, Integer> apply(String text) {
    	// Netejem text
        String cleanedText = text.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Passem a minúscules i separem paraules
        List<String> words = Arrays.asList(cleanedText.toLowerCase().split("\\s+")); 

        // Retornem Diccionari
        int totalWords = words.size();
        return Map.of("totalWords", totalWords); 
    }   
}