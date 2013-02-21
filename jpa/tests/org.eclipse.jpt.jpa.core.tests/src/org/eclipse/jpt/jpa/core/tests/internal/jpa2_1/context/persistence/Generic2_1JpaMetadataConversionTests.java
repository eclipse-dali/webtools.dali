/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.persistence;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.ParameterMode2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.QueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.Generic2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class Generic2_1JpaMetadataConversionTests
	extends Generic2_1ContextModelTestCase
{
	private IProgressMonitor progressMonitor;

	public Generic2_1JpaMetadataConversionTests(String name) {
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
	
	private ICompilationUnit createTestEntityWithNamedStoredProcedureQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY, 
						JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER, JPA2_1.PARAMETER_MODE,  JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedStoredProcedureQueries({" +
						"@NamedStoredProcedureQuery(" +
							"name=\"nq1\", procedureName=\"abcd\", " +
							"parameters={@StoredProcedureParameter(name=\"nq1parameter1\", mode=ParameterMode.INOUT, type=String.class), " +
												"@StoredProcedureParameter(name=\"nq1parameter2\", mode=ParameterMode.OUT, type=Integer.class)}, " +
							"resultClasses={Employee.class, Address.class}, " +
							"resultSetMappings={\"EmployeeRSM\", \"AddressRSM\"}, " +
							"hints={@QueryHint(name=\"nq1hint1\", value = \"aaa\"), @QueryHint(name=\"nq1hint2\", value=\"bbb\")}), " +
						"@NamedStoredProcedureQuery(" +
							"name=\"nq2\", procedureName=\"efgh\"," +
							"parameters=@StoredProcedureParameter(name=\"nq2parameter1\", mode=ParameterMode.INOUT, type=Boolean.class)," +
							"resultClasses=Project.class," +
							"resultSetMappings=\"ProjectRSM\"," +
							"hints=@QueryHint(name=\"nq2hint1\", value=\"ccc\"))})");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name=\"nq\"), @NamedStoredProcedureQuery(name=\"query\")})").append(CR);
				sb.append("@NamedNativeQuery(name=\"nnq\")");
			}
		});
	}

	private ICompilationUnit createTestEntityWithDuplicateQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY, JPA.NAMED_NATIVE_QUERIES, JPA.NAMED_NATIVE_QUERY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name=\"query\"), @NamedStoredProcedureQuery(name=\"nspq\")})");
				sb.append("@NamedNativeQueries({@NamedNativeQuery(name=\"query\"), @NamedNativeQuery(name=\"nnq\")})");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperWithNamedStoredProcedureQueries() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, JPA2_1.NAMED_STORED_PROCEDURE_QUERIES, JPA2_1.NAMED_STORED_PROCEDURE_QUERY, 
						JPA2_1.NAMED_STORED_PROCEDURE_PARAMETER, JPA2_1.PARAMETER_MODE,  JPA.QUERY_HINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@NamedStoredProcedureQueries({" +
						"@NamedStoredProcedureQuery(" +
							"name=\"nq1\", procedureName=\"abcd\", " +
							"parameters={@StoredProcedureParameter(name=\"nq1parameter1\", mode=ParameterMode.INOUT, type=String.class), " +
												"@StoredProcedureParameter(name=\"nq1parameter2\", mode=ParameterMode.OUT, type=Integer.class)}, " +
							"resultClasses={Employee.class, Address.class}, " +
							"resultSetMappings={\"EmployeeRSM\", \"AddressRSM\"}, " +
							"hints={@QueryHint(name=\"nq1hint1\", value = \"aaa\"), @QueryHint(name=\"nq1hint2\", value=\"bbb\")}), " +
						"@NamedStoredProcedureQuery(" +
							"name=\"nq2\", procedureName=\"efgh\"," +
							"parameters=@StoredProcedureParameter(name=\"nq2parameter1\", mode=ParameterMode.INOUT, type=Boolean.class)," +
							"resultClasses=Project.class," +
							"resultSetMappings=\"ProjectRSM\"," +
							"hints=@QueryHint(name=\"nq2hint1\", value=\"ccc\"))})");
			}
		});
	}
	

	public void testConvertNamedStoredProceduerQueriesOnEntity() throws Exception {
		createTestEntityWithNamedStoredProcedureQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test Java queries are converted to orm.xml and removed from the Java entity
		assertEquals(2, ((QueryContainer2_1) entityMappings.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		assertEquals(0, ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		
		// test the mapping file queries have correct values
		ListIterator<OrmNamedStoredProcedureQuery2_1> procedureQueries= ((OrmQueryContainer2_1) entityMappings.getQueryContainer()).getNamedStoredProcedureQueries().iterator();
		OrmNamedStoredProcedureQuery2_1 procedureQuery1 = procedureQueries.next();
		
		// ----- the first mapping file query -----
		
		assertEquals("nq1", (procedureQuery1.getName()));
		assertEquals("abcd", (procedureQuery1.getProcedureName()));
		assertEquals(2,  procedureQuery1.getParametersSize());
		assertEquals(2,  procedureQuery1.getResultClassesSize());
		assertEquals(2,  procedureQuery1.getResultSetMappingsSize());
		assertEquals(2, (procedureQuery1.getHintsSize()));
		
		// test the parameters of the first mapping file query have the correct value
		ListIterator<OrmStoredProcedureParameter2_1> nq1prameters = procedureQuery1.getParameters().iterator();
		
			// test the first parameter of the first mapping file query
			OrmStoredProcedureParameter2_1 nq1parameter1 = nq1prameters.next();
			assertEquals("nq1parameter1", nq1parameter1.getName());
			assertEquals(ParameterMode2_1.INOUT, nq1parameter1.getMode());
			assertEquals("String", nq1parameter1.getTypeName());

			// test the second parameter hint of the first mapping file query
			OrmStoredProcedureParameter2_1 nq1parameter2 = nq1prameters.next();
			assertEquals("nq1parameter2", nq1parameter2.getName());
			assertEquals(ParameterMode2_1.OUT, nq1parameter2.getMode());
			assertEquals("Integer", nq1parameter2.getTypeName());
		
		// test the result classes of the first mapping file query have the correct value
		ListIterator<String> nq1ResultClasses = procedureQuery1.getResultClasses().iterator();
		assertEquals("Employee", nq1ResultClasses.next());
		assertEquals("Address", nq1ResultClasses.next());
		
		// test the result classes of the first mapping file query have the correct value
		ListIterator<String> nq1ResultSetMappings = procedureQuery1.getResultSetMappings().iterator();
		assertEquals("EmployeeRSM", nq1ResultSetMappings.next());
		assertEquals("AddressRSM", nq1ResultSetMappings.next());

		// test the query hints of the first mapping file query have the correct value
		ListIterator<OrmQueryHint> nq1hints = procedureQuery1.getHints().iterator();
		
			// test the first query hint of the first mapping file query
			OrmQueryHint nq1hint1 = nq1hints.next();
			assertEquals("nq1hint1", nq1hint1.getName());
			assertEquals("aaa", nq1hint1.getValue());
		
			// test the second query hint of the first mapping file query
			OrmQueryHint nq1hint2 = nq1hints.next();
			assertEquals("nq1hint2", nq1hint2.getName());
			assertEquals("bbb", nq1hint2.getValue());
		
		// ----- the second mapping file query -----
		OrmNamedStoredProcedureQuery2_1 procedureQuery2 = procedureQueries.next();
		assertEquals("nq2", (procedureQuery2.getName()));
		assertEquals("efgh", (procedureQuery2.getProcedureName()));
		assertEquals(1,  procedureQuery2.getParametersSize());
		assertEquals(1,  procedureQuery2.getResultClassesSize());
		assertEquals(1,  procedureQuery2.getResultSetMappingsSize());
		assertEquals(1, (procedureQuery2.getHintsSize()));
		
		// test the parameter of the second mapping file query have the correct value
		OrmStoredProcedureParameter2_1 nq2parameter = procedureQuery2.getParameters().iterator().next();
		assertEquals("nq2parameter1", nq2parameter.getName());
		assertEquals(ParameterMode2_1.INOUT, nq2parameter.getMode());
		assertEquals("Boolean", nq2parameter.getTypeName());

		// test the result class of the second mapping file query have the correct value
		String nq2ResultClass = procedureQuery2.getResultClasses().iterator().next();
		assertEquals("Project", nq2ResultClass);
		
		// test the result set mapping of the second mapping file query have the correct value
		String nq2ResultSetMapping = procedureQuery2.getResultSetMappings().iterator().next();
		assertEquals("ProjectRSM", nq2ResultSetMapping);
		
		// test the query hint of the second mapping file query have the correct value
		OrmQueryHint nq2hint1 = procedureQuery2.getHints().iterator().next();
		assertEquals("nq2hint1", nq2hint1.getName());
		assertEquals("ccc", nq2hint1.getValue());
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
		assertEquals(1, ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		assertEquals(1, entity.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(1, ((QueryContainer2_1) entityMappings.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		assertEquals(1, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(1, entityMappings.getQueryContainer().getNamedQueriesSize());
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
		assertEquals(1, ((QueryContainer2_1) entity.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		assertEquals(1, entity.getQueryContainer().getNamedNativeQueriesSize());
		assertEquals(1, ((QueryContainer2_1) entityMappings.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		assertEquals(1, entityMappings.getQueryContainer().getNamedNativeQueriesSize());
	}
	
	public void testConvertNamedStoredProcedureQueriesOnMappedSuperclass() throws Exception {
		createTestMappedSuperWithNamedStoredProcedureQueries();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaMappedSuperclass mappedSuperclass = (JavaMappedSuperclass) getJavaPersistentType().getMapping();
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		EntityMappings entityMappings = getEntityMappings();
		
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		persistenceUnit.convertJavaQueries(entityMappings, progressMonitor);
		
		// test Java queries are converted to orm.xml and removed from the Java entity
		assertEquals(2, ((QueryContainer2_1) entityMappings.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		assertEquals(0, ((QueryContainer2_1) mappedSuperclass.getQueryContainer()).getNamedStoredProcedureQueriesSize());
		
		// test the mapping file queries have correct values
		ListIterator<OrmNamedStoredProcedureQuery2_1> procedureQueries= ((OrmQueryContainer2_1) entityMappings.getQueryContainer()).getNamedStoredProcedureQueries().iterator();
		OrmNamedStoredProcedureQuery2_1 procedureQuery1 = procedureQueries.next();
		
		// ----- the first mapping file query -----
		
		assertEquals("nq1", (procedureQuery1.getName()));
		assertEquals("abcd", (procedureQuery1.getProcedureName()));
		assertEquals(2,  procedureQuery1.getParametersSize());
		assertEquals(2,  procedureQuery1.getResultClassesSize());
		assertEquals(2,  procedureQuery1.getResultSetMappingsSize());
		assertEquals(2, (procedureQuery1.getHintsSize()));
		
		// test the parameters of the first mapping file query have the correct value
		ListIterator<OrmStoredProcedureParameter2_1> nq1prameters = procedureQuery1.getParameters().iterator();
		
			// test the first parameter of the first mapping file query
			OrmStoredProcedureParameter2_1 nq1parameter1 = nq1prameters.next();
			assertEquals("nq1parameter1", nq1parameter1.getName());
			assertEquals(ParameterMode2_1.INOUT, nq1parameter1.getMode());
			assertEquals("String", nq1parameter1.getTypeName());

			// test the second parameter hint of the first mapping file query
			OrmStoredProcedureParameter2_1 nq1parameter2 = nq1prameters.next();
			assertEquals("nq1parameter2", nq1parameter2.getName());
			assertEquals(ParameterMode2_1.OUT, nq1parameter2.getMode());
			assertEquals("Integer", nq1parameter2.getTypeName());
		
		// test the result classes of the first mapping file query have the correct value
		ListIterator<String> nq1ResultClasses = procedureQuery1.getResultClasses().iterator();
		assertEquals("Employee", nq1ResultClasses.next());
		assertEquals("Address", nq1ResultClasses.next());
		
		// test the result classes of the first mapping file query have the correct value
		ListIterator<String> nq1ResultSetMappings = procedureQuery1.getResultSetMappings().iterator();
		assertEquals("EmployeeRSM", nq1ResultSetMappings.next());
		assertEquals("AddressRSM", nq1ResultSetMappings.next());

		// test the query hints of the first mapping file query have the correct value
		ListIterator<OrmQueryHint> nq1hints = procedureQuery1.getHints().iterator();
		
			// test the first query hint of the first mapping file query
			OrmQueryHint nq1hint1 = nq1hints.next();
			assertEquals("nq1hint1", nq1hint1.getName());
			assertEquals("aaa", nq1hint1.getValue());
		
			// test the second query hint of the first mapping file query
			OrmQueryHint nq1hint2 = nq1hints.next();
			assertEquals("nq1hint2", nq1hint2.getName());
			assertEquals("bbb", nq1hint2.getValue());
		
		// ----- the second mapping file query -----
		OrmNamedStoredProcedureQuery2_1 procedureQuery2 = procedureQueries.next();
		assertEquals("nq2", (procedureQuery2.getName()));
		assertEquals("efgh", (procedureQuery2.getProcedureName()));
		assertEquals(1,  procedureQuery2.getParametersSize());
		assertEquals(1,  procedureQuery2.getResultClassesSize());
		assertEquals(1,  procedureQuery2.getResultSetMappingsSize());
		assertEquals(1, (procedureQuery2.getHintsSize()));
		
		// test the parameter of the second mapping file query have the correct value
		OrmStoredProcedureParameter2_1 nq2parameter = procedureQuery2.getParameters().iterator().next();
		assertEquals("nq2parameter1", nq2parameter.getName());
		assertEquals(ParameterMode2_1.INOUT, nq2parameter.getMode());
		assertEquals("Boolean", nq2parameter.getTypeName());

		// test the result class of the second mapping file query have the correct value
		String nq2ResultClass = procedureQuery2.getResultClasses().iterator().next();
		assertEquals("Project", nq2ResultClass);
		
		// test the result set mapping of the second mapping file query have the correct value
		String nq2ResultSetMapping = procedureQuery2.getResultSetMappings().iterator().next();
		assertEquals("ProjectRSM", nq2ResultSetMapping);
		
		// test the query hint of the second mapping file query have the correct value
		OrmQueryHint nq2hint1 = procedureQuery2.getHints().iterator().next();
		assertEquals("nq2hint1", nq2hint1.getName());
		assertEquals("ccc", nq2hint1.getValue());
	}
}
