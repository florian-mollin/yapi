package com.mollin.yapi.result;

import com.google.gson.annotations.Expose;
import com.mollin.yapi.utils.YeelightUtils;

import java.util.Optional;

/**
 * Representation of command response (when response is valid/ok)
 */
public class YeelightResultOk {
    /**
     * Command ID that produced the result
     */
    @Expose private int id;
    /**
     * Result array returned by command
     */
    @Expose private String[] result;

    /**
     * @see YeelightResultOk#from(String)
     */
    private YeelightResultOk(){
    }

    /**
     * Generate a {@link YeelightResultOk} from datas if datas is a response
     * @param datas JSON representation of valid/ok response
     * @return YeelightResultOk if datas is a valid JSON representation of valid/ok response
     */
    public static Optional<YeelightResultOk> from(String datas) {
        return YeelightUtils.generateFromJsonDatas(datas, YeelightResultOk::validate, YeelightResultOk.class);
    }

    /**
     * Validate an okResult
     * @param okResult OkResult to validate
     * @return True if okResult contains a non-null result attribute
     */
    private static boolean validate(YeelightResultOk okResult) {
        return okResult.result != null;
    }

    /**
     * Getter for command ID
     * @return Command ID that produced the result
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for result array
     * @return Result array content for command response
     */
    public String[] getResult() {
        return this.result;
    }
}
