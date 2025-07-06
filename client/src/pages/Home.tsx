import React, { FC, useEffect, useState } from "react";
import logo from "../assets/Energetic Logo with Digital Button Interaction.png";

import LogoutIcon from "@mui/icons-material/Logout";
import {
  AppBar,
  Toolbar,
  Typography,
  Tab,
  Tabs,
  IconButton,
  Tooltip,
  Badge,
  Fade,
  Box,
  Container,
  Paper,
  Button,
} from "@mui/material";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout, selectRole } from "../redux/authSlice";

interface HomeProps {}

const Home: FC<HomeProps> = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const role = useSelector(selectRole);

  const handleLogout = () => {
    dispatch(logout());
    navigate("/login");
  };

  return (
    <Box
      dir="rtl"
      sx={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}
    >
      <AppBar
        position="fixed"
        sx={{ backgroundColor: "#fff", boxShadow: "none" }}
      >
        <Toolbar
          sx={{
            justifyContent: "space-between",
            alignItems: "center",
            padding: "8px 16px",
          }}
        >
          <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
            <Tooltip title="Click To Fix">
              <IconButton
                onClick={() => navigate("/")}
                sx={{ color: "#c1dbca" }}
              >
                <img
                  src={logo}
                  alt="Click To Fix Logo"
                  style={{ height: 50, borderRadius: "50%" }}
                />
              </IconButton>
            </Tooltip>
          </Box>
          <Tooltip title="התנתקות">
            <IconButton onClick={handleLogout} sx={{ color: "#c1dbca" }}>
              <LogoutIcon />
            </IconButton>
          </Tooltip>
        </Toolbar>
      </AppBar>

      <Container
        sx={{
          flexGrow: 1,
          marginTop: "6rem",
          paddingLeft: "2rem",
          position: "relative",
          height: location.pathname === "/" ? "calc(100vh - 6rem)" : "auto",
        }}
        maxWidth={false}
        disableGutters
      >
        {role === "technician" ? (
          <Button onClick={()=>navigate("/technician-dashboard")}>הצג קריאות פתוחות</Button>
        ) : (
          <Button onClick={()=>navigate("/request-from")}>הצג טופס יצירת קריאה</Button>
        )}
        <Outlet />
      </Container>

      <Box
        sx={{
          flexShrink: 0,
          textAlign: "center",
          padding: 2,
          background: "#f5f5f5",
          borderTop: "1px solid #ddd",
        }}
      >
        <Typography variant="body2" color="textSecondary">
          All rights reserved &copy; | Click To Fix | Developed by Tzipora
          Kroizer | tzipora652@gmail.com
        </Typography>
      </Box>
    </Box>
  );
};

export default Home;
