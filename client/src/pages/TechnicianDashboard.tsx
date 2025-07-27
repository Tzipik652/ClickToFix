import React, { useEffect, useState } from 'react';
import {
  Container, Typography, Paper, List, ListItem, ListItemText, Button, CircularProgress,
  Box, TextField, MenuItem,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle
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
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedRequestId, setSelectedRequestId] = useState<number | null>(null);
  const [estimatedTime, setEstimatedTime] = useState<string>('');


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

  const handleConfirmEstimatedTime = async () => {
    if (!selectedRequestId) return;
    try {
      await axios.patch(`/api/requests/${selectedRequestId}`, {
        status: Status.ASSIGNED,
        technicianId: technicianId,
        estimatedArrival: estimatedTime,
      });

      setRequests(prev =>
        prev.map(req =>
          req.id === selectedRequestId
            ? { ...req, status: Status.ASSIGNED, estimatedArrival: estimatedTime }
            : req
        )
      );
      setOpenDialog(false);
      setSelectedRequestId(null);
    } catch (err) {
      alert('שגיאה בשמירת זמן הגעה');
    }
  };

  const openEstimatedTimeDialog = (requestId: number) => {
    setSelectedRequestId(requestId);
    setEstimatedTime('');
    setOpenDialog(true);
  };

  const filteredRequests = requests.filter((req) => {
    const matchesStatus = statusFilter === 'ALL' || req.status === statusFilter;

    if (!req.createdAt) return false; 

    const parsedDate = new Date(req.createdAt);
    if (isNaN(parsedDate.getTime())) return false; 

    const requestDate = parsedDate.toISOString().split('T')[0];

    const matchesDateFrom = !dateFrom || requestDate >= dateFrom;
    const matchesDateTo = !dateTo || requestDate <= dateTo;

    return matchesStatus && matchesDateFrom && matchesDateTo;
  });


  if (loading) return <CircularProgress />;
  if (error) return <Typography color="error">{error}</Typography>;

  const handleStatusComplate = (id: number) => {
    axios.patch(`/api/requests/${id}`, { status: Status.COMPLETED })
      .then(() => {
        setRequests(prev => prev.map(req => req.id === id ? { ...req, status: Status.COMPLETED } : req));
      })
      .catch(err => {
        alert('שגיאה בסימון הקריאה כטופלה');
      });
  }

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
                    secondary={`כתובת: ${req.address} | סטטוס: ${req.status} 
                    | תאריך: ${req.createdAt ? new Date(req.createdAt).toLocaleDateString() : "ללא תאריך"} | זמן הגעה: ${req.estimatedArrival ?? "לא הוזן"}
                    }`}
                />
                <Button
                  variant="contained"
                  size="small"
                  onClick={() => openEstimatedTimeDialog(req.id)}
                  sx={{ mr: 1 }}
                >
                  התחל טיפול
                </Button>
                <Button
                  variant="outlined"
                  size="small"
                  onClick={() => handleStatusComplate(req.id)}
                >
                  סמן כטופל
                </Button>
              </ListItem>
            ))}
          </List>
        )}
      </Paper>
      <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
        <DialogTitle>הזן זמן הגעה משוער</DialogTitle>
        <DialogContent>
          <TextField
            label="זמן הגעה"
            type="time"
            fullWidth
            value={estimatedTime}
            onChange={(e) => setEstimatedTime(e.target.value)}
            InputLabelProps={{ shrink: true }}
            inputProps={{ step: 300 }} // 5 דקות
            sx={{ mt: 1 }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenDialog(false)}>ביטול</Button>
          <Button variant="contained" onClick={handleConfirmEstimatedTime} disabled={!estimatedTime}>
            אישור
          </Button>
        </DialogActions>
      </Dialog>

    </Container>
  );
};

export default TechnicianDashboard;
