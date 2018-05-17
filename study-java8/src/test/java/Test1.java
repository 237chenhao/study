import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author cj-ch
 * @date 2017/12/21 上午9:47
 */
public class Test1 {

    public static void main(String[] args) {
        List<A> list = new ArrayList<>();
        list.add(null);
        BigDecimal totalRefundMoney = list.stream()
                .filter(Objects::nonNull)
                .map(A::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalRefundMoney);
    }

    @Data
    public static class A{
        private BigDecimal amount;
        private Long value;
    }
}
