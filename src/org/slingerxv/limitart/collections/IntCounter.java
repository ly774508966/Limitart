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
package org.slingerxv.limitart.collections;

import org.slingerxv.limitart.base.Conditions;
import org.slingerxv.limitart.base.ThreadUnsafe;
import org.slingerxv.limitart.util.GameMathUtil;

/**
 * int计数器
 *
 * @author hank
 * @version 2017/11/2 0002 20:41
 */
@ThreadUnsafe
public class IntCounter {
    private int count;

    public IntCounter() {
        this(0);
    }

    public IntCounter(int initVal) {
        Conditions.checkArgs(initVal >= low() && initVal <= high(), "low<=initVal<=high");
        set(initVal);
    }

    /**
     * 最大限制
     *
     * @return
     */
    protected int high() {
        return Integer.MAX_VALUE;
    }

    /**
     * 最小限制
     *
     * @return
     */
    protected int low() {
        return 0;
    }

    /**
     * 获取当前数值
     *
     * @return
     */
    public int get() {
        return this.count;
    }

    /**
     * 直接设置当前值
     *
     * @param value
     * @return
     */
    protected int set(int value) {
        return this.count = GameMathUtil.fixedBetween(value, low(), high());
    }

    /**
     * +1并获取
     *
     * @return
     */
    public int incrementAndGet() {
        return addAndGet(1);
    }

    /**
     * -1并获取
     *
     * @return
     */
    public int decrementAndGet() {
        return addAndGet(-1);
    }

    /**
     * 增加并获取
     *
     * @param delta
     * @return
     */
    public int addAndGet(int delta) {
        return set(get() + delta);
    }

    /**
     * 获取并+1
     *
     * @return
     */
    public int getAndIncrement() {
        return getAndAdd(1);
    }

    /**
     * 获取并-1
     *
     * @return
     */
    public int getAndDecrement() {
        return getAndAdd(-1);
    }

    /**
     * 获取并增加
     *
     * @param delta
     * @return
     */
    public int getAndAdd(int delta) {
        int old = get();
        set(old + delta);
        return old;
    }
}
