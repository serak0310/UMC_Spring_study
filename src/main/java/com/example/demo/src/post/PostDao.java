package com.example.demo.src.post;

import com.example.demo.src.board.model.GetBoardRes;
import com.example.demo.src.board.model.PostBoardReq;
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
}
