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

import java.util.ListIterator;
import org.eclipse.jpt.eclipselink.core.context.Converter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.ConverterHolder;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;

@SuppressWarnings("nls")
public class EclipseLinkEntityMappingsTests extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkEntityMappingsTests(String name) {
		super(name);
	}

	
	public void testUpdateConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.convertersSize());
		assertEquals(0, resourceEntityMappings.getConverters().size());
		
		//add a converter to the resource model, check context model
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverterImpl();
		resourceEntityMappings.getConverters().add(resourceConverter);
		resourceConverter.setClassName("Foo");
		resourceConverter.setName("myConverter");
		
		assertEquals(1, ormContextConverterHolder.convertersSize());
		ListIterator<Converter> ormContextConverters = ormContextConverterHolder.converters();
		Converter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, resourceEntityMappings.getConverters().size());

		//add another converter to the resource model, check context model
		XmlConverter resourceConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlConverterImpl();
		resourceEntityMappings.getConverters().add(0, resourceConverter2);
		resourceConverter2.setClassName("Foo2");
		resourceConverter2.setName("myConverter2");
		
		assertEquals(2, ormContextConverterHolder.convertersSize());
		ormContextConverters = ormContextConverterHolder.converters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(2, resourceEntityMappings.getConverters().size());
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.convertersSize());
		ormContextConverters = ormContextConverterHolder.converters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, resourceEntityMappings.getConverters().size());
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.convertersSize());
		ormContextConverters = ormContextConverterHolder.converters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, resourceEntityMappings.getConverters().size());
		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getConverters().remove(resourceConverter2);
		
		assertEquals(0, ormContextConverterHolder.convertersSize());
		assertFalse(ormContextConverterHolder.converters().hasNext());
		assertEquals(0, resourceEntityMappings.getConverters().size());
	}

	public void testModifyConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.convertersSize());
		assertEquals(0, resourceEntityMappings.getConverters().size());
		
		//add a converter to the context model, check resource model
		Converter contextConverter = ormContextConverterHolder.addConverter(0);
		contextConverter.setConverterClass("Foo");
		contextConverter.setName("myConverter");
		
		assertEquals(1, resourceEntityMappings.getConverters().size());
		assertEquals("Foo", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter", resourceEntityMappings.getConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.convertersSize());
		ListIterator<Converter> ormContextConverters = ormContextConverterHolder.converters();
		Converter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
	
		//add another converter to the context model, check resource model
		Converter contextConverter2 = ormContextConverterHolder.addConverter(0);
		contextConverter2.setConverterClass("Foo2");
		contextConverter2.setName("myConverter2");
		
		assertEquals(2, resourceEntityMappings.getConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter2", resourceEntityMappings.getConverters().get(0).getName());
		assertEquals("Foo", resourceEntityMappings.getConverters().get(1).getClassName());
		assertEquals("myConverter", resourceEntityMappings.getConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.convertersSize());
		ormContextConverters = ormContextConverterHolder.converters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
	
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getConverters().size());
		assertEquals("Foo", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter", resourceEntityMappings.getConverters().get(0).getName());
		assertEquals("Foo2", resourceEntityMappings.getConverters().get(1).getClassName());
		assertEquals("myConverter2", resourceEntityMappings.getConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.convertersSize());
		ormContextConverters = ormContextConverterHolder.converters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeConverter(0);
		
		assertEquals(1, resourceEntityMappings.getConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getConverters().get(0).getClassName());
		assertEquals("myConverter2", resourceEntityMappings.getConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.convertersSize());
		ormContextConverters = ormContextConverterHolder.converters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeConverter(contextConverter2);
		
		assertEquals(0, ormContextConverterHolder.convertersSize());
		assertFalse(ormContextConverterHolder.converters().hasNext());
		assertEquals(0, resourceEntityMappings.getConverters().size());
	}
	
	public void testUpdateTypeConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
		
		//add a converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverterImpl();
		resourceEntityMappings.getTypeConverters().add(resourceTypeConverter);
		resourceTypeConverter.setDataType("Foo");
		resourceTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ListIterator<TypeConverter> ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		TypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());

		//add another converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverterImpl();
		resourceEntityMappings.getTypeConverters().add(0, resourceTypeConverter2);
		resourceTypeConverter2.setDataType("Foo2");
		resourceTypeConverter2.setName("myTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());
		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getTypeConverters().remove(resourceTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertFalse(ormContextConverterHolder.typeConverters().hasNext());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
	}

	public void testModifyTypeConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
		
		//add a converter to the context model, check resource model
		TypeConverter contextTypeConverter = ormContextConverterHolder.addTypeConverter(0);
		contextTypeConverter.setDataType("Foo");
		contextTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ListIterator<TypeConverter> ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		TypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
	
		//add another converter to the context model, check resource model
		TypeConverter contextTypeConverter2 = ormContextConverterHolder.addTypeConverter(0);
		contextTypeConverter2.setDataType("Foo2");
		contextTypeConverter2.setName("myTypeConverter2");
		
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEntityMappings.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter", resourceEntityMappings.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
	
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveTypeConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEntityMappings.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter2", resourceEntityMappings.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(0);
		
		assertEquals(1, resourceEntityMappings.getTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEntityMappings.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(contextTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertFalse(ormContextConverterHolder.typeConverters().hasNext());
		assertEquals(0, resourceEntityMappings.getTypeConverters().size());
	}
	
	public void testUpdateObjectTypeConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
		
		//add a converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverterImpl();
		resourceEntityMappings.getObjectTypeConverters().add(resourceObjectTypeConverter);
		resourceObjectTypeConverter.setDataType("Foo");
		resourceObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ListIterator<ObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());

		//add another converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverterImpl();
		resourceEntityMappings.getObjectTypeConverters().add(0, resourceObjectTypeConverter2);
		resourceObjectTypeConverter2.setDataType("Foo2");
		resourceObjectTypeConverter2.setName("myObjectTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getObjectTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getObjectTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());
		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getObjectTypeConverters().remove(resourceObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.objectTypeConverters().hasNext());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
	}

	public void testModifyObjectTypeConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
		
		//add a converter to the context model, check resource model
		ObjectTypeConverter contextObjectTypeConverter = ormContextConverterHolder.addObjectTypeConverter(0);
		contextObjectTypeConverter.setDataType("Foo");
		contextObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ListIterator<ObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
	
		//add another converter to the context model, check resource model
		ObjectTypeConverter contextObjectTypeConverter2 = ormContextConverterHolder.addObjectTypeConverter(0);
		contextObjectTypeConverter2.setDataType("Foo2");
		contextObjectTypeConverter2.setName("myObjectTypeConverter2");
		
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEntityMappings.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntityMappings.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
	
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveObjectTypeConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEntityMappings.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntityMappings.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(0);
		
		assertEquals(1, resourceEntityMappings.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntityMappings.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(contextObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.objectTypeConverters().hasNext());
		assertEquals(0, resourceEntityMappings.getObjectTypeConverters().size());
	}
	
	public void testUpdateStructConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
		
		//add a converter to the resource model, check context model
		XmlStructConverter resourceStructConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverterImpl();
		resourceEntityMappings.getStructConverters().add(resourceStructConverter);
		resourceStructConverter.setConverter("Foo");
		resourceStructConverter.setName("myStructConverter");
		
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ListIterator<StructConverter> ormContextStructConverters = ormContextConverterHolder.structConverters();
		StructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, resourceEntityMappings.getStructConverters().size());

		//add another converter to the resource model, check context model
		XmlStructConverter resourceStructConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverterImpl();
		resourceEntityMappings.getStructConverters().add(0, resourceStructConverter2);
		resourceStructConverter2.setConverter("Foo2");
		resourceStructConverter2.setName("myStructConverter2");
		
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		
		//move a converter in the resource model, check context model
		resourceEntityMappings.getStructConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getStructConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, resourceEntityMappings.getStructConverters().size());
		
		
		//remove a converter from the resource model, check context model
		resourceEntityMappings.getStructConverters().remove(resourceStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertFalse(ormContextConverterHolder.structConverters().hasNext());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
	}

	public void testModifyStructConverters() throws Exception {
		EclipseLinkEntityMappings contextEntityMappings = entityMappings();
		ConverterHolder ormContextConverterHolder = contextEntityMappings.getConverterHolder();
		XmlEntityMappings resourceEntityMappings = ormResource().getEntityMappings();
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
		
		//add a converter to the context model, check resource model
		StructConverter contextStructConverter = ormContextConverterHolder.addStructConverter(0);
		contextStructConverter.setConverterClass("Foo");
		contextStructConverter.setName("myStructConverter");
		
		assertEquals(1, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ListIterator<StructConverter> ormContextStructConverters = ormContextConverterHolder.structConverters();
		StructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
	
		//add another converter to the context model, check resource model
		StructConverter contextStructConverter2 = ormContextConverterHolder.addStructConverter(0);
		contextStructConverter2.setConverterClass("Foo2");
		contextStructConverter2.setName("myStructConverter2");
		
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals("Foo", resourceEntityMappings.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter", resourceEntityMappings.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
	
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveStructConverter(0, 1);
		
		assertEquals(2, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals("Foo2", resourceEntityMappings.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter2", resourceEntityMappings.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(0);
		
		assertEquals(1, resourceEntityMappings.getStructConverters().size());
		assertEquals("Foo2", resourceEntityMappings.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEntityMappings.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(contextStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertFalse(ormContextConverterHolder.structConverters().hasNext());
		assertEquals(0, resourceEntityMappings.getStructConverters().size());
	}
}