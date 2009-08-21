/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class Generic2_0JavaEntityTests extends ContextModelTestCase
{
	private static final String ENTITY_NAME = "entityName";
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String DISCRIMINATOR_VALUE = "MY_DISCRIMINATOR_VALUE";
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;
	
	
	public Generic2_0JavaEntityTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private void createTestAbstractEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public abstract class ").append(TYPE_NAME).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, FILE_NAME, sourceWriter);
	}

	private ICompilationUnit createTestEntityAnnotationOnProperty() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("private String foo;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany");
				sb.append(CR);
				sb.append("    private int address3;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany");
				sb.append(CR);
				sb.append("    private int address4;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
	
	private ICompilationUnit createTestAbstractEntityTablePerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)");
				sb.append("abstract");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("private String foo;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany");
				sb.append(CR);
				sb.append("    private int address3;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany");
				sb.append(CR);
				sb.append("    private int address4;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	private ICompilationUnit createTestEntityWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity(name=\"" + ENTITY_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Table(name=\"" + TABLE_NAME + "\")");
			}
		});
	}

	private void createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}

	private ICompilationUnit createTestEntityWithInheritance() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithDiscriminatorValue() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@DiscriminatorValue(value=\"" + DISCRIMINATOR_VALUE + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithSecondaryTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@SecondaryTable(name=\"foo\")");
			}
		});
	}

	private ICompilationUnit createTestEntityWithSecondaryTables() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@SecondaryTables({@SecondaryTable(name=\"foo\"), @SecondaryTable(name=\"bar\")})");
			}
		});
	}

	private ICompilationUnit createAbstractTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)").append(CR);
				sb.append("abstract");
			}
		});
	}

	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = (Entity) getJavaPersistentType().getMapping();
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
	}
	
	public void testOverridableAttributes() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<JavaPersistentAttribute> overridableAttributes = getJavaEntity().overridableAttributes();
		assertFalse(overridableAttributes.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = getJavaEntity().overridableAttributes();		
		assertEquals("id", overridableAttributes.next().getName());
		assertEquals("name", overridableAttributes.next().getName());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = getJavaEntity().overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = getJavaEntity().overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testAllOverridableAttributes() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<PersistentAttribute> overridableAttributes = getJavaEntity().allOverridableAttributes();
		assertEquals("id", overridableAttributes.next().getName());
		assertEquals("name", overridableAttributes.next().getName());
		assertEquals("foo", overridableAttributes.next().getName());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAttributesTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<PersistentAttribute> overridableAttributes = getJavaEntity().allOverridableAttributes();
		assertEquals("id", overridableAttributes.next().getName());
		assertEquals("name", overridableAttributes.next().getName());
		assertEquals("foo", overridableAttributes.next().getName());
		assertFalse(overridableAttributes.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAttributes = abstractEntity.allOverridableAttributes();
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAssociationsTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<PersistentAttribute> overridableAssociations = getJavaEntity().allOverridableAssociations();
		assertEquals("address", overridableAssociations.next().getName());
		assertEquals("address2", overridableAssociations.next().getName());
		assertEquals("address3", overridableAssociations.next().getName());
		assertEquals("address4", overridableAssociations.next().getName());
		assertFalse(overridableAssociations.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAssociations = abstractEntity.allOverridableAssociations();
		assertFalse(overridableAssociations.hasNext());
	}

	public void testAllOverridableAttributesMappedSuperclassInOrmXml() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<PersistentAttribute> overridableAttributes = getJavaEntity().allOverridableAttributes();
		assertEquals("id", overridableAttributes.next().getName());
		assertEquals("name", overridableAttributes.next().getName());
		assertEquals("foo", overridableAttributes.next().getName());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = getJavaEntity().allOverridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertEquals("foo", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		specifiedAttributeOverrides = getJavaEntity().specifiedAttributeOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, javaEntity.virtualAttributeOverridesSize());
		AttributeOverride virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(3, javaEntity.virtualAttributeOverridesSize());
		virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(2, javaEntity.virtualAttributeOverridesSize());
	}
	
	public void testDefaultAttributeOverridesEntityHierachy() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, javaEntity.virtualAttributeOverridesSize());
		AttributeOverride virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		JavaEntity superclass = (JavaEntity) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(3, javaEntity.virtualAttributeOverridesSize());
		virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = javaEntity.virtualAttributeOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(2, javaEntity.virtualAttributeOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, getJavaEntity().specifiedAttributeOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");

		assertEquals(2, getJavaEntity().specifiedAttributeOverridesSize());
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = getJavaEntity();
		
		assertEquals(3, javaEntity.virtualAttributeOverridesSize());

		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(2, javaEntity.virtualAttributeOverridesSize());
		
		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(1, javaEntity.virtualAttributeOverridesSize());
		
		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(0, javaEntity.virtualAttributeOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = getJavaEntity();
		
		assertEquals(3, javaEntity.attributeOverridesSize());

		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(3, javaEntity.attributeOverridesSize());
		
		javaEntity.virtualAttributeOverrides().next().setVirtual(false);
		assertEquals(3, javaEntity.attributeOverridesSize());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		annotation.setName("bar");	
		assertEquals(4, javaEntity.attributeOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAttributeOverride> virtualAttributeOverrides = getJavaEntity().virtualAttributeOverrides();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().setVirtual(false);
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(3, CollectionTools.size(typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)));

		getJavaEntity().specifiedAttributeOverrides().next().setVirtual(true);
		
		Iterator<NestableAnnotation> attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<JavaAttributeOverride> attributeOverrides = getJavaEntity().specifiedAttributeOverrides();
		assertEquals("name", attributeOverrides.next().getName());		
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		getJavaEntity().specifiedAttributeOverrides().next().setVirtual(true);
		attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = getJavaEntity().specifiedAttributeOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		getJavaEntity().specifiedAttributeOverrides().next().setVirtual(true);
		attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = getJavaEntity().specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());

		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);

		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		Iterator<NestableAnnotation> javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaAttributeOverrides));
		
		
		entity.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<AttributeOverride> attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());


		entity.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
	}
