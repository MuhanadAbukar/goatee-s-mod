package com.goatee.tutorial.Events;

import java.awt.Color;
import java.util.HashMap;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.goatee.tutorial.CombatMode.KeyHandler;
import com.goatee.tutorial.packets.PacketRegistry;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;

public class ClientEvents {
	@SideOnly(Side.CLIENT)
	public static HashMap<UUID, Boolean> onGroundLT = new HashMap<UUID, Boolean>();
	@SideOnly(Side.CLIENT)
	public static boolean isSprintDisabled = false;
	@SideOnly(Side.CLIENT)
	public static boolean isMovementDisabled = false;
	@SideOnly(Side.CLIENT)
	public static boolean isCombatModeEnabled = false;
	@SideOnly(Side.CLIENT)
	public static boolean isBlind = false;
	@SideOnly(Side.CLIENT)
	public static boolean loggedIn = false;

	@SubscribeEvent
	public void onKeyPress(KeyInputEvent e) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
			handleFlightKey(e, p);
			handleCombatModeKey(e, p);
		}
	}

	public void handleFlightKey(KeyInputEvent e, EntityClientPlayerMP p) {
		if (JRMCoreKeyHandler.KiFlight.getIsKeyPressed()) {
			if (!DBCKiTech.floating) {
				PacketRegistry.tellServer("1");
			}
			if (DBCKiTech.floating) {
				PacketRegistry.tellServer("2");
			}
		}
	}

	public void handleCombatModeKey(KeyInputEvent e, EntityClientPlayerMP p) {
		if (KeyHandler.CombatMode.getIsKeyPressed() && !isCombatModeEnabled) {
			KeyHandler.saveAllKeybinds();
			KeyHandler.unbindNonCombatKeys();
			ClientEvents.isCombatModeEnabled = true;
			PacketRegistry.tellServer("CombatMode:" + true);
			p.addChatMessage(new ChatComponentText("§lCombat Mode has been enabled!"));
		} else if (KeyHandler.CombatMode.getIsKeyPressed() && isCombatModeEnabled) {
			KeyHandler.reAddSavedKeys();
			ClientEvents.isCombatModeEnabled = false;
			PacketRegistry.tellServer("CombatMode:" + false);
			p.addChatMessage(new ChatComponentText("§lCombat Mode has been disabled!"));
		}
	}

	@SubscribeEvent
	public void onLogOut(ClientDisconnectionFromServerEvent e) {
		EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
		handleCombatModeDisconnect(e);
		ClientEvents.onGroundLT.remove(p.getUniqueID());
		loggedIn = false;
	}

	public void handleCombatModeDisconnect(ClientDisconnectionFromServerEvent e) {
		if (isCombatModeEnabled) {
			KeyHandler.reAddSavedKeys();
			ClientEvents.isCombatModeEnabled = false;
			PacketRegistry.tellServer("CombatMode:" + false);

		}
	}

	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.phase.equals(Phase.START)) {
			if (Minecraft.getMinecraft().thePlayer != null) {
				EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
				handleLogin(e, p);
				handleFlightTick(e, p);
				handleIsMovementDisabledTick(e, p);
				handleIsSprintDisabledTick(e, p);
			}
		}
	}

	public void handleLogin(PlayerTickEvent e, EntityClientPlayerMP p) {
		if (!loggedIn) {
			onGroundLT.put(p.getUniqueID(), p.onGround);
			loggedIn = true;

		}
	}

	public void handleFlightTick(PlayerTickEvent e, EntityClientPlayerMP p) {
		if (p.onGround != onGroundLT.get(p.getUniqueID())) {
			onGroundLT.replace(p.getUniqueID(), p.onGround);
			if (p.onGround) {
				PacketRegistry.tellServer("2");

			}
		}

	}

	public void handleIsMovementDisabledTick(PlayerTickEvent e, EntityClientPlayerMP p) {
		if (isMovementDisabled) {
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), false);
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
			KeyBinding.setKeyBindState(JRMCoreKeyHandler.KiFlight.getKeyCode(), false);

			p.setSprinting(false);
		}
	}

	public void handleIsSprintDisabledTick(PlayerTickEvent e, EntityClientPlayerMP p) {
		if (isSprintDisabled) {
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
			p.setSprinting(false);
		}
	}

	@SubscribeEvent
	public void EntityViewRenderEvent(FogDensity event) {
		if (event.entity instanceof EntityPlayer && isBlind) {
			event.setCanceled(true);
			event.density = 0.5f;
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onFogColorsEvent(EntityViewRenderEvent.FogColors event) {
		if (event.entity instanceof EntityPlayer && isBlind) {
			event.red = Color.RED.getRGB();
			event.green = Color.GREEN.getRGB();
			event.blue = 0;
		}
	}

}
