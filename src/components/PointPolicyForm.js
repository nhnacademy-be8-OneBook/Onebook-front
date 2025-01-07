import React, { useState, useEffect } from 'react';
import axios from 'axios';

const PointPolicyForm = () => {
    const [policyName, setPolicyName] = useState('');
    const [pointAmount, setPointAmount] = useState('');
    const [policyId, setPolicyId] = useState('');
    const [policyDetail, setPolicyDetail] = useState(null);

    // createPointPolicy 함수 (POST 요청)
    const createPointPolicy = async () => {
        const policyRequest = {
            policyName,
            pointAmount: parseInt(pointAmount, 10),
        };

        try {
            const response = await axios.post(
                'http://localhost:8500/task/member/admin/point-policies', // 백엔드 URL
                policyRequest // 요청 데이터
            );
            console.log('Point Policy created:', response.data);
        } catch (error) {
            console.error('Error creating Point Policy:', error);
        }
    };

    // getPointPolicy 함수 (GET 요청)
    const getPointPolicy = async () => {
        try {
            const response = await axios.get(
                `http://localhost:8500/task/member/admin/point-policies/${policyId}` // 백엔드 URL
            );
            setPolicyDetail(response.data); // 응답 데이터 상태에 저장
        } catch (error) {
            console.error('Error fetching Point Policy:', error);
        }
    };

    // 폼 제출 이벤트 핸들러
    const handleSubmit = (e) => {
        e.preventDefault();
        createPointPolicy(); // 정책 생성 요청
    };

    useEffect(() => {
        if (policyId) {
            getPointPolicy(); // 정책 ID가 있을 경우 정보 가져오기
        }
    }, [policyId]);

    return (
        <div>
            <h2>Create Point Policy</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Policy Name</label>
                    <input
                        type="text"
                        value={policyName}
                        onChange={(e) => setPolicyName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Point Amount</label>
                    <input
                        type="number"
                        value={pointAmount}
                        onChange={(e) => setPointAmount(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Create Policy</button>
            </form>

            <div>
                <h2>Get Point Policy</h2>
                <input
                    type="text"
                    value={policyId}
                    onChange={(e) => setPolicyId(e.target.value)}
                    placeholder="Enter Point Policy ID"
                />
                <button onClick={getPointPolicy}>Fetch Policy</button>
            </div>

            {policyDetail && (
                <div>
                    <h3>Policy Name: {policyDetail.policyName}</h3>
                    <p>Point Amount: {policyDetail.pointAmount}</p>
                    <p>Status: {policyDetail.policyStatus}</p>
                </div>
            )}
        </div>
    );
};

export default PointPolicyForm;