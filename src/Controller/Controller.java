package Controller;

import java.util.Map;
import java.util.HashMap; 
import java.util.List; 
import java.util.ArrayList; 
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.concurrent.*;

import Invoker.Invoker;
import Observer.Observer;
import Observer.Metric;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobin;
import ResultFuture.ResultFuture;

/**
 * Controller que gestiona la execucio d'accions mitjançant Invokers
 * i utilitza un PolicyManager per determinar la estrategia de gestio de recursos dels invocadors.
 * Implementa la interface Observer per registrar metriques d'execucio
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class Controller implements Observer{

    private int numInvoker; 
    private int totalMemoria; 
    private int memoryUsed; 
    private Map <String, Integer> actionMemory; 
    private Map<String, Function<?, ?>> actionRegister; 	// Mapa per relacionar el nom i la funció.
    private Invoker[] invokers; 							// Llista de Invokers.
    private PolicyManager strategy; 						// Estratègia gestió de recursos.
    private ExecutorService executorService; 				// Servei d'execució per accions asíncrones.
    private List<Metric> metrics; 							// Llista que emmagatzema mètriques d'execució.

    /**
     * Constructor de la classe Controller.
     * 
     * @param numInvoker	Numero de Invokers disponibles.
     * @param totalMemoria	Memoria total disponible per els Invokers.
     */
    public Controller (int numInvoker, int totalMemoria){
        this.numInvoker = numInvoker; 
        this.totalMemoria = totalMemoria; 
        this.memoryUsed=0;
        this.invokers = new Invoker[numInvoker]; 
        this.actionMemory = new HashMap<>(); 
        this.actionRegister = new HashMap<>();
        this.executorService = Executors.newFixedThreadPool(numInvoker); 
        this.metrics = new ArrayList<>(); 
        
        // Inicialitza els Invokers i els associa com Observers.
        for (int i=0; i < numInvoker; i++){
            invokers[i] = new Invoker(totalMemoria / numInvoker);
            invokers[i].addObserver(this); 
        }
    }
    
    /**
     * Registra una accio amb la funcio associada i la quantitat de memoria requerida.
     * 
     * @param <A>		Tipus del parametre d'entrada de la funcio.
     * @param <B>		Tipus del resultat de la funció.
     * @param action	Nom de l'acció.	
     * @param function	Funció associada a l'acció.
     * @param memory	Memòria requerida per l'acció.
     */
    public <A, B> void registerAction (String action, Function<A, B> function, int memory){
        
    	// Verifica si hi ha suficient memòria per l'acció.
    	if (memoryUsed + memory > totalMemoria){
            throw new RuntimeException("Not enough memory for the action"); 
        }

        memoryUsed += memory; 

        actionRegister.put(action, function); 
        actionMemory.put(action, memory); 
    }
    
    /**
     * Invoca l'acció especificada amb la llista de paràmetres i retorna una llista de resultats.
     * 
     * @param <A>		Tipus del paràmetre d'entrada.
     * @param <B>		Tipus del resultat de la funció.
     * @param action	Nom de l'acció.
     * @param params	Llista de paràmetres d'entrada.
     * @return	Llista de resultats de l'acció invocada.
     */
    public <A, B> List<B> invoke (String action, List<A> params){
        List<B> results = new ArrayList<>();
        
        // Obté la funció associada a l'acció.
        @SuppressWarnings("unchecked")
        Function <A, B> function = (Function<A, B>) actionRegister.get(action); 

        if (function == null){
            throw new RuntimeException("Function: "+ action + " NOT FOUND"); 
        }

        // Itera sobre els paràmetres i executa l'acció en el Invoker seleccionat.
        for (A param : params){
            Invoker selectInvoker = selectInvoker(); 
            results.add(selectInvoker.executeAction(function, param, actionMemory.get(action), params.size())); 
        }
        return results; 
    }
    
    /**
     * Invoca l'acció de manera asíncrona i retorna un ResultFuture per obtenir el resultat en un futur.
     * 
     * @param <A>				Tipus del paràmetre d'entrada de la funció.
     * @param <B>				Tipus del resultat de la funció.
     * @param action			Nom de l'acció a invocar.
     * @param sleepDuration		Duració d'espera associada a l'acció asíncrona.
     * @return ResultFuture que representa el resultat futur de l'acció asíncrona.
     */
    public <A, B> ResultFuture<B> invoke_async(String action, B sleepDuration){

    	ResultFuture<B> future = new ResultFuture<>(); 
    	
        @SuppressWarnings("unchecked")
        Function <A, B> function = (Function<A, B>) actionRegister.get(action); 

        if (function == null){
            throw new RuntimeException("Function: "+ action + " NOT FOUND"); 
        }

        executorService.submit(() -> {
        	try {
        		@SuppressWarnings("unchecked")
        		B result = selectInvoker().executeAction(function, (A) Map.of("text", sleepDuration), actionMemory.get(action),1);
        		future.setResult(result); 
        	} catch (Exception e) {
        		future.setException(e); 
        	}
        }); 
        
        return future; 
        
    }
    
    /**
     * Selecciona un Invoker segons la estratègia especificada (per defecte, RoundRobin).
     * 
     * @return Invoker seleccionat segons l'estratègia.
     */
    public Invoker selectInvoker() {
        if (strategy == null){
            strategy = new RoundRobin(); 
        }

        return strategy.selectInvoker(invokers); 
    }
    
    /**
     * Calcula i mostra la eficiènica de cada Invoker en termes de memòria utilitzada.
     * La eficiència s'expressa com percentatge de la memòria total del invocador.
     */
    public void eficienciaPolicyManager(){
		for (int i=0; i<invokers.length; i++) {
			double memUsed = invokers[i].getMemUsed(); 
			double memInvoker = invokers[i].getTotalMemoria(); 
			double percentatge = memUsed/memInvoker *100;
			long aprox = Math.round(percentatge);
			System.out.println("Invoker "+ (i+1) + ": " + aprox + "% utilitzat"); 
		}
	 }

    /**
     * Tanca el ExecutorService associat a aquest controller.
     */
    public void shutdown(){
    	executorService.shutdown();
    }
    
    /**
     * Actualitza les mètriques d'execució quan es notifica un canvi en un Invoker.
     * 
     * @param tempsExecucio Temps d'execució de l'acció.
     * @param memoryUsed 	Memòria utilitzada durant la execució.
     * @param invoker		Invocador associat a l'execució.
     */
    @Override
 	public void update(long tempsExecucio, int memoryUsed, Invoker invoker) {
    	metrics.add(new Metric(tempsExecucio, memoryUsed, invoker)); 
 	}
    
    /**
     * Calcula i mostra mètriques agregades, com el temps màxim, mínim i promig d'execució,
     * i la utilització de memòria per cada Invoker.
     * 
     * @param metrics Llista de mètriques individuals.
     */
     
    public void CalculMetrics (List<Metric> metrics) {
    	
    	// Càlcul promig de temps execució.
        double promig = metrics.stream()
        					   .mapToLong(Metric::getTempsExe)
        					   .average()
        					   .orElse(0.0); 
        
        // Càlcul temps execució Màx.
        long max = metrics.stream()
        				  .mapToLong(Metric::getTempsExe)
        				  .max()
        				  .orElse(0);
        
        // Càlcul temps execució Mín.
        long min = metrics.stream()
        				  .mapToLong(Metric::getTempsExe)
        				  .min()
        				  .orElse(0); 
        
        // Càlcul temps agregat.
        long tempsAgregat = metrics.stream()
					   .mapToLong(Metric::getTempsExe)
					   .sum();
      
        // Calcula utilització memòria per cada Invoker.
        Map<Invoker, Integer> utilizacionMemoria = new HashMap<>();

        Map<Invoker, List<Metric>> metricsPorInvoker = metrics.stream()
                .collect(Collectors.groupingBy(Metric::getInvoker));

        metricsPorInvoker.forEach((invoker, invokerMetrics) -> {
        	int memoriaTotal = invokerMetrics.stream()
                    .mapToInt(Metric::getMemoryUsed)
                    .sum();
        	utilizacionMemoria.put(invoker, memoriaTotal);
        });
        
        // Printar Resultats.
        
        System.out.println("Temps Execucio MAX: "+ max + " nanosegons."); 
        System.out.println("Temps Execucio MIN: "+ min + " nanosegons."); 
        System.out.println("Temps Execucio PROMIG: "+ promig + " nanosegons.");
        System.out.println("Temps Execucio AGREGAT: "+ tempsAgregat + " nanosegons.");
        
        for (int i=0; i<invokers.length; i++) {
        	System.out.println("Invoker "+ (i+1) +" "+ utilizacionMemoria.get(invokers[i])+ "MB de memoria."); 
        }
    }
    
    // Getters i Setters 
    
    /**
     * Getter
     * @return numInvoker
     */
	public int getNumInvoker() {
		return numInvoker;
	}

	/**
	 * Setter
	 * @param numInvoker Número Invokers
	 */
	public void setNumInvoker(int numInvoker) {
		this.numInvoker = numInvoker;
	}

	/**
	 * Getter
	 * @return totalMemoria
	 */
	public int getTotalMemoria() {
		return totalMemoria;
	}

	/**
	 * Setter
	 * @param totalMemoria Memoria Disponible
	 */
	public void setTotalMemoria(int totalMemoria) {
		this.totalMemoria = totalMemoria;
	}

	/**
	 * Getter
	 * @return memoryUsed
	 */
	public int getMemoryUsed() {
		return memoryUsed;
	}

	/** 
	 * Setter
	 * @param memoryUsed Memoria Utilitzada
	 */
	public void setMemoryUsed(int memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
	
	/**
	 * Getter
	 * @return actionMemory
	 */

	public Map<String, Integer> getActionMemory() {
		return actionMemory;
	}

	/**
	 * Setter
	 * @param actionMemory ActionMemory
	 */
	public void setActionMemory(Map<String, Integer> actionMemory) {
		this.actionMemory = actionMemory;
	}

	/**
	 * Getter
	 * @return actionregister
	 */
	public Map<String, Function<?, ?>> getActionRegister() {
		return actionRegister;
	}

	/**
	 * Setter
	 * @param actionRegister ActionRegister
	 */
	public void setActionRegister(Map<String, Function<?, ?>> actionRegister) {
		this.actionRegister = actionRegister;
	}

	/** 
	 * Getter
	 * @return Llista Invokers
	 */
	public Invoker[] getInvokers() {
		return invokers;
	}
	/**
	 * Setter
	 * @param invokers Llista Invokers
	 */
	public void setInvokers(Invoker[] invokers) {
		this.invokers = invokers;
	}

	/**
	 * Getter
	 * @return strategy
	 */
	public PolicyManager getStrategy() {
		return strategy;
	}
	
	/**
	 * Setter
	* @param strategy Estratègia
	*/
	public void setStrategy(PolicyManager strategy) {
		this.strategy = strategy;
	}

	/**
	 * Getter
	 * @return executorService
	 */
	public ExecutorService getExecutorService() {
		return executorService;
	}
	/**
	 * Setter
	* @param executorService ExecutorService
	*/
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	/**
	 * Getter
	 * @return metrics
	 */
	public List<Metric> getMetrics() {
		return metrics;
	}
	
	/**
	* Setter
	* @param metrics metrics
	*/
	public void setMetrics(List<Metric> metrics) {
		this.metrics = metrics;
	}
}
