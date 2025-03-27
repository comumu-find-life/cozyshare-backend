package com.core.mapper;

import com.core.domain.deal.dto.ProtectedDealGeneratorRequest;
import com.core.domain.deal.dto.ProtectedDealGeneratorResponse;
import com.core.domain.deal.dto.ProtectedDealResponse;
import com.core.domain.deal.dto.ProtectedDealOverViewResponse;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.deal.model.ProtectedDealDateTime;
import com.core.domain.home.model.Home;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ProtectedDealMapper {

    ProtectedDealMapper INSTANCE = Mappers.getMapper(ProtectedDealMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dealState", expression = "java(com.core.domain.deal.model.DealState.REQUEST_DEAL)")
    @Mapping(target = "protectedDealDateTime", expression = "java(createProtectedDealDateTime(request.getDealAt()))") // Set current time
    ProtectedDeal toEntity(ProtectedDealGeneratorRequest request);


    default ProtectedDealDateTime createProtectedDealDateTime(LocalDateTime dealAt) {
        ProtectedDealDateTime protectedDealDateTime = new ProtectedDealDateTime();
        protectedDealDateTime.setCreateAt(LocalDateTime.now());
        protectedDealDateTime.setDealAt(dealAt);
        return protectedDealDateTime;
    }

    @Mappings({
            @Mapping(target = "id", expression = "java(deal.getId())"),
            @Mapping(target = "dealState", source = "deal.dealState"),
            @Mapping(target = "deposit", source = "deal.deposit"),
            @Mapping(target = "fee", expression = "java(deal.calculateFee())"),
            @Mapping(target = "totalPrice", expression = "java(deal.calculateTotalPrice())"),
            @Mapping(target = "createAt", expression = "java(deal.getProtectedDealDateTime().getCreateAt())"),
            @Mapping(target = "startAt", expression = "java(deal.getProtectedDealDateTime().getStartAt())"),
            @Mapping(target = "cancelAt", expression = "java(deal.getProtectedDealDateTime().getCancelAt())"),
            @Mapping(target = "dealAt", expression = "java(deal.getProtectedDealDateTime().getDealAt())"),
            @Mapping(target = "completeAt", expression = "java(deal.getProtectedDealDateTime().getCompleteAt())"),
            @Mapping(target = "address", expression = "java(home.getHomeAddress().parseAddress())"),
            @Mapping(target = "homeImage", expression = "java(home.getMainImage())"),
            @Mapping(target = "rent", expression = "java(home.getHomeInfo().getRent())"),
            @Mapping(target = "bill", expression = "java(home.getHomeInfo().getBill())"),
            @Mapping(target = "bond", expression = "java(home.getHomeInfo().getBond())")
    })
    ProtectedDealResponse toResponse(ProtectedDeal deal, Home home);


    @Mapping(target = "dealId", source = "dealId")
    ProtectedDealGeneratorResponse toGeneratorResponse(Long dealId);

}