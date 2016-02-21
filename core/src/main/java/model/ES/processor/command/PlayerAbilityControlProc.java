package model.ES.processor.command;


import processor.JmeProcessor;
import processor.JmeProcessor.TYPE;
import model.Command;
import model.ES.component.ability.AbilityTrigger;
import model.ES.component.ability.PlayerControl;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;

import controller.ECS.DataState;
import controller.ECS.Processor;

public class PlayerAbilityControlProc extends Processor implements JmeProcessor {

	private Command command; 
	
	public TYPE getType() {
		return JmeProcessor.TYPE.COMMAND;
	}
	
	@Override
	protected void onInitialized(AppStateManager stateManager) {
		command = stateManager.getState(DataState.class).getCommand();
	}
	
	@Override
	protected void registerSets() {
		registerDefault(AbilityTrigger.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		AbilityTrigger triggers = e.get(AbilityTrigger.class);
		triggers.triggers.clear();
		for(String abilityName : command.abilities){
			triggers.triggers.put(abilityName, true);
		}
	}
}
