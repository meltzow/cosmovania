package view.tab.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Locale;

import com.simsilica.es.EntityComponent;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.BorderPane;
import view.util.Consumer3;

public abstract class PropertyEditor extends BorderPane {
	protected final DecimalFormat df;
	private final Consumer3<EntityComponent, String, Object> updateCompFunction;
	
	EntityComponent comp;
	PropertyDescriptor pd;
	
	private boolean ready = false;
	protected boolean editionMode = false;
	
	public PropertyEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		this.updateCompFunction = updateCompFunction;
		df = (DecimalFormat)DecimalFormat.getNumberInstance(Locale.ENGLISH);
		df.setMinimumFractionDigits(2);
		
		this.comp = comp;
		this.pd = pd;
		setPrefHeight(25);
		setPadding(new Insets(2, 0, 2, 0));

		
		Label l = new Label();
		l.setText(pd.getDisplayName());
		l.setMinWidth(150);
		l.setTextOverrun(OverrunStyle.ELLIPSIS);
		setLeft(l);
		
		createEditor();
		setAlignment(getLeft(), Pos.CENTER_LEFT);
		if(getCenter() != null)
			setAlignment(getCenter(), Pos.CENTER_LEFT);
		if(getBottom() != null)
			setAlignment(getBottom(), Pos.CENTER_LEFT);
		if(getRight() != null)
			setAlignment(getRight(), Pos.CENTER_LEFT);
		updateValue(comp);
		ready = true;
	}
	
	protected abstract void createEditor();
	protected abstract Object getPropertyValue();
	protected abstract void setPropertyValue(Object o);
	
	public void updateValue(EntityComponent comp){
		if(!editionMode){
			try {
				Object val = pd.getReadMethod().invoke(comp, new Object[0]);
				setPropertyValue(val);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void applyChange(ActionEvent event){
		if(ready){
			updateCompFunction.accept(comp, pd.getName(), getPropertyValue());
			this.requestFocus();
		}
	}

	protected void setEditionMode(){
		editionMode = true;
	}
}
