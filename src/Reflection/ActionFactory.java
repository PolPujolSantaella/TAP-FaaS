package Reflection;

import java.lang.reflect.Proxy;
import Controller.Controller;

/**
 * Classe que proporciona mètodes per la creació d'instàncies de la interface Action mitjançant reflexió.
 * Aquesta classe utilitza la Reflection per crear un proxy dinàmic de la interface Action.
 * 
 * @author Pol Pujol
 * @author Ivet Pallàs
 */
public class ActionFactory {
	
	/**
	 * Constructor de la classe Action Factory
	 */
	public ActionFactory () {
		// Constructor buit
	}
	
	/**
	 * Mètode per crear un proxy de la interface Action. 
	 * 
	 * @param controller Controller on es crearà l'acció.
	 * @return Instància de la interface Action representada per un proxy dinàmic.
	 */
    public static Action createActionProxy (Controller controller){
        return (Action) Proxy.newProxyInstance(
                Action.class.getClassLoader(), 
                new Class[]{Action.class}, 
                new ActionProxy(controller)
        ); 
    }   
}