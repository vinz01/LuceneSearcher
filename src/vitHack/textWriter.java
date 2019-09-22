package vitHack;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class textWriter {

    private class GuiOutputStream extends OutputStream {
        JTextArea textArea;

        public GuiOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int data) throws IOException {
            textArea.append(new String(new byte[] { (byte) data }));
        }
    }

    public void guiConsoleTest() throws IOException {
        System.out.println("Normal java console output");

        JTextArea textArea = new JTextArea(); // Output text area

        // Remember old output stream (optional)
        // Stream for output to gui
        GuiOutputStream rawout = new GuiOutputStream(textArea);
        // Set new stream for System.out
        System.setOut(new PrintStream(rawout, true));

        // Demo gui
        JFrame window = new JFrame("Console test 2");
        window.add(new JScrollPane(textArea));
        window.setSize(1400, 800);
        window.setVisible(true);
        
        
        
        
        
        FileInputStream fstream = new FileInputStream("gui.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        try {
			while ((strLine = br.readLine()) != null)   {
			 
			  System.out.println (strLine);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        //Close the input stream
        fstream.close();
        

        // Clean up and exit

    }

}