/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence.resource;

import org.eclipse.jpt.core.internal.content.persistence.PersistencePackage;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.GenericTranslator;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PersistenceTranslator extends RootTranslator
	implements PersistenceXMLMapper
{
	public static PersistenceTranslator INSTANCE = new PersistenceTranslator();
	
	private static Translator[] children;
	
	private static PersistencePackage PERSISTENCE_PKG = PersistencePackage.eINSTANCE;
	
	
	public PersistenceTranslator() {
		super(PERSISTENCE, PersistencePackage.eINSTANCE.getPersistence());
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	private static Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createVersionTranslator(),
			new ConstantAttributeTranslator(XML_NS, PERSISTENCE_NS_URL),
			new ConstantAttributeTranslator(XML_NS_XSI, XSI_NS_URL),
			new ConstantAttributeTranslator(XSI_SCHEMA_LOCATION, PERSISTENCE_NS_URL + ' ' + PERSISTENCE_SCHEMA_LOC_1_0),
			createPersistenceUnitTranslator()
		};
	}
	
	private static Translator createVersionTranslator() {
		return new Translator(PERSISTENCE_VERSION, PERSISTENCE_PKG.getPersistence_Version(), DOM_ATTRIBUTE);
	}
	
	private static Translator createPersistenceUnitTranslator() {
		GenericTranslator translator = new GenericTranslator(PERSISTENCE_UNIT, PERSISTENCE_PKG.getPersistence_PersistenceUnits());
		translator.setChildren(
			new Translator[] {
				IDTranslator.INSTANCE,
				createPersistenceUnitNameTranslator(),
				createTransactionTypeTranslator(),
				createPersistenceUnitDescriptionTranslator(),
				createPersistenceUnitProviderTranslator(),
				createJtaDataSourceTranslator(),
				createNonJtaDataSourceTranslator(),
				createMappingFileTranslator(),
				createJarFileTranslator(),
				createClassTranslator(),
				createExcludeUnlistedClassesTranslator(),
				createPropertiesTranslator()
			}
		);
		return translator;
	}
	
	private static Translator createPersistenceUnitNameTranslator() {
		return new Translator(PERSISTENCE_UNIT_NAME, PERSISTENCE_PKG.getPersistenceUnit_Name(), DOM_ATTRIBUTE);
	}
	
	private static Translator createTransactionTypeTranslator() {
		return new TransactionTypeTranslator();
	}
	
	private static Translator createPersistenceUnitDescriptionTranslator() {
		return new Translator(PERSISTENCE_UNIT_DESCRIPTION, PERSISTENCE_PKG.getPersistenceUnit_Description());
	}
	
	private static Translator createPersistenceUnitProviderTranslator() {
		return new Translator(PERSISTENCE_UNIT_PROVIDER, PERSISTENCE_PKG.getPersistenceUnit_Provider(), UNSET_IF_NULL);
	}
	
	private static Translator createJtaDataSourceTranslator() {
		return new Translator(JTA_DATA_SOURCE, PERSISTENCE_PKG.getPersistenceUnit_JtaDataSource());
	}
	
	private static Translator createNonJtaDataSourceTranslator() {
		return new Translator(NON_JTA_DATA_SOURCE, PERSISTENCE_PKG.getPersistenceUnit_NonJtaDataSource());
	}
	
	private static Translator createMappingFileTranslator() {
		return new MappingFileTranslator(MAPPING_FILE, PERSISTENCE_PKG.getPersistenceUnit_MappingFiles());
	}
	
	private static Translator createJarFileTranslator() {
		return new Translator(JAR_FILE, PERSISTENCE_PKG.getPersistenceUnit_JarFiles());
	}
	
	private static Translator createClassTranslator() {
		return new JavaClassRefTranslator(CLASS, PERSISTENCE_PKG.getPersistenceUnit_Classes());
	}
	
	private static Translator createExcludeUnlistedClassesTranslator() {
		return new BooleanTranslator(EXCLUDE_UNLISTED_CLASSES, PERSISTENCE_PKG.getPersistenceUnit_ExcludeUnlistedClasses(), UNSET_IF_NULL);
	}
	
	private static Translator createPropertiesTranslator() {
		GenericTranslator translator = new GenericTranslator(PROPERTIES, PERSISTENCE_PKG.getPersistenceUnit_Properties());
		translator.setChildren(
			new Translator[] {
				createPropertyTranslator()
			}
		);
		return translator;
	}
	
	private static Translator createPropertyTranslator() {
		GenericTranslator translator = new GenericTranslator(PROPERTY, PERSISTENCE_PKG.getProperties_Properties(), END_TAG_NO_INDENT);
		translator.setChildren(
			new Translator[] {
				createPropertyNameTranslator(),
				createPropertyValueTranslator()
			}
		);
		return translator;
	}
	
	private static Translator createPropertyNameTranslator() {
		return new Translator(PROPERTY_NAME, PERSISTENCE_PKG.getProperty_Name(), DOM_ATTRIBUTE);
	}
	
	private static Translator createPropertyValueTranslator() {
		return new Translator(PROPERTY_VALUE, PERSISTENCE_PKG.getProperty_Value(), DOM_ATTRIBUTE);
	}
}
