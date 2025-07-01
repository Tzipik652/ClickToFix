import { createSlice } from '@reduxjs/toolkit';

interface AuthState {
  role: 'customer' | 'technician' | null;
  isAuthenticated: boolean;
}

const initialState: AuthState = {
  role: null,
  isAuthenticated: false,
};

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setRole: (state, action) => {
      state.role = action.payload;
    },
    login: (state) => {
      state.isAuthenticated = true;
    },
    logout: (state) => {
      state.isAuthenticated = false;
      state.role = null;
    },
  },
});

export const { setRole, login, logout } = authSlice.actions;
export default authSlice.reducer;
