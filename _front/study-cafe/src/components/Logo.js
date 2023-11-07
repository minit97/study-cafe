import React from 'react';
import {makeStyles} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  logoWrapper : {
    display: 'flex'
  },
  logoTitle : {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginLeft: '5px',
    color: 'black',
    fontWeight: "bold",
    fontSize: "20px"
  }
}))

const Logo = (props) => {
  const classes = useStyles();

  return (
    <div className={classes.logoWrapper}>
      <img alt="Logo" src="/static/logo.svg"{...props}/>
      <span className={classes.logoTitle}>HM Study Cafe</span>
    </div>
  );
};

export default Logo;
