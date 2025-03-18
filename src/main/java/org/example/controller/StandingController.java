package org.example.controller;

import org.example.model.StandingResponse;
import org.example.service.StandingService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StandingController {

    private final StandingService standingService;

    public StandingController(StandingService standingService) {
        this.standingService = standingService;
    }

    @GetMapping("/standings")
    public EntityModel<StandingResponse> getStandings(
            @RequestParam String country,
            @RequestParam String league,
            @RequestParam String team) {

        Optional<StandingResponse> response = standingService.getStandings(country, league, team);

        return response.map(standing -> {
            EntityModel<StandingResponse> model = EntityModel.of(standing);

            model.add(WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(StandingController.class)
                                    .getStandings(country, league, team))
                    .withSelfRel());

            return model;
        }).orElseThrow(() -> new RuntimeException("Data not found"));
    }
}
