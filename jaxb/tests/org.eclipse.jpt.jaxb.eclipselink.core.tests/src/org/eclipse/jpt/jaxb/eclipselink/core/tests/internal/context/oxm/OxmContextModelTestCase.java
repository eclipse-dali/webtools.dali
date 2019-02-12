/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.oxm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;

@SuppressWarnings("nls")
public class OxmContextModelTestCase
		extends ELJaxbContextModelTestCase {
	
	protected OxmContextModelTestCase(String name) {
		super(name);
	}
	
	
	protected void addOxmFile(String fileName, StringBuffer contents) throws Exception {
		IFile oxmFile = getJavaProjectTestHarness().getProject().getFolder("src").getFile(fileName);
		oxmFile.create(new ByteArrayInputStream(contents.toString().getBytes()), true, null);
	}
	
	protected void assertFileContentsContains(String fileName, String stringToTest, boolean doesContain) throws Exception {
		StringBuilder sb = new StringBuilder();
	    IFile oxmFile = getJavaProjectTestHarness().getProject().getFolder("src").getFile(fileName);
		InputStream is = oxmFile.getContents();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		is.close();
			
	    if (doesContain) {
    		assertTrue(new String(sb).contains(stringToTest));
    	}
    	else {
    		assertFalse(new String(sb).contains(stringToTest));
    	}
	}
}
