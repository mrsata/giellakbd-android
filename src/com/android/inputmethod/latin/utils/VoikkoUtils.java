/*
 * Copyright (C) 2015 Brendan Molloy <brendan@bbqsrc.net>
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

package com.android.inputmethod.latin.utils;

import android.content.Context;
import android.util.Log;

import org.puimula.libvoikko.Voikko;

public final class VoikkoUtils {
    private static final String TAG = JniUtils.class.getSimpleName();

    private VoikkoUtils() {}

    static {
        try {
            System.loadLibrary("stlport_shared");
        } catch (UnsatisfiedLinkError ule) {
            Log.e(TAG, "Could not load native library stlport_shared", ule);
        }
    }

    public static void init(Context ctx) {
        final String dir = ctx.getApplicationContext().getApplicationInfo().nativeLibraryDir;
        Voikko.addLibraryPath(dir);

        // Force loading of voikko native library
        Voikko.listDicts();
    }
}
