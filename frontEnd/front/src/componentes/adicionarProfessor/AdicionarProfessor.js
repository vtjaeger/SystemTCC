import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import './AdicionarProfessor.css';

export const AdicionarProfessor = () => {
    const [nome, setNome] = useState('');
    const history = useHistory(); 

    const handleSubmit = async (event) => {
        event.preventDefault();

        const profData = {
            nome: nome
        };

        try {
            const response = await fetch('http://localhost:8080/professor', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(profData)
            });

            if (response.ok) {
                console.log('professor cadastrado');
                history.push('/professor'); 
            } else {
                console.error('erro em cadastrar professor:', response.statusText);
            }
        } catch (error) {
            console.error('erro de requisicao:', error);
        }
    };

    return (
        <div className="adicionar-professor-container">
            <h2>Adicionar Professor</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Nome:</label>
                    <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} required />
                </div>
                <button type="submit">Enviar</button>
            </form>

            <div className='divBtn'>
                <Link to="/professor">
                    <button className='btnVoltar'>Voltar</button>
                </Link>
            </div>
        </div>
    );
};
