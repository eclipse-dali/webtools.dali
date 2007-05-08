/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaEObject;
import org.eclipse.jpt.core.internal.IJpaRootContentNode;
import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.IXmlEObject;
import org.eclipse.jpt.core.internal.JpaEObject;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn;
import org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn;
import org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery;
import org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable;
import org.eclipse.jpt.core.internal.content.orm.EntityMappings;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal;
import org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.internal.content.orm.XmlBasic;
import org.eclipse.jpt.core.internal.content.orm.XmlColumn;
import org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn;
import org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable;
import org.eclipse.jpt.core.internal.content.orm.XmlEmbedded;
import org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.internal.content.orm.XmlEntity;
import org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml;
import org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal;
import org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.internal.content.orm.XmlGenerator;
import org.eclipse.jpt.core.internal.content.orm.XmlId;
import org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn;
import org.eclipse.jpt.core.internal.content.orm.XmlJoinTable;
import org.eclipse.jpt.core.internal.content.orm.XmlManyToMany;
import org.eclipse.jpt.core.internal.content.orm.XmlManyToOne;
import org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal;
import org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery;
import org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToMany;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToOne;
import org.eclipse.jpt.core.internal.content.orm.XmlOrderBy;
import org.eclipse.jpt.core.internal.content.orm.XmlOverride;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.content.orm.XmlQueryHint;
import org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode;
import org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlTable;
import org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator;
import org.eclipse.jpt.core.internal.content.orm.XmlTransient;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.internal.content.orm.XmlVersion;
import org.eclipse.jpt.core.internal.mappings.IAbstractColumn;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.core.internal.mappings.IEmbeddedId;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.IGenerator;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IManyToMany;
import org.eclipse.jpt.core.internal.mappings.IManyToOne;
import org.eclipse.jpt.core.internal.mappings.IMappedSuperclass;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.INamedNativeQuery;
import org.eclipse.jpt.core.internal.mappings.INamedQuery;
import org.eclipse.jpt.core.internal.mappings.INonOwningMapping;
import org.eclipse.jpt.core.internal.mappings.IOneToMany;
import org.eclipse.jpt.core.internal.mappings.IOneToOne;
import org.eclipse.jpt.core.internal.mappings.IOrderBy;
import org.eclipse.jpt.core.internal.mappings.IOverride;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IQuery;
import org.eclipse.jpt.core.internal.mappings.IQueryHint;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.mappings.ITransient;
import org.eclipse.jpt.core.internal.mappings.IUniqueConstraint;
import org.eclipse.jpt.core.internal.mappings.IVersion;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage
 * @generated
 */
public class OrmAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OrmPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = OrmPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrmSwitch<Adapter> modelSwitch = new OrmSwitch<Adapter>() {
		@Override
		public Adapter caseXmlRootContentNode(XmlRootContentNode object) {
			return createXmlRootContentNodeAdapter();
		}

		@Override
		public Adapter caseEntityMappingsInternal(EntityMappingsInternal object) {
			return createEntityMappingsInternalAdapter();
		}

		@Override
		public Adapter caseEntityMappings(EntityMappings object) {
			return createEntityMappingsAdapter();
		}

		@Override
		public Adapter caseEntityMappingsForXml(EntityMappingsForXml object) {
			return createEntityMappingsForXmlAdapter();
		}

		@Override
		public Adapter caseXmlTypeMapping(XmlTypeMapping object) {
			return createXmlTypeMappingAdapter();
		}

		@Override
		public Adapter caseXmlPersistentType(XmlPersistentType object) {
			return createXmlPersistentTypeAdapter();
		}

		@Override
		public Adapter caseXmlMappedSuperclass(XmlMappedSuperclass object) {
			return createXmlMappedSuperclassAdapter();
		}

		@Override
		public Adapter caseXmlEntityInternal(XmlEntityInternal object) {
			return createXmlEntityInternalAdapter();
		}

		@Override
		public Adapter caseXmlEntityForXml(XmlEntityForXml object) {
			return createXmlEntityForXmlAdapter();
		}

		@Override
		public Adapter caseXmlEntity(XmlEntity object) {
			return createXmlEntityAdapter();
		}

