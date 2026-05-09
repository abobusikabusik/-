package my_beans;

import java.util.EventListener;

public interface DataSheetChangeListener extends EventListener
{
    void dataChanged(DataSheetChangeEvent e);
}