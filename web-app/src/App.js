import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "/node_modules/bootstrap/dist/js/bootstrap.min.js";
import "./App.css";
import { useContext, useState } from "react";
import { createTheme, CssBaseline, ThemeProvider } from "@material-ui/core";
import { queryClient, setQueryDefaults } from "./service/QueryClient";
import { QueryClientProvider } from "react-query";
import AppRoutes from "./routes/Routes";
import DateFnsUtils from '@date-io/date-fns';
import { Routes } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import { MuiPickersUtilsProvider } from "@material-ui/pickers";

const customTheme = {
  palette: {
    primary: {
      main: "#1976d2",
      mainGradient: "linear-gradient(90deg, #1976d2 0%, #42a5f5 100%)",
    },
    secondary: {
      main: "#90caf9",
    },
    text: {
      dark: "#0d47a1",
    },
    mode: "light",
  },
  overrides: {
    MuiTableRow: {
      head: {
        background:
          "linear-gradient(90deg, #1976d2 0%, #42a5f5 100%)",
        color: "white",
      },
    },
    MuiTableSortLabel: {
      root: {
        color: "#02101e",
        fontSize: "1.2em",
        "&:hover": {
          color: "#0d47a1 !important",
        },
        "&.MuiTableSortLabel-active": {
          color: "#0d47a1",
        },
        "& *": {
          color: "#1565c0 !important",
        },
      },
    },
  },
  toolbarHeight: 50,
};

setQueryDefaults();

function App() {
  const [theme, setTheme] = useState(customTheme);

  return (
    <AuthProvider>
        <QueryClientProvider client={queryClient}>
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <ThemeProvider theme={createTheme(theme)}>
            <CssBaseline />
            <Routes>{AppRoutes}</Routes>
          </ThemeProvider>
          </MuiPickersUtilsProvider>
        </QueryClientProvider>
    </AuthProvider>
  );
}

export default App;