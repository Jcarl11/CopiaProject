package MiscellaneousClasses;

import Entities.NotesEntity;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;


public class CustomCell extends ListCell<NotesEntity>
{

    AnchorPane anchorPane = new AnchorPane();
    Button button = new Button("Click Me");
    Label remarkLabel = new Label();
    Label objectId = new Label();
    Label createdAt = new Label();
    Label updatedAt = new Label();
    
    public CustomCell()
    {
        remarkLabel.setWrapText(true);
        objectId.setWrapText(true);
        createdAt.setWrapText(true);
        updatedAt.setWrapText(true);
        AnchorPane.setTopAnchor(createdAt, 10.0);
        AnchorPane.setLeftAnchor(createdAt, 10.0);
        AnchorPane.setRightAnchor(updatedAt, 10.0);
        AnchorPane.setTopAnchor(updatedAt, 10.0);
        AnchorPane.setTopAnchor(remarkLabel, 30.0);
        AnchorPane.setLeftAnchor(remarkLabel, 10.0);
        AnchorPane.setRightAnchor(remarkLabel, 10.0);
        AnchorPane.setBottomAnchor(remarkLabel, 30.0);
        AnchorPane.setBottomAnchor(objectId, 10.0);
        AnchorPane.setLeftAnchor(objectId, 10.0);
        AnchorPane.setBottomAnchor(button, 10.0);
        AnchorPane.setRightAnchor(button, 10.0);
        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
        anchorPane.getChildren().addAll(remarkLabel,objectId,createdAt,updatedAt);
        setPrefWidth(USE_PREF_SIZE);
        anchorPane.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
        anchorPane.getStyleClass().add("anchorPane");
    }
    @Override
    protected void updateItem(NotesEntity item, boolean empty) 
    {
        super.updateItem(item, empty);
        if(empty || item == null)
        {
            setGraphic(null);
        }
        else
        {
            remarkLabel.setText(item.getRemarks());
            objectId.setText(item.getObjectId());
            createdAt.setText(item.getCreatedAt());
            updatedAt.setText(item.getUpdatedAt());
            
            setGraphic(anchorPane);
        }
    }
    
}
