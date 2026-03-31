package finder;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Comparator;

// task 4: class to read xml, filter, sort and create new xml
public class xml_processor
{
    public static void main(String[] args)
    {
        try
        {
            List<baby_name> names_list = new ArrayList<>();
            Set<String> seen_names = new HashSet<>();
            int target_amount = 15;
            String target_ethnicity = "hispanic";

            // 1. sax parsing
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sax_parser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler()
            {
                String current_tag = "";
                String name, gender, ethnicity;
                int count, rank;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                {
                    current_tag = qName.toLowerCase();
                }

                @Override
                public void characters(char[] ch, int start, int length)
                {
                    String data = new String(ch, start, length).trim();
                    if (data.isEmpty()) return;

                    switch (current_tag)
                    {
                        case "nm": name = data.toLowerCase(); break;
                        case "gndr": gender = data.toLowerCase(); break;
                        case "ethcty": ethnicity = data.toLowerCase(); break;
                        case "cnt": count = Integer.parseInt(data); break;
                        case "rnk": rank = Integer.parseInt(data); break;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName)
                {
                    if (qName.equalsIgnoreCase("row") && ethnicity != null && ethnicity.equals(target_ethnicity)) {

                        // ДОДАЛИ ПЕРЕВІРКУ: чи є вже таке ім'я в нашому Set? (!seen_names.contains(name))
                        if (rank <= target_amount && !seen_names.contains(name))
                        {
                            names_list.add(new baby_name(name, gender, count, rank));
                            seen_names.add(name); // запам'ятовуємо, що це ім'я вже взяли
                        }

                        ethnicity = null;
                        name = null;
                        gender = null;
                        count = 0;
                        rank = 0;
                    }
                    current_tag = "";
                }
            };

            sax_parser.parse(new File("Popular_Baby_Names_NY.xml"), handler);

            // 2. sorting by rank
            names_list.sort(Comparator.comparingInt(baby_name::getRank));

            System.out.println("\ntop " + target_amount + " names for a group: " + target_ethnicity.toUpperCase() + "\n");
            for (int i = 0; i < Math.min(target_amount, names_list.size()); i++)
            {
                baby_name b = names_list.get(i);
                System.out.println("rank: " + b.getRank() + " | name: " + b.getName() + " (" + b.getGender() + ") | amount: " + b.getCount());
            }
            System.out.println("--------------------------------------------------\n");

            // 3. dom creation for top 15 names
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("popular_names");
            doc.appendChild(root);

            for (int i = 0; i < Math.min(target_amount, names_list.size()); i++)
            {
                baby_name b = names_list.get(i);
                Element row = doc.createElement("baby");

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(b.getName()));
                row.appendChild(name);

                Element gender = doc.createElement("gender");
                gender.appendChild(doc.createTextNode(b.getGender()));
                row.appendChild(gender);

                Element count = doc.createElement("count");
                count.appendChild(doc.createTextNode(String.valueOf(b.getCount())));
                row.appendChild(count);

                Element rank = doc.createElement("rank");
                rank.appendChild(doc.createTextNode(String.valueOf(b.getRank())));
                row.appendChild(rank);

                root.appendChild(row);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(new File("top_names.xml")));

            System.out.println("new xml created!");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
