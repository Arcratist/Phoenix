package com.guitcube.pheonix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.guitcube.pheonix.client.renderer.entity.RenderPhoenixBlockFactory;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Phoenix.MODID)
public class Phoenix {
	public static final String MODID = "phoenix";

	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static Phoenix INSTANCE;

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, MODID);

	private KeyBinding openPhoenixGuiKeybind = new KeyBinding("key.openPhoenixGui", 80, "key.categories.phoenix");

	public Phoenix() {
		INSTANCE = this;

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		modEventBus.addListener(this::loadComplete);
		modEventBus.addListener(this::doClientStuff);

		forgeEventBus.addListener(this::clientTick);
		forgeEventBus.addListener(this::onKey);

		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		FLUIDS.register(modEventBus);

		ClientRegistry.registerKeyBinding(openPhoenixGuiKeybind);

	}

	public void onKey(KeyInputEvent event) {
		if (openPhoenixGuiKeybind.isPressed()) {
		}
	}

	public void clientTick(ClientTickEvent event) {

	}

	public void doClientStuff(FMLClientSetupEvent event) {
		OBJLoader.INSTANCE.addDomain(MODID);
		RenderingRegistry.registerEntityRenderingHandler(PhoenixBlockEntity.class, RenderPhoenixBlockFactory.INSTANCE);
	}

	public void loadComplete(FMLLoadCompleteEvent event) {
		LOGGER.info("Finished Initialization...");
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
//		@SubscribeEvent
		public static void onModelBakeEvent(ModelBakeEvent event) {
			try {
				// Try to load an OBJ model (placed in
				// src/main/resources/assets/examplemod/models/)
				IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation(MODID + ":bunny.obj"));

				if (model instanceof OBJModel) {
					// If loading OBJ model succeeds, bake the model and replace stick's model with
					// the baked model
					IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.ITEM);
					event.getModelRegistry().put(new ModelResourceLocation("stick", "inventory"), bakedModel);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@SubscribeEvent
		public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		}

	}

}