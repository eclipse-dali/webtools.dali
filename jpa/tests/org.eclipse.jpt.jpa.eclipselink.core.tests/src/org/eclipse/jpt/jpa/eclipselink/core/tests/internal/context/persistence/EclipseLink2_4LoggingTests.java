/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logging2_4;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class EclipseLink2_4LoggingTests extends EclipseLink2_0PersistenceUnitTestCase {
	private Logging2_0 logging;

	public static final String SQL_LOGGING_KEY = Logging2_0.ECLIPSELINK_SQL_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel SQL_LOGGING_TEST_VALUE = LoggingLevel.off;
	public static final LoggingLevel SQL_LOGGING_TEST_VALUE_2 = LoggingLevel.severe;

	public static final String TRANSACTION_LOGGING_KEY = Logging2_0.ECLIPSELINK_TRANSACTION_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel TRANSACTION_LOGGING_TEST_VALUE = LoggingLevel.warning;
	public static final LoggingLevel TRANSACTION_LOGGING_TEST_VALUE_2 = LoggingLevel.info;

	public static final String EVENT_LOGGING_KEY = Logging2_0.ECLIPSELINK_EVENT_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel EVENT_LOGGING_TEST_VALUE = LoggingLevel.config;
	public static final LoggingLevel EVENT_LOGGING_TEST_VALUE_2 = LoggingLevel.fine;

	public static final String CONNECTION_LOGGING_KEY = Logging2_0.ECLIPSELINK_CONNECTION_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel CONNECTION_LOGGING_TEST_VALUE = LoggingLevel.finer;
	public static final LoggingLevel CONNECTION_LOGGING_TEST_VALUE_2 = LoggingLevel.finest;

	public static final String QUERY_LOGGING_KEY = Logging2_0.ECLIPSELINK_QUERY_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel QUERY_LOGGING_TEST_VALUE = LoggingLevel.all;
	public static final LoggingLevel QUERY_LOGGING_TEST_VALUE_2 = LoggingLevel.off;

	public static final String CACHE_LOGGING_KEY = Logging2_0.ECLIPSELINK_CACHE_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel CACHE_LOGGING_TEST_VALUE = LoggingLevel.severe;
	public static final LoggingLevel CACHE_LOGGING_TEST_VALUE_2 = LoggingLevel.warning;

	public static final String PROPAGATION_LOGGING_KEY = Logging2_0.ECLIPSELINK_PROPAGATION_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel PROPAGATION_LOGGING_TEST_VALUE = LoggingLevel.info;
	public static final LoggingLevel PROPAGATION_LOGGING_TEST_VALUE_2 = LoggingLevel.config;

	public static final String SEQUENCING_LOGGING_KEY = Logging2_0.ECLIPSELINK_SEQUENCING_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel SEQUENCING_LOGGING_TEST_VALUE = LoggingLevel.fine;
	public static final LoggingLevel SEQUENCING_LOGGING_TEST_VALUE_2 = LoggingLevel.finer;

	public static final String EJB_LOGGING_KEY = Logging2_0.ECLIPSELINK_EJB_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel EJB_LOGGING_TEST_VALUE = LoggingLevel.finest;
	public static final LoggingLevel EJB_LOGGING_TEST_VALUE_2 = LoggingLevel.all;

	public static final String DMS_LOGGING_KEY = Logging2_0.ECLIPSELINK_DMS_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel DMS_LOGGING_TEST_VALUE = LoggingLevel.off;
	public static final LoggingLevel DMS_LOGGING_TEST_VALUE_2 = LoggingLevel.severe;

	public static final String WEAVER_LOGGING_KEY = Logging2_0.ECLIPSELINK_WEAVER_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel WEAVER_LOGGING_TEST_VALUE = LoggingLevel.finer;
	public static final LoggingLevel WEAVER_LOGGING_TEST_VALUE_2 = LoggingLevel.finest;

	public static final String PROPERTIES_LOGGING_KEY = Logging2_0.ECLIPSELINK_PROPERTIES_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel PROPERTIES_LOGGING_TEST_VALUE = LoggingLevel.all;
	public static final LoggingLevel PROPERTIES_LOGGING_TEST_VALUE_2 = LoggingLevel.off;

	public static final String SERVER_LOGGING_KEY = Logging2_0.ECLIPSELINK_SERVER_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel SERVER_LOGGING_TEST_VALUE = LoggingLevel.severe;
	public static final LoggingLevel SERVER_LOGGING_TEST_VALUE_2 = LoggingLevel.warning;

	// logging categories introduced by EclipseLink 2.4
	
	public static final String METADATA_LOGGING_KEY = Logging2_4.ECLIPSELINK_METADATA_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel METADATA_LOGGING_TEST_VALUE = LoggingLevel.warning;
	public static final LoggingLevel METADATA_LOGGING_TEST_VALUE_2 = LoggingLevel.info;

	public static final String METAMODEL_LOGGING_KEY = Logging2_4.ECLIPSELINK_METAMODEL_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel METAMODEL_LOGGING_TEST_VALUE = LoggingLevel.config;
	public static final LoggingLevel METAMODEL_LOGGING_TEST_VALUE_2 = LoggingLevel.fine;

	public static final String JPA_LOGGING_KEY = Logging2_4.ECLIPSELINK_JPA_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel JPA_LOGGING_TEST_VALUE = LoggingLevel.all;
	public static final LoggingLevel JPA_LOGGING_TEST_VALUE_2 = LoggingLevel.off;

	public static final String DDL_LOGGING_KEY = Logging2_4.ECLIPSELINK_DDL_CATEGORY_LOGGING_LEVEL;
	public static final LoggingLevel DDL_LOGGING_TEST_VALUE = LoggingLevel.severe;
	public static final LoggingLevel DDL_LOGGING_TEST_VALUE_2 = LoggingLevel.warning;

	// ********** constructors **********
	public EclipseLink2_4LoggingTests(String name) {
		super(name);
	}

	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaFacet.VERSION_2_0.getVersionString());	
		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM, EclipseLinkPlatform.VERSION_2_4);
		return dataModel;
	}
	
	// ********** behavior **********
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.logging = (Logging2_0) this.subject.getLogging();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.logging.addPropertyChangeListener(Logging2_0.SQL_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.TRANSACTION_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.EVENT_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.CONNECTION_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.QUERY_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.CACHE_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.PROPAGATION_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.SEQUENCING_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.EJB_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.DMS_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.WEAVER_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.PROPERTIES_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_0.SERVER_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);

		this.logging.addPropertyChangeListener(Logging2_4.METADATA_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_4.METAMODEL_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_4.JPA_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging2_4.DDL_CATEGORY_LOGGING_PROPERTY, propertyChangeListener);

		this.clearEvent();
	}

	/**
	 * Initializes directly the PersistenceUnit properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 15; // PersistenceUnit properties
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 1; // 1 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		// Initializes PersistenceUnit properties
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(SQL_LOGGING_KEY, SQL_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(TRANSACTION_LOGGING_KEY, TRANSACTION_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(EVENT_LOGGING_KEY, EVENT_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(CONNECTION_LOGGING_KEY, CONNECTION_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(QUERY_LOGGING_KEY, QUERY_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(CACHE_LOGGING_KEY, CACHE_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(PROPAGATION_LOGGING_KEY, PROPAGATION_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(SEQUENCING_LOGGING_KEY, SEQUENCING_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(EJB_LOGGING_KEY, EJB_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(DMS_LOGGING_KEY, DMS_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(WEAVER_LOGGING_KEY, WEAVER_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(PROPERTIES_LOGGING_KEY, PROPERTIES_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(SERVER_LOGGING_KEY, SERVER_LOGGING_TEST_VALUE);		
		
		this.persistenceUnitSetProperty(METADATA_LOGGING_KEY, METADATA_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(METAMODEL_LOGGING_KEY, METAMODEL_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(JPA_LOGGING_KEY, JPA_LOGGING_TEST_VALUE);
		this.persistenceUnitSetProperty(DDL_LOGGING_KEY, DDL_LOGGING_TEST_VALUE);

	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.logging;
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		
		return this.logging.getLevel(propertyName);
	}

	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {

		this.logging.setLevel(propertyName, (LoggingLevel) newValue);
	}
	
	// ********** Sql Level tests **********
	public void testSetSqlLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			SQL_LOGGING_KEY,
			SQL_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			SQL_LOGGING_KEY,
			SQL_LOGGING_TEST_VALUE,
			SQL_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveSqlLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			SQL_LOGGING_KEY,
			SQL_LOGGING_TEST_VALUE,
			SQL_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Transaction Level tests **********
	public void testSetTransactionLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			TRANSACTION_LOGGING_KEY,
			TRANSACTION_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			TRANSACTION_LOGGING_KEY,
			TRANSACTION_LOGGING_TEST_VALUE,
			TRANSACTION_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveTransactionLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			TRANSACTION_LOGGING_KEY,
			TRANSACTION_LOGGING_TEST_VALUE,
			TRANSACTION_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Event Level tests **********
	public void testSetEventLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			EVENT_LOGGING_KEY,
			EVENT_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			EVENT_LOGGING_KEY,
			EVENT_LOGGING_TEST_VALUE,
			EVENT_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveEventLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			EVENT_LOGGING_KEY,
			EVENT_LOGGING_TEST_VALUE,
			EVENT_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Connection Level tests **********
	public void testSetConnectionLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			CONNECTION_LOGGING_KEY,
			CONNECTION_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			CONNECTION_LOGGING_KEY,
			CONNECTION_LOGGING_TEST_VALUE,
			CONNECTION_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveConnectionLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			CONNECTION_LOGGING_KEY,
			CONNECTION_LOGGING_TEST_VALUE,
			CONNECTION_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Query Level tests **********
	public void testSetQueryLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			QUERY_LOGGING_KEY,
			QUERY_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			QUERY_LOGGING_KEY,
			QUERY_LOGGING_TEST_VALUE,
			QUERY_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveQueryLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			QUERY_LOGGING_KEY,
			QUERY_LOGGING_TEST_VALUE,
			QUERY_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Cache Level tests **********
	public void testSetCacheLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			CACHE_LOGGING_KEY,
			CACHE_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			CACHE_LOGGING_KEY,
			CACHE_LOGGING_TEST_VALUE,
			CACHE_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveCacheLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			CACHE_LOGGING_KEY,
			CACHE_LOGGING_TEST_VALUE,
			CACHE_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Propagation Level tests **********
	public void testSetPropagationLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			PROPAGATION_LOGGING_KEY,
			PROPAGATION_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			PROPAGATION_LOGGING_KEY,
			PROPAGATION_LOGGING_TEST_VALUE,
			PROPAGATION_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemovePropagationLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			PROPAGATION_LOGGING_KEY,
			PROPAGATION_LOGGING_TEST_VALUE,
			PROPAGATION_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Sequencing Level tests **********
	public void testSetSequencingLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			SEQUENCING_LOGGING_KEY,
			SEQUENCING_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			SEQUENCING_LOGGING_KEY,
			SEQUENCING_LOGGING_TEST_VALUE,
			SEQUENCING_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveSequencingLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			SEQUENCING_LOGGING_KEY,
			SEQUENCING_LOGGING_TEST_VALUE,
			SEQUENCING_LOGGING_TEST_VALUE_2);
	}
	
	// ********** EJB Level tests **********
	public void testSetEJBLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			EJB_LOGGING_KEY,
			EJB_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			EJB_LOGGING_KEY,
			EJB_LOGGING_TEST_VALUE,
			EJB_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveEJBLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			EJB_LOGGING_KEY,
			EJB_LOGGING_TEST_VALUE,
			EJB_LOGGING_TEST_VALUE_2);
	}
	
	// ********** DMS Level tests **********
	public void testSetDMSLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			DMS_LOGGING_KEY,
			DMS_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			DMS_LOGGING_KEY,
			DMS_LOGGING_TEST_VALUE,
			DMS_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveDMSLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			DMS_LOGGING_KEY,
			DMS_LOGGING_TEST_VALUE,
			DMS_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Weaver Level tests **********
	public void testSetWeaverLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			WEAVER_LOGGING_KEY,
			WEAVER_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			WEAVER_LOGGING_KEY,
			WEAVER_LOGGING_TEST_VALUE,
			WEAVER_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveWeaverLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			WEAVER_LOGGING_KEY,
			WEAVER_LOGGING_TEST_VALUE,
			WEAVER_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Properties Level tests **********
	public void testSetPropertiesLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			PROPERTIES_LOGGING_KEY,
			PROPERTIES_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			PROPERTIES_LOGGING_KEY,
			PROPERTIES_LOGGING_TEST_VALUE,
			PROPERTIES_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemovePropertiesLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			PROPERTIES_LOGGING_KEY,
			PROPERTIES_LOGGING_TEST_VALUE,
			PROPERTIES_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Server Level tests **********
	public void testSetServerLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			SERVER_LOGGING_KEY,
			SERVER_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			SERVER_LOGGING_KEY,
			SERVER_LOGGING_TEST_VALUE,
			SERVER_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveServerLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			SERVER_LOGGING_KEY,
			SERVER_LOGGING_TEST_VALUE,
			SERVER_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Metadata Level tests **********
	public void testSetMetadataLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			METADATA_LOGGING_KEY,
			METADATA_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			METADATA_LOGGING_KEY,
			METADATA_LOGGING_TEST_VALUE,
			METADATA_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveMetadataLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			METADATA_LOGGING_KEY,
			METADATA_LOGGING_TEST_VALUE,
			METADATA_LOGGING_TEST_VALUE_2);
	}
	
	// ********** Metamodel Level tests **********
	public void testSetMetamodelLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			METAMODEL_LOGGING_KEY,
			METAMODEL_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			METAMODEL_LOGGING_KEY,
			METAMODEL_LOGGING_TEST_VALUE,
			METAMODEL_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveMetamodelLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			METAMODEL_LOGGING_KEY,
			METAMODEL_LOGGING_TEST_VALUE,
			METAMODEL_LOGGING_TEST_VALUE_2);
	}

	// ********** JPA Level tests **********
	public void testSetJPALoggingLevel() throws Exception {
		this.verifyModelInitialized(
			JPA_LOGGING_KEY,
			JPA_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			JPA_LOGGING_KEY,
			JPA_LOGGING_TEST_VALUE,
			JPA_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveJPALoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			JPA_LOGGING_KEY,
			JPA_LOGGING_TEST_VALUE,
			JPA_LOGGING_TEST_VALUE_2);
	}
	
	// ********** DDL Level tests **********
	public void testSetDDLLoggingLevel() throws Exception {
		this.verifyModelInitialized(
			DDL_LOGGING_KEY,
			DDL_LOGGING_TEST_VALUE);
		this.verifySetProperty(
			DDL_LOGGING_KEY,
			DDL_LOGGING_TEST_VALUE,
			DDL_LOGGING_TEST_VALUE_2);
	}

	public void testAddRemoveDDLLoggingLevel() throws Exception {
		this.verifyAddRemoveProperty(
			DDL_LOGGING_KEY,
			DDL_LOGGING_TEST_VALUE,
			DDL_LOGGING_TEST_VALUE_2);
	}
}
