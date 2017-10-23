import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {

        Map<Integer,Integer> list = new HashMap<>();

        Map<String, Integer> newmap =
        list.values().stream().sorted((o1,o2) -> o1.compareTo(o2))
                .collect(Collectors.toMap((o)->o,));





    }
}
