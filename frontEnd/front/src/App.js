import { BrowserRouter, Route, Switch } from 'react-router-dom';
import './App.css';
import { Header } from './componentes/header/Header';
import { Bancas } from './componentes/bancas/Bancas';
import { Alunos } from './componentes/alunos/Alunos';
import { AdicionarBanca } from './componentes/adicionarBanca/AdicionarBanca';
import {AdicionarAluno} from './componentes/adicionarAluno/AdicionarAluno'

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Switch>
        <Route path="/" exact component={Bancas} />
        <Route path="/alunos" component={Alunos} />
        <Route path="/adicionarBanca" component={AdicionarBanca} />
        <Route path="/adicionarAluno" component={AdicionarAluno} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
