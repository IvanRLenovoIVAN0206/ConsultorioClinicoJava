package repository;

import model.Doctor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {
    private final String filePath = "db/doctores.csv";

    public List<Doctor> cargarDoctores() {
        List<Doctor> doctores = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return doctores;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    doctores.add(new Doctor(datos[0].trim(), datos[1].trim(), datos[2].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer doctores.csv: " + e.getMessage());
        }
        return doctores;
    }

    public void guardarDoctores(List<Doctor> doctores) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Doctor d : doctores) {
                bw.write(d.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar doctores.csv: " + e.getMessage());
        }
    }
}