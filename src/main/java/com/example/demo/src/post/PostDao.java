package com.example.demo.src.post;

import com.example.demo.src.board.model.GetBoardRes;
import com.example.demo.src.board.model.PatchBoardReq;
import com.example.demo.src.board.model.PostBoardReq;
import com.example.demo.src.post.model.PatchPostReq;
import com.example.demo.src.post.model.PostPostReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 게시글 생성
    public int createPost(PostPostReq postPostReq) {
        String createPostQuery = "insert into Post (boardIdx, userIdx, title, content) VALUES (?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createPostParams = new Object[]{postPostReq.getBoardIdx(), postPostReq.getUserIdx(), postPostReq.getTitle(), postPostReq.getContent()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 postIdx번호를 반환한다.
    }

    // 게시글 이름, 내용 변경
    public int modifyPost(PatchPostReq patchPostReq) {
        String modifyPostQuery = "update Post set title= ?, content = ? where postIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyPostParams = new Object[]{patchPostReq.getTitle(), patchPostReq.getContent(), patchPostReq.getPostIdx()}; // 주입될 값들(nickname, userIdx) 순
        return this.jdbcTemplate.update(modifyPostQuery, modifyPostParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 게시글 삭제
    public int deletePost(int postIdx) {
        String deletePostQuery = "update Post set status = 'D' where postIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] deletePostParams = new Object[]{postIdx}; // 주입될 값들(nickname, userIdx) 순
        return this.jdbcTemplate.update(deletePostQuery, deletePostParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
