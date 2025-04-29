package com.core.mapper;

import com.core.deal.dto.ProtectedDealGeneratorRequest;
import com.core.deal.dto.ProtectedDealGeneratorResponse;
import com.core.deal.dto.ProtectedDealResponse;
import com.core.deal.model.ProtectedDeal;
import com.core.home.model.Home;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-29T15:08:09+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ProtectedDealMapperImpl implements ProtectedDealMapper {

    @Override
    public ProtectedDeal toEntity(ProtectedDealGeneratorRequest request) {
        if ( request == null ) {
            return null;
        }

        ProtectedDeal.ProtectedDealBuilder protectedDeal = ProtectedDeal.builder();

        protectedDeal.homeId( request.getHomeId() );
        protectedDeal.dmId( request.getDmId() );
        protectedDeal.getterId( request.getGetterId() );
        protectedDeal.providerId( request.getProviderId() );
        protectedDeal.deposit( request.getDeposit() );

        protectedDeal.dealState( com.core.deal.model.DealState.REQUEST_DEAL );
        protectedDeal.protectedDealDateTime( createProtectedDealDateTime(request.getDealAt()) );

        return protectedDeal.build();
    }

    @Override
    public ProtectedDealResponse toResponse(ProtectedDeal deal, Home home) {
        if ( deal == null && home == null ) {
            return null;
        }

        ProtectedDealResponse.ProtectedDealResponseBuilder protectedDealResponse = ProtectedDealResponse.builder();

        if ( deal != null ) {
            protectedDealResponse.dealState( deal.getDealState() );
            protectedDealResponse.deposit( deal.getDeposit() );
        }
        protectedDealResponse.id( deal.getId() );
        protectedDealResponse.fee( deal.calculateFee() );
        protectedDealResponse.totalPrice( deal.calculateTotalPrice() );
        protectedDealResponse.createAt( deal.getProtectedDealDateTime().getCreateAt() );
        protectedDealResponse.startAt( deal.getProtectedDealDateTime().getStartAt() );
        protectedDealResponse.cancelAt( deal.getProtectedDealDateTime().getCancelAt() );
        protectedDealResponse.dealAt( deal.getProtectedDealDateTime().getDealAt() );
        protectedDealResponse.completeAt( deal.getProtectedDealDateTime().getCompleteAt() );
        protectedDealResponse.address( home.getHomeAddress().parseAddress() );
        protectedDealResponse.homeImage( home.getMainImage() );
        protectedDealResponse.rent( home.getHomeInfo().getRent() );
        protectedDealResponse.bill( home.getHomeInfo().getBill() );
        protectedDealResponse.bond( home.getHomeInfo().getBond() );

        return protectedDealResponse.build();
    }

    @Override
    public ProtectedDealGeneratorResponse toGeneratorResponse(Long dealId) {
        if ( dealId == null ) {
            return null;
        }

        ProtectedDealGeneratorResponse.ProtectedDealGeneratorResponseBuilder protectedDealGeneratorResponse = ProtectedDealGeneratorResponse.builder();

        protectedDealGeneratorResponse.dealId( dealId );

        return protectedDealGeneratorResponse.build();
    }
}
