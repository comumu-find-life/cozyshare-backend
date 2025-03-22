package com.core.domain.chat.repository.impl;

import com.core.domain.chat.model.DirectMessage;
import com.core.domain.chat.repository.CustomDirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomDirectMessageRepositoryImpl implements CustomDirectMessageRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<DirectMessage> findLastMessageByUserIds(Long user1Id, Long user2Id) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("senderId").is(user1Id).and("receiverId").is(user2Id),
                Criteria.where("senderId").is(user2Id).and("receiverId").is(user1Id)
        ));
        query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "sentAt"));
        query.limit(1);

        DirectMessage lastMessage = mongoTemplate.findOne(query, DirectMessage.class);
        return Optional.ofNullable(lastMessage);
    }

    @Override
    public List<DirectMessage> findDirectMessageByUserIds(Long user1Id, Long user2Id) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("senderId").is(user1Id).and("receiverId").is(user2Id),
                Criteria.where("senderId").is(user2Id).and("receiverId").is(user1Id)
        ));
        query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "sentAt"));

        return mongoTemplate.find(query, DirectMessage.class);
    }

    @Override
    public int countNotReadMessage(Long senderId, Long receiverId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("senderId").is(receiverId)
                .and("receiverId").is(senderId)
                .and("isRead").is(false));
        return (int) mongoTemplate.count(query, DirectMessage.class);
    }

    @Override
    public void markMessagesAsRead(Long senderId, Long receiverId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("senderId").is(receiverId)
                .and("receiverId").is(senderId)
                .and("isRead").is(false));
        Update update = new Update();
        update.set("isRead", true);
        mongoTemplate.updateMulti(query, update, DirectMessage.class);
    }
}