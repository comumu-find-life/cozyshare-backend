
<div align="center">

<!-- logo -->
## 🦘 호주 쉐어하우스 보증금 사기 방지를 위한 서비스

</div> 


----

```
호주에서 집을 렌트할 때 발생하는 계약금 사기 사례가 점차 증가하고 있습니다. 이러한 문제는 
기존의 "FlatMate", "호주나라"와 같은 서비스들이 임대인과 임차인을 연결해주는 역할까지만 수행하고, 
계약금에 대한 안전장치는 제공하지 않기 때문에 발생하고 있습니다.

Cozy Share 프로젝트는 이 문제를 해결하기 위해, 계약금을 안전하게 중개·관리해주는 ‘안전 거래’ 기능을 도입하고자 합니다. 
이 기능을 통해 임대인과 임차인 간의 신뢰를 확보하고, 사기 피해를 방지할 수 있습니다.

궁극적으로 Cozy Share는 "안전한 집 렌트 문화 조성"을 목표로 하며, 호주 내에서 보다 투명하고 신뢰할 수 있는 주거 환경을
만들어 나가고 있습니다.
```


### ⚒️ 사용 기술

----

**Backend** : `Spring Boot`, `JPA`, `QueryDSL`  
**Database** : `MySQL`, `MongoDB`, `Redis`  
**Frontend** : `Flutter`  
**Devops** : `Docker`, `GitAction`, `Aws Ec2`, `Aws RDS`, `Aws S3`, `Aws cloud Watch`

### API 명세서

---

<h4><a href="http://52.65.105.204:8080/swagger-ui/index.html#/" target="_blank" style="color: #007bff; text-decoration: none;">API 명세서(Swagger)</a></h4>


### 시스템 아키텍처

---

<img src="https://github.com/user-attachments/assets/f618145b-9148-4f4d-b492-438e59fc5c9b" width="1390" height="470"/>

### ERD

---

<img src="https://github.com/user-attachments/assets/12642643-8ff4-40e3-8cac-014d4b1efc20" width="650" height="550"/>





### 성능개선 및 트러블 슈팅

----

**NO OFFSET을 이용한 페이징 조회 성능 개선**

```
문제 상황 : 데이터가 쌓이고 페이징 조회의 번호가 커질수록 버려지는 행(ROW) 가 많아져 성능 저하에 대한 문제점 발견

개선 방법 : 이를 해결하고자 조회 시작 부분을 인덱스로 찾아 매번 첫 페이지를 읽도록 하는 No Offset 방식을 도입해 
이전 페이지를 건너 뛰며 항상 동일한 페이징 성능을 보장하도록 설계했습니다. 

결과적으로 100,000 데이터 기준, 970ms -> 220ms 로 약 약 77.32% 성능을 개선에 성공했습니다.
```

**커버링 인덱스를 활용한 조회 성능 개선**

```
문제 상황 : NO OFFSET 에서 페이지1 -> 페이지5 로 넘어가는 사용자 UX에 대한 문제점 발견

개선 방법 : 조회대상 PK 조회 쿼리, 조회한 PK 를 이용한 상세 정보 조회 쿼리로 분리했습니다.
첫 번째 쿼리에서는 커버링 인덱스로 PK만 추출해 테이블 접근을 줄였고, 
두 번째 쿼리에서는 해당 PK를 기준으로 필요한 데이터를 조회해 성능을 개선했습니다.
```

**비관적락을 활용한 금융데이터 동시성 문제 해결**


### 개발 기록

----

<h4><a href="https://comumu.tistory.com/148" target="_blank" style="color: #007bff; text-decoration: none;">역직렬화 트러블 슈팅</a></h4>
<h4><a href="https://comumu.tistory.com/151" target="_blank" style="color: #007bff; text-decoration: none;">AWS EC2 CPU 트러블 슈팅</a></h4>
<h4><a href="https://comumu.tistory.com/158" target="_blank" style="color: #007bff; text-decoration: none;">Redis Master-Replica 구조에서의 권한 문제</a></h4>
<h4><a href="https://comumu.tistory.com/79" target="_blank" style="color: #007bff; text-decoration: none;">MapStruct 로 DTO <-> Entity 변환</a></h4>
<h4><a href="https://comumu.tistory.com/127" target="_blank" style="color: #007bff; text-decoration: none;">멀티모듈 테스트 코드 given 줄이기</a></h4>
<h4><a href="https://comumu.tistory.com/155" target="_blank" style="color: #007bff; text-decoration: none;">비관적 락을 이용한 금융기능 동시성 제어</a></h4>
<h4><a href="https://comumu.tistory.com/160" target="_blank" style="color: #007bff; text-decoration: none;">STOMP 웹소켓 채팅 기능 구현</a></h4>
<h4><a href="https://comumu.tistory.com/141" target="_blank" style="color: #007bff; text-decoration: none;">웹소켓 채팅 기능 읽음/읽지않음 처리</a></h4>
<h4><a href="https://comumu.tistory.com/142" target="_blank" style="color: #007bff; text-decoration: none;">ConcurrentHashMap 이란?</a></h4>
<h4><a href="https://comumu.tistory.com/149" target="_blank" style="color: #007bff; text-decoration: none;">Docker EC2 CI/CD 구축</a></h4>
<h4><a href="https://comumu.tistory.com/150" target="_blank" style="color: #007bff; text-decoration: none;">Docker Redis 배포</a></h4>
<h4><a href="https://comumu.tistory.com/153" target="_blank" style="color: #007bff; text-decoration: none;">Cloud Watch 로 로그 모니터링</a></h4>
<h4><a href="https://comumu.tistory.com/143" target="_blank" style="color: #007bff; text-decoration: none;">Redis 를 이용한 성능 개선</a></h4>
<h4><a href="https://comumu.tistory.com/152" target="_blank" style="color: #007bff; text-decoration: none;">커스텀 Resolver Exception 로 예외 관리하기
<h4><a href="https://comumu.tistory.com/77" target="_blank" style="color: #007bff; text-decoration: none;">OAuth2.0 로그인 기능 구현</a></h4>
<h4><a href="https://comumu.tistory.com/76" target="_blank" style="color: #007bff; text-decoration: none;">JWT 회원가입/로그인 기능 구현</a></h4>

### 서비스 화면

----


<details>
  <summary><b>📍 메인화면 - 전체 보기</b></summary>

|메인화면|찜 목록|지도 검색|채팅방|마이페이지|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/1b2dc5af-f4fd-47cd-8dbd-acac21fd71ea" width="200"/>|<img src="https://github.com/user-attachments/assets/ac0d39ac-b0c0-49f7-a125-5b1a8143ac9b" width="200"/>|<img src="https://github.com/user-attachments/assets/f643cf71-ecf1-4a2c-91fb-216a6bda2c8d" width="200"/>|<img src="https://github.com/user-attachments/assets/9348cc84-2562-46b4-b791-23eca3b9c3f9" width="200"/>|<img src="https://github.com/user-attachments/assets/599de358-24d9-43fc-886a-d47527c4e762" width="200"/>



**기능 설명**
```
메인화면 - 호주의 대도시를 리스트로 만들어 클릭시 해당 지역의 게시물을 조회한다.
찜 목록 - 찜 목록을 관리한다.
지도 검색 - Google Map 을 연동해 Cluster 기능을 구현했다. 줌인,아웃으로 집 조회가 가능하다.
채팅방 - 자신의 채팅 목록을 조회한다.
마이페이지 - 자신의 정보를 조회한다.

```

</details>


<details>
  <summary><b>📍 집 게시글 조회 - 전체 보기</b></summary>

|맵 검색|필터링|주소 검색|리스트 목록|
|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/5956f026-fa73-4dd2-a3e0-c11cc77ba4e8" width="200"/>|<img src="https://github.com/user-attachments/assets/1fb81c90-1810-4ca2-af96-4a14e9cc38d3" width="200"/>|<img src="https://github.com/user-attachments/assets/8d330cac-ec04-4165-ab6c-0d47b8e2fd61" width="200"/>|<img src="https://github.com/user-attachments/assets/f826885c-90b4-4cf7-9ede-bc57daa555bb" width="200"/>

|집 정보1|집 정보2|집 정보3|
|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/2dceb108-580a-4127-9e71-477c47ab6d83" width="200"/>|<img src="https://github.com/user-attachments/assets/616d291c-6c1e-4ecc-b2f3-b557a488bcd7" width="200"/>|<img src="https://github.com/user-attachments/assets/8a68555b-72fb-4a79-b54f-042cc42c273e" width="200"/>



**기능 설명**
```
맵 검색 - Google Map 을 연동해 Cluster 기능을 구현했다. 줌인,아웃으로 집 조회가 가능하다.
필터링 - 찾고자 하는 집 정보를 필터링한다.
주소 검색 - Google Api 와 연동해 도시(city) 와 주(state)를 조회한다.
리스트 목록 - 집 목록을 리스트 형식으로 조회한다.
집 정보1~3 - 집 정보를 조회한다.

```

</details>

<details>
  <summary><b>📍 집 게시글 등록 기능 - 전체 보기</b></summary>

|등록 시작|이미지 등록|주소 등록|주소 검증|가격 등록|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/100f996c-0680-4463-b97a-441787d8a94d" width="200"/>|<img src="https://github.com/user-attachments/assets/b187d7bb-f4b9-4469-887b-090a8f2b7ebc" width="200"/>|<img src="https://github.com/user-attachments/assets/c9f2eaa3-05fa-47b1-a31b-9d777c8ac923" width="200"/>|<img src="https://github.com/user-attachments/assets/319d9bbe-9991-49b4-bbe6-52a3c9344de6" width="200"/>|<img src="https://github.com/user-attachments/assets/1d21236a-d7d1-4b5e-ac92-5b92cedd97c2" width="200"/>|

|상세정보 등록1|상세정보 등록2|등록 완료|
|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/e81e36f9-8eab-4ece-a034-303281f2e0d2" width="200"/>|<img src="https://github.com/user-attachments/assets/1daaba46-a649-42e4-b584-1ca79b16f69d" width="200"/>|<img src="https://github.com/user-attachments/assets/bac0af26-cfb8-46f7-a2f3-9885be5f9a6a" width="200"/>|


**기능 설명**
```
등록 시작 - 집 게시물 등록을 시작한다.
이미지 등록 - 집 사진을 등록한다. (최소 한장, 최대 10장)
주소 등록 - 호주 주소 양식에 맞춰 주소를 등록한다.
주소 검증 - 입력한 주소가 맞는지 검증한다.
가격 등록 - 렌트비, 보증금, 공과금을 입력한다.
상세 정보 등록 1 - 집의 상세 정보를 등록한다. (ex. 집 형태, 성별, 침대, 화장실, 인원)
상세 정보 등록 2 - 집의 상세 정보를 등록한다. (ex. 주차 여부, 옵션)
등록 완료 - 등록 완료를 알린다.
```

</details>



<details>
  <summary><b>📍 채팅 && 안전 거래 화면 - 전체 보기</b></summary>

|임대인 채팅1|임대인 채팅2|거래 생성1|거래 생성2|거래 생성3|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/c735bd21-8bb4-4a78-b65f-eee66ccb1d20" width="200"/>|<img src="https://github.com/user-attachments/assets/ba1c8683-2e7d-4346-b7a8-4f635b9a025a" width="200"/>|<img src="https://github.com/user-attachments/assets/2cae77eb-9bd1-4904-804f-3723e1c2c0aa" width="200"/>|<img src="https://github.com/user-attachments/assets/20ede429-f967-4836-85c3-6244bbf9e86d" width="200"/>|<img src="https://github.com/user-attachments/assets/807c5deb-8231-4708-98cd-a3f2bcd14740" width="200"/>|

**기능 설명**
```
[안전 거래 생성]
  임대인 채팅1 - 자신에게 온 채팅을 조회한다.
  임대인 채팅2 - 상단에 있는 "Deal" 버튼을 눌러 안전거래를 생성한다.
  거래 생성1 - 계약금(Deposit) 과 거래 날짜,시간을 지정한다.
  거래 생성2 - 거래 정보를 확인 후 생성한다.
  거래 생성3 - 안전거래 생성이 완료되면 채팅 화면 위젯으로 나타난다.
```

