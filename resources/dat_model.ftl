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

package ${_package};

import top.limitart.dat.DataMeta;

/**
* ${_explain}
*
* @author limitart
*/
public class ${_name} extends DataMeta {
<#list _cols as _col>
    /**
    *  ${_col._explain}
    */
    private ${_col._type} ${_col._name};
</#list>
<#list _cols as _col>
    /**
    *  ${_col._explain}
    */
    public ${_col._type} get${_col._name?cap_first}() {
        return this.${_col._name};
    }
</#list>
}