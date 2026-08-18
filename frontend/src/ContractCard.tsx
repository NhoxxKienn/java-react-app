interface ContractCardProps{
    customer: string,
    monthlyRate: number,
    start: string,
    termMonths: number,
}

export default function ContractCard({ customer, monthlyRate, start, termMonths} : ContractCardProps) {
    return (
        <div className="card">
            <h2>{customer}
                {termMonths >= 24 && <span className="badge">Long term</span>}
            </h2>
            <dl>
                <dt>Monthly rate</dt>
                <dd>{monthlyRate}</dd>

                <dt>Start</dt>
                <dd>{start}</dd>

                <dt>Term</dt>
                <dd>{termMonths} months</dd>
            </dl>
        </div>
    );
}