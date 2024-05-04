import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './Professores.css'; 

export const Professores = () => {
    const [professores, setProfessores] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/professor')
            .then(response => response.json())
            .then(data => setProfessores(data))
            .catch(error => console.error('Erro na requisição:', error));
    }, []);

    return (
        <div className="professor-container">
            <h2>Professores</h2>
            <div className="professor-grid">
                {professores.map(professor => (
                    <div key={professor.id} className="professor-card">
                        <p>{professor.id}</p>
                        <h3>{professor.nome}</h3>
                    </div>
                ))}
            </div>

            <div className='divBtns'>
                <Link to="/adicionarProfessor">
                    <button className='btnAdicionarProfessor'>Adicionar Professor</button>
                </Link>
                <Link to="/">
                    <button className='btnVoltar'>Voltar</button>
                </Link>
            </div>
        </div>
    );
};
