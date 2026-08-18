import ContractCard from './ContractCard'
import './App.css'

const contracts = [
  { id: 1, customer: 'Jack Bauer',   monthlyRate: 199.99, start: '2026-01-01', termMonths: 24 },
  { id: 2, customer: 'Kim Palmer',   monthlyRate: 50.00,  start: '2026-03-15', termMonths: 12 },
  { id: 3, customer: 'Tony Almeida', monthlyRate: 320.50, start: '2026-02-01', termMonths: 36 },
]

function App() {

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
    </>
  )
}

export default App
