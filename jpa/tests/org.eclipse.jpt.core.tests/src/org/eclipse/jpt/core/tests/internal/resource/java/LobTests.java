/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.LobAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class LobTests extends JavaResourceModelTestCase {

	public LobTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestLob() throws Exception {
		this.createAnnotationAndMembers("Lob", "");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.LOB);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Lob");
			}
		});
	}
	
	public void testLob() throws Exception {
		ICompilationUnit cu = this.createTestLob();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		LobAnnotation lob = (LobAnnotation) attributeResource.getAnnotation(JPA.LOB);
		assertNotNull(lob);
	}

}
