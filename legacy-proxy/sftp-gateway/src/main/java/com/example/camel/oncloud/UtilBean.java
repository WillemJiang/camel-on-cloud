/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.example.camel.oncloud;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class UtilBean {
    public String calcMd5Checksum(File file) throws Exception {
        InputStream inputStream = new DataInputStream(new FileInputStream(file));
        String md5 = DigestUtils.md5DigestAsHex(inputStream);
        inputStream.close();
        return md5;
    }
}
