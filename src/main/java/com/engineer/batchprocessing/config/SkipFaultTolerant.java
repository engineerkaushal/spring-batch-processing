package com.engineer.batchprocessing.config;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class SkipFaultTolerant implements SkipPolicy {
    @Override
    public boolean shouldSkip (Throwable t, long skipCount) throws SkipLimitExceededException {
        return t instanceof NumberFormatException;
    }
}
