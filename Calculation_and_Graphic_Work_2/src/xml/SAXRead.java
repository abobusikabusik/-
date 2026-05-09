package xml;

import my_beans.Data;
import my_beans.DataSheet;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXRead
{
    // parse xml file and convert to datasheet object
    public static DataSheet XMLReadData(String fileName)
    {
        DataSheet dataSheet = new DataSheet();
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler()
            {
                Data currentData = null;
                String currentElement = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                {
                    currentElement = qName;
                    if (currentElement.equals("data"))
                    {
                        currentData = new Data();
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length)
                {
                    String value = new String(ch, start, length).trim();
                    if (value.isEmpty() || currentData == null) return;

                    switch (currentElement)
                    {
                        case "date": currentData.setDate(value); break;
                        case "x": currentData.setX(Double.parseDouble(value)); break;
                        case "y": currentData.setY(Double.parseDouble(value)); break;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName)
                {
                    if (qName.equals("data"))
                    {
                        dataSheet.addDataItem(currentData);
                    }
                    currentElement = "";
                }
            };

            parser.parse(new java.io.File(fileName), handler);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return dataSheet;
    }
}
