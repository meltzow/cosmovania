package application.topDownScene;

import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import model.world.Tool;
import util.event.EntitySelectionChanged;
import util.event.EventManager;
import util.geometry.geom2d.Point2D;
import application.topDownScene.state.WorldToolState;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3x.jfx.injfx.JmeForImageView;
import com.simsilica.es.EntityId;

import controller.ECS.SceneSelectorState;

public class EditionInputListener implements SceneInputListener {
	private static enum ActionType {StartPrimary,
		StartSecondary,
		StopPrimary,
		StopSecondary,
		OncePrimary,
		OnceSecondary
	}

	private final JmeForImageView jme;
	
	public EditionInputListener(JmeForImageView jme) {
		this.jme = jme;
	}

	@Override
	public void onMousePressed(MouseEvent e){
		ActionType type;
		switch(e.getButton()){
		case PRIMARY : type = ActionType.StartPrimary; break;
		case SECONDARY : type = ActionType.StartSecondary; break;
		default : type = null;
		}
		if(type != null)
			jme.enqueue(app -> setToolAction(app, type));
	}

	@Override
	public void onMouseMoved(MouseEvent e){
		jme.enqueue(app -> setSceneMouseCoord(app, new Point2D(e.getX(), e.getY())));
	}

	@Override
	public void onMouseReleased(MouseEvent e){
		switch(e.getButton()){
		case PRIMARY :
			jme.enqueue(app -> setToolAction(app, ActionType.StopPrimary));
			jme.enqueue(app -> setToolAction(app, ActionType.OncePrimary));
			break;
		case SECONDARY : 
			jme.enqueue(app -> setToolAction(app, ActionType.StopSecondary));
			jme.enqueue(app -> setToolAction(app, ActionType.OnceSecondary));
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onMouseScroll(ScrollEvent e){
	}

	@Override
	public void onKeyPressed(KeyEvent e){
	}
	
	@Override
	public void onKeyReleased(KeyEvent e){
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
	}

	static private boolean setSceneMouseCoord(SimpleApplication app, Point2D coord) {
		AppStateManager stateManager = app.getStateManager();
		stateManager.getState(SceneSelectorState.class).setCoordInScreenSpace(coord);
		return true;
	}

	static private boolean setToolAction(SimpleApplication app, ActionType type) {
		Tool t = app.getStateManager().getState(WorldToolState.class).getTool();
		if(t != null){
			switch(type){
			case OncePrimary : t.onPrimarySingleAction(); break;
			case OnceSecondary : t.onSecondarySingleAction(); break;
			case StartPrimary : t.onPrimaryActionStart(); break;
			case StartSecondary : t.onSecondaryActionStart(); break;
			case StopPrimary : t.onPrimaryActionEnd(); break;
			case StopSecondary : t.onSecondaryActionEnd(); break;
			}
		} else {
			AppStateManager stateManager = app.getStateManager();
			EntityId pointed = stateManager.getState(SceneSelectorState.class).getPointedEntity();
			if(pointed != null)
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						EventManager.post(new EntitySelectionChanged(pointed));
					}
				});
		}
		return true;
	}
}