/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


public class GenericJavaPersistentAttributeTests
		extends JaxbContextModelTestCase {
	
	private static String TEST_CLASS_NAME = "TestClass";
	
	public GenericJavaPersistentAttributeTests(String name) {
		super(name);
	}
	
	
	private void createXmlTypeWithVariousAttributes() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import ").append(JAXB.XML_TYPE).append(";").append(CR);
				sb.append("import java.util.List;").append(CR);
				sb.append(CR);
				sb.append("@XmlType").append(CR);
				sb.append("public class ").append(TEST_CLASS_NAME).append("<T extends Number> ").append("{").append(CR);
				sb.append("    public String string;").append(CR);
				sb.append("    public List<String> stringList;").append(CR);
				sb.append("    public String[] stringArray;").append(CR);
				sb.append("    public String[][] stringDoubleArray;").append(CR);
				sb.append("    public T generic;").append(CR);
				sb.append("    public List<T> genericList;").append(CR);
				sb.append("    public T[] genericArray;").append(CR);
				sb.append("    public List<?> wildcardList;").append(CR);
				sb.append("    public byte[] byteArray;").append(CR);
				sb.append("    public List list;").append(CR);
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, TEST_CLASS_NAME + ".java", sourceWriter);
	}
	
	
	public void testJavaResourceType() throws Exception {
		createXmlTypeWithVariousAttributes();
		JaxbClassMapping classMapping = ((JaxbClass) getContextRoot().getType(PACKAGE_NAME_ + TEST_CLASS_NAME)).getMapping();
		
		// String string
		JaxbPersistentAttribute att = CollectionTools.get(classMapping.getAttributes(), 0);
		assertEquals("java.lang.String", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(false, att.isJavaResourceAttributeCollectionType());
		
		// List<String> stringList
		att = CollectionTools.get(classMapping.getAttributes(), 1);
		assertEquals("java.lang.String", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(true, att.isJavaResourceAttributeCollectionType());
		
		// String[] stringArray
		att = CollectionTools.get(classMapping.getAttributes(), 2);
		assertEquals("java.lang.String", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(true, att.isJavaResourceAttributeCollectionType());
		
		// String[][] stringDoubleArray
		att = CollectionTools.get(classMapping.getAttributes(), 3);
		assertEquals("java.lang.String[][]", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(false, att.isJavaResourceAttributeCollectionType());
		
		// T generic
		att = CollectionTools.get(classMapping.getAttributes(), 4);
		assertEquals("java.lang.Number", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(false, att.isJavaResourceAttributeCollectionType());
		
		// List<T> genericList
		att = CollectionTools.get(classMapping.getAttributes(), 5);
		assertEquals("java.lang.Number", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(true, att.isJavaResourceAttributeCollectionType());
		
		// T[] genericArray
		att = CollectionTools.get(classMapping.getAttributes(), 6);
		assertEquals("java.lang.Number", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(true, att.isJavaResourceAttributeCollectionType());
		
		// List<?> wildcardList
		att = CollectionTools.get(classMapping.getAttributes(), 7);
		assertEquals("java.lang.Object", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(true, att.isJavaResourceAttributeCollectionType());
		
		// byte[] byteArray
		att = CollectionTools.get(classMapping.getAttributes(), 8);
		assertEquals("byte[]", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(false, att.isJavaResourceAttributeCollectionType());
		
		// List list
		att = CollectionTools.get(classMapping.getAttributes(), 9);
		assertEquals("java.lang.Object", att.getJavaResourceAttributeBaseTypeName());
		assertEquals(true, att.isJavaResourceAttributeCollectionType());
	}
}