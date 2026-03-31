package finder;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

// task 2: class to validate xml using xsd schema
public class xml_validator
{
    public static void main(String[] args)
    {
        try
        {
            String xml_path = "Popular_Baby_Names_NY.xml";
            String xsd_path = "src/baby_scheme.xsd";

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsd_path));
            Validator validator = schema.newValidator();

            // performing validation
            validator.validate(new StreamSource(new File(xml_path)));
            System.out.println("validation successful: xml matches the schema");

        }
        catch (Exception e)
        {
            System.out.println("validation failed: " + e.getMessage());
        }
    }
}
