package com.krld.common;

import java.io.Serializable;

@Deprecated
public interface MyResponse  extends Serializable {
    public long getRequestId();
    public Object getResponse();
}
