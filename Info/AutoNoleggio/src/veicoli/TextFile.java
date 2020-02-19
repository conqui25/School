/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veicoli;

import java.io.*;

/**
 *
 * @author 75662330
 */
public class TextFile {

    private char mode;
    private BufferedReader reader;
    private BufferedWriter writer;

    public TextFile(String fileName, char mode) throws IOException {
        this.mode = 'R';
        if (mode == 'W' || mode == 'w') {
            this.mode = 'W';
            writer = new BufferedWriter(new FileWriter(fileName));
            return;
        }
        reader = new BufferedReader(new FileReader(fileName));
    }

    public boolean toFile(String line) throws FileException, IOException {
        if (this.mode == 'R') {
            throw new FileException("Read-only" + " file!");
        }

        writer.write(line);

        writer.newLine();

        return true;
    }

    public String fromFile() throws FileException, IOException {
        String tmp;
        if (this.mode == 'W') {
            throw new FileException("Write-only" + " file!");
        }
        tmp = reader.readLine();
        if (tmp == null) {
            throw new FileException("End of file!");
        }
        return tmp;
    }

    public void closeFile() throws IOException {
        if (this.mode == 'W') {
            writer.close();
        } else {
            reader.close();
        }
    }

    public boolean empty() throws FileException, IOException {
        if (this.mode == 'W') {
            throw new FileException("Write-only" + " file!");
        }
        return reader.ready();
    }

}
