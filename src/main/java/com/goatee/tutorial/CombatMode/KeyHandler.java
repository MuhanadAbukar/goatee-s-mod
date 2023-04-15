package com.goatee.tutorial.CombatMode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler {
	public static KeyBinding CombatMode;

	public static KeyBinding[] keyBindings = Minecraft.getMinecraft().gameSettings.keyBindings;
	public static int keyCode = Keyboard.getEventKey();

	public static ArrayList<Integer> mykeys;
	public static ArrayList<Integer> numpadKeys;
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
		for (KeyBinding bind : keyBindings) {
			if (mykeys.contains(bind.getKeyCode())) {
				//System.out.println("Unbinded " + bind.getKeyCode() + " " + bind.getKeyDescription());
				bind.setKeyCode(0);
				wasRemoved.add(bind);
				unpressKey(bind);
			//	System.out.println("saved code for " + bind.getKeyDescription() + " is "
						//+ savedBinds.get(bind.getKeyDescription()));
			}
		}
		KeyBinding.resetKeyBindingArrayAndHash();
		
	}

	public static void reAddSavedKeys() {
		if (!wasRemoved.isEmpty()) {
			for (KeyBinding bind : wasRemoved) {
				bind.setKeyCode(savedBinds.get(bind.getKeyDescription()));
				//System.out.println("readded code " + bind.getKeyCode() + " for " + bind.getKeyDescription());
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
	}

	static {
		numpadKeys = new ArrayList<>(Arrays.asList(70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83));
		otherkeys = new ArrayList<>(Arrays.asList(15, 16, 43, 51, 52, 54, 157, 184));

		mykeys = new ArrayList<>();
		mykeys.addAll(numpadKeys);
		mykeys.addAll(otherkeys);
		savedBinds = new HashMap<String, Integer>();
		wasRemoved = new ArrayList<>();

		CombatMode = new KeyBinding("Toggle Combat Mode", 41, "JinRyuu's JRMCore");

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
