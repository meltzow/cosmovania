/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.View;
import app.AppFacade;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Benoît
 */
public abstract class Controller extends AbstractAppState {
	protected final SpatialSelector spatialSelector = new SpatialSelector();

	protected InputInterpreter inputInterpreter;
	protected CameraManager cameraManager;
	public CameraManager getCameraManager() {
		return cameraManager;
	}
	protected View view;
	
	public Controller() {
		super();
	}
	
	@Override
	public final void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		onInitialize(stateManager);
	}

	@Override
	public void setEnabled(boolean enabled) {
		if(enabled){
			inputInterpreter.registerInputs();
			cameraManager.registerInputs(AppFacade.getInputManager());
			cameraManager.activate();
			onEnabled();
		} else {
			inputInterpreter.unregisterInputs();
			cameraManager.unregisterInputs(AppFacade.getInputManager());
			onDisabled();
		}
	}
	
	public void onInitialize(AppStateManager stateManager){
		
	}
	public void onEnabled(){
		
	}
	public void onDisabled(){
		
	}
}
