package finder;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

// class for finding all unique tags in the xml file
public class sax_tag_finder
{
    public static void main(String[] args)
    {
        try
        {
            // path to the xml file
            File input_file = new File("Popular_Baby_Names_NY.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sax_parser = factory.newSAXParser();

            // set to store unique tag names (no dublicates)
            Set<String> unique_tags = new HashSet<>();

            DefaultHandler handler = new DefaultHandler()
            {
                int count = 0; // how much tags

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                {
                    unique_tags.add(qName);

                    // print first 50 tags to see the structure
                    if (count < 50)
                    {
                        System.out.println("tag found: <" + qName + ">");
                        count++;
                    }
                }
            };

            sax_parser.parse(input_file, handler);

            System.out.println("\nlist of all unique tags: ");
            for (String tag : unique_tags)
            {
                System.out.println(tag);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
