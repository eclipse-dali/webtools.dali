/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.EventMethod;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage#getXmlEntity()
 * @model kind="class"
 * @generated
 */
public class XmlEntity extends org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEntity()
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
		return EclipseLink1_1OrmPackage.Literals.XML_ENTITY;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLink1_1OrmPackage.eINSTANCE.getXmlEntity(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildClassTranslator(),
			buildAccessTranslator(),
			buildMetadataCompleteTranslator(),
			buildReadOnlyTranslator(),
			buildExistenceCheckingTranslator(),
			buildExcludeDefaultMappingsTranslator(),
			buildDescriptionTranslator(),
			buildCustomizerTranslator(),
			buildChangeTrackingTranslator(),
			buildTableTranslator(),
			buildSecondaryTableTranslator(),
			buildPrimaryKeyJoinColumnTranslator(),
			buildIdClassTranslator(),
			buildInheritanceTranslator(),
			buildDiscriminatorValueTranslator(),
			buildDiscriminatorColumnTranslator(),
			buildOptimisticLockingTranslator(),
			buildCacheTranslator(),
			buildConverterTranslator(),
			buildTypeConverterTranslator(),
			buildObjectTypeConverterTranslator(),
			buildStructConverterTranslator(),
			buildCopyPolicyTranslator(),
			buildInstantiationCoypPolicyTranslator(),
			buildCloneCopyPolicyTranslator(),
			buildSequenceGeneratorTranslator(),
			buildSequenceGeneratorTranslator(),
			buildTableGeneratorTranslator(),
			buildNamedQueryTranslator(),
			buildNamedNativeQueryTranslator(),
			buildNamedStoredProcedureQueryTranslator(),
			buildSqlResultSetMappingTranslator(),
			buildExcludeDefaultListenersTranslator(),
			buildExcludeSuperclassListenersTranslator(),
			buildEntityListenersTranslator(),
			EventMethod.buildTranslator(JPA.PRE_PERSIST, OrmPackage.eINSTANCE.getXmlEntity_PrePersist()),
			EventMethod.buildTranslator(JPA.POST_PERSIST, OrmPackage.eINSTANCE.getXmlEntity_PostPersist()),
			EventMethod.buildTranslator(JPA.PRE_REMOVE, OrmPackage.eINSTANCE.getXmlEntity_PreRemove()),
			EventMethod.buildTranslator(JPA.POST_REMOVE, OrmPackage.eINSTANCE.getXmlEntity_PostRemove()),
			EventMethod.buildTranslator(JPA.PRE_UPDATE, OrmPackage.eINSTANCE.getXmlEntity_PreUpdate()),
			EventMethod.buildTranslator(JPA.POST_UPDATE, OrmPackage.eINSTANCE.getXmlEntity_PostUpdate()),
			EventMethod.buildTranslator(JPA.POST_LOAD, OrmPackage.eINSTANCE.getXmlEntity_PostLoad()),
			buildPropertyTranslator(),
			buildAttributeOverrideTranslator(),
			buildAssociationOverrideTranslator(),
			Attributes.buildTranslator(JPA.ATTRIBUTES, OrmPackage.eINSTANCE.getXmlTypeMapping_Attributes())
		};
	}

} // XmlEntity
