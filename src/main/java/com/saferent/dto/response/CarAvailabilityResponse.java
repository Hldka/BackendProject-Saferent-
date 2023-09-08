package com.saferent.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CarAvailabilityResponse extends SfResponse{
    private boolean available;

    private double totalPrice ;

    public CarAvailabilityResponse(String message, boolean success, boolean available, double totalPrice) {
        super(message,success);
        this.available = available;
        this.totalPrice = totalPrice;
    }

}
