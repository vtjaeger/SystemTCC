package com.tcc.service;

import com.tcc.dtos.request.user.LoginRequest;
import com.tcc.dtos.request.user.UserRequest;
import com.tcc.models.*;
import com.tcc.repository.AlunoRepository;
import com.tcc.repository.CoordenadorRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.repository.UserRepository;
import com.tcc.security.TokenService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private CoordenadorRepository coordenadorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<User> saveUser(UserRequest userRequest) {
        String encryptedPassword = passwordEncoder.encode(userRequest.password());
        User user = new User(userRequest.login(), encryptedPassword);
        user.setEnabled(true);
        user.setRole(userRequest.role());
        user = userRepository.save(user);

        switch (userRequest.role()) {
            case ALUNO -> {
                Aluno aluno = new Aluno();
                aluno.setUser(user);
                aluno.setLogin(user.getLogin());
                alunoRepository.save(aluno);
                user.setAluno(aluno);
            }
            case PROFESSOR -> {
                Professor professor = new Professor();
                professor.setUser(user);
                professor.setLogin(user.getLogin());
                professorRepository.save(professor);
                user.setProfessor(professor);
            }
            case COORDENADOR -> {
                Coordenador coordenador = new Coordenador();
                coordenador.setUser(user);
                coordenador.setLogin(user.getLogin());
                coordenadorRepository.save(coordenador);
                user.setCoordenador(coordenador);
            }
            case ADMIN -> {
                user.setRole(UserRole.ADMIN);
            }
            default -> throw new IllegalArgumentException("invalid role");
        }
        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity getUsers(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok().body(userList);
    }

    public ResponseEntity inactiveOrActive(Long id) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.inactiveOrActive();
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }


    public ResponseEntity generateExcel() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.now();
        String caminho = "C:\\Users\\Pichau\\Downloads\\excel_" + date.format(formatter) + ".xlsx";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("USERS");

        String[] headers = {"ID", "LOGIN", "ENABLE", "ROLE"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        List<User> users = userRepository.findAll();

        int rowNumber = 1;
        for (User user : users) {
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getLogin());
            row.createCell(2).setCellValue(user.isEnabled());
            row.createCell(3).setCellValue(user.getRole().getRole());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(caminho)) {
            workbook.write(fileOutputStream);
        } finally {
            workbook.close();
        }

        return ResponseEntity.ok().body("arquivo salvo em: " + caminho);
    }
}