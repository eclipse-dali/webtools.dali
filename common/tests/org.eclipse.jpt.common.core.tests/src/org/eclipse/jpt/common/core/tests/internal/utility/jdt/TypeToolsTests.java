/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
import org.eclipse.jpt.common.core.internal.utility.TypeTools;

public class TypeToolsTests
	extends AnnotationTestCase
{
	public TypeToolsTests(String name) {
		super(name);
	}
	
	public void testIsSubtypeOf() throws Exception {		
		IJavaProject jProj = this.getJavaProject();
		
		// same type
		assertTrue(TypeTools.isSubTypeOf(ArrayList.class.getName(), ArrayList.class.getName(), jProj));
		assertTrue(TypeTools.isSubTypeOf(List.class.getName(), List.class.getName(), jProj));
		
		// concrete type is subtype of interface
		assertTrue(TypeTools.isSubTypeOf(ArrayList.class.getName(), List.class.getName(), jProj));
		assertTrue(TypeTools.isSubTypeOf(TreeSet.class.getName(), Iterable.class.getName(), jProj));
		
		// concrete type is not subtype of interface
		assertFalse(TypeTools.isSubTypeOf(ArrayList.class.getName(), Map.class.getName(), jProj));
		assertFalse(TypeTools.isSubTypeOf(TreeSet.class.getName(), Map.class.getName(), jProj));
		
		// interface is subtype of interface
		assertTrue(TypeTools.isSubTypeOf(List.class.getName(), Collection.class.getName(), jProj));
		assertTrue(TypeTools.isSubTypeOf(SortedSet.class.getName(), Iterable.class.getName(), jProj));
		
		// interface is not subtype of interface
		assertFalse(TypeTools.isSubTypeOf(List.class.getName(), Map.class.getName(), jProj));
		assertFalse(TypeTools.isSubTypeOf(SortedSet.class.getName(), Map.class.getName(), jProj));
		
		// concrete type is subtype of concrete type
		assertTrue(TypeTools.isSubTypeOf(ArrayList.class.getName(), AbstractList.class.getName(), jProj));
		assertTrue(TypeTools.isSubTypeOf(LinkedList.class.getName(), AbstractCollection.class.getName(), jProj));
		
		// concrete type is not subtype of concrete type
		assertFalse(TypeTools.isSubTypeOf(ArrayList.class.getName(), Vector.class.getName(), jProj));
		assertFalse(TypeTools.isSubTypeOf(LinkedList.class.getName(), Vector.class.getName(), jProj));
	}
}
