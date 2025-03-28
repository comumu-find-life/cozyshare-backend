package com.core.domain.chat.repository;


import com.core.domain.chat.model.DirectMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, String>, CustomDirectMessageRepository {
}