//	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AttributeOverrideAnnotation) typeResource.addAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AttributeOverrideAnnotation) typeResource.addAnnotation(1, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AttributeOverrideAnnotation) typeResource.addAnnotation(2, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<AttributeOverride> attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.moveAnnotation(2, 0, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.moveAnnotation(0, 1, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		attributeOverrides = entity.specifiedAttributeOverrides();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAttributeOverride> virtualAttributeOverrides = getJavaEntity().virtualAttributeOverrides();	
		AttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());

		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());

		getJavaEntity().virtualAttributeOverrides().next().setVirtual(false);
		AttributeOverride specifiedAttributeOverride = getJavaEntity().specifiedAttributeOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = getJavaEntity().virtualAttributeOverrides();	
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());
	}
	
	
	public void testOverridableAssociations() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> overridableAssociations = getJavaEntity().overridableAssociations();
		assertFalse(overridableAssociations.hasNext());
	}

	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
//	//TODO add all mapping types to the mapped superclass to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().allOverridableAssociationNames();
		assertEquals("address", overridableAssociationNames.next());
		assertEquals("address2", overridableAssociationNames.next());
		assertEquals("address3", overridableAssociationNames.next());
		assertEquals("address4", overridableAssociationNames.next());
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAllOverridableAssociations() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<PersistentAttribute> overridableAssociations = getJavaEntity().allOverridableAssociations();
		assertEquals("address", overridableAssociations.next().getName());
		assertEquals("address2", overridableAssociations.next().getName());
		assertEquals("address3", overridableAssociations.next().getName());
		assertEquals("address4", overridableAssociations.next().getName());
		assertFalse(overridableAssociations.hasNext());
	}
	
	public void testAllOverridableAssociationsMappedSuperclassInOrmXml() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<PersistentAttribute> overridableAssociations = getJavaEntity().allOverridableAssociations();
		assertEquals("address", overridableAssociations.next().getName());
		assertEquals("address2", overridableAssociations.next().getName());
		assertEquals("address3", overridableAssociations.next().getName());
		assertEquals("address4", overridableAssociations.next().getName());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAZ");
		specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		specifiedAssociationOverrides = getJavaEntity().specifiedAssociationOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testDefaultAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, javaEntity.virtualAssociationOverridesSize());
		AssociationOverride virtualAssociationOverride = javaEntity.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		

		//MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		//BasicMapping idMapping = (BasicMapping) mappedSuperclass.persistentType().attributeNamed("id").getMapping();
		//idMapping.getColumn().setSpecifiedName("FOO");
		//idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(4, javaEntity.virtualAssociationOverridesSize());
		virtualAssociationOverride = javaEntity.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());

		//idMapping.getColumn().setSpecifiedName(null);
		//idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = javaEntity.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride = virtualAssociationOverride.setVirtual(false);
		assertEquals(3, javaEntity.virtualAssociationOverridesSize());
		
		
		
//		//TODO joinColumns for default association overrides
////	IJoinColumn defaultJoinColumn = defaultAssociationOverride.joinColumns().next();
////	assertEquals("address", defaultJoinColumn.getName());
////	assertEquals("address", defaultJoinColumn.getReferencedColumnName());
////	assertEquals(SUB_TYPE_NAME, defaultJoinColumn.getTable());
////	
////
////	IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
////	
////	IOneToOneMapping addressMapping = (IOneToOneMapping) mappedSuperclass.persistentType().attributeNamed("address").getMapping();
////	IJoinColumn joinColumn = addressMapping.addSpecifiedJoinColumn(0);
////	joinColumn.setSpecifiedName("FOO");
////	joinColumn.setSpecifiedReferencedColumnName("BAR");
////	joinColumn.setSpecifiedTable("BAZ");
////	
////	assertEquals(SUB_TYPE_NAME, typeResource.getName());
////	assertNull(typeResource.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(typeResource.annotation(AssociationOverrides.ANNOTATION_NAME));
////
////	assertEquals(1, CollectionTools.size(javaEntity.defaultAssociationOverrides()));
////	defaultAssociationOverride = javaEntity.defaultAssociationOverrides().next();
////	assertEquals("address", defaultAssociationOverride.getName());
////	assertEquals("FOO", defaultJoinColumn.getName());
////	assertEquals("BAR", defaultJoinColumn.getReferencedColumnName());
////	assertEquals("BAZ", defaultJoinColumn.getTable());
////
////	joinColumn.setSpecifiedName(null);
////	joinColumn.setSpecifiedReferencedColumnName(null);
////	joinColumn.setSpecifiedTable(null);
////	assertEquals(SUB_TYPE_NAME, typeResource.getName());
////	assertNull(typeResource.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(typeResource.annotation(AssociationOverrides.ANNOTATION_NAME));
////
////	defaultAssociationOverride = javaEntity.defaultAssociationOverrides().next();
////	assertEquals("address", defaultJoinColumn.getName());
////	assertEquals("address", defaultJoinColumn.getReferencedColumnName());
////	assertEquals(SUB_TYPE_NAME, defaultJoinColumn.getTable());
////	
////	javaEntity.addSpecifiedAssociationOverride(0).setName("address");
////	assertEquals(0, CollectionTools.size(javaEntity.defaultAssociationOverrides()));

	}
	
	public void testSpecifiedAssociationOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0, getJavaEntity().specifiedAssociationOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");

		assertEquals(2, getJavaEntity().specifiedAssociationOverridesSize());
	}
	
	public void testDefaultAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = getJavaEntity();
		
		assertEquals(4, javaEntity.virtualAssociationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(3, javaEntity.virtualAssociationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(2, javaEntity.virtualAssociationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(1, javaEntity.virtualAssociationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(0, javaEntity.virtualAssociationOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Entity javaEntity = getJavaEntity();
		
		assertEquals(4, javaEntity.associationOverridesSize());

		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(4, javaEntity.associationOverridesSize());
		
		javaEntity.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(4, javaEntity.associationOverridesSize());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		annotation.setName("bar");	
		assertEquals(5, javaEntity.associationOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAssociationOverride> virtualAssociationOverrides = getJavaEntity().virtualAssociationOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().setVirtual(false);
		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(2, CollectionTools.size(typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)));

		getJavaEntity().specifiedAssociationOverrides().next().setVirtual(true);
		
		Iterator<NestableAnnotation> associationOverrideResources = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<JavaAssociationOverride> associationOverrides = getJavaEntity().specifiedAssociationOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		getJavaEntity().specifiedAssociationOverrides().next().setVirtual(true);
		associationOverrideResources = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = getJavaEntity().specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());

		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();
		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);

		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		Iterator<NestableAnnotation> javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		entity.moveSpecifiedAssociationOverride(1, 0);
		ListIterator<AssociationOverride> associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		entity.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("address2", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = getJavaEntity();		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AssociationOverrideAnnotation) typeResource.addAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverrideAnnotation) typeResource.addAnnotation(1, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverrideAnnotation) typeResource.addAnnotation(2, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<AssociationOverride> associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.moveAnnotation(2, 0, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.moveAnnotation(0, 1, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		associationOverrides = entity.specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaAssociationOverride> virtualAssociationOverrides = getJavaEntity().virtualAssociationOverrides();	
		AssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address3", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address4", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		getJavaEntity().virtualAssociationOverrides().next().setVirtual(false);
		AssociationOverride specifiedAssociationOverride = getJavaEntity().specifiedAssociationOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = getJavaEntity().virtualAssociationOverrides();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address3", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address4", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}
}
