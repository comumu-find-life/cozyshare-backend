import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 30, // 동시 가상 사용자 수
    iterations: 1000,
};

export default function () {
    let res = http.get('http://localhost:8080/v1/api/homes/overview');

    check(res, {
        '응답 코드가 200이다': (r) => r.status === 200,
        '응답 본문이 비어있지 않다': (r) => r.body.length > 0,
    });

    sleep(1); // 각 요청 사이에 1초 대기
}
