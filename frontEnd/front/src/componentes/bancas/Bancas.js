import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './Bancas.css'; 

export const Bancas = () => {
    const [bancas, setBancas] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/banca')
            .then(response => response.json())
            .then(data => setBancas(data))
            .catch(error => console.error('erro requisicao:', error));
    }, []);

    return (
        <div className="bancas-container">
            <h2>Bancas</h2>
            <div className="bancas-grid">
                {bancas.map(banca => (
                    <div key={banca.bancaId} className="banca-card">
                        <h3>{banca.titulo}</h3>
                        <p><strong>Integrantes:</strong> {banca.integrante1}, {banca.integrante2}, {banca.integrante3}</p>
                        <p><strong>Orientador:</strong> {banca.orientador}</p>
                        <p><strong>Data/Hora:</strong> {banca.dataHora}</p>
                    </div>
                ))}
            </div>

            <div className='divBtns'>
                <Link to="/adicionarBanca">
                    <button className='btnAdicionarBanca'>Adicionar banca</button>
                </Link>
                <Link to="/alunos">
                    <button className='btnAlunos'>Ir para <strong>ALUNOS</strong></button>
                </Link>
            </div>
        </div>
    );
};
