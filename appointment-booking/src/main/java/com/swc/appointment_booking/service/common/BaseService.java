package com.swc.appointment_booking.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<REQ, RES, ID> {

    RES create(REQ dto);

    RES findById(ID id);

    Page<RES> findAll(Pageable pageable);

    RES update(ID id, REQ dto);

    void deleteById(ID id);
}
