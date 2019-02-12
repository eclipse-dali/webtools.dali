/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.persistence;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmSequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.LockModeType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmNamedQuery2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class Generic2_0JpaMetadataConversionTests extends
		Generic2_0ContextModelTestCase {

	private IProgressMonitor progressMonitor;

	public Generic2_0JpaMetadataConversionTests(String name) {
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
	
	// ************ entity with queries*************
	
	private ICompilationUnit createTestEntityWithNamedQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.NAMED_QUERIES, JPA.NAMED_QUERY, JPA.QUERY_HINT, JPA2_0.LOCK_MODE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedQueries({" +
							"@NamedQuery(name=\"nq1\", query=\"abcd\", lockMode=LockModeType.OPTIMISTIC, " +
										"hints={@QueryHint(name=\"nq1hint1\", value = \"aaa\"), " +
												"@QueryHint(name=\"nq1hint2\", value=\"bbb\")}), " +
							"@NamedQuery(name=\"nq2\", query=\"efgh\", lockMode=LockModeType.READ, " +
										"hints=@QueryHint(name=\"nq2hint1\", value=\"ccc\"))})");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithNamedNativeQueries() throws Exception {
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
							"@NamedNativeQuery(name=\"nnq2\", query=\"efgh\", resultClass=foo2.class, resultSetMapping=\"bar2\", " +
										"hints=@QueryHint(name=\"nnq2hint1\", value=\"ccc\"))})");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithDuplicateQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.NAMED_QUERY, JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedQuery(name=\"query\")").append(CR);
				sb.append("@NamedNativeQueries({@NamedNativeQuery(name=\"query\"), @NamedNativeQuery(name=\"nnq\")})");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.NAMED_QUERIES, JPA.NAMED_QUERY, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedQueries({@NamedQuery(name=\"nq\"), @NamedQuery(name=\"query\")})").append(CR);
				sb.append("@NamedNativeQuery(name=\"nnq\")");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperWithNamedQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, JPA.NAMED_QUERIES, JPA.NAMED_QUERY, JPA.QUERY_HINT, JPA2_0.LOCK_MODE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@NamedQueries({" +
							"@NamedQuery(name=\"nq1\", query=\"abcd\", lockMode=LockModeType.OPTIMISTIC, " +
										"hints={@QueryHint(name=\"nq1hint1\", value = \"aaa\"), " +
												"@QueryHint(name=\"nq1hint2\", value=\"bbb\")}), " +
							"@NamedQuery(name=\"nq2\", query=\"efgh\", lockMode=LockModeType.READ, " +
										"hints=@QueryHint(name=\"nq2hint1\", value=\"ccc\"))})");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassWithNamedNativeQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY, JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@NamedNativeQueries({" +
							"@NamedNativeQuery(name=\"nnq1\", query=\"abcd\", resultClass=foo1.class, resultSetMapping=\"bar1\", " +
										"hints={@QueryHint(name=\"nnq1hint1\", value = \"aaa\"), " +
												"@QueryHint(name=\"nnq1hint2\", value=\"bbb\")}), " +
							"@NamedNativeQuery(name=\"nnq2\", query=\"efgh\", resultClass=foo2.class, resultSetMapping=\"bar2\", " +
										"hints=@QueryHint(name=\"nnq2hint1\", value=\"ccc\"))})");
			}
		});
	}
	
	public void testConvertNamedQueriesOnEntity() throws Exception {
		createTestEntityWithNamedQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test Java queries are converted to orm.xml and removed from the Java entity
		assertEquals(2, entityMappings.getQueryContainer().getNamedQueriesSize());
		assertEquals(0, entity.getQueryContainer().getNamedQueriesSize());
		
		// test the mapping file queries have correct values
		Iterable<OrmNamedQuery> queries = entityMappings.getQueryContainer().getNamedQueries();
		queries = IterableTools.sort(queries, JpaNamedContextModel.NAME_COMPARATOR);
		Iterator<OrmNamedQuery> queriesIterator = queries.iterator();
		OrmNamedQuery nq1 = queriesIterator.next();
		
		// test the first mapping file query
		assertEquals("nq1", (nq1.getName()));
		assertEquals("abcd", (nq1.getQuery()));
		assertEquals(2, (nq1.getHintsSize()));
		
		// test the query hints of the first mapping file query have the correct value
		ListIterator<OrmQueryHint> nq1hints = nq1.getHints().iterator();
		
		// test the first query hint of the first mapping file query
		OrmQueryHint nq1hint1 = nq1hints.next();
		assertEquals("nq1hint1", nq1hint1.getName());
		assertEquals("aaa", nq1hint1.getValue());
		
		// test the second query hint of the first mapping file query
		OrmQueryHint nq1hint2 = nq1hints.next();
		assertEquals("nq1hint2", nq1hint2.getName());
		assertEquals("bbb", nq1hint2.getValue());
		
		// test the second mapping file query
		OrmNamedQuery nq2 = queriesIterator.next();
		assertEquals("nq2", (nq2.getName()));
		assertEquals("efgh", (nq2.getQuery()));
		assertEquals(1, (nq2.getHintsSize()));
		
		// test the query hints of the second mapping file query have the correct value
		OrmQueryHint nq2hint1 = nq2.getHints().iterator().next();
		
		// test the query hint of the second mapping file query
		assertEquals("nq2hint1", nq2hint1.getName());
		assertEquals("ccc", nq2hint1.getValue());
		
		assertEquals(LockModeType2_0.OPTIMISTIC, ((OrmNamedQuery2_0)nq1).getLockMode());
		assertEquals(LockModeType2_0.READ, ((OrmNamedQuery2_0)nq2).getLockMode());
	}
	
	public void testConvertNamedNativeQueriesOnEntity() throws Exception {
		createTestEntityWithNamedNativeQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test Java queries are converted to orm.xml and removed from the Java entity
		assertEquals(2, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(0, entity.getQueryContainer().getNamedNativeQueriesSize());
		
		// test the mapping file queries have correct values
		Collection<OrmNamedNativeQuery> namedNativeQueries = CollectionTools.hashBag(entityMappings.getQueryContainer().getNamedNativeQueries());

		// test the first mapping file query
		OrmNamedNativeQuery nnq1 = selectModelNamed(namedNativeQueries, "nnq1");
		assertEquals("nnq1", (nnq1.getName()));
		assertEquals("abcd", (nnq1.getQuery()));
		assertEquals("foo1", nnq1.getResultClass());
		assertEquals("bar1", nnq1.getResultSetMapping());
		assertEquals(2, (nnq1.getHintsSize()));
		
		// test the query hints of the first mapping file query have the correct value
		ListIterator<OrmQueryHint> nq1hints = nnq1.getHints().iterator();
		
		// test the first query hint of the first mapping file query
		OrmQueryHint nnq1hint1 = nq1hints.next();
		assertEquals("nnq1hint1", nnq1hint1.getName());
		assertEquals("aaa", nnq1hint1.getValue());
		
		// test the second query hint of the first mapping file query
		OrmQueryHint nnq1hint2 = nq1hints.next();
		assertEquals("nnq1hint2", nnq1hint2.getName());
		assertEquals("bbb", nnq1hint2.getValue());
		
		// test the second mapping file query
		OrmNamedNativeQuery nnq2 = selectModelNamed(namedNativeQueries, "nnq2");
		assertEquals("nnq2", (nnq2.getName()));
		assertEquals("efgh", (nnq2.getQuery()));
		assertEquals("foo2", nnq2.getResultClass());
		assertEquals("bar2", nnq2.getResultSetMapping());
		assertEquals(1, (nnq2.getHintsSize()));
		
		// test the query hints of the second mapping file query have the correct value
		OrmQueryHint nnq2hint1 = nnq2.getHints().iterator().next();
		
		// test the query hint of the second mapping file query
		assertEquals("nnq2hint1", nnq2hint1.getName());
		assertEquals("ccc", nnq2hint1.getValue());
		
	}

	public static <M extends JpaNamedContextModel> M selectModelNamed(Iterable<M> models, String name) {
		for (M node : models) {
			if (node.getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

	public void testConvertOverriddenQueries() throws Exception {
		createTestEntityWithQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		OrmNamedNativeQuery nnq = entityMappings.getQueryContainer().addNamedNativeQuery();
		nnq.setName("nq");
		OrmNamedQuery nq = entityMappings.getQueryContainer().addNamedQuery();
		nq.setName("nnq");
		
		JavaEntity entity = getJavaEntity();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test overridden Java queries are not converted to orm.xml and not 
		// removed from the Java entity, but unique query is
		assertEquals(1, entity.getQueryContainer().getNamedQueriesSize());
		assertEquals(1, entity.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(2, entityMappings.getQueryContainer().getNamedQueriesSize());
		assertEquals(1, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
	}
	
	public void testConvertDuplicateQueries() throws Exception {
		createTestEntityWithDuplicateQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);

		JavaEntity entity = getJavaEntity();
		
		// test duplicate Java queries are not converted to orm.xml and 
		// not removed from the Java entity, but unique query is 
		assertEquals(1, entity.getQueryContainer().getNamedQueriesSize());
		assertEquals(1, entity.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(0, entityMappings.getQueryContainer().getNamedQueriesSize());
		assertEquals(1, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
	}
	
	public void testConvertNamedQueriesOnMappedSuperclass() throws Exception {
		createTestMappedSuperWithNamedQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaMappedSuperclass mappedSuperclass = (JavaMappedSuperclass) getJavaPersistentType().getMapping();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test Java queries are converted to orm.xml and removed from the Java entity
		assertEquals(2, entityMappings.getQueryContainer().getNamedQueriesSize());
		assertEquals(0, mappedSuperclass.getQueryContainer().getNamedQueriesSize());
		
		// test the mapping file queries have correct values
		Iterable<OrmNamedQuery> queries = entityMappings.getQueryContainer().getNamedQueries();
		queries = IterableTools.sort(queries, JpaNamedContextModel.NAME_COMPARATOR);
		Iterator<OrmNamedQuery> queriesIterator = queries.iterator();
		OrmNamedQuery nq1 = queriesIterator.next();
		
		// test the first mapping file query
		assertEquals("nq1", (nq1.getName()));
		assertEquals("abcd", (nq1.getQuery()));
		assertEquals(2, (nq1.getHintsSize()));
		
		// test the query hints of the first mapping file query have the correct value
		ListIterator<OrmQueryHint> nq1hints = nq1.getHints().iterator();
		
		// test the first query hint of the first mapping file query
		OrmQueryHint nq1hint1 = nq1hints.next();
		assertEquals("nq1hint1", nq1hint1.getName());
		assertEquals("aaa", nq1hint1.getValue());
		
		// test the second query hint of the first mapping file query
		OrmQueryHint nq1hint2 = nq1hints.next();
		assertEquals("nq1hint2", nq1hint2.getName());
		assertEquals("bbb", nq1hint2.getValue());
		
		// test the second mapping file query
		OrmNamedQuery nq2 = queriesIterator.next();
		assertEquals("nq2", (nq2.getName()));
		assertEquals("efgh", (nq2.getQuery()));
		assertEquals(1, (nq2.getHintsSize()));
		
		// test the query hints of the second mapping file query have the correct value
		OrmQueryHint nq2hint1 = nq2.getHints().iterator().next();
		
		// test the query hint of the second mapping file query
		assertEquals("nq2hint1", nq2hint1.getName());
		assertEquals("ccc", nq2hint1.getValue());
		
		assertEquals(LockModeType2_0.OPTIMISTIC, ((OrmNamedQuery2_0)nq1).getLockMode());
		assertEquals(LockModeType2_0.READ, ((OrmNamedQuery2_0)nq2).getLockMode());
	}
	
	public void testConvertNamedNativeQueriesOnMappedSuperclass() throws Exception {
		createTestMappedSuperclassWithNamedNativeQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaMappedSuperclass mappedSuperclass = (JavaMappedSuperclass) getJavaPersistentType().getMapping();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test Java queries are converted to orm.xml and removed from the Java entity
		assertEquals(2, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(0, mappedSuperclass.getQueryContainer().getNamedNativeQueriesSize());
		
		// test the mapping file queries have correct values
		Collection<OrmNamedNativeQuery> namedNativeQueries = CollectionTools.hashBag(entityMappings.getQueryContainer().getNamedNativeQueries());

		// test the first mapping file query
		OrmNamedNativeQuery nnq1 = selectModelNamed(namedNativeQueries, "nnq1");
		assertEquals("nnq1", (nnq1.getName()));
		assertEquals("abcd", (nnq1.getQuery()));
		assertEquals("foo1", nnq1.getResultClass());
		assertEquals("bar1", nnq1.getResultSetMapping());
		assertEquals(2, (nnq1.getHintsSize()));
		
		// test the query hints of the first mapping file query have the correct value
		ListIterator<OrmQueryHint> nq1hints = nnq1.getHints().iterator();
		
		// test the first query hint of the first mapping file query
		OrmQueryHint nnq1hint1 = nq1hints.next();
		assertEquals("nnq1hint1", nnq1hint1.getName());
		assertEquals("aaa", nnq1hint1.getValue());
		
		// test the second query hint of the first mapping file query
		OrmQueryHint nnq1hint2 = nq1hints.next();
		assertEquals("nnq1hint2", nnq1hint2.getName());
		assertEquals("bbb", nnq1hint2.getValue());
		
		// test the second mapping file query
		OrmNamedNativeQuery nnq2 = selectModelNamed(namedNativeQueries, "nnq2");
		assertEquals("nnq2", (nnq2.getName()));
		assertEquals("efgh", (nnq2.getQuery()));
		assertEquals("foo2", nnq2.getResultClass());
		assertEquals("bar2", nnq2.getResultSetMapping());
		assertEquals(1, (nnq2.getHintsSize()));
		
		// test the query hints of the second mapping file query have the correct value
		OrmQueryHint nnq2hint1 = nnq2.getHints().iterator().next();
		
		// test the query hint of the second mapping file query
		assertEquals("nnq2hint1", nnq2hint1.getName());
		assertEquals("ccc", nnq2hint1.getValue());
		
	}
	

	// ************ entity with generators*************
	
	private ICompilationUnit createTestEntityWithGenerators() throws Exception {
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
		});
	}
	
	private ICompilationUnit createTestEntityWithGeneratorsOnIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.TABLE_GENERATOR, JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
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
	
	private ICompilationUnit createTestEntityWithDuplicateGenerators() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.TABLE_GENERATOR, JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@TableGenerator(name=\"gen\", table=\"ID_GEN\", schema=\"APP\", catalog=\"FOO\"," +
						"pkColumnName=\"GEN_NAME\", valueColumnName=\"GEN_VALUE\", pkColumnValue=\"TGEN\"," +
						"allocationSize=50, initialValue=100)").append(CR);
				sb.append("@SequenceGenerator(name=\"gen\", sequenceName=\"Foo_Seq\", schema=\"SYS\", catalog=\"BAR\"," +
						"allocationSize=5, initialValue=10)");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@TableGenerator(name=\"tgen\", table=\"ID_GEN\", schema=\"APP\", catalog=\"FOO\"," +
						"pkColumnName=\"GEN_NAME\", valueColumnName=\"GEN_VALUE\", pkColumnValue=\"TGEN\"," +
						"allocationSize=50, initialValue=100)").append(CR);
				sb.append("@SequenceGenerator(name=\"gen\", sequenceName=\"Foo_Seq\", schema=\"SYS\", catalog=\"BAR\"," +
						"allocationSize=5, initialValue=10)");
			}
		});
	}

	public void testTestConvertGenerators() throws Exception {
		createTestEntityWithGenerators();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaGenerators(entityMappings, progressMonitor);
		
		// test Java generators are converted to orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getSequenceGeneratorsSize());
		assertEquals(1, entityMappings.getTableGeneratorsSize());
		assertNull(entity.getGeneratorContainer().getSequenceGenerator());
		assertNull(entity.getGeneratorContainer().getTableGenerator());
		
		// test the mapping file generators have correct values
		OrmTableGenerator tableGen = entityMappings.getTableGenerators().iterator().next();
		assertEquals("tgen", tableGen.getName());
		assertEquals("ID_GEN", tableGen.getTableName());
		assertEquals("APP", tableGen.getSchema());
		assertEquals("FOO", tableGen.getCatalog());
		assertEquals("GEN_NAME", tableGen.getPkColumnName());
		assertEquals("GEN_VALUE", tableGen.getValueColumnName());
		assertEquals("TGEN", tableGen.getPkColumnValue());
		assertEquals(50, tableGen.getAllocationSize());
		assertEquals(100, tableGen.getInitialValue());
		
		OrmSequenceGenerator seqGen = entityMappings.getSequenceGenerators().iterator().next();
		assertEquals("sgen", seqGen.getName());
		assertEquals("Foo_Seq", seqGen.getSequenceName());
		assertEquals(5, seqGen.getAllocationSize());
		assertEquals(10, seqGen.getInitialValue());
		
		assertEquals("SYS", ((GenericOrmSequenceGenerator2_0)seqGen).getSchema());
		assertEquals("BAR", ((GenericOrmSequenceGenerator2_0)seqGen).getCatalog());
	}

	public void testTestConvertGeneratorsOnIdMapping() throws Exception {
		createTestEntityWithGeneratorsOnIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaGenerators(entityMappings, progressMonitor);
		
		// test Java generators are converted to orm.xml and removed from the Java entity
		assertEquals(1, entityMappings.getSequenceGeneratorsSize());
		assertEquals(1, entityMappings.getTableGeneratorsSize());
		
		JavaIdMapping mapping = (JavaIdMapping)entity.getIdAttribute().getMapping();
		assertNull(mapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(mapping.getGeneratorContainer().getTableGenerator());
		
		// test the mapping file generators have correct values
		OrmTableGenerator tableGen = entityMappings.getTableGenerators().iterator().next();
		assertEquals("tgen", tableGen.getName());
		assertEquals("ID_GEN", tableGen.getTableName());
		assertEquals("APP", tableGen.getSchema());
		assertEquals("FOO", tableGen.getCatalog());
		assertEquals("GEN_NAME", tableGen.getPkColumnName());
		assertEquals("GEN_VALUE", tableGen.getValueColumnName());
		assertEquals("TGEN", tableGen.getPkColumnValue());
		assertEquals(50, tableGen.getAllocationSize());
		assertEquals(100, tableGen.getInitialValue());
		
		OrmSequenceGenerator seqGen = entityMappings.getSequenceGenerators().iterator().next();
		assertEquals("sgen", seqGen.getName());
		assertEquals("Foo_Seq", seqGen.getSequenceName());
		assertEquals(5, seqGen.getAllocationSize());
		assertEquals(10, seqGen.getInitialValue());
		
		assertEquals("SYS", ((GenericOrmSequenceGenerator2_0)seqGen).getSchema());
		assertEquals("BAR", ((GenericOrmSequenceGenerator2_0)seqGen).getCatalog());
	}
	
	public void testConvertOverridenGenerators() throws Exception {
		createTestEntityWithDuplicateGenerators();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		OrmSequenceGenerator sequence = entityMappings.addSequenceGenerator();
		sequence.setName("gen");
		
		JavaEntity entity = getJavaEntity();		
		JavaIdMapping mapping = (JavaIdMapping)entity.getIdAttribute().getMapping();
		
		persistenceUnit.convertJavaGenerators(entityMappings, progressMonitor);
		
		// test overriden Java generators are not converted to orm.xml and not 
		// removed from the Java entity, but unique generator is
		assertNotNull(entity.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(entity.getGeneratorContainer().getTableGenerator());
		assertEquals(1, entityMappings.getSequenceGeneratorsSize());
		assertEquals(1, entityMappings.getTableGeneratorsSize());
		assertNotNull(mapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(mapping.getGeneratorContainer().getTableGenerator());
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
		
		// test overriden Java generators are not converted to orm.xml and not 
		// removed from the Java entity, but unique generator is
		assertNotNull(entity.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(entity.getGeneratorContainer().getTableGenerator());
		assertEquals(0, entityMappings.getSequenceGeneratorsSize());
		assertEquals(1, entityMappings.getTableGeneratorsSize());
		assertNotNull(mapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(mapping.getGeneratorContainer().getTableGenerator());
	}
}
