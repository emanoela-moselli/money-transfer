package revolut.dto;

public class TransactionDTO {

	private String originAccount;
	
	private String destinationAccount;

	private Double amount;
	
	public TransactionDTO(){}

	public TransactionDTO(String originAccount, String destinationAccount, Double amount) {
		super();
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
		this.amount = amount;
	}
	
	public String getOriginAccount() {
		return originAccount;
	}
	
	public String getDestinationAccount() {
		return destinationAccount;
	}
	
	public Double getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((destinationAccount == null) ? 0 : destinationAccount.hashCode());
		result = prime * result + ((originAccount == null) ? 0 : originAccount.hashCode());
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
		TransactionDTO other = (TransactionDTO) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (destinationAccount == null) {
			if (other.destinationAccount != null)
				return false;
		} else if (!destinationAccount.equals(other.destinationAccount))
			return false;
		if (originAccount == null) {
			if (other.originAccount != null)
				return false;
		} else if (!originAccount.equals(other.originAccount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransactionDTO [originAccount=" + originAccount + ", destinationAccount=" + destinationAccount
				+ ", amount=" + amount + "]";
	}
	
	
	
}
