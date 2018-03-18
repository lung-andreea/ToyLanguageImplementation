package View;

public class Channel {
    public SelectProgramController selectProgramController;
    public MainWindowController mainWindowController;

    public Channel(SelectProgramController s, MainWindowController m)
    {
        selectProgramController=s;
        mainWindowController=m;
    }
}
