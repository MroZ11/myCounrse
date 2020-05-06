package com.zs.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.okta.spring.boot.oauth.config.OktaOAuth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OAuth2Controller {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private OktaOAuth2Properties oktaOAuth2Properties;



    @RequestMapping("/")
    public String index(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        return "index";
    }

    @RequestMapping("/sendEmail")
    public String sendEmail(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        return "index";
    }



    @RequestMapping("/userinfo")
    public String userinfo(Model model,OAuth2AuthenticationToken authentication) {
        // authentication.getAuthorizedClientRegistrationId() returns the
        // registrationId of the Client that was authorized during the Login flow
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),authentication.getName());
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        System.out.println(accessToken.getTokenValue());
        Map userAttributes = Collections.emptyMap();
        String userInfoEndpointUri = authorizedClient.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        if (!StringUtils.isEmpty(userInfoEndpointUri)) {// userInfoEndpointUri is optional for OIDC Clients
            userAttributes = WebClient.builder()
                    .filter(oauth2Credentials(authorizedClient))
                    .build().get().uri(userInfoEndpointUri).retrieve().bodyToMono(Map.class).block();
        }



        model.addAttribute("userAttributes", userAttributes);
        return "userinfo";
    }

    @RequestMapping("/remove")
    public void remove(HttpServletResponse response, OAuth2AuthenticationToken authentication) throws Exception{
        // authentication.getAuthorizedClientRegistrationId() returns the
        // registrationId of the Client that was authorized during the Login flow
        OAuth2AuthorizedClient authorizedClient = getAuthorizedClient(authentication);


        /*authorizedClient.getClientRegistration().getProviderDetails()
*/


        response.sendRedirect("https://dev-359953.okta.com/oauth2/default"+"/v1/logout?id_token_hint="+authentication.getName());
    }



    @RequestMapping("/remove_session")
    @ResponseBody
    public Object token(HttpServletResponse response, OAuth2AuthenticationToken authentication) throws Exception{
        String url = "https://dev-359953.okta.com/api/v1/users/"+authentication.getName()+"/sessions";
        //这个api 是官网提供的 okta官网api写得很详细
        Map map = WebClient.builder().build().delete().uri(url).headers(httpHeaders -> {
            httpHeaders.add(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE);
            httpHeaders.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
            //这个api_token需要在开发平台自己手动设置
            httpHeaders.add(HttpHeaders.AUTHORIZATION,"SSWS "+"00JQ5giVORzKmAc-0luCt8f9UO-gLlhTdGR1LMNeEC");
        }).retrieve().bodyToMono(Map.class).block();


        return map;
    }



    @RequestMapping("/scopes")
    @ResponseBody
    public Object sco(Model model,OAuth2AuthenticationToken authentication) {
        // authentication.getAuthorizedClientRegistrationId() returns the
        // registrationId of the Client that was authorized during the Login flow
        OAuth2AuthorizedClient authorizedClient = getAuthorizedClient(authentication);
        Set set = authorizedClient.getClientRegistration().getScopes();

        return set;
    }



    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

    private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {

        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " +
                                    authorizedClient.getAccessToken().getTokenValue()).build();
                    return Mono.just(authorizedRequest);
                });
    }
}