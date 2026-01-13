package com.dialltay.ehelper.application.domain.service;

import com.dialltay.ehelper.application.domain.model.Gender;
import com.dialltay.ehelper.application.port.out.LoadUserPort;
import com.dialltay.ehelper.application.port.out.CreateUserPort;
import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.in.CreateUserUseCase;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;

@Service
public class CreateUserService implements CreateUserUseCase {

    private static final int MAX_LINES = 15;
    private static final long MAX_FILE_SIZE = 50 * 1024; // 50KB
    private static final Logger logger = LoggerFactory.getLogger(CreateUserService.class);

    private final CreateUserPort createUserPort;
    private final LoadUserPort userQueries;

    public CreateUserService(CreateUserPort createUserPort, LoadUserPort userQueries) {
        this.createUserPort = createUserPort;
        this.userQueries = userQueries;
    }

    @Override
    @Transactional
    public Long createUser(CreateUserCommand userCommand) {
        userCommand.validateSelf();
        var exists = userQueries.existsByEmailOrTelephone(userCommand.email(), userCommand.telephone());
        if (exists) {
            throw new UserDuplicationException("Duplication d'utilisateur, email ou téléphone existe déjà.");
        }

        // notify your about his/her account creation
        return this.createUserPort.save(userCommand);
    }

    @Override
    @Transactional
    public void createUsersBatch(MultipartFile file)  {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Fichier vide");
        }

        // Extension
        String fileName = FilenameUtils.normalize(file.getOriginalFilename());
        if (!"csv".equalsIgnoreCase(FilenameUtils.getExtension(fileName))) {
           throw new IllegalArgumentException("Extension de fichier invalide");
        }

        // Content type
        if (!"text/csv".equals(file.getContentType())) {
            throw new IllegalArgumentException("Type de fichier invalide");
        }

        // Taille
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Fichier trop volumineux");
        }

        try (Reader readerIn = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader(Headers.class)
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(readerIn);

            int lineCount = 0;
            List<CreateUserCommand> commands = new ArrayList<>();
            for (CSVRecord record : records) {

                if (++lineCount > MAX_LINES) {
                    throw new IllegalArgumentException(
                            "Maximum " + MAX_LINES + " lignes autorisées");
                }

                var gender = Gender.valueOf(record.get(Headers.GENDER).trim().toUpperCase());
                var birthDate = LocalDate.parse(record.get(Headers.BIRTH_DATE), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                CreateUserCommand newUser = new CreateUserCommand(
                        gender,
                        StringEscapeUtils.escapeHtml4(record.get(Headers.FIRST_NAME).trim()),
                        StringEscapeUtils.escapeHtml4(record.get(Headers.LAST_NAME).trim()),
                        birthDate,
                        StringEscapeUtils.escapeHtml4(record.get(Headers.EMAIL).trim()),
                        StringEscapeUtils.escapeHtml4(record.get(Headers.TELEPHONE).trim())
                );

                newUser.validateSelf();
                commands.add( newUser );
            }

            this.createUserPort.saveAll(commands);
            logger.info("{} utilisateurs créés avec succès à partir du fichier {}", commands.size(), fileName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
