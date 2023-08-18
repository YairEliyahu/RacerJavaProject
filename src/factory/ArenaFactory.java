///**
// * This department designs an aerial arena template
// * 
// *
// * @version 11.06 11 Jun 2023
// * @author 1:  Yair Eliyahu
// * @see   	Arena class
// */





package factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import game.arenas.Arena;
import game.racers.Racer;

public class ArenaFactory {
	private static String GAME_PACKAGE = "game";
	private static String GAME_PACKAGE_DIR = "src/game";
	private static ArenaFactory instance;

	public static ArenaFactory getInstance() {
		if (instance == null) {
			instance = new ArenaFactory();
		}
		return instance;
	}

	private List<Class<?>> classList;
	private List<Class<?>> racersList;
	private List<Class<?>> arenasList;

	private ArenaFactory() {
		try {
			this.classList = this.loadClasses(new File(GAME_PACKAGE_DIR), GAME_PACKAGE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.arenasList = loadArenas();
		this.racersList = loadRacers();
	}

	public List<Class<?>> getArenasList() {
		return arenasList;
	}

	public List<String> getArenasNamesList() {
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.arenasList) {
			String s = c.getName();
			list.add(s.substring(s.lastIndexOf('.') + 1));
		}
		return list;
	}

	public List<Class<?>> getRacersList() {
		return racersList;
	}

	public List<String> getRacersNamesList() {
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.racersList) {
			String s = c.getName();
			list.add(s.substring(s.lastIndexOf('.') + 1));
		}
		return list;
	}

	private List<Class<?>> loadArenas() {
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> cls : classList) {
			if (Arena.class.isAssignableFrom(cls) && (Modifier.isAbstract(cls.getModifiers()) == false)) {

				list.add(cls);
			}
		}
		return list;
	}

	private List<Class<?>> loadClasses(File directory, String packageName)
			throws ClassNotFoundException, FileNotFoundException {
		List<Class<?>> list = new ArrayList<Class<?>>();

		if (!directory.exists()) {
			throw new FileNotFoundException();
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				list.addAll(loadClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".java")) {
				list.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 5)));
			}
		}
		return list;
	}

	private List<Class<?>> loadRacers() {
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> cls : classList) {
			if (Racer.class.isAssignableFrom(cls) && (Modifier.isAbstract(cls.getModifiers()) == false)) {
				list.add(cls);
			}
		}
		return list;
	}

}






















//
//package factory;
//
//import game.arenas.Arena;
//import game.arenas.air.AerialArena;
//import game.arenas.land.LandArena;
//import game.arenas.naval.NavalArena;
//
//
//
//public class ArenaFactory {
//	static String arenaTypes;
////	static int Deflength = 800;
////	static int DefnumRacer = 6;
//	//private static Arena arena = null;
//	private static RaceBuilder builder = RaceBuilder.getInstance();
//
//	
//	  /**
//     * Creates an arena based on the provided arena type.
//     *
//     * @param arenaType the type of the arena to create (LAND, AIR, or NAVY)
//     * @return the created arena
//     * @throws IllegalArgumentException if an invalid arena type is provided
//     */
//	
//    public static Arena createArena(String arenaType,int Deflength,int DefnumRacer) {
//        switch (arenaType) {
//            case "LAND":
//            	arenaTypes = "Land Arena";
//            	try {
//            		return builder.buildArena("game.arenas.land.LandArena", Deflength, DefnumRacer);
//            	} catch (Exception ex) {
//    				System.out.println(ex);
//    			}
//                //return new LandArena(Deflength, DefnumRacer);
//            case "AIR":
//            	arenaTypes = "Aerial Arena";
//            	try {
//            		return builder.buildArena("game.arenas.air.AerialArena", Deflength, DefnumRacer);
//            	} catch (Exception ex) {
//    				System.out.println(ex);
//    			}
//                //return new AerialArena(Deflength, DefnumRacer);
//            case "NAVY":
//            	arenaTypes = "Naval Arena";
//            	try {
//            		return builder.buildArena("game.arenas.naval.NavalArena", Deflength, DefnumRacer);
//            	} catch (Exception ex) {
//    				System.out.println(ex);
//    			}
//              //  return new NavalArena(Deflength, DefnumRacer);
//            default:
//                throw new IllegalArgumentException("Invalid arena type: " + arenaType);
//        }
//       
//    }
//    
//    /**
//     * Prints the details of the created arena.
//     */
////    public void PrintArena() {
////		System.out.println("You have successfully created your arena!" + "\nArena Details:" + "\nType:" + arenaTypes + "\nLength Arena:" + Deflength + "\nNumber of Racer:" + DefnumRacer);
////	}
//    
//    /**
//     * Main method to create a LandArena and print its details.
//     *
//     * @param args the command-line arguments (not used)
//     */
////    public static void main(String[] args) {
////		ArenaFactory obj = new ArenaFactory();
////		obj.createArena("LAND");
////		obj.PrintArena();
////		
////
////	}
//}
//
//
//
//	
//
//