		@Override
		public Adapter caseXmlEmbeddable(XmlEmbeddable object) {
			return createXmlEmbeddableAdapter();
		}

		@Override
		public Adapter caseXmlAttributeMapping(XmlAttributeMapping object) {
			return createXmlAttributeMappingAdapter();
		}

		@Override
		public Adapter caseXmlNullAttributeMapping(XmlNullAttributeMapping object) {
			return createXmlNullAttributeMappingAdapter();
		}

		@Override
		public Adapter caseXmlBasic(XmlBasic object) {
			return createXmlBasicAdapter();
		}

		@Override
		public Adapter caseXmlId(XmlId object) {
			return createXmlIdAdapter();
		}

		@Override
		public Adapter caseXmlTransient(XmlTransient object) {
			return createXmlTransientAdapter();
		}

		@Override
		public Adapter caseXmlEmbedded(XmlEmbedded object) {
			return createXmlEmbeddedAdapter();
		}

		@Override
		public Adapter caseXmlEmbeddedId(XmlEmbeddedId object) {
			return createXmlEmbeddedIdAdapter();
		}

		@Override
		public Adapter caseXmlVersion(XmlVersion object) {
			return createXmlVersionAdapter();
		}

		@Override
		public Adapter caseXmlMultiRelationshipMappingInternal(XmlMultiRelationshipMappingInternal object) {
			return createXmlMultiRelationshipMappingInternalAdapter();
		}

		@Override
		public Adapter caseXmlMultiRelationshipMappingForXml(XmlMultiRelationshipMappingForXml object) {
			return createXmlMultiRelationshipMappingForXmlAdapter();
		}

