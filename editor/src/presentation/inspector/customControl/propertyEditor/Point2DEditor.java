package presentation.inspector.customControl.propertyEditor;

import java.beans.PropertyDescriptor;

import com.simsilica.es.EntityComponent;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import presentation.util.Consumer3;
import util.geometry.geom2d.Point2D;

public class Point2DEditor extends PropertyEditor{
	private static final int WIDTH = 60;

	TextField xField, yField;
	
	public Point2DEditor(EntityComponent comp, PropertyDescriptor pd, Consumer3<EntityComponent, String, Object> updateCompFunction) {
		super(comp, pd, updateCompFunction);
	}

	@Override
	protected void createEditor() {
		HBox box = new HBox(5);
		setCenter(box);
		// y label
		box.getChildren().add(new Label("X"));

		// x text field
		xField = new TextField();
		xField.setPrefWidth(WIDTH);
		xField.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		xField.focusedProperty().addListener(e -> setEditionMode());

		box.getChildren().add(xField);
		
		// y label
		box.getChildren().add(new Label("Y"));
		
		// y texte field
		yField = new TextField();
		yField.setPrefWidth(WIDTH);
		yField.addEventHandler(ActionEvent.ACTION, e -> applyChange(e));
		yField.focusedProperty().addListener(e -> setEditionMode());
		box.getChildren().add(yField);
	}

	@Override
	protected Object getPropertyValue() {
		return new Point2D(Double.parseDouble(xField.getText()),Double.parseDouble(yField.getText()));  
	}

	@Override
	protected void setPropertyValue(Object o) {
		Point2D p = (Point2D)o;
		xField.setText(df.format(p.x));
		yField.setText(df.format(p.y));
	}
	
	

}
