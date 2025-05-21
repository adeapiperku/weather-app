import React, { useState } from "react";
import Avatar from "@material-ui/core/Avatar";
import CssBaseline from "@material-ui/core/CssBaseline";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Link from "@material-ui/core/Link";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import { useMutation } from "react-query";
import { UserService } from "../../service/UserService";
import useUser from "../../hooks/useUser";
import MuiAlert from "@material-ui/lab/Alert";
import { Snackbar } from "@material-ui/core";
import ValidTextField from "../../common/ValidTextField";
import LoadingButton from "../../components/LoadingButton";
import { QueryKeys } from "../../service/QueryKeys";

const useStyles = makeStyles((theme) => ({
  container: {
    minHeight: "100vh",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    background: "#f7f9fc",
  },
  paper: {
    padding: theme.spacing(5),
    backgroundColor: "#ffffff",
    borderRadius: theme.spacing(2),
    boxShadow: "0 10px 30px rgba(0, 0, 0, 0.1)",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    width: "100%",
    maxWidth: 420,
  },
  form: {
    width: "100%",
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
    padding: theme.spacing(1.5),
    fontWeight: 600,
    borderRadius: theme.spacing(1),
    fontSize: "1rem",
    boxShadow: "0 3px 5px rgba(0, 0, 0, 0.1)",
  },
}));

const userService = new UserService();

export default function AdminSignIn({ onSuccess, hideSignUpLink, isLoading }) {
  const classes = useStyles();
  const { user, setUser } = useUser();
  const navigate = useNavigate();
  const [userAccount, setUserAccount] = useState({
    email: "",
    password: "",
  });
  const [errorMessage, setErrorMessage] = useState(false);

  const {
    mutate,
    isLoading: signInLoading,
    error,
  } = useMutation(
    QueryKeys.USER_BY_EMAIL(userAccount.email),
    (user) => userService.adminLogIn(user),
    {
      onSuccess: (data) => {
        if(data?.user?.type === 'Admin') {
          setUser(data);
          !!onSuccess ? onSuccess(data) : navigate("/admin/dashboard");
        } else {
          setErrorMessage(true);
        }
      },
    },
  );

  function handleLogin() {
    mutate(userAccount);
  }

  function handleKeyPress(event) {
    if (event.key === "Enter") {
      handleLogin();
    }
  }

  const handleSnackbarClose = () => {
    setErrorMessage(false);
  };

  if(user) {
    navigate('/admin/dashboard',  { replace: true })
  }

  return (
    <div className={classes.container}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={classes.paper}>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <ValidTextField
            variant="standard"
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            value={userAccount.email}
            onChange={(e) =>
              setUserAccount((prev) => ({ ...prev, email: e.target.value }))
            }
            autoComplete="email"
            autoFocus
            error={error}
          />
          <ValidTextField
            variant="standard"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            value={userAccount.password}
            onChange={(e) =>
              setUserAccount((prev) => ({ ...prev, password: e.target.value }))
            }
            onKeyDown={handleKeyPress}
            autoComplete="current-password"
            error={error}
          />
          <LoadingButton
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={handleLogin}
            loading={isLoading || signInLoading}
          >
            Sign In
          </LoadingButton>
        </div>
      </Container>
      <Snackbar open={errorMessage} autoHideDuration={6000} onClose={handleSnackbarClose}>
        <MuiAlert elevation={6} variant="filled" onClose={handleSnackbarClose} severity="error">
          Admin role is required to login!
        </MuiAlert>
      </Snackbar>
    </div>
  );
}
