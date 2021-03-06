package com.intelerad.android.portal.rpc;
/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;
import java.util.Map;

/**
 * Represents the result of an AppEngineClient call.
 */
public class Response {
    
    /**
     * The HTTP status code
     */
    private final int mStatus;
    
    /**
     * The HTTP headers received in the response
     */
    private final Map<String, List<String>> mHeaders;
    
    /**
     * The response body, if any
     */
    private final byte[] mBody;

    protected Response( int status, Map<String, List<String>> headers, byte[] body )
    {
        mStatus = status;
        mHeaders = headers;
        mBody = body;
    }
    
    public byte[] getBody()
    {
        return mBody;
    }
    
    public Map<String, List<String>> getHeaders()
    {
        return mHeaders;
    }
    
    public int getStatus()
    {
        return mStatus;
    }
}