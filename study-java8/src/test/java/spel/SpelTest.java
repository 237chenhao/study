package spel;

import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Created by chenhao on 2017/11/16.
 */
public class SpelTest {

    @Test
    public void test1(){
        ExpressionParser parser =new SpelExpressionParser();
        StandardEvaluationContext context =new StandardEvaluationContext();
        context.setVariable("message","hhhhhhh");
        Object obj = parser
                .parseExpression("new String(#message + 'TTTTTT')")
                .getValue(context);
        System.out.println(obj);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.toInstant(ZoneOffset.of("+8")));


    }
}
