/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.utility.jdt;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;


public class JDTToolsTests
		extends AnnotationTestCase {
	
	public JDTToolsTests(String name) {
		super(name);
	}
	
	
	public void testTypeIsSubtype() throws Exception {		
		IJavaProject jProj = getJavaProject().getJavaProject();
		
		// same type
		assertTrue(JDTTools.typeIsSubType(jProj, ArrayList.class.getName(), ArrayList.class.getName()));
		assertTrue(JDTTools.typeIsSubType(jProj, List.class.getName(), List.class.getName()));
		
		// concrete type is subtype of interface
		assertTrue(JDTTools.typeIsSubType(jProj, ArrayList.class.getName(), List.class.getName()));
		assertTrue(JDTTools.typeIsSubType(jProj, TreeSet.class.getName(), Iterable.class.getName()));
		
		// concrete type is not subtype of interface
		assertFalse(JDTTools.typeIsSubType(jProj, ArrayList.class.getName(), Map.class.getName()));
		assertFalse(JDTTools.typeIsSubType(jProj, TreeSet.class.getName(), Map.class.getName()));
		
		// interface is subtype of interface
		assertTrue(JDTTools.typeIsSubType(jProj, List.class.getName(), Collection.class.getName()));
		assertTrue(JDTTools.typeIsSubType(jProj, SortedSet.class.getName(), Iterable.class.getName()));
		
		// interface is not subtype of interface
		assertFalse(JDTTools.typeIsSubType(jProj, List.class.getName(), Map.class.getName()));
		assertFalse(JDTTools.typeIsSubType(jProj, SortedSet.class.getName(), Map.class.getName()));
		
		// concrete type is subtype of concrete type
		assertTrue(JDTTools.typeIsSubType(jProj, ArrayList.class.getName(), AbstractList.class.getName()));
		assertTrue(JDTTools.typeIsSubType(jProj, LinkedList.class.getName(), AbstractCollection.class.getName()));
		
		// concrete type is not subtype of concrete type
		assertFalse(JDTTools.typeIsSubType(jProj, ArrayList.class.getName(), Vector.class.getName()));
		assertFalse(JDTTools.typeIsSubType(jProj, LinkedList.class.getName(), Vector.class.getName()));
	}
}
