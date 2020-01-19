package gameClient;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import utils.Point3D;

public class KML_Logger {

	private int num_level;
	private StringBuilder information;

	private final String Folder = "data/";
	private final String Kml = ".kml";

	private static KML_Logger logger = null;

	private KML_Logger(int num_level) {
		this.num_level = num_level;
		information = new StringBuilder();
		start();
		
	}

	//starts 
	public void start() {
		 information.append(
	                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
	                        "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
	                        "  <Document>\r\n" +
	                        "    <name>Scenario number " + this.num_level + "</name>" +
	                        " <Style id=\"node\">\r\n" +
	                        "      <IconStyle>\r\n" +
	                        "        <Icon>\r\n" +
	                        "          <href>http://maps.google.com/mapfiles/kml/pushpin/wht-pushpin.png</href>\r\n" +
	                        "        </Icon>\r\n" +
	                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
	                        "      </IconStyle>\r\n" +
	                        "    </Style>" +
	                        " <Style id=\"banana\">\r\n" +
	                        "      <IconStyle>\r\n" +
	                        "        <Icon>\r\n" +
	                        "          <href>http://maps.google.com/mapfiles/kml/pal5/icon57.png</href>\r\n" +
	                        "        </Icon>\r\n" +
	                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
	                        "      </IconStyle>\r\n" +
	                        "    </Style>" +
	                        " <Style id=\"apple\">\r\n" +
	                        "      <IconStyle>\r\n" +
	                        "        <Icon>\r\n" +
	                        "          <href>http://maps.google.com/mapfiles/kml/pal5/icon56.png</href>\r\n" +
	                        "        </Icon>\r\n" +
	                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
	                        "      </IconStyle>\r\n" +
	                        "    </Style>" +
	                        " <Style id=\"robot\">\r\n" +
	                        "      <IconStyle>\r\n" +
	                        "        <Icon>\r\n" +
	                        "          <href>http://maps.google.com/mapfiles/kml/pal4/icon57.png</href>\r\n" +
	                        "        </Icon>\r\n" +
	                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
	                        "      </IconStyle>\r\n" +
	                        "    </Style>\r\n"
	        );
	}

	public void addMark(String type, Point3D location) {
			
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		String time = sdf.format(date);
		
		information.append("<Placemark>\r\n" + 
				"      <TimeStamp>\r\n" + 
				"        <when>"+time+"</when>\r\n" + 
				"      </TimeStamp>\r\n" + 
				"      <styleUrl>#"+type+"</styleUrl>\r\n" + 
				"      <Point>\r\n" + 
				"        <coordinates>"+location.toString()+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>\r\n"
				);
	}

	public static KML_Logger getVariable(int num_level) {
			if(logger == null) {
				logger = new KML_Logger(num_level);
			}
			
			return logger;
	}	

	//write to a file
	public void export() {

		File file = new File(Folder+num_level+Kml);
    	try {
			PrintWriter pw = new PrintWriter(file);
			pw.write(information.toString());
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	//closes file
	public void end() {
		information.append(
    			"  </Document>\r\n" + 
    			"</kml>"
    			);
	}
}
