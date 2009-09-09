/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkReadOnlyAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class ReadOnlyTests extends EclipseLinkJavaResourceModelTestCase {

	public ReadOnlyTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestReadOnly() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@ReadOnly");
			}
		});
	}
	
	public void testReadOnly() throws Exception {
		ICompilationUnit cu = this.createTestReadOnly();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		EclipseLinkReadOnlyAnnotation readOnly = (EclipseLinkReadOnlyAnnotation) typeResource.getAnnotation(EclipseLink.READ_ONLY);
		assertNotNull(readOnly);
	}

}
