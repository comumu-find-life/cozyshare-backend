package com.core.home.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class HomeOverviewWrapper {
    private List<HomeOverviewResponse> homes = new ArrayList<>();

    public HomeOverviewWrapper(List<HomeOverviewResponse> homes) {
        this.homes = homes;
    }
}