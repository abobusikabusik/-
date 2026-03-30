package finder;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ethnicity_finder
{
    public static void main(String[] args)
    {
        try
        {
            File inputfile = new File("Popular_Baby_Names_NY.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxparser = factory.newSAXParser();

            Set<String> ethnic_groups = new HashSet<>(); // no dublicates

            DefaultHandler handler = new DefaultHandler()
            {
                boolean is_ethnicity = false; // if we're in tag <ethcty>

                // tags
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                {
                    // checking, ignoring case
                    if (qName.equalsIgnoreCase("ethcty"))
                    {
                        is_ethnicity = true; // found tag <ethcty>
                    }
                }

                // usual text, inside tags
                @Override
                public void characters(char[] ch, int start, int length)
                {
                    if (is_ethnicity)
                    {
                        String data = new String(ch, start, length);
                        if (!data.isEmpty())
                        {
                            ethnic_groups.add(data);
                        }
                    }
                }

                // finishing text inside tags
                @Override
                public void endElement(String uri, String localName, String qName)
                {
                    if (qName.equalsIgnoreCase("ethcty"))
                    {
                        is_ethnicity = false;
                    }
                }
            };

            saxparser.parse(inputfile, handler);

            System.out.println("unique ethnic groups found: ");
            for (String group : ethnic_groups)
            {
                System.out.println(group);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
