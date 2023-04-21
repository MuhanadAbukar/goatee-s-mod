package com.goatee.tutorial.CombatMode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import com.goatee.tutorial.options;
import com.goatee.tutorial.Events.ClientEvents;

import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler {
	public static KeyBinding CombatMode;
	public static KeyBinding CMGui;
	public static KeyBinding InstantTransmission;
	public static KeyBinding Target;
	public static KeyBinding CycleTarLeft;
	public static KeyBinding CycleTarRight;
	public static ArrayList<KeyBinding> CMKeysList;
	public static HashMap<String, KeyBinding> CMKeysMap = new HashMap<String, KeyBinding>();

	public static GameSettings gs = Minecraft.getMinecraft().gameSettings;
	public static KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;
	public static int keyCode = Keyboard.getEventKey();

	public static ArrayList<Integer> mykeys;
	public static ArrayList<Integer> numpadKeys;
	public static ArrayList<KeyBinding> mvmntKeys;

	public static ArrayList<Integer> otherkeys;

	public static HashMap<String, Integer> savedBinds;
	public static ArrayList<KeyBinding> wasRemoved;

	public static void saveAllKeybinds() {
		KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;
		for (int i = 0; i < keyBindings.length; i++) {
			savedBinds.put(keyBindings[i].getKeyDescription(), keyBindings[i].getKeyCode());
		}

	}

	public static void unbindNonCombatKeys() {
		for (int key : mykeys) {
			unbindKey(key);
		}

	}

	public static void doGuiConfig() {
		if (ClientEvents.isCombatModeEnabled) {
			unbindCombatKeys();
			reAddSavedKeys();
			saveAllKeybinds();
			bindCombatKeys();
		}
	}

	public static void unbindCombatKeys() {
		for (KeyBinding bind : CMKeysList) {
			bind.setKeyCode(0);
		}
		KeyBinding.resetKeyBindingArrayAndHash();
	}

	public static void unbindKey(int key) {
		for (KeyBinding bind : keyBindings) {
			if (bind.getKeyCode() == key) {
				bind.setKeyCode(0);
				wasRemoved.add(bind);
			}
		}
		KeyBinding.resetKeyBindingArrayAndHash();
	}

	public static void bindCombatKeys() {
		for (KeyBinding bind : CMKeysList) {
			int key = options.keys.get(bind.getKeyDescription());
			unbindKey(key);
			bind.setKeyCode(key);
		}
		KeyBinding.resetKeyBindingArrayAndHash();
	}

	public static void reAddSavedKeys() {
		if (!wasRemoved.isEmpty()) {
			for (KeyBinding bind : wasRemoved) {
				bind.setKeyCode(savedBinds.get(bind.getKeyDescription()));
				// System.out.println("readded code " + bind.getKeyCode() + " for " + bind.getKeyDescription());
			}
			wasRemoved.clear();
			savedBinds.clear();
			KeyBinding.resetKeyBindingArrayAndHash();
		}

	}

	public static void unpressKey(KeyBinding bind) {
		try {
			Method unpressKey = KeyBinding.class.getDeclaredMethod("unpressKey");
			unpressKey.setAccessible(true);
			unpressKey.invoke(bind);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void registerKeys() {
		ClientRegistry.registerKeyBinding(CombatMode);
		ClientRegistry.registerKeyBinding(CMGui);
		AddKeysToMap();

	}

	private static void AddKeysToMap() {
		for (KeyBinding bind : CMKeysList) {
			if (!CMKeysMap.containsKey(bind.getKeyDescription())) {
				CMKeysMap.put(bind.getKeyDescription(), bind);
			}
			ClientRegistry.registerKeyBinding(bind);

		}
	}

	static {
		numpadKeys = new ArrayList<>(Arrays.asList(70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83));
		otherkeys = new ArrayList<>(Arrays.asList(15, 16, 43, 51, 52, 54, 157, 184));
		mvmntKeys = new ArrayList<>(Arrays.asList(gs.keyBindForward, gs.keyBindBack, gs.keyBindLeft, gs.keyBindRight, gs.keyBindJump, gs.keyBindSneak, gs.keyBindSprint, JRMCoreKeyHandler.KiFlight));

		mykeys = new ArrayList<>();
		mykeys.addAll(numpadKeys);
		mykeys.addAll(otherkeys);
		savedBinds = new HashMap<String, Integer>();
		wasRemoved = new ArrayList<>();

		CombatMode = new KeyBinding("Toggle Combat Mode", 41, "Combat Mode Settings");
		CMGui = new KeyBinding("Open GUI", 98, "Combat Mode Settings");
		CycleTarLeft = new KeyBinding("Cycle Target (Left)", 0, "Combat Mode");
		CycleTarRight = new KeyBinding("Cycle Target (Right)", 0, "Combat Mode");
		InstantTransmission = new KeyBinding("Instant Transmission", 0, "Combat Mode");
		Target = new KeyBinding("Target Enemy", 0, "Combat Mode");

		CMKeysList = new ArrayList<>(Arrays.asList(InstantTransmission, Target, CycleTarLeft, CycleTarRight));

	}

}
//	try
//	{
//		if (Keyboard.isCreated() && Keyboard.getEventKeyState()) {
//
//			if (mykeys.contains(keyCode)) {
//				keybindArray = KeyBinding.class.getDeclaredField("keybindArray");
//				keybindArray.setAccessible(true);
//				@SuppressWarnings("unchecked")
//				List<KeyBinding> x = (List<KeyBinding>) keybindArray.get(null);
//				for (KeyBinding bind : x) {
//					if (bind.getKeyCode() == keyCode) {
//						Method j = KeyBinding.class.getDeclaredMethod("unpressKey");
//						j.setAccessible(true);
//						j.invoke(bind);
//						KeyBinding.setKeyBindState(keyCode, false);
//						System.out.println("yo");
//
//					}
//				}
//
//			}
//		}
//	}catch(
//	Exception q)
//	{
//		q.printStackTrace();
//	}

//try {
//keybindArray = KeyBinding.class.getDeclaredField("keybindArray");
//keybindArray.setAccessible(true);
//savedkeybindArray = (List) keybindArray.get(null);
//for (KeyBinding x : savedkeybindArray) {
//	System.out.println(x.getKeyDescription());
//
//}
//System.out.println("saved binds");
//
//} catch (Exception e) {
//e.printStackTrace();
//}

//System.out.println("mc keybinds " + Minecraft.getMinecraft().gameSettings.keyBindings[i].getKeyDescription()
//		+ " " + Minecraft.getMinecraft().gameSettings.keyBindings[i]);
//System.out.println("mc keybinds " + keyBindings[i].getKeyDescription() + " " + keyBindings[i] + " clone");
//System.out.println("array " + Minecraft.getMinecraft().gameSettings.keyBindings);
//System.out.println("array " + keyBindings + " clone");
