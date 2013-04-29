package gotchas;

import junit.framework.TestCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Topic {
    private Pattern pattern = Pattern.compile("^Change [0-9]++ ");

    public boolean matches(String haystack) {
        Matcher matcher = pattern.matcher(haystack);
        return matcher.lookingAt();
    }
}

public class RegexTest extends TestCase {
    private Topic topic() {
        Topic topic = new Topic();
        return topic;
    }

    public final void test_callPatternTwice() {
        String change1 = "Change 1 ";
        String change2 = "Change 222 ";
        Topic topic = topic();
        assertTrue(topic.matches(change1));
        assertTrue(topic.matches(change2));
    }
}
