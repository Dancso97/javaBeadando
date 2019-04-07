package betegkezelo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "betegek")
@XmlAccessorType (XmlAccessType.FIELD)
public class BetegekWrapper {
	
	@XmlElement(name = "beteg")
	private List <BetegekModel> betegek = null;

	public List<BetegekModel> getBetegek() {
		return betegek;
	}

	public void setBetegek(List<BetegekModel> betegek) {
		this.betegek = betegek;
	}
	
	

}
