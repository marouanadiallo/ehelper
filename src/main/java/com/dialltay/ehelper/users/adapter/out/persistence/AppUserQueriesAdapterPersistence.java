package com.dialltay.ehelper.users.adapter.out.persistence;

import com.dialltay.ehelper.users.domain.model.AppUser;
import com.dialltay.ehelper.users.domain.model.Gender;
import com.dialltay.ehelper.users.port.out.AppUserQueries;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AppUserQueriesAdapterPersistence implements AppUserQueries {

    private final JdbcClient jdbcClient;

    public AppUserQueriesAdapterPersistence(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        String findByIdSql = """
                SELECT id, business_id, gender, first_name, last_name, birth_date, email, telephone
                FROM t_app_user
                WHERE id = ?
                """;
        return Optional.of(jdbcClient.sql(findByIdSql)
                .param(id)
                .query(rs -> {
                    if (rs.next()) {
                        return AppUser.withId(
                                rs.getLong("id"),
                                (java.util.UUID) rs.getObject("business_id"),
                                Gender.valueOf(rs.getString("gender")),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getDate("birth_date").toLocalDate(),
                                rs.getString("email"),
                                rs.getString("telephone")
                        );
                    }
                    return null;
                }));
    }

    @Override
    public boolean existsByEmailOrTelephone(String email, String telephone) {
        String existsByEmailOrTelephoneSql = """
                SELECT COUNT(1) FROM t_app_user WHERE email = ? OR telephone = ?
                """;
        Integer count = jdbcClient.sql(existsByEmailOrTelephoneSql)
                .param(email)
                .param(telephone)
                .query(rs -> {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return 0;
                });
        return count > 0;
    }

}
