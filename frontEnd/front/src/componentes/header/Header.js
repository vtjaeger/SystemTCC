import React from 'react';
import { Link } from 'react-router-dom'; // Importe o Link do React Router
import './Header.css';

export const Header = () => {
    return (
        <header className="header">
            <img src='unisinos.png' alt="Unisinos" className="logo"></img>
            <nav>
                <ul className="menu">
                    <li><Link to="/professor">Professores</Link></li>
                    <li><a href="#">Coordenadores</a></li>
                    <li><a href="#">Apresentacoes</a></li>
                </ul>
            </nav>
        </header>
    )
}
