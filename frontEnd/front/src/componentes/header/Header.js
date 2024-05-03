import React from 'react';
import './Header.css';

export const Header = () => {
    return (
        <header className="header">
            <img src='unisinos.png' alt="Unisinos" className="logo"></img>
            <nav>
                <ul className="menu">
                    <li><a href="#">opcao 1</a></li>
                    <li><a href="#">opcao2</a></li>
                    <li><a href="#">opcao3</a></li>
                </ul>
            </nav>
        </header>
    )
}
