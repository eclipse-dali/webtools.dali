/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.jaxb.core.internal.resource.jaxbprops.JaxbPropertiesResourceModelProvider;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;


public class JaxbPropertiesResourceTests
		extends AnnotationTestCase {
	
	private static String JAXB_PROPERTIES = "jaxb.properties";
	
	
	public JaxbPropertiesResourceTests(String name) {
		super(name);
	}
	
	
	private IFile createJaxbProperties(IPath projectRelativePath, String[] ... propertyValuePairs) throws Exception {
		IFolder folder = getJavaProject().getProject().getFolder(projectRelativePath);
		if (! folder.exists()) {
			folder.create(true, false, null);
		}
		IFile jaxbProperties = getJavaProject().getProject().getFile(projectRelativePath.append(new Path(JAXB_PROPERTIES)));
		InputStream stream = inputStream(propertyValuePairs);
		jaxbProperties.create(stream, true, null);
		return jaxbProperties;
	}
	
	private void setProperties(IFile jaxbProperties, String[] ... propertyValuePairs) throws Exception {
		jaxbProperties.setContents(inputStream(propertyValuePairs), true, false, null);
	}
	
	private InputStream inputStream(String[] ... propertyValuePairs) {
		StringBuffer sb = new StringBuffer();
		for (String[] propertyValuePair : propertyValuePairs) {
			sb.append(propertyValuePair[0] + "=" + propertyValuePair[1] + CR);
		}
		return new ByteArrayInputStream(sb.toString().getBytes());
	}
	
	public void testUpdateProperties() throws Exception {
		IFile jaxbProperties = createJaxbProperties(new Path("src/test"), new String[] {"foo", "fooProp"}, new String[] {"bar", "barProp"});
		JaxbPropertiesResource resource = JaxbPropertiesResourceModelProvider.instance().buildResourceModel(jaxbProperties);
		
		assertEquals(resource.getProperty("foo"), "fooProp");
		assertEquals(resource.getProperty("bar"), "barProp");
		
		setProperties(jaxbProperties, new String[] {"foo", "fooProp2"}, new String[] {"baz", "bazProp"});
		
		assertEquals(resource.getProperty("foo"), "fooProp2");
		assertNull(resource.getProperty("bar"));
		assertEquals(resource.getProperty("baz"), "bazProp");
		
		setProperties(jaxbProperties);
		
		assertNull(resource.getProperty("foo"));
		assertNull(resource.getProperty("bar"));
		assertNull(resource.getProperty("baz"));
	}
}
