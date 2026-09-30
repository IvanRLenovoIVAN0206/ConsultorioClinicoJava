package service;

import model.Cita;
import model.Doctor;
import model.Paciente;
import model.Usuario;
import repository.CitaRepository;
import repository.DoctorRepository;
import repository.PacienteRepository;
import repository.UsuarioRepository;

import java.util.List;

public class ConsultorioService {
    private final DoctorRepository doctorRepo = new DoctorRepository();
    private final PacienteRepository pacienteRepo = new PacienteRepository();
    private final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private final CitaRepository citaRepo = new CitaRepository();

    private List<Doctor> doctores;
    private List<Paciente> pacientes;
    private List<Usuario> usuarios;
    private List<Cita> citas;

    public ConsultorioService() {
        this.doctores = doctorRepo.cargarDoctores();
        this.pacientes = pacienteRepo.cargarPacientes();
        this.usuarios = usuarioRepo.cargarUsuarios();
        this.citas = citaRepo.cargarCitas(doctores, pacientes);
    }

    public boolean autenticar(String username, String password) {
        return usuarios.stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }

    public void registrarDoctor(String id, String nombre, String especialidad) {
        Doctor doc = new Doctor(id, nombre, especialidad);
        doctores.add(doc);
        doctorRepo.guardarDoctores(doctores);
    }

    public void registrarPaciente(String id, String nombre) {
        Paciente pac = new Paciente(id, nombre);
        pacientes.add(pac);
        pacienteRepo.guardarPacientes(pacientes);
    }

    public boolean registrarCita(String idCita, String fechaHora, String motivo, String idDoctor, String idPaciente) {
        Doctor doc = doctores.stream().filter(d -> d.getId().equalsIgnoreCase(idDoctor)).findFirst().orElse(null);
        Paciente pac = pacientes.stream().filter(p -> p.getId().equalsIgnoreCase(idPaciente)).findFirst().orElse(null);

        if (doc == null || pac == null) {
            return false;
        }

        Cita cita = new Cita(idCita, fechaHora, motivo, doc, pac);
        citas.add(cita);
        citaRepo.guardarCitas(citas);
        return true;
    }

    public List<Doctor> getDoctores() { return doctores; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Cita> getCitas() { return citas; }
}