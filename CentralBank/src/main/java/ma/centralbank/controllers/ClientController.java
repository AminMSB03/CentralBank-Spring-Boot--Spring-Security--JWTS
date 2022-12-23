package ma.centralbank.controllers;


import ma.centralbank.reqObjects.RegisterForm;
import ma.centralbank.reqObjects.Response;
import ma.centralbank.reqObjects.Transaction;
import ma.centralbank.services.client.ClientService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/register",method =RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@ModelAttribute RegisterForm registerForm) throws IOException {
        this.clientService.register(registerForm);
        return new ResponseEntity<>(Response.builder().error("No error is there").value("votre compte bancaire a été créé").build(), HttpStatusCode.valueOf(200));
    }


    public ResponseEntity<Response> makeTransaction(@RequestBody Transaction transaction){

        Boolean res = this.clientService.makeTransaction(transaction.getIdSenderBankAccount(), transaction.getIdReceiverBankAccount(), transaction.getAmount());
        if(res){
            return new ResponseEntity<>(Response.builder().error("No error is there").value("Votre transaction a été bien réalisée").build(),HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>(Response.builder().error("sending bad data").value("Votre transaction n'a pas été bien effectuée ").build(),HttpStatusCode.valueOf(400));
        }
    }


}