package com.fly.common.date;

import java.io.Serializable;

/**
 * @author zefa
 *
 */
public class TimeRangeBean implements Serializable {
    private static final long serialVersionUID = -6616654009320107431L;
    
    private long startTime;
    private long endTime;
    
    public long getStartTime() {
        return startTime;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public long getEndTime() {
        return endTime;
    }
    
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public String toString() {
        return "TimeRangeBean [startTime=" + startTime + ", endTime=" + endTime
                + "]";
    }
}
