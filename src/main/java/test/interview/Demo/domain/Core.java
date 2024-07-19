package test.interview.demo.domain;

import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;


public interface Core {

    public UUID getId();
    public Date getCreatedTime();

}