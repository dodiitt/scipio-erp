/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.ilscipio.scipio.ce.webapp.ftl.lang;

import java.util.List;

import com.ilscipio.scipio.ce.webapp.ftl.CommonFtlUtil;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * SCIPIO: CopyObjectMethod - Helper method to clone (shallow copy) a map or list.
 */
public class CopyMapMethod implements TemplateMethodModelEx {

    //private static final Debug.OfbizLogger module = Debug.getOfbizLogger(java.lang.invoke.MethodHandles.lookup().lookupClass());

    /*
     * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
     */
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
        if (args == null || args.size() < 1 || args.size() > 3) {
            throw new TemplateModelException("Invalid number of arguments (expected: 1-3)");
        }
        TemplateModel hashObjModel = (TemplateModel) args.get(0);
        if (!(hashObjModel instanceof TemplateHashModel)) {
            throw new TemplateModelException("First argument not an instance of TemplateHashModel");
        }
        TemplateHashModel hashModel = (TemplateHashModel) hashObjModel;

        String mode = null;
        if (args.size() >= 2) {
            mode = LangFtlUtil.getAsStringNonEscaping(((TemplateScalarModel) args.get(1)));
        }

        TemplateModel keysModel = null;
        if (args.size() >= 3) {
            keysModel = (TemplateModel) args.get(2);
        }

        Environment env = CommonFtlUtil.getCurrentEnvironment();

        Boolean include = null;
        if (mode != null && !mode.isEmpty()) {
            if (mode.contains("i")) {
                include = Boolean.TRUE;
            }
            else if (mode.contains("e")) {
                include = Boolean.FALSE;
            }
        }
        ObjectWrapper objectWrapper = LangFtlUtil.getCurrentObjectWrapper(env);
        return LangFtlUtil.copyMap(hashModel, LangFtlUtil.getAsStringSet(keysModel), include,
                LangFtlUtil.TemplateValueTargetType.SIMPLEMODEL, objectWrapper);
    }

}
