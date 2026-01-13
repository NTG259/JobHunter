package com.ntg.JobHunter.utils.response;

import com.ntg.JobHunter.domain.User;
import com.ntg.JobHunter.domain.res.CreatedUserResponse;
import com.ntg.JobHunter.domain.res.MetaResponse;
import com.ntg.JobHunter.domain.res.ResultPaginationResponse;
import com.ntg.JobHunter.utils.UserConvert;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class FormatResultPagination {
    public static ResultPaginationResponse createPaginationResponse(Page page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setPage(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);
        rs.setResult(page.getContent());

        return rs;
    }

    public static ResultPaginationResponse createPaginateUserRes(Page<User> page) {
        ResultPaginationResponse rs = new ResultPaginationResponse();
        MetaResponse mr = new MetaResponse();

        mr.setPage(page.getNumber() + 1);
        mr.setPageSize(page.getSize());
        mr.setPages(page.getTotalPages());
        mr.setTotal(page.getTotalElements());

        rs.setMeta(mr);

        List<CreatedUserResponse> listUser = page.getContent()
                .stream().map(UserConvert::convertToResCreatedUserRes)
                .collect(Collectors.toList());

        rs.setResult(listUser);

        return rs;
    }

}