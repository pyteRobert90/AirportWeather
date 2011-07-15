/*
 * Copyright 2011 Jan Kronquist.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayway.camel.typeconverter;

import java.lang.reflect.Constructor;

import org.apache.camel.Converter;
import org.apache.camel.FallbackConverter;
import org.apache.camel.spi.TypeConverterRegistry;

/**
 * Convert to a type if there is a one argument constructor in that type that
 * can take the value as argument.
 */
@Converter
public class UsingConstructor {
    @SuppressWarnings("unchecked")
    @FallbackConverter
    public static <T> T convertTo(Class<T> type, Object value, TypeConverterRegistry registry) throws Throwable {
        for (Constructor<?> c : type.getConstructors()) {
            Class<?>[] types = c.getParameterTypes();
            if (types.length == 1 && types[0].isAssignableFrom(value.getClass())) {
                    return (T) c.newInstance(value);
            }
        }
        return null;
    }
}
