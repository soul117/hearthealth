/*
 * Copyright (C) 2014 The Android Open Source Project
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
package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import java.util.List;

public class CollectionUtils {
    public static <T> boolean contains(final T[] array, final T v) {
        for (final T e : array)
            if (e == v || v != null && v.equals(e))
                return true;

        return false;
    }

    public static <T> T[] convertToArray(final List<T> list) {
        T[] array = (T[]) new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static long[] convertLongListToPrimitiveArray(final List<Long> longList) {
        long[] array = new long[longList.size()];
        for (int i = 0; i < longList.size(); i++) {
            array[i] = longList.get(i);
        }
        return array;
    }

    public static <T> boolean isNotEmptyArray(T[] array) {
        return array != null && array.length > 0;
    }

    public static boolean isNotEmptyLongArray(long[] array) {
        return array != null && array.length > 0;
    }
}
