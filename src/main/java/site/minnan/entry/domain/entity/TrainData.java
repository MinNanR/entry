package site.minnan.entry.domain.entity;

import lombok.Data;
import site.minnan.entry.domain.aggregate.Train;

@Data
public class TrainData extends Train {

    private Integer travelerCount;
}
