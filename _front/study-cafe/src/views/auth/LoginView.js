import React, { useState } from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import { Formik } from 'formik';
import axios from 'axios';
import {
  Box,
  Button,
  Container,
  Link,
  TextField,
  Typography,
  makeStyles,
  Backdrop,
  CircularProgress
} from '@material-ui/core';
import Page from 'src/components/Page';

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.dark,
    height: '100%',
    paddingBottom: theme.spacing(3),
    paddingTop: theme.spacing(3)
  },
  backdrop: {
    zIndex: theme.zIndex.drawer + 1,
    color: '#fff',
  },
}));

const LoginView = () => {
  const classes = useStyles();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  if (loading) {
    return (
      <Backdrop className={classes.backdrop} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
    );
  }

  const loginValidation = Yup.object().shape({
    email: Yup.string().max(255).required('Email is required'),
    password: Yup.string().max(255).required('Password is required')
  })

  const login = (values) => {
    setLoading(true);
    try {
      const data = {
        username: values.email,
        password: values.password
      };

      axios.post('/api/authenticate', JSON.stringify(data), {
        headers: {
          'Content-Type': 'application/json',
        }
      })
        .then((res) => {
          console.log('res.data.accessToken : ' + res.data.token);
          axios.defaults.headers.common['Authorization'] = 'Bearer ' + res.data.token;
          navigate('/', { replace: true });
        })
        .catch((ex) => {
          console.log('login requset fail : ' + ex);
          setLoading(false)
        })
        .finally(() => { console.log('login request end'); });
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <Page className={classes.root} title="Login">
      <Box display="flex" flexDirection="column" height="100%" justifyContent="center">
        <Container maxWidth="sm">
          <Formik
            initialValues={{email: '', password: ''}}
            validationSchema={loginValidation}
            onSubmit={login}
          >
            {({
              errors,
              handleBlur,
              handleChange,
              handleSubmit,
              isSubmitting,
              touched,
              values
            }) => (
              <form onSubmit={handleSubmit}>

                <Box mb={3}>
                  <Typography color="textPrimary" variant="h2">
                    Sign in
                  </Typography>
                  <Typography color="textSecondary" gutterBottom variant="body2">
                    Sign in on the internal platform
                  </Typography>
                </Box>

                <TextField
                  error={Boolean(touched.email && errors.email)}
                  fullWidth
                  helperText={touched.email && errors.email}
                  label="Email Address"
                  margin="normal"
                  name="email"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  type="email"
                  value={values && values.email}
                  variant="outlined"
                />

                <TextField
                  error={Boolean(touched.password && errors.password)}
                  fullWidth
                  helperText={touched.password && errors.password}
                  label="Password"
                  margin="normal"
                  name="password"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  type="password"
                  value={values && values.password}
                  variant="outlined"
                />

                <Box my={2}>
                  <Button color="primary" disabled={isSubmitting} fullWidth size="large" type="submit" variant="contained">
                    Sign in now
                  </Button>
                </Box>

                <Typography color="textSecondary" variant="body1">
                  Don&apos;t have an account?{' '}
                  <Link component={RouterLink} to="/register" variant="h6">
                    Sign up
                  </Link>
                </Typography>
              </form>
            )}

          </Formik>
        </Container>
      </Box>
    </Page>
  );
};

export default LoginView;
