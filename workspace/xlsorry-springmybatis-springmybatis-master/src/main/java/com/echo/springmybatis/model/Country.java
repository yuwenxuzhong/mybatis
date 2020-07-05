package com.echo.springmybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 即时到账
 *
 * @author echo
 * @date 2019-09-05 03:09:49
 * @since jdk 1.8
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    /**
     * 主键
     */
    private Integer id;
    private String countryname;
    private String countrycode;
}
