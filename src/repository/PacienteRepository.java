package repository;

import model.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    private final String filePath = "db/pacientes.csv";

    public List<Paciente> cargarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return pacientes;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    pacientes.add(new Paciente(datos[0].trim(), datos[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer pacientes.csv: " + e.getMessage());
        }
        return pacientes;
    }

    public void guardarPacientes(List<Paciente> pacientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Paciente p : pacientes) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar pacientes.csv: " + e.getMessage());
        }
    }
}