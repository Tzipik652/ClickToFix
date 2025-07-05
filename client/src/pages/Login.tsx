import React, { useState } from 'react';
import {
  Container, Paper, Typography, Box,
  TextField, Button, MenuItem
} from '@mui/material';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline'; 
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const roles = ['customer', 'technician'];

const LoginPage = () => {
    const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();

  const loginSchema = Yup.object({
    email: Yup.string().email('Invalid email').required('Required'),
    password: Yup.string().required('Required'),
    phone: Yup.string().required('Required'),
    role: Yup.string().oneOf(roles).required('Required'),
  });

    const handleLogin = async (values: any, { setSubmitting }: any) => {
        setErrorMessage('');
        try {
            const url = values.role === 'customer'
            ? '/api/customers/login'
            : '/api/technicians/login';

            const payload = {
            ...values,
            passwordHash: values.password,
            };

            const res = await axios.post(url, payload);
            navigate("/home");
        } catch (error: any) {
            const errMsg = error?.response?.data || error.message;
            const status = error?.response?.status;

            setErrorMessage(errMsg);
             if (status === 401) {
                setErrorMessage("הסיסמה שגויה");
            }
            if (status === 500) {
                setErrorMessage("המשתמש לא קיים במערכת");
            }
        } finally {
            setSubmitting(false);
        }
    };

  return (
    <Container maxWidth="sm">
      <Paper elevation={3} sx={{ mt: 4, p: 3 }}>
        <Typography variant="h5" textAlign="center">התחברות</Typography>
        <Formik
          initialValues={{
            email: '',
            password: '',
            phone: '',
            role: 'customer'
          }}
          validationSchema={loginSchema}
          onSubmit={handleLogin}
        >
          {({ isSubmitting, errors, touched, handleChange, values }) => (
            <Form>
              <TextField
                fullWidth name="email" label="Email"
                value={values.email} onChange={handleChange}
                error={touched.email && Boolean(errors.email)}
                helperText={touched.email && errors.email}
                margin="normal"
              />
              <TextField
                fullWidth name="password" label="Password" type="password"
                value={values.password} onChange={handleChange}
                error={touched.password && Boolean(errors.password)}
                helperText={touched.password && errors.password}
                margin="normal"
              />
              <TextField
                fullWidth name="phone" label="Phone"
                value={values.phone} onChange={handleChange}
                error={touched.phone && Boolean(errors.phone)}
                helperText={touched.phone && errors.phone}
                margin="normal"
              />
              <TextField
                select fullWidth name="role" label="Role"
                value={values.role} onChange={handleChange} margin="normal"
              >
                {roles.map(role => (
                  <MenuItem key={role} value={role}>
                    {role === 'customer' ? 'לקוח' : 'טכנאי'}
                  </MenuItem>
                ))}
              </TextField>
            {errorMessage && (
                <Box
                    mt={2}
                    p={2}
                    display="flex"
                    flexDirection="column"
                    alignItems="center"
                    bgcolor="#ffebee"
                    border="1px solid #f44336"
                    borderRadius={2}
                    color="#b71c1c"
                    sx={{ animation: 'fadeIn 0.3s ease-in-out' }}
                >
                    <Box display="flex" alignItems="center" gap={1}>
                        <Typography>{errorMessage}</Typography>
                        <ErrorOutlineIcon />
                    </Box>

                    
                    
                </Box>
                )}
              <Button fullWidth type="submit" variant="contained" disabled={isSubmitting} sx={{ mt: 2 }}>
                התחבר
              </Button>
              <Button
                        fullWidth
                        variant="text"
                        onClick={() => navigate('/register')}
                        sx={{ mt: 1 }}
                    >
                    לעבור להרשמה
            </Button>
            </Form>
          )}
        </Formik>
      </Paper>
    </Container>
  );
};

export default LoginPage;
