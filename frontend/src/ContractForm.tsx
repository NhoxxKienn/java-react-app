import {useState} from "react";

interface ContractFormProps {
    onAdd: (customer: string, monthlyRate: number, termMonths: number) => void;
}

export default function ContractForm({onAdd}: ContractFormProps) {
    const [customer, setCustomer] = useState('');
    const [monthlyRate, setMonthlyRate] = useState('');
    const [termMonths, setTermMonths] = useState('');
    const [errors, setErrors] = useState<{ customer?: string; monthlyRate?: string; termMonths?: string }>({});

    function handleSubmit(e: { preventDefault: () => void; }) {
        e.preventDefault()

        const nextErrors: { customer?: string; monthlyRate?: string, termMonths?: string} = {};
        if (customer.trim() === '') {
            nextErrors.customer = 'Customer is required';
        }
        if (Number(monthlyRate) <= 0 || monthlyRate === '') {
            nextErrors.monthlyRate = 'Monthly rate must be positive';
        }
        if (Number(termMonths) <= 0 || termMonths === '') {
            nextErrors.termMonths = 'Term must be at least one month';
        }
        if (Object.keys(nextErrors).length > 0) {
            setErrors(nextErrors);
            return;
        }
        setErrors({});
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
                  aria-invalid={errors.customer? true : undefined}
                  aria-describedby={errors.customer? 'customer-error' : undefined}
              />
          </label>
          {errors.customer && <p id="customer-error" role="alert">{errors.customer}</p>}
          <label>
              Monthly rate
              <input
                  value={monthlyRate}
                  onChange={(e) => setMonthlyRate(e.target.value)}
                  aria-invalid={errors.monthlyRate ? true : undefined}
                  aria-describedby={errors.monthlyRate ? 'rate-error' : undefined}
              />
          </label>
          {errors.monthlyRate && <p id="rate-error" role="alert">{errors.monthlyRate}</p>}
          <label>
              Term (months)
              <input
                value={termMonths}
                onChange={(e) => {setTermMonths(e.target.value)}}
                aria-invalid={errors.termMonths ? true : undefined}
                aria-describedby={errors.termMonths ? 'term-error' : undefined}
              />
          </label>
          {errors.termMonths && <p id="term-error" role="alert">{errors.termMonths}</p>}

          <button type="submit">
              Add Contract
          </button>
      </form>
    );
}