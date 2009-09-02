/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.jpa2.resource.orm.JPA;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Mapped Superclass</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlMappedSuperclass()
 * @model kind="class"
 * @generated
 */
public class XmlMappedSuperclass extends org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlMappedSuperclass
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlMappedSuperclass()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return EclipseLink2_0OrmPackage.Literals.XML_MAPPED_SUPERCLASS;
	}
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLink2_0OrmPackage.eINSTANCE.getXmlMappedSuperclass(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildClassTranslator(),
			buildAccessTranslator(),
			buildCacheableTranslator(),
			buildMetadataCompleteTranslator(),
			buildReadOnlyTranslator(),
			buildExistenceCheckingTranslator(),
			buildExcludeDefaultMappingsTranslator(),
			buildDescriptionTranslator(),
			buildCustomizerTranslator(),
			buildChangeTrackingTranslator(),
			buildIdClassTranslator(),
			buildPrimaryKeyTranslator(),
			buildOptimisticLockingTranslator(),
			buildCacheTranslator(),
			buildConverterTranslator(),
			buildTypeConverterTranslator(),
			buildObjectTypeConverterTranslator(),
			buildStructConverterTranslator(),
			buildCopyPolicyTranslator(),
			buildInstantiationCoypPolicyTranslator(),
			buildCloneCopyPolicyTranslator(),
			buildExcludeDefaultListenersTranslator(),
			buildExcludeSuperclassListenersTranslator(),
			buildEntityListenersTranslator(),
			buildPrePersistTranslator(),
			buildPostPersistTranslator(),
			buildPreRemoveTranslator(),
			buildPostRemoveTranslator(),
			buildPreUpdateTranslator(),
			buildPostUpdateTranslator(),
			buildPostLoadTranslator(),
			buildPropertyTranslator(),
			Attributes.buildTranslator()
		};
	}
	
	protected static Translator buildCacheableTranslator() {
		return new Translator(JPA.CACHEABLE, Orm2_0Package.eINSTANCE.getXmlMappedSuperclass_Cacheable(), Translator.DOM_ATTRIBUTE);
	}

} // XmlMappedSuperclass
