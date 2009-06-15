/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Mapped Superclass</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt2_0.core.resource.orm.Orm2_0Package#getXmlMappedSuperclass()
 * @model kind="class"
 * @generated
 */
public class XmlMappedSuperclass extends org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass
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
		return Orm2_0Package.Literals.XML_MAPPED_SUPERCLASS;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			Orm2_0Package.eINSTANCE.getXmlMappedSuperclass(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildClassTranslator(),
			buildAccessTranslator(),
			buildMetadataCompleteTranslator(),
			buildDescriptionTranslator(),
			buildIdClassTranslator(),
			buildExcludeDefaultListenersTranslator(),
			buildExcludeSuperclassListenersTranslator(),
			buildEntityListenersTranslator(),
			PrePersist.buildTranslator(JPA.PRE_PERSIST, OrmPackage.eINSTANCE.getXmlMappedSuperclass_PrePersist()),
			PostPersist.buildTranslator(JPA.POST_PERSIST, OrmPackage.eINSTANCE.getXmlMappedSuperclass_PostPersist()),
			PreRemove.buildTranslator(JPA.PRE_REMOVE, OrmPackage.eINSTANCE.getXmlMappedSuperclass_PreRemove()),
			PostRemove.buildTranslator(JPA.POST_REMOVE, OrmPackage.eINSTANCE.getXmlMappedSuperclass_PostRemove()),
			PreUpdate.buildTranslator(JPA.PRE_UPDATE, OrmPackage.eINSTANCE.getXmlMappedSuperclass_PreUpdate()),
			PostUpdate.buildTranslator(JPA.POST_UPDATE, OrmPackage.eINSTANCE.getXmlMappedSuperclass_PostUpdate()),
			PostLoad.buildTranslator(JPA.POST_LOAD, OrmPackage.eINSTANCE.getXmlMappedSuperclass_PostLoad()),
			Attributes.buildTranslator(JPA.ATTRIBUTES, OrmPackage.eINSTANCE.getXmlTypeMapping_Attributes())
		};
	}

} // XmlMappedSuperclass
