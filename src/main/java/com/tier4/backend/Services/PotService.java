package com.tier4.backend.Services;

import com.tier4.backend.Repositories.PotRepo;
import com.tier4.backend.Repositories.UserRepo;
import com.tier4.backend.Repositories.VectorRepo;
import com.tier4.backend.Utils.Converter;
import com.tier4.backend.web.Domain.Pot;
import com.tier4.backend.web.Domain.User;
import com.tier4.backend.web.Domain.Vector;
import com.tier4.backend.web.Model.Pot.PotDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PotService {

    @Autowired
    private VectorRepo vectorRepo;

    @Autowired
    private PotRepo potRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Converter converter;

    @Transactional
    public void createPot(PotDto potDto) {

        System.out.println("entering create pot function "+potDto.getPhoneNumber());
        Vector vector = vectorRepo.getByValue(potDto.getPhoneNumber());
        log.info("VECTOR: "+vector);
        User user = vector.getUser();
        log.info(user.toString());

        if(user.getPot() == null){
            user.setPot(new ArrayList<>());
        }

        List<Pot> potList = user.getPot();

        Pot pot = Pot.builder()
                .title(potDto.getTitle())
                .amount(potDto.getAmount())
                .currentAmount(0.0)
                .description(potDto.getDescription())
                .autoDeduct(true)
                .totalTime(potDto.getEta())
                .timeLeft(potDto.getEta())
                .weight(potDto.getWeight())
                .build();

        pot.setUser(user);
        pot = potRepo.save(pot);

        user.getPot().add(pot);
        user = userRepo.save(user);

        List<Pot> pots = weightOptimizer(user.getPot());
        potRepo.saveAll(pots);
        user.setPot(pots);
        userRepo.save(user);

    }

    @Transactional
    public PotDto deletePot(Long potId){

        Pot pot = potRepo.getById(potId);
        User user = pot.getUser();
        double currentAmount = pot.getCurrentAmount();

        user.getPot().remove(pot);
        potRepo.delete(pot);

        List<Pot> pots = weightOptimizer(user.getPot());
        potRepo.saveAll(pots);
        user.setPot(pots);
        userRepo.save(user);

        return converter.potToPotDto(pot);
    }

    @Transactional(readOnly = true)
    public PotDto getPotDetails(Long potId){

        return converter.potToPotDto(potRepo.getById(potId));
    }

    @Transactional
    public void updatePot(Long potId,PotDto potDto) {

        Pot pot = potRepo.getById(potId);
        pot.setAmount(potDto.getAmount());
        pot.setTotalTime(potDto.getEta());
        pot.setTimeLeft(Double.toString(Double.parseDouble(pot.getTimeLeft())+Double.parseDouble(potDto.getEta())));
        User user = pot.getUser();

        for(int i = 0;i<user.getPot().size();i++){
            if(user.getPot().get(i).getId() == potId)
                user.getPot().set(i,pot);
        }

        potRepo.save(pot);
        userRepo.save(user);
    }


    public static List<Pot> weightOptimizer(List<Pot> pots){

        pots.forEach(System.out::println);

        List<Double> weightRatio = pots.stream().sequential()
                                    .map(pot -> {
                                        double timeLeft = Double.parseDouble(pot.getTimeLeft());
                                        double totalTime = Double.parseDouble(pot.getTotalTime());
                                        double totalAmount = pot.getAmount();
                                        double currentAmount = pot.getCurrentAmount();

                                        if(timeLeft == 0)
                                            return 0.0;

                                        return ((totalAmount-currentAmount) * totalTime)/ Math.pow(timeLeft,3)*totalAmount;

                                    }).collect(Collectors.toList());

        log.info("WEIGHTS: "+weightRatio);

        double weightSum = weightRatio.stream()
                            .filter(weight-> weight != Double.NaN)
                            .mapToDouble(weight->weight)
                            .sum();

        System.out.println(weightSum);

        weightRatio = weightRatio.stream().sequential()
                .map(weight->weight/weightSum)
                .collect(Collectors.toList());

        System.out.println("WEIGHT RATIO :"+weightRatio);
        for(int i =0 ;i<pots.size();i++)
            pots.get(i).setWeight(weightRatio.get(i));


        return pots;
    }

    public static List<Pot> weightOptimizer2(List<Pot> pots){





        pots.forEach(System.out::println);

        List<Double> weightRatio = pots.stream().sequential()
                .map(pot -> {
                    double timeLeft = Double.parseDouble(pot.getTimeLeft());
                    double totalTime = Double.parseDouble(pot.getTotalTime());
                    double totalAmount = pot.getAmount();
                    double currentAmount = pot.getCurrentAmount();

                    if(timeLeft == 0)
                        return 0.0;

                    return ((totalAmount-currentAmount) * totalTime)/ Math.pow(timeLeft,3)*totalAmount;

                }).collect(Collectors.toList());

        log.info("WEIGHTS: "+weightRatio);

        double weightSum = weightRatio.stream()
                .filter(weight-> weight != Double.NaN)
                .mapToDouble(weight->weight)
                .sum();

        System.out.println(weightSum);

        weightRatio = weightRatio.stream().sequential()
                .map(weight->weight/weightSum)
                .collect(Collectors.toList());

        System.out.println("WEIGHT RATIO :"+weightRatio);
        for(int i =0 ;i<pots.size();i++)
            pots.get(i).setWeight(weightRatio.get(i));


        return pots;
    }







    public List<PotDto> getAllPots(String phoneNumber) {

        return vectorRepo.getByValue(phoneNumber).getUser().getPot().stream()
                .map(pot -> Converter.potToPotDto(pot))
                .collect(Collectors.toList());
    }
}
