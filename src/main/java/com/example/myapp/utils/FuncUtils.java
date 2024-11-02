
package com.example.myapp.utils;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;

@Component
public class FuncUtils {

    private WebClient webClient ; 
    public static WebClient webClientStatic ; 
    public Flux<String> callFunction(String uri , String functionName , String body){

        Flux<String> response = webClient
        .post()
        .uri(uri + "/" + functionName)
        .bodyValue(body)
        .acceptCharset(StandardCharsets.UTF_8)
        .accept(MediaType.TEXT_PLAIN)
        .exchangeToFlux( result->{
            if(result.statusCode().is2xxSuccessful())
            {
                return result.bodyToFlux(String.class) ; 
            }
            return Flux.error(new Exception("Error to Communicate with the Function")) ; 
        }) ; 
        return response ; 
    }

    public FuncUtils(WebClient webClient){
        this.webClient = webClient ; 
        FuncUtilsInitializeStatic();
    }
    public void FuncUtilsInitializeStatic(){
        if(FuncUtils.webClientStatic == null)
        FuncUtils.webClientStatic = webClient ; 
    }
    public static Flux<String> callFunctionStatic(String uri , String functionName , String body){
        Flux<String> response = webClientStatic
        .post()
        .uri(uri + "/" + functionName)
        .bodyValue(body)
        .acceptCharset(StandardCharsets.UTF_8)
        .accept(MediaType.TEXT_PLAIN)
        .exchangeToFlux( result->{
            if(result.statusCode().is2xxSuccessful())
            {
                return result.bodyToFlux(String.class) ; 
            }
            return Flux.error(new Exception("Error to Communicate with the Function")) ; 
        }) ; 
        return response ; 
    }
}