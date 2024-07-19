package test.interview.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String email;
    private int customerNumber;

}