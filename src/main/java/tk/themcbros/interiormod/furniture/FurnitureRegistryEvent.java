package tk.themcbros.interiormod.furniture;
import net.minecraftforge.eventbus.api.Event;

public class FurnitureRegistryEvent extends Event {

	private IFurnitureRegistry materialRegistry;
    
    public FurnitureRegistryEvent(IFurnitureRegistry materialRegistry) {
        this.materialRegistry = materialRegistry;
    }
    
    public IFurnitureRegistry getMaterialRegistry() {
        return this.materialRegistry;
    }
	
}