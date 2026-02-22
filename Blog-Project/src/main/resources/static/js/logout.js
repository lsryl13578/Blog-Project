// 현재 URL의 쿼리 파라미터를 가져옴 (?logout=true 같은 값)
const urlParams = new URLSearchParams(window.location.search);

// logout 파라미터 값이 있는지 확인
const fromLogout = urlParams.get('logout');

// 만약 logout 파라미터가 존재하면 (로그아웃 후 리다이렉트 된 경우)
// 브라우저에 저장된 access_token을 삭제하여 인증 정보 제거
if (fromLogout) {
	localStorage.removeItem("access_token");
}