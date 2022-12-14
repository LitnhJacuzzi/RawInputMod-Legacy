package com.kuri0.rawinput;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Mouse;

@Mod(modid = RawInput.MODID, version = RawInput.VERSION)
public class RawInput
{
    public static final String MODID = "rawinput";
    public static final String VERSION = "1.1.1";
    
    public static Mouse mouse;
    public static Controller[] controllers;
    // Delta for mouse
    public static int dx = 0;
    public static int dy = 0;

	public static void getMouse() {
		for (int i = 0; i < controllers.length && mouse == null; i++) {
			if (controllers[i].getType() == Controller.Type.MOUSE) {
				controllers[i].poll();
				if (((Mouse) controllers[i]).getX().getPollData() != 0.0 || ((Mouse) controllers[i]).getY().getPollData() != 0.0) {
					mouse = (Mouse) controllers[i];
				}
			}
		}
	}

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		ClientCommandHandler.instance.registerCommand(new RescanCommand());
		ClientCommandHandler.instance.registerCommand(new ToggleCommand());
		Minecraft.getMinecraft().mouseHelper = new RawMouseHelper();
		controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        Thread inputThread = new Thread(() -> {
			while(true){
				if (mouse != null && Minecraft.getMinecraft().currentScreen == null){
					mouse.poll();
					dx += (int)mouse.getX().getPollData();
					dy += (int)mouse.getY().getPollData();
				} else {
					getMouse();
				}

				try {
					Thread.sleep(1);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
        });

		inputThread.setName("inputThread");
        inputThread.start();
    }
}
