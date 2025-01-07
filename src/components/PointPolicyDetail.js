import React, { useState } from 'react';
import axios from 'axios';

const PointPolicyDetail = () => {
    const [pointPolicyId, setPointPolicyId] = useState('');
    const [policyDetail, setPolicyDetail] = useState(null);

    // 포인트 정책 정보 가져오기
    const fetchPolicyDetail = async () => {
        try {
            const response = await axios.get(
                `http://localhost:8500/task/member/admin/point-policies/${pointPolicyId}`
            );
            setPolicyDetail(response.data); // 응답 데이터 상태에 저장
        } catch (error) {
            console.error('Error fetching Point Policy:', error);
        }
    };

    return (
        <div>
            <h2>Point Policy Detail</h2>
            <input
                type="text"
                value={pointPolicyId}
                onChange={(e) => setPointPolicyId(e.target.value)}
                placeholder="Enter Point Policy ID"
            />
            <button onClick={fetchPolicyDetail}>Fetch Policy</button>

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

export default PointPolicyDetail;
