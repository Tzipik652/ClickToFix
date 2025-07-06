import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface AuthState {
  userId: string | null;
  username: string | null;
  email: string | null;
  role: 'customer' | 'technician' | null;
  address: string | null;
  location: {
    latitude: number | null;
    longitude: number | null;
  };
  isAuthenticated: boolean;
}

const initialState: AuthState = {
  userId: null,
  username: null,
  email: null,  
  role: null,
  address: null,
  location: {
    latitude: null,
    longitude: null,
  },
  isAuthenticated: false,
};

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state,
        action: PayloadAction<{
        userId: string;
        username: string;
        email: string;
        address: string;
        location: {
          latitude: number;
          longitude: number;
        };
        role: 'customer' | 'technician';
      }>
    ) => {
      state.userId = action.payload.userId;
      state.username = action.payload.username;
      state.email = action.payload.email;
      state.role = action.payload.role;
      state.address = action.payload.address;
      state.location = action.payload.location;
      state.isAuthenticated = true;
    },
    logout: (state) => {
      state.userId = null;
      state.username = null;
      state.email = null;
      state.address = null;
      state.location =  {
        latitude: null,
        longitude: null,
      };
      state.isAuthenticated = false;
      state.role = null;
    },
  },
});

export const { login, logout } = authSlice.actions;

export const selectUserId = (state: any) => state.auth.userId;
export const selectUsername = (state: any) => state.auth.username;
export const selectEmail = (state: any) => state.auth.email;
export const selectIsAuthenticated = (state: any) => state.auth.isAuthenticated;
export const selectAddress = (state: any) => state.auth.address;
export const selectLocation = (state: any) => state.auth.location;
export const selectRole = (state: any) => state.auth.role;

export default authSlice.reducer;
