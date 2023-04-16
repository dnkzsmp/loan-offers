package ru.generator.loanoffers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.generator.loanoffers.database.Offer;
import ru.generator.loanoffers.database.OfferRepository;
import ru.generator.loanoffers.enums.Answer;
import ru.generator.loanoffers.service.OfferService;

import java.util.List;

@Controller
public class OffersController {
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferService offerService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = principal.toString();

        List<Offer> nullOffers = offerRepository.findOfferByAnswerIsNull();
        if (!nullOffers.isEmpty()) {
            Offer offer = nullOffers.get(0);
            model.addAttribute("offer", offer);
            model.addAttribute("offerId", offer.getId());
        } else {
            model.addAttribute("offer", null);
        }

        model.addAttribute("username", username);
        return "main";
    }

    @PostMapping("/main")
    public String postMainPage(@RequestParam("answer") String answer, @RequestParam("offerId") String offerId,Model model) {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = principal.toString();

        Offer newOffer = offerRepository.findById(Long.parseLong(offerId)).get();
        switch (answer) {
            case "yes" -> newOffer.setAnswer(Answer.YES);
            case "no" -> newOffer.setAnswer(Answer.NO);
            case "later" -> newOffer.setAnswer(Answer.LATER);
        }
        offerService.generateNewOffer(newOffer);
        List<Offer> nullOffers = offerRepository.findOfferByAnswerIsNull();
        if (!nullOffers.isEmpty()) {
            Offer offer = nullOffers.get(0);
            model.addAttribute("offer", offer);
            model.addAttribute("offerId", offer.getId());
        } else {
            model.addAttribute("offer", null);
        }

        model.addAttribute("username", username);
        return "main";
    }
}
