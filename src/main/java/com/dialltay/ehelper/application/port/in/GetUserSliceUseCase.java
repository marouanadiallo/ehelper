package com.dialltay.ehelper.application.port.in;


import com.dialltay.ehelper.application.domain.model.UserProjections;
import org.springframework.data.domain.Slice;

public interface GetUserSliceUseCase {
    Slice<UserProjections.UserTable> firstSliceOfUsers();
}
