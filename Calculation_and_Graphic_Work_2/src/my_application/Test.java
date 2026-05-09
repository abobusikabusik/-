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
        // setup main application window
        setTitle("Розрахунково-графічна робота №2 (JavaBeans)");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // default file chooser to project directory
        fileChooser.setCurrentDirectory(new File("."));

        // initialize empty datasheet with one row
        dataSheet = new DataSheet();
        dataSheet.addDataItem(new Data());

        // create and configure graph component (east)
        dataSheetGraph = new DataSheetGraph();
        dataSheetGraph.setPreferredSize(new Dimension(400, 500));
        dataSheetGraph.setDataSheet(dataSheet); // Передаємо дані графіку
        add(dataSheetGraph, BorderLayout.EAST);

        // create and configure table component (west)
        dataSheetTable = new DataSheetTable();
        dataSheetTable.setPreferredSize(new Dimension(350, 500));
        dataSheetTable.getTableModel().setDataSheet(dataSheet);
        add(dataSheetTable, BorderLayout.WEST);

        // link table model events to graph redraw
        dataSheetTable.getTableModel().addDataSheetChangeListener(new DataSheetChangeListener() {
            @Override
            public void dataChanged(DataSheetChangeEvent e)
            {
                // redraw graph when table data changes
                dataSheetGraph.revalidate();
                dataSheetGraph.repaint();
            }
        });

        // create control panel with buttons (south)
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

        // exit button action
        exitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                dispose(); // close window
            }
        });

        // clear button action
        clearButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dataSheet = new DataSheet();
                dataSheet.addDataItem(new Data()); // add 1 empty row
                dataSheetTable.getTableModel().setDataSheet(dataSheet);
                dataSheetTable.revalidate();
                dataSheetGraph.setDataSheet(dataSheet);
            }
        });

        // save xml button action
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    if (!fileName.endsWith(".xml"))
                    {
                        fileName += ".xml"; // add extension if missing
                    }
                    DataSheetToXML.saveXMLDoc(DataSheetToXML.createDataSheetDOM(dataSheet), fileName);
                    JOptionPane.showMessageDialog(null,
                            "Файл " + fileName.trim() + " успішно збережено!",
                            "Результати збережені",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // open xml button action
        readButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null))
                {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    dataSheet = SAXRead.XMLReadData(fileName); // read file

                    // update ui components with new data
                    dataSheetTable.getTableModel().setDataSheet(dataSheet);
                    dataSheetTable.revalidate();
                    dataSheetGraph.setDataSheet(dataSheet);
                }
            }
        });
    }

    public static void main(String[] args)
    {
        // start application
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Test frame = new Test();
                frame.setLocationRelativeTo(null); // center window on screen
                frame.setVisible(true);
            }
        });
    }
}