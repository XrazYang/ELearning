package org.example.matters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class WorkerEvent {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private boolean flag; //false 表示在督办数量明细表中

    @Getter
    private TodoEvents todo;

    @Getter
    private OffsetEvents offset;
}
