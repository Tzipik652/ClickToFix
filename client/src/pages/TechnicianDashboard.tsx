import React, { useEffect, useState } from 'react';
import {
  Container, Typography, Paper, List, ListItem, ListItemText, Button, CircularProgress,
  Box, TextField, MenuItem
} from '@mui/material';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { selectUserId, selectUsername } from '../redux/authSlice';
import { Request, Status } from '../models/request';

const TechnicianDashboard = () => {
  const technicianId = useSelector(selectUserId);
  const technicianName = useSelector(selectUsername);
  const [requests, setRequests] = useState<Request[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [statusFilter, setStatusFilter] = useState<string>('ALL');
  const [dateFrom, setDateFrom] = useState<string>('');
  const [dateTo, setDateTo] = useState<string>('');

  useEffect(() => {
    const fetchRequests = async () => {
      try {
        const res = await axios.get(`/api/requests`);
        setRequests(res.data);
      } catch (err) {
        setError('שגיאה בטעינת הקריאות');
      } finally {
        setLoading(false);
      }
    };

    fetchRequests();
  }, [technicianId]);

  const handleStatusChange = async (requestId: number, newStatus: Status) => {
    try {
      await axios.patch(`/api/requests/${requestId}`, { status: newStatus });
      setRequests(prev =>
        prev.map(req => req.id === requestId ? { ...req, status: newStatus } : req)
      );
    } catch (err) {
      alert('שגיאה בעדכון הסטטוס');
    }
  };

 const filteredRequests = requests.filter((req) => {
  const matchesStatus = statusFilter === 'ALL' || req.status === statusFilter;

  if (!req.createdAt) return false; // מונע Invalid Date

  const parsedDate = new Date(req.createdAt);
  if (isNaN(parsedDate.getTime())) return false; // תאריך לא תקין

  const requestDate = parsedDate.toISOString().split('T')[0];

  const matchesDateFrom = !dateFrom || requestDate >= dateFrom;
  const matchesDateTo = !dateTo || requestDate <= dateTo;

  return matchesStatus && matchesDateFrom && matchesDateTo;
});


  if (loading) return <CircularProgress />;
  if (error) return <Typography color="error">{error}</Typography>;

  return (
    <Container maxWidth="md">
      <Typography variant="h5" mt={4} mb={2} align="center">
        שלום {technicianName} 📋
      </Typography>

      <Paper elevation={2} sx={{ p: 2, mb: 3 }}>
        <Typography variant="h6" mb={2}>סינון קריאות</Typography>
        <Box display="flex" gap={2} flexWrap="wrap">
          <TextField
            select
            label="סטטוס"
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            sx={{ minWidth: 150 }}
          >
            <MenuItem value="ALL">הכל</MenuItem>
            <MenuItem value="PENDING">פתוח</MenuItem>
            <MenuItem value="ASSIGNED">בטיפול</MenuItem>
            <MenuItem value="COMPLETED">טופל</MenuItem>
            <MenuItem value="CANCELLED">בוטלו</MenuItem>
          </TextField>

          <TextField
            type="date"
            label="מתאריך"
            value={dateFrom}
            onChange={(e) => setDateFrom(e.target.value)}
          />
          <TextField
            type="date"
            label="עד תאריך"
            value={dateTo}
            onChange={(e) => setDateTo(e.target.value)}
          />
        </Box>
      </Paper>

      <Paper elevation={3} sx={{ p: 2 }}>
        {filteredRequests.length === 0 ? (
          <Typography textAlign="center">אין קריאות מתאימות לסינון.</Typography>
        ) : (
          <List>
            {filteredRequests.map((req) => (
              <ListItem key={req.id} divider>
                <ListItemText
                    primary={`#${req.id} - ${req.description}`}
                    secondary={`כתובת: ${req.address} | סטטוס: ${req.status} | תאריך: ${
                    req.createdAt ? new Date(req.createdAt).toLocaleDateString() : "ללא תאריך"
                    }`}
                />
                <Button
                  variant="contained"
                  size="small"
                  onClick={() => handleStatusChange(req.id, Status.ASSIGNED)}
                  sx={{ mr: 1 }}
                >
                  התחל טיפול
                </Button>
                <Button
                  variant="outlined"
                  size="small"
                  onClick={() => handleStatusChange(req.id, Status.COMPLETED)}
                >
                  סמן כטופל
                </Button>
              </ListItem>
            ))}
          </List>
        )}
      </Paper>
    </Container>
  );
};

export default TechnicianDashboard;
