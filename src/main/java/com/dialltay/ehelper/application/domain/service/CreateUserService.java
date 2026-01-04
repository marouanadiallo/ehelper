package com.dialltay.ehelper.application.domain.service;

import com.dialltay.ehelper.application.domain.model.Gender;
import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.in.CreateUserUseCase;
import com.dialltay.ehelper.application.port.out.CreateUserPort;
import com.dialltay.ehelper.application.port.out.LoadUserPort;
import jakarta.validation.Validator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateUserService implements CreateUserUseCase {

    private final CreateUserPort createUserPort;
    private final LoadUserPort userQueries;
    private final Validator validator;

    public CreateUserService(CreateUserPort createUserPort, LoadUserPort userQueries, Validator validator) {
        this.createUserPort = createUserPort;
        this.userQueries = userQueries;
        this.validator = validator;
    }

    @Override
    @Transactional
    public Long createUser(CreateUserCommand command) {
        var exists = userQueries.existsByEmailOrTelephone(command.email(), command.telephone());
        if (exists) {
            throw new UserDuplicationException("Duplication d'utilisateur, email ou téléphone existe déjà.");
        }

        // notify your about his/her account creation
        return this.createUserPort.save(command);
    }

    @Override
    @Transactional
    public void createUsersBatch(MultipartFile file) {
        if (file.isEmpty()) {
            //TODO: log and throw exception
            return;
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!fileExtension.equalsIgnoreCase("csv")) {
            //TODO: log and throw exception
            return;
        }

        try (Reader readerIn = new InputStreamReader(file.getInputStream())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader(Headers.class)
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(readerIn);

            List<CreateUserCommand> commands = new ArrayList<>();
            for (CSVRecord record : records) {
                var gender = Gender.valueOf(record.get(Headers.GENDER));
                var birthDate = LocalDate.parse(record.get(Headers.BIRTH_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                CreateUserCommand newUser = new CreateUserCommand(
                        gender,
                        record.get(Headers.FIRST_NAME),
                        record.get(Headers.LAST_NAME),
                        birthDate,
                        record.get(Headers.EMAIL),
                        record.get(Headers.TELEPHONE)
                );
                var cv = this.validator.validate(newUser);
                if (!cv.isEmpty()) {
                    // throw validation exception
                    return;
                }
                commands.add( newUser );
            }
            this.createUserPort.saveAll(commands);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    enum Headers {
        GENDER,
        LAST_NAME,
        FIRST_NAME,
        EMAIL,
        TELEPHONE,
        BIRTH_DATE
    }
}
