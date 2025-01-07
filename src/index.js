import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';  // App.js를 import
import reportWebVitals from './reportWebVitals';

// React 애플리케이션을 root div에 렌더링
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <App />  // App.js 컴포넌트를 렌더링
    </React.StrictMode>
);

// 성능 측정용
reportWebVitals();
