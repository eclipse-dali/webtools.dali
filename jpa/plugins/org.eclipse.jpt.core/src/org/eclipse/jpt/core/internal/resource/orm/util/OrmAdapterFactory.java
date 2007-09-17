/**
 * <copyright>
 * </copyright>
 *
 * $Id: OrmAdapterFactory.java,v 1.1.2.1 2007/09/17 20:58:27 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.resource.orm.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.orm.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.orm.Attributes;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.CascadeType;
import org.eclipse.jpt.core.internal.resource.orm.Column;
import org.eclipse.jpt.core.internal.resource.orm.ColumnResult;
import org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.orm.EmptyType;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.EntityListener;
import org.eclipse.jpt.core.internal.resource.orm.EntityListeners;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.EntityResult;
import org.eclipse.jpt.core.internal.resource.orm.EventMethod;
import org.eclipse.jpt.core.internal.resource.orm.FieldResult;
import org.eclipse.jpt.core.internal.resource.orm.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.IdClass;
import org.eclipse.jpt.core.internal.resource.orm.Inheritance;
import org.eclipse.jpt.core.internal.resource.orm.JoinColumn;
import org.eclipse.jpt.core.internal.resource.orm.JoinTable;
import org.eclipse.jpt.core.internal.resource.orm.Lob;
import org.eclipse.jpt.core.internal.resource.orm.ManyToMany;
import org.eclipse.jpt.core.internal.resource.orm.ManyToOne;
import org.eclipse.jpt.core.internal.resource.orm.MapKey;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.orm.NamedQuery;
import org.eclipse.jpt.core.internal.resource.orm.OneToMany;
import org.eclipse.jpt.core.internal.resource.orm.OneToOne;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.resource.orm.PostLoad;
import org.eclipse.jpt.core.internal.resource.orm.PostPersist;
import org.eclipse.jpt.core.internal.resource.orm.PostRemove;
import org.eclipse.jpt.core.internal.resource.orm.PostUpdate;
import org.eclipse.jpt.core.internal.resource.orm.PrePersist;
import org.eclipse.jpt.core.internal.resource.orm.PreRemove;
import org.eclipse.jpt.core.internal.resource.orm.PreUpdate;
import org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.orm.QueryHint;
import org.eclipse.jpt.core.internal.resource.orm.SecondaryTable;
import org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping;
import org.eclipse.jpt.core.internal.resource.orm.Table;
import org.eclipse.jpt.core.internal.resource.orm.TableGenerator;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint;
import org.eclipse.jpt.core.internal.resource.orm.Version;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage
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
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrmSwitch<Adapter> modelSwitch =
		new OrmSwitch<Adapter>()
		{
			@Override
			public Adapter caseEntityMappings(EntityMappings object)
			{
				return createEntityMappingsAdapter();
			}
			@Override
			public Adapter casePersistenceUnitMetadata(PersistenceUnitMetadata object)
			{
				return createPersistenceUnitMetadataAdapter();
			}
			@Override
			public Adapter casePersistenceUnitDefaults(PersistenceUnitDefaults object)
			{
				return createPersistenceUnitDefaultsAdapter();
			}
			@Override
			public Adapter caseMappedSuperclass(MappedSuperclass object)
			{
				return createMappedSuperclassAdapter();
			}
			@Override
			public Adapter caseEntity(Entity object)
			{
				return createEntityAdapter();
			}
			@Override
			public Adapter caseEmbeddable(Embeddable object)
			{
				return createEmbeddableAdapter();
			}
			@Override
			public Adapter caseAttributes(Attributes object)
			{
				return createAttributesAdapter();
			}
			@Override
			public Adapter caseEmbeddableAttributes(EmbeddableAttributes object)
			{
				return createEmbeddableAttributesAdapter();
			}
			@Override
			public Adapter caseId(Id object)
			{
				return createIdAdapter();
			}
			@Override
			public Adapter caseEmbeddedId(EmbeddedId object)
			{
				return createEmbeddedIdAdapter();
			}
			@Override
			public Adapter caseBasic(Basic object)
			{
				return createBasicAdapter();
			}
			@Override
			public Adapter caseVersion(Version object)
			{
				return createVersionAdapter();
			}
			@Override
			public Adapter caseManyToOne(ManyToOne object)
			{
				return createManyToOneAdapter();
			}
			@Override
			public Adapter caseOneToMany(OneToMany object)
			{
				return createOneToManyAdapter();
			}
			@Override
			public Adapter caseOneToOne(OneToOne object)
			{
				return createOneToOneAdapter();
			}
			@Override
			public Adapter caseManyToMany(ManyToMany object)
			{
				return createManyToManyAdapter();
			}
			@Override
			public Adapter caseEmbedded(Embedded object)
			{
				return createEmbeddedAdapter();
			}
			@Override
			public Adapter caseTransient(Transient object)
			{
				return createTransientAdapter();
			}
			@Override
			public Adapter caseAssociationOverride(AssociationOverride object)
			{
				return createAssociationOverrideAdapter();
			}
			@Override
			public Adapter caseAttributeOverride(AttributeOverride object)
			{
				return createAttributeOverrideAdapter();
			}
			@Override
			public Adapter caseCascadeType(CascadeType object)
			{
				return createCascadeTypeAdapter();
			}
			@Override
			public Adapter caseColumn(Column object)
			{
				return createColumnAdapter();
			}
			@Override
			public Adapter caseColumnResult(ColumnResult object)
			{
				return createColumnResultAdapter();
			}
			@Override
			public Adapter caseDiscriminatorColumn(DiscriminatorColumn object)
			{
				return createDiscriminatorColumnAdapter();
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
			public Adapter caseGeneratedValue(GeneratedValue object)
			{
				return createGeneratedValueAdapter();
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
			public Adapter caseJoinColumn(JoinColumn object)
			{
				return createJoinColumnAdapter();
			}
			@Override
			public Adapter caseJoinTable(JoinTable object)
			{
				return createJoinTableAdapter();
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
			public Adapter caseNamedNativeQuery(NamedNativeQuery object)
			{
				return createNamedNativeQueryAdapter();
			}
			@Override
			public Adapter caseNamedQuery(NamedQuery object)
			{
				return createNamedQueryAdapter();
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
			public Adapter casePrimaryKeyJoinColumn(PrimaryKeyJoinColumn object)
			{
				return createPrimaryKeyJoinColumnAdapter();
			}
			@Override
			public Adapter caseQueryHint(QueryHint object)
			{
				return createQueryHintAdapter();
			}
			@Override
			public Adapter caseSecondaryTable(SecondaryTable object)
			{
				return createSecondaryTableAdapter();
			}
			@Override
			public Adapter caseSequenceGenerator(SequenceGenerator object)
			{
				return createSequenceGeneratorAdapter();
			}
			@Override
			public Adapter caseSqlResultSetMapping(SqlResultSetMapping object)
			{
				return createSqlResultSetMappingAdapter();
			}
			@Override
			public Adapter caseTable(Table object)
			{
				return createTableAdapter();
			}
			@Override
			public Adapter caseTableGenerator(TableGenerator object)
			{
				return createTableGeneratorAdapter();
			}
			@Override
			public Adapter caseUniqueConstraint(UniqueConstraint object)
			{
				return createUniqueConstraintAdapter();
			}
			@Override
			public Adapter caseEmptyType(EmptyType object)
			{
				return createEmptyTypeAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings <em>Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityMappings
	 * @generated
	 */
	public Adapter createEntityMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata <em>Persistence Unit Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata
	 * @generated
	 */
	public Adapter createPersistenceUnitMetadataAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults <em>Persistence Unit Defaults</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults
	 * @generated
	 */
	public Adapter createPersistenceUnitDefaultsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass <em>Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass
	 * @generated
	 */
	public Adapter createMappedSuperclassAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Embeddable <em>Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embeddable
	 * @generated
	 */
	public Adapter createEmbeddableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Attributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Attributes
	 * @generated
	 */
	public Adapter createAttributesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes <em>Embeddable Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddableAttributes
	 * @generated
	 */
	public Adapter createEmbeddableAttributesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Id <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Id
	 * @generated
	 */
	public Adapter createIdAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EmbeddedId <em>Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmbeddedId
	 * @generated
	 */
	public Adapter createEmbeddedIdAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Basic <em>Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Basic
	 * @generated
	 */
	public Adapter createBasicAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Version <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Version
	 * @generated
	 */
	public Adapter createVersionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToOne <em>Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToOne
	 * @generated
	 */
	public Adapter createManyToOneAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany <em>One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToMany
	 * @generated
	 */
	public Adapter createOneToManyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne <em>One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OneToOne
	 * @generated
	 */
	public Adapter createOneToOneAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.ManyToMany <em>Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ManyToMany
	 * @generated
	 */
	public Adapter createManyToManyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Embedded <em>Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Embedded
	 * @generated
	 */
	public Adapter createEmbeddedAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Transient <em>Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Transient
	 * @generated
	 */
	public Adapter createTransientAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride <em>Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AssociationOverride
	 * @generated
	 */
	public Adapter createAssociationOverrideAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverride <em>Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AttributeOverride
	 * @generated
	 */
	public Adapter createAttributeOverrideAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.CascadeType <em>Cascade Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.CascadeType
	 * @generated
	 */
	public Adapter createCascadeTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Column <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Column
	 * @generated
	 */
	public Adapter createColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.ColumnResult <em>Column Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.ColumnResult
	 * @generated
	 */
	public Adapter createColumnResultAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn <em>Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.DiscriminatorColumn
	 * @generated
	 */
	public Adapter createDiscriminatorColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListeners <em>Entity Listeners</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListeners
	 * @generated
	 */
	public Adapter createEntityListenersAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityListener <em>Entity Listener</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityListener
	 * @generated
	 */
	public Adapter createEntityListenerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EntityResult <em>Entity Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EntityResult
	 * @generated
	 */
	public Adapter createEntityResultAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.FieldResult <em>Field Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FieldResult
	 * @generated
	 */
	public Adapter createFieldResultAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.GeneratedValue <em>Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.GeneratedValue
	 * @generated
	 */
	public Adapter createGeneratedValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.IdClass <em>Id Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.IdClass
	 * @generated
	 */
	public Adapter createIdClassAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Inheritance <em>Inheritance</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Inheritance
	 * @generated
	 */
	public Adapter createInheritanceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn <em>Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinColumn
	 * @generated
	 */
	public Adapter createJoinColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.JoinTable <em>Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.JoinTable
	 * @generated
	 */
	public Adapter createJoinTableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Lob <em>Lob</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Lob
	 * @generated
	 */
	public Adapter createLobAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.MapKey <em>Map Key</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.MapKey
	 * @generated
	 */
	public Adapter createMapKeyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EventMethod <em>Event Method</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EventMethod
	 * @generated
	 */
	public Adapter createEventMethodAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery <em>Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery
	 * @generated
	 */
	public Adapter createNamedNativeQueryAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery <em>Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.NamedQuery
	 * @generated
	 */
	public Adapter createNamedQueryAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PostLoad <em>Post Load</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostLoad
	 * @generated
	 */
	public Adapter createPostLoadAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PostPersist <em>Post Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostPersist
	 * @generated
	 */
	public Adapter createPostPersistAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PostRemove <em>Post Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostRemove
	 * @generated
	 */
	public Adapter createPostRemoveAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PostUpdate <em>Post Update</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PostUpdate
	 * @generated
	 */
	public Adapter createPostUpdateAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PrePersist <em>Pre Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrePersist
	 * @generated
	 */
	public Adapter createPrePersistAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PreRemove <em>Pre Remove</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PreRemove
	 * @generated
	 */
	public Adapter createPreRemoveAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PreUpdate <em>Pre Update</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PreUpdate
	 * @generated
	 */
	public Adapter createPreUpdateAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.QueryHint <em>Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.QueryHint
	 * @generated
	 */
	public Adapter createQueryHintAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping <em>Sql Result Set Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping
	 * @generated
	 */
	public Adapter createSqlResultSetMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn <em>Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn
	 * @generated
	 */
	public Adapter createPrimaryKeyJoinColumnAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable <em>Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SecondaryTable
	 * @generated
	 */
	public Adapter createSecondaryTableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator <em>Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator
	 * @generated
	 */
	public Adapter createSequenceGeneratorAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.Table
	 * @generated
	 */
	public Adapter createTableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator <em>Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.TableGenerator
	 * @generated
	 */
	public Adapter createTableGeneratorAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint <em>Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint
	 * @generated
	 */
	public Adapter createUniqueConstraintAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.orm.EmptyType <em>Empty Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.orm.EmptyType
	 * @generated
	 */
	public Adapter createEmptyTypeAdapter()
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
