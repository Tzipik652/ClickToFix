import React, { FC, useEffect, useState } from "react";

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
} from "@mui/material";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { logout, selectRole } from "../redux/authSlice";

interface HomeProps {}

const Home: FC<HomeProps> = () => {
  const [mounted, setMounted] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();

  useEffect(() => {
    setMounted(true);
  }, []);
  const tabValue = (() => {
    if (location.pathname.startsWith("/products")) return 0;
    if (location.pathname.startsWith("/profile")) return 1;
    if (location.pathname.startsWith("/add-product")) return 2;
    return -1;
  })();

  const handleLogout = () => {
    // dispatch(clearCart());
    dispatch(logout());
    navigate("/login");
  };

  const handleTabChange = (_: any, newValue: number) => {
    if (newValue === 0) navigate("/products");
    if (newValue === 1) navigate("/profile");
    if (newValue === 2) navigate("/add-product");
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
                  src={"https://www.freepik.com/free-vector/toolbox-diy-house-repair_2870128.htm#fromView=keyword&page=1&position=14&uuid=de31d0f6-ff72-49e3-b1fa-91afbc8dbc5d&query=Tools"}
                  alt="Click To Fix Logo"
                  style={{ height: 50, borderRadius: "50%" }}
                />
              </IconButton>
            </Tooltip>
            {/* {username && (
              <Typography
                variant="subtitle1"
                sx={{ color: "#7a7a7a", fontWeight: "bold" }}
              >
                שלום, {username}
              </Typography>
            )} */}
          </Box>
          <Tabs
            value={tabValue}
            onChange={handleTabChange}
            textColor="secondary"
            indicatorColor="secondary"
            sx={{
              "& .MuiTab-root": {
                color: "#7a7a7a",
                fontWeight: "bold",
                minWidth: 90,
                "&.Mui-selected": { color: "#E56360" },
              },
              "& .MuiTabs-indicator": { backgroundColor: "#E56360" },
            }}
          >
            <Tab label="המתנות שלנו" />
            <Tab label="הפרטים שלי" />
            {/* {isAdmin && <Tab label="הוספת מוצר" />} */}
          </Tabs>
          <Tooltip title="עגלת קניות">
            <IconButton
              onClick={() => navigate("/cart")}
              sx={{ color: "#c1dbca" }}
            >
              {/* <Badge badgeContent={cartCount} color="error" max={99}>
                <ShoppingCartIcon />
              </Badge> */}
            </IconButton>
          </Tooltip>
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
        {location.pathname === "/" && (
          <>


          </>
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
          All rights reserved &copy; | Click To Fix
          | Developed by Tzipora Kroizer | tzipora652@gmail.com
        </Typography>
      </Box>
    </Box>
  );
};

export default Home;
