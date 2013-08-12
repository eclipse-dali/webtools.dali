/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAdapter;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlAdapterTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaXmlAdapterTests(String name) {
		super(name);
	}

	private ICompilationUnit createXmlType() throws Exception {
		return this.createTestType(
				new DefaultAnnotationWriter() {
					@Override
					public Iterator<String> imports() {
						return IteratorTools.iterator(JAXB.XML_TYPE);
					}
					
					@Override
					public void appendTypeAnnotationTo(StringBuilder sb) {
						sb.append("@XmlType");
					}
				});
	}
	
	private void createObjObjXmlAdapter() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import " + JAXB.XML_ADAPTER + ";").append(CR);
				sb.append(CR);
				sb.append("public class ObjObjAdapter extends XmlAdapter<Object, Object> {").append(CR);
				sb.append("    public Object marshal(Object obj) { return null; }").append(CR);
				sb.append("    public Object unmarshal(Object obj) { return null; }").append(CR);
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "ObjObjAdapter.java", sourceWriter);
	}
	
	private void createMapCalendarXmlAdapter() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import " + JAXB.XML_ADAPTER + ";").append(CR);
				sb.append("import java.util.Map;").append(CR);
				sb.append("import java.util.GregorianCalendar;").append(CR);
				sb.append(CR);
				sb.append("public class MapCalendarAdapter extends XmlAdapter<GregorianCalendar, Map<String, String>> {").append(CR);
				sb.append("    public GregorianCalendar marshal(Map<String, String> obj) { return null; }").append(CR);
				sb.append("    public GregorianCalendar marshal(Object obj) { return null; }").append(CR);
				sb.append("    public Map<String, String> unmarshal(Gregorian Calendar obj) { return null; }").append(CR);
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "MapCalendarAdapter.java", sourceWriter);
	}
	
	
	public void testBoundAndValueTypes() throws Exception {
		createXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping attributeMapping = (XmlElementMapping) persistentAttribute.getMapping();
		
		XmlJavaTypeAdapter xmlJavaTypeAdapter = attributeMapping.addXmlJavaTypeAdapter();
		xmlJavaTypeAdapter.setValue("test.ObjObjAdapter");
		
		assertEquals("test.ObjObjAdapter", xmlJavaTypeAdapter.getValue());
		assertEquals("test.ObjObjAdapter", xmlJavaTypeAdapter.getFullyQualifiedValue());
		assertNull(xmlJavaTypeAdapter.getXmlAdapter());
		
		createObjObjXmlAdapter();
		XmlAdapter xmlAdapter = xmlJavaTypeAdapter.getXmlAdapter();
		assertNotNull(xmlAdapter);
		assertEquals("java.lang.Object", xmlAdapter.getBoundType());
		assertEquals("java.lang.Object", xmlAdapter.getValueType());
		
		createMapCalendarXmlAdapter();
		xmlJavaTypeAdapter.setValue("test.MapCalendarAdapter");
		xmlAdapter = xmlJavaTypeAdapter.getXmlAdapter();
		assertNotNull(xmlAdapter);
		assertEquals("java.util.Map", xmlAdapter.getBoundType());
		assertEquals("java.util.GregorianCalendar", xmlAdapter.getValueType());
	}
}
