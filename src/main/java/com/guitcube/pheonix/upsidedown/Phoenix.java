package com.guitcube.pheonix.upsidedown;

import com.guitcube.pheonix.core.gui.PhoenixScreen;
import com.guitcube.pheonix.upsidedown.block.InvertedWaterBlock;
import com.guitcube.pheonix.upsidedown.renderer.WeatherRenderer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Phoenix.MODID)
public class Phoenix {
	public static final String MODID = "phoenix";

	public static final ResourceLocation FLUID_STILL = new ResourceLocation("minecraft:block/brown_mushroom_block");
	public static final ResourceLocation FLUID_FLOWING = new ResourceLocation("minecraft:block/mushroom_stem");

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, MODID);

	public static RegistryObject<FlowingFluid> test_fluid = FLUIDS.register("test_fluid", () -> new ForgeFlowingFluid.Source(Phoenix.test_fluid_properties));
	public static RegistryObject<FlowingFluid> test_fluid_flowing = FLUIDS.register("test_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(Phoenix.test_fluid_properties));

	public static RegistryObject<InvertedWaterBlock> test_fluid_block = BLOCKS.register("test_fluid_block", () -> new InvertedWaterBlock(test_fluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
	public static RegistryObject<Item> inverted_water_bucket = ITEMS.register("inverted_water_bucket", () -> new BucketItem(test_fluid, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC)));

	public static final ForgeFlowingFluid.Properties test_fluid_properties = new ForgeFlowingFluid.Properties(test_fluid, test_fluid_flowing, FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xffff00ff)).bucket(inverted_water_bucket).block(test_fluid_block);
	KeyBinding openPhoenixGuiKeybind = new KeyBinding("key.openPhoenixGui", 80, "key.categories.phoenix");

	WeatherRenderer wR = new WeatherRenderer();

	public Phoenix() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		modEventBus.addListener(this::loadComplete);

		forgeEventBus.addListener(this::clientTick);
		forgeEventBus.addListener(this::onKey);

		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		FLUIDS.register(modEventBus);

		ClientRegistry.registerKeyBinding(openPhoenixGuiKeybind);

		addScreenOptions();

	}

	public void onKey(KeyInputEvent event) {
		if (openPhoenixGuiKeybind.isPressed()) {
			PhoenixScreen.open();
		}
	}

	public void loadComplete(FMLLoadCompleteEvent event) {

	}

	public void clientTick(ClientTickEvent event) {

		if (Minecraft.getInstance().world != null) {
			if (Minecraft.getInstance().world.getDimension() != null) {
				if (Minecraft.getInstance().world.dimension.getWeatherRenderer() == null)
					Minecraft.getInstance().world.dimension.setWeatherRenderer(wR);

				wR.rendererUpdateCount++;

				wR.rainRotX = (float) optionsSliderRainRotX.sliderValue * 360.0f;
				wR.rainRotY = (float) optionsSliderRainRotY.sliderValue * 360.0f;
				wR.rainRotZ = (float) optionsSliderRainRotZ.sliderValue * 360.0f;
				wR.rainHeight = (float) ((float) 16.0f - (optionsSliderRainHeight.sliderValue * 32.0f));

			}
		}
	}

	GuiSlider optionsSliderRainRotX;
	GuiSlider optionsSliderRainRotY;
	GuiSlider optionsSliderRainRotZ;
	GuiSlider optionsSliderRainHeight;
	GuiSlider optionsSliderRainSpeed;
	Button optionsButtonGiveBucket;

	private void addScreenOptions() {
		PhoenixScreen.objectList.clear();

		PhoenixScreen.objectList.add(optionsSliderRainRotX = new GuiSlider(0, 0, 160, 20, "Rain Rotation X: ", "°", 0.0F, 360.0F, wR.rainRotX, false, true, null));
		PhoenixScreen.objectList.add(optionsSliderRainRotY = new GuiSlider(0, 0, 160, 20, "Rain Rotation Y: ", "°", 0.0F, 360.0F, wR.rainRotY, false, true, null));
		PhoenixScreen.objectList.add(optionsSliderRainRotZ = new GuiSlider(0, 0, 160, 20, "Rain Rotation Z: ", "°", 0.0F, 360.0F, wR.rainRotZ, false, true, null));
		PhoenixScreen.objectList.add(optionsSliderRainHeight = new GuiSlider(0, 0, 160, 20, "Rain Height Offset: ", "M", -32F, 32.0F, wR.rainHeight, false, true, null));
		PhoenixScreen.objectList.add(optionsSliderRainSpeed = new GuiSlider(0, 0, 160, 20, "Rain Speed Offset: ", "", -5F, 5.0F, 0, false, true, null));
		optionsSliderRainSpeed.active = false;
		// PhoenixScreen.objectList.add(optionsButtonGiveBucket = new Button(0, 0, 160,
		// 20, "Give Inverted Water Bucket", button -> giveItem(new
		// ItemStack(test_fluid_bucket.get()))));
	}

}