import React, { useState } from 'react';
import {
  Container, Paper, Typography, Box,
  TextField, Button, MenuItem
} from '@mui/material';
import { Formik, Form } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const roles = ['customer', 'technician'];
const expertiseOptions = ['חשמל', 'אינסטלציה', 'מזגנים', 'מחשבים'];

const AuthPage = () => {
  const [isRegister, setIsRegister] = useState(false);
    const navigate= useNavigate();
  const validationSchema = (isRegister: boolean) =>
    Yup.object({
      name: isRegister
        ? Yup.string().required('Name is required')
        : Yup.string().notRequired(),
      email: Yup.string().email('Invalid email').required('Required'),
      password: Yup.string().required('Required'),
      phone: Yup.string().required('Required'),
      role: Yup.string().oneOf(roles).required('Required'),
      address: Yup.string().when('role', {
        is: 'customer',
        then: (schema) => schema.required('Address is required'),
        otherwise: (schema) => schema.notRequired()
      }),
      location: Yup.object().shape({
        lat: Yup.number().typeError('Must be a number').required('Latitude is required'),
        lng: Yup.number().typeError('Must be a number').required('Longitude is required'),
      }),
      expertise: Yup.array().of(Yup.string()).when('role', {
        is: 'technician',
        then: (schema) => schema.min(1, 'Select at least one expertise'),
        otherwise: (schema) => schema.notRequired()
      })
    });

  const handleSubmit = async (values: any, { setSubmitting }: any) => {
    try {
      const url =
        values.role === 'customer'
          ? `/api/customers${isRegister ? '/register' : '/login'}`
          : `/api/technicians${isRegister ? '/register' : '/login'}`;
      const res = await axios.post(url, values);
      alert('Success: ' + JSON.stringify(res.data));
        {!isRegister && navigate("/home")}
    } catch (error: any) {
      alert('Error: ' + error?.response?.data || error.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Container maxWidth="sm">
      <Paper elevation={3} sx={{ mt: 4, p: 3 }}>
        <Typography variant="h5" textAlign="center">
          {isRegister ? 'הרשמה' : 'התחברות'}
        </Typography>

        <Formik
          initialValues={{
            name: '',
            email: '',
            password: '',
            phone: '',
            role: 'customer',
            address: '',
            location: { lat: '', lng: '' },
            expertise: [],
          }}
          validationSchema={() => validationSchema(isRegister)}
          onSubmit={handleSubmit}
        >
          {({ isSubmitting, errors, touched, handleChange, values }) => (
            <Form>
              <Box mt={2}>
                {isRegister && (
                  <TextField
                    fullWidth
                    name="name"
                    label="Name"
                    value={values.name}
                    onChange={handleChange}
                    error={touched.name && Boolean(errors.name)}
                    helperText={touched.name && errors.name}
                    margin="normal"
                  />
                )}
                <TextField
                  fullWidth
                  name="email"
                  label="Email"
                  value={values.email}
                  onChange={handleChange}
                  error={touched.email && Boolean(errors.email)}
                  helperText={touched.email && errors.email}
                  margin="normal"
                />
                <TextField
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  value={values.password}
                  onChange={handleChange}
                  error={touched.password && Boolean(errors.password)}
                  helperText={touched.password && errors.password}
                  margin="normal"
                />
                <TextField
                  fullWidth
                  name="phone"
                  label="Phone"
                  value={values.phone}
                  onChange={handleChange}
                  error={touched.phone && Boolean(errors.phone)}
                  helperText={touched.phone && errors.phone}
                  margin="normal"
                />
                <TextField
                  select
                  fullWidth
                  name="role"
                  label="Role"
                  value={values.role}
                  onChange={handleChange}
                  margin="normal"
                >
                  {roles.map(role => (
                    <MenuItem key={role} value={role}>
                      {role === 'customer' ? 'לקוח' : 'טכנאי'}
                    </MenuItem>
                  ))}
                </TextField>

                {isRegister && values.role === 'customer' && (
                  <TextField
                    fullWidth
                    name="address"
                    label="כתובת"
                    value={values.address}
                    onChange={handleChange}
                    error={touched.address && Boolean(errors.address)}
                    helperText={touched.address && errors.address}
                    margin="normal"
                  />
                )}

                {isRegister && (
                  <>
                    <TextField
                      fullWidth
                      name="location.lat"
                      label="קו רוחב (Latitude)"
                      value={values.location.lat}
                      onChange={handleChange}
                      error={touched.location?.lat && Boolean(errors.location?.lat)}
                      helperText={touched.location?.lat && errors.location?.lat}
                      margin="normal"
                    />
                    <TextField
                      fullWidth
                      name="location.lng"
                      label="קו אורך (Longitude)"
                      value={values.location.lng}
                      onChange={handleChange}
                      error={touched.location?.lng && Boolean(errors.location?.lng)}
                      helperText={touched.location?.lng && errors.location?.lng}
                      margin="normal"
                    />
                  </>
                )}

                {isRegister && values.role === 'technician' && (
                  <TextField
                    select
                    fullWidth
                    name="expertise"
                    label="תחומי התמחות"
                    SelectProps={{ multiple: true }}
                    value={values.expertise}
                    onChange={handleChange}
                    error={touched.expertise && Boolean(errors.expertise)}
                    helperText={touched.expertise && errors.expertise}
                    margin="normal"
                  >
                    {expertiseOptions.map(option => (
                      <MenuItem key={option} value={option}>
                        {option}
                      </MenuItem>
                    ))}
                  </TextField>
                )}
              </Box>

              <Button
                fullWidth
                type="submit"
                variant="contained"
                color="primary"
                disabled={isSubmitting}
                sx={{ mt: 2 }}
              >
                {isRegister ? 'Register' : 'Login'}
              </Button>
              <Button
                fullWidth
                variant="text"
                onClick={() => setIsRegister(prev => !prev)}
                sx={{ mt: 1 }}
              >
                {isRegister ? 'כבר רשום? התחבר' : 'עוד לא רשום? לחץ כאן להרשמה'}
              </Button>
            </Form>
          )}
        </Formik>
      </Paper>
    </Container>
  );
};

export default AuthPage;
