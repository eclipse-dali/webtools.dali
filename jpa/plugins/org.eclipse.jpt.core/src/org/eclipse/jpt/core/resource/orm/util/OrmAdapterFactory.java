/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.jpt.core.resource.orm.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.jpt.core.resource.orm.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage
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
	public OrmAdapterFactory()
	{
		if (modelPackage == null)
		{
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
	public boolean isFactoryForType(Object object)
	{
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrmSwitch<Adapter> modelSwitch =
		new OrmSwitch<Adapter>()
		{
			@Override
			public Adapter caseXmlEntityMappings(XmlEntityMappings object)
			{
				return createXmlEntityMappingsAdapter();
			}
			@Override
			public Adapter caseXmlPersistenceUnitMetadata(XmlPersistenceUnitMetadata object)
			{
				return createXmlPersistenceUnitMetadataAdapter();
			}
			@Override
			public Adapter caseXmlPersistenceUnitDefaults(XmlPersistenceUnitDefaults object)
			{
				return createXmlPersistenceUnitDefaultsAdapter();
			}
			@Override
			public Adapter caseAbstractTypeMapping(AbstractTypeMapping object)
			{
				return createAbstractTypeMappingAdapter();
			}
			@Override
			public Adapter caseXmlMappedSuperclass(XmlMappedSuperclass object)
			{
				return createXmlMappedSuperclassAdapter();
			}
			@Override
			public Adapter caseXmlEntity(XmlEntity object)
			{
				return createXmlEntityAdapter();
			}
			@Override
			public Adapter caseXmlEmbeddable(XmlEmbeddable object)
			{
				return createXmlEmbeddableAdapter();
			}
			@Override
			public Adapter caseAttributes(Attributes object)
			{
				return createAttributesAdapter();
			}
			@Override
			public Adapter caseXmlAttributeMapping(XmlAttributeMapping object)
			{
				return createXmlAttributeMappingAdapter();
			}
			@Override
			public Adapter caseColumnMapping(ColumnMapping object)
			{
				return createColumnMappingAdapter();
			}
			@Override
			public Adapter caseXmlRelationshipMapping(XmlRelationshipMapping object)
			{
				return createXmlRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlMultiRelationshipMapping(XmlMultiRelationshipMapping object)
			{
				return createXmlMultiRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlSingleRelationshipMapping(XmlSingleRelationshipMapping object)
			{
				return createXmlSingleRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlId(XmlId object)
			{
				return createXmlIdAdapter();
			}
			@Override
			public Adapter caseIdImpl(IdImpl object)
			{
				return createIdImplAdapter();
			}
			@Override
			public Adapter caseXmlEmbeddedId(XmlEmbeddedId object)
			{
				return createXmlEmbeddedIdAdapter();
			}
			@Override
			public Adapter caseEmbeddedIdImpl(EmbeddedIdImpl object)
			{
				return createEmbeddedIdImplAdapter();
			}
			@Override
			public Adapter caseXmlBasic(XmlBasic object)
			{
				return createXmlBasicAdapter();
			}
			@Override
			public Adapter caseBasicImpl(BasicImpl object)
			{
				return createBasicImplAdapter();
			}
			@Override
			public Adapter caseXmlVersion(XmlVersion object)
			{
				return createXmlVersionAdapter();
			}
			@Override
			public Adapter caseVersionImpl(VersionImpl object)
			{
				return createVersionImplAdapter();
			}
			@Override
			public Adapter caseXmlManyToOne(XmlManyToOne object)
			{
				return createXmlManyToOneAdapter();
			}
			@Override
			public Adapter caseManyToOneImpl(ManyToOneImpl object)
			{
				return createManyToOneImplAdapter();
			}
			@Override
			public Adapter caseXmlOneToMany(XmlOneToMany object)
			{
				return createXmlOneToManyAdapter();
			}
			@Override
			public Adapter caseOneToManyImpl(OneToManyImpl object)
			{
				return createOneToManyImplAdapter();
			}
			@Override
			public Adapter caseXmlOneToOne(XmlOneToOne object)
			{
				return createXmlOneToOneAdapter();
			}
			@Override
			public Adapter caseOneToOneImpl(OneToOneImpl object)
			{
				return createOneToOneImplAdapter();
			}
			@Override
			public Adapter caseXmlManyToMany(XmlManyToMany object)
			{
				return createXmlManyToManyAdapter();
			}
			@Override
			public Adapter caseManyToManyImpl(ManyToManyImpl object)
			{
				return createManyToManyImplAdapter();
			}
			@Override
			public Adapter caseXmlEmbedded(XmlEmbedded object)
			{
				return createXmlEmbeddedAdapter();
			}
			@Override
			public Adapter caseEmbeddedImpl(EmbeddedImpl object)
			{
				return createEmbeddedImplAdapter();
			}
			@Override
			public Adapter caseXmlTransient(XmlTransient object)
			{
				return createXmlTransientAdapter();
			}
			@Override
			public Adapter caseTransientImpl(TransientImpl object)
			{
				return createTransientImplAdapter();
			}
			@Override
			public Adapter caseXmlAssociationOverride(XmlAssociationOverride object)
			{
				return createXmlAssociationOverrideAdapter();
			}
			@Override
			public Adapter caseXmlAttributeOverride(XmlAttributeOverride object)
			{
				return createXmlAttributeOverrideAdapter();
			}
			@Override
			public Adapter caseAttributeOverrideImpl(AttributeOverrideImpl object)
			{
				return createAttributeOverrideImplAdapter();
			}
			@Override
			public Adapter caseCascadeType(CascadeType object)
			{
				return createCascadeTypeAdapter();
			}
			@Override
			public Adapter caseCascadeTypeImpl(CascadeTypeImpl object)
			{
				return createCascadeTypeImplAdapter();
			}
			@Override
			public Adapter caseXmlNamedColumn(XmlNamedColumn object)
			{
				return createXmlNamedColumnAdapter();
			}
			@Override
			public Adapter caseXmlAbstractColumn(XmlAbstractColumn object)
			{
				return createXmlAbstractColumnAdapter();
			}
			@Override
			public Adapter caseXmlColumn(XmlColumn object)
			{
				return createXmlColumnAdapter();
			}
			@Override
			public Adapter caseColumnImpl(ColumnImpl object)
			{
				return createColumnImplAdapter();
			}
			@Override
			public Adapter caseColumnResult(ColumnResult object)
			{
				return createColumnResultAdapter();
			}
			@Override
			public Adapter caseXmlDiscriminatorColumn(XmlDiscriminatorColumn object)
			{
				return createXmlDiscriminatorColumnAdapter();
			}
			@Override
			public Adapter caseEntityListeners(EntityListeners object)
			{
				return createEntityListenersAdapter();
			}
			@Override
			public Adapter caseEntityListener(EntityListener object)
			{
				return createEntityListenerAdapter();
			}
			@Override
			public Adapter caseEntityResult(EntityResult object)
			{
				return createEntityResultAdapter();
			}
			@Override
			public Adapter caseEventMethod(EventMethod object)
			{
				return createEventMethodAdapter();
			}
			@Override
			public Adapter caseFieldResult(FieldResult object)
			{
				return createFieldResultAdapter();
			}
			@Override
			public Adapter caseXmlGeneratedValue(XmlGeneratedValue object)
			{
				return createXmlGeneratedValueAdapter();
			}
			@Override
			public Adapter caseGeneratedValueImpl(GeneratedValueImpl object)
			{
				return createGeneratedValueImplAdapter();
			}
			@Override
			public Adapter caseIdClass(IdClass object)
			{
				return createIdClassAdapter();
			}
			@Override
			public Adapter caseInheritance(Inheritance object)
			{
				return createInheritanceAdapter();
			}
			@Override
			public Adapter caseXmlJoinColumn(XmlJoinColumn object)
			{
				return createXmlJoinColumnAdapter();
			}
			@Override
			public Adapter caseJoinColumnImpl(JoinColumnImpl object)
			{
				return createJoinColumnImplAdapter();
			}
			@Override
			public Adapter caseXmlJoinTable(XmlJoinTable object)
			{
				return createXmlJoinTableAdapter();
			}
			@Override
			public Adapter caseJoinTableImpl(JoinTableImpl object)
			{
				return createJoinTableImplAdapter();
			}
			@Override
			public Adapter caseLob(Lob object)
			{
				return createLobAdapter();
			}
			@Override
			public Adapter caseMapKey(MapKey object)
			{
				return createMapKeyAdapter();
			}
			@Override
			public Adapter caseMapKeyImpl(MapKeyImpl object)
			{
				return createMapKeyImplAdapter();
			}
			@Override
			public Adapter caseXmlQuery(XmlQuery object)
			{
				return createXmlQueryAdapter();
			}
			@Override
			public Adapter caseXmlNamedNativeQuery(XmlNamedNativeQuery object)
			{
				return createXmlNamedNativeQueryAdapter();
			}
			@Override
			public Adapter caseXmlNamedQuery(XmlNamedQuery object)
			{
				return createXmlNamedQueryAdapter();
			}
			@Override
			public Adapter casePostLoad(PostLoad object)
			{
				return createPostLoadAdapter();
			}
			@Override
			public Adapter casePostPersist(PostPersist object)
			{
				return createPostPersistAdapter();
			}
			@Override
			public Adapter casePostRemove(PostRemove object)
			{
				return createPostRemoveAdapter();
			}
			@Override
			public Adapter casePostUpdate(PostUpdate object)
			{
				return createPostUpdateAdapter();
			}
			@Override
			public Adapter casePrePersist(PrePersist object)
			{
				return createPrePersistAdapter();
			}
			@Override
			public Adapter casePreRemove(PreRemove object)
			{
				return createPreRemoveAdapter();
			}
			@Override
			public Adapter casePreUpdate(PreUpdate object)
			{
				return createPreUpdateAdapter();
			}
			@Override
			public Adapter caseXmlPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn object)
			{
				return createXmlPrimaryKeyJoinColumnAdapter();
			}
			@Override
			public Adapter caseXmlQueryHint(XmlQueryHint object)
			{
				return createXmlQueryHintAdapter();
			}
			@Override
			public Adapter caseXmlAbstractTable(XmlAbstractTable object)
			{
				return createXmlAbstractTableAdapter();
			}
			@Override
			public Adapter caseXmlTable(XmlTable object)
			{
				return createXmlTableAdapter();
			}
			@Override
			public Adapter caseXmlSecondaryTable(XmlSecondaryTable object)
			{
				return createXmlSecondaryTableAdapter();
			}
			@Override
			public Adapter caseXmlGenerator(XmlGenerator object)
			{
				return createXmlGeneratorAdapter();
			}
			@Override
			public Adapter caseXmlSequenceGenerator(XmlSequenceGenerator object)
			{
				return createXmlSequenceGeneratorAdapter();
			}
			@Override
			public Adapter caseSequenceGeneratorImpl(SequenceGeneratorImpl object)
			{
				return createSequenceGeneratorImplAdapter();
			}
			@Override
			public Adapter caseSqlResultSetMapping(SqlResultSetMapping object)
			{
				return createSqlResultSetMappingAdapter();
			}
			@Override
			public Adapter caseXmlTableGenerator(XmlTableGenerator object)
			{
				return createXmlTableGeneratorAdapter();
			}
			@Override
			public Adapter caseTableGeneratorImpl(TableGeneratorImpl object)
			{
				return createTableGeneratorImplAdapter();
			}
			@Override
			public Adapter caseUniqueConstraint(UniqueConstraint object)
			{
				return createUniqueConstraintAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
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
	public Adapter createAdapter(Notifier target)
	{
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public Adapter createXmlEntityMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata <em>Xml Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata
	 * @generated
	 */
	public Adapter createXmlPersistenceUnitMetadataAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults <em>Xml Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults
	 * @generated
	 */
	public Adapter createXmlPersistenceUnitDefaultsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.AbstractTypeMapping <em>Abstract Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractTypeMapping
	 * @generated
	 */
	public Adapter createAbstractTypeMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass <em>Xml Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass
	 * @generated
	 */
	public Adapter createXmlMappedSuperclassAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEntity <em>Xml Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntity
	 * @generated
	 */
	public Adapter createXmlEntityAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddable <em>Xml Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddable
	 * @generated
	 */
	public Adapter createXmlEmbeddableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.Attributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.Attributes
	 * @generated
	 */
	public Adapter createAttributesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeMapping
	 * @generated
	 */
	public Adapter createXmlAttributeMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.ColumnMapping <em>Column Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnMapping
	 * @generated
	 */
	public Adapter createColumnMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping <em>Xml Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlRelationshipMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping <em>Xml Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlMultiRelationshipMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping <em>Xml Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlSingleRelationshipMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlId
	 * @generated
	 */
	public Adapter createXmlIdAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.IdImpl <em>Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.IdImpl
	 * @generated
	 */
	public Adapter createIdImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public Adapter createXmlEmbeddedIdAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.EmbeddedIdImpl <em>Embedded Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.EmbeddedIdImpl
	 * @generated
	 */
	public Adapter createEmbeddedIdImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlBasic
	 * @generated
	 */
	public Adapter createXmlBasicAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.BasicImpl <em>Basic Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.BasicImpl
	 * @generated
	 */
	public Adapter createBasicImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlVersion
	 * @generated
	 */
	public Adapter createXmlVersionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.VersionImpl <em>Version Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.VersionImpl
	 * @generated
	 */
	public Adapter createVersionImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public Adapter createXmlManyToOneAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.ManyToOneImpl <em>Many To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.ManyToOneImpl
	 * @generated
	 */
	public Adapter createManyToOneImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public Adapter createXmlOneToManyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.OneToManyImpl <em>One To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.OneToManyImpl
	 * @generated
	 */
	public Adapter createOneToManyImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public Adapter createXmlOneToOneAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.OneToOneImpl <em>One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.OneToOneImpl
	 * @generated
	 */
	public Adapter createOneToOneImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public Adapter createXmlManyToManyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.ManyToManyImpl <em>Many To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.ManyToManyImpl
	 * @generated
	 */
	public Adapter createManyToManyImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbedded
	 * @generated
	 */
	public Adapter createXmlEmbeddedAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.EmbeddedImpl <em>Embedded Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.EmbeddedImpl
	 * @generated
	 */
	public Adapter createEmbeddedImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTransient
	 * @generated
	 */
	public Adapter createXmlTransientAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.TransientImpl <em>Transient Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.TransientImpl
	 * @generated
	 */
	public Adapter createTransientImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlAssociationOverride <em>Xml Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAssociationOverride
	 * @generated
	 */
	public Adapter createXmlAssociationOverrideAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride <em>Xml Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeOverride
	 * @generated
	 */
	public Adapter createXmlAttributeOverrideAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.AttributeOverrideImpl <em>Attribute Override Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.AttributeOverrideImpl
	 * @generated
	 */
	public Adapter createAttributeOverrideImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.CascadeType <em>Cascade Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeType
	 * @generated
	 */
	public Adapter createCascadeTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.CascadeTypeImpl <em>Cascade Type Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.CascadeTypeImpl
	 * @generated
	 */
	public Adapter createCascadeTypeImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlNamedColumn <em>Xml Named Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedColumn
	 * @generated
	 */
	public Adapter createXmlNamedColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlAbstractColumn <em>Xml Abstract Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAbstractColumn
	 * @generated
	 */
	public Adapter createXmlAbstractColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlColumn <em>Xml Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlColumn
	 * @generated
	 */
	public Adapter createXmlColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.ColumnImpl <em>Column Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnImpl
	 * @generated
	 */
	public Adapter createColumnImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.ColumnResult <em>Column Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnResult
	 * @generated
	 */
	public Adapter createColumnResultAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn <em>Xml Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlDiscriminatorColumn
	 * @generated
	 */
	public Adapter createXmlDiscriminatorColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.EntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.EntityListeners
	 * @generated
	 */
	public Adapter createEntityListenersAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.EntityListener <em>Entity Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.EntityListener
	 * @generated
	 */
	public Adapter createEntityListenerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.EntityResult <em>Entity Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.EntityResult
	 * @generated
	 */
	public Adapter createEntityResultAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.EventMethod <em>Event Method</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.EventMethod
	 * @generated
	 */
	public Adapter createEventMethodAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.FieldResult <em>Field Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.FieldResult
	 * @generated
	 */
	public Adapter createFieldResultAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue <em>Xml Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGeneratedValue
	 * @generated
	 */
	public Adapter createXmlGeneratedValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.GeneratedValueImpl <em>Generated Value Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.GeneratedValueImpl
	 * @generated
	 */
	public Adapter createGeneratedValueImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.IdClass <em>Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.IdClass
	 * @generated
	 */
	public Adapter createIdClassAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.Inheritance <em>Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.Inheritance
	 * @generated
	 */
	public Adapter createInheritanceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn <em>Xml Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumn
	 * @generated
	 */
	public Adapter createXmlJoinColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.JoinColumnImpl <em>Join Column Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.JoinColumnImpl
	 * @generated
	 */
	public Adapter createJoinColumnImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTable <em>Xml Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTable
	 * @generated
	 */
	public Adapter createXmlJoinTableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.JoinTableImpl <em>Join Table Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.JoinTableImpl
	 * @generated
	 */
	public Adapter createJoinTableImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.Lob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.Lob
	 * @generated
	 */
	public Adapter createLobAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.MapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.MapKey
	 * @generated
	 */
	public Adapter createMapKeyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.MapKeyImpl <em>Map Key Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.MapKeyImpl
	 * @generated
	 */
	public Adapter createMapKeyImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlQuery <em>Xml Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQuery
	 * @generated
	 */
	public Adapter createXmlQueryAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery <em>Xml Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery
	 * @generated
	 */
	public Adapter createXmlNamedNativeQueryAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlNamedQuery <em>Xml Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlNamedQuery
	 * @generated
	 */
	public Adapter createXmlNamedQueryAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.PostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.PostLoad
	 * @generated
	 */
	public Adapter createPostLoadAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.PostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.PostPersist
	 * @generated
	 */
	public Adapter createPostPersistAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.PostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.PostRemove
	 * @generated
	 */
	public Adapter createPostRemoveAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.PostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.PostUpdate
	 * @generated
	 */
	public Adapter createPostUpdateAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.PrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.PrePersist
	 * @generated
	 */
	public Adapter createPrePersistAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.PreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.PreRemove
	 * @generated
	 */
	public Adapter createPreRemoveAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.PreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.PreUpdate
	 * @generated
	 */
	public Adapter createPreUpdateAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn <em>Xml Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn
	 * @generated
	 */
	public Adapter createXmlPrimaryKeyJoinColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlQueryHint <em>Xml Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlQueryHint
	 * @generated
	 */
	public Adapter createXmlQueryHintAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlAbstractTable <em>Xml Abstract Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAbstractTable
	 * @generated
	 */
	public Adapter createXmlAbstractTableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlTable <em>Xml Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTable
	 * @generated
	 */
	public Adapter createXmlTableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlSecondaryTable <em>Xml Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSecondaryTable
	 * @generated
	 */
	public Adapter createXmlSecondaryTableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlGenerator <em>Xml Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlGenerator
	 * @generated
	 */
	public Adapter createXmlGeneratorAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator <em>Xml Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator
	 * @generated
	 */
	public Adapter createXmlSequenceGeneratorAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.SequenceGeneratorImpl <em>Sequence Generator Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.SequenceGeneratorImpl
	 * @generated
	 */
	public Adapter createSequenceGeneratorImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.SqlResultSetMapping
	 * @generated
	 */
	public Adapter createSqlResultSetMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlTableGenerator <em>Xml Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTableGenerator
	 * @generated
	 */
	public Adapter createXmlTableGeneratorAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.TableGeneratorImpl <em>Table Generator Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.TableGeneratorImpl
	 * @generated
	 */
	public Adapter createTableGeneratorImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.UniqueConstraint <em>Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.UniqueConstraint
	 * @generated
	 */
	public Adapter createUniqueConstraintAdapter()
	{
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
	public Adapter createEObjectAdapter()
	{
		return null;
	}

} //OrmAdapterFactory
