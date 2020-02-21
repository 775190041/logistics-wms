package com.nf.commons.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 *  操作结果集
 */
@Data

//@JsonIgnoreProperties() 忽略字段。忽略的字段在括号加上。 ，。
public class ResourceVo implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;

    private ResourceVo(Builder builder) {
        setSuccess(builder.success);
        setMsg(builder.msg);
        setObj(builder.obj);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private boolean success;
        private String msg;
        private Object obj;

        private Builder() {
        }

        public Builder success(boolean val) {
            success = val;
            return this;
        }

        public Builder msg(String val) {
            msg = val;
            return this;
        }

        public Builder obj(Object val) {
            obj = val;
            return this;
        }

        public ResourceVo build() {
            return new ResourceVo(this);
        }
    }
}