		@Override
		public Adapter caseXmlMultiRelationshipMapping(XmlMultiRelationshipMapping object) {
			return createXmlMultiRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseXmlOneToMany(XmlOneToMany object) {
			return createXmlOneToManyAdapter();
		}

		@Override
		public Adapter caseXmlManyToMany(XmlManyToMany object) {
			return createXmlManyToManyAdapter();
		}

		@Override
		public Adapter caseXmlPersistentAttribute(XmlPersistentAttribute object) {
			return createXmlPersistentAttributeAdapter();
		}

		@Override
		public Adapter casePersistenceUnitMetadataInternal(PersistenceUnitMetadataInternal object) {
			return createPersistenceUnitMetadataInternalAdapter();
		}

		@Override
		public Adapter casePersistenceUnitMetadata(PersistenceUnitMetadata object) {
			return createPersistenceUnitMetadataAdapter();
		}

		@Override
		public Adapter casePersistenceUnitMetadataForXml(PersistenceUnitMetadataForXml object) {
			return createPersistenceUnitMetadataForXmlAdapter();
		}

		@Override
		public Adapter casePersistenceUnitDefaultsInternal(PersistenceUnitDefaultsInternal object) {
			return createPersistenceUnitDefaultsInternalAdapter();
		}

		@Override
		public Adapter casePersistenceUnitDefaults(PersistenceUnitDefaults object) {
			return createPersistenceUnitDefaultsAdapter();
		}

		@Override
		public Adapter casePersistenceUnitDefaultsForXml(PersistenceUnitDefaultsForXml object) {
			return createPersistenceUnitDefaultsForXmlAdapter();
		}

		@Override
		public Adapter caseXmlTable(XmlTable object) {
			return createXmlTableAdapter();
		}

		@Override
		public Adapter caseAbstractXmlNamedColumn(AbstractXmlNamedColumn object) {
			return createAbstractXmlNamedColumnAdapter();
		}

		@Override
		public Adapter caseAbstractXmlColumn(AbstractXmlColumn object) {
			return createAbstractXmlColumnAdapter();
		}

		@Override
		public Adapter caseXmlColumn(XmlColumn object) {
			return createXmlColumnAdapter();
		}

		@Override
		public Adapter caseXmlJoinColumn(XmlJoinColumn object) {
			return createXmlJoinColumnAdapter();
		}

		@Override
		public Adapter caseIXmlColumnMapping(IXmlColumnMapping object) {
			return createIXmlColumnMappingAdapter();
		}

		@Override
		public Adapter caseXmlManyToOne(XmlManyToOne object) {
			return createXmlManyToOneAdapter();
		}

		@Override
		public Adapter caseXmlOneToOne(XmlOneToOne object) {
			return createXmlOneToOneAdapter();
		}

		@Override
		public Adapter caseXmlSingleRelationshipMapping(XmlSingleRelationshipMapping object) {
			return createXmlSingleRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseXmlRelationshipMapping(XmlRelationshipMapping object) {
			return createXmlRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseXmlJoinTable(XmlJoinTable object) {
			return createXmlJoinTableAdapter();
		}

		@Override
		public Adapter caseAbstractXmlTable(AbstractXmlTable object) {
			return createAbstractXmlTableAdapter();
		}

		@Override
		public Adapter caseXmlOverride(XmlOverride object) {
			return createXmlOverrideAdapter();
		}

		@Override
		public Adapter caseXmlAttributeOverride(XmlAttributeOverride object) {
			return createXmlAttributeOverrideAdapter();
		}

		@Override
		public Adapter caseXmlAssociationOverride(XmlAssociationOverride object) {
			return createXmlAssociationOverrideAdapter();
		}

		@Override
		public Adapter caseXmlDiscriminatorColumn(XmlDiscriminatorColumn object) {
			return createXmlDiscriminatorColumnAdapter();
		}

		@Override
		public Adapter caseXmlSecondaryTable(XmlSecondaryTable object) {
			return createXmlSecondaryTableAdapter();
		}

		@Override
		public Adapter caseXmlPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn object) {
			return createXmlPrimaryKeyJoinColumnAdapter();
		}

		@Override
		public Adapter caseXmlGeneratedValue(XmlGeneratedValue object) {
			return createXmlGeneratedValueAdapter();
		}

		@Override
		public Adapter caseXmlGenerator(XmlGenerator object) {
			return createXmlGeneratorAdapter();
		}

		@Override
		public Adapter caseXmlSequenceGenerator(XmlSequenceGenerator object) {
			return createXmlSequenceGeneratorAdapter();
		}

		@Override
		public Adapter caseXmlTableGenerator(XmlTableGenerator object) {
			return createXmlTableGeneratorAdapter();
		}

		@Override
		public Adapter caseXmlOrderBy(XmlOrderBy object) {
			return createXmlOrderByAdapter();
		}

		@Override
		public Adapter caseAbstractXmlQuery(AbstractXmlQuery object) {
			return createAbstractXmlQueryAdapter();
		}

		@Override
		public Adapter caseXmlNamedQuery(XmlNamedQuery object) {
			return createXmlNamedQueryAdapter();
		}

		@Override
		public Adapter caseXmlNamedNativeQuery(XmlNamedNativeQuery object) {
			return createXmlNamedNativeQueryAdapter();
		}

		@Override
		public Adapter caseXmlQueryHint(XmlQueryHint object) {
			return createXmlQueryHintAdapter();
		}

		@Override
		public Adapter caseXmlUniqueConstraint(XmlUniqueConstraint object) {
			return createXmlUniqueConstraintAdapter();
		}

		@Override
		public Adapter caseIJpaEObject(IJpaEObject object) {
			return createIJpaEObjectAdapter();
		}

		@Override
		public Adapter caseJpaEObject(JpaEObject object) {
			return createJpaEObjectAdapter();
		}

		@Override
		public Adapter caseIJpaSourceObject(IJpaSourceObject object) {
			return createIJpaSourceObjectAdapter();
		}

		@Override
		public Adapter caseIXmlEObject(IXmlEObject object) {
			return createIXmlEObjectAdapter();
		}

		@Override
		public Adapter caseXmlEObject(XmlEObject object) {
			return createXmlEObjectAdapter();
		}

		@Override
		public Adapter caseIJpaContentNode(IJpaContentNode object) {
			return createIJpaContentNodeAdapter();
		}

		@Override
		public Adapter caseIJpaRootContentNode(IJpaRootContentNode object) {
			return createIJpaRootContentNodeAdapter();
		}

		@Override
		public Adapter caseITypeMapping(ITypeMapping object) {
			return createITypeMappingAdapter();
		}

		@Override
		public Adapter caseIPersistentType(IPersistentType object) {
			return createIPersistentTypeAdapter();
		}

		@Override
		public Adapter caseIMappedSuperclass(IMappedSuperclass object) {
			return createIMappedSuperclassAdapter();
		}

		@Override
		public Adapter caseIEntity(IEntity object) {
			return createIEntityAdapter();
		}

		@Override
		public Adapter caseIEmbeddable(IEmbeddable object) {
			return createIEmbeddableAdapter();
		}

		@Override
		public Adapter caseIAttributeMapping(IAttributeMapping object) {
			return createIAttributeMappingAdapter();
		}

		@Override
		public Adapter caseIColumnMapping(IColumnMapping object) {
			return createIColumnMappingAdapter();
		}

		@Override
		public Adapter caseIBasic(IBasic object) {
			return createIBasicAdapter();
		}

		@Override
		public Adapter caseIId(IId object) {
			return createIIdAdapter();
		}

		@Override
		public Adapter caseITransient(ITransient object) {
			return createITransientAdapter();
		}

		@Override
		public Adapter caseIEmbedded(IEmbedded object) {
			return createIEmbeddedAdapter();
		}

		@Override
		public Adapter caseIEmbeddedId(IEmbeddedId object) {
			return createIEmbeddedIdAdapter();
		}

		@Override
		public Adapter caseIVersion(IVersion object) {
			return createIVersionAdapter();
		}

		@Override
		public Adapter caseIRelationshipMapping(IRelationshipMapping object) {
			return createIRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseINonOwningMapping(INonOwningMapping object) {
			return createINonOwningMappingAdapter();
		}

		@Override
		public Adapter caseIMultiRelationshipMapping(IMultiRelationshipMapping object) {
			return createIMultiRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseIOneToMany(IOneToMany object) {
			return createIOneToManyAdapter();
		}

		@Override
		public Adapter caseIManyToMany(IManyToMany object) {
			return createIManyToManyAdapter();
		}

		@Override
		public Adapter caseIPersistentAttribute(IPersistentAttribute object) {
			return createIPersistentAttributeAdapter();
		}

		@Override
		public Adapter caseITable(ITable object) {
			return createITableAdapter();
		}

		@Override
		public Adapter caseINamedColumn(INamedColumn object) {
			return createINamedColumnAdapter();
		}

		@Override
		public Adapter caseIAbstractColumn(IAbstractColumn object) {
			return createIAbstractColumnAdapter();
		}

		@Override
		public Adapter caseIColumn(IColumn object) {
			return createIColumnAdapter();
		}

		@Override
		public Adapter caseIAbstractJoinColumn(IAbstractJoinColumn object) {
			return createIAbstractJoinColumnAdapter();
		}

		@Override
		public Adapter caseIJoinColumn(IJoinColumn object) {
			return createIJoinColumnAdapter();
		}

		@Override
		public Adapter caseISingleRelationshipMapping(ISingleRelationshipMapping object) {
			return createISingleRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseIManyToOne(IManyToOne object) {
			return createIManyToOneAdapter();
		}

		@Override
		public Adapter caseIOneToOne(IOneToOne object) {
			return createIOneToOneAdapter();
		}

		@Override
		public Adapter caseIJoinTable(IJoinTable object) {
			return createIJoinTableAdapter();
		}

		@Override
		public Adapter caseIOverride(IOverride object) {
			return createIOverrideAdapter();
		}

		@Override
		public Adapter caseIAttributeOverride(IAttributeOverride object) {
			return createIAttributeOverrideAdapter();
		}

		@Override
		public Adapter caseIAssociationOverride(IAssociationOverride object) {
			return createIAssociationOverrideAdapter();
		}

		@Override
		public Adapter caseIDiscriminatorColumn(IDiscriminatorColumn object) {
			return createIDiscriminatorColumnAdapter();
		}

		@Override
		public Adapter caseISecondaryTable(ISecondaryTable object) {
			return createISecondaryTableAdapter();
		}

		@Override
		public Adapter caseIPrimaryKeyJoinColumn(IPrimaryKeyJoinColumn object) {
			return createIPrimaryKeyJoinColumnAdapter();
		}

		@Override
		public Adapter caseIGeneratedValue(IGeneratedValue object) {
			return createIGeneratedValueAdapter();
		}

		@Override
		public Adapter caseIGenerator(IGenerator object) {
			return createIGeneratorAdapter();
		}

		@Override
		public Adapter caseISequenceGenerator(ISequenceGenerator object) {
			return createISequenceGeneratorAdapter();
		}

		@Override
		public Adapter caseITableGenerator(ITableGenerator object) {
			return createITableGeneratorAdapter();
		}

		@Override
		public Adapter caseIOrderBy(IOrderBy object) {
			return createIOrderByAdapter();
		}

		@Override
		public Adapter caseIQuery(IQuery object) {
			return createIQueryAdapter();
		}

		@Override
		public Adapter caseINamedQuery(INamedQuery object) {
			return createINamedQueryAdapter();
		}

		@Override
		public Adapter caseINamedNativeQuery(INamedNativeQuery object) {
			return createINamedNativeQueryAdapter();
		}

		@Override
		public Adapter caseIQueryHint(IQueryHint object) {
			return createIQueryHintAdapter();
		}

		@Override
		public Adapter caseIUniqueConstraint(IUniqueConstraint object) {
			return createIUniqueConstraintAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.XmlEObject <em>Xml EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.XmlEObject
	 * @generated
	 */
	public Adapter createXmlEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode <em>Xml Root Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode
	 * @generated
	 */
	public Adapter createXmlRootContentNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal <em>Entity Mappings Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal
	 * @generated
	 */
	public Adapter createEntityMappingsInternalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappings <em>Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappings
	 * @generated
	 */
	public Adapter createEntityMappingsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml <em>Entity Mappings For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.EntityMappingsForXml
	 * @generated
	 */
	public Adapter createEntityMappingsForXmlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping <em>Xml Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping
	 * @generated
	 */
	public Adapter createXmlTypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType <em>Xml Persistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentType
	 * @generated
	 */
	public Adapter createXmlPersistentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntity <em>Xml Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntity
	 * @generated
	 */
	public Adapter createXmlEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable <em>Xml Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddable
	 * @generated
	 */
	public Adapter createXmlEmbeddableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping
	 * @generated
	 */
	public Adapter createXmlAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping <em>Xml Null Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping
	 * @generated
	 */
	public Adapter createXmlNullAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute <em>Xml Persistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute
	 * @generated
	 */
	public Adapter createXmlPersistentAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlBasic
	 * @generated
	 */
	public Adapter createXmlBasicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlId
	 * @generated
	 */
	public Adapter createXmlIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTransient
	 * @generated
	 */
	public Adapter createXmlTransientAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbedded
	 * @generated
	 */
	public Adapter createXmlEmbeddedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEmbeddedId
	 * @generated
	 */
	public Adapter createXmlEmbeddedIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlVersion
	 * @generated
	 */
	public Adapter createXmlVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal <em>Xml Multi Relationship Mapping Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal
	 * @generated
	 */
	public Adapter createXmlMultiRelationshipMappingInternalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml <em>Xml Multi Relationship Mapping For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml
	 * @generated
	 */
	public Adapter createXmlMultiRelationshipMappingForXmlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping <em>Xml Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlMultiRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToMany
	 * @generated
	 */
	public Adapter createXmlOneToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToMany
	 * @generated
	 */
	public Adapter createXmlManyToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal <em>Persistence Unit Metadata Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal
	 * @generated
	 */
	public Adapter createPersistenceUnitMetadataInternalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata
	 * @generated
	 */
	public Adapter createPersistenceUnitMetadataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataForXml
	 * @generated
	 */
	public Adapter createPersistenceUnitMetadataForXmlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal <em>Persistence Unit Defaults Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal
	 * @generated
	 */
	public Adapter createPersistenceUnitDefaultsInternalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaults
	 * @generated
	 */
	public Adapter createPersistenceUnitDefaultsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsForXml
	 * @generated
	 */
	public Adapter createPersistenceUnitDefaultsForXmlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTable <em>Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTable
	 * @generated
	 */
	public Adapter createXmlTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn <em>Abstract Xml Named Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn
	 * @generated
	 */
	public Adapter createAbstractXmlNamedColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn <em>Abstract Xml Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlColumn
	 * @generated
	 */
	public Adapter createAbstractXmlColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn <em>Xml Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlColumn
	 * @generated
	 */
	public Adapter createXmlColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn <em>Xml Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn
	 * @generated
	 */
	public Adapter createXmlJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping <em>IXml Column Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.IXmlColumnMapping
	 * @generated
	 */
	public Adapter createIXmlColumnMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlManyToOne
	 * @generated
	 */
	public Adapter createXmlManyToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOneToOne
	 * @generated
	 */
	public Adapter createXmlOneToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping <em>Xml Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSingleRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlSingleRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping <em>Xml Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlJoinTable <em>Xml Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlJoinTable
	 * @generated
	 */
	public Adapter createXmlJoinTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable <em>Abstract Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlTable
	 * @generated
	 */
	public Adapter createAbstractXmlTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlOverride <em>Xml Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOverride
	 * @generated
	 */
	public Adapter createXmlOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride
	 * @generated
	 */
	public Adapter createXmlAttributeOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride <em>Xml Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlAssociationOverride
	 * @generated
	 */
	public Adapter createXmlAssociationOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn
	 * @generated
	 */
	public Adapter createXmlDiscriminatorColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSecondaryTable
	 * @generated
	 */
	public Adapter createXmlSecondaryTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn
	 * @generated
	 */
	public Adapter createXmlPrimaryKeyJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue <em>Xml Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlGeneratedValue
	 * @generated
	 */
	public Adapter createXmlGeneratedValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlGenerator <em>Xml Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlGenerator
	 * @generated
	 */
	public Adapter createXmlGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator
	 * @generated
	 */
	public Adapter createXmlSequenceGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator <em>Xml Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator
	 * @generated
	 */
	public Adapter createXmlTableGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlOrderBy <em>Xml Order By</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlOrderBy
	 * @generated
	 */
	public Adapter createXmlOrderByAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery <em>Abstract Xml Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.AbstractXmlQuery
	 * @generated
	 */
	public Adapter createAbstractXmlQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery <em>Xml Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery
	 * @generated
	 */
	public Adapter createXmlNamedQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery
	 * @generated
	 */
	public Adapter createXmlNamedNativeQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlQueryHint <em>Xml Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlQueryHint
	 * @generated
	 */
	public Adapter createXmlQueryHintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint <em>Xml Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlUniqueConstraint
	 * @generated
	 */
	public Adapter createXmlUniqueConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaEObject <em>IJpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaEObject
	 * @generated
	 */
	public Adapter createIJpaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.JpaEObject <em>Jpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.JpaEObject
	 * @generated
	 */
	public Adapter createJpaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaSourceObject <em>IJpa Source Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaSourceObject
	 * @generated
	 */
	public Adapter createIJpaSourceObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IXmlEObject <em>IXml EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IXmlEObject
	 * @generated
	 */
	public Adapter createIXmlEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaContentNode <em>IJpa Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaContentNode
	 * @generated
	 */
	public Adapter createIJpaContentNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaRootContentNode <em>IJpa Root Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaRootContentNode
	 * @generated
	 */
	public Adapter createIJpaRootContentNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass
	 * @generated
	 */
	public Adapter createXmlMappedSuperclassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal <em>Xml Entity Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityInternal
	 * @generated
	 */
	public Adapter createXmlEntityInternalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml <em>Xml Entity For Xml</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlEntityForXml
	 * @generated
	 */
	public Adapter createXmlEntityForXmlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IAttributeMapping <em>IAttribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IAttributeMapping
	 * @generated
	 */
	public Adapter createIAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IColumnMapping <em>IColumn Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumnMapping
	 * @generated
	 */
	public Adapter createIColumnMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IPersistentAttribute <em>IPersistent Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IPersistentAttribute
	 * @generated
	 */
	public Adapter createIPersistentAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ITable <em>ITable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable
	 * @generated
	 */
	public Adapter createITableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn <em>INamed Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn
	 * @generated
	 */
	public Adapter createINamedColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn <em>IAbstract Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn
	 * @generated
	 */
	public Adapter createIAbstractColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IColumn <em>IColumn</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumn
	 * @generated
	 */
	public Adapter createIColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn <em>IAbstract Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn
	 * @generated
	 */
	public Adapter createIAbstractJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IJoinColumn <em>IJoin Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinColumn
	 * @generated
	 */
	public Adapter createIJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping <em>ISingle Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping
	 * @generated
	 */
	public Adapter createISingleRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IManyToOne <em>IMany To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToOne
	 * @generated
	 */
	public Adapter createIManyToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IOneToOne <em>IOne To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToOne
	 * @generated
	 */
	public Adapter createIOneToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable <em>IJoin Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable
	 * @generated
	 */
	public Adapter createIJoinTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IOverride <em>IOverride</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IOverride
	 * @generated
	 */
	public Adapter createIOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride <em>IAttribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAttributeOverride
	 * @generated
	 */
	public Adapter createIAttributeOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride <em>IAssociation Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride
	 * @generated
	 */
	public Adapter createIAssociationOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn <em>IDiscriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn
	 * @generated
	 */
	public Adapter createIDiscriminatorColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable <em>ISecondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable
	 * @generated
	 */
	public Adapter createISecondaryTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn <em>IPrimary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn
	 * @generated
	 */
	public Adapter createIPrimaryKeyJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IGeneratedValue <em>IGenerated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IGeneratedValue
	 * @generated
	 */
	public Adapter createIGeneratedValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IGenerator <em>IGenerator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator
	 * @generated
	 */
	public Adapter createIGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator <em>ISequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator
	 * @generated
	 */
	public Adapter createISequenceGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator <em>ITable Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator
	 * @generated
	 */
	public Adapter createITableGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IOrderBy <em>IOrder By</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IOrderBy
	 * @generated
	 */
	public Adapter createIOrderByAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IQuery <em>IQuery</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IQuery
	 * @generated
	 */
	public Adapter createIQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INamedQuery <em>INamed Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedQuery
	 * @generated
	 */
	public Adapter createINamedQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery <em>INamed Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedNativeQuery
	 * @generated
	 */
	public Adapter createINamedNativeQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IQueryHint <em>IQuery Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IQueryHint
	 * @generated
	 */
	public Adapter createIQueryHintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IUniqueConstraint <em>IUnique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IUniqueConstraint
	 * @generated
	 */
	public Adapter createIUniqueConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IBasic <em>IBasic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic
	 * @generated
	 */
	public Adapter createIBasicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IId <em>IId</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IId
	 * @generated
	 */
	public Adapter createIIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ITransient <em>ITransient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ITransient
	 * @generated
	 */
	public Adapter createITransientAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded <em>IEmbedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded
	 * @generated
	 */
	public Adapter createIEmbeddedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddedId <em>IEmbedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddedId
	 * @generated
	 */
	public Adapter createIEmbeddedIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IVersion <em>IVersion</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IVersion
	 * @generated
	 */
	public Adapter createIVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping <em>IRelationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping
	 * @generated
	 */
	public Adapter createIRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping <em>INon Owning Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INonOwningMapping
	 * @generated
	 */
	public Adapter createINonOwningMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping <em>IMulti Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping
	 * @generated
	 */
	public Adapter createIMultiRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IOneToMany <em>IOne To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToMany
	 * @generated
	 */
	public Adapter createIOneToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IManyToMany <em>IMany To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToMany
	 * @generated
	 */
	public Adapter createIManyToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IPersistentType <em>IPersistent Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IPersistentType
	 * @generated
	 */
	public Adapter createIPersistentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IMappedSuperclass <em>IMapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IMappedSuperclass
	 * @generated
	 */
	public Adapter createIMappedSuperclassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEntity <em>IEntity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity
	 * @generated
	 */
	public Adapter createIEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddable <em>IEmbeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddable
	 * @generated
	 */
	public Adapter createIEmbeddableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.ITypeMapping <em>IType Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.ITypeMapping
	 * @generated
	 */
	public Adapter createITypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}
} //JpaCoreXmlAdapterFactory
