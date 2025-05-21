import React, { useState } from 'react';
import {
  CircularProgress,
  Paper,
  Typography,
  Grid,
  IconButton,
  TextField,
  Button,
  Snackbar,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@material-ui/core';
import MuiAlert from '@material-ui/lab/Alert';
import { makeStyles } from '@material-ui/core/styles';
import EditIcon from '@material-ui/icons/Edit';
import useUser from '../../hooks/useUser';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';
import MuiAppBar from '../../components/MuiAppBar';
import { useMutation, useQuery } from 'react-query';
import { CustomerService } from '../../service/CustomerService';
import { FavoritesService } from '../../service/FavoritesService';

const useStyles = makeStyles((theme) => ({
  profileContainer: {
    minHeight: '100vh',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: theme.palette.background.default,
    padding: theme.spacing(5),
  },
  card: {
    borderRadius: '1rem',
    width: '100%',
    maxWidth: '800px',
    padding: theme.spacing(3),
    backgroundColor: theme.palette.background.paper,
    color: theme.palette.text.primary,
  },
  avatarSection: {
    backgroundColor: theme.palette.primary.main,
    color: theme.palette.common.white,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    borderTopLeftRadius: '1rem',
    borderBottomLeftRadius: '1rem',
    padding: theme.spacing(2),
  },
  userImage: {
    width: theme.spacing(12),
    height: theme.spacing(12),
    marginBottom: theme.spacing(2),
  },
  userDetails: {
    padding: theme.spacing(2),
  },
  userInfo: {
    marginBottom: theme.spacing(2),
  },
  editIconContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing(2),
  },
  editIcon: {
    marginLeft: theme.spacing(1),
  },
}));

const customerService = new CustomerService();
const favortiesService = new FavoritesService();

const ClientProfile = () => {
  const classes = useStyles();
  const { user, setUser } = useUser();
  const [isEditing, setIsEditing] = useState(false);
  const [editedSuccessfully, setEditedSuccessfully] = useState(false);
  const [editFailed, setEditFailed] = useState(false);
  const [showFavorites, setShowFavorites] = useState(false);

  const { mutateAsync: updateCustomer } = useMutation(
    (cust) => customerService.update(cust),
    {
      onSuccess: (data) => console.log('updated here', data),
      onError: (e) => console.log(e),
    }
  );

  const { data: favoriteCities, isLoading: loadingFavorites } = useQuery(
    ['favoriteCities', user?.user?.id],
    () => favortiesService.getFavoriteCities(user.user.id),
    {
      enabled: showFavorites && !!user?.user?.id, 
    }
  );

  const handleEditClick = () => {
    setIsEditing(!isEditing);
  };

  const handleSave = async (event) => {
    event.preventDefault();
    const updatedUser = {
      ...user.user,
      firstName: event.target.firstName?.value,
      lastName: event.target.lastName?.value,
      email: event.target.email?.value,
    };
    try {
      await updateCustomer(updatedUser);
      const userLocalStorage = {
        accessToken: user?.accessToken,
        refreshToken: user?.refreshToken,
        user: { ...updatedUser },
      };
      setUser(userLocalStorage);
      setIsEditing(false);
      setEditedSuccessfully(true);
    } catch (error) {
      setEditFailed(true);
    }
  };

  const handleSnackbarClose = () => {
    setEditedSuccessfully(false);
    setEditFailed(false);
  };

  const handleOpenFavoritesDialog = () => {
    setShowFavorites(true);
  };

  const handleCloseFavoritesDialog = () => {
    setShowFavorites(false);
  };

  if (!user) {
    return (
      <div className={classes.profileContainer}>
        <CircularProgress />
      </div>
    );
  }

  const renderUserInfo = () => (
    <Grid container spacing={2} className={classes.userInfo}>
      <Grid item xs={6}>
        <Typography variant="subtitle2">Email</Typography>
        <Typography variant="body2">{user?.user.email}</Typography>
      </Grid>
      <Button
        variant="outlined"
        color="primary"
        onClick={handleOpenFavoritesDialog}
        style={{ marginBottom: '16px' }}
      >
        View Favorite Cities
      </Button>
    </Grid>
  );

  const renderEditForm = () => (
    <form onSubmit={handleSave}>
      <TextField
        fullWidth
        name="firstName"
        label="First Name"
        defaultValue={user?.user.firstName}
        variant="outlined"
        margin="dense"
      />
      <TextField
        fullWidth
        name="lastName"
        label="Last Name"
        defaultValue={user?.user.lastName}
        variant="outlined"
        margin="dense"
      />
      <TextField
        fullWidth
        name="email"
        label="Email"
        defaultValue={user?.user.email}
        variant="outlined"
        margin="dense"
      />
      <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: '16px' }}>
        <Button type="submit" variant="contained" color="primary" style={{ marginRight: '8px' }}>
          Save
        </Button>
        <Button type="button" variant="contained" onClick={handleEditClick}>
          Cancel
        </Button>
      </div>
    </form>
  );

  return (
    <>
      <MuiAppBar />
      <MuiPickersUtilsProvider utils={DateFnsUtils}>
        <section className={classes.profileContainer}>
          <Paper elevation={3} className={classes.card}>
            <Grid container>
              <Grid item xs={12} md={4} className={classes.avatarSection}>
                <Typography variant="h6">
                  {user.user.firstName} {user.user.lastName}
                </Typography>
              </Grid>
              <Grid item xs={12} md={8} className={classes.userDetails}>
                <div className={classes.editIconContainer}>
                  <Typography variant="h5">
                    <b>User Profile</b>
                  </Typography>
                  <IconButton className={classes.editIcon} onClick={handleEditClick}>
                    <EditIcon />
                  </IconButton>
                </div>
                <hr />
                {isEditing ? renderEditForm() : renderUserInfo()}
              </Grid>
            </Grid>
          </Paper>

          <Snackbar open={editedSuccessfully} autoHideDuration={6000} onClose={handleSnackbarClose}>
            <MuiAlert elevation={6} variant="filled" onClose={handleSnackbarClose} severity="success">
              Data updated successfully!
            </MuiAlert>
          </Snackbar>

          <Snackbar open={editFailed} autoHideDuration={6000} onClose={handleSnackbarClose}>
            <MuiAlert elevation={6} variant="filled" onClose={handleSnackbarClose} severity="error">
              Failed to update data. Please try again.
            </MuiAlert>
          </Snackbar>

          <Dialog open={showFavorites} onClose={handleCloseFavoritesDialog}>
            <DialogTitle>Favorite Cities</DialogTitle>
            <DialogContent dividers>
              {loadingFavorites ? (
                <CircularProgress />
              ) : (favoriteCities || []).length > 0 ? (
                <ul>
                  {favoriteCities.map((city) => (
                    <li key={city.id}>
                      <Typography variant="body2">{city.cityName}</Typography>
                    </li>
                  ))}
                </ul>
              ) : (
                <Typography variant="body2">No favorite cities added.</Typography>
              )}
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseFavoritesDialog} color="primary">
                Close
              </Button>
            </DialogActions>
          </Dialog>
        </section>
      </MuiPickersUtilsProvider>
    </>
  );
};

export default ClientProfile;
