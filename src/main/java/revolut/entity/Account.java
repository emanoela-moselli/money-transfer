package revolut.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import revolut.dto.AccountDTO;

@Table
@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	private String number;

	private Double balance;
	
	@OneToMany(mappedBy="originAccount")
	private List<Transaction> transactions; 
	
	@Version
	private Integer version;
	
	public Account(){}
	
	public Account(Long id, String number, Double balance) {
		this(number, balance);
		this.id = id;
	}

	public Account(String number, Double balance) {
		super();
		this.number = number;
		this.balance = balance;
	}

	public Account(AccountDTO accountDTO) {
		this.id = accountDTO.getId();
		this.number = accountDTO.getNumber();
		this.balance = accountDTO.getBalance();
	}
	
	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public Double getBalance() {
		return balance;
	}
	
	public Integer getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", number=" + number + ", balance=" + balance + "]";
	}

}
