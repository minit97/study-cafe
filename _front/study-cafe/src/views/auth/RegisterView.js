import React from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import { Formik } from 'formik';
import {
  Box,
  Button,
  Checkbox,
  Container,
  FormHelperText,
  Link,
  TextField,
  Typography,
  makeStyles
} from '@material-ui/core';
import Page from 'src/components/Page';
import {value} from "lodash/seq";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.dark,
    height: '100%',
    paddingBottom: theme.spacing(3),
    paddingTop: theme.spacing(3)
  }
}));

const RegisterView = () => {
  const classes = useStyles();
  const navigate = useNavigate();

  const signupValidation = Yup.object().shape({
    email: Yup.string().max(255).required('Email is required'),
    nickname: Yup.string().max(255).required('Nickname is required'),
    password: Yup.string().max(255).required('password is required'),
    policy: Yup.boolean().oneOf([true], 'This field must be checked')
  })

  const signup = async (values) => {
    const data = {
      username: values.email,
      password: values.password,
      nickname: values.nickname
    };

    try {
      const promise = await axios.post('/api/signup', JSON.stringify(data), {
        headers: {
          'Content-Type': 'application/json',
        }
      });

      alert("회원가입 성공!")
      navigate('/login', { replace: true });
    }catch(error) {
      alert(error.response.data.message);
    }
  }


  return (
    <Page className={classes.root} title="Register">
      <Box display="flex" flexDirection="column" height="100%" justifyContent="center">
        <Container maxWidth="sm">
          <Formik
            initialValues={{
              email: '',
              nickname: '',
              password: '',
              policy: false
            }}
            validationSchema={signupValidation}
            onSubmit={signup}
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
                    Create new account
                  </Typography>
                  <Typography color="textSecondary" gutterBottom variant="body2">
                    Use your email to create new account
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
                  value={values.email}
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
                  value={values.password}
                  variant="outlined"
                />
                <TextField
                  error={Boolean(touched.nickname && errors.nickname)}
                  fullWidth
                  helperText={touched.nickname && errors.nickname}
                  label="Nickname"
                  margin="normal"
                  name="nickname"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  value={values.nickname}
                  variant="outlined"
                />

                <Box alignItems="center" display="flex" ml={-1}>
                  <Checkbox checked={values.policy} name="policy" onChange={handleChange}/>
                  <Typography color="textSecondary" variant="body1">
                    I have read the{' '}
                    <Link color="primary" component={RouterLink} to="#" underline="always" variant="h6">
                      Terms and Conditions
                    </Link>
                  </Typography>
                </Box>

                {Boolean(touched.policy && errors.policy) && (
                  <FormHelperText error>
                    {errors.policy}
                  </FormHelperText>
                )}

                <Box my={2}>
                  <Button
                    color="primary"
                    disabled={isSubmitting}
                    fullWidth
                    size="large"
                    type="submit"
                    variant="contained"
                  >
                    Sign up now
                  </Button>
                </Box>
                <Typography color="textSecondary" variant="body1">
                  Have an account?{' '}
                  <Link component={RouterLink} to="/login" variant="h6">
                    Sign in
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

export default RegisterView;
