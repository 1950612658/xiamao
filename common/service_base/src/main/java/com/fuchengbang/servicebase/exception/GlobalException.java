package com.fuchengbang.servicebase.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther : coach
 * @create : 2020/11/27 0027 9:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("消息")
    private String msg;
}
