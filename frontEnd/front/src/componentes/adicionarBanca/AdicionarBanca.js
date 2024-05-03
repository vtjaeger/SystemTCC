import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './AdicionarBanca.css';

export const AdicionarBanca = () => {
    const [titulo, setTitulo] = useState('');
    const [integrante1, setIntegrante1] = useState('');
    const [integrante2, setIntegrante2] = useState('');
    const [integrante3, setIntegrante3] = useState('');
    const [orientador, setOrientador] = useState('');
    const [professores, setProfessores] = useState([]);
    const [selectedProfessores, setSelectedProfessores] = useState(['', '', '']);

    useEffect(() => {
        const fetchProfessores = async () => {
            try {
                const response = await fetch('http://localhost:8080/professor');
                const data = await response.json();
                setProfessores(data);
            } catch (error) {
                console.error('erro requisicao professores:', error);
            }
        };

        fetchProfessores();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const bancaData = {
            titulo,
            integrante1,
            integrante2,
            integrante3,
            orientador,
            professores: selectedProfessores.map(id => ({ id }))
        };

        try {
            const response = await fetch('http://localhost:8080/banca', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(bancaData)
            });

            if (response.ok) {
                console.log('cadastrado');
            } else {
                console.error("erro1");
                console.error('erro no cadastro:', response.statusText);
            }
        } catch (error) {
            console.error("erro2");
            console.error('erro de requisicao:', error);
        }
    };

    return (
        <div>
            <div className="adicionar-banca-container">
                <h2>Adicionar Banca</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Título:</label>
                        <input type="text" value={titulo} onChange={(e) => setTitulo(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label>Integrante 1:</label>
                        <input type="text" value={integrante1} onChange={(e) => setIntegrante1(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label>Integrante 2:</label>
                        <input type="text" value={integrante2} onChange={(e) => setIntegrante2(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label>Integrante 3:</label>
                        <input type="text" value={integrante3} onChange={(e) => setIntegrante3(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label>Orientador:</label>
                        <input type="text" value={orientador} onChange={(e) => setOrientador(e.target.value)} required />
                    </div>
                    <div className="form-group">
                        <label>Professores:</label>
                        {[1, 2, 3].map((index) => (
                            <select key={index} value={selectedProfessores[index - 1]} onChange={(e) => {
                                const newSelectedProfessores = [...selectedProfessores];
                                newSelectedProfessores[index - 1] = e.target.value;
                                setSelectedProfessores(newSelectedProfessores);
                            }}>
                                <option value="">Selecione um professor</option>
                                {professores.map(professor => (
                                    <option key={professor.id} value={professor.id}>
                                        {professor.nome}
                                    </option>
                                ))}
                            </select>
                        ))}
                    </div>
                    <button type="submit">Adicionar Banca</button>
                </form>
            </div>
            <div className='divBtn'>
                <Link to="/">
                    <button className='btnVoltar'>Voltar</button>
                </Link>
            </div>
        </div>
    );
};
