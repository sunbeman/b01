package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(String id);
    boolean existsByNickname(String nickname);
    Optional<User> findById(String id);
}

/*
(자바는 다중상속이 불가능하기 떄문에 인터페이스간 상속을 통해 직렬로 연결하여 다중상속의 기능을 구현)

'JpaRepository' 는 Spring Data JPA 에서 제공하는 인터페이스. CRUD 및 페이징, 정렬 등 데이터 엑세스 메서드 제공

ex)

1. 기본 CRUD 메서드
 (1) save : entity 저장 및 업데이트.
 (2) saveAll(Iterable<s> entities) : 여러 엔티티 저장, 업데이트.
 (3) findById(ID id) : 주어진 id의 entity 조회. Optional 로 반환.
    - Optional (클래스) : 값이 존재할 수도 있고 없을 수도 있는 container object.
        NullPointException 을 방지 및 값이 없는 경우 적절한 대체 로직 제공.
        ex) findById 메서드가 Optional<T> 반환 => 해당 id에 해당하는 entity 부존재 가능성 의미.
    - 구체적인 설명은 UserServiceImpl 에서 계속.

 (4) existsById(ID id) : 주어진 id의 entity 존재여부 확인.
 (5) findAll() : 모든 entity 조회.
 (6) findAllById(Iterable<ID> ids> : 주어진 id 목록에 해당하는 모든 entity 조회.
 (7) count() : 모든 entity 개수 반환.
 (8) deleteById(ID id) : 주어진 id의 entity 삭제.
 (9) delete(T entity) : 주어진 entity 삭제.
 (10) deleteAll(Iterable<? extends T> entities) : 주어진 entity collection 의 모든 entity 삭제.
 (11) deleteAll() : DB 에 존재하는 모든 entity 삭제.

2. 페이징 및 정렬 메서드
 (1) findAll(Sort sort) : 정렬 기준에 따라 모든 entity 조회.
 (2) findAll(Pageable pageable) : paging 정보를 기반으로 페이지 단위 entity 조회.
        = Finds all enities according to the given pageable object, retrieving result page by page.
        = Searches for entities page by page based on the provided paging information.

 */
