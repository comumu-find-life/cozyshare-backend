import http from 'k6/http';
import { check } from 'k6';
import { Trend } from 'k6/metrics';

export let customResponseTime = new Trend('custom_http_req_duration');

export let options = {
  vus: 10,
  duration: '10s',
  ext: {
    loadimpact: {
      distribution: {
        "amazon:us": { loadZone: "amazon:us-east-1", percent: 50 },
        "amazon:eu": { loadZone: "amazon:eu-west-1", percent: 50 }
      }
    },
    prometheus: {
      // Prometheus exporter 포트는 6565로 설정됩니다.
      port: '6565'
    }
  }
};

export default function () {
  let res = http.get('http://localhost:8080/v1/api/homes/overview');
  customResponseTime.add(res.timings.duration);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}
