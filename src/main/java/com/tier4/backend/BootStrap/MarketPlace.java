package com.tier4.backend.BootStrap;


import com.tier4.backend.web.Domain.Pot;
import com.tier4.backend.web.Domain.Vector;
import com.tier4.backend.web.Model.Pot.PotDto;

import java.util.List;

public class MarketPlace {

   public static final List<PotDto> marketPlacePot = List.of(

                                PotDto.builder()
                                        .title("Netflix Mobile-only plan")
                                        .description("1-screen support, HD content, mobile-only")
                                        .amount(199.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/netflix_PNG22.png")
                                        .build(),

                                PotDto.builder()
                                        .title("Netflix Basic plan")
                                        .description("1-screen support, HD content, access across all platforms")
                                        .amount(499.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/netflix_PNG22.png")
                                        .build(),

                                PotDto.builder()
                                        .title("Netflix Standard plan")
                                        .description("2-screen support, HD content, access across all platforms")
                                        .amount(649.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/netflix_PNG22.png")
                                        .build(),   
                                        
                                PotDto.builder()
                                        .title("Netflix Premium plan")
                                        .description("4-screen support, HD content, access across all platforms")
                                        .amount(799.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/netflix_PNG22.png")
                                        .build(), 

                                PotDto.builder()
                                        .title("Zomato Pro Membership - 3 Month Membership")
                                        .description("Valid for 3 Months\nValid across India\nZomato Gold experience.\nUp to 40% off\nOne free dish\nTwo free drinks")
                                        .amount(200.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/HEADER_FINAL.png")
                                        .build() ,
                                          
                                PotDto.builder()
                                        .title("Swiggy Binge - 1 month membership")
                                        .description("The Swiggy Super Binge plan provides unlimited free deliveries from any restaurant for a month. Additionally, \nusers will get buy one get one offer from popular restaurants with this plan.")
                                        .amount(329.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/Swiggy-PNG-Logo-1024x1024.png")
                                        .build(),
                                        
                                PotDto.builder()
                                        .title("Swiggy Bite - 1 month membership")
                                        .description("	As part of this plan, users will be able to avail 10 free deliveries from restaurants within five kilometres of their location.")
                                        .amount(169.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/Swiggy-PNG-Logo-1024x1024.png")
                                        .build(),
                                
                                PotDto.builder()
                                        .title("Zomato Pro Membership - Annual Pro Membership")
                                        .description("All Pro benefits valid for 12 Months\nValid across India\nZomato Gold experience.\nUp to 40% off\nOne free dish\nTwo free drinks")
                                        .amount(750.0)
                                        .autoDeduct(true)
                                        .imageLink("https://hackrx.s3.ap-south-1.amazonaws.com/HEADER_FINAL.png")
                                        .build()

                             );

}

