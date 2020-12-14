package com.fuchengbang.cms.service.impl;

import com.fuchengbang.cms.entity.CrmBanner;
import com.fuchengbang.cms.mapper.CrmBannerMapper;
import com.fuchengbang.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author coach
 * @since 2020-12-03
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> selectAllBanner() {
        //根据id进行降序排列，显示排列之后前两条记录
        return baseMapper.selectList(null);
    }
}
