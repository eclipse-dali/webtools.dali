/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmConverterType2_1;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_5ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_5EntityMappingsTests
	extends EclipseLink2_5ContextModelTestCase
{
	public EclipseLink2_5EntityMappingsTests(String name) {
		super(name);
	}

	
	public void testUpdateCustomConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertEquals(0, resourceEntityMappings.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//add a converter to the resource model, check context model
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEntityMappings.getConverters().add(resourceConverter);
		resourceConverter.setClassName("Foo");
		resourceConverter.setName("myConverter");
		
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ListIterator<? extends EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, resourceEntityMappings.getConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//add another converter to the resource model, check context model
		XmlConverter resourceConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEntityMappings.getConverters().add(0, resourceConverter2);
		resourceConverter2.setClassName("Foo2");
		resourceConverter2.setName("myConverter2");
		
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(2, resourceEntityMappings.getConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, resourceEntityMappings.getConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, resourceEntityMappings.getConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getConverters().remove(resourceConverter2);
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertFalse(ormContextConverterHolder.getCustomConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}

	public void testModifyCustomConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertEquals(0, resourceEntityMappings.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//add a converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter = ormContextConverterHolder.addCustomConverter("myConverter", 0);
		contextConverter.setConverterClass("Foo");
		
		assertEquals(1, resourceEntityMappings.getConverters().size());
		assertEquals("Foo", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceEntityMappings.getConverters().get(0)).getName());
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ListIterator<? extends EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//add another converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter2 = ormContextConverterHolder.addCustomConverter("myConverter2", 0);
		contextConverter2.setConverterClass("Foo2");
		
		assertEquals(2, resourceEntityMappings.getConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceEntityMappings.getConverters().get(0)).getName());
		assertEquals("Foo", resourceEntityMappings.getConverters().get(1).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceEntityMappings.getConverters().get(1)).getName());
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveCustomConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getConverters().size());
		assertEquals("Foo", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceEntityMappings.getConverters().get(0)).getName());
		assertEquals("Foo2", resourceEntityMappings.getConverters().get(1).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceEntityMappings.getConverters().get(1)).getName());
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(0);
		
		assertEquals(1, resourceEntityMappings.getConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceEntityMappings.getConverters().get(0)).getName());
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(contextConverter2);
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertFalse(ormContextConverterHolder.getCustomConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}

	public void testMorphEclipseLinkConvertersAndJpa2_1Converters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();

		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertEquals(0, resourceEntityMappings.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));		

		//add a converter to the resource model, check context model
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEntityMappings.getConverters().add(resourceConverter);
		resourceConverter.setClassName("model.FooConverter");

		//no 'name' set so the converter is a JPA 2.1 converter
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		OrmConverterType2_1 ormConverterType2_1 = ((EntityMappings2_1) contextEntityMappings).getConverterTypes().iterator().next();
		assertEquals("FooConverter", ormConverterType2_1.getSimpleName());
		assertEquals("model.FooConverter", ormConverterType2_1.getName());

		resourceConverter.setName("myConverter");
		//'name' now set so the converter is an EclipseLink converter
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ListIterator<? extends EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("model.FooConverter", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, resourceEntityMappings.getConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		assertEquals(0, IterableTools.size(((EntityMappings2_1) contextEntityMappings).getConverterTypes()));

		resourceConverter.setName(null);
		//no 'name' set so the converter is a JPA 2.1 converter
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		ormConverterType2_1 = ((EntityMappings2_1) contextEntityMappings).getConverterTypes().iterator().next();
		assertEquals("FooConverter", ormConverterType2_1.getSimpleName());
		assertEquals("model.FooConverter", ormConverterType2_1.getName());
	}

	public void testUpdateTypeConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//add a converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceEntityMappings.getTypeConverters().add(resourceTypeConverter);
		resourceTypeConverter.setDataType("Foo");
		resourceTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ListIterator<? extends EclipseLinkTypeConverter> ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		EclipseLinkTypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));	
	
		//add another converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceEntityMappings.getTypeConverters().add(0, resourceTypeConverter2);
		resourceTypeConverter2.setDataType("Foo2");
		resourceTypeConverter2.setName("myTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getTypeConverters().remove(resourceTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getTypeConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));	
	}

	public void testModifyTypeConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkTypeConverter contextTypeConverter = ormContextConverterHolder.addTypeConverter("myTypeConverter", 0);
		contextTypeConverter.setDataType("Foo");
		
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ListIterator<? extends EclipseLinkTypeConverter> ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		EclipseLinkTypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkTypeConverter contextTypeConverter2 = ormContextConverterHolder.addTypeConverter("myTypeConverter2", 0);
		contextTypeConverter2.setDataType("Foo2");
		
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEntityMappings.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter", resourceEntityMappings.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveTypeConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEntityMappings.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter2", resourceEntityMappings.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(0);
		
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(contextTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getTypeConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
	
	public void testUpdateObjectTypeConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//add a converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceEntityMappings.getObjectTypeConverters().add(resourceObjectTypeConverter);
		resourceObjectTypeConverter.setDataType("Foo");
		resourceObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ListIterator<? extends EclipseLinkObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		EclipseLinkObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//add another converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceEntityMappings.getObjectTypeConverters().add(0, resourceObjectTypeConverter2);
		resourceObjectTypeConverter2.setDataType("Foo2");
		resourceObjectTypeConverter2.setName("myObjectTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getObjectTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getObjectTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getObjectTypeConverters().remove(resourceObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getObjectTypeConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));	
	}

	public void testModifyObjectTypeConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkObjectTypeConverter contextObjectTypeConverter = ormContextConverterHolder.addObjectTypeConverter("myObjectTypeConverter", 0);
		contextObjectTypeConverter.setDataType("Foo");
		contextObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ListIterator<? extends EclipseLinkObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		EclipseLinkObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkObjectTypeConverter contextObjectTypeConverter2 = ormContextConverterHolder.addObjectTypeConverter("myObjectTypeConverter2", 0);
		contextObjectTypeConverter2.setDataType("Foo2");
		contextObjectTypeConverter2.setName("myObjectTypeConverter2");
		
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEntityMappings.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntityMappings.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveObjectTypeConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEntityMappings.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntityMappings.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(0);
		
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(contextObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getObjectTypeConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
	
	public void testUpdateStructConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//add a converter to the resource model, check context model
		XmlStructConverter resourceStructConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceEntityMappings.getStructConverters().add(resourceStructConverter);
		resourceStructConverter.setConverter("Foo");
		resourceStructConverter.setName("myStructConverter");
		
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ListIterator<? extends EclipseLinkStructConverter> ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		EclipseLinkStructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, resourceEntityMappings.getStructConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//add another converter to the resource model, check context model
		XmlStructConverter resourceStructConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceEntityMappings.getStructConverters().add(0, resourceStructConverter2);
		resourceStructConverter2.setConverter("Foo2");
		resourceStructConverter2.setName("myStructConverter2");
		
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getStructConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getStructConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, resourceEntityMappings.getStructConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));	
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getStructConverters().remove(resourceStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertFalse(ormContextConverterHolder.getStructConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));	
	}

	public void testModifyStructConverters() throws Exception {
		XmlEntityMappings resourceEntityMappings = getXmlEntityMappings();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		EclipseLinkEntityMappings contextEntityMappings = getEntityMappings();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = contextEntityMappings.getConverterContainer();
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkStructConverter contextStructConverter = ormContextConverterHolder.addStructConverter("myStructConverter", 0);
		contextStructConverter.setConverterClass("Foo");
		
		assertEquals(1, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ListIterator<? extends EclipseLinkStructConverter> ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		EclipseLinkStructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkStructConverter contextStructConverter2 = ormContextConverterHolder.addStructConverter("myStructConverter2", 0);
		contextStructConverter2.setConverterClass("Foo2");
		
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals("Foo", resourceEntityMappings.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter", resourceEntityMappings.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveStructConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals("Foo2", resourceEntityMappings.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter2", resourceEntityMappings.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(0);
		
		assertEquals(1, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(contextStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertFalse(ormContextConverterHolder.getStructConverters().iterator().hasNext());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
}