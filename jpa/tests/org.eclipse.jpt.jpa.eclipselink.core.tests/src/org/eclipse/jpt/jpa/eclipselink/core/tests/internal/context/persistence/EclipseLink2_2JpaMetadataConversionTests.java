/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.persistence.Generic2_0JpaMetadataConversionTests;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_2ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_2JpaMetadataConversionTests extends EclipseLink2_2ContextModelTestCase {

	private IProgressMonitor progressMonitor;

	public EclipseLink2_2JpaMetadataConversionTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.progressMonitor = new IProgressMonitor() {
			public void worked(int work) {}
			public void subTask(String name) {}
			public void setTaskName(String name) {}
			public void setCanceled(boolean value) {}
			public boolean isCanceled() {return false;}
			public void internalWorked(double work) {}
			public void done() {}
			public void beginTask(String name, int totalWork) {}
		} ;
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.progressMonitor = null;
		super.tearDown();
	}
	
	// ************ metadata conversion of equivalent queries on EclipseLink platform*************
	
	private ICompilationUnit createTestEntityWithDuplicateQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.NAMED_QUERY, JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedQuery(name=\"nquery\")").append(CR);
				sb.append("@NamedNativeQueries({" +
						"@NamedNativeQuery(name=\"nnq1\", query=\"abcd\"), " +
						"@NamedNativeQuery(name=\"nnq1\", query=\"abcde\")})");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithEquivalentQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedNativeQueries({" +
							"@NamedNativeQuery(name=\"nnq1\", query=\"abcd\", resultClass=foo1.class, resultSetMapping=\"bar1\", " +
										"hints={@QueryHint(name=\"nnq1hint1\", value = \"aaa\"), " +
												"@QueryHint(name=\"nnq1hint2\", value=\"bbb\")}), " +
							"@NamedNativeQuery(name=\"nnq1\", query=\"abcd\", resultClass=foo1.class, resultSetMapping=\"bar1\", " +
										"hints={@QueryHint(name=\"nnq1hint1\", value = \"aaa\"), " +
												"@QueryHint(name=\"nnq1hint2\", value=\"bbb\")})})");
			}
		});
	}
	
	public void testConvertDuplicateQueries() throws Exception {
		createTestEntityWithDuplicateQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();

		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		JavaEntity entity = getJavaEntity();
		// test duplicate Java queries are not converted to eclipselink-orm.xml and 
		// not removed from the Java entity, but unique query is 
		assertEquals(0, entity.getQueryContainer().getNamedQueriesSize());
		assertEquals(2, entity.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(1, entityMappings.getQueryContainer().getNamedQueriesSize());
		assertEquals(0, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
	}

	public void testConvertEquivalentQueries() throws Exception {
		createTestEntityWithEquivalentQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		JavaEntity entity = getJavaEntity();
		
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test only one of the equivalent Java queries are converted
		// to eclipselink-orm.xml and the rest are removed 
		assertEquals(0, entity.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(1, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
	}
	
	// ************ metadata conversion of equivalent generators on EclipseLink platform*************
	
	private ICompilationUnit createTestEntityWithDuplicateGenerators() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.TABLE_GENERATOR, JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@TableGenerator(name=\"tgen\", initialValue=100)").append(CR);
				sb.append("@SequenceGenerator(name=\"sgen\", initialValue=10)");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@TableGenerator(name=\"tgen\", initialValue=10)").append(CR);
				sb.append("@SequenceGenerator(name=\"sgen\", initialValue=1)");
			}
		});
	}
		
	private ICompilationUnit createTestEntityWithEquivalentGenerators() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.TABLE_GENERATOR, JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@TableGenerator(name=\"tgen\", table=\"ID_GEN\", schema=\"APP\", catalog=\"FOO\"," +
						"pkColumnName=\"GEN_NAME\", valueColumnName=\"GEN_VALUE\", pkColumnValue=\"TGEN\"," +
						"allocationSize=50, initialValue=100)").append(CR);
				sb.append("@SequenceGenerator(name=\"sgen\", sequenceName=\"Foo_Seq\", schema=\"SYS\", catalog=\"BAR\"," +
						"allocationSize=5, initialValue=10)");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@TableGenerator(name=\"tgen\", table=\"ID_GEN\", schema=\"APP\", catalog=\"FOO\"," +
						"pkColumnName=\"GEN_NAME\", valueColumnName=\"GEN_VALUE\", pkColumnValue=\"TGEN\"," +
						"allocationSize=50, initialValue=100)").append(CR);
				sb.append("@SequenceGenerator(name=\"sgen\", sequenceName=\"Foo_Seq\", schema=\"SYS\", catalog=\"BAR\"," +
						"allocationSize=5, initialValue=10)");
			}
		});
	}
	
	public void testConvertDuplicateGenerators() throws Exception {
		createTestEntityWithDuplicateGenerators();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		JavaEntity entity = getJavaEntity();
		JavaIdMapping mapping = (JavaIdMapping)entity.getIdAttribute().getMapping();
		
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaGenerators(entityMappings, progressMonitor);
		
		// test duplicate Java generators are not converted to eclipselink-orm.xml and 
		// not removed from the Java entity, but unique generator is
		assertNotNull(entity.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(entity.getGeneratorContainer().getTableGenerator());
		assertEquals(0, entityMappings.getSequenceGeneratorsSize());
		assertEquals(0, entityMappings.getTableGeneratorsSize());
		assertNotNull(mapping.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(mapping.getGeneratorContainer().getTableGenerator());
	}
	
	public void testConvertEquivalentGenerators() throws Exception {
		createTestEntityWithEquivalentGenerators();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		JavaEntity entity = getJavaEntity();
		JavaIdMapping mapping = (JavaIdMapping)entity.getIdAttribute().getMapping();

		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaGenerators(entityMappings, progressMonitor);
		
		// test only one of the equivalent generators are converted to 
		// eclipselink-orm.xml and the rest are removed
		assertNull(entity.getGeneratorContainer().getSequenceGenerator());
		assertNull(entity.getGeneratorContainer().getTableGenerator());
		assertNull(mapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(mapping.getGeneratorContainer().getTableGenerator());
		assertEquals(1, entityMappings.getSequenceGeneratorsSize());
		assertEquals(1, entityMappings.getTableGeneratorsSize());
	}
	
	// ************ entity with converters*************
	
	private ICompilationUnit createTestEntityWithConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, EclipseLink.CONVERTER, EclipseLink.TYPE_CONVERTER,
						EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLink.STRUCT_CONVERTER, EclipseLink.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)").append(CR);
				sb.append("@TypeConverter(name=\"type\", dataType=Number.class, objectType=Integer.class)").append(CR);
				sb.append("@ObjectTypeConverter(name=\"otype\", dataType=Character.class, objectType=String.class, " +
						"conversionValues={@ConversionValue(dataValue=\"m\", objectValue=\"male\"), " +
										  "@ConversionValue(dataValue=\"f\", objectValue=\"female\")}," +
						"defaultObjectValue=\"female\")").append(CR);
				sb.append("@StructConverter(name=\"struct\", converter=\"bar\")");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConverterOnConvertibleMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, EclipseLink.CONVERTERS, EclipseLink.CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@Converters({" +
						"@Converter(name = \"custom1\", converterClass=foo1.class)," +
						"@Converter(name = \"custom2\", converterClass=foo2.class)})");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConverterOnCollectionMapping() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION,
												"java.util.Map", EclipseLink.STRUCT_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection").append(CR);
				sb.append("@StructConverter(name=\"struct\", converter=\"bar\")").append(CR);
				sb.append("    private Map<String, String> projects;");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithDuplicateConvertersOfDiffTypes() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC, EclipseLink.CONVERTER, EclipseLink.TYPE_CONVERTER,
						EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLink.STRUCT_CONVERTER, EclipseLink.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)").append(CR);
				sb.append("@TypeConverter(name=\"type\")").append(CR);
				sb.append("@ObjectTypeConverter(name=\"type\")").append(CR);
				sb.append("@StructConverter(name=\"struct\", converter=\"bar\")");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("@ObjectTypeConverter(name=\"otype\")");
			}
		});
	}

	private ICompilationUnit createTestEntity2WithDuplicateConvertersOfSameType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC, EclipseLink.CONVERTER, EclipseLink.TYPE_CONVERTER,
						EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLink.STRUCT_CONVERTER, EclipseLink.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)").append(CR);
				sb.append("@TypeConverter(name=\"type\")").append(CR);
				sb.append("@ObjectTypeConverter(name=\"otype\")").append(CR);
				sb.append("@StructConverter(name=\"struct\", converter=\"bar\")");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=bar.class)");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithEquivalentConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC, EclipseLink.CONVERTER, EclipseLink.TYPE_CONVERTER,
						EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLink.STRUCT_CONVERTER, EclipseLink.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)").append(CR);
				sb.append("@TypeConverter(name=\"type\")").append(CR);
				sb.append("@ObjectTypeConverter(name=\"otype\")").append(CR);
				sb.append("@StructConverter(name=\"struct\", converter=\"bar\")");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)");
			}
		});
	}
	
	public void testConvertConvertersonEntity() throws Exception {
		createTestEntityWithConverters();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = getJavaEntity();
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test Java converter are converted to eclipselink-orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getConverterContainer().getCustomConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getObjectTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getStructConvertersSize());
		assertEquals(entity.getConverterContainer().getCustomConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getTypeConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getObjectTypeConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getStructConvertersSize(), 0);
		
		// test the mapping file converters have correct values
		EclipseLinkCustomConverter custom = entityMappings.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("custom", custom.getName());
		assertEquals("foo", custom.getConverterClass());
		
		EclipseLinkTypeConverter type = entityMappings.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("type", type.getName());
		assertEquals("java.lang.Number", type.getDataType());
		assertEquals("java.lang.Integer", type.getObjectType());
		
		EclipseLinkObjectTypeConverter otype = entityMappings.getConverterContainer().getObjectTypeConverters().iterator().next();
		assertEquals("otype", otype.getName());
		assertEquals("java.lang.Character", otype.getDataType());
		assertEquals("java.lang.String", otype.getObjectType());
		ListIterator<? extends EclipseLinkConversionValue> values = otype.getConversionValues().iterator();
		EclipseLinkConversionValue value1 = values.next();
		assertEquals("m", value1.getDataValue());
		assertEquals("male", value1.getObjectValue());
		EclipseLinkConversionValue value2 = values.next();
		assertEquals("f", value2.getDataValue());
		assertEquals("female", value2.getObjectValue());
		assertEquals("female", otype.getDefaultObjectValue());
		
		EclipseLinkStructConverter struct = entityMappings.getConverterContainer().getStructConverters().iterator().next();
		assertEquals("struct", struct.getName());
		assertEquals("bar", struct.getConverterClass());
	}
	
	public void testConvertConvertersOnEntityConvertibleMapping() throws Exception {
		createTestEntityWithConverterOnConvertibleMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = getJavaEntity();
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test Java converter on attribute mapping is converted to eclipselink-orm.xml and removed from the Java entity
		assertEquals(2, entityMappings.getConverterContainer().getCustomConvertersSize());
		
		EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)entity.getIdAttribute().getMapping();
		
		assertEquals(mapping.getConverterContainer().getConvertersSize(), 0);
		
		// test the mapping file converter have correct values
		Collection<OrmEclipseLinkCustomConverter> customConverters = CollectionTools.collection(entityMappings.getConverterContainer().getCustomConverters());
		EclipseLinkCustomConverter custom1 = Generic2_0JpaMetadataConversionTests.selectNodeNamed(customConverters, "custom1");
		assertEquals("custom1", custom1.getName());
		assertEquals("foo1", custom1.getConverterClass());
		EclipseLinkCustomConverter custom2 = Generic2_0JpaMetadataConversionTests.selectNodeNamed(customConverters, "custom2");
		assertEquals("custom2", custom2.getName());
		assertEquals("foo2", custom2.getConverterClass());
	}
	
	public void testConvertConvertersOnCollectionMapping() throws Exception {
		createTestEntityWithConverterOnCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = getJavaEntity();
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test Java converter on attribute mapping is converted to eclipselink-orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getConverterContainer().getStructConvertersSize());
		
		if (entity.getAttributeMappings().iterator().hasNext()) {
			EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)entity.getAttributeMappings().iterator().next();
			assertEquals(mapping.getConverterContainer().getConvertersSize(), 0);
		}

		// test the mapping file converter have correct values
		EclipseLinkStructConverter custom = entityMappings.getConverterContainer().getStructConverters().iterator().next();
		assertEquals("struct", custom.getName());
		assertEquals("bar", custom.getConverterClass());
		
		// TODO test converting converter on map key - not supported yet
	}
	
		public void testConvertOverriddenConverters() throws Exception {
		createTestEntityWithDuplicateConvertersOfDiffTypes();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();		
		OrmEclipseLinkConverterContainer ormConvertercontainer = entityMappings.getConverterContainer();
		ormConvertercontainer.addCustomConverter("type", ormConvertercontainer.getCustomConvertersSize());
	
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		JavaEclipseLinkEntity entity = getJavaEntity();
		EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)entity.getAttributeMappings().iterator().next();
		assertTrue(IterableTools.contains(persistenceUnit.getConverters(), mapping.getConverterContainer().getConverters().iterator().next()));
				
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test overriden Java converters are not converted to eclipselink-orm.xml and 
		// not removed from the Java entity, but unique converters are
		assertEquals(entity.getConverterContainer().getTypeConvertersSize(), 1);
		assertEquals(entity.getConverterContainer().getObjectTypeConvertersSize(), 1);
		assertEquals(entity.getConverterContainer().getCustomConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getStructConvertersSize(), 0);
		assertEquals(mapping.getConverterContainer().getObjectTypeConvertersSize(), 0);
		assertEquals(2, ormConvertercontainer.getCustomConvertersSize());
		assertEquals(1, ormConvertercontainer.getStructConvertersSize());
		assertEquals(0, ormConvertercontainer.getTypeConvertersSize());
		assertEquals(1, ormConvertercontainer.getObjectTypeConvertersSize());
	}
	
	public void testConvertDuplicateConvertersWithDiffTypes() throws Exception {
		createTestEntityWithDuplicateConvertersOfDiffTypes();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		JavaEclipseLinkEntity entity = getJavaEntity();
		EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)entity.getAttributeMappings().iterator().next();
				
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test duplicate Java converters are not converted to eclipselink-orm.xml and 
		// not removed from the Java entity, but unique converters are
		assertEquals(entity.getConverterContainer().getTypeConvertersSize(), 1);
		assertEquals(entity.getConverterContainer().getObjectTypeConvertersSize(), 1);
		assertEquals(entity.getConverterContainer().getCustomConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getStructConvertersSize(), 0);
		assertEquals(mapping.getConverterContainer().getObjectTypeConvertersSize(), 0);
		assertEquals(1, entityMappings.getConverterContainer().getCustomConvertersSize());
		assertEquals(0, entityMappings.getConverterContainer().getTypeConvertersSize());	
		assertEquals(1, entityMappings.getConverterContainer().getStructConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getObjectTypeConvertersSize());
	}
	
	public void testConvertDuplicateConvertersWithSameType() throws Exception {
		createTestEntity2WithDuplicateConvertersOfSameType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		JavaEclipseLinkEntity entity = getJavaEntity();
		EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)entity.getAttributeMappings().iterator().next();

		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test duplicate Java converters are not converted to eclipselink-orm.xml and 
		// not removed from the Java entity, but unique converters are
		assertEquals(entity.getConverterContainer().getCustomConvertersSize(), 1);
		assertEquals(entity.getConverterContainer().getTypeConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getObjectTypeConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getStructConvertersSize(), 0);
		assertEquals(mapping.getConverterContainer().getCustomConvertersSize(), 1);
		assertEquals(0, entityMappings.getConverterContainer().getCustomConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getStructConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getObjectTypeConvertersSize());
	}
	
	public void testConvertEquivalentConverters() throws Exception {
		createTestEntityWithEquivalentConverters();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		JavaEclipseLinkEntity entity = getJavaEntity();
		EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)entity.getAttributeMappings().iterator().next();
				
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test only one of the equivalent converters is converted to 
		// eclipselink-orm.xml and the rest are removed 
		assertEquals(entity.getConverterContainer().getCustomConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getTypeConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getObjectTypeConvertersSize(), 0);
		assertEquals(entity.getConverterContainer().getStructConvertersSize(), 0);
		assertEquals(mapping.getConverterContainer().getCustomConvertersSize(), 0);
		assertEquals(1, entityMappings.getConverterContainer().getCustomConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getStructConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getObjectTypeConvertersSize());
	}
	
	// ************ embeddable with converters*************
	
	private ICompilationUnit createTestEmbeddableWithConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.EMBEDDABLE, EclipseLink.CONVERTER, EclipseLink.TYPE_CONVERTER,
						EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLink.STRUCT_CONVERTER, EclipseLink.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)").append(CR);
				sb.append("@TypeConverter(name=\"type\", dataType=Number.class, objectType=Integer.class)").append(CR);
				sb.append("@ObjectTypeConverter(name=\"otype\", dataType=Character.class, objectType=String.class, " +
						"conversionValues={@ConversionValue(dataValue=\"m\", objectValue=\"male\"), " +
										  "@ConversionValue(dataValue=\"f\", objectValue=\"female\")}," +
						"defaultObjectValue=\"female\")").append(CR);
				sb.append("@StructConverter(name=\"struct\", converter=\"bar\")");
			}
		});
	}
	
	private ICompilationUnit createTestEmbeddableWithConverterOnConvertibleMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.EMBEDDABLE, JPA.VERSION, EclipseLink.CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)");
			}
		});
	}
	
	public void testConvertConvertersOnEmbeddable() throws Exception {
		createTestEmbeddableWithConverters();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEmbeddable embeddable = getJavaEmbeddable();
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test Java converter are converted to eclipselink-orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getConverterContainer().getCustomConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getObjectTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getStructConvertersSize());
		assertEquals(embeddable.getConverterContainer().getCustomConvertersSize(), 0);
		assertEquals(embeddable.getConverterContainer().getTypeConvertersSize(), 0);
		assertEquals(embeddable.getConverterContainer().getObjectTypeConvertersSize(), 0);
		assertEquals(embeddable.getConverterContainer().getStructConvertersSize(), 0);
		
		// test the mapping file converters have correct values
		EclipseLinkCustomConverter custom = entityMappings.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("custom", custom.getName());
		assertEquals("foo", custom.getConverterClass());
		
		EclipseLinkTypeConverter type = entityMappings.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("type", type.getName());
		assertEquals("java.lang.Number", type.getDataType());
		assertEquals("java.lang.Integer", type.getObjectType());
		
		EclipseLinkObjectTypeConverter otype = entityMappings.getConverterContainer().getObjectTypeConverters().iterator().next();
		assertEquals("otype", otype.getName());
		assertEquals("java.lang.Character", otype.getDataType());
		assertEquals("java.lang.String", otype.getObjectType());
		ListIterator<? extends EclipseLinkConversionValue> values = otype.getConversionValues().iterator();
		EclipseLinkConversionValue value1 = values.next();
		assertEquals("m", value1.getDataValue());
		assertEquals("male", value1.getObjectValue());
		EclipseLinkConversionValue value2 = values.next();
		assertEquals("f", value2.getDataValue());
		assertEquals("female", value2.getObjectValue());
		assertEquals("female", otype.getDefaultObjectValue());
		
		EclipseLinkStructConverter struct = entityMappings.getConverterContainer().getStructConverters().iterator().next();
		assertEquals("struct", struct.getName());
		assertEquals("bar", struct.getConverterClass());
	}
	
	public void testConvertConvertersOnEmbeddableConvertibleMapping() throws Exception {
		createTestEmbeddableWithConverterOnConvertibleMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEmbeddable embeddable = getJavaEmbeddable();
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test Java converter on attribute mapping is converted to eclipselink-orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getConverterContainer().getCustomConvertersSize());
		
		EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)embeddable.getAttributeMappings().iterator().next();
		assertEquals(mapping.getConverterContainer().getConvertersSize(), 0);
		
		// test the mapping file converter have correct values
		EclipseLinkCustomConverter custom = entityMappings.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("custom", custom.getName());
		assertEquals("foo", custom.getConverterClass());
	}
	
	// ************ mapped superclass  with converters*************
	private ICompilationUnit createTestMappedSuperclassWithConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, EclipseLink.CONVERTER, EclipseLink.TYPE_CONVERTER,
						EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLink.STRUCT_CONVERTER, EclipseLink.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)").append(CR);
				sb.append("@TypeConverter(name=\"type\", dataType=Number.class, objectType=Integer.class)").append(CR);
				sb.append("@ObjectTypeConverter(name=\"otype\", dataType=Character.class, objectType=String.class, " +
						"conversionValues={@ConversionValue(dataValue=\"m\", objectValue=\"male\"), " +
										  "@ConversionValue(dataValue=\"f\", objectValue=\"female\")}," +
						"defaultObjectValue=\"female\")").append(CR);
				sb.append("@StructConverter(name=\"struct\", converter=\"bar\")");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassWithConverterOnConvertibleMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, JPA.ID, EclipseLink.CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@Converter(name=\"custom\", converterClass=foo.class)");
			}
		});
	}
	
	public void testConvertConvertersOnMappedSuperclass() throws Exception {
		createTestMappedSuperclassWithConverters();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkMappedSuperclass mappedSuperclass = getJavaMappedSuperclass();
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test Java converter are converted to eclipselink-orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getConverterContainer().getCustomConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getObjectTypeConvertersSize());
		assertEquals(1, entityMappings.getConverterContainer().getStructConvertersSize());
		assertEquals(mappedSuperclass.getConverterContainer().getCustomConvertersSize(), 0);
		assertEquals(mappedSuperclass.getConverterContainer().getTypeConvertersSize(), 0);
		assertEquals(mappedSuperclass.getConverterContainer().getObjectTypeConvertersSize(), 0);
		assertEquals(mappedSuperclass.getConverterContainer().getStructConvertersSize(), 0);
		
		// test the mapping file converters have correct values
		EclipseLinkCustomConverter custom = entityMappings.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("custom", custom.getName());
		assertEquals("foo", custom.getConverterClass());
		
		EclipseLinkTypeConverter type = entityMappings.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("type", type.getName());
		assertEquals("java.lang.Number", type.getDataType());
		assertEquals("java.lang.Integer", type.getObjectType());
		
		EclipseLinkObjectTypeConverter otype = entityMappings.getConverterContainer().getObjectTypeConverters().iterator().next();
		assertEquals("otype", otype.getName());
		assertEquals("java.lang.Character", otype.getDataType());
		assertEquals("java.lang.String", otype.getObjectType());
		ListIterator<? extends EclipseLinkConversionValue> values = otype.getConversionValues().iterator();
		EclipseLinkConversionValue value1 = values.next();
		assertEquals("m", value1.getDataValue());
		assertEquals("male", value1.getObjectValue());
		EclipseLinkConversionValue value2 = values.next();
		assertEquals("f", value2.getDataValue());
		assertEquals("female", value2.getObjectValue());
		assertEquals("female", otype.getDefaultObjectValue());
		
		EclipseLinkStructConverter struct = entityMappings.getConverterContainer().getStructConverters().iterator().next();
		assertEquals("struct", struct.getName());
		assertEquals("bar", struct.getConverterClass());
	}
	
	public void testConvertConvertersOnMappedSuperclassConvertibleMapping() throws Exception {
		createTestMappedSuperclassWithConverterOnConvertibleMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkMappedSuperclass mappedSuperclass = getJavaMappedSuperclass();
		addXmlMappingFileRef(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaConverters(entityMappings, progressMonitor);
		
		// test Java converter on attribute mapping is converted to eclipselink-orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getConverterContainer().getCustomConvertersSize());
		
		EclipseLinkConvertibleMapping mapping = (EclipseLinkConvertibleMapping)mappedSuperclass.getAttributeMappings().iterator().next();
		assertEquals(mapping.getConverterContainer().getConvertersSize(), 0);
		
		// test the mapping file converter have correct values
		EclipseLinkCustomConverter custom = entityMappings.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("custom", custom.getName());
		assertEquals("foo", custom.getConverterClass());
	}
}
