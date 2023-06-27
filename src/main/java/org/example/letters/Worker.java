package org.example.letters;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Worker implements Comparable<Worker> {

    private String name;
    private int nums;
    private boolean overHundred;
    private boolean key;

    /**
     * 姓名相同，且航线相同,且是是否超过一百字相同，才更新nums
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker worker = (Worker) o;
        return overHundred == worker.overHundred && key == worker.key && name.equals(worker.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, overHundred, key);
    }

    @Override
    public int compareTo(Worker o) {
        if (this.nums < o.nums) {
            return 1;
        } else if (this.nums == o.nums) {
            return 0;
        } else {
            return -1;
        }
    }
}
