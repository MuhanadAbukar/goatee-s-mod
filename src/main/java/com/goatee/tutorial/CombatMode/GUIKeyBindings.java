package com.goatee.tutorial.CombatMode;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.lwjgl.input.Mouse;

import com.goatee.tutorial.options;
import com.goatee.tutorial.Events.ClientEvents;
import com.goatee.tutorial.gui.utils.GuiCustomScroll;
import com.goatee.tutorial.gui.utils.GuiNPCInterface;
import com.goatee.tutorial.gui.utils.GuiNpcButton;
import com.goatee.tutorial.gui.utils.GuiNpcLabel;
import com.goatee.tutorial.gui.utils.GuiNpcTextField;
import com.goatee.tutorial.gui.utils.ICustomScrollListener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class GUIKeyBindings extends GuiNPCInterface implements ICustomScrollListener {

	int y = this.guiTop + 4;
	// public GuiNpcButton b1 = new GuiNpcButton(0, 100, 75, "Close");
	private GuiCustomScroll scroll;
	private String selected;
	private KeyBinding selectedKey;
	boolean stop = false;
	int mch = Minecraft.getMinecraft().displayHeight;
	int mcw = Minecraft.getMinecraft().displayWidth;
	private GuiNpcButton btnChangeKeyBinding;
	boolean btnChangeClicked = false;

	@Override
	public void initGui() {
		this.closeOnE = false;
		super.initGui();
		scroll = new GuiCustomScroll(this, 0);
		scroll.setSize(200, 180);
		scroll.guiLeft = guiLeft + 4;
		scroll.guiTop = guiTop + 16;
		addScroll(scroll);
		scroll.setList(searchForKeys());
		this.btnChangeKeyBinding = new GuiNpcButton(0, scroll.guiLeft + 125, scroll.guiTop + 181, 75, 20, "Select Key");
		addButton(btnChangeKeyBinding);
		getButton(0).setEnabled(selected != null);
		addLabel(new GuiNpcLabel(0, "", scroll.guiLeft, scroll.guiTop + 183 + 18 + 5));
		getLabel(0).enabled = false;
		// getTextField(0).

		this.addTextField(new GuiNpcTextField(0, this, this.fontRendererObj, scroll.guiLeft + 1, scroll.guiTop + 183, 120, 18, ""));

	}

	public void keyTyped(char c, int i) {
		if (btnChangeClicked && i == 1)
			closeOnEsc = false;
		super.keyTyped(c, i);
		System.out.println("c is " + c + ", i is" + i);
		if (btnChangeClicked) {
			if (i == 1) {
				getButton(0).displayString = EnumChatFormatting.GRAY + GameSettings.getKeyDisplayString(i);
				options.keys.replace(selectedKey.getKeyDescription(), i);
				options.saveOptions();
				getLabel(0).enabled = true;
				getLabel(0).label = EnumChatFormatting.GRAY + "Removed keybind!";
				KeyHandler.doGuiConfig();
			} else if (options.keys.get(selectedKey.getKeyDescription()) == i) {
				getButton(0).displayString = GameSettings.getKeyDisplayString(i);
			} else if (isKeyOverlapping(i)) {
				getButton(0).displayString = EnumChatFormatting.RED + GameSettings.getKeyDisplayString(i);
			} else {
				getButton(0).displayString = EnumChatFormatting.GREEN + GameSettings.getKeyDisplayString(i);
				getLabel(0).enabled = true;
				getLabel(0).label = EnumChatFormatting.GREEN + "Successfully changed keybind for " + selectedKey.getKeyDescription() + "!";
				options.keys.replace(selectedKey.getKeyDescription(), i);
				options.saveOptions();
				KeyHandler.doGuiConfig();
			}
			btnChangeClicked = false;
			closeOnEsc = true;

		}
	}

	private boolean isKeyOverlapping(int i) {
		if (options.keys.get(selectedKey.getKeyDescription()) != i) {
			for (Entry<String, Integer> entry : options.keys.entrySet()) {
				if (entry.getValue() == i && entry.getKey() != selectedKey.getKeyDescription()) {
					getLabel(0).enabled = true;
					getLabel(0).label = EnumChatFormatting.RED + "Keybind overlaps with " + entry.getKey() + "!";
					return true;
				}
			}
			for (KeyBinding bind : KeyHandler.mvmntKeys) {
				if (bind.getKeyCode() == i) {
					getLabel(0).enabled = true;
					getLabel(0).label = EnumChatFormatting.RED + "Keybind overlaps with Movement Key " + I18n.format(bind.getKeyDescription(), new Object[0]) + "!";
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void customScrollClicked(int i, int j, int k, GuiCustomScroll scroll) {
		this.selected = scroll.getSelected();
		System.out.println(selected);
		this.selectedKey = KeyHandler.CMKeysMap.get(selected);
		System.out.println(selectedKey);
		getButton(0).setEnabled(selected != null);
		getButton(0).displayString = "Select Key";
		getLabel(0).enabled = false;
		btnChangeClicked = false;

	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			int keycode = options.keys.get(selectedKey.getKeyDescription());
			if (!btnChangeClicked) {
				btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + GameSettings.getKeyDisplayString(keycode) + EnumChatFormatting.WHITE + " <";
				btnChangeClicked = true;
				getLabel(0).enabled = false;
			} else {
				btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + GameSettings.getKeyDisplayString(keycode);
				btnChangeClicked = false;
			}
			break;
		case 1:
			mc.thePlayer.closeScreen();
			break;
		}
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		scroll.drawScreen(i, j, f);
		mch = Minecraft.getMinecraft().displayHeight;
		mcw = Minecraft.getMinecraft().displayWidth;
		if (Mouse.isButtonDown(0) && !stop) {
			System.out.println("Mouse x = " + i + ", mouse y = " + j);
			System.out.println("scroll height = " + scroll.height + ", scroll width = " + scroll.width);
			System.out.println("ySize = " + xSize + ", ySize = " + ySize);
			System.out.println("height = " + height + ", width = " + width);
			System.out.println(mch + " x " + mcw);
			System.out.println(Minecraft.getMinecraft().mcDataDir.getAbsolutePath());

			stop = true;
		}
		if (Mouse.isButtonDown(1) && stop) {
			System.out.println("--------------");
			stop = false;
		}
	}

	private List<String> searchForKeys() {
		List<String> list = new ArrayList<>();
		for (KeyBinding bind : KeyHandler.CMKeysList) {
			if (!list.contains(bind.getKeyDescription())) {
				list.add(bind.getKeyDescription());
			}
		}
		return list;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	public void save() {
	}

	public static double screenX(double n) {
		ScaledResolution screen = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		return screen.getScaledWidth_double() * n;
	}

	public static double screenY(double n) {
		ScaledResolution screen = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		return screen.getScaledHeight_double() * n;
	}

}
