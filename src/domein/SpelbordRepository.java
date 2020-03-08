package domein;

import persistentie.SpelbordMapper;

public class SpelbordRepository {
	private final SpelbordMapper spelbordMapper;
	
	public SpelbordRepository() {
		this.spelbordMapper = new SpelbordMapper();
	}
	
	public Spelbord geefSpelbordMetVelden(String spelbordNaam) {
		return spelbordMapper.geefBordMetVelden(spelbordNaam);
	}
}
