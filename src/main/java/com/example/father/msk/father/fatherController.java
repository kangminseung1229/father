package com.example.father.msk.father;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class fatherController {

    @Autowired
    private moneyRepository moneyRepo;

    @GetMapping("")
    public String list(Model model) {

        LocalDate now = LocalDate.now();

        money sample = new money();

        Optional<money> opt = moneyRepo.findByDatememo(now);


        System.out.println("==============================================================================================================================");
        System.out.println("[now]--->"+ now); 
        System.out.println("[비었니?]--->"+ opt.isEmpty()); // 비었니?
        System.out.println("[있니?]--->"+ opt.isPresent()); // 있니? 
        System.out.println("==============================================================================================================================");

        if (opt.isEmpty()) {
            //현재날짜 데이터 삽입
            sample.setDatememo(now);
            sample.setMonth(now.getMonthValue());
            moneyRepo.save(sample);
        }
        

        model.addAttribute("list", moneyRepo.findBymonthOrderByDatememo(now.getMonthValue()));
        model.addAttribute("monthTotal", moneyRepo.sumByMonth(now.getMonthValue()));

        return "html/list";
    }

    // 금액 입력 후 저장
    @PostMapping("priceSave")
    @ResponseBody
    public money priceSave(@RequestParam(required = true) String price, @RequestParam(required = true) Long id){

        Optional<money> updateMoney =  moneyRepo.findById(id);

        money target = new money();
        target = updateMoney.get();
        target.setId(id);
        target.setPrice(Long.parseLong(price.replace(",","")));
        
        return moneyRepo.save(target);
    }

}
