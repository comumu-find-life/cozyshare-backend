package com.core.chat.repository;


import com.core.chat.model.DirectMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, String>, CustomDirectMessageRepository {
}
