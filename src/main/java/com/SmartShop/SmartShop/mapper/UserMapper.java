package com.SmartShop.SmartShop.mapper;

import com.SmartShop.SmartShop.dto.*;
import com.SmartShop.SmartShop.model.Client;
import com.SmartShop.SmartShop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest dto);

    UserResponse toUserResponse(User user);
    default ClientResponse map(Client client) {
        if (client == null) return null;
        ClientResponse cr = new ClientResponse();
        cr.setId(client.getId());
        cr.setNiveauFidelite(client.getNiveauFidelite());
        return cr;
    }
}
