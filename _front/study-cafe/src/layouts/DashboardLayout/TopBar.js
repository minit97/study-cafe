import React, { useState } from 'react';
import {Link as RouterLink, useNavigate} from 'react-router-dom';
import clsx from 'clsx';
import PropTypes from 'prop-types';
import {
  AppBar,
  Badge,
  Box,
  Hidden,
  IconButton,
  Toolbar,
  makeStyles
} from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import NotificationsIcon from '@material-ui/icons/NotificationsOutlined';
import InputIcon from '@material-ui/icons/Input';
import Logo from 'src/components/Logo';
import axios from "axios";

const useStyles = makeStyles(() => ({
  root: {},
  avatar: {
    width: 60,
    height: 60
  }
}));

const TopBar = ({
  className,
  onMobileNavOpen,
  ...rest
}) => {
  const classes = useStyles();
  const navigate = useNavigate();
  const [notifications] = useState([]);

  const logout = async () => {
    try {
      const promise = await axios.post('/api/logout1', '', {
        headers: {
          'Content-Type': 'application/json',
        }
      });

      navigate('/login', { replace: true });
    }catch(error) {
      console.log(error);
    }
  }

  return (
    <AppBar
      className={clsx(classes.root, className)}
      elevation={0}
      {...rest}
    >
      <Toolbar>
        <RouterLink to="/">
          <Logo />
        </RouterLink>
        <Box flexGrow={1} />

        <IconButton color="inherit" onClick={logout}>
          <InputIcon />
        </IconButton>

        {/*<Hidden mdDown>*/}
        {/*  <IconButton color="inherit">*/}
        {/*    <Badge badgeContent={notifications.length} color="primary" variant="dot">*/}
        {/*      <NotificationsIcon />*/}
        {/*    </Badge>*/}
        {/*  </IconButton>*/}
        {/*</Hidden>*/}

        <Hidden lgUp>
          <IconButton color="inherit" onClick={onMobileNavOpen}>
            <MenuIcon />
          </IconButton>
        </Hidden>
      </Toolbar>
    </AppBar>
  );
};

TopBar.propTypes = {
  className: PropTypes.string,
  onMobileNavOpen: PropTypes.func
};

export default TopBar;
