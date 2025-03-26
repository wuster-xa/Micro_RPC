package com.microrpc.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nonnull
@TypeQualifierDefault({METHOD, FIELD, PARAMETER})
public @interface NullSafe {
    // 标记类中的所有方法返回值、字段和参数默认为非空
} 