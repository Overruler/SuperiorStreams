package where;

import java.util.ArrayList;
import java.util.List;
import utils.streams.Streams;

public class WhereClauses {

	static ArrayList<String> removeBadWords(List<String> list) {
		return Streams.where(list, s -> s.contains("fuck") == false);
	}
}
