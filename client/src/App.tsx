import { Route, Routes } from 'react-router-dom';
import AuthPage from './pages/AuthPage';


function App() {
  return (
    <Routes>
     <Route path="/auth" element={<AuthPage />} />
     <Route path="/home" element={<div>שלום וברכה</div>} />

    </Routes>
  );
}

export default App;
