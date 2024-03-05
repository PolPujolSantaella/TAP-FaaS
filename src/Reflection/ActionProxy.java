package Reflection;

import java.lang.reflect.InvocationHandler;

import java.lang.reflect.Method; 
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.List;

import Controller.Controller;

/**
 * Un 'InvocationHandler' que serveix com proxy per les accions definides per la interface Action.
 * Permet invocar dinàmicament els mètodes de la interface Action.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class ActionProxy implements InvocationHandler {
    private final Controller controller; // Controller
    private String actionName; // Nom Acció
    
    /**
     * Constructor de ActionProxy
     * 
     * @param controller El Contoller utilitzat per invocar accions.
     */
    public ActionProxy(Controller controller) {
        this.controller = controller; 
    }
    
    /**
     * Mètode que crea un proxy per una interface de acció específica i un nom d'accions donat.
     * 
     * @param <T>				Tipus de la interface Action
     * @param actionInterface	La interface Action per la qual es crearà el proxy.
     * @param actionName		EL nom de l'acció associada al proxy.
     * @return Una instància de la interface Action que actua com proxy.
     */
    @SuppressWarnings("unchecked")
    public <T extends Action> T createActionProxy (Class<T> actionInterface, String actionName){
        this.actionName = actionName; 
        return (T) Proxy.newProxyInstance(
                actionInterface.getClassLoader(),
                new Class<?>[]{actionInterface},
                this
        );
    }

    /**
     * Mètode invocat quan es crida un mètode en el proxy.
     * Si el mètode es "execute", invoca la acció corresponent.
     * 
     * @param proxy 	El objecte proxy.
     * @param method	El mètode invocat.
     * @param args		Els arguments proporcionats al mètode.
     * @return El resultat de la invocació del mètode o 'null' di el mètode no es "execute".
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke (Object proxy, Method method, Object[] args){
        if (method.getName().equals("execute")){
            return controller.invoke(actionName, (List<Map<String,Integer>>) args[0]); 
        }
        return null; 
    }
}