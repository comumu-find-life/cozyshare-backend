
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


### ⚒️ 사용 기술 및 아키텍처

----

**Backend** : `Spring Boot`, `JPA`, `QueryDSL`  
**Database** : `MySQL`, `MongoDB`, `Redis`  
**Frontend** : `Flutter`  
**Devops** : `Docker`, `GitAction`, `Aws Ec2`, `Aws RDS`, `Aws S3`, `Aws cloud Watch`



<details>
<summary><b>🗂️ ERD</b></summary>

<img src="https://github.com/user-attachments/assets/12642643-8ff4-40e3-8cac-014d4b1efc20" width="650" height="550"/>" width="650" height="550"/>

</details>
<details>

<summary><b>📝 API 명세서</b></summary>
<h4><a href="http://52.65.105.204:8080/swagger-ui/index.html#/" target="_blank" style="color: #007bff; text-decoration: none;">API 명세서(Swagger)</a></h4>

</details>

<details>
<summary><b>⚙️ 시스템 아키텍처</b></summary>

<br />

<img src="https://github.com/user-attachments/assets/bc681591-494a-415d-bf62-81fe9ee62515" width="1390" height="470"/>

**CI/CD 파이프 라인**
```
(1) Docker 이미지를 만들어 Docker Hub 에 push 한다.  
(2) master 브랜치에 병합하면 github/workflow/deploy.yml 파일이 실행된다.
(3) Docker Hub 에서 이미지를 pull 받아 총 3개의 서버(api, chatting, batch)를 EC2 서버에 배포한다. 
```



</details>

<details>
<summary><b>📂 모듈 구조</b></summary>

<br />

<img src="https://github.com/user-attachments/assets/5e2566d0-8b47-408f-9808-b05c1e69dfa3" width="1000" height="500"/>

**fl-api 모듈**

```
클라이언트와의 RESTful API 통신을 담당하는 모듈입니다. 애플리케이션의 주요 CRUD API를 관리하며, AWS EC2 환경에 배포됩니다.  
```
**fl-chatting 모듈**  
```
사용자의 채팅 관련 기능을 전담하는 독립적인 모듈입니다. fl-api 모듈과 분리되어 배포되며, 대화 내역 조회 및 메시지 읽음 상태를 확인하고 사용자에게 알림을 전송하는 역할을 수행합니다.   
```
**fl-batch 모듈**  
```
애플리케이션의 배치 작업을 담당하는 모듈입니다. 현재 임대인과 임차인 간의 계약 시간을 주기적으로 확인하여 FCM 푸시 알림을 전송하는 기능을 포함하고 있습니다.  
향후 유령 게시글 관리, 금융 트랜잭션 자동화(입출금 처리) 등의 기능이 추가될 예정입니다.
```
**fl-core 모듈**  
```
애플리케이션의 핵심 도메인을 관리하는 모듈로, 도메인 모델, 리포지토리, 서비스 계층을 포함합니다.  
루트 애그리게이트(Root Aggregate)를 기반으로 도메인이 분리되어 있으며, DTO와 Entity 간 변환 로직이 구현되어 있습니다.  
```
**fl-infra 모듈**
```
외부 라이브러리와 연동되는 기능 및 커스텀 예외 처리를 담당하는 모듈입니다. 라이브러리 변경 시 해당 모듈에서 구현체를 대체할 수 있도록 설계되었으며,  
시스템의 유지보수성과 확장성을 고려하여 관리됩니다. 
```
</details>




### 주요 업무 && 트러블 슈팅

----

<details>
<summary><b>WebSocket 을 이용한 실시간 채팅 기능 구현</b></summary>  



```
사용자의 채팅 참여 여부를 처리하기 위해 멀티스레드 환경에서도 안전하게 동작할 수 있는 자료구조가 필요하다고 판단했습니다.
이에 Map 의 동시성 구현체 중 SynchronizedMap 과 ConcurrentHashMap 을 비교 분석했으며, SynchronizedMap은 
전체에 락을 거는 방식으로 동시 처리 시 성능 저하가 발생할 수 있는 반면, ConcurrentHashMap 은 락 스트라이핑(lock striping) 기법을 통해 
더 높은 처리 성능을 제공한다는 점에서 적합하다고 판단했습니다.

이러한 이유로 ConcurrentHashMap 을 선택하여, 사용자 세션 정보를 메모리에서 안전하게 관리하고, 
이를 기반으로 FCM 푸시 전송 여부와 메시지의 읽음/읽지 않음 상태를 효율적으로 처리하는 기능을 구현했습니다
```


</details>  

<details>
<summary><b>Docker 와 Git Actions 을 이용한 CI/CD 파이프라인 구축</b></summary>


```
총 3개의 서버(API 서버, Batch 서버, 채팅 서버)를 배포해야 했습니다. 각각의 서버를 따로 배포하는 방식은 번거롭고 
수작업이 많아 실수가 발생할 가능성이 높았습니다. 이를 해결하기 위해, Docker Compose와 GitHub Actions를
활용해 세 서버를 한 번에 통합 배포할 수 있는 CI/CD 파이프라인을 구축했습니다.

CI/CD 자동화 도구로는 Jenkins와 같은 옵션도 고려할 수 있었지만, 해당 프로젝트는 개인 프로젝트로 규모가 크지 않고 
복잡한 빌드나 배포 시나리오가 존재하지 않았습니다. 따라서 별도의 서버 설치 및 유지보수가 필요 없는 GitHub Actions를 선택했습니다.

GitHub Actions는 GitHub와의 자연스러운 연동 덕분에 코드가 main 브랜치에 머지되는 순간 자동으로 배포가 실행되도록 
설정할 수 있으며, 설정이 간단하고 직관적입니다. 
또한 오픈소스 프로젝트에 한해 무료로 제공되는 runner를 활용할 수 있어 비용적인 측면에서도 효율적입니다.

Docker Compose를 활용한 통합 배포 역시 서버 간의 의존성과 실행 순서를 관리하기 용이해, 다중 서버 환경을 관리하는 데 매우 효과적이었습니다. 결과적으로, 코드 변경 → GitHub에 푸시 → 자동 빌드 및 배포까지 전 과정을 자동화함으로써 배포의 신뢰성과 생산성을 크게 향상시킬 수 있었습니다.
```

</details>

<details>
<summary><b>CloudWatch 를 이용한 로그 모니터링</b></summary>


```
이전 프로젝트에서는 따로 예외를 처리하는 클래스나 인터페이스를 만들지 않고 제공하는 IllegalArgumentException 나 
RuntimeException 등을 사용해 throw 하는 방식을 선택했지만 서비스 배포전, 기능 테스트를 하며 문제가 발생했을때 
클라이언트에서 서버의 문제를 명확하게 파악하기 어려운 불편함이 존재했습니다.

때문에 클라이언트한테 전송하는 에러 양식과 메시지를 커스텀한 뒤 문서화하면 장애에 더 빠르게 대응할 수 있음을 알게 되었습니다.
Runtime 을 상속박아 커스텀 예외를 각각 구현했고 AOP 를 활용해 커스텀 예외가 발생하면 이를 EC2 에 로그를 저장했지만 
이는 언젠가 메모리가 부족해질 수 있고 CPU 사용량과 관련있어 심각한 장애로 이어질 수 있음을 깨닫고 
CloudWatch 와 연동해 서버 로그를 따로 수집한 뒤 이메일과 연동해 실시간으로 장애에 대응할 수 있는 모니터링 환경을 구축했습니다.
```


</details>  

<details>
<summary><b>Redis 를 이용한 조회 기능 개선</b></summary>


```
모든 집 게시글을 조회하는 API 는 두개의 화면에서 공통적으로 사용되고 있습니다.또한 가장 많은 호출이 이뤄지는 
기능이기 때문에 이를 Redis 를 활용해 캐싱한다면 비약적인 성능 개선을 이끌어낼 수 있을거라 생각했습니다. 

Look Aside(읽기) Write-Through(쓰기) 전략을 사용해 캐싱처리를 했고, DB 데이터와 캐싱 데이터가 
달라질 수 있는 정합 문제를 고려해 설계했고 노출되는 중요 데이터 (가격, 위치) 가 아닌 이상 Redis 캐싱 데이터를 유지함으로써 
캐싱 데이터를 삭제하고 지우는 비용을 절감해 291ms → 152ms (100개 데이터 기준) 으로 성능을 성공적으로 개선했습니다.
```

</details>  

<details>
<summary><b>Redis Master-Replica 구조에서의 쓰기 권한 문제 해결</b></summary>



```
운영 환경에서 docker-compose를 활용해 Redis를 배포하던 중, Replica 노드에서 데이터를 삽입하려 할 때 권한 관련 오류가 발생했습니다.
이는 Redis의 Master-Replica 구조에서 Replica 노드는 쓰기 권한이 제한되어 있기 때문에 발생한 문제였습니다.

초기에는 해당 Replica 노드의 권한을 수동으로 임시 승격시켜 문제를 해결했지만, Redis 컨테이너가 재시작될 때마다 구성이 초기화되면서 
동일한 문제가 반복적으로 발생했습니다. 이 방식은 운영 환경에서 신뢰성과 유지보수성 측면에서 적합하지 않다고 판단했습니다.

이에 따라 Redis의 아키텍처를 1:2 구조의 Master-Replica 구성으로 재설계하였습니다. 모든 쓰기 요청은 Master 노드에서 처리하고, 
읽기 요청은 Replica 노드에서 분산 처리하도록 역할을 명확히 분리했습니다.
```

</details>  


<details>
<summary><b>비관적 락을 이용한 금융기능 동시성 제어</b></summary>



```
금융 데이터는 높은 보안성과 정합성이 요구되는 민감한 정보입니다. 멀티 스레드 환경에서 동시에 여러 거래가 발생할 경우, 
사용자 포인트 정보에 대한 데이터 정합성 문제가 발생할 수 있습니다. 
이러한 문제를 해결하기 위해, 비관적 락(Pessimistic Lock)을 도입하여 데이터의 조회 단계부터 차단함으로써 동시 접근을 방지하고,
보다 안전한 데이터 처리를 가능하게 했습니다. 

하지만 비관적 락은 잠재적으로 데드락(Deadlock) 문제를 유발할 수 있기 때문에, 트랜잭션 타임아웃을 5초로 설정하여
장시간 대기 상태를 방지하고 시스템의 안정성을 확보했습니다. 이러한 방식으로 동시성 제어와 데이터 무결성을 동시에 만족시키며, 
금융 서비스에 적합한 신뢰성 높은 기능을 구현했습니다.
```

</details>  

<details>
<summary><b>Junit5 와 test fixture 를 활용한 단일/통합테스트 작성</b></summary>

```
멀티 모듈 구조에서 테스트 코드를 작성할 때, 서로 다른 모듈 간에 중복된 given 코드를 반복해서 작성해야 하는 
불편함이 있었습니다. 이를 해결하기 위해, testFixtures 기능을 활용하여 Entity나 DTO 등 테스트에 필요한 
객체 생성 코드를 하위 공통 모듈에 정의함으로써, 각 모듈에서 공통된 given을 재사용할 수 있도록 구성했습니다. 

또한 통합 테스트의 범위에 대해 고민한 결과, 실제 API 요청과 최대한 유사한 환경을 구성하는 것이 통합 테스트의 
본래 목적에 부합한다고 판단했습니다. 따라서 테스트에서 외부 라이브러리나 데이터베이스를 과도하게 Mock 처리하기보다는, 
실제 운영 환경과 유사한 인프라를 구성하여 테스트를 수행했습니다. 
```

</details>  

<details>
<summary><b>루트 애그리거트 도메인 설계</b></summary>



```
객체 간의 독립성과 확장성을 확보하고, 테스트 코드 작성 시 높은 개발 생산성을 유지하기 위해 각 도메인의 연관관계를 제거하고 
루트 애그리거트(Root Aggregate) 중심으로 설계했습니다. 이를 통해 도메인 객체 간의 결합도를 낮추고, 각 객체가 책임을 
명확히 갖도록 구성할 수 있었습니다.

하지만 서로 다른 루트 애그리거트에 속한 도메인 객체 간의 데이터를 조회해야 할 경우에는 몇 가지 단점이 발생했습니다.
직접 JOIN 쿼리를 작성해야 하고, 두 개 이상의 객체를 동시에 조회할 때는 QueryDSL의 Tuple을 사용하여 데이터를 가져온 뒤, 
목적에 맞게 수작업으로 데이터를 가공해야 하는 번거로움이 있었습니다.특히 fetchJoin을 사용할 경우, Tuple 형태로 조회된 결과는 
distinct 쿼리가 제대로 동작하지 않아 중복 데이터를 제거하는 추가 로직을 직접 구현해야 하는 불편함도 존재했습니다.

MSA 나 규모가 큰 프로젝트일 경우 이처럼 객체간의 독립성을 확보하는 것이 좋았지만, 직접 수동으로 제어해야 하는
로직이 추가적으로 많이 존재해 각각의 도메인 설꼐의 장단점을 비교해 좀 더 신중하게 설계할 필요가 있을 것 같습니다.
```

</details>  

<details>
<summary><b>안전거래 동작 과정 </b></summary>

<br>
<img src="https://github.com/user-attachments/assets/47d42ac7-def0-4627-ac2d-3e2206f5dd3a" width="600" height="800"/>


</details>  

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






