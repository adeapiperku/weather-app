import React, { useState } from "react";
import CssBaseline from "@material-ui/core/CssBaseline";
import Link from "@material-ui/core/Link";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import { Link as RouterLink, useNavigate } from "react-router-dom";
import { useMutation } from "react-query";
import { QueryKeys } from "../../service/QueryKeys";
import useUser from "../../hooks/useUser";
import { CustomerService } from "../../service/CustomerService";
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

const customerService = new CustomerService();

export default function ClientSignUp({ onSuccess, hideSignInLink, isLoading }) {
  const classes = useStyles();
  const navigate = useNavigate();
  const { user } = useUser();

  const [userAccount, setUserAccount] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    birthDate: new Date(),
    phoneNumber: "",
  });

  const {
    mutate: createUser,
    isLoading: signUpLoading,
    error,
  } = useMutation(
    QueryKeys.USER_BY_EMAIL(userAccount.email),
    (user) => customerService.create(user),
    {
      onSuccess: (data) => {
        !!onSuccess ? onSuccess(data) : navigate("/client/sign-in");
      },
    }
  );

  if (user) {
    navigate("/client/home", { replace: true });
  }

  const handleSubmit = () => {
    createUser({ ...userAccount });
  };

  return (
    <div className={classes.container}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className={classes.paper}>
          <Typography component="h1" variant="h5">
            Sign Up
          </Typography>
          <form className={classes.form} noValidate>
            <ValidTextField
              variant="standard"
              margin="normal"
              required
              fullWidth
              id="firstName"
              label="First Name"
              name="firstName"
              value={userAccount.firstName}
              onChange={(e) =>
                setUserAccount((prev) => ({
                  ...prev,
                  firstName: e.target.value,
                }))
              }
              autoFocus
              error={error?.firstName}
            />
            <ValidTextField
              variant="standard"
              margin="normal"
              required
              fullWidth
              id="lastName"
              label="Last Name"
              name="lastName"
              value={userAccount.lastName}
              onChange={(e) =>
                setUserAccount((prev) => ({
                  ...prev,
                  lastName: e.target.value,
                }))
              }
              error={error?.lastName}
            />
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
              error={error?.email}
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
              autoComplete="current-password"
              error={error?.password}
            />
            <div className={classes.linkContainer}>
              {!hideSignInLink && (
                <Link
                  component={RouterLink}
                  to="/client/sign-in"
                  variant="body2"
                  className={classes.link}
                >
                  Already have an account? Sign In
                </Link>
              )}
            </div>
            <LoadingButton
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
              onClick={handleSubmit}
              loading={isLoading || signUpLoading}
            >
              Sign Up
            </LoadingButton>
          </form>
        </div>
      </Container>
    </div>
  );
}
