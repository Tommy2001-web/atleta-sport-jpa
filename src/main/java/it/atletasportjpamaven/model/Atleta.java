package it.atletasportjpamaven.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "atleta")
public class Atleta {
    //id, nome, cognome, dataDiNascita, codice, numeroMedaglieVinte
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "data_di_nascita")
    private LocalDate dataDiNascita;

    @Column(name = "codice")
    private String codice;

    @Column(name = "numero_medaglie_vinte")
    private Integer numeroMedaglieVinte;

    @ManyToMany
    @JoinTable(name = "atleta_sport", joinColumns = @JoinColumn(name = "atleta_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sport_id", referencedColumnName = "id"))
    private Set<Sport> sport = new HashSet<>();

    public Atleta() {
    }

    public Atleta(String nome, String cognome, LocalDate dataDiNascita, String codice, Integer numeroMedaglieVinte) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.codice = codice;
        this.numeroMedaglieVinte = numeroMedaglieVinte;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String coidce) {
        this.codice = coidce;
    }

    public Integer getNumeroMedaglieVinte() {
        return numeroMedaglieVinte;
    }

    public void setNumeroMedaglieVinte(Integer numeroMedaglieVinte) {
        this.numeroMedaglieVinte = numeroMedaglieVinte;
    }

    public Set<Sport> getSport() {
        return sport;
    }

    public void setSport(Set<Sport> sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", coidce='" + codice + '\'' +
                ", numeroMedaglieVinte=" + numeroMedaglieVinte +
                '}';
    }
}
