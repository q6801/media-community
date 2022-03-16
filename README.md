# Dremean
개발 공부를 도와주는 커뮤니티

### 기술 스택

<img align="left" src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=black"/>
<img align="left" src="https://img.shields.io/badge/spring boot-색상코드?style=flat-square&logo=spring boot&logoColor=black"/>
<img align="left" src="https://img.shields.io/badge/WebRTC-333333?style=flat-square&logo=WebRTC&logoColor=white"/>
<img align="left" src="https://img.shields.io/badge/aws-232F3E?style=flat-square&logo=Amazon aws&logoColor=white"/>
<img align="left" src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=black"/>
<img align="left" src="https://img.shields.io/badge/jenkins-D24939?style=flat-square&logo=jenkins&logoColor=black"/>
<img align="left" src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<img  src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/>


### Patch Notes

--- 
***version_1.0.0***

```Changes```
- 카테고리별 커뮤니티 작성 가능
- 채팅방에서 다수의 인원이 실시간 채팅 가능
- 20명까지는 동영상 streaming에 참여 가능
---

***version_1.0.1***

```Bug Fixes```
- xss 공격에 취약하던 점을 보완
- json을 토한 xss 공격을 customXssFilter에서 jsoup을 사용해서 방어

---
***version_1.1.0***

```Changes```
- boards에서 createdAt을 작성 후 얼마나 시간이 지났는지로 표시
- 게시물 혹은 reply에서 보여주는 시간을 yyyyMMdd로 변경 
- 글 삭제기능 추가

```Bug Fixes```
- 1.0.1에서 추가한 xss filter때문에 글 제목, 채팅 방 제목에 ‘<’같은 기호를 사용 못하던 문제 수정
- form과 multipart form을 사용한 Xss 공격을 Lucy Servlet Xss Filter로 막음

---

***version_1.2.0***

```Changes```
- 커뮤니티 목록에서 댓글 수 보이게 수정
- 게시글 작성할 때 익명 가능
  
```Bug Fixes```
- 게시물에서 ql-editor 태그 부제로 에디터의 css 적용 안되던 것 수정
- 회원탈퇴 안되던 것 수정 (delete cascade가 안 되어 있었음)