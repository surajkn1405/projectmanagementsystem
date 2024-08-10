package com.suraj.controller;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.suraj.modal.PlaneType;
import com.suraj.modal.User;
import com.suraj.response.PaymentLinkResponse;
import com.suraj.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private UserService userService;


    @PostMapping("/{planeType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlaneType planeType,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        int amount = 799 * 100;
        if (planeType.equals(PlaneType.ANNUALLY)){
            amount = amount*12;
            amount = (int) (amount*0.7);
        }

        try {
            RazorpayClient razorPay = new RazorpayClient(apiKey, apiSecret);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("Name", user.getFullName());
            customer.put("Email", user.getEmail());

            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            notify.put("notify", notify);

            paymentLinkRequest.put("callback_url", "http://localhost:5173/upgrade_plane/success?planeType=" + planeType);
            PaymentLink payment = razorPay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse response = new PaymentLinkResponse();
            response.setPayment_link_url(paymentLinkUrl);
            response.setPayment_link_id(paymentLinkId);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }catch (RazorpayException e){
            throw new RazorpayException("Something went wrong try after some time");
        }
    }

}
