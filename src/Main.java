import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public Main() {
    }
    public static void GenerarHTML(String csvFilePath, String htmlTemplatePath) {
        String outputDir = "salida";
        File directory = new File(outputDir);
        if (directory.exists()) {
            File[] v4 = directory.listFiles();
            int v5 = v4.length;

            for(int v6 = 0; v6 < v5; ++v6) {
                File file = v4[v6];
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        } else {
            directory.mkdir();
        }
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            try {
                List<String[]> peliculas = new ArrayList();

                String row;
                while((row = csvReader.readLine()) != null) {
                    String[] datos = row.split(",");
                    peliculas.add(datos);
                }
                String plantillaHTML = new String(Files.readAllBytes(Paths.get(htmlTemplatePath)));
                Iterator v8 = peliculas.iterator();
                while(v8.hasNext()) {
                    String[] pelicula = (String[])v8.next();
                    String htmlGenerado = plantillaHTML;
                    for(int n = 0; n < pelicula.length; ++n) {
                        htmlGenerado = htmlGenerado.replace("%%" + (n + 1) + "%%", pelicula[n]);
                    }
                    String v10000 = pelicula[1].replace(" ", "_");
                    String nombreArchivo = v10000 + "_" + pelicula[0] + ".html";
                    FileWriter fileWriter = new FileWriter(outputDir + "/" + nombreArchivo);
                    fileWriter.write(htmlGenerado);
                    fileWriter.close();
                }
                System.out.println("Se han generado los archivos en la carpeta 'salida'.");
            } catch (Throwable v14) {
                try {
                    csvReader.close();
                } catch (Throwable v13) {
                    v14.addSuppressed(v13);
                }
                throw v14;
            }
            csvReader.close();
        } catch (IOException v15) {
            v15.printStackTrace();
        }

    }
    public static void main(String[] args) {
        String csvFilePath = "peliculas.csv";
        String htmlTemplatePath = "template.html";
        GenerarHTML(csvFilePath, htmlTemplatePath);
    }
}