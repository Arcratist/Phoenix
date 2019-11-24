package com.guitcube.pheonix.core.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class PhoenixScreen extends Screen {

	public static List<Button> objectList = new ArrayList<Button>();

	public PhoenixScreen() {
		super(new StringTextComponent("Phoenix Screen"));
	}

	@Override
	protected void init() {
		
		for (int i = 0; i < objectList.size(); i++) {
			objectList.get(i).x = 10 + (i * 170);
			objectList.get(i).y = 30;
			addButton(objectList.get(i));
		}

		addButton(new Button((width / 2) - 80, height - 30, 160, 20, "Done", button -> minecraft.displayGuiScreen(null)));
		
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		
		//this.renderBackground();
		this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 10, 0xffff00);
		this.drawString(this.font, "NOTES:", 10, 200, 0xffff00);
		this.drawString(this.font, "Sliders are sticky. Do not release mouse button when mouse pointer is off of slider...", 10, 220, 0xffffff);
		this.drawString(this.font, "For inverted water bucket do:", 10, 230, 0xffffff);
		this.drawString(this.font, "/give @a phoenix:inverted_water_bucket", 10, 240, 0x00ff00);
		this.drawString(this.font, "                                                    (Currently doesn't work)", 10, 240, 0xff0000);
		super.render(mouseX, mouseY, partialTicks);
		
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	public static void open() {
		Minecraft.getInstance().displayGuiScreen(new PhoenixScreen());
	}
	
}