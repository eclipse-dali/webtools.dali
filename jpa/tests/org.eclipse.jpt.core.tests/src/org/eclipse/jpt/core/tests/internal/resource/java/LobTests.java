/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.Lob;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class LobTests extends JavaResourceModelTestCase {

	public LobTests(String name) {
		super(name);
	}

	private IType createTestLob() throws Exception {
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
		IType testType = this.createTestLob();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		Lob lob = (Lob) attributeResource.annotation(JPA.LOB);
		assertNotNull(lob);
	}

}
