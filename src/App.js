import React from 'react';
import './App.css';
import PointPolicyForm from './PointPolicyForm';  // PointPolicyForm 컴포넌트 import
import PointPolicyDetail from './PointPolicyDetail';  // PointPolicyDetail 컴포넌트 import

function App() {
    return (
        <div className="App">
            <h1>Point Policy Management</h1>
            <PointPolicyForm />  // 정책을 생성하는 폼
            <PointPolicyDetail />  // 특정 정책을 조회하는 상세 화면
        </div>
    );
}

export default App;
