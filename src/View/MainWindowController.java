package View;
import Model.Commands.Command;
import Model.Exceptions.EmptyStackException;
import Model.PrgState;
import Model.Statements.IStmt;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {
    public Command command;
    public PrgState selectedPrgState;
    public Channel channel;
    public Button runButton;
    public Label currentPrgLabel;
    public Label nrOfPrgStates;
    public ListView prgStateIDs;
    public ListView exeStack;
    public TableView symTable;
    public TableColumn varNameSymTable;
    public TableColumn valueSymTable;
    public ListView out;
    public TableView fileTable;
    public TableColumn IDcolumnFileTbl;
    public TableColumn fileNameColumnFileTbl;
    public TableView heapTable;
    public TableColumn addrColumnHeap;
    public TableColumn ValColumnHeap;
    public TableView barrierTable;
    public TableColumn indexColumnBarrier;
    public TableColumn valueColumnBarrier;
    public TableColumn ListOfValuesBarrier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        varNameSymTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String,Integer>,String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String,Integer>,String> param) {
            return new SimpleStringProperty(param.getValue().getKey());
        }
    });

        valueSymTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer>, SimpleIntegerProperty>() {
            @Override
            public SimpleIntegerProperty call(TableColumn.CellDataFeatures<Map.Entry<String,Integer>,Integer> param) {
                return new SimpleIntegerProperty(param.getValue().getValue());
            }
        });

        addrColumnHeap.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, Integer>, SimpleIntegerProperty>() {
            @Override
            public SimpleIntegerProperty call(TableColumn.CellDataFeatures<Map.Entry<Integer,Integer>,Integer> param) {
                return new SimpleIntegerProperty(param.getValue().getKey());
            }
        });

        ValColumnHeap.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, Integer>, SimpleIntegerProperty>() {
            @Override
            public SimpleIntegerProperty call(TableColumn.CellDataFeatures<Map.Entry<Integer,Integer>,Integer> param) {
                return new SimpleIntegerProperty(param.getValue().getValue());
            }
        });

        IDcolumnFileTbl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer,String>, Integer>, SimpleIntegerProperty>() {
            @Override
            public SimpleIntegerProperty call(TableColumn.CellDataFeatures<Map.Entry<Integer,String>,Integer> param) {
                return new SimpleIntegerProperty(param.getValue().getKey());
                }
        });

        fileNameColumnFileTbl.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer,String>,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer,String>,String> param) {
                return new SimpleStringProperty(param.getValue().getValue());
            }
        });

        indexColumnBarrier.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Pair>, Integer>, SimpleIntegerProperty>() {
            @Override
            public SimpleIntegerProperty call(TableColumn.CellDataFeatures<Map.Entry<Integer,Pair>,Integer> param) {
                return new SimpleIntegerProperty(param.getValue().getKey());
            }
        });

        valueColumnBarrier.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Pair>, Pair>, SimpleIntegerProperty>() {
            @Override
            public SimpleIntegerProperty call(TableColumn.CellDataFeatures<Map.Entry<Integer,Pair>,Pair> param) {
                return new SimpleIntegerProperty((Integer)(((Pair)param.getValue().getValue()).getKey()));
            }
        });

        ListOfValuesBarrier.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Pair>, Pair>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer,Pair>,Pair> param) {
                return new SimpleStringProperty((String) (((Pair)param.getValue().getValue()).getValue()));
            }
        });

    }

    public void executeOneStep()
    {
        try {
            command.executeOneStep();
            populateFields();
        }
        catch (Exception e)
        {
            Stage popup = new Stage();
            popup.setTitle("Message Window");
            Label lbl = new Label(e.getMessage());
            Button ok = new Button("OK");
            ok.setOnAction(f->{popup.close();});
            VBox layout = new VBox(10);
            layout.getChildren().addAll(lbl,ok);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout,400,130);
            popup.setScene(scene);
            popup.showAndWait();
            populateFields();
        }
    }

    public void reInitializeFields()
    {
        command.prepareForExecution();

        currentPrgLabel.setText(command.toString());

        symTable.getItems().clear();
        out.getItems().clear();
        heapTable.getItems().clear();
        fileTable.getItems().clear();

        prgStateIDs.getItems().clear();
        prgStateIDs.getItems().add(command.getController().getRepo().getPrgList().get(0).getID());

        prgStateIDs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                populateSymTableExeStack();
            }
        });

    }

    public void populateSymTableExeStack()
    {

        Platform.runLater(()->
        {
            selectedPrgState = command.getController().getRepo().getPrgStateByID((Integer) prgStateIDs.getSelectionModel().getSelectedItem());

            // Populate EXESTACK
            ObservableList<IStmt> exeStackData = FXCollections.observableArrayList(selectedPrgState.getExeStack().getStack());
            exeStack.setItems(exeStackData);

            // Populate SYMTBLE
            ObservableList<Map.Entry<String,Integer>> symTableItems = FXCollections.observableArrayList(selectedPrgState.getSymTable().getContent().entrySet());
            symTable.setItems(symTableItems);
            symTable.refresh();

        });
    }

    public void populateFields()
    {
        // Populate OUT
        List<Integer> outValues = command.getController().getRepo().getPrgList().get(0).getOut().getContent();
        ObservableList<Integer> outData = FXCollections.observableArrayList(outValues);
        out.setItems(outData);

        // Populate HEAP
        ObservableList<Map.Entry<Integer,Integer>> heapData = FXCollections.observableArrayList(command.getController().getRepo().getPrgList().get(0).getHeap().getContent().entrySet());
        heapTable.setItems(heapData);

        // Populate FILETABLE
        ObservableList<Map.Entry<Integer,String>> fileTableData = FXCollections.observableArrayList(command.getController().getRepo().getPrgList().get(0).getFileTable().getContentForGUI().entrySet());
        fileTable.setItems(fileTableData);

        // Populate BarrierTable
        ObservableList<Map.Entry<Integer,Pair>> barrierData = FXCollections.observableArrayList(command.getController().getRepo().getPrgList().get(0).getBarrierTable().getContentForGUI().entrySet());
        barrierTable.setItems(barrierData);

        // Populate PrgList Window in GUI
        List<Integer> ids = command.getController().getRepo().getPrgList().stream().map(p->p.getID()).collect(Collectors.toList());
        ObservableList<Integer> prgListIDs = FXCollections.observableArrayList(ids);
        prgStateIDs.setItems(prgListIDs);

        if(selectedPrgState!=null)
            populateSymTableExeStack();

        nrOfPrgStates.setText(Integer.toString(ids.size()));

    }

}
