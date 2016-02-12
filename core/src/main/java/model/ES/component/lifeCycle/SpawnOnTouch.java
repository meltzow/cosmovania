package model.ES.component.lifeCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simsilica.es.EntityComponent;

public class SpawnOnTouch implements EntityComponent {
	private final List<String> blueprintNames;
	
	public SpawnOnTouch() {
		blueprintNames = Collections.unmodifiableList(new ArrayList<>());
	}
	
	public SpawnOnTouch(@JsonProperty("blueprintNames")List<String> blueprintNames) {
		this.blueprintNames = Collections.unmodifiableList(new ArrayList<>(blueprintNames));
	}

	public List<String> getBlueprintNames() {
		return blueprintNames;
	}
}
