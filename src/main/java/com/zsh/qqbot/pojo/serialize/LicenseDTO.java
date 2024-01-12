package com.zsh.qqbot.pojo.serialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * license信息
 *
 * @author zsh
 * @version 1.0.0
 * @since 2022-09-25 13:28
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDTO {

    private Long expireTime;

    private String signData;

}
