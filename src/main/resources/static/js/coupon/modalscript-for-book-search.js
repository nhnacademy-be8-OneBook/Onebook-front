const btn = document.getElementById("popupBtn"); // 모달을 띄우는 버튼 요소 가져오기
const modal = document.getElementById("modalWrap"); // 모달 창 요소 가져오기
const closeBtn = document.getElementById("closeBtn"); // 모달을 닫는 버튼(X) 요소 가져오기

btn.onclick = function () {
    modal.style.display = "block"; // 버튼을 클릭하면 모달을 보이게 함
};

closeBtn.onclick = function () {
    modal.style.display = "none"; // 모달을 닫는 버튼(X)을 클릭하면 모달을 숨김
};

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none"; // 모달 외부를 클릭하면 모달을 숨김
    }
};

function inputBookId() {
    // 모달창의 input 값 가져오기
    const modalInputValue = document.getElementById('modalBookId').value;

    // 메인 폼의 input에 값 복사
    document.querySelector('input[name="bookIsbn13"]').value = modalInputValue;
    document.getElementById('bookIsbn13').value = modalInputValue;

    // 모달 닫기 (선택사항)
    document.getElementById('modalWrap').style.display = 'none';
}

    const rangeInput = document.getElementById('discountRange');
    const rangeValue = document.getElementById('rangeValue');

    // 초기값 설정
    rangeValue.textContent = rangeInput.value + '%';

    // range slider 값이 변경될 때마다 실행되는 이벤트 리스너
    rangeInput.addEventListener('input', function() {
    rangeValue.textContent = this.value + '%';
});
