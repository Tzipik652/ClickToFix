import { Route, Routes } from 'react-router-dom';
import LoginPage from './pages/Login';
import RegisterPage from './pages/Register';
import { Suspense } from 'react';
import { Box, CircularProgress } from '@mui/material';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import Home from './pages/Home';


function App() {
  return (
      <Suspense
            fallback={
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  height: "80vh",
                }}
              >
                <CircularProgress color="secondary" />
              </Box>
            }
          >
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/" element={<PrivateRoute><Home /></PrivateRoute>}>

        </Route>
      </Routes>
      </Suspense>
  );
}

export default App;
