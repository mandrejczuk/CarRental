package com.example.projekt.sevices;

import com.example.projekt.models.Reservation;
import com.example.projekt.models.Token;
import com.example.projekt.repositories.ReservationRepository;
import com.example.projekt.repositories.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService extends GenericManagementService<Reservation, ReservationRepository> {

    TokenRepository tokenRepository;
    MailService mailService;
    private Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService(ReservationRepository repository, TokenRepository tokenRepository, MailService mailService) {

        super(repository);
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }

    public List<Reservation> getAllUserReservations(Long userId)
    {
        return  repository.findAllByUserId(userId);
    }

    @Override
    public Reservation add(Reservation entity) {
        super.add(entity);
        if(entity.isEnabled())
        {
            return entity;
        }
        else
        {
            sendToken(entity);
            return entity;
        }
    }

    @Override
    public void delete(Long id) {

        if(tokenRepository.existsByReservation(repository.findById(id).get()))
        {
            tokenRepository.delete(tokenRepository.findByReservation(repository.findById(id).get()));
        }
      Reservation reservation =  repository.findById(id).get();
      sendDeleteMail(reservation.getUser().getEmail(), reservation.getCar().getBrand());
        super.delete(id);
    }

    public void adminDeletesReservation(Long id)
    {
        if(tokenRepository.existsByReservation(repository.findById(id).get()))
        {
            tokenRepository.delete(tokenRepository.findByReservation(repository.findById(id).get()));
        }
        super.delete(id);
    }

    public void sendToken(Reservation reservation)
    {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setReservation(reservation);
        tokenRepository.save(token);
        String url = "http://localhost:8080/reservation/token?value=" + tokenValue;
        try {
            mailService.sendMail( reservation.getUser().getEmail() ,"CarRental reservation for car " + reservation.getCar().getBrand(),"Click this link to pay \n" + url, false);
            logger.info("MAIL WITH PAYMENT INFO SENT TO:  " + reservation.getUser().getEmail());
        } catch (MessagingException e) {
            logger.error("SENDING MAIL WITH PAYMENT INFO, PROBLEM IN THIS RESERVATION ID: "+reservation.getId());
            e.printStackTrace();
        }
    }
    public void sendDeleteMail(String email,String brand)
    {
        try {
            mailService.sendMail(email,"CarRental reservation delete","Your reservation for "+ brand +" has been deleted",false);
            logger.info("MAIL WITH DELETE INFO SENT TO:  " +email);
        } catch (MessagingException e) {
            logger.error("SENDING MAIL WITH DELETE INFO, PROBLEM IN THIS RESERVATION ID: "+email);
            e.printStackTrace();
        }
    }
    @Scheduled(fixedRate = 60000)
    public void schedulingDeleteReservations()
    {
        List<Token> tokens = tokenRepository.findAll();
        int i = 0;

        for(Token t : tokens)
        {
            if(LocalTime.now().isAfter(t.getCreatedAt().plusMinutes(3)))
            {
                if(!t.getReservation().isEnabled())
                {
                    Reservation reservation = t.getReservation();
                    sendSchedulingDeleteMail(reservation.getUser().getEmail(),reservation.getCar().getBrand());
                    tokenRepository.delete(t);
                    repository.delete(reservation);
                    i++;
                }
            }
        }
        logger.info("SCHEDULED TASK DELETED: "+i +" reservations");

    }
    public void sendSchedulingDeleteMail(String email,String brand)
    {
        try {
            mailService.sendMail(email,"CarRental reservation delete","Your reservation for "+ brand +" has been deleted for not paying in time",false);
            logger.info("MAIL WITH SCHEDULED-DELETE INFO SENT TO:  " +email);
        } catch (MessagingException e) {
            logger.error("SENDING MAIL WITH SCHEDULED-DELETE INFO, PROBLEM IN THIS RESERVATION ID: "+email);
            e.printStackTrace();
        }
    }
}
