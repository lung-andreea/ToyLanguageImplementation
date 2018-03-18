package View;

import Controller.Controller;
import Model.Commands.Command;
import Model.Commands.RunExample;
import Model.DataStructures.*;
import Model.Expressions.ArithExp;
import Model.Expressions.ConstExp;
import Model.Expressions.ReadHeap;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Statements.*;
import Repository.Repository;
import Repository.RepositoryInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SelectProgramController implements Initializable {
    public ListView listView;
    public Channel channel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IStmt ex1 = new CompStmt(new AssignStmt("a", new ArithExp(new ConstExp(2), new ConstExp(2), 2)), new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(2)), new AssignStmt("v", new ConstExp(3))), new PrintStmt(new VarExp("v"))));

        IStmt ex2 = new CompStmt(new CompStmt(new CompStmt(new OpenRFile("var_f", "test.in"), new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c")))), new IfStmt(new VarExp("var_c"), new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))), new PrintStmt(new ConstExp(0)))), new CloseRFile(new VarExp("var_f")));

        IStmt ex3 = new CompStmt(new OpenRFile("var_f", "test.in"), new CompStmt(new ReadFile(new ArithExp(new VarExp("var_f"), new ConstExp(2), 1), "var_c"), new CompStmt(new IfStmt(new VarExp("var_c"), new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))), new PrintStmt(new ConstExp(0))), new CloseRFile(new VarExp("var_f")))));

        IStmt ex4 = new CompStmt(new AssignStmt("v",new ConstExp(10)), new CompStmt(new NewStmt("v",new ConstExp(20)),new CompStmt(new NewStmt("a",new ConstExp(22)),new CompStmt(new WriteHeap("a",new ConstExp(30)),new CompStmt(new PrintStmt(new VarExp("a")),new CompStmt(new PrintStmt(new ReadHeap("a")),new AssignStmt("a",new ConstExp(0))))))));

        IStmt ex5 = new CompStmt(new AssignStmt("v",new ConstExp(10)),new CompStmt(new NewStmt("v",new ConstExp(20)),new CompStmt(new NewStmt("a",new ConstExp(22)),new CompStmt(new PrintStmt(new ArithExp(new ConstExp(100),new ReadHeap("v"),1)),new PrintStmt(new ArithExp(new ConstExp(100),new ReadHeap("a"),1))))));

        IStmt ex6 = new CompStmt(new AssignStmt("v",new ConstExp(6)),new CompStmt(new WhileStmt(new ArithExp(new VarExp("v"),new ConstExp(4),2),new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithExp(new VarExp("v"),new ConstExp(1),2)))),new PrintStmt(new VarExp("v"))));

        IStmt ex7 = new CompStmt(new AssignStmt("v",new ConstExp(10)),new CompStmt(new NewStmt("a",new ConstExp(22)),new CompStmt(new ForkStmt(new CompStmt(new WriteHeap("a",new ConstExp(30)),new CompStmt(new AssignStmt("v",new ConstExp(32)),new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeap("a")))))),new CompStmt(new PrintStmt(new VarExp("v")),new PrintStmt(new ReadHeap("a"))))));

        IStmt ex8 = new CompStmt(new AssignStmt("v",new ConstExp(1)),new CompStmt(new ForkStmt(new AssignStmt("v",new ConstExp(2))),new ForkStmt(new AssignStmt("v",new ConstExp(3)))));

        IStmt ex9 = new CompStmt(new CompStmt(new NewStmt("v1",new ConstExp(2)),
                new CompStmt(new NewStmt("v2",new ConstExp(3)), new CompStmt(new NewStmt("v3",new ConstExp(4)),
                        new newBarrier("cnt",new ReadHeap("v2"))))),
                new CompStmt(new CompStmt(new ForkStmt(new CompStmt(new Await("cnt"),
                        new CompStmt(new WriteHeap("v1",new ArithExp(new ReadHeap("v1"),new ConstExp(10),3)),
                                new PrintStmt(new ReadHeap("v1"))))),
                        new ForkStmt(new CompStmt(new Await("cnt"),
                                new CompStmt(new WriteHeap("v2",new ArithExp(new ReadHeap("v2"),new ConstExp(10),3)),
                                        new CompStmt(new WriteHeap("v2",new ArithExp(new ReadHeap("v2"),new ConstExp(10),3)),
                                                new PrintStmt(new ReadHeap("v2")))
                                        )))),
                        new CompStmt(new Await("cnt"),new PrintStmt(new ReadHeap("v3")))));

        List<IStmt> programs = Arrays.asList(ex1,ex2,ex3,ex4,ex5,ex6,ex7,ex8,ex9);

        List<Command> commands = new ArrayList<>();

        for(int i=1;i<=9;i++)
        {
            MyIStack<IStmt> exeStack = new MyStack<>();
            MyIDictionary<String, Integer> symTable = new MyDictionary<>();
            MyIList<Integer> out = new MyList<>();
            FileTableInterface fileTable = new FileTable();
            FileTableInterface br = new BarrierTable();
            HeapInterface heap = new Heap();

            PrgState prg = new PrgState(exeStack, symTable, out, fileTable, heap,br, programs.get(i-1),i);
            String logFilePath = "log"+Integer.toString(i)+".txt";
            RepositoryInterface repo = new Repository(logFilePath);
            repo.addNewPrg(prg);
            Controller ctrl = new Controller(repo);
            commands.add(new RunExample(Integer.toString(i), programs.get(i-1).toString(), ctrl));
        }

        ObservableList<Command> data = FXCollections.observableArrayList(commands);
        listView.setItems(data);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Command>() {
            @Override
            public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
                channel.mainWindowController.command=newValue;
                channel.mainWindowController.reInitializeFields();
            }
        });
    }

}
