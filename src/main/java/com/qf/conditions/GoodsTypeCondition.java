package com.qf.conditions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kilote
 * @version 1.0
 * @ClassName: GoodsTypeCondition
 * @Date: 2022/9/23 20:50
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodsTypeCondition {
    private String goodsName;
    private String goodsLevel;
}
