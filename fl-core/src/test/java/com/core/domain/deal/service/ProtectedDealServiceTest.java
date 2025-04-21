package com.core.domain.deal.service;

import com.core.deal.model.ProtectedDeal;
import com.core.deal.repository.ProtectedDealRepository;
import com.core.deal.service.ProtectedDealService;
import com.core.user.model.User;
import com.core.user.model.UserAccount;
import com.core.user.repository.UserAccountRepository;
import com.core.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProtectedDealServiceTest {

    @Mock
    private ProtectedDealRepository protectedDealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private ProtectedDealService protectedDealService;

    private ProtectedDeal protectedDeal;
    private User getter;
    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        protectedDeal = mock(ProtectedDeal.class);
        getter = mock(User.class);
        userAccount = mock(UserAccount.class);

        when(protectedDeal.getId()).thenReturn(1L);
        when(protectedDeal.getGetterId()).thenReturn(2L);
    }
}
