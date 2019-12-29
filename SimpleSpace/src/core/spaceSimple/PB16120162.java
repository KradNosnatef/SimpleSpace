package core.spaceSimple;

import java.io.IOException;
import ui.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PB16120162 extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("App");
		initRootLayout();
	}
	
	private void initRootLayout(){
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(PB16120162.class.getResource("../../ui/view/RootLayout.fxml"));
				rootLayout=(BorderPane)loader.load();
				Scene scene = new Scene(rootLayout);
				primaryStage.setScene(scene);
				primaryStage.show();
				Controller controller = loader.getController();
				controller.setAppCore(this);
			}catch(IOException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
