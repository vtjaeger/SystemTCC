import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './Alunos.css'

export const Alunos = () => {
    const [alunos, setAlunos] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/aluno')
            .then(response => response.json())
            .then(data => setAlunos(data))
            .catch(error => console.error('Erro de requisicao :', error));
    }, []);

    return (
        <div className='alunos-container'>
            <h3>Alunos cadastrados</h3>
            <div className='alunos-grid'>
            {alunos.map(aluno => (
                <div key={`${aluno.id}-${aluno.alunoId}`} className="aluno-card">
                    <p><strong>Id:</strong> {aluno.id}</p>
                    <p><strong>Nome:</strong> {aluno.nome}</p>
                </div>
            ))}
            </div>

            <div className='divBtn'>
                <Link to="/adicionarAluno">
                    <button className='btnAdicionarAluno'>Adicionar Aluno</button>
                </Link>

                <Link to="/">
                    <button className='btnVoltar'>Voltar</button>
                </Link>

            </div>

        </div>
    );
};