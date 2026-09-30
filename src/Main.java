import model.Cita;
import model.Doctor;
import model.Paciente;
import service.ConsultorioService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsultorioService service = new ConsultorioService();

        System.out.println("==========================================");
        System.out.println("   SISTEMA DE CONSULTORIO CLÍNICO JAVA   ");
        System.out.println("==========================================");

        // Control de acceso (Login)
        boolean autenticado = false;
        int intentos = 0;

        while (!autenticado && intentos < 3) {
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine().trim();
            System.out.print("Contraseña: ");
            String password = scanner.nextLine().trim();

            if (service.autenticar(usuario, password)) {
                autenticado = true;
                System.out.println("\n¡Acceso concedido! Bienvenido al sistema.");
            } else {
                intentos++;
                System.out.println("Credenciales incorrectas. Intentos restantes: " + (3 - intentos));
            }
        }

        if (!autenticado) {
            System.out.println("Número máximo de intentos alcanzado. Cerrando el programa...");
            return;
        }

        // Menú Principal
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Dar de alta a un Doctor");
            System.out.println("2. Dar de alta a un Paciente");
            System.out.println("3. Crear una Cita");
            System.out.println("4. Mostrar todas las Citas");
            System.out.println("5. Mostrar Doctores y Pacientes");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                switch (opcion) {
                    case 1:
                        System.out.print("ID del Doctor: ");
                        String idDoc = scanner.nextLine().trim();
                        System.out.print("Nombre completo: ");
                        String nomDoc = scanner.nextLine().trim();
                        System.out.print("Especialidad: ");
                        String espDoc = scanner.nextLine().trim();

                        service.registrarDoctor(idDoc, nomDoc, espDoc);
                        System.out.println(" Doctor registrado con éxito.");
                        break;

                    case 2:
                        System.out.print("ID del Paciente: ");
                        String idPac = scanner.nextLine().trim();
                        System.out.print("Nombre completo: ");
                        String nomPac = scanner.nextLine().trim();

                        service.registrarPaciente(idPac, nomPac);
                        System.out.println(" Paciente registrado con éxito.");
                        break;

                    case 3:
                        System.out.print("ID de la Cita: ");
                        String idCita = scanner.nextLine().trim();
                        System.out.print("Fecha y Hora (ej. 2026-03-01 10:00): ");
                        String fechaHora = scanner.nextLine().trim();
                        System.out.print("Motivo de consulta: ");
                        String motivo = scanner.nextLine().trim();
                        System.out.print("ID del Doctor asignado: ");
                        String docId = scanner.nextLine().trim();
                        System.out.print("ID del Paciente asignado: ");
                        String pacId = scanner.nextLine().trim();

                        boolean exito = service.registrarCita(idCita, fechaHora, motivo, docId, pacId);
                        if (exito) {
                            System.out.println(" Cita registrada con éxito.");
                        } else {
                            System.out.println(" Error: Verifique que el ID del Doctor y Paciente existan.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- HISTORIAL DE CITAS ---");
                        if (service.getCitas().isEmpty()) {
                            System.out.println("No hay citas registradas.");
                        } else {
                            for (Cita c : service.getCitas()) {
                                System.out.println("Cita ID: " + c.getId() + " | Fecha: " + c.getFechaHora() +
                                        " | Motivo: " + c.getMotivo() +
                                        " | Doctor: " + c.getDoctor().getNombreCompleto() +
                                        " | Paciente: " + c.getPaciente().getNombreCompleto());
                            }
                        }
                        break;

                    case 5:
                        System.out.println("\n--- DOCTORES ---");
                        for (Doctor d : service.getDoctores()) {
                            System.out.println("ID: " + d.getId() + " - " + d.getNombreCompleto() + " (" + d.getEspecialidad() + ")");
                        }
                        System.out.println("\n--- PACIENTES ---");
                        for (Paciente p : service.getPacientes()) {
                            System.out.println("ID: " + p.getId() + " - " + p.getNombreCompleto());
                        }
                        break;

                    case 6:
                        System.out.println("Guardando cambios y saliendo... ¡Hasta luego!");
                        break;

                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Error: Por favor ingresa un número válido.");
            }
        } while (opcion != 6);

        scanner.close();
    }
}