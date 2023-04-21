package com.goatee.tutorial.Events;

import java.awt.Color;
import java.util.HashMap;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.goatee.tutorial.options;
import com.goatee.tutorial.CombatMode.GUIKeyBindings;
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

@SideOnly(Side.CLIENT)
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
	@SideOnly(Side.CLIENT)
	public static boolean isMenuDisabled = false;
	@SideOnly(Side.CLIENT)
	public static boolean closeScreen;
	@SideOnly(Side.CLIENT)
	public static boolean isFlightGravityDisabled;
	@SideOnly(Side.CLIENT)
	Minecraft mc = Minecraft.getMinecraft();

	@SideOnly(Side.CLIENT)
	// EntityClientPlayerMP p = mc.thePlayer;

	@SubscribeEvent
	public void onKeyPress(KeyInputEvent e) {
		if (Minecraft.getMinecraft().thePlayer != null) {
			EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
			handleFlightKey(e, p);
			handleCombatModeKey(e, p);
			openCMGui(e, p);
			if (isCombatModeEnabled) {
				handleIT(e, p);
			}
		}
	}

	private void openCMGui(KeyInputEvent e, EntityClientPlayerMP p) {
		if (KeyHandler.CMGui.getIsKeyPressed()) {
			mc.displayGuiScreen(new GUIKeyBindings());
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

	public void handleIT(KeyInputEvent e, EntityClientPlayerMP p) {
		if (KeyHandler.InstantTransmission.getIsKeyPressed()) {
			p.addChatMessage(new ChatComponentText("§a§lInstant Transmission!"));
		}
	}

	public void handleCombatModeKey(KeyInputEvent e, EntityClientPlayerMP p) {
		if (KeyHandler.CombatMode.getIsKeyPressed() && !isCombatModeEnabled) {
			KeyHandler.saveAllKeybinds();
			KeyHandler.bindCombatKeys();
			isCombatModeEnabled = true;
			PacketRegistry.tellServer("CombatMode:" + true);
			p.addChatMessage(new ChatComponentText("§lCombat Mode has been enabled!"));
		} else if (KeyHandler.CombatMode.getIsKeyPressed() && isCombatModeEnabled) {
			KeyHandler.unbindCombatKeys();
			KeyHandler.reAddSavedKeys();
			isCombatModeEnabled = false;
			PacketRegistry.tellServer("CombatMode:" + false);
			p.addChatMessage(new ChatComponentText("§lCombat Mode has been disabled!"));
			// Runtime.getRuntime().addShutdownHook();
		}
	}

	@SubscribeEvent
	public void onLogOut(ClientDisconnectionFromServerEvent e) {
		EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
		handleCombatModeDisconnect(e);
		options.saveOptions();
		onGroundLT.remove(p.getUniqueID());
		closeScreen = false;
		loggedIn = false;
		Runtime r = Runtime.getRuntime();
		r.addShutdownHook(new ShutDownHookThread());
	}

	public void handleCombatModeDisconnect(ClientDisconnectionFromServerEvent e) {
		if (isCombatModeEnabled) {
			KeyHandler.unbindCombatKeys();
			KeyHandler.reAddSavedKeys();
			isCombatModeEnabled = false;
			PacketRegistry.tellServer("CombatMode:" + false);

		}
	}

	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.phase.equals(Phase.START)) {
			EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
			if (p != null) {
				handleLogin(e, p);
				handleFlightTick(e, p);
				handleIsMovementDisabledTick(e, p);
				handleIsSprintDisabledTick(e, p);
				handleisMenuDisabled(e, p);
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
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
			KeyBinding.setKeyBindState(JRMCoreKeyHandler.KiFlight.getKeyCode(), false);

			p.setSprinting(false);
		}
	}

	public void handleIsSprintDisabledTick(PlayerTickEvent e, EntityClientPlayerMP p) {
		if (isSprintDisabled) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
			p.setSprinting(false);
		}

	}

	public void handleisMenuDisabled(PlayerTickEvent e, EntityClientPlayerMP p) {
		if (isMenuDisabled) {
			KeyBinding.setKeyBindState(JRMCoreKeyHandler.DS.getKeyCode(), false);
			if (!closeScreen) {
				p.closeScreen();
				closeScreen = true;
			}
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

class ShutDownHookThread extends Thread {
	public void run() {
		KeyHandler.unbindCombatKeys();
		KeyHandler.reAddSavedKeys();
		System.out.println("Yo, exiting thread " + Thread.currentThread().getName());
	}
}
