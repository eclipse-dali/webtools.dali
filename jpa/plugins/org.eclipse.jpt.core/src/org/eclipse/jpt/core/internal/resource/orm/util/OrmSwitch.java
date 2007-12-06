/**
 * <copyright>
 * </copyright>
 *
 * $Id: OrmSwitch.java,v 1.1.2.4 2007/12/06 15:56:01 kmoore Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
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
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.UniqueConstraint;
import org.eclipse.jpt.core.internal.resource.orm.Version;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage
 * @generated
 */
public class OrmSwitch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static OrmPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmSwitch()
	{
		if (modelPackage == null)
		{
			modelPackage = OrmPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject)
	{
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject)
	{
		if (theEClass.eContainer() == modelPackage)
		{
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else
		{
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject)
	{
		switch (classifierID)
		{
			case OrmPackage.ENTITY_MAPPINGS:
			{
				EntityMappings entityMappings = (EntityMappings)theEObject;
				T result = caseEntityMappings(entityMappings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_METADATA:
			{
				PersistenceUnitMetadata persistenceUnitMetadata = (PersistenceUnitMetadata)theEObject;
				T result = casePersistenceUnitMetadata(persistenceUnitMetadata);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS:
			{
				PersistenceUnitDefaults persistenceUnitDefaults = (PersistenceUnitDefaults)theEObject;
				T result = casePersistenceUnitDefaults(persistenceUnitDefaults);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.TYPE_MAPPING:
			{
				TypeMapping typeMapping = (TypeMapping)theEObject;
				T result = caseTypeMapping(typeMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.MAPPED_SUPERCLASS:
			{
				MappedSuperclass mappedSuperclass = (MappedSuperclass)theEObject;
				T result = caseMappedSuperclass(mappedSuperclass);
				if (result == null) result = caseTypeMapping(mappedSuperclass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ENTITY:
			{
				Entity entity = (Entity)theEObject;
				T result = caseEntity(entity);
				if (result == null) result = caseTypeMapping(entity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.EMBEDDABLE:
			{
				Embeddable embeddable = (Embeddable)theEObject;
				T result = caseEmbeddable(embeddable);
				if (result == null) result = caseTypeMapping(embeddable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ATTRIBUTES:
			{
				Attributes attributes = (Attributes)theEObject;
				T result = caseAttributes(attributes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.EMBEDDABLE_ATTRIBUTES:
			{
				EmbeddableAttributes embeddableAttributes = (EmbeddableAttributes)theEObject;
				T result = caseEmbeddableAttributes(embeddableAttributes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ID:
			{
				Id id = (Id)theEObject;
				T result = caseId(id);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.EMBEDDED_ID:
			{
				EmbeddedId embeddedId = (EmbeddedId)theEObject;
				T result = caseEmbeddedId(embeddedId);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.BASIC:
			{
				Basic basic = (Basic)theEObject;
				T result = caseBasic(basic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.VERSION:
			{
				Version version = (Version)theEObject;
				T result = caseVersion(version);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.MANY_TO_ONE:
			{
				ManyToOne manyToOne = (ManyToOne)theEObject;
				T result = caseManyToOne(manyToOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ONE_TO_MANY:
			{
				OneToMany oneToMany = (OneToMany)theEObject;
				T result = caseOneToMany(oneToMany);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ONE_TO_ONE:
			{
				OneToOne oneToOne = (OneToOne)theEObject;
				T result = caseOneToOne(oneToOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.MANY_TO_MANY:
			{
				ManyToMany manyToMany = (ManyToMany)theEObject;
				T result = caseManyToMany(manyToMany);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.EMBEDDED:
			{
				Embedded embedded = (Embedded)theEObject;
				T result = caseEmbedded(embedded);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.TRANSIENT:
			{
				Transient transient_ = (Transient)theEObject;
				T result = caseTransient(transient_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ASSOCIATION_OVERRIDE:
			{
				AssociationOverride associationOverride = (AssociationOverride)theEObject;
				T result = caseAssociationOverride(associationOverride);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ATTRIBUTE_OVERRIDE:
			{
				AttributeOverride attributeOverride = (AttributeOverride)theEObject;
				T result = caseAttributeOverride(attributeOverride);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.CASCADE_TYPE:
			{
				CascadeType cascadeType = (CascadeType)theEObject;
				T result = caseCascadeType(cascadeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.COLUMN:
			{
				Column column = (Column)theEObject;
				T result = caseColumn(column);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.COLUMN_RESULT:
			{
				ColumnResult columnResult = (ColumnResult)theEObject;
				T result = caseColumnResult(columnResult);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.DISCRIMINATOR_COLUMN:
			{
				DiscriminatorColumn discriminatorColumn = (DiscriminatorColumn)theEObject;
				T result = caseDiscriminatorColumn(discriminatorColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ENTITY_LISTENERS:
			{
				EntityListeners entityListeners = (EntityListeners)theEObject;
				T result = caseEntityListeners(entityListeners);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ENTITY_LISTENER:
			{
				EntityListener entityListener = (EntityListener)theEObject;
				T result = caseEntityListener(entityListener);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ENTITY_RESULT:
			{
				EntityResult entityResult = (EntityResult)theEObject;
				T result = caseEntityResult(entityResult);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.EVENT_METHOD:
			{
				EventMethod eventMethod = (EventMethod)theEObject;
				T result = caseEventMethod(eventMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.FIELD_RESULT:
			{
				FieldResult fieldResult = (FieldResult)theEObject;
				T result = caseFieldResult(fieldResult);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.GENERATED_VALUE:
			{
				GeneratedValue generatedValue = (GeneratedValue)theEObject;
				T result = caseGeneratedValue(generatedValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ID_CLASS:
			{
				IdClass idClass = (IdClass)theEObject;
				T result = caseIdClass(idClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.INHERITANCE:
			{
				Inheritance inheritance = (Inheritance)theEObject;
				T result = caseInheritance(inheritance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.JOIN_COLUMN:
			{
				JoinColumn joinColumn = (JoinColumn)theEObject;
				T result = caseJoinColumn(joinColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.JOIN_TABLE:
			{
				JoinTable joinTable = (JoinTable)theEObject;
				T result = caseJoinTable(joinTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.LOB:
			{
				Lob lob = (Lob)theEObject;
				T result = caseLob(lob);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.MAP_KEY:
			{
				MapKey mapKey = (MapKey)theEObject;
				T result = caseMapKey(mapKey);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.NAMED_NATIVE_QUERY:
			{
				NamedNativeQuery namedNativeQuery = (NamedNativeQuery)theEObject;
				T result = caseNamedNativeQuery(namedNativeQuery);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.NAMED_QUERY:
			{
				NamedQuery namedQuery = (NamedQuery)theEObject;
				T result = caseNamedQuery(namedQuery);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.POST_LOAD:
			{
				PostLoad postLoad = (PostLoad)theEObject;
				T result = casePostLoad(postLoad);
				if (result == null) result = caseEventMethod(postLoad);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.POST_PERSIST:
			{
				PostPersist postPersist = (PostPersist)theEObject;
				T result = casePostPersist(postPersist);
				if (result == null) result = caseEventMethod(postPersist);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.POST_REMOVE:
			{
				PostRemove postRemove = (PostRemove)theEObject;
				T result = casePostRemove(postRemove);
				if (result == null) result = caseEventMethod(postRemove);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.POST_UPDATE:
			{
				PostUpdate postUpdate = (PostUpdate)theEObject;
				T result = casePostUpdate(postUpdate);
				if (result == null) result = caseEventMethod(postUpdate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PRE_PERSIST:
			{
				PrePersist prePersist = (PrePersist)theEObject;
				T result = casePrePersist(prePersist);
				if (result == null) result = caseEventMethod(prePersist);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PRE_REMOVE:
			{
				PreRemove preRemove = (PreRemove)theEObject;
				T result = casePreRemove(preRemove);
				if (result == null) result = caseEventMethod(preRemove);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PRE_UPDATE:
			{
				PreUpdate preUpdate = (PreUpdate)theEObject;
				T result = casePreUpdate(preUpdate);
				if (result == null) result = caseEventMethod(preUpdate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PRIMARY_KEY_JOIN_COLUMN:
			{
				PrimaryKeyJoinColumn primaryKeyJoinColumn = (PrimaryKeyJoinColumn)theEObject;
				T result = casePrimaryKeyJoinColumn(primaryKeyJoinColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.QUERY_HINT:
			{
				QueryHint queryHint = (QueryHint)theEObject;
				T result = caseQueryHint(queryHint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.SECONDARY_TABLE:
			{
				SecondaryTable secondaryTable = (SecondaryTable)theEObject;
				T result = caseSecondaryTable(secondaryTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.SEQUENCE_GENERATOR:
			{
				SequenceGenerator sequenceGenerator = (SequenceGenerator)theEObject;
				T result = caseSequenceGenerator(sequenceGenerator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.SQL_RESULT_SET_MAPPING:
			{
				SqlResultSetMapping sqlResultSetMapping = (SqlResultSetMapping)theEObject;
				T result = caseSqlResultSetMapping(sqlResultSetMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.TABLE:
			{
				Table table = (Table)theEObject;
				T result = caseTable(table);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.TABLE_GENERATOR:
			{
				TableGenerator tableGenerator = (TableGenerator)theEObject;
				T result = caseTableGenerator(tableGenerator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.UNIQUE_CONSTRAINT:
			{
				UniqueConstraint uniqueConstraint = (UniqueConstraint)theEObject;
				T result = caseUniqueConstraint(uniqueConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityMappings(EntityMappings object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Persistence Unit Metadata</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Persistence Unit Metadata</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceUnitMetadata(PersistenceUnitMetadata object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Persistence Unit Defaults</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Persistence Unit Defaults</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceUnitDefaults(PersistenceUnitDefaults object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeMapping(TypeMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapped Superclass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapped Superclass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappedSuperclass(MappedSuperclass object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Embeddable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Embeddable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmbeddable(Embeddable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attributes</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributes(Attributes object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Embeddable Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Embeddable Attributes</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmbeddableAttributes(EmbeddableAttributes object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseId(Id object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Embedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Embedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmbeddedId(EmbeddedId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasic(Basic object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersion(Version object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyToOne(ManyToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>One To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>One To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOneToMany(OneToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOneToOne(OneToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyToMany(ManyToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Embedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Embedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmbedded(Embedded object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transient</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transient</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransient(Transient object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationOverride(AssociationOverride object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeOverride(AttributeOverride object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cascade Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cascade Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCascadeType(CascadeType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColumn(Column object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Column Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Column Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColumnResult(ColumnResult object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Discriminator Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Discriminator Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDiscriminatorColumn(DiscriminatorColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Listeners</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Listeners</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityListeners(EntityListeners object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Listener</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Listener</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityListener(EntityListener object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityResult(EntityResult object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFieldResult(FieldResult object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generated Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generated Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGeneratedValue(GeneratedValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Id Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Id Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdClass(IdClass object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inheritance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inheritance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInheritance(Inheritance object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJoinColumn(JoinColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Join Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Join Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJoinTable(JoinTable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lob</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lob</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLob(Lob object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Key</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Key</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapKey(MapKey object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Method</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Method</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEventMethod(EventMethod object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Native Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Native Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedNativeQuery(NamedNativeQuery object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedQuery(NamedQuery object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Post Load</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Post Load</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePostLoad(PostLoad object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Post Persist</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Post Persist</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePostPersist(PostPersist object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Post Remove</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Post Remove</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePostRemove(PostRemove object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Post Update</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Post Update</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePostUpdate(PostUpdate object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pre Persist</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pre Persist</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrePersist(PrePersist object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pre Remove</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pre Remove</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreRemove(PreRemove object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pre Update</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pre Update</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreUpdate(PreUpdate object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query Hint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query Hint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryHint(QueryHint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sql Result Set Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sql Result Set Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSqlResultSetMapping(SqlResultSetMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Primary Key Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primary Key Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrimaryKeyJoinColumn(PrimaryKeyJoinColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Secondary Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Secondary Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSecondaryTable(SecondaryTable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequenceGenerator(SequenceGenerator object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTable(Table object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableGenerator(TableGenerator object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unique Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unique Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUniqueConstraint(UniqueConstraint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object)
	{
		return null;
	}

} //OrmSwitch
