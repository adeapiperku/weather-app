import React, { useState } from "react";
import CssBaseline from "@material-ui/core/CssBaseline";
import Link from "@material-ui/core/Link";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import { useMutation } from "react-query";
import { UserService } from "../../service/UserService";
import useUser from "../../hooks/useUser";
import { QueryKeys } from "../../service/QueryKeys";
import ValidTextField from "../../common/ValidTextField";
import LoadingButton from "../../components/LoadingButton";

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
    margin: theme.spacing(4, 0, 2),
    padding: theme.spacing(1.5),
    fontWeight: 600,
    borderRadius: theme.spacing(1),
    fontSize: "1rem",
    boxShadow: "0 3px 5px rgba(0,0,0,0.1)",
  },
  linkContainer: {
    marginTop: theme.spacing(3),
    marginBottom: theme.spacing(3),
    display: "flex",
    justifyContent: "center",
  },  
  link: {
    textDecoration: "none",
    fontWeight: 500,
    color: theme.palette.primary.main,
    "&:hover": {
      textDecoration: "underline",
    },
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

  const {
    mutate,
    isLoading: signInLoading,
    error,
  } = useMutation(
    QueryKeys.USER_BY_EMAIL(userAccount.email),
    (user) => userService.clientLogIn(user),
    {
      onSuccess: (data) => {
        setUser(data);
        !!onSuccess ? onSuccess(data) : navigate("/client/home");
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

  if(user) {
    navigate('/client/home',  { replace: true })
  }

  return (
    <div className={classes.container}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={classes.paper}>
          <Typography component="h1" variant="h5">
            Sign In
          </Typography>
          <form className={classes.form} noValidate>
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
                setUserAccount((prev) => ({
                  ...prev,
                  password: e.target.value,
                }))
              }
              onKeyDown={handleKeyPress}
              autoComplete="current-password"
              error={error}
            />

            <div className={classes.linkContainer}>
              {!hideSignUpLink && (
                <Link
                  component={RouterLink}
                  to="/client/sign-up"
                  variant="body2"
                  className={classes.link}
                >
                  Don't have an account? Sign Up
                </Link>
              )}
            </div>

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
          </form>
        </div>
      </Container>
    </div>
  );
}
