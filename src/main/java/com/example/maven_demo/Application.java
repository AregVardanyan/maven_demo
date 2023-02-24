package com.example.maven_demo;

import com.example.maven_demo.manager.DemandManager;
import com.example.maven_demo.manager.DoctorManager;
import com.example.maven_demo.manager.PacientManager;
import com.example.maven_demo.manager.impl.DemandManagerImpl;
import com.example.maven_demo.manager.impl.DoctorManagerImpl;
import com.example.maven_demo.manager.impl.PacientManagerImpl;
import com.example.maven_demo.models.Doctor;
import com.example.maven_demo.models.Pacient;
import com.example.maven_demo.models.TimeInterval;
import com.example.maven_demo.models.enums.Gender;
import com.example.maven_demo.models.enums.WorkStatus;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Application {

    private Pacient pacient;

    private Doctor doctor;

    private final static Scanner scanner = new Scanner(System.in);

    private final DoctorManager doctorManager = new DoctorManagerImpl();

    private final DemandManager demandManager = new DemandManagerImpl();

    private final PacientManager pacientManager = new PacientManagerImpl();


    public void start(){
        welcomePage();
        String command = scanner.nextLine();
        switch (command) {
            case "0": {
                exit();
                break;
            }

            case "1": {
                loginAsDoctor();
                break;
            }
            case "2": {
                registerAsDoctor();
                break;
            }
            case "3": {
                loginAsPacient();
                break;
            }
            case "4": {
                registerAsPacient();
                break;
            }
            default: {
                start();
            }
        }
    }

    private void registerAsPacient() {
        System.out.println("Input your email");
        String email = scanner.nextLine();
        while (pacientManager.existByEmail(email)) {
            System.out.println("Email already used");
            System.out.println("Input your email");
            email = scanner.nextLine();
        }

        System.out.println("Input your name");
        String name = scanner.nextLine();

        System.out.println("Input your surname");
        String surname = scanner.nextLine();

        System.out.println("Input your password");
        String password = scanner.nextLine();

        System.out.println("Input your gender");
        String gender = scanner.nextLine();

        System.out.println("Input your age");
        int age = Integer.parseInt(scanner.nextLine());

        pacient = pacientManager.save(Pacient.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .gender(Gender.valueOf(gender))
                .age(age)
                .build());
        pacientHome();
    }

    private void registerAsDoctor() {
        System.out.println("Input your email");
        String email = scanner.nextLine();
        while (doctorManager.existByEmail(email)) {
            System.out.println("Email already used");
            System.out.println("Input your email");
            email = scanner.nextLine();
        }

        System.out.println("Input your name");
        String name = scanner.nextLine();

        System.out.println("Input your surname");
        String surname = scanner.nextLine();

        System.out.println("Input your password");
        String password = scanner.nextLine();

        while (true) {
            System.out.println("Input your rest times 1 ");
            System.out.println("Continue 2");
            String command = scanner.nextLine();
            switch (command){
                case "1":{
                    while (true){
                        System.out.println("start hour");
                        String s = scanner.nextLine();
                        System.out.println("end hour");
                        String e = scanner.nextLine();
                        try {

                            Time start = new Time(LocalDateTime.now().);
                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    doctorManager.setRestWorkTime(TimeInterval.builder()
                            .doctorId(doctor.getId())
                            .start(start)
                            .end(end)
                            .workStatus(WorkStatus.REST));
                }
                case "2":{
                    doctor = doctorManager.save(Doctor.builder()
                            .name(name)
                            .surname(surname)
                            .email(email)
                            .password(password)
                            .build());
                    //doctorHome();
                }
            }
        }
    }

    private void loginAsPacient() {
        System.out.println("Input your email");
        String email = scanner.nextLine();

        System.out.println("Input your password");
        String password = scanner.nextLine();
        pacient = pacientManager.getByEmailAndPassword(email, password);
        if (pacient == null) {
            System.out.println("Incorrect email or password");
            start();
        } else {
            pacientHome();
        }
    }

    private void loginAsDoctor() {
        System.out.println("Input your email");
        String email = scanner.nextLine();

        System.out.println("Input your password");
        String password = scanner.nextLine();
        doctor = doctorManager.getByEmailAndPassword(email, password);
        if (doctor == null) {
            System.out.println("Incorrect email or password");
            start();
        } else {
            //doctorHome();
        }
    }
    private void pacientHome(){
        pacientPage();
        String command = scanner.nextLine();
        switch (command) {
            case "0": {
                exit();
                break;
            }

            case "1": {
                pacient = null;
                start();
                break;
            }
            case "2": {
                //doctorview();
                break;
            }
            case "3": {
                loginAsPacient();
                break;
            }
            case "4": {
                registerAsPacient();
                break;
            }
            default: {
                start();
            }
        }
    }

    private void exit() {
        System.out.println("By ... ");
        System.exit(0);
    }

    private void welcomePage() {
        System.out.println("For exit press 0");
        System.out.println("For login as doctor 1");
        System.out.println("For registration as doctor 2");
        System.out.println("For login as pacient 3");
        System.out.println("For registration as pacient 4");
    }

    private void pacientPage() {
        System.out.println("For exit press 0");
        System.out.println("For logout 1.");
        System.out.println("Doctor view 2");
        System.out.println("For demands view 3");
    }

}
