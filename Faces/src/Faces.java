import java.io.*;

public class Faces {
	private String inputFileName = "text.txt";
	private String outputFileName = "generiert.txt";
	private StringBuffer output = new StringBuffer();
	private int degFaces = 0;

	public Faces() {
	}

	public Faces(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public Faces(String inputFileName, String outputFileName) {
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
	}

	public int getDegFaces() {
		return this.degFaces;
	}

	// Einlesen der ursprünglichen Datei mit degenerierten Dreiecken
	public String read() {
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(inputFileName));
			String line = null;
			while ((line = in.readLine()) != null) {
				output.append(proofLine(line)); // an Ausgabe unter speziellen
												// Bed. anfügen (siehe
												// prrofLine)
			}
		} catch (IOException e) {
			System.out.println("Textdatei " + inputFileName + " fehlt!");
		}
		return output.toString();
	}

	private String proofLine(String line) {
		String[] v1, v2, v3, geteilt;

		// line muss "f x1/y1/z1 x2/y2/z2 /x3/y3/z3" als form haben
		if (line.matches("[f] \\w+/\\w+/\\w+ \\w+/\\w+/\\w+ \\w+/\\w+/\\w+")) {
			geteilt = line.split(" ");
			v1 = geteilt[1].split("/");
			v2 = geteilt[2].split("/");
			v3 = geteilt[3].split("/");

			// vergleichen von x1-x3
			if (v1[0].equals(v2[0]) || v1[0].equals(v3[0])
					|| v2[0].equals(v3[0])) {
				this.degFaces++;
			} else {
				return line + "\n";
			}
		}
		return line + "\n";

	}

	// in Datei "outputFileName" schreiben
	public void writeText(String text) {
		try {
			File file = new File(outputFileName);
			FileWriter fw = new FileWriter(file);
			fw.write(text);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Faces f = new Faces();
		if (args.length == 2) {
			f = new Faces(args[0], args[1]);
		} else if (args.length == 1) {
			f = new Faces(args[0]);
		}
		f.writeText(f.read());
		System.out.println(f.getDegFaces());

	}
}
