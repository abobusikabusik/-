package finder;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

// task 1: class for finding all unique tags in the xml file
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
                int elements_printed = 0;
                boolean print_mode = true;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                {
                    unique_tags.add(qName);

                    if (print_mode)
                    {
                        System.out.println("<" + qName + ">");
                        elements_printed++;

                        // stopping printing after 30 tag
                        if (elements_printed > 30)
                        {
                            print_mode = false;
                            System.out.println("\n... printing stopped ...\n");
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length)
                {
                    // printing text (inside tags)
                    if (print_mode)
                    {
                        String data = new String(ch, start, length).trim();
                        if (!data.isEmpty())
                        {
                            System.out.println(data);
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName)
                {
                    // printing closing tag
                    if (print_mode)
                    {
                        System.out.println("</" + qName + ">");
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
