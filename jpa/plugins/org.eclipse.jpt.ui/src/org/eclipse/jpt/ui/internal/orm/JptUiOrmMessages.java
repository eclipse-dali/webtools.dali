/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.orm;

import org.eclipse.osgi.util.NLS;

/**
 * The localized messages used by the ORM widgets.
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class JptUiOrmMessages {

	public static String AccessTypeComposite_access;
	public static String AccessTypeComposite_field;
	public static String AccessTypeComposite_property;
	public static String Boolean_False;
	public static String Boolean_True;
	public static String EntityMappingsDetailsPage_access;
	public static String EntityMappingsDetailsPage_catalog;
	public static String EntityMappingsDetailsPage_field;
	public static String EntityMappingsDetailsPage_package;
	public static String EntityMappingsDetailsPage_property;
	public static String EntityMappingsDetailsPage_schema;
	public static String EntityMappingsPage_catalogDefault;
	public static String EntityMappingsPage_catalogNoDefaultSpecified;
	public static String EntityMappingsPage_schemaDefault;
	public static String EntityMappingsPage_schemaNoDefaultSpecified;
	public static String OrmJavaAttributeChooser_javaAttribute;
	public static String OrmJavaClassChooser_javaClass;
	public static String OrmPersistentTypeDetailsPage_metadataComplete;
	public static String OrmPersistentTypeDetailsPage_metadataCompleteWithDefault;
	public static String PersistenceUnitMetadataComposite_access;
	public static String PersistenceUnitMetadataComposite_cascadePersistCheckBox;
	public static String PersistenceUnitMetadataComposite_catalog;
	public static String PersistenceUnitMetadataComposite_field;
	public static String PersistenceUnitMetadataComposite_persistenceUnitSection;
	public static String PersistenceUnitMetadataComposite_property;
	public static String PersistenceUnitMetadataComposite_schema;
	public static String PersistenceUnitMetadataComposite_xmlMappingMetadataCompleteCheckBox;
	public static String PersistenceUnitMetadataSection_catalogDefault;
	public static String PersistenceUnitMetadataSection_schemaDefault;

	static {
		NLS.initializeMessages("jpt_ui_orm", JptUiOrmMessages.class);
	}

	private JptUiOrmMessages() {
		throw new UnsupportedOperationException();
	}
}