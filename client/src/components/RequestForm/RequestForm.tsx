import React, { useState } from "react";
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  Alert,
  IconButton,
  Collapse,
} from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { Formik, Form } from "formik";
import * as Yup from "yup";
import axios from "axios";
import { useSelector } from "react-redux";
import {
  selectAddress,
  selectLocation,
  selectRole,
  selectUserId,
  selectUsername,
} from "../../redux/authSlice";

const RequestForm = () => {
  const role = useSelector(selectRole);
  const customerId = useSelector(selectUserId);
  const customerName = useSelector(selectUsername);
  const address = useSelector(selectAddress);
  const location = useSelector(selectLocation);

  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const initialValues = {
    description: "",
    address: address ?? "",
  };

  const validationSchema = Yup.object({
    description: Yup.string().required("יש להזין תיאור תקלה"),
    address: Yup.string().required("יש להזין כתובת"),
  });

  const handleSubmit = async (values: any, { resetForm }: any) => {
    setErrorMessage("");
    setSuccessMessage("");
    try {
      const payload = {
        ...values,
        customerId: customerId,
      };

      await axios.post("/api/requests", payload);
      setSuccessMessage("הבקשה נשלחה בהצלחה ותטופל בהקדם. עדכון על הבקשה יתקבל במייל עימו נרשמת למערכת");
      resetForm();
    } catch (error: any) {
      const message = error.response?.data || error.message || "אירעה שגיאה";
      setErrorMessage("שגיאה בשליחה: " + message);
    }
  };

  if (role !== "customer") {
    return (
      <Typography textAlign="center" mt={5} color="error">
        רק לקוחות יכולים לשלוח בקשות שירות.
      </Typography>
    );
  }

  if (!address || !location?.latitude || !location?.longitude) {
    return (
      <Box mt={5} textAlign="center">
        <Typography color="error" variant="h6">
          לא נמצאו פרטי כתובת או מיקום. יש להתחבר מחדש או לבדוק את חשבון המשתמש.
        </Typography>
        <Button
          variant="contained"
          sx={{ mt: 2 }}
          onClick={() => window.location.reload()}
        >
          טען מחדש
        </Button>
      </Box>
    );
  }

  return (
    <Container maxWidth="sm">
      <Paper elevation={3} sx={{ mt: 4, p: 3 }}>
        <Typography variant="h6" textAlign="center" gutterBottom>
          שלום {customerName} 😊 מלא את פרטי התקלה ובקליק היא תתוקן בעז"ה 👨‍💻
        </Typography>
        <Typography variant="h5" textAlign="center" gutterBottom>
          טופס קריאת שירות
        </Typography>

        <Collapse in={!!errorMessage}>
          <Alert
            severity="error"
            sx={{ mb: 2 }}
            action={
              <IconButton size="small" color="inherit" onClick={() => setErrorMessage("")}>
                <CloseIcon fontSize="small" />
              </IconButton>
            }
          >
            {errorMessage}
          </Alert>
        </Collapse>

        <Collapse in={!!successMessage}>
          <Alert
            severity="success"
            sx={{ mb: 2 }}
            action={
              <IconButton size="small" color="inherit" onClick={() => setSuccessMessage("")}>
                <CloseIcon fontSize="small" />
              </IconButton>
            }
          >
            {successMessage}
          </Alert>
        </Collapse>

        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
          enableReinitialize
        >
          {({ values, handleChange, errors, touched, isSubmitting }) => (
            <Form>
              <TextField
                fullWidth
                name="description"
                label="תיאור התקלה"
                value={values.description}
                onChange={handleChange}
                error={touched.description && Boolean(errors.description)}
                helperText={touched.description && errors.description}
                margin="normal"
              />
              <TextField
                fullWidth
                name="address"
                label="כתובת"
                value={values.address}
                onChange={handleChange}
                error={touched.address && Boolean(errors.address)}
                margin="normal"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                disabled={isSubmitting}
                sx={{ mt: 2 }}
              >
                שלח בקשה
              </Button>
            </Form>
          )}
        </Formik>
      </Paper>
    </Container>
  );
};

export default RequestForm;
