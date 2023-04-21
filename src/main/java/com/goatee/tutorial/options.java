package com.goatee.tutorial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.goatee.tutorial.CombatMode.KeyHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class options {
	private static final Logger logger = LogManager.getLogger();
	public static String mcabpath = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
	public static File optn = new File(mcabpath, "optionsgoat.txt");
	public static HashMap<String, Integer> keys = new HashMap<>();
	public static Double flySpeed;

	public static File createOptions() {
		logger.info("optionsgoat created!");
		return createFile(optn, false);

	}

	public static void saveOptions() {
		saveFile(optn);
	}

	public static void loadOptions() {
		String s = "";
		try {
			if (!optn.exists())
				createOptions();
			else
				updateOptions();
			BufferedReader bf = new BufferedReader(new FileReader(optn));
			while ((s = bf.readLine()) != null) {
				String[] a = s.split(":");
				for (KeyBinding bind : KeyHandler.CMKeysList) {
					String l = bind.getKeyDescription();
					if (a[0].equals("key_" + l)) {
						keys.put(l, Integer.parseInt(a[1]));
					}
				}
				if (a[0].equals("flySpeed")) {
					flySpeed = Double.parseDouble(a[1]);
				}
			}
			bf.close();
		} catch (Exception e) {
			logger.warn("Failed to load goat options, " + e);
			logger.warn("Skipping bad goat option : " + s);
			e.printStackTrace();
		}

	}

	private static void updateOptions() {
		String s = "";
		try {
			File temp = createTempOptions();
			BufferedReader tempBF = new BufferedReader(new FileReader(temp));
			BufferedReader BF = new BufferedReader(new FileReader(optn));
			PrintWriter PW = new PrintWriter(new BufferedWriter(new FileWriter(optn, true)));
			while ((s = tempBF.readLine()) != null) {
				if (!doesLineExist(optn, s.split(":")[0])) {
					PW.println(s);
					logger.info("appended " + s);
				}

			}
			tempBF.close();
			BF.close();
			PW.close();
			temp.delete();
		} catch (Exception e) {
			logger.warn("Failed at creating temp option, " + e);
			logger.warn("Skipping bad goat option : " + s);
		}

	}

	public static File createTempOptions() {
		return createFile(new File(mcabpath, "optionsgoat_temp.txt"), false);
	}

	public static File createFile(File file, boolean bo) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file, bo));
			for (KeyBinding bind : KeyHandler.CMKeysList) {
				String l = bind.getKeyDescription();
				pw.println("key_" + l + ":" + 0);
			}
			pw.println("flySpeed:" + 1.0);
			pw.println("nagareru ase mo kirei na aqua:" + 1.0);
			pw.println("rubi wo kakushita kono mabuta:" + 1.0);
			pw.println("utaiodorimau watashi ha maria:" + 1.0);
			pw.println("sou uso ha tobikiri no ai da:" + 1.0);
			pw.println("asdas:");
			pw.print("asdasdas");
			pw.close();
			return file;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;

	}

	public static File saveFile(File file) {
		try {

			PrintWriter pw = new PrintWriter(new FileWriter(file));
			for (KeyBinding bind : KeyHandler.CMKeysList) {
				String l = bind.getKeyDescription();
				pw.println("key_" + l + ":" + keys.get(l));
			}
			pw.println("flySpeed:" + flySpeed);
			pw.close();
			return file;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;

	}

	public static boolean doesLineExist(File file, String line) {
		boolean x = (readFromFile(file, line) != null) ? true : false;
		logger.info(x + " contains " + line);
		return x;
	}

	public static String readFromFile(File file, String lineToRead) {
		String s = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((s = reader.readLine()) != null) {
				if (s.contains(lineToRead)) {
					reader.close();
					return s;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
