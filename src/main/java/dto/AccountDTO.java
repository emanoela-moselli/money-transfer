package dto;

import entity.Account;

public class AccountDTO {

	private Long id;

	private String number;

	private Double balance;
	
	public AccountDTO(){}

	public AccountDTO(String number, Double balance) {
		super();
		this.number = number;
		this.balance = balance;
	}
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.number = account.getNumber();
		this.balance = account.getBalance();
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
		AccountDTO other = (AccountDTO) obj;
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
		return "AccountDTO [id=" + id + ", number=" + number + ", balance=" + balance + "]";
	}

}
