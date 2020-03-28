package domein;

import java.util.List;
import java.util.Objects;

import persistentie.SpelbordMapper;

public class SpelbordRepository {
	private final SpelbordMapper spelbordMapper;
	
	public SpelbordRepository() {
		this.spelbordMapper = new SpelbordMapper();
	}
	
	public Spelbord geefSpelbordMetVelden(String spelbordNaam, int aantalRijen, int aantalKolommen) {
		return spelbordMapper.geefBordMetVelden(spelbordNaam, aantalRijen, aantalKolommen);
	}
	
	public List<Spelbord> geefSpelborden(String spelNaam){
		return spelbordMapper.geefBorden(spelNaam);
	}
	
	public void insertBord(Spelbord spelbord, String spelNaam) {
		spelbordMapper.insertBord(spelbord, spelNaam);
	}
	
	public boolean bordExists(String spelbordNaam) {
		return !Objects.equals(spelbordMapper.geefBordMetVelden(spelbordNaam, BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen()), null); 
	}
}
