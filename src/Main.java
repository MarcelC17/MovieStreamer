import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import input.DataBase;
import web.WebPage;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        DataBase database = objectMapper.readValue(new File(args[0]), DataBase.class);

        WebPage webPage = new WebPage();
        webPage.setDataBase(database);
        webPage.run();


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]),webPage.getOutput());
    }
}


