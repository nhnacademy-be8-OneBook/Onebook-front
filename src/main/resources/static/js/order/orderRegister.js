// 팝업창 열기 함수
function openAddressPopup() {
    window.open('/order/addresses', 'addressPopup', 'width=1400,height=800');
}

// 팝업창에서 선택된 주소 데이터를 업데이트
function setAddress(recipientName, recipientPhoneNumber, recipientRequestedTerm, recipientAddress) {
    console.log(recipientName, recipientPhoneNumber) //, recipientRoadNameAddress);
    document.getElementById("recipientInputAddress").value = recipientAddress;
    document.getElementById("recipientInputName").value = recipientName;
    document.getElementById("recipientInputPhoneNumber").value = recipientPhoneNumber;
    document.getElementById("recipientInputRequestedTerm").value = recipientRequestedTerm;

    // document.getElementById("recipientInputRoadNameAddress").value = recipientRoadNameAddress;
    // document.getElementById("recipientInputNotes").value = recipientNotes;
    // document.getElementById("recipientInputDetailAddress").value = recipientDetailAddress;
}

// 포장지 팝업창 열기
function openPackagingPopup() {
    window.open('/packagings', 'packagingPopup', 'width=800,height=500');
}

// 팝업창에서 선택된 포장지 데이터를 업데이트
function setPackaging(packagingId, packagingName, packagingPrice) {
    document.getElementById("packagingInputId").value = packagingId;
    document.getElementById("packagingInputName").value = packagingName;
    document.getElementById("packagingInputPrice").value = packagingPrice;
}

// 서버에 전송할 객체 만들어줌

// 선택한 날짜 정보를 저장할 객체
let selectedReservation = {};

function setReservationDate(element) {
    // 기존 선택 제거
    const allOptions = document.querySelectorAll("#reserveDatesArea a");
    allOptions.forEach((opt) => opt.classList.remove("selected"));

    // 현재 선택 항목 활성화
    element.classList.add("selected");

    // 선택한 데이터 추출
    selectedReservation = {
        orderNum: element.getAttribute("data-ordernum"),
        completedDate: element.getAttribute("data-expectdeliverycompletedate"),
        description: element.getAttribute("data-expectdeliverycompletedatedesc"),
        isReserve: element.getAttribute("data-isreserve"),
    };

    // 저장된 데이터 확인 (디버깅용)
    console.log("선택된 예약 정보:", selectedReservation);

    // 필요 시 추가 로직 (예: 서버 전송)
    // sendToServer(selectedReservation);
    // 선택된 값을 숨겨진 input 태그에 설정
    document.getElementById("deliveryCompletedDate").value = selectedReservation.completedDate;
}

document.addEventListener("DOMContentLoaded", function () {
    const updateTotalPrice = (bookId, price, quantity) => {
        const totalPrice = price * quantity;
        const totalElement = document.getElementById(`total-${bookId}`);
        totalElement.textContent = totalPrice.toLocaleString("en", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        });
    };

    const updateCartTotal = () => {
        let cartTotal = 0;
        document.querySelectorAll(".input-number").forEach(input => {
            const price = parseFloat(input.dataset.price);
            const quantity = parseInt(input.value, 10) || 1;
            cartTotal += price * quantity;
        });

        document.getElementById("cart-total").textContent = cartTotal.toLocaleString("en", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        });
        document.getElementById("final-total").textContent = cartTotal.toLocaleString("en", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2,
        });
    };

    document.querySelectorAll(".input-number").forEach(input => {
        input.addEventListener("change", (e) => {
            const bookId = e.target.dataset.bookId;
            const price = parseFloat(e.target.dataset.price);
            const quantity = parseInt(e.target.value, 10) || 1;

            if (quantity > 0) {
                updateTotalPrice(bookId, price, quantity);
                updateCartTotal();
            } else {
                e.target.value = 1; // 최소 수량은 1로 설정
                updateTotalPrice(bookId, price, 1);
                updateCartTotal();
            }
        });
    });

    document.querySelectorAll(".btn-number").forEach(button => {
        button.addEventListener("click", (e) => {
            const bookId = button.dataset.bookId;
            const input = document.querySelector(`.input-number[data-book-id="${bookId}"]`);
            const price = parseFloat(input.dataset.price);
            let quantity = parseInt(input.value, 10) || 1;

            if (button.dataset.type === "plus" && quantity < 100) {
                quantity++;
            } else if (button.dataset.type === "minus" && quantity > 1) {
                quantity--;
            }

            input.value = quantity;
            updateTotalPrice(bookId, price, quantity);
            updateCartTotal();
        });
    });

    // 초기 총 가격 계산
    updateCartTotal();
});