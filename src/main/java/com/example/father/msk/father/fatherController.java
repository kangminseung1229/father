package com.example.father.msk.father;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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

        model.addAttribute("list", moneyRepo.findByYearAndMonthOrderByDatememo(year, month));
        model.addAttribute("sumCompanyPrice", moneyRepo.sumCompanyPrice(year, month));
        model.addAttribute("sumMyPrice", moneyRepo.sumMyPrice(year, month));
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "html/list";
    }

    @GetMapping(value = { "/", "memo" })
    public String memo(Model model, @RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int day) {

        LocalDate now = LocalDate.now();

        if (year == 0) {
            year = now.getYear();
        }
        if (month == 0) {
            month = now.getMonthValue();
        }
        if (day == 0) {
            day = now.getDayOfMonth();
        }

        LocalDate targetDate = LocalDate.of(year, month, day);

        Optional<money> tempRow;

        tempRow =  moneyRepo.findByDatememo(targetDate);

        if (tempRow.isPresent()) {
            money realrow = tempRow.get();
            model.addAttribute("id", realrow.getId());
            model.addAttribute("companyPrice", realrow.getCompanyprice());
            model.addAttribute("myPrice", realrow.getMyprice());
            model.addAttribute("totalPrice", realrow.getCompanyprice() + realrow.getMyprice());
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);

        return "html/memo";
    }

    

    @PostMapping(value="companypriceSave")
    @ResponseBody
    public money companypriceSave(money entity, HttpServletRequest request) {


        int year = Integer.parseInt(request.getParameter("year"));
        int month = Integer.parseInt(request.getParameter("month"));
        int day = Integer.parseInt(request.getParameter("day"));
        
        LocalDate datememo = LocalDate.of(year, month, day);
        
        entity.setDatememo(datememo);
        entity.setTotalprice(entity.getCompanyprice() + entity.getMyprice());

        moneyRepo.save(entity);

        return entity;
    }
    

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
