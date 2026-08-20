import {render, screen} from '@testing-library/react';
import ContractForm from "./ContractForm.tsx";
import { expect, test, vi } from 'vitest';
import {userEvent} from "@testing-library/user-event";

test('renders labelled inputs', () => {
    render(<ContractForm onAdd={() => {}}/>);

    expect(screen.getByRole('textbox', { name: /customer/i})).toBeInTheDocument();
    expect(screen.getByRole('button', {name: /add contract/i})).toBeInTheDocument();
});

test('submits the entered values', async () => {
    const onAdd = vi.fn();
    render(<ContractForm onAdd={onAdd} />);

    await userEvent.type(screen.getByRole('textbox', { name: /customer/i }), 'ACME');
    await userEvent.type(screen.getByRole('textbox', { name: /monthly rate/i }), '199.99');
    await userEvent.type(screen.getByRole('textbox', { name: /term/i }), '24');
    await userEvent.click(screen.getByRole('button', { name: /add contract/i }));


    expect(onAdd).toHaveBeenCalledWith('ACME', 199.99, 24);
});

test('shows errors and does not submit when fields are empty', async () => {
   const onAdd = vi.fn();
   render(<ContractForm onAdd={onAdd} />);

   await userEvent.click(screen.getByRole('button', {name: /add contract/i}));

   expect(screen.getAllByRole('alert')).toHaveLength(3);
   expect(onAdd).not.toHaveBeenCalled();
});