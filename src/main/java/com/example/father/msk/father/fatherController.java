package com.example.father.msk.father;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ReturnedType;
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

    private final Long absouluteCompanyPrice = 3718000l;

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

        // 쿼리 합계가 null이면 null
        Long sumMyPrice = moneyRepo.sumMyPrice(year, month);
        Long sumCompanyPrice = moneyRepo.sumCompanyPrice(year, month);
        
        if (sumMyPrice == null) {
            sumMyPrice = 0l;
        }
        
        if (sumCompanyPrice == null) {
            sumCompanyPrice = 0l;
        }
        
        
        Long attributeValue = sumCompanyPrice - absouluteCompanyPrice;



        Optional<List<money>> moneyList = moneyRepo.findByYearAndMonthOrderByDatememo(year, month);
        List<money> list;

        if (moneyList.isPresent()) {
            list = moneyList.get();
        } else {
            list = null;
        }

        model.addAttribute("list", list); 
        model.addAttribute("sumCompanyPrice", attributeValue);
        model.addAttribute("sumMyPrice", sumMyPrice);
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

        tempRow = moneyRepo.findByDatememo(targetDate);

        if (tempRow.isPresent()) {
            money realrow = tempRow.get();
            model.addAttribute("id", realrow.getId());
            model.addAttribute("companyPrice", realrow.getCompanyprice());
            model.addAttribute("myPrice", realrow.getMyprice());

            // 필요없다고 지움
            // model.addAttribute("totalPrice", realrow.getCompanyprice() +
            // realrow.getMyprice());

        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);

        return "html/memo";
    }

    @PostMapping(value = "companypriceSave")
    @ResponseBody
    public money companypriceSave(money entity, HttpServletRequest request) {

        int year = Integer.parseInt(request.getParameter("year"));
        int month = Integer.parseInt(request.getParameter("month"));
        int day = Integer.parseInt(request.getParameter("day"));

        LocalDate datememo = LocalDate.of(year, month, day);

        // entity 형식의 datememo 가 String으로 맞지않기 때문에 따로 request에서 분리한다.
        // entity 의 타입이 javascript 의 타입과 일치하지 않으면 엔티티오류가 남 (필드와 폼 필드의 이름이 같을 때)
        // String strDatememo = request.getParameter("strdatememo");
        // LocalDate datememo = LocalDate.parse(strDatememo);
        entity.setDatememo(datememo);

        entity.setTotalprice(entity.getCompanyprice() + entity.getMyprice());

        return moneyRepo.save(entity);
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
