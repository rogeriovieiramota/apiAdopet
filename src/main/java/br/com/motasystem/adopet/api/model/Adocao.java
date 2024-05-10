package br.com.motasystem.adopet.api.model;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "adocoes")
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id") Os atributos da entidade são equivalentes às colunas da tabela. Conforme a convenção, o nome da coluna na tabela é igual ao nome do atributo. Portanto, se o atributo chama id, então o nome da coluna também se chama id. Se o nome do atributo é data, o nome da coluna também. É REDUNDANTE
    private Long id;

    //@Column(name = "data")
    private LocalDateTime data;

    @ManyToOne(fetch=FetchType.LAZY) //o lazy faz nao carregar
    //@JsonBackReference("tutor_adocoes") QDO. O CONTROLLER PASSA DIRETAMENTE PARA A ENTIDADE, MAS NO CASO TEMOS OS DTO´s NÃO É PRECISO , ele faz um carregamento eager aqui, assim nao é muito bom , muito performatico
    //@JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @OneToOne(fetch = FetchType.LAZY) //o lazy faz nao carregar
    //@JoinColumn(name = "pet_id")
    @JsonManagedReference("adocao_pets")
    private Pet pet;

    //@Column(name = "motivo")
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAdocao status;

    //@Column(name = "justificativa_status")
    private String justificativaStatus;       

    public Adocao(Tutor tutor, Pet pet, String motivo) {
		super();
		this.tutor = tutor;
		this.pet = pet;
		this.motivo = motivo;
		this.status = StatusAdocao.AGUARDANDO_AVALIACAO;
		this.data = LocalDateTime.now();		
	}
    
    public Adocao() { //por causa do Jpa    	
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adocao adocao = (Adocao) o;
        return Objects.equals(id, adocao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Pet getPet() {
        return pet;
    }

    public String getMotivo() {
        return motivo;
    }

    public StatusAdocao getStatus() {
        return status;
    }

    public String getJustificativaStatus() {
        return justificativaStatus;
    }

	public void marcarComoAprovado() {
		this.status = StatusAdocao.APROVADO;
		
	}

	public void marcarComoReprovada(String justificativa) {
		this.status = StatusAdocao.REPROVADO;
		this.justificativaStatus = justificativa;
		
	}

}
