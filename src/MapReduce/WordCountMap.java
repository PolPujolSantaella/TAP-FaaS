package MapReduce;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe que implementa la funció de mapeig per comptar el número de ocurrencies de cada paraula en un text.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class WordCountMap implements Function<String, Map<String, Integer>> {

	/**
	 * Constructor WordCountMap
	 */
	public WordCountMap() {
		//Constructor buit
	}
	/**
	 * Aplica la funció de mapeig per comptar el número de ocurrencies de cada paraula en un text.
	 * Neteja el text, passa a minúscules i compta les ocurrencies.
	 * 
	 * @param text Text del qual es compten les ocurrencies de paraules.
	 * @return Diccionari que assigna cada parula al seu número de ocurrencies en el text.
	 */
    @Override
    public Map<String, Integer> apply(String text) {
    	// Netejem text
        String cleanedText = text.replaceAll("[^a-zA-Z0-9\\s]", "");

        // Passem a minuscules i separem paraules
        List<String> words = Arrays.asList(cleanedText.toLowerCase().split("\\s+"));

        // Retornem Diccionari paraula/num ocurrencia
        return words.stream().collect(Collectors.toMap(Function.identity(), w -> 1, Integer::sum));
    }
}