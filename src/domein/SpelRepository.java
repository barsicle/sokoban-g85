package domein;

import java.util.List;

import persistentie.SpelMapper;

public class SpelRepository {
	
	private final SpelMapper spelMapper;
	
	public SpelRepository() {
		spelMapper = new SpelMapper();
	}
	
	public List<String> getSpelNamen(){
		return spelMapper.getSpelNamen();
	}
	
	public Spel geefSpel(String spelNaam) {
		return spelMapper.geefSpel(spelNaam);
	}
	
	public void insertSpel(Spel spel) {
		spelMapper.insertSpel(spel);
	}

}
