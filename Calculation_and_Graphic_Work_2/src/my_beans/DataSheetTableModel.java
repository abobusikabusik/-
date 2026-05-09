package my_beans;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;

public class DataSheetTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;

    private int columnCount = 3;
    private int rowCount = 1;
    private DataSheet dataSheet = null;
    private String[] columnNames = {"Date", "X Value", "Y Value"};

    // Список "вух", які слухають нашу таблицю
    private ArrayList<DataSheetChangeListener> listenerList = new ArrayList<>();
    private DataSheetChangeEvent event = new DataSheetChangeEvent(this);

    public DataSheet getDataSheet()
    {
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet)
    {
        this.dataSheet = dataSheet;
        rowCount = this.dataSheet.size();
        fireDataSheetChange(); // Кричимо, що дані завантажились
    }

    @Override
    public int getColumnCount() { return columnCount; }

    @Override
    public int getRowCount() { return rowCount; }

    @Override
    public String getColumnName(int column) { return columnNames[column]; }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) { return true; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (dataSheet != null)
        {
            if (columnIndex == 0) return dataSheet.getDataItem(rowIndex).getDate();
            else if (columnIndex == 1) return dataSheet.getDataItem(rowIndex).getX();
            else if (columnIndex == 2) return dataSheet.getDataItem(rowIndex).getY();
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        try
        {
            double d;
            if (dataSheet != null)
            {
                if (columnIndex == 0)
                {
                    dataSheet.getDataItem(rowIndex).setDate((String) value);
                }
                else if (columnIndex == 1)
                {
                    d = Double.parseDouble(value.toString());
                    dataSheet.getDataItem(rowIndex).setX(d);
                }
                else if (columnIndex == 2)
                {
                    d = Double.parseDouble(value.toString());
                    dataSheet.getDataItem(rowIndex).setY(d);
                }
                fireDataSheetChange(); // Кричимо, що юзер змінив цифру!
            }
        }
        catch (Exception ex)
        {
            System.out.println("Помилка вводу: введіть число!");
        }
    }

    public void setRowCount(int rowCount)
    {
        if (rowCount > 0) this.rowCount = rowCount;
    }

    // --- Магія подій (додаємо, видаляємо і сповіщаємо слухачів) ---
    public void addDataSheetChangeListener(DataSheetChangeListener l)
    {
        listenerList.add(l);
    }

    public void removeDataSheetChangeListener(DataSheetChangeListener l)
    {
        listenerList.remove(l);
    }

    protected void fireDataSheetChange()
    {
        for (DataSheetChangeListener listener : listenerList)
        {
            listener.dataChanged(event);
        }
    }
}