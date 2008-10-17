/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddable;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class EclipseLinkOrmEmbeddableTests extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmEmbeddableTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEmbeddableForCustomizer() throws Exception {
		createCustomizerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, EclipseLinkJPA.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}
	
	private void createCustomizerAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Customizer", "Class value()");		
	}
		
	public void testUpdateCustomizerClass() throws Exception {
		createTestEmbeddableForCustomizer();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaEmbeddable javaContextEmbeddable = (EclipseLinkJavaEmbeddable) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmEmbeddable ormContextEmbeddable = (EclipseLinkOrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) ormResource().getEntityMappings().getEmbeddables().get(0);


		// check defaults
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// set xml customizer, check defaults
		resourceEmbeddable.setCustomizer(EclipseLinkOrmFactory.eINSTANCE.createXmlCustomizer());
		assertNull(resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		
		// set xml customizer class, check override
		
		resourceEmbeddable.getCustomizer().setCustomizerClassName("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// clear xml customizer class, set java customizer class, check defaults
		
		resourceEmbeddable.getCustomizer().setCustomizerClassName(null);
		javaContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass("bar");
		
		assertNull(resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		// set metadataComplete to True, check defaults not from java

		ormContextEmbeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		ormContextEmbeddable.setSpecifiedMetadataComplete(null);
		
		// set xml customizer class, check override
		
		resourceEmbeddable.getCustomizer().setCustomizerClassName("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		//set xml customizer null
		javaContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass(null);
		resourceEmbeddable.setCustomizer(null);
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

	}
	
	public void testModifyCustomizerClass() throws Exception {
		createTestEmbeddableForCustomizer();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmEmbeddable ormContextEmbeddable = (EclipseLinkOrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) ormResource().getEntityMappings().getEmbeddables().get(0);
		
		// check defaults
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// set context customizer, check resource
		
		ormContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
				
		// set context customizer to null, check resource
		
		ormContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass(null);
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
	}

}
