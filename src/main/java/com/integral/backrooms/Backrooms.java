package com.integral.backrooms;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.integral.backrooms.handlers.ExampleConfig;
import com.integral.backrooms.handlers.GenericEventHandler;
import com.integral.backrooms.network.packets.ExamplePacket;
import com.integral.backrooms.proxy.CommonProxy;

@Mod(modid = Backrooms.MODID, version = Backrooms.VERSION, name = Backrooms.NAME)
public class Backrooms {
    public static final String MODID = "backrooms";
    public static final String NAME = "Backrooms Mod";
    public static final String VERSION = "1.0.0";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "com.forohon.backrooms.proxy.ClientProxy", serverSide = "com.forohon.backrooms.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final Logger logger = LogManager.getLogger("Backrooms");
    public static final int howCoolAmI = Integer.MAX_VALUE;

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderers();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // NO-OP
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ExampleConfig.load(event);

        packetHandler = NetworkRegistry.INSTANCE.newSimpleChannel("ExampleModChannel");
        packetHandler.registerMessage(ExamplePacket.Handler.class, ExamplePacket.class, 1, Side.CLIENT);

        MinecraftForge.EVENT_BUS.register(new GenericEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }

    public static CreativeTabs tabExampleMod = new CreativeTabs("tabExampleMod") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Items.BAKED_POTATO);
        }
    };
}
