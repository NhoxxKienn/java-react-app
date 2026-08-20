import ContractCard from './ContractCard'
import ContractForm from './ContractForm'
import './App.css'
import {useEffect, useState} from "react";


interface Contract {
    id:number;
    customer: string;
    monthlyRate:number;
    start: string;
    termMonths: number;
}

const API = 'http://localhost:8080/api/contracts'
function App() {
    const [contracts, setContracts] = useState<Contract[]>([])
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    useEffect(() => {
        setLoading(true)
        setError(null)
        fetch(API)
            .then((res) => {
                if (!res.ok) throw new Error(`Request failed: ${res.status}`)
                return res.json()
            })
            .then((data) => setContracts(data))
            .catch((err: Error) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);
    function handleAdd(customer: string, monthlyRate: number, termMonths: number) {
        fetch(API,
            {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    customer,
                    monthlyRate,
                    termMonths,
                    start: new Date().toISOString().slice(0,10),
                }),
            })
            .then((res) =>  {
                if (!res.ok) throw new Error(`Could not create contract: ${res.status}`)
                return fetch(API)
            })
            .then((res) => res.json())
            .then((data:Contract[]) => setContracts(data))
            .catch((err: Error) => setError(err.message))
    }

  return (
    <>
      <h1>Contracts</h1>
        {loading && <p>Loading…</p>}
        {error && <p role="alert">{error}</p>}
      {contracts.map((contract) => (
          <ContractCard
              key={contract.id}
              customer={contract.customer}
              monthlyRate={contract.monthlyRate}
              start={contract.start}
              termMonths={contract.termMonths}
          />
          ))}
      <ContractForm
        onAdd={handleAdd}
      />
    </>
  )
}

export default App
