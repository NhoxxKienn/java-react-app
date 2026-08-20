import ContractCard from './ContractCard'
import ContractForm from './ContractForm'
import './App.css'
import {useState} from "react";

const init = [
    {id: 1, customer: 'Jack Bauer', monthlyRate: 199.99, start: '2026-01-01', termMonths: 24},
    {id: 2, customer: 'Kim Palmer', monthlyRate: 50.00, start: '2026-03-15', termMonths: 12},
    {id: 3, customer: 'Tony Almeida', monthlyRate: 320.50, start: '2026-02-01', termMonths: 36},
]


function App() {
    const [contracts, setContracts] = useState(init)

    function handleAdd(customer: string, monthlyRate: number, termMonths: number) {
        setContracts([
            ...contracts,
            {id: Date.now(), customer, monthlyRate, start: new Date().toISOString().slice(0, 10),termMonths}
        ])
    }

  return (
    <>
      <h1>Contracts</h1>
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
