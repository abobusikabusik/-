package my_application;

import my_beans.Data;
import my_beans.DataSheet;
import my_beans.DataSheetChangeEvent;
import my_beans.DataSheetChangeListener;
import my_beans.DataSheetGraph;
import my_beans.DataSheetTable;
import xml.DataSheetToXML;
import xml.SAXRead;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Test extends JFrame
{
    private DataSheet dataSheet = null;
    private DataSheetTable dataSheetTable;
    private DataSheetGraph dataSheetGraph;
    private final JFileChooser fileChooser = new JFileChooser();

    public Test()
    {
        // Налаштування головного вікна
        setTitle("Розрахунково-графічна робота №2 (JavaBeans)");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Щоб файли шукалися в поточній папці проєкту
        fileChooser.setCurrentDirectory(new File("."));

        // Створюємо початкове порожнє сховище даних з одним рядком
        dataSheet = new DataSheet();
        dataSheet.addDataItem(new Data());

        // 1. Створюємо і налаштовуємо Графік (Східна частина)
        dataSheetGraph = new DataSheetGraph();
        dataSheetGraph.setPreferredSize(new Dimension(400, 500));
        dataSheetGraph.setDataSheet(dataSheet); // Передаємо дані графіку
        add(dataSheetGraph, BorderLayout.EAST);

        // 2. Створюємо і налаштовуємо Таблицю (Західна частина)
        dataSheetTable = new DataSheetTable();
        dataSheetTable.setPreferredSize(new Dimension(350, 500));
        dataSheetTable.getTableModel().setDataSheet(dataSheet); // Передаємо дані таблиці
        add(dataSheetTable, BorderLayout.WEST);

        // ГОЛОВНА МАГІЯ: Зв'язуємо таблицю і графік через нашу Подію!
        dataSheetTable.getTableModel().addDataSheetChangeListener(new DataSheetChangeListener() {
            @Override
            public void dataChanged(DataSheetChangeEvent e)
            {
                // Коли в таблиці щось змінилось - перемальовуємо графік
                dataSheetGraph.revalidate();
                dataSheetGraph.repaint();
            }
        });

        // 3. Створюємо панель з кнопками управління (Південна частина)
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton readButton = new JButton("Open XML");
        JButton saveButton = new JButton("Save XML");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        panelButtons.add(readButton);
        panelButtons.add(saveButton);
        panelButtons.add(clearButton);
        panelButtons.add(exitButton);
        add(panelButtons, BorderLayout.SOUTH);

        // --- ОБРОБНИКИ КНОПОК ---

        // Кнопка Exit
        exitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                dispose(); // Закриває вікно
            }
        });

        // Кнопка Clear (Очистити все)
        clearButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dataSheet = new DataSheet();
                dataSheet.addDataItem(new Data()); // Додаємо 1 пустий рядок
                dataSheetTable.getTableModel().setDataSheet(dataSheet);
                dataSheetTable.revalidate();
                dataSheetGraph.setDataSheet(dataSheet);
            }
        });

        // Кнопка Save XML (Зберегти)
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    if (!fileName.endsWith(".xml"))
                    {
                        fileName += ".xml"; // Додаємо розширення, якщо юзер забув
                    }
                    DataSheetToXML.saveXMLDoc(DataSheetToXML.createDataSheetDOM(dataSheet), fileName);
                    JOptionPane.showMessageDialog(null,
                            "Файл " + fileName.trim() + " успішно збережено!",
                            "Результати збережені",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Кнопка Open XML (Відкрити)
        readButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null))
                {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    dataSheet = SAXRead.XMLReadData(fileName); // Читаємо файл

                    // Оновлюємо таблицю і графік новими даними
                    dataSheetTable.getTableModel().setDataSheet(dataSheet);
                    dataSheetTable.revalidate();
                    dataSheetGraph.setDataSheet(dataSheet);
                }
            }
        });
    }

    public static void main(String[] args)
    {
        // Запускаємо нашу красу!
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Test frame = new Test();
                frame.setLocationRelativeTo(null); // Центруємо вікно на екрані
                frame.setVisible(true);
            }
        });
    }
}