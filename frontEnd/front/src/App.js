import './App.css';
import { Alunos } from './componentes/alunos/Alunos';
import { Bancas } from './componentes/bancas/Bancas';
import { Header } from './componentes/header/Header';

function App() {
  return (
    <div className="App">
      <header>
        <Header/>
      </header>
      <div className='getBancas'>
        <Bancas/>
      </div>
      <Alunos></Alunos>
    </div>
  );
}

export default App;
