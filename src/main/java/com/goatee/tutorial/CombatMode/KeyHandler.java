package com.goatee.tutorial.CombatMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler {

	private static Field keybindArray = null;
	@SuppressWarnings({ "unused", "rawtypes" })
	private static List savedkeybindArray = new ArrayList<>();

	public static ArrayList<Integer> mykeys;

	public static int keyCode = Keyboard.getEventKey();

	@SuppressWarnings("rawtypes")
	public static void savePlayerKeybinds() {

		try {
			keybindArray = KeyBinding.class.getDeclaredField("keybindArray");
			keybindArray.setAccessible(true);
			savedkeybindArray = (List) keybindArray.get(null);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean unbindPlayerKeys() {
		System.out.println("e");
		try {
			keybindArray = KeyBinding.class.getDeclaredField("keybindArray");
			keybindArray.setAccessible(true);
			int added = 0;
			@SuppressWarnings("unchecked")
			List<KeyBinding> x = (List<KeyBinding>) keybindArray.get(null);
			for (KeyBinding bind : x) {
				if (mykeys.contains(bind.getKeyCode())) {
					System.out.println("Unbinded " + bind.getKeyCode() + " " + bind.getKeyDescription());
					bind.setKeyCode(0);
					added++;
					if (added == mykeys.size()) {
						KeyBinding.resetKeyBindingArrayAndHash();
						return true;
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Keyboard.isCreated() && Keyboard.getEventKeyState()) {

			if (mykeys.contains(keyCode)) {
				System.out.println(keyCode);

			}
		}

		return false;
	}

	public static void reAddSavedKeys() {

		try {
			keybindArray = KeyBinding.class.getDeclaredField("keybindArray");
			keybindArray.setAccessible(true);
			keybindArray.set(null, savedkeybindArray);
			KeyBinding.resetKeyBindingArrayAndHash();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static {
		mykeys = new ArrayList<>(Arrays.asList(16, 17, 18, 19));
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
