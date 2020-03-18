package domein;

import java.util.List;

import persistentie.SpelbordMapper;

public class SpelbordRepository {
	private final SpelbordMapper spelbordMapper;
	
	public SpelbordRepository() {
		this.spelbordMapper = new SpelbordMapper();
	}
	
	public Spelbord geefSpelbordMetVelden(String spelbordNaam) {
		return spelbordMapper.geefBordMetVelden(spelbordNaam);
	}
	
	public List<Spelbord> geefSpelborden(String spelNaam){
		return spelbordMapper.geefBorden(spelNaam);
	}
	
	public void insertBord(Spelbord spelbord) {
		spelbordMapper.insertBord(spelbord);
	}
}
