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

public class JptUiOrmMessages
{
	private static final String BUNDLE_NAME = "jpt_ui_orm"; //$NON-NLS-1$




	public static String PersistentTypePage_javaClassLabel;
	public static String PersistentTypePage_MetadataCompleteLabel;
	public static String PersistentTypePage_AccessLabel;

	public static String PersistenceUnitMetadataSection_SchemaDefault;
	public static String PersistenceUnitMetadataSection_CatalogDefault;

	public static String PersistentAttributePage_javaAttributeLabel;

	public static String EntityMappingsDetailsPage_package;

	public static String EntityMappingsPage_XmlMappingMetadataCompleteCheckBox;
	public static String EntityMappingsPage_CascadePersistCheckBox;
	public static String EntityMappingsPage_PersistenceUnitSection;
	public static String EntityMappingsPage_SchemaDefault;
	public static String EntityMappingsPage_SchemaNoDefaultSpecified;
	public static String EntityMappingsPage_CatalogDefault;
	public static String EntityMappingsPage_CatalogNoDefaultSpecified;

	public static String OrmSchemaChooser_SchemaChooser;
	public static String OrmCatalogChooser_CatalogChooser;

	public static String OrmJavaClassChooser_browse;
	public static String OrmJavaClassChooser_XmlJavaClassDialog_title;
	public static String OrmJavaClassChooser_XmlJavaClassDialog_message;
	public static String OrmPackageChooser_PackageDialog_title;
	public static String OrmPackageChooser_PackageDialog_message;

	public static String AccessTypeComposite_field;
	public static String AccessTypeComposite_property;

	public static String EntityMappingsDetailsPage_field;
	public static String EntityMappingsDetailsPage_property;

	public static String PersistenceUnitMetadataComposite_field;
	public static String PersistenceUnitMetadataComposite_property;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, JptUiOrmMessages.class);
	}

	private JptUiOrmMessages() {
		throw new UnsupportedOperationException();
	}
}
