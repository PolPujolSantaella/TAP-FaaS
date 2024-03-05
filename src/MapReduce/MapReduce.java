package MapReduce;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe que implementa la funció de reducció per combinar els resultats de mapeig en un sol resultat.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class MapReduce implements Function<List<Map<String, Integer>>, Map<String, Integer>> {
    
	/**
	 * Constructor MapReduce
	 */
	public MapReduce() {
		// Constructor Buit
	}
	/**
	 * Aplica la funció de reduccio per combinar els resultats de mapeig en un sol resultat.
	 * Realitza la suma de ocurrencies de cada paraula dels resultats de mapeig individuals.
	 * 
	 * @param mapResults Llista de resultats de mapeig indiviuals.
	 * @return Diccionari que assigna cada paraula al seu número de ocurrencies en tots els resultats de mapeig.
	 */
    @Override
    public Map<String, Integer> apply (List<Map<String, Integer>> mapResults){
    	// Retornem suma
        return mapResults.stream()
                .flatMap(map -> map.entrySet().stream()) 
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum)); 
    }
}