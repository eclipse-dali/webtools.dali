/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.internal.resource.jaxbindex.JaxbIndexResourceModelProvider;
import org.eclipse.jpt.jaxb.core.resource.jaxbindex.JaxbIndexResource;


@SuppressWarnings("nls")
public class JaxbIndexResourceTests
		extends AnnotationTestCase {
	
	private static String JAXB_INDEX = "jaxb.index";
	
	
	public JaxbIndexResourceTests(String name) {
		super(name);
	}
	
	
	@SuppressWarnings("resource")
	private IFile createJaxbIndex(IPath projectRelativePath, String... classNames) throws Exception {
		IFolder folder = getProject().getFolder(projectRelativePath);
		if (! folder.exists()) {
			folder.create(true, false, null);
		}
		IFile jaxbIndex = getProject().getFile(projectRelativePath.append(new Path(JAXB_INDEX)));
		// stream is closed by IFile.create(...)
		InputStream stream = inputStream(classNames);
		jaxbIndex.create(stream, true, null);
		return jaxbIndex;
	}
	
	private void setClassNames(IFile jaxbIndex, String... classNames) throws Exception {
		jaxbIndex.setContents(inputStream(classNames), true, false, null);
	}
	
	private InputStream inputStream(String... classNames) {
		StringBuffer sb = new StringBuffer();
		for (String className : classNames) {
			sb.append(className + CR);
		}
		return new ByteArrayInputStream(sb.toString().getBytes());
	}
	
	public void testUpdateClasses() throws Exception {
		IFile jaxbIndex = createJaxbIndex(new Path("src/test"), "foo", "bar");
		JaxbIndexResource resource = JaxbIndexResourceModelProvider.instance().buildResourceModel(jaxbIndex);
		
		assertTrue(IterableTools.elementsAreEqual(
				resource.getFullyQualifiedClassNames(), 
				IterableTools.iterable(new String[] {"test.foo", "test.bar"})));
		
		setClassNames(jaxbIndex, "foo", "bar", "baz");
		
		assertTrue(IterableTools.elementsAreEqual(
				resource.getFullyQualifiedClassNames(), 
				IterableTools.iterable(new String[] {"test.foo", "test.bar", "test.baz"})));
		
		setClassNames(jaxbIndex);
		
		assertTrue(IterableTools.isEmpty(resource.getFullyQualifiedClassNames()));
		
		jaxbIndex = createJaxbIndex(new Path("src"), "foo", "bar");
		resource = JaxbIndexResourceModelProvider.instance().buildResourceModel(jaxbIndex);
		
		assertTrue(IterableTools.elementsAreEqual(
				resource.getFullyQualifiedClassNames(), 
				IterableTools.iterable(new String[] {"foo", "bar"})));
		
		setClassNames(jaxbIndex, "foo", "bar", "baz");
		
		assertTrue(IterableTools.elementsAreEqual(
				resource.getFullyQualifiedClassNames(), 
				IterableTools.iterable(new String[] {"foo", "bar", "baz"})));
		
		setClassNames(jaxbIndex);
		
		assertTrue(IterableTools.isEmpty(resource.getFullyQualifiedClassNames()));
	}
}
