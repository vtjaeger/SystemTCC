import React, { useState, useEffect } from 'react';
import './Alunos.css'

export const Alunos = () => {
    const [alunos, setAlunos] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/aluno')
            .then(response => response.json())
            .then(data => setAlunos(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className='alunos-container'>
            <h3>Alunos cadastrados</h3>
            <div className='alunos-grid'>
                {alunos.map(aluno => (
                    <div key={aluno.alunoId} className="aluno-card"> {/* Use uma classe para cada card de banca */}
                    <p><strong>Id:</strong> {aluno.id}</p>
                    <p><strong>Nome:</strong> {aluno.nome}</p>
                </div>
                ))}
            </div>
        </div>
    );
};