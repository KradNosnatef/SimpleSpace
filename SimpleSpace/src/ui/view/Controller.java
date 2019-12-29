package ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.util.Timer;
import java.util.TimerTask;

import core.spaceSimple.*;

public class Controller {
	int pause=1;
	int paint=0;
	CenterEntity earth;
	Entity ship1;
	String centerEntityInfoText,infoText;

	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void onButtonTimeShift1XClick(){
		Phase.playspeed=1;
	}
	@FXML
	private void onButtonTimeShift5XClick(){
		Phase.playspeed=5;
	}
	@FXML
	private void onButtonTimeShift10XClick(){	
		Phase.playspeed=10;
	}
	@FXML
	private void onButtonTimeShiftPauseClick() {
		if(pause==1)pause=0;
		else pause=1;
	}
	@FXML
	private void onButtonAPAccelerate_1Click() {
		ship1.maneuver.APAccelerate(0.01);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private void onButtonAPAccelerate_5Click() {
		ship1.maneuver.APAccelerate(0.05);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private void onButtonAPBrake_1Click() {
		ship1.maneuver.APAccelerate(-0.01);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private void onButtonAPBrake_5Click() {
		ship1.maneuver.APAccelerate(-0.05);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private void onButtonPEAccelerate_1Click() {
		ship1.maneuver.PEAccelerate(0.01);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private void onButtonPEAccelerate_5Click() {
		ship1.maneuver.PEAccelerate(0.05);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private void onButtonPEBrake_1Click() {
		ship1.maneuver.PEAccelerate(-0.01);
		refreshCenterEntityInfoTextArea();	
	}
	@FXML
	private void onButtonPEBrake_5Click() {
		ship1.maneuver.PEAccelerate(-0.05);
		refreshCenterEntityInfoTextArea();
	}
	@FXML
	private Circle ShipPosition1;
	@FXML
	private Ellipse Orbit1;
	@FXML
	private Circle ShipPosition2;
	@FXML
	private Ellipse Orbit2;
	
	
	@FXML
	private Label Evade;

	@FXML
	private TextField centerEntityMassGTextField;

	@FXML
	private TextArea infoTextArea;
	
	@FXML
	private TextField shipEntitySpeedTextField;

	@FXML
	private TextField shipEntityAPPETextField;
	
	@FXML
	private void onButtonCenterEntityCheckClick() {
		//System.out.println(centerEntityMassGTextField.getText());
		earth.setMassG(Double.valueOf(centerEntityMassGTextField.getText()));
		ship1.setVAPPE(Double.valueOf(shipEntitySpeedTextField.getText()), Double.valueOf(shipEntityAPPETextField.getText()));
		paint=1;
		refreshCenterEntityInfoTextArea();
		shipEntitySpeedTextField.setText("");
		shipEntityAPPETextField.setText("");
	}
	
	private void refreshCenterEntityInfoTextArea(){
		centerEntityInfoText=String.valueOf(earth.getMassG());
		refreshInfoTextArea();
	}
	
	private void refreshInfoTextArea(){
		infoText="Center entity Mass*G=\n\t"+centerEntityInfoText+"\n";
		infoText+="Ship AP=\n\t"+String.valueOf(ship1.getAP())+"\n";
		infoText+="Ship APSpeed=\n\t"+String.valueOf(ship1.getAPSpeed())+"\n";
		infoText+="Ship PE=\n\t"+String.valueOf(ship1.getPE())+"\n";
		infoText+="Ship PESpeed=\n\t"+String.valueOf(ship1.getPESpeed())+"\n";
		infoTextArea.setText(infoText);
		refreshOrbit();
	}
	
	private void refreshOrbit() {
		if(ship1.getPE()<0)Evade.setText("Evade!");
		else{
			Orbit1.setRadiusX(5*ship1.getSemimajorAxis());
			Orbit1.setRadiusY(5*ship1.getSemiminorAxis());
			Orbit1.setCenterX(5*ship1.getSemifocallength());
			ship1.orbitCalculator.refreshArray();
			refreshShipPosition();
			Evade.setText("");
		}
	}

	
	public void refreshShipPosition() {
		int angle;
		double length;
		angle=ship1.orbitCalculator.getAngle();
		System.out.println(angle);
		length=ship1.getSemiminorAxis()*ship1.getSemiminorAxis()/(ship1.getSemimajorAxis()-ship1.getSemifocallength()*Math.cos(Math.toRadians(Double.valueOf(angle))));
		System.out.println(angle+","+length);
		ShipPosition1.setCenterX(5*Math.cos(Math.toRadians(Double.valueOf(angle)))*length);
		ShipPosition1.setCenterY(-5*Math.sin(Math.toRadians(Double.valueOf(angle)))*length);
	}
	
	public void setAppCore(PB16120162 appCore) {
		earth=new CenterEntity();
		ship1=new Entity(earth);
		ship1.orbitCalculator=new OrbitCalculator(ship1);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		        public void run() {
		            if(pause==0) {
		            	System.out.println("TimerUP!");
		            	Phase.val=Phase.val+Phase.playspeed;
		            	ship1.phase.optimization();
		            	if(paint==1)refreshShipPosition();
		            }
		        }
		}, 0 , 40);
	}
}
