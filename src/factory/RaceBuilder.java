/**
 *	The race construction department creates arrays of races and dynamically loads them according to Reflection
 *
 * @version 04.04 04 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	
 */

package factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.arenas.Arena;
//import game.arenas.exceptions.*;
import game.racers.Racer;
import utilities.EnumContainer;
import utilities.EnumContainer.Color;

public class RaceBuilder {

	private static RaceBuilder instance;
	private ClassLoader classLoader;
	private Class<?> classObject;
	private Constructor<?> constructor;

	/*
	 * The buildArena method takes in three parameters: arenaType, length, and
	 * maxRacers. It returns an instance of the Arena class based on the arenaType
	 * parameter.
	 * 
	 * The method uses Java reflection to dynamically load the class specified by
	 * the arenaType parameter and invoke its constructor with the given length and
	 * maxRacers parameters.
	 * 
	 * If the class specified by the arenaType parameter does not exist or does not
	 * have a constructor that takes in a double and an int, the method throws
	 * several exceptions: ClassNotFoundException, NoSuchMethodException,
	 * SecurityException, InstantiationException, IllegalAccessException,
	 * IllegalArgumentException, and InvocationTargetException.
	 * 
	 * Finally, the method returns the newly created instance of the Arena class.
	 */
	public Arena buildArena(String arenaType, double length, int maxRacers)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ClassLoader c = ClassLoader.getSystemClassLoader();
		Class<?> a;
		Constructor<?> b;
		a = c.loadClass(arenaType);
		b = a.getConstructor(double.class, int.class);
		return (Arena) b.newInstance(length, maxRacers);

	}

	/*
	 * an method called buildRacer that takes four parameters: racerType, name,
	 * maxSpeed, acceleration, and color. It uses reflection to create an instance
	 * of the racer object based on the racerType parameter.
	 * 
	 * First, it retrieves the system class loader using
	 * ClassLoader.getSystemClassLoader(). Then, it loads the class object for the
	 * racer type specified in the racerType parameter using c.loadClass(racerType).
	 * It retrieves the constructor for the class object using
	 * a.getConstructor(String.class,double.class,double.class,Color.class).
	 * 
	 * Finally, it creates a new instance of the racer object using
	 * b.newInstance(name,maxSpeed,acceleration,color) and returns the object casted
	 * to the Racer class.
	 * 
	 * This method allows for dynamic object creation based on the type of racer
	 * specified as a string in the racerType parameter. This can be useful in
	 * scenarios where different types of racers need to be created dynamically at
	 * runtime, without needing to modify the code to add new racer types.
	 */
	public Racer buildRacer(String racerType, String name, double maxSpeed, double acceleration,
			EnumContainer.Color color) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ClassLoader c = ClassLoader.getSystemClassLoader();
		Class<?> a;
		Constructor<?> b;
		a = c.loadClass(racerType);
		b = a.getConstructor(String.class, double.class, double.class, Color.class);
		return (Racer) b.newInstance(name, maxSpeed, acceleration, color);

	}

	/*
	 * This code defines a method buildWheeledRacer() that creates and returns an
	 * instance of a Racer subclass with wheels. The method takes in several
	 * parameters including racerType (a String representing the name of the
	 * subclass of Racer to be created), name, maxSpeed, acceleration, color (an
	 * instance of the Color enum class), and numOfWheels (an int representing the
	 * number of wheels of the racer).
	 * 
	 * The method then uses a ClassLoader to dynamically load the class with the
	 * name specified by racerType. It then retrieves the appropriate constructor
	 * for that class, which takes in a String, double, double, Color, and int.
	 * Finally, it creates a new instance of the class using the retrieved
	 * constructor and the provided arguments, and returns it as a Racer object.
	 */
	public Racer buildWheeledRacer(String racerType, String name, double maxSpeed, double acceleration,
			EnumContainer.Color color, int numOfWheels)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ClassLoader c = ClassLoader.getSystemClassLoader();
		Class<?> a;
		Constructor<?> b;
		a = c.loadClass(racerType);
		b = a.getConstructor(String.class, double.class, double.class, Color.class, int.class);
		return (Racer) b.newInstance(name, maxSpeed, acceleration, color, numOfWheels);

	}

	// Getter and Setter
	public static RaceBuilder getInstance() {
		if (instance == null)
			instance = new RaceBuilder();
		return instance;
	}

	public static boolean setInstance(RaceBuilder instance) {
		if (RaceBuilder.instance != instance) {
			RaceBuilder.instance = instance;
			return true;
		} else
			return false;
	}

}
