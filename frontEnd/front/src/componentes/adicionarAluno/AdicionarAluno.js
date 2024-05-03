import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import './AdicionarAluno.css';

export const AdicionarAluno = () => {
    const [nome, setNome] = useState('');
    const history = useHistory(); 

    const handleSubmit = async (event) => {
        event.preventDefault();

        const alunoData = {
            nome: nome
        };

        try {
            const response = await fetch('http://localhost:8080/aluno', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(alunoData)
            });

            if (response.ok) {
                console.log('Aluno cadastrado com sucesso!');
                history.push('/alunos'); 
            } else {
                console.error('erro em cadastrar aluno:', response.statusText);
            }
        } catch (error) {
            console.error('erro de requisicao:', error);
        }
    };

    return (
        <div className="adicionar-aluno-container">
            <h2>Adicionar Aluno</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Nome:</label>
                    <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} required />
                </div>
                <button type="submit">Enviar</button>
            </form>

            <div className='divBtn'>
                <Link to="/alunos">
                    <button className='btnVoltar'>Voltar</button>
                </Link>
            </div>
        </div>
    );
};
