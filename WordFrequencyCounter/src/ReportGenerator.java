import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class ReportGenerator {

	public void generateReport(Map<String, Counter> hm, Writer writer)
			throws IOException {

		TreeMap<Integer, TreeSet<String>> tm = new TreeMap<Integer, TreeSet<String>>(
				Collections.reverseOrder());
		int total_quantity = 0;

		for (String word : hm.keySet()) {
			total_quantity += hm.get(word).getCounter();
			
			if (!tm.containsKey(hm.get(word).getCounter())) {
				tm.put(hm.get(word).getCounter(), new TreeSet<String>());
			}
			tm.get(hm.get(word).getCounter()).add(word);
		}

		for (Entry<Integer, TreeSet<String>> entry : tm.entrySet()) {
			for (String string : tm.get(entry.getKey())) {
				System.out.println(string
						+ ", "
						+ entry.getKey()
						+ ", "
						+ String.format("%2.2f", (double) entry.getKey() * 100
								/ total_quantity) + "%");
			}
		}

		for (Entry<Integer, TreeSet<String>> entry : tm.entrySet()) {
			for (String string : tm.get(entry.getKey())) {
				writer.write(string
						+ ", "
						+ entry.getKey()
						+ ", "
						+ String.format("%2.2f", (double) entry.getKey() * 100
								/ total_quantity) + "%" + "\r\n");
			}
		}

	}

}
