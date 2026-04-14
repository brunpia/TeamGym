// Ansvar: starte appen og åpne FXML-vinduet.
// Denne skal være liten og nesten ikke inneholde logikk.

package teamgym;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TeamGymApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TeamGymApp.class.getResource("/teamgym/TeamGym.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TeamGym-Tumbling");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
