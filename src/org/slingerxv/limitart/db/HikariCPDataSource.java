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
package org.slingerxv.limitart.db;

import com.zaxxer.hikari.HikariDataSource;

/**
 * HikariCP数据源
 *
 * @author hank
 * @version 2018/4/14 0014 16:47
 */
public class HikariCPDataSource extends HikariDataSource implements DBDataSource {
    public HikariCPDataSource(String url, String username, String password) {
        setJdbcUrl(url);
        setUsername(username);
        setPassword(password);
    }

    @Override
    public boolean autoCommit() {
        return isAutoCommit();
    }

    @Override
    public void autoCommit(boolean autoCommit) {
        setAutoCommit(autoCommit);
    }
}