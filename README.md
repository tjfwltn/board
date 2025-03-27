## 기능 구현할 것

1. 게시글 조회했을 때 조회 수 +1
    - 동시성 문제 어떻게 해결할지
2. 추천 기능 구현
    - 동시성 문제 어떻게 해결할지
3. 게시글의 댓글 구현 (대댓글까지 가능하면)
    - 댓글의 정렬 기능 구현
    - 본문 보기 기능 구현 (대댓글까지 구현한다면 대댓글 순까지)
4. 검색 자동 완성 기능 구현? (trie 자료 구조)
5. User 테이블 만들고 연관관계 구축
   - 같은 id의 유저가 게시글을 여러 번 조회 시 카운트 제한할 것인지
   - 한 게시글을 1일 1회만 추천 가능하게 할 것
6. 게시글 페이징 기능 
   - 게시물 정렬 방법에 따라 20개씩 페이징
7. 게시글 정렬 기능
   - 최신순, 최다 조회순 등 정렬 기능 만들기
8. 일간 인기 게시글 조회
   - 조회 수와 추천 수를 기준으로 정렬하여 20개를 나오게 하기 
9. 게시글 & 댓글 신고 기능
   - 일정 횟수 이상 신고되면 자동 삭제 or 관리자 검토?
   