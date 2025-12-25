package com.dialltay.ehelper.application.domain.service;

import com.dialltay.ehelper.application.domain.model.UserProjections;
import com.dialltay.ehelper.application.port.in.GetUserSliceUseCase;
import com.dialltay.ehelper.application.port.out.LoadUserPort;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GerUsersService implements GetUserSliceUseCase {

    private final LoadUserPort loadUserPort;

    public GerUsersService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public Slice<UserProjections.UserTable> firstSliceOfUsers() {

        return this.loadUserPort.loadAUserSlice(
                PageRequest.of(0, 5,
                                Sort.by("lastName").ascending()
                )
        );
    }
}
