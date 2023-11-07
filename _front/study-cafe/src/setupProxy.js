const createProxyMiddleware = require('http-proxy-middleware');

// src/setupProxy.js
module.exports = (app) => {
    app.use(
        '/api',
        createProxyMiddleware({
          target: 'http://localhost:8080/devracoon/',
          changeOrigin: true,
          pathRewrite: { '^/api': '' }
        })
    );
};
