package model.ES.processor.command;

import processor.JmeProcessor;
import processor.JmeProcessor.TYPE;
import util.LogUtil;
import model.Command;
import model.ES.component.ability.PlayerControl;
import model.ES.component.motion.PlanarNeededThrust;
import model.ES.component.motion.PlanarStance;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.Entity;

import controller.ECS.DataState;
import controller.ECS.Processor;

public class PlayerThrustControlProc extends Processor implements JmeProcessor {

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
		registerDefault(PlanarStance.class, PlayerControl.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		if(command.target == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);
		if(stance.coord.getDistance(command.target) > 0.1){
    		PlanarNeededThrust thrust = new PlanarNeededThrust(command.thrust.getRotation(stance.orientation.getValue()));
    		setComp(e, thrust);
		}
	}
}
