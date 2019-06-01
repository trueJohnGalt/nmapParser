import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class Main {

    public static void main(String[] args) {
        try {
            File inputFile = new File("result.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            Element command = (Element) doc.getElementsByTagName("nmaprun").item(0);
            Element ip = (Element) doc.getElementsByTagName("address").item(0);
            Element os = (Element) doc.getElementsByTagName("script").item(1);
            Element ssh_port = (Element) doc.getElementsByTagName("port").item(0);
            Element ssh_version = (Element) doc.getElementsByTagName("service").item(0);
            Element web_access = (Element) doc.getElementsByTagName("service").item(2);
            Element web_access_port = (Element) doc.getElementsByTagName("port").item(2);
            HashMap<String, String> open_ports = new HashMap<>();

            for (int i = 0; i < 6; i++) {
                Element temp_port = (Element) doc.getElementsByTagName("port").item(i);
                Element temp_status = (Element) doc.getElementsByTagName("state").item(i);
                Element temp_service = (Element) doc.getElementsByTagName("service").item(i);

                if (temp_status.getAttribute("state").equals("open")) {
                    open_ports.put(temp_port.getAttribute("portid"), temp_service.getAttribute("name"));
                }
            }

            Iterator iterator = open_ports.keySet().iterator();
            System.out.println();
            System.out.println("1. Команда для виконання сканування портів: " + command.getAttribute("args"));
            System.out.println("2. IP-адреса хосту: " + ip.getAttribute("addr"));
            System.out.println("3. OS: " + os.getAttribute("output"));
            System.out.println("4. Відкриті порти та сервіси на них: ");
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                String value = open_ports.get(key).toString();

                System.out.println("Порт: " + key + " Сервіс: " + value);
            }
            System.out.println("5. SSH порт: " + ssh_port.getAttribute("portid"));
            System.out.println("SSH версія: " + ssh_version.getAttribute("version"));
            System.out.println("6. Технологія доступу до web ресурсів: " + web_access.getAttribute("name"));
            System.out.println("Порт технології доступу до web ресурсів: " + web_access_port.getAttribute("portid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

