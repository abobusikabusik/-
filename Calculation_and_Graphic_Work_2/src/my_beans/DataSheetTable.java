package my_beans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataSheetTable extends JPanel
{
    private JTable table;
    private DataSheetTableModel tableModel;
    private JButton addButton;
    private JButton delButton;

    public DataSheetTable()
    {
        // Налаштовуємо компонування панелі
        setLayout(new BorderLayout());

        // Створюємо таблицю та її модель
        table = new JTable();
        tableModel = new DataSheetTableModel();
        table.setModel(tableModel);

        // Панель прокрутки для таблиці
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Панель для кнопок внизу
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 5));
        addButton = new JButton("Add (+)");
        delButton = new JButton("Delete (-)");
        panelButtons.add(addButton);
        panelButtons.add(delButton);
        add(panelButtons, BorderLayout.SOUTH);

        // Обробник кнопки Додати
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                tableModel.setRowCount(tableModel.getRowCount() + 1);
                tableModel.getDataSheet().addDataItem(new Data());
                table.revalidate();
                tableModel.fireDataSheetChange();
            }
        });

        // Обробник кнопки Видалити
        delButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (tableModel.getRowCount() > 1)
                {
                    tableModel.setRowCount(tableModel.getRowCount() - 1);
                    tableModel.getDataSheet().removeDataItem(tableModel.getDataSheet().size() - 1);
                }
                else
                {
                    tableModel.getDataSheet().getDataItem(0).setDate("");
                    tableModel.getDataSheet().getDataItem(0).setX(0);
                    tableModel.getDataSheet().getDataItem(0).setY(0);
                    table.repaint();
                }
                table.revalidate();
                tableModel.fireDataSheetChange();
            }
        });
    }

    public DataSheetTableModel getTableModel()
    {
        return tableModel;
    }

    public void setTableModel(DataSheetTableModel tableModel)
    {
        this.tableModel = tableModel;
        table.setModel(tableModel);
        table.revalidate();
    }

    @Override
    public void revalidate()
    {
        if (table != null) table.revalidate();
        super.revalidate();
    }
}