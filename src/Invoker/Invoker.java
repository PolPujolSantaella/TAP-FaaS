package Invoker;
  
import java.util.function.Function; 
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List; 
import java.util.ArrayList; 

import Observer.Observer;

/**
 * Classe que representa un Invoker per executar accions de manera síncrona i asíncrona, 
 * gestionant la memòria utilitzada i notificant als Observers sobre el temps i la memòria utilitzada.
 * Implementa el patró Observer per la notificació de events.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class Invoker {

    private int totalMemoria; 
    private int memUsed;
    private int funcionsComplete; 
    private boolean ple; 
    private final Lock memoryLock = new ReentrantLock();
    private List<Observer> observers = new ArrayList<>(); 

    /**
     * Constructor de la classe Invoker.
     * 
     * @param totalMemoria Memòria total disponible per el Invoker.
     */
    public Invoker (int totalMemoria){
        this.totalMemoria = totalMemoria; 
    }

    /**
     * Executa una acció de manera síncrona, gestionant la memòria utilitzada i notificant als Observers.
     * 
     * @param <A>		  	Tipus del paràmetre d'entrada de la funció.
     * @param <B>			Tipus del resultat de la funció.
     * @param function		Funció que s'ha d'executar.
     * @param param			Paràmeter d'entrada per la funció.
     * @param memoryAction	Memòria requerida per l'acció.
     * @param numFunctions	Número total de funcions a executar.
     * @return Result de l'execució de la funció.
     */
    public <A, B> B executeAction (Function<A, B> function, A param, int memoryAction, int numFunctions){
        int memoryFunction = calculateMemory(memoryAction, numFunctions); 

        memUsed += memoryFunction; 

        if (totalMemoria - memUsed < memoryFunction){
            ple = true; 
        }

        funcionsComplete ++; 

        long start = System.nanoTime(); 
        B result = function.apply(param); 
        long end = System.nanoTime(); 

        long tempsExe = end - start; 
        
        notifyObservers(tempsExe, memoryFunction, this); 

        return result; 
    }
    
    /**
     * Executa una acció de manera asíncrona, gestionant la memòria utilitzada i notificant als Observers.
     * 
     * @param <A>		  	Tipus del paràmetre d'entrada de la funció.
     * @param <B>			Tipus del resultat de la funció.
     * @param function		Funció que s'ha d'executar de manera asíncrona
     * @param param			Paràmetre d'entrada per la funció.
     * @param memoryAction	Memòria requerida per l'acció.
     * @param numFunctions	Número total de funcions a executar. 
     * @return Future que representa el resultat de la execució asíncrona.
     */
    public <A, B> Future<B> executeActionAsync(Function<A, B> function, A param, int memoryAction, int numFunctions) {
        int memoryFunction = calculateMemory(memoryAction, numFunctions);

        memoryLock.lock();
        try {
            memUsed += memoryFunction;

            if (totalMemoria - memUsed < memoryFunction) {
                ple = true;
            }
        } finally {
            memoryLock.unlock();
        }

        funcionsComplete++;

        CompletableFuture<B> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            long start = System.nanoTime();
            B result = function.apply(param);
            long end = System.nanoTime();

            long tempsExe = end - start;

            future.complete(result);
            notifyObservers(tempsExe, memoryFunction, this); 
        });

        return future;
    }
    
    /**
     * Agrega un Observer per rebre notificacions de events.
     * 
     * @param observer Observer a agregar.
     */
    public void addObserver (Observer observer) {
    	observers.add(observer); 
    }
    
    /**
     * Notifica a tots els Observers sobre un event.
     * 
     * @param tempsExe	Temps d'execució de l'acció.
     * @param memory	Memòria utilitzada durant la execució.
     * @param invoker	Invoker associat a l'execució.
     */
    public void notifyObservers(long tempsExe, int memory, Invoker invoker) {
    	for (Observer observer : observers) {
    		observer.update(tempsExe, memory, this); 
    	}
    }
    
    /**
     * Calcula la memòria per una acció en funció del número total de funcions.
     * 
     * @param memory 		Memòria rquerida per l'acció.
     * @param numFunctions	Número total de funcions.
     * @return Memòria calculada per una funció.
     */

    public int calculateMemory(int memory, int numFunctions){
		return memory/numFunctions; 
	}
    
    
    // Getters i Setters

    /**
     * Getter
     * @return totalMemoria
     */
	public int getTotalMemoria() {
		return totalMemoria;
	}

	/**
	 * Setter
	 * @param totalMemoria Memoria disponible
	 */
	public void setTotalMemoria(int totalMemoria) {
		this.totalMemoria = totalMemoria;
	}

	/**
	 * Getter
	 * @return memUsed
	 */
	public int getMemUsed() {
		return memUsed;
	}

	/**
	 * Setter
	 * @param memUsed Memoria utilitzada
	 */
	public void setMemUsed(int memUsed) {
		this.memUsed = memUsed;
	}
	
	/**
	 * Getter
	 * @return funcionsComplete
	 */
	public int getFuncionsComplete() {
		return funcionsComplete;
	}

	/**
	 * Setter
	 * @param funcionsComplete funcionsComplete
	 */
	public void setFuncionsComplete(int funcionsComplete) {
		this.funcionsComplete = funcionsComplete;
	}

	/**
	 * Getter
	 * @return ple
	 */
	public boolean isPle() {
		return ple;
	}

	/**
	 * Setter
	 * @param ple Si està ple
	 */
	public void setPle(boolean ple) {
		this.ple = ple;
	}

	/**
	 * Getter 
	 * @return observers
	 */
	public List<Observer> getObservers() {
		return observers;
	}
	
	/**
	 * Setter
	 * @param observers Observer
	 */

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}
	
	/**
	 * Getter
	 * @return memoryLock
	 */

	public Lock getMemoryLock() {
		return memoryLock;
	}   
}
