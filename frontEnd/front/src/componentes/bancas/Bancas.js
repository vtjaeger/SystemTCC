import React, { useState, useEffect } from 'react';
import './Bancas.css'; // Importe o arquivo de estilos CSS

export const Bancas = () => {
    const [bancas, setBancas] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/banca')
            .then(response => response.json())
            .then(data => setBancas(data))
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className="bancas-container"> {/* Adicione uma classe ao container das bancas */}
            <h2>Bancas</h2>
            <div className="bancas-grid"> {/* Use uma classe para criar um grid de bancas */}
                {bancas.map(banca => (
                    <div key={banca.bancaId} className="banca-card"> {/* Use uma classe para cada card de banca */}
                        <h3>{banca.titulo}</h3>
                        <p><strong>Integrantes:</strong> {banca.integrante1}, {banca.integrante2}, {banca.integrante3}</p>
                        <p><strong>Orientador:</strong> {banca.orientador}</p>
                        <p><strong>Data/Hora:</strong> {banca.dataHora}</p>
                        {/* Adicione mais informações conforme necessário */}
                    </div>
                ))}
            </div>
        </div>
    );
};