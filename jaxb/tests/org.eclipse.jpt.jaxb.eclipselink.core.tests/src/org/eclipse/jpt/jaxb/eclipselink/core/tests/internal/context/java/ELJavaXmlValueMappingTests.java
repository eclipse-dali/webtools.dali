/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlValueMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;

@SuppressWarnings("nls")
public class ELJavaXmlValueMappingTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlValueMappingTests(String name) {
		super(name);
	}
	
	
	@Override
	protected String getPlatformID() {
		return ELJaxb_2_2_PlatformDefinition.ID;
	}
	
	private ICompilationUnit createTypeWithXmlValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_TYPE, JAXB.XML_VALUE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlValue");
			}
		});
	}
	
	public void testModifyXmlCDATA() throws Exception {
		createTypeWithXmlValue();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlValueMapping mapping = (ELJavaXmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(resourceAttribute.getAnnotation(ELJaxb.XML_CDATA));
		assertNull(mapping.getXmlCDATA());
		
		mapping.addXmlCDATA();
		
		assertNotNull(resourceAttribute.getAnnotation(ELJaxb.XML_CDATA));
		assertNotNull(mapping.getXmlCDATA());
		
		mapping.removeXmlCDATA();
		
		assertNull(resourceAttribute.getAnnotation(ELJaxb.XML_CDATA));
		assertNull(mapping.getXmlCDATA());
	}
	
	public void testUpdateXmlCDATA() throws Exception {
		createTypeWithXmlValue();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlValueMapping mapping = (ELJavaXmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(resourceAttribute.getAnnotation(ELJaxb.XML_CDATA));
		assertNull(mapping.getXmlCDATA());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlValueMappingTests.this.addMarkerAnnotation(
						declaration.getDeclaration(), ELJaxb.XML_CDATA);
			}
		});
		
		assertNotNull(resourceAttribute.getAnnotation(ELJaxb.XML_CDATA));
		assertNotNull(mapping.getXmlCDATA());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlValueMappingTests.this.removeAnnotation(declaration, ELJaxb.XML_CDATA);
			}
		});
		
		assertNull(resourceAttribute.getAnnotation(ELJaxb.XML_CDATA));
		assertNull(mapping.getXmlCDATA());
	}
}
