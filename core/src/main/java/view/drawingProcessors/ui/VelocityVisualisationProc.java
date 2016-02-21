package view.drawingProcessors.ui;

import processor.JmeProcessor;
import processor.JmeProcessor.TYPE;
import model.ES.component.debug.VelocityViewing;
import model.ES.component.motion.PlanarStance;
import model.ES.richData.VelocityView;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.SpatialPool;
import view.material.MaterialManager;
import view.math.TranslateUtil;
import app.AppFacade;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;

import controller.ECS.Processor;

public class VelocityVisualisationProc extends Processor implements JmeProcessor  {
	
	public TYPE getType() {
		return JmeProcessor.TYPE.VISUAL;
	}

	@Override
	protected void registerSets() {
		registerDefault(VelocityViewing.class, PlanarStance.class);
	}

	@Override
	protected void onEntityEachTick(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		
		for(VelocityView v : e.get(VelocityViewing.class).velocities.values()){
			if(!SpatialPool.velocities.containsKey(v)){
				Geometry body = new Geometry("velocity");
				body.setMaterial(MaterialManager.getColor(TranslateUtil.toColorRGBA(v.color)));
				SpatialPool.velocities.put(v, body);
				AppFacade.getMainSceneNode().attachChild(body);
			}
			Geometry g = (Geometry)SpatialPool.velocities.get(v);
			Point2D end = stance.coord.getAddition(v.velocity);
			Line l = new Line(TranslateUtil.toVector3f(stance.coord.get3D(v.elevation)), TranslateUtil.toVector3f(end.get3D(v.elevation)));
			l.setLineWidth(v.thickness);
			g.setMesh(l);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
