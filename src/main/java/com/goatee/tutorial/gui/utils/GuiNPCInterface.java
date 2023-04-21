package com.goatee.tutorial.gui.utils;

import java.awt.Toolkit;
import java.awt.Window;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public abstract class GuiNPCInterface extends GuiScreen {
	public static Window AWTWindow;
	public EntityClientPlayerMP player;
	public boolean drawDefaultBackground = true;
	private HashMap<Integer, GuiNpcButton> buttons = new HashMap<Integer, GuiNpcButton>();
	private HashMap<Integer, GuiMenuTopButton> topbuttons = new HashMap<Integer, GuiMenuTopButton>();
	private HashMap<Integer, GuiMenuSideButton> sidebuttons = new HashMap<Integer, GuiMenuSideButton>();
	private HashMap<Integer, GuiNpcTextField> textfields = new HashMap<Integer, GuiNpcTextField>();
	private HashMap<Integer, GuiNpcLabel> labels = new HashMap<Integer, GuiNpcLabel>();
	private HashMap<Integer, GuiCustomScroll> scrolls = new HashMap<Integer, GuiCustomScroll>();
	private HashMap<Integer, GuiNpcSlider> sliders = new HashMap<Integer, GuiNpcSlider>();
	private HashMap<Integer, GuiScreen> extra = new HashMap<Integer, GuiScreen>();
	public String title;
	private ResourceLocation background = null;
	public boolean closeOnEsc = true;
	public boolean closeOnE = true;
	public int guiLeft, guiTop, xSize, ySize;
	private SubGuiInterface subgui;
	public int mouseX, mouseY;

	public float bgScale = 1;
	public float bgScaleX = 1;
	public float bgScaleY = 1;
	public float bgScaleZ = 1;

	public GuiNPCInterface() {
		this.player = Minecraft.getMinecraft().thePlayer;
		title = "";
		xSize = 200;
		ySize = 222;
	}

	public void setBackground(String texture) {
		background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
	}

	public ResourceLocation getResource(String texture) {
		return new ResourceLocation("customnpcs", "textures/gui/" + texture);
	}

	@Override
	public void initGui() {
		super.initGui();
		GuiNpcTextField.unfocus();
		if (subgui != null) {
			subgui.setWorldAndResolution(mc, width, height);
			subgui.initGui();
		}
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		buttonList.clear();
		labels.clear();
		textfields.clear();
		buttons.clear();
		sidebuttons.clear();
		topbuttons.clear();
		scrolls.clear();
		sliders.clear();
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	public void updateScreen() {
		if (subgui != null)
			subgui.updateScreen();
		else {
			for (GuiNpcTextField tf : textfields.values()) {
				if (tf.enabled)
					tf.updateCursorCounter();
			}
			super.updateScreen();
		}
	}

	public void addExtra(GuiHoverText gui) {
		gui.setWorldAndResolution(mc, 350, 250);
		extra.put(gui.id, gui);
	}

	public void mouseClicked(int i, int j, int k) {
		if (AWTWindow != null)
			return;
		if (subgui != null)
			subgui.mouseClicked(i, j, k);
		else {
			for (GuiNpcTextField tf : new ArrayList<GuiNpcTextField>(textfields.values()))
				if (tf.enabled)
					tf.mouseClicked(i, j, k);
			if (k == 0) {
				for (GuiCustomScroll scroll : new ArrayList<GuiCustomScroll>(scrolls.values())) {
					scroll.mouseClicked(i, j, k);
				}
			}
			mouseEvent(i, j, k);
			super.mouseClicked(i, j, k);
		}
	}

	public void mouseEvent(int i, int j, int k) {
	};

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (subgui != null)
			subgui.buttonEvent(guibutton);
		else {
			buttonEvent(guibutton);
		}
	}

	public void buttonEvent(GuiButton guibutton) {
	};

	@Override
	public void keyTyped(char c, int i) {
		if (AWTWindow != null)
			return;
		if (subgui != null)
			subgui.keyTyped(c, i);
		for (GuiNpcTextField tf : textfields.values())
			tf.textboxKeyTyped(c, i);

		if (closeOnEsc && i == 1 || closeOnE && !GuiNpcTextField.isFieldActive() && isInventoryKey(i)) {
			close();
		}
	}

	public void onGuiClosed() {
		GuiNpcTextField.unfocus();
	}

	public void close() {
		Keyboard.enableRepeatEvents(false);
		displayGuiScreen(null);
		mc.setIngameFocus();
		save();
	}

	@SuppressWarnings("unchecked")
	public void addButton(GuiNpcButton button) {
		buttons.put(button.id, button);
		buttonList.add(button);
	}

	@SuppressWarnings("unchecked")
	public void addTopButton(GuiMenuTopButton button) {
		topbuttons.put(button.id, button);
		buttonList.add(button);
	}

	@SuppressWarnings("unchecked")
	public void addSideButton(GuiMenuSideButton button) {
		sidebuttons.put(button.id, button);
		buttonList.add(button);
	}

	public GuiNpcButton getButton(int i) {
		return buttons.get(i);
	}

	public GuiMenuSideButton getSideButton(int i) {
		return sidebuttons.get(i);
	}

	public GuiMenuTopButton getTopButton(int i) {
		return topbuttons.get(i);
	}

	public void addTextField(GuiNpcTextField tf) {
		textfields.put(tf.id, tf);
	}

	public GuiNpcTextField getTextField(int i) {
		return textfields.get(i);
	}

	public void addLabel(GuiNpcLabel label) {
		labels.put(label.id, label);
	}

	public GuiNpcLabel getLabel(int i) {
		return labels.get(i);
	}

	@SuppressWarnings("unchecked")
	public void addSlider(GuiNpcSlider slider) {
		sliders.put(slider.id, slider);
		buttonList.add(slider);
	}

	public GuiNpcSlider getSlider(int i) {
		return sliders.get(i);
	}

	public void addScroll(GuiCustomScroll scroll) {
		scroll.setWorldAndResolution(mc, 350, 250);
		scrolls.put(scroll.id, scroll);
	}

	public GuiCustomScroll getScroll(int id) {
		return scrolls.get(id);
	}

	public abstract void save();

	@Override
	public void drawScreen(int i, int j, float f) {
		if (AWTWindow != null) {
			if (!AWTWindow.isVisible()) {
				AWTWindow.dispose();
				AWTWindow = null;
			} else if (Display.isActive()) {
				Toolkit.getDefaultToolkit().beep();
				AWTWindow.setVisible(true);
			}
		}
		mouseX = i;
		mouseY = j;
		if (drawDefaultBackground && subgui == null)
			drawDefaultBackground();

		if (background != null && mc.renderEngine != null) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPushMatrix();
			GL11.glTranslatef(guiLeft, guiTop, 0);
			GL11.glScalef(bgScale * bgScaleX, bgScale * bgScaleY, bgScale * bgScaleZ);
			mc.renderEngine.bindTexture(background);
			if (xSize > 256) {
				drawTexturedModalRect(0, 0, 0, 0, 250, ySize);
				drawTexturedModalRect(250, 0, 256 - (xSize - 250), 0, xSize - 250, ySize);
			} else
				drawTexturedModalRect(0, 0, 0, 0, xSize, ySize);
			GL11.glPopMatrix();
		}

		drawCenteredString(fontRendererObj, title, width / 2, guiTop + 4, 0xffffff);
		for (GuiNpcLabel label : labels.values())
			label.drawLabel(this, fontRendererObj);
		for (GuiNpcTextField tf : textfields.values()) {
			tf.drawTextBox(i, j);
		}
		for (GuiCustomScroll scroll : scrolls.values())
			scroll.drawScreen(i, j, f, hasSubGui() ? 0 : Mouse.getDWheel());
		for (GuiScreen gui : extra.values())
			gui.drawScreen(i, j, f);
		super.drawScreen(i, j, f);
		if (subgui != null)
			subgui.drawScreen(i, j, f);
	}

	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
	}

	public void elementClicked() {
		if (subgui != null)
			subgui.elementClicked();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public void doubleClicked() {
	}

	public boolean isInventoryKey(int i) {
		return i == mc.gameSettings.keyBindInventory.getKeyCode(); // inventory key
	}

	@Override
	public void drawDefaultBackground() {
		super.drawDefaultBackground();
	}

	public void displayGuiScreen(GuiScreen gui) {
		mc.displayGuiScreen(gui);
	}

	public void setSubGui(SubGuiInterface gui) {
		subgui = gui;
		subgui.setWorldAndResolution(mc, width, height);
		subgui.parent = this;
		initGui();
	}

	public void closeSubGui(SubGuiInterface gui) {
		subgui = null;
	}

	public boolean hasSubGui() {
		return subgui != null;
	}

	public SubGuiInterface getSubGui() {
		if (hasSubGui() && subgui.hasSubGui())
			return subgui.getSubGui();
		return subgui;
	}

	public void openLink(String link) {
		try {
			Class oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
			oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI(link) });
		} catch (Throwable throwable) {
		}
	}
}
