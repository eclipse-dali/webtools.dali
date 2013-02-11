/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkReadOnlyAnnotation;

@SuppressWarnings("nls")
public class ReadOnlyTests extends EclipseLinkJavaResourceModelTestCase {

	public ReadOnlyTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestReadOnly() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(EclipseLink.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@ReadOnly");
			}
		});
	}
	
	public void testReadOnly() throws Exception {
		ICompilationUnit cu = this.createTestReadOnly();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkReadOnlyAnnotation readOnly = (EclipseLinkReadOnlyAnnotation) resourceType.getAnnotation(EclipseLink.READ_ONLY);
		assertNotNull(readOnly);
	}

}
