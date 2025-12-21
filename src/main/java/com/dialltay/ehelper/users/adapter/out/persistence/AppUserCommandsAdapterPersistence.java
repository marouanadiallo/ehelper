package com.dialltay.ehelper.users.adapter.out.persistence;

import com.dialltay.ehelper.users.domain.model.AppUser;
import com.dialltay.ehelper.users.port.out.AppUserCommands;
import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AppUserCommandsAdapterPersistence implements AppUserCommands {
    public static final String NEW_USER_INSERT_SQL = """
            INSERT INTO t_app_user (business_id, gender, first_name, last_name, birth_date, email, telephone)
            VALUES (?, ?, ?, ?, ?, ?, ?)""";

    private final JdbcClient jdbcClient;
    private final JdbcTemplate jdbcTemplate;

    public AppUserCommandsAdapterPersistence(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(AppUser user) {

        var generatedKeyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(NEW_USER_INSERT_SQL)
                .param(user.getBusinessId())
                .param(user.getGender().name())
                .param(user.getFirstName())
                .param(user.getLastName())
                .param(user.getBirthDate())
                .param(user.getEmail())
                .param(user.getTelephone())
                .update(generatedKeyHolder, "id");

        return (Long) generatedKeyHolder.getKey();
    }

    @Override
    public void saveAll(List<AppUser> users) {
        jdbcTemplate.batchUpdate(NEW_USER_INSERT_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                AppUser user = users.get(i);
                ps.setObject(1, user.getBusinessId());
                ps.setString(2, user.getGender().name());
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setDate(5,   user.getSqlBirthDate());
                ps.setString(6, user.getEmail());
                ps.setString(7, user.getTelephone());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }

    @Override
    public void updateContactInfo(Long id, String email, String telephone) {

    }

    @Override
    public void updatePersonalInfo(Long id, String firstName, String lastName, LocalDate birthDate) {

    }
}
