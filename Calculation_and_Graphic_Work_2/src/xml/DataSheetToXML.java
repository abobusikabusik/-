package xml;

import my_beans.Data;
import my_beans.DataSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DataSheetToXML
{
    public static Document createDataSheetDOM(DataSheet dataSheet)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("dataSheet");
            doc.appendChild(root);

            for (int i = 0; i < dataSheet.size(); i++)
            {
                Data dataItem = dataSheet.getDataItem(i);
                Element dataElement = doc.createElement("data");

                Element dateElem = doc.createElement("date");
                dateElem.appendChild(doc.createTextNode(dataItem.getDate()));
                dataElement.appendChild(dateElem);

                Element xElem = doc.createElement("x");
                xElem.appendChild(doc.createTextNode(String.valueOf(dataItem.getX())));
                dataElement.appendChild(xElem);

                Element yElem = doc.createElement("y");
                yElem.appendChild(doc.createTextNode(String.valueOf(dataItem.getY())));
                dataElement.appendChild(yElem);

                root.appendChild(dataElement);
            }
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveXMLDoc(Document doc, String fileName)
    {
        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
