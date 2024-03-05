package ResultFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Classe que representa un resultat futur utilitzant CompletableFuture.
 * Permet establir el resultat o excepció que serà completat.
 * 
 * @param <T> El tipus de resultat esperant en el futur.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class ResultFuture<T> {
    
    private final CompletableFuture<T> future; 

    /**
     * Constructor de ResultFuture 
     */
    public ResultFuture(){
        this.future = new CompletableFuture<>();
    }
    
    /**
     * Estableix el resultat del CompletableFuture.
     * 
     * @param result El resultat que s'establirà en el CompletableFuture.
     */
    public void setResult(T result) {
    	future.complete(result); 
    }
    
    /**
     * Estableix una excepció en el CompletableFuture.
     * 
     * @param exception La excepció que s'establirà en el CompletableFutue.
     */
    public void setException (Exception exception) {
    	future.completeExceptionally(exception); 
    }

    /**
     * Obté el resultat del CompletableFuture.
     * 
     * @return El resultat del CompletableFuture.
     * @throws RuntimeException Si passa una interrupció o excepció al obtindre el resultat.
     */
    public T get(){
        try{
            return future.get(); 
        } catch (InterruptedException | ExecutionException e){
            throw new RuntimeException(e); 
        }
    }
}
