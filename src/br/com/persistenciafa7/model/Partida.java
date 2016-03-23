package br.com.persistenciafa7.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="PARTIDAS")
@TableGenerator(table="SEQUENCE_GENERATOR", 
				name="PARTIDA_GEN", 
				pkColumnName="seq_id", 
				valueColumnName="next_val",	
				pkColumnValue="id_partida", 
				allocationSize=1)
public class Partida extends BaseModel {

	private static final long serialVersionUID = -1121004077701455513L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="PARTIDA_GEN")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_mandante")
	@Basic(optional = false)
	private Time mandante;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_visitante")
	@Basic(optional = false)
	private Time visitante;
	
	@Column(name="data_hora")
	@Basic(optional = false)
	private Date data ;
	
	@Column(name="gols_mantande")
	@Basic(optional = false)
	private Integer golsMandante;
	
	@Column(name="gols_visitante")
	@Basic(optional = false)
	private Integer golsVisitante;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="ESCALACOES", 
			   joinColumns=@JoinColumn(name="id_partida"),
			   inverseJoinColumns=@JoinColumn(name="id_jogador"))
	@Basic(optional = false)
	private Collection<Jogador> jogadores;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getMandante() {
		return mandante;
	}

	public void setMandante(Time mandante) {
		this.mandante = mandante;
	}

	public Time getVisitante() {
		return visitante;
	}

	public void setVisitante(Time visitante) {
		this.visitante = visitante;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getGolsMandante() {
		return golsMandante;
	}

	public void setGolsMandante(Integer golsMandante) {
		this.golsMandante = golsMandante;
	}

	public Integer getGolsVisitante() {
		return golsVisitante;
	}

	public void setGolsVisitante(Integer golsVisitante) {
		this.golsVisitante = golsVisitante;
	}

	public Collection<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(Collection<Jogador> jogadores) {
		this.jogadores = jogadores;
	}
}