package ru.generator.loanoffers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.generator.loanoffers.database.Offer;
import ru.generator.loanoffers.database.OfferRepository;
import ru.generator.loanoffers.enums.Answer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;
    private final long MONTH_TIME = 2629700000L;

    /*
    * Тут мы перегенерируем новые предложения по следующим условиям:
    * - Если предложение принято, то пересоздаем его через 3 месяца
    * - Если отклонено, то пересоздаем через месяц
    * */
    public void generateNewOffer(Offer offer) {
        Offer newOffer = new Offer();
        newOffer.setId(offer.getId());
        newOffer.setTitle(offer.getTitle());
        newOffer.setContent(offer.getContent());
        newOffer.setAnswer(offer.getAnswer());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (Answer.YES.equals(offer.getAnswer())) {
            calendar.add(Calendar.MONTH, 3);
        } else if (Answer.NO.equals(offer.getAnswer())) {
            calendar.add(Calendar.MONTH, 1);
        }

        newOffer.setCreationDate(calendar.getTime());

        offerRepository.save(newOffer);
    }

    /*
    * Проверка (каждый месяц с помощью @Scheduled) на срок хранения предложения
    * Если у предложения ответ YES/NO и прошло 6 месяцев - удаляем
    * Предложения, у которых ответ LATER, мы просто игнорим
    * */
    @Scheduled(fixedRate = MONTH_TIME)
    public void deleteOldOffers() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -6);

        List<Offer> oldYesOffers = offerRepository.findOfferByAnswerAndCreationDate(Answer.YES, calendar.getTime());
        List<Offer> oldNoOffers = offerRepository.findOfferByAnswerAndCreationDate(Answer.NO, calendar.getTime());

        offerRepository.deleteAll(oldYesOffers);
        offerRepository.deleteAll(oldNoOffers);
    }

    /*
    * Генерация нового рандомного предложения каждый месяц
    * */
    @Scheduled(fixedRate = MONTH_TIME)
    public void createNewRandomOffer() {
        this.generateNewOffer(Offer.builder()
                .title("Уважаемый пользователь!")
                .content("Вам одобрен кредит")
                .answer(null)
                .build());
    }
}
