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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.EntityMappings2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_5ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_5OrmEntityTests
	extends EclipseLink2_5ContextModelTestCase
{

	public EclipseLink2_5OrmEntityTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntityForConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}

	public void testUpdateCustomConverters() throws Exception {
		createTestEntityForConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmEntity ormContextEntity = (EclipseLinkOrmEntity) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextEntity.getConverterContainer();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);

		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));

		//add a converter to the resource model, check context model
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEntity.getConverters().add(resourceConverter);
		resourceConverter.setClassName("Foo");
		resourceConverter.setName("myConverter");

		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ListIterator<? extends EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));

		//add another converter to the resource model, check context model
		XmlConverter resourceConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEntity.getConverters().add(0, resourceConverter2);
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
		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));

		//move a converter in the resource model, check context model
		resourceEntity.getConverters().move(0, 1);

		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));

		//remove a converter from the resource model, check context model
		resourceEntity.getConverters().remove(0);

		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));

		//remove a converter from the resource model, check context model
		resourceEntity.getConverters().remove(resourceConverter2);

		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertFalse(ormContextConverterHolder.getCustomConverters().iterator().hasNext());
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}

	public void testModifyCustomConverters() throws Exception {
		createTestEntityForConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmEntity ormContextEntity = (EclipseLinkOrmEntity) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextEntity.getConverterContainer();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);

		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));

		//add a converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter = ormContextConverterHolder.addCustomConverter("myConverter", 0);
		contextConverter.setConverterClass("Foo");

		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals("Foo", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceEntity.getConverters().get(0)).getName());
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ListIterator<? extends EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));

		//even with the name set to null this should still be an EclipseLink converter at the Entity level
		contextConverter.setName(null);
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals(null, ormContextConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		assertEquals(0, IterableTools.size(((EntityMappings2_1) getEntityMappings()).getConverterTypes()));

		contextConverter.setName("myConverter");

		//add another converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter2 = ormContextConverterHolder.addCustomConverter("myConverter2", 0);
		contextConverter2.setConverterClass("Foo2");

		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals("Foo2", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceEntity.getConverters().get(0)).getName());
		assertEquals("Foo", resourceEntity.getConverters().get(1).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceEntity.getConverters().get(1)).getName());
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

		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals("Foo", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceEntity.getConverters().get(0)).getName());
		assertEquals("Foo2", resourceEntity.getConverters().get(1).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceEntity.getConverters().get(1)).getName());
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

		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals("Foo2", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceEntity.getConverters().get(0)).getName());
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
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
}
