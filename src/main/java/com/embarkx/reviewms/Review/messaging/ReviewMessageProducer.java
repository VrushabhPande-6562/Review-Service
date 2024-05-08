package com.embarkx.reviewms.Review.messaging;

import com.embarkx.reviewms.Review.Review;
import com.embarkx.reviewms.Review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private  final RabbitTemplate rabbitTemplate;


    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(Review review){
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(review.getId());
        reviewMessage.setTitle(reviewMessage.getTitle());
        reviewMessage.setDescription(reviewMessage.getDescription());
        reviewMessage.setRating(reviewMessage.getRating());
        reviewMessage.setCompanyId(review.getCompanyId());
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);

    }
}
