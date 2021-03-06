/*
 * Copyright (c) 2016-present The Limitart Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.limitart.collections;


import top.limitart.base.Func1;
import top.limitart.base.ThreadUnsafe;
import top.limitart.util.EnumUtil;

import java.util.Iterator;

/**
 * 通过枚举的序列(ordinal)来映射数组元素
 *
 * @author hank
 */
@ThreadUnsafe
public class EnumList<E extends Enum<E>, V> implements Iterable<V> {
    private V[] objects;

    /**
     * 创建一个空的枚举列表
     *
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E extends Enum<E>, V> EnumList<E, V> create(Class<E> enumClass) {
        return new EnumList<>(enumClass);
    }

    /**
     * 通过初始化函数创建一个新的枚举列表
     *
     * @param initializer
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E extends Enum<E>, V> EnumList<E, V> withInitializer(Class<E> enumClass, Func1<E, V> initializer) {
        return new EnumList<>(enumClass, initializer);
    }

    public EnumList(Class<E> enumClass) {
        objects = (V[]) new Object[EnumUtil.length(enumClass)];
    }

    public EnumList(Class<E> enumClass, Func1<E, V> initializer) {
        this(enumClass);
        for (int i = 0; i < objects.length; i++) {
            put(i, initializer.run(EnumUtil.byOrdinal(enumClass, i)));
        }
    }

    /**
     * 清空
     */
    public void clear() {
        objects = (V[]) new Object[objects.length];
    }


    /**
     * 大小
     *
     * @return
     */
    public int size() {
        return objects.length;
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public V get(E key) {
        return get(key.ordinal());
    }

    /**
     * 通过序列获取
     *
     * @param ordinal
     * @return
     */
    private V get(int ordinal) {
        if (ordinal < 0 || ordinal > objects.length - 1) {
            return null;
        }
        return objects[ordinal];
    }

    /**
     * 放置元素
     *
     * @param key
     * @param v
     * @return 旧值
     */
    public V put(E key, V v) {
        return put(key.ordinal(), v);
    }

    private V put(int ordinal, V v) {
        // 容错
        if (ordinal > objects.length - 1) {
            Object[] temp = new Object[ordinal + 1];
            System.arraycopy(objects, 0, temp, 0, objects.length);
            this.objects = (V[]) temp;
        }
        V object = objects[ordinal];
        objects[ordinal] = v;
        return object;
    }

    @Override
    public Iterator<V> iterator() {
        return new ArrayIterator<>(objects);
    }

}
