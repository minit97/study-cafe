import axios from 'axios';

const refreshToken = (callback) => {
    axios.post('/api/auth/refreshToken', {
        headers: {
          'Content-Type': 'application/json',
        }
    })
    .then((res) => {
        console.log('res.data.accessToken : ' + res.data);
        axios.defaults.headers.common['Authorization'] = 'Bearer ' + res.data;
        if (callback) {
            callback(true);
        }

        setTimeout(() => {
            refreshToken(null);
        }, (60 * 1000));
    })
    .catch((ex) => {
        console.log('app silent requset fail : ' + ex);
        if (callback) {
            callback(false);
        }
    })
    .finally(() => {
      console.log('refresh token request end');
    //   setLoading(true);
    });
};

export default refreshToken;
