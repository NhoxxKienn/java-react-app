import {useState} from "react";

interface ContractFormProps {
    onAdd: (customer: string, monthlyRate: number, termMonths: number) => void;
}

export default function ContractForm({onAdd}: ContractFormProps) {
    const [customer, setCustomer] = useState('');
    const [monthlyRate, setMonthlyRate] = useState('');
    const [termMonths, setTermMonths] = useState('');

    function handleSubmit(e: { preventDefault: () => void; }) {
        e.preventDefault()
        onAdd(customer, Number(monthlyRate), Number(termMonths));
        setCustomer('')
        setMonthlyRate('')
        setTermMonths('')
    }

    return (
      <form onSubmit={handleSubmit}>
          <label>
              Customer
              <input
                  value={customer}
                  onChange={(e) => setCustomer(e.target.value)}
              />
          </label>
          <label>
              Monthly rate
              <input
                  value={monthlyRate}
                  onChange={(e) => setMonthlyRate(e.target.value)}
              />
          </label>
          <label>
              Term (months)
              <input
                value={termMonths}
                onChange={(e) => {setTermMonths(e.target.value)}}
              />
          </label>

          <button type="submit">
              Add Contract
          </button>
      </form>
    );
}