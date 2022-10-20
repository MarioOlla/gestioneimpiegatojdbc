package it.prova.gestioneimpiegatojdbc.model;

import java.util.Date;
import java.util.Objects;

public class Impiegato {
	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataNascita;
	private Date dataAssunzione;
	private Compagnia compagnia;

	public Impiegato() {

	}

	public Impiegato(long id, String nome, String cognome, String codiceFiscale, Date dataNascita, Date dataAssunzione,
			Compagnia compagnia) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.compagnia = compagnia;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Compagnia getCompagnia() {
		return compagnia;
	}

	public void setCompagnia(Compagnia compagnia) {
		this.compagnia = compagnia;
	}

	@Override
	public String toString() {
		return "Impiegato [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codice fiscale=" + codiceFiscale
				+ ", data di nascita=" + dataNascita + ", data assunzione=" + dataAssunzione + ", compagnia="
				+ compagnia + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, cognome, dataAssunzione, dataNascita, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Impiegato other = (Impiegato) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(dataAssunzione, other.dataAssunzione)
				&& Objects.equals(dataNascita, other.dataNascita) && Objects.equals(nome, other.nome);
	}

}
