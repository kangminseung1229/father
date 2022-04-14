package com.example.father.msk.mypay;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

/**
 * MypayController
 * 
 * 법인택시 기사들을 위해 기본급 + 추가급 기록용
 */
@Controller
@RequestMapping("/mypay")
@RequiredArgsConstructor
public class MypayController {

    private final MypayRepository mypayRepository;

    
    //list 보기
    @GetMapping("/list")
    public String list (Model model, @RequestParam(required = false) Long id){

        if (id != null) {

            Optional<Mypay> targetMypay = mypayRepository.findById(id);

            targetMypay.ifPresent(target->{
                model.addAttribute("targetMypay", target);
            });
            
        }

        List<Mypay> mypayList = mypayRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        model.addAttribute(mypayList);

        return "mypay/list";
        
    }

    //추가하기
    @PostMapping("/insert")
    @Transactional
    public String dataInsert(@RequestParam(required = false) String basicpay, @RequestParam(required = false) String pluspay, @RequestParam(required = false) Long id){


        //입력값이 없는 경우
        if (basicpay == "") {
            basicpay = "0";
        }

        if (pluspay == "") {
            pluspay = "0";
        }


        //타입변경
        Long basicpayToLong = Long.parseLong(basicpay,10);
        Long pluspayToLong = Long.parseLong(pluspay,10);
        
        Mypay mypay = new Mypay();

        if (id == null) {
           mypay =  Mypay.builder()
                               .basicpay(basicpayToLong)
                               .pluspay(pluspayToLong)
                               .build();
        } else if (id != null){
            mypay = mypayRepository.findById(id).get();
            mypay.setBasicpay(basicpayToLong);
            mypay.setPluspay(pluspayToLong);

        }
        mypay.sum();
        mypayRepository.save(mypay);

        return "redirect:/mypay/list";

    }



    
}