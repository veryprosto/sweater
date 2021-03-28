package ru.veryprosto.sweater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.veryprosto.sweater.domain.Message;
import ru.veryprosto.sweater.repos.MessageRepo;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    MessageRepo messageRepo;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    //тут в скобках ничего не указали, значит после отработки этого метода мы окажемся на той же странице где и нажали кнопку для выполнения этого метода
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepo.save(message); //Метод save - метод суперкласса(CrudRepository) встроенного в спрингбут от которого наследуется наш репозиторий
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages; //так как в ниже написаном if два разных метода возвращают List и Iterable, а List - наследник Iterable поэтому объявляем переменную Iterable
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter); //этот метод возвращает лист
        } else {
            messages = messageRepo.findAll(); //этот метод возвращает iterable
        }

        model.put("messages", messages);
        return "main";
    }

}