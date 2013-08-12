/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTransient;
import org.eclipse.jpt.jpa.core.resource.orm.XmlVersion;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmPersistentTypeTests extends ContextModelTestCase
{
	public static final String MODEL_TYPE_NAME = "Model";
	public static final String FULLY_QUALIFIED_MODEL_TYPE_NAME = PACKAGE_NAME + "." + MODEL_TYPE_NAME;
	
	public static final String EMPLOYEE_TYPE_NAME = "Employee";
	public static final String FULLY_QUALIFIED_EMPLOYEE_TYPE_NAME = PACKAGE_NAME + "." + EMPLOYEE_TYPE_NAME;

	
	public GenericOrmPersistentTypeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}	
	
	private void createModelType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public abstract class ").append(MODEL_TYPE_NAME).append(" {");
				sb.append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    ");
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, MODEL_TYPE_NAME + ".java", sourceWriter);
	}
	
	private void createEmployeeType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append(EMPLOYEE_TYPE_NAME).append(" extends ").append(MODEL_TYPE_NAME).append(" {");
				sb.append(CR);
				sb.append("    private String department;").append(CR);
				sb.append(CR);
				sb.append("    private java.util.Date startDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, EMPLOYEE_TYPE_NAME + ".java", sourceWriter);
	}
	
//	public void testUpdateXmlTypeMapping() throws Exception {
//		assertFalse(entityMappings().ormPersistentTypes().hasNext());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		
//		//add embeddable in the resource model, verify context model updated
//		Embeddable embeddable = OrmFactory.eINSTANCE.createEmbeddable();
//		ormResource().getEntityMappings().getEmbeddables().add(embeddable);
//		embeddable.setClassName("model.Foo");
//		assertTrue(entityMappings().ormPersistentTypes().hasNext());
//		assertEquals("model.Foo", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertTrue(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo", ormResource().getEntityMappings().getEmbeddables().get(0).getClassName());
//		
//		//add entity in the resource model, verify context model updated
//		Entity entity = OrmFactory.eINSTANCE.createEntity();
//		ormResource().getEntityMappings().getEntities().add(entity);
//		entity.setClassName("model.Foo2");
//		assertTrue(entityMappings().ormPersistentTypes().hasNext());
//		assertEquals("model.Foo2", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
//		assertTrue(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo2", ormResource().getEntityMappings().getEntities().get(0).getClassName());
//
//		//add mapped-superclass in the resource model, verify context model updated
//		MappedSuperclass mappedSuperclass = OrmFactory.eINSTANCE.createMappedSuperclass();
//		ormResource().getEntityMappings().getMappedSuperclasses().add(mappedSuperclass);
//		mappedSuperclass.setClassName("model.Foo3");
//		assertTrue(entityMappings().ormPersistentTypes().hasNext());
//		assertEquals("model.Foo3", entityMappings().ormPersistentTypes().next().getMapping().getClass_());
//		assertFalse(ormResource().getEntityMappings().getMappedSuperclasses().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEntities().isEmpty());
//		assertFalse(ormResource().getEntityMappings().getEmbeddables().isEmpty());
//		assertEquals("model.Foo3", ormResource().getEntityMappings().getMappedSuperclasses().get(0).getClassName());
//	}
//	
	
	public void testMorphXmlTypeMapping() throws Exception {
		assertFalse(getEntityMappings().getPersistentTypes().iterator().hasNext());
		assertTrue(getXmlEntityMappings().getMappedSuperclasses().isEmpty());
		assertTrue(getXmlEntityMappings().getEntities().isEmpty());
		assertTrue(getXmlEntityMappings().getEmbeddables().isEmpty());
		
		OrmPersistentType embeddablePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		OrmPersistentType mappedSuperclassPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo3");
	
		OrmPersistentType ormPersistentType = getEntityMappings().getPersistentTypes().iterator().next();
		assertEquals(mappedSuperclassPersistentType, ormPersistentType);
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, ormPersistentType.getMapping().getKey());
	
		ormPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertEquals(0, getXmlEntityMappings().getMappedSuperclasses().size());
		assertEquals(1, getXmlEntityMappings().getEntities().size());
		assertEquals(2, getXmlEntityMappings().getEmbeddables().size());
		
		Iterator<OrmPersistentType> ormPersistentTypes = getEntityMappings().getPersistentTypes().iterator();
		//the same OrmPersistentTypes should still be in the context model
		assertEquals(ormPersistentTypes.next(), entityPersistentType);
		assertEquals(ormPersistentTypes.next(), embeddablePersistentType);
		assertEquals(ormPersistentTypes.next(), mappedSuperclassPersistentType);
		
		assertEquals("model.Foo", getXmlEntityMappings().getEmbeddables().get(0).getClassName());
		assertEquals("model.Foo3", getXmlEntityMappings().getEmbeddables().get(1).getClassName());
	}
	
	public void testRemoveId() throws Exception {
		createTestType();
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityPersistentType.addAttributeToXml(entityPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("id",  entity.getAttributes().getIds().get(0).getName());

		((OrmSpecifiedPersistentAttribute) entityPersistentType.getAttributeNamed("id")).removeFromXml();
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveBasic() throws Exception {
		createTestType();
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityPersistentType.addAttributeToXml(entityPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("id",  entity.getAttributes().getBasics().get(0).getName());

		((OrmSpecifiedPersistentAttribute) entityPersistentType.getAttributeNamed("id")).removeFromXml();
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveVersion() throws Exception {
		createTestType();
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityPersistentType.addAttributeToXml(entityPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("id",  entity.getAttributes().getVersions().get(0).getName());

		((OrmSpecifiedPersistentAttribute) entityPersistentType.getAttributeNamed("id")).removeFromXml();
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveEmbedded() throws Exception {
		createTestType();
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityPersistentType.addAttributeToXml(entityPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("id",  entity.getAttributes().getEmbeddeds().get(0).getName());

		((OrmSpecifiedPersistentAttribute) entityPersistentType.getAttributeNamed("id")).removeFromXml();
		assertNull(entity.getAttributes());
	}
	
	public void testRemoveTransient() throws Exception {
		createTestType();
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityPersistentType.addAttributeToXml(entityPersistentType.getAttributeNamed("id"), MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("id",  entity.getAttributes().getTransients().get(0).getName());

		((OrmSpecifiedPersistentAttribute) entityPersistentType.getAttributeNamed("id")).removeFromXml();
		assertNull(entity.getAttributes());
	}

	public void testUpdateSpecifiedPersistentAttributes() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);

		entity.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		XmlBasic basic = OrmFactory.eINSTANCE.createXmlBasic();
		entity.getAttributes().getBasics().add(basic);
		basic.setName("basicAttribute");
			
		OrmPersistentAttribute ormPersistentAttribute = entityPersistentType.getAttributes().iterator().next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		
		XmlEmbedded embedded = OrmFactory.eINSTANCE.createXmlEmbedded();
		entity.getAttributes().getEmbeddeds().add(embedded);
		embedded.setName("embeddedAttribute");
		
		Iterator<OrmPersistentAttribute> attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
	
		XmlVersion version = OrmFactory.eINSTANCE.createXmlVersion();
		entity.getAttributes().getVersions().add(version);
		version.setName("versionAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		XmlId id = OrmFactory.eINSTANCE.createXmlId();
		entity.getAttributes().getIds().add(id);
		id.setName("idAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		XmlTransient transientResource = OrmFactory.eINSTANCE.createXmlTransient();
		entity.getAttributes().getTransients().add(transientResource);
		transientResource.setName("transientAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		XmlManyToOne manyToOneResource = OrmFactory.eINSTANCE.createXmlManyToOne();
		entity.getAttributes().getManyToOnes().add(manyToOneResource);
		manyToOneResource.setName("manyToOneAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		XmlManyToMany manyToManyResource = OrmFactory.eINSTANCE.createXmlManyToMany();
		entity.getAttributes().getManyToManys().add(manyToManyResource);
		manyToManyResource.setName("manyToManyAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());
		
		XmlOneToMany oneToManyResource = OrmFactory.eINSTANCE.createXmlOneToMany();
		entity.getAttributes().getOneToManys().add(oneToManyResource);
		oneToManyResource.setName("oneToManyAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		XmlOneToOne oneToOneResource = OrmFactory.eINSTANCE.createXmlOneToOne();
		entity.getAttributes().getOneToOnes().add(oneToOneResource);
		oneToOneResource.setName("oneToOneAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());


		XmlEmbeddedId embeddedIdResource = OrmFactory.eINSTANCE.createXmlEmbeddedId();
		entity.getAttributes().getEmbeddedIds().add(embeddedIdResource);
		embeddedIdResource.setName("embeddedIdAttribute");
		
		attributes = entityPersistentType.getAttributes().iterator();
		ormPersistentAttribute = attributes.next();
		assertEquals("idAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedIdAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("basicAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("versionAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("oneToOneAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("manyToManyAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("embeddedAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		ormPersistentAttribute = attributes.next();
		assertEquals("transientAttribute", ormPersistentAttribute.getName());
		assertEquals(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMapping().getKey());
		assertFalse(attributes.hasNext());

		entity.getAttributes().getBasics().remove(0);
		entity.getAttributes().getEmbeddeds().remove(0);
		entity.getAttributes().getTransients().remove(0);
		entity.getAttributes().getIds().remove(0);
		entity.getAttributes().getVersions().remove(0);
		entity.getAttributes().getManyToOnes().remove(0);
		entity.getAttributes().getManyToManys().remove(0);
		entity.getAttributes().getOneToManys().remove(0);
		entity.getAttributes().getOneToOnes().remove(0);
		entity.getAttributes().getEmbeddedIds().remove(0);
		assertFalse(entityPersistentType.getAttributes().iterator().hasNext());
		assertNotNull(entity.getAttributes());
	}

	public void testInheritedAttributesResolve() throws Exception {
		createModelType();
		createEmployeeType();
		
		OrmPersistentType employeePersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_EMPLOYEE_TYPE_NAME);

		
		OrmSpecifiedPersistentAttribute attribute = employeePersistentType.addAttributeToXml(employeePersistentType.getAttributeNamed("startDate"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		attribute.getMapping().setName("id");
		attribute = employeePersistentType.addAttributeToXml(employeePersistentType.getAttributeNamed("startDate"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		attribute.getMapping().setName("name");
		OrmSpecifiedPersistentAttribute startDateAttribute = employeePersistentType.addAttributeToXml(employeePersistentType.getAttributeNamed("startDate"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		((OrmBasicMapping) startDateAttribute.getMapping()).setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) ((OrmBasicMapping) startDateAttribute.getMapping()).getConverter()).setTemporalType(TemporalType.DATE);
		
		OrmPersistentAttribute idAttribute = employeePersistentType.getAttributeNamed("id");
		JavaSpecifiedPersistentAttribute javaPersistentAttribute = idAttribute.getJavaPersistentAttribute();
		assertNotNull(javaPersistentAttribute);
		assertEquals("id", javaPersistentAttribute.getName());
		assertEquals("test.Employee", javaPersistentAttribute.getDeclaringPersistentType().getName());
		assertEquals("test.Model", javaPersistentAttribute.getResourceAttribute().getParent().getTypeBinding().getQualifiedName());
	}
}