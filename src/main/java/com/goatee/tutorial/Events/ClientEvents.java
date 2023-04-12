package com.goatee.tutorial.Events;

import com.goatee.tutorial.CombatMode.KeyHandler;
import com.goatee.tutorial.packets.PacketRegistry;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ClientEvents {
	@SideOnly(Side.CLIENT)
	public static boolean isOnGroundTriggered = true;
	@SideOnly(Side.CLIENT)
	public static boolean isSprintDisabled = false;
	@SideOnly(Side.CLIENT)
	public static boolean isMovementDisabled = false;
	public boolean idk = false;

	// @SubscribeEvent
	public void eventKey(PlayerTickEvent e) {

		if (idk == false) {
			System.out.println("s");

			idk = KeyHandler.unbindPlayerKeys();
		}

	}

	@SubscribeEvent
	public void onKeyPress(KeyInputEvent e) {
		if (Minecraft.getMinecraft().gameSettings.keyBindDrop.getIsKeyPressed()) {
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindDrop.getKeyCode(), false);
			System.out.println("dropped");
		}
		handleFlightKeyInput(e);

	}

	/*
	 * there's a DBC bug in where when flying, if you land due to flight gravity,
	 * not by pressing F, you are still flying as DBCKiTech.floating doesn't get set
	 * to false. This fixes that
	 */
	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.phase.equals(Phase.START)) {

			// checks if player is on ground once, then stops. This "once" resets once
			// player is no longer on ground, then checks again after player lands. If on
			// ground, disables DBC flight and tells server isFlying is false.

			handleFlightTick(e);

		}
	}

	public void handleFlightKeyInput(KeyInputEvent e) {
		if (JRMCoreKeyHandler.KiFlight.getIsKeyPressed() && JRMCoreKeyHandler.KiFlight.isPressed()) {
			System.out.println("flight key code is " + JRMCoreKeyHandler.KiFlight.getKeyCode());
			// sends to server that player isFlying on flight keybind press.
			if (!DBCKiTech.floating) {
				PacketRegistry.tellServer("1");
			}
			if (DBCKiTech.floating) {
				PacketRegistry.tellServer("2");
			}

		}
	}

	public void handleFlightTick(PlayerTickEvent e) {
		if (e.player.onGround && !DBCKiTech.floating && !isOnGroundTriggered) {
			isOnGroundTriggered = true;
			PacketRegistry.tellServer("2");
		}
		if (!e.player.onGround) {
			isOnGroundTriggered = false;
		}
	}

	public void handleMovementDisabledTick(PlayerTickEvent e) {
		if (isMovementDisabled) {
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
			e.player.setSprinting(false);
		}
	}

	public void handleSprintDisabledTick(PlayerTickEvent e) {
		if (isSprintDisabled) {
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
			e.player.setSprinting(false);

		}
	}

}