|거래수락1|거래수락2|거래수락3|거래수락4|
|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/422d245d-e375-40f3-8980-037783ca6bd6" width="200"/>|<img src="https://github.com/user-attachments/assets/fffd1f75-4a93-45cc-b072-bb765f40bc7c" width="200"/>|<img src="https://github.com/user-attachments/assets/ae2be066-964b-4f21-bfa8-48666705a8c3" width="200"/>|<img src="https://github.com/user-attachments/assets/a676ba01-b1c0-4e2b-bcf2-ca74059a85e1" width="200"/>

```
[거래 수락]
  거래 수락1 - 임차인은 생성된 안전 거래를 조회한다.
  거래 수락2 - 임차인은 거래 정보를 확인 한다.
  거래 수락3 - 임차인은 거래 정보를 수락한다.(임차인 포인트 차감)
  거래 수락4 - 임차인이 거래를 수락하면 채팅 화면 위젯으로 나타난다.
```

|거래 완료1|거래 완료2|거래 완료3|거래 목록|거래 정보|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/2d84b0a6-c1ef-4776-a3f3-567c6ebb6877" width="200"/>|<img src="https://github.com/user-attachments/assets/abcb4fb0-7140-44b6-8c16-1fefa323df50" width="200"/>|<img src="https://github.com/user-attachments/assets/783fedc3-31fe-42dd-be06-e878334aadde" width="200"/>|<img src="https://github.com/user-attachments/assets/839989d5-7750-4391-8d63-f2080c170344" width="200"/>|<img src="https://github.com/user-attachments/assets/f3efbdec-71dd-45fe-98ce-91358462cf66" width="200"/>|


```
[거래 완료]
  거래 완료1 - 임차인과 임대인이 실제로 만나 거래가 성사되면, 임차인은 거래 완료 버튼을 누른다.
  거래 완료2 - 임차인은 거래 완료 버튼을 누른다.(임대인 포인트 증가)
  거래 완료3 - 거래가 완료되면 채팅 위젯으로 나타난다.
  거래 목록1 - 완료된 거래 내역은 마이페이지에서 조회가 가능하다.
  거래 정보 - 거래 내역을 조회한다.
```
</details>


<details>
  <summary><b>📍 안전거래 가이드 - 전체 보기</b></summary>

|안내 화면1|안내 화면2|안내 화면3|안내 화면1|
|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/b46c33c7-9a77-43e6-9282-13a062b469d8" width="200"/>|<img src="https://github.com/user-attachments/assets/b92a0f22-680c-4e5c-a428-1bb4d6df9b46" width="200"/>|<img src="https://github.com/user-attachments/assets/0c091b3e-8e40-4df4-80b7-cf0a6f550f31" width="200"/>|<img src="https://github.com/user-attachments/assets/10704f16-2311-4a58-bbf4-b3fc90d6649b" width="200"/>



</details>

<details>
  <summary><b>📍 포인트 관련 기능 - 전체 보기</b></summary>

|포인트 내역|Paypal 정보 수정|포인트 충전|Paypal 정보 등록|Paypal WebView|출금|
|:---:|:---:|:---:|:---:|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/25f260cd-6200-4641-bc3b-ca557cf85610" width="200"/>|<img src="https://github.com/user-attachments/assets/e57ad6bc-7f78-4efe-8265-ab01b4d58295" width="200"/>|<img src="https://github.com/user-attachments/assets/9327ba01-7509-48a9-92c3-d0f250e1b1ae" width="200"/>|<img src="https://github.com/user-attachments/assets/4cb2262a-b72e-4b0d-89db-bb23d808c265" width="200"/>|<img src="https://github.com/user-attachments/assets/6033715f-af3e-4857-89e7-28ad22a7a30c" width="200"/>|<img src="https://github.com/user-attachments/assets/d28b2b89-ba0b-4dd3-9407-a0096024f731" width="200"/>

```
[포인트]
  포인트 내역 - 포인트 입,출금 내역을 조회한다.
  Paypal 정보수정 - 자신의 Paypal 정보를 수정한다.
  포인트 충전 - 포인트를 충전한다.
  Paypal 정보 등록 - 자신의 Paypal 정보를 등록한다.
  Paypal WebView - 포인트 충전은 Paypal 을 통해 진행된다
  출금 - 자신이 보유한 포인트를 출금한다.
```

</details>


<br />






