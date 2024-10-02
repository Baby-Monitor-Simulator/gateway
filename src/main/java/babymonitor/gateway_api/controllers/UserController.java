package babymonitor.gateway_api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController    {
    //GET
    //@ResponseStatus(code = HttpStatus.OK, reason = "OK")
    @GetMapping("/")
    public String GetAll() throws ExecutionException, InterruptedException {

        return "Here are all the users";
    }
}
