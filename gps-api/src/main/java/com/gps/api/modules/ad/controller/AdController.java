package com.gps.api.modules.ad.controller;

import com.gps.db.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "广告回调")
@RestController
@RequestMapping("ad")
public class AdController {

    @GetMapping("adcb")//user_id={user_id}&trans_id={trans_id}&reward_amount={reward_amount}&reward_name={reward_name}&sign={sign}&unit_id={unit_id}
    public R adcb(@RequestParam String user_id,
                  @RequestParam String trans_id,
                  @RequestParam String reward_amount,
                  @RequestParam String reward_name,
                  @RequestParam String sign,
                  @RequestParam String unit_id) {
        return R.ok();
    }
}
