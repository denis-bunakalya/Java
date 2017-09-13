import java.util.HashMap;
import java.util.Map;


public class StatStorage {
	
	private HashMap<String, Counter> hm;

	public StatStorage() {
		
		hm = new HashMap<String, Counter>();
		
	}
	
	public void saveWord( String word ) {
	
		if (hm.containsKey(word)) {
			hm.get(word).incrementCounter();
		} else {
			hm.put(word, new Counter());
		}
		
	}
	
	public Map<String, Counter> getMap() {
		
		return hm;
		
	}	
	
}
