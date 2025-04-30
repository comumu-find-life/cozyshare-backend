package com.core.chat.repository.impl;

import com.core.chat.model.DirectMessage;
import com.core.chat.repository.CustomDirectMessageRepository;
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
public class DirectMessageRepositoryImpl implements CustomDirectMessageRepository {

    private final MongoTemplate mongoTemplate;
    private static final String SENT_AT_FIELD = "sentAt";

    @Override
    public Optional<DirectMessage> findLastMessageByUserIds(Long user1Id, Long user2Id) {
        Query query = new Query(createBidirectionalUserCriteria(user1Id, user2Id))
                .with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, SENT_AT_FIELD))
                .limit(1);
        return Optional.ofNullable(mongoTemplate.findOne(query, DirectMessage.class));
    }

    @Override
    public List<DirectMessage> findDirectMessageByUserIds(Long user1Id, Long user2Id) {
        Query query = new Query(createBidirectionalUserCriteria(user1Id, user2Id))
                .with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, SENT_AT_FIELD));
        return mongoTemplate.find(query, DirectMessage.class);
    }

    @Override
    public int countNotReadMessage(Long senderId, Long receiverId) {
        Query query = new Query(Criteria.where("senderId").is(receiverId)
                .and("receiverId").is(senderId)
                .and("isRead").is(false));
        return (int) mongoTemplate.count(query, DirectMessage.class);
    }

    @Override
    public void markMessagesAsRead(Long senderId, Long receiverId) {
        Query query = new Query(Criteria.where("senderId").is(receiverId)
                .and("receiverId").is(senderId)
                .and("isRead").is(false));
        Update update = new Update().set("isRead", true);
        mongoTemplate.updateMulti(query, update, DirectMessage.class);
    }

    private Criteria createBidirectionalUserCriteria(Long user1Id, Long user2Id) {
        return new Criteria().orOperator(
                Criteria.where("senderId").is(user1Id).and("receiverId").is(user2Id),
                Criteria.where("senderId").is(user2Id).and("receiverId").is(user1Id)
        );
    }
}
