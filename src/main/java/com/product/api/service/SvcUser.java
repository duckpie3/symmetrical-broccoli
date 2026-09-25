package com.product.api.service;

import com.product.api.dto.in.DtoUserIn;
import com.product.api.dto.out.DtoUserOut;

public interface SvcUser {

    public void register(DtoUserIn in);

    public void update(DtoUserIn in, Integer id);

    public DtoUserOut getUser(Integer id);

    public void enable(Integer id);

    public void disable(Integer id);
}
