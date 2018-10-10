package entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity
public class Transaction {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Calendar date;
	
	@Enumerated(EnumType.STRING)
	private ETransaction type;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Account originAccount;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Account destinationAccount;
	
	public Transaction(){}

	public Transaction(Calendar date, ETransaction type, Account originAccount,  Account destinationAccount) {
		super();
		this.date = date;
		this.type = type;
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public ETransaction getType() {
		return type;
	}
	
	public Account getOriginAccount() {
		return originAccount;
	}
	
	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	
	public Account getDestinationAccount() {
		return destinationAccount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((destinationAccount == null) ? 0 : destinationAccount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((originAccount == null) ? 0 : originAccount.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Transaction other = (Transaction) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (destinationAccount == null) {
			if (other.destinationAccount != null)
				return false;
		} else if (!destinationAccount.equals(other.destinationAccount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (originAccount == null) {
			if (other.originAccount != null)
				return false;
		} else if (!originAccount.equals(other.originAccount))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", type=" + type + ", originAccount=" + originAccount
				+ ", destinationAccount=" + destinationAccount + "]";
	}
	
}
