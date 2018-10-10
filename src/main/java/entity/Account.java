package entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dto.AccountDTO;

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
	
	public Account(){}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", number=" + number + ", balance=" + balance + "]";
	}

}
