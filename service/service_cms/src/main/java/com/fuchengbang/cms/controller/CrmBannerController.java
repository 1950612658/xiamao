package com.fuchengbang.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fuchengbang.cms.entity.CrmBanner;
import com.fuchengbang.cms.service.CrmBannerService;
import com.fuchengbang.commonutils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author coach
 * @since 2020-12-03
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    private CrmBannerService service;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{page}/{limit}")
    public R index(@ApiParam(name = "page", value = "当前页码", required = true)
                   @PathVariable Long page,
                   @ApiParam(name = "limit", value = "每页记录数", required = true)
                   @PathVariable Long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        service.page(bannerPage, null);
        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = service.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("addBanner")
    public R save(@RequestBody CrmBanner banner) {
        service.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        service.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        service.removeById(id);
        return R.ok();
    }

}

