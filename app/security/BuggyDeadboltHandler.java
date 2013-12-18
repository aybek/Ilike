/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package security;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.utils.AuthorisedUserUtils;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.SimpleResult;

/**
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class BuggyDeadboltHandler extends AbstractDeadboltHandler
{

    @Override
    public F.Promise<SimpleResult> beforeAuthCheck(Http.Context context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Subject getSubject(Http.Context context)
    {
        // in a real application, the student name would probably be in the session following a login process
        return AuthorisedUserUtils.getUserByEmail(Controller.session().get("connected"));
    }

    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context)
    {
        return new MyDynamicResourceHandler();
    }


    public Result onAccessFailure(Http.Context context,
                                  String content)
    {
        throw new RuntimeException("An exception occurred in onAccessFailure");
    }
}
