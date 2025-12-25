package com.dialltay.ehelper.adapter.out.persistence;

import com.dialltay.ehelper.application.domain.model.UserProjections;
import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.out.LoadUserPort;
import com.dialltay.ehelper.application.port.out.UpdateUserPort;
import com.dialltay.ehelper.commons.annotations.PersistenceAdapter;
import com.dialltay.ehelper.application.port.out.CreateUserPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.List;

@PersistenceAdapter
public class UserPersistenceAdapter implements CreateUserPort, UpdateUserPort, LoadUserPort {

    private final AppUserRepository userRepository;

    public UserPersistenceAdapter(AppUserRepository appUserRepository) {
        this.userRepository = appUserRepository;
    }

    @Override
    public Long save(CreateUserCommand command) {
        var userEntity = AppUserJpaEntity.of(
                command.gender(),
                command.firstName(),
                command.lastName(),
                command.birthDate(),
                command.email(),
                command.telephone()
        );
        var savedUser = this.userRepository.save(userEntity);
        return savedUser.getId();
    }

    @Override
    public void saveAll(List<CreateUserCommand> commands) {
        var userEntities = commands.stream().map(command -> AppUserJpaEntity.of(
                command.gender(),
                command.firstName(),
                command.lastName(),
                command.birthDate(),
                command.email(),
                command.telephone()
        )).toList();
        this.userRepository.saveAll(userEntities);
    }

    @Override
    public boolean existsByEmailOrTelephone(String email, String telephone) {
        return this.userRepository.existsByEmailOrTelephone(email, telephone);
    }

    @Override
    public Slice<UserProjections.UserTable> loadAUserSlice(Pageable pageable) {
        return null;
    }

    @Override
    public void updateContactInfo(Long id, String email, String telephone) {

    }

    @Override
    public void updatePersonalInfo(Long id, String firstName, String lastName, LocalDate birthDate) {

    }
}
