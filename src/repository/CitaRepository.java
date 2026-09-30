package repository;

import model.Cita;
import model.Doctor;
import model.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CitaRepository {
    private final String filePath = "db/citas.csv";

    public List<Cita> cargarCitas(List<Doctor> doctores, List<Paciente> pacientes) {
        List<Cita> citas = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return citas;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String idCita = datos[0].trim();
                    String fechaHora = datos[1].trim();
                    String motivo = datos[2].trim();
                    String idDoctor = datos[3].trim();
                    String idPaciente = datos[4].trim();

                    Doctor doc = doctores.stream()
                            .filter(d -> d.getId().equalsIgnoreCase(idDoctor))
                            .findFirst().orElse(null);

                    Paciente pac = pacientes.stream()
                            .filter(p -> p.getId().equalsIgnoreCase(idPaciente))
                            .findFirst().orElse(null);

                    if (doc != null && pac != null) {
                        citas.add(new Cita(idCita, fechaHora, motivo, doc, pac));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer citas.csv: " + e.getMessage());
        }
        return citas;
    }

    public void guardarCitas(List<Cita> citas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Cita c : citas) {
                bw.write(c.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar citas.csv: " + e.getMessage());
        }
    }
}