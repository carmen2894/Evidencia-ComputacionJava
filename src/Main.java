import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;


class SistemaCitas {
    private List<Doctor> doctores;
    private List<Paciente> pacientes;
    private List<Cita> citas;
    private String dbFolder = "db/";
    private String usuarioAdmin = "admi";
    private String contraseñaAdmin = "admi123";

    public SistemaCitas() {
        this.doctores = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public boolean login(String usuario, String contraseña) {
        if (usuario.equals(usuarioAdmin) && contraseña.equals(contraseñaAdmin)) {
            return true;
        } else {
            return false;
        }
    }


    public void agregarDoctor(Doctor doctor) {
        doctores.add(doctor);
        guardarDatos(doctor, "doctor_" + doctor.getId() + ".txt");
    }

    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
        guardarDatos(paciente, "paciente_" + paciente.getId() + ".txt");
    }

    public void crearCita(Date fechaHora, String motivo, int doctorId, int pacienteId) {
        Doctor doctor = buscarDoctorPorId(doctorId);
        Paciente paciente = buscarPacientePorId(pacienteId);
        if (doctor != null && paciente != null) {
            Cita cita = new Cita(citas.size() + 1, fechaHora, motivo, doctor, paciente);
            citas.add(cita);
            guardarDatos(cita, "cita_" + cita.getId() + ".txt");
        } else {
            System.out.println("Doctor o paciente no encontrado.");
        }
    }

    private Doctor buscarDoctorPorId(int id) {
        for (Doctor doctor : doctores) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        return null;
    }

    private Paciente buscarPacientePorId(int id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId() == id) {
                return paciente;
            }
        }
        return null;
    }

    private void guardarDatos(Object objeto, String nombreArchivo) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dbFolder + nombreArchivo));
            outputStream.writeObject(objeto);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        SistemaCitas sistema = new SistemaCitas();
        Scanner scanner = new Scanner(System.in);

        System.out.println("------------- Bienvenido al sistema de citas -------------");
        System.out.println("Por favor, ingrese sus credenciales:");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        if (sistema.login(usuario, contraseña)) {




            System.out.println("Ingrese los datos de los doctores:");
            for (int i = 1; i <= 2; i++) {
                System.out.println("Doctor " + i + ":");
                System.out.print("ID: ");
                int idDoctor = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nombre: ");
                String nombreDoctor = scanner.nextLine();
                System.out.print("Especialidad: ");
                String especialidadDoctor = scanner.nextLine();
                Doctor doctor = new Doctor(idDoctor, nombreDoctor, especialidadDoctor);
                sistema.agregarDoctor(doctor);
            }


            System.out.println("Ingrese los datos de los pacientes:");
            for (int i = 1; i <= 2; i++) {
                System.out.println("Paciente " + i + ":");
                System.out.print("ID: ");
                int idPaciente = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada
                System.out.print("Nombre: ");
                String nombrePaciente = scanner.nextLine();
                Paciente paciente = new Paciente(idPaciente, nombrePaciente);
                sistema.agregarPaciente(paciente);
            }


            System.out.println("Ingrese los datos de las citas:");
            for (int i = 1; i <= 2; i++) {
                System.out.println("Cita " + i + ":");
                System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
                String fechaHoraString = scanner.nextLine();
                System.out.print("Motivo: ");
                String motivo = scanner.nextLine();
                System.out.print("ID del doctor: ");
                int idDoctorCita = scanner.nextInt();
                System.out.print("ID del paciente: ");
                int idPacienteCita = scanner.nextInt();
                scanner.nextLine();


                try {

                    Date fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fechaHoraString);
                    sistema.crearCita(fechaHora, motivo, idDoctorCita, idPacienteCita);
                } catch (ParseException e) {
                    System.out.println("Error: El formato de fecha y hora no es válido. Por favor, ingrese una fecha y hora en el formato yyyy-MM-dd HH:mm.");

                }
            }

        } else {

            System.out.println("Acceso denegado. Usuario o contraseña incorrectos.");
        }
    }
}