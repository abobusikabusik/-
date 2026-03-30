package finder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

// task 5: read the new xml file
public class xml_reader
{
    public static void main(String[] args)
    {
        try
        {
            File inputfile = new File("top_names.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputfile);

            System.out.println("root: " + doc.getDocumentElement().getNodeName());

            NodeList list = doc.getElementsByTagName("baby");

            for (int i = 0; i < list.getLength(); i++)
            {
                Element element = (Element) list.item(i);
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String gender = element.getElementsByTagName("gender").item(0).getTextContent();
                String count = element.getElementsByTagName("count").item(0).getTextContent();
                String rank = element.getElementsByTagName("rank").item(0).getTextContent();

                System.out.println("rank " + rank + " | name: " + name + " (" + gender + "), count: " + count);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
