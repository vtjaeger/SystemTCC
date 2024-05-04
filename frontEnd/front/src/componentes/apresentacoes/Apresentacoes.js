import React, { useState, useEffect } from 'react';
import './Apresentacoes.css';

export const Apresentacoes = () => {
    const [apresentacoes, setApresentacoes] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/apresentacao')
            .then(response => response.json())
            .then(data => setApresentacoes(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className='apresentacoes-container'>
            <h3>Apresentações marcadas</h3>
            <div className='apresentacoes-grid'>
                {apresentacoes.map(apresentacao => (
                    <div key={apresentacao.bancaId} className="apresentacao-card">
                        <p><strong>Id:</strong> {apresentacao.bancaId}</p>
                        <p><strong>Título:</strong> {apresentacao.titulo}</p>
                        <p><strong>Integrante 1:</strong> {apresentacao.integrante1}</p>
                        <p><strong>Integrante 2:</strong> {apresentacao.integrante2}</p>
                        <p><strong>Integrante 3:</strong> {apresentacao.integrante3}</p>
                        <p><strong>Orientador:</strong> {apresentacao.orientador}</p>
                        <p><strong>Professores:</strong> {apresentacao.nomesProfessores.join(', ')}</p>
                        <p><strong>Data e Hora:</strong> {apresentacao.dataHora}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};
