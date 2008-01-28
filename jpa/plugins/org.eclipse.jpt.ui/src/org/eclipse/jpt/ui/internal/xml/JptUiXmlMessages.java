/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml;

import org.eclipse.osgi.util.NLS;

public class JptUiXmlMessages
{
	private static final String BUNDLE_NAME = "jpt_ui_xml"; //$NON-NLS-1$


	public static String PersistentTypePage_javaClassLabel;
	public static String PersistentTypePage_MetadataCompleteLabel;
	public static String PersistentTypePage_AccessLabel;

	public static String PersistenceUnitMetadataSection_SchemaDefault;
	public static String PersistenceUnitMetadataSection_CatalogDefault;

	public static String PersistentAttributePage_javaAttributeLabel;

	public static String XmlEntityMappingsDetailsPage_package;

	public static String XMLEntityMappingsPage_XmlMappingMetadataCompleteCheckBox;
	public static String XMLEntityMappingsPage_CascadePersistCheckBox;
	public static String XMLEntityMappingsPage_PersistenceUnitSection;
	public static String XMLEntityMappingsPage_SchemaDefault;
	public static String XMLEntityMappingsPage_SchemaNoDefaultSpecified;
	public static String XMLEntityMappingsPage_CatalogDefault;
	public static String XMLEntityMappingsPage_CatalogNoDefaultSpecified;

	public static String XmlSchemaChooser_SchemaChooser;
	public static String XmlCatalogChooser_CatalogChooser;

	public static String XmlJavaClassChooser_XmlJavaClassDialog_title;
	public static String XmlJavaClassChooser_XmlJavaClassDialog_message;
	public static String XmlPackageChooser_PackageDialog_title;
	public static String XmlPackageChooser_PackageDialog_message;


	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, JptUiXmlMessages.class);
	}

	private JptUiXmlMessages() {
		throw new UnsupportedOperationException();
	}
}
