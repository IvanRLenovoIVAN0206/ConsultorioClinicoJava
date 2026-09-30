package repository;

import model.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private final String filePath = "db/usuarios.csv";

    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(filePath);

        // Si no existe el archivo, creamos un usuario administrador por defecto
        if (!file.exists()) {
            usuarios.add(new Usuario("admin", "1234"));
            guardarUsuarios(usuarios);
            return usuarios;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    usuarios.add(new Usuario(datos[0].trim(), datos[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer usuarios.csv: " + e.getMessage());
        }
        return usuarios;
    }

    public void guardarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Usuario u : usuarios) {
                bw.write(u.getUsername() + "," + u.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios.csv: " + e.getMessage());
        }
    }
}