package com.fuchengbang.cms.controller;

import com.fuchengbang.cms.entity.CrmBanner;
import com.fuchengbang.cms.service.CrmBannerService;
import com.fuchengbang.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther : coach
 * @create : 2020/12/4 0004 9:24
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerApiController {
    @Autowired
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        System.out.println("获取到了bannerList");
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }
}
