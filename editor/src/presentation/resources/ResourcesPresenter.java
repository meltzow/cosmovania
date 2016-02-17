package presentation.resources;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.ES.component.Parenting;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import presentation.BlueprintComparator;
import presentation.EntityNode;
import presentation.base.AbstractPresenter;

public class ResourcesPresenter extends AbstractPresenter<ResourcesViewer> {

	private final ListProperty<Blueprint> blueprintList;

	public ResourcesPresenter(ResourcesViewer viewer) {
		super(viewer);
		blueprintList = new SimpleListProperty<Blueprint>(FXCollections.observableArrayList());
		for(Blueprint bp : BlueprintLibrary.getAllBlueprints())
			blueprintList.add(bp);
		blueprintList.sort(new BlueprintComparator());
	}
	
	public ListProperty<Blueprint> getBlueprintList() {
		return blueprintList;
	}
	
	public void saveEntityAsBlueprint(EntityNode ep){
		Blueprint saved = createBlueprint(ep);
		BlueprintLibrary.saveBlueprint(saved);
		
		for(Blueprint bp : blueprintList)
			if(bp.getName().equalsIgnoreCase(saved.getName())){
				blueprintList.set(blueprintList.indexOf(bp), saved);
				return;
			}
		// at this point, the saved blueprint doesn't replace any existing blueprint
		blueprintList.add(saved);
		blueprintList.sort(new BlueprintComparator());
	}
	
	private Blueprint createBlueprint(EntityNode ep){
		// we have to ignore the parenting component, for it is created from the blueprint tree
		List<EntityComponent> comps = new ArrayList<>();
		for(EntityComponent comp : ep.componentListProperty())
			if(!(comp instanceof Parenting))
				comps.add(comp);

		List<Blueprint> children = new ArrayList<>();
		for(EntityNode child : ep.childrenListProperty())
			children.add(createBlueprint(child));
		
		return new Blueprint(ep.nameProperty().getValue(),
				comps,
				children);
	}

}
