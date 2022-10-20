package it.prova.gestioneimpiegatojdbc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Compagnia {
	private Long id;
	private String ragioneSociale;
	private Integer fatturatoAnnuo;
	private Date dataFondazione;
	private List<Impiegato> impiegati = new ArrayList<>();

	public Compagnia() {

	}

	public Compagnia(long id, String ragioneSociale, int fatturatoAnnuo, Date dataFondazione) {
		super();
		this.id = id;
		this.ragioneSociale = ragioneSociale;
		this.fatturatoAnnuo = fatturatoAnnuo;
		this.dataFondazione = dataFondazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public Integer getFatturatoAnnuo() {
		return fatturatoAnnuo;
	}

	public void setFatturatoAnnuo(int fatturatoAnnuo) {
		this.fatturatoAnnuo = fatturatoAnnuo;
	}

	public Date getDataFondazione() {
		return dataFondazione;
	}

	public void setDataFondazione(Date dataFondazione) {
		this.dataFondazione = dataFondazione;
	}

	public List<Impiegato> getImpiegati() {
		return impiegati;
	}

	public void setImpiegati(List<Impiegato> impiegati) {
		this.impiegati = impiegati;
	}

	@Override
	public String toString() {
		return "Compagnia [id=" + id + ", ragione sociale=" + ragioneSociale + ", fatturato annuo=" + fatturatoAnnuo
				+ ", data di fondazione=" + dataFondazione + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compagnia other = (Compagnia) obj;
		return Objects.equals(dataFondazione, other.dataFondazione)
				&& Objects.equals(fatturatoAnnuo, other.fatturatoAnnuo)
				&& Objects.equals(ragioneSociale, other.ragioneSociale);
	}

}
