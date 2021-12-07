package com.example.father.msk.father;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class fatherController {

    @Autowired
    private moneyRepository moneyRepo;

    @GetMapping("list")
    public String list(Model model, @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month) {

        LocalDate now = LocalDate.now();

        if (year == 0) {
            year = now.getYear();
        }

        if (month == 0) {
            month = now.getMonthValue();
        }

        money sample = new money();

        Optional<money> opt = moneyRepo.findByDatememo(now);

        model.addAttribute("list", moneyRepo.findBymonthOrderByDatememo(now.getMonthValue()));
        model.addAttribute("monthTotal", moneyRepo.sumByMonth(now.getMonthValue()));
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "html/list";
    }

    @GetMapping(value = { "/", "memo" })
    public String memo(Model model, @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month) {

        LocalDate now = LocalDate.now();

        if (year == 0) {
            year = now.getYear();
        }

        if (month == 0) {
            month = now.getMonthValue();
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", now.getDayOfMonth());

        return "html/memo";
    }

    // @PostMapping(value="companypriceSave")
    // @ResponseBody
    // public String companypriceSave(HttpServletRequest request) {

    //     String year, month;
    //     LocalDate datememo;
    //     Long companyPrice, myPrice, totalprice;

        

        

        
    //     return entity;
    // }
    

    // // 금액 입력 후 저장
    // @PostMapping("priceSave")
    // @ResponseBody
    // public money priceSave(@RequestParam(required = true) String price,
    // @RequestParam(required = true) Long id){

    // Optional<money> updateMoney = moneyRepo.findById(id);

    // money target = new money();
    // target = updateMoney.get();
    // target.setId(id);
    // target.setPrice(Long.parseLong(price.replace(",","")));

    // return moneyRepo.save(target);
    // }

}
