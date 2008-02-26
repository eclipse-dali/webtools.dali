/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.jpt.core.resource.orm.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.jpt.core.resource.orm.*;

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
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage
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
			case OrmPackage.XML_ENTITY_MAPPINGS:
			{
				XmlEntityMappings xmlEntityMappings = (XmlEntityMappings)theEObject;
				T result = caseXmlEntityMappings(xmlEntityMappings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_PERSISTENCE_UNIT_METADATA:
			{
				XmlPersistenceUnitMetadata xmlPersistenceUnitMetadata = (XmlPersistenceUnitMetadata)theEObject;
				T result = caseXmlPersistenceUnitMetadata(xmlPersistenceUnitMetadata);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS:
			{
				XmlPersistenceUnitDefaults xmlPersistenceUnitDefaults = (XmlPersistenceUnitDefaults)theEObject;
				T result = caseXmlPersistenceUnitDefaults(xmlPersistenceUnitDefaults);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ABSTRACT_TYPE_MAPPING:
			{
				AbstractTypeMapping abstractTypeMapping = (AbstractTypeMapping)theEObject;
				T result = caseAbstractTypeMapping(abstractTypeMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MAPPED_SUPERCLASS:
			{
				XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass)theEObject;
				T result = caseXmlMappedSuperclass(xmlMappedSuperclass);
				if (result == null) result = caseAbstractTypeMapping(xmlMappedSuperclass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ENTITY:
			{
				XmlEntity xmlEntity = (XmlEntity)theEObject;
				T result = caseXmlEntity(xmlEntity);
				if (result == null) result = caseAbstractTypeMapping(xmlEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_EMBEDDABLE:
			{
				XmlEmbeddable xmlEmbeddable = (XmlEmbeddable)theEObject;
				T result = caseXmlEmbeddable(xmlEmbeddable);
				if (result == null) result = caseAbstractTypeMapping(xmlEmbeddable);
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
			case OrmPackage.XML_ATTRIBUTE_MAPPING:
			{
				XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping)theEObject;
				T result = caseXmlAttributeMapping(xmlAttributeMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.COLUMN_MAPPING:
			{
				ColumnMapping columnMapping = (ColumnMapping)theEObject;
				T result = caseColumnMapping(columnMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_RELATIONSHIP_MAPPING:
			{
				XmlRelationshipMapping xmlRelationshipMapping = (XmlRelationshipMapping)theEObject;
				T result = caseXmlRelationshipMapping(xmlRelationshipMapping);
				if (result == null) result = caseXmlAttributeMapping(xmlRelationshipMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING:
			{
				XmlMultiRelationshipMapping xmlMultiRelationshipMapping = (XmlMultiRelationshipMapping)theEObject;
				T result = caseXmlMultiRelationshipMapping(xmlMultiRelationshipMapping);
				if (result == null) result = caseXmlRelationshipMapping(xmlMultiRelationshipMapping);
				if (result == null) result = caseXmlAttributeMapping(xmlMultiRelationshipMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING:
			{
				XmlSingleRelationshipMapping xmlSingleRelationshipMapping = (XmlSingleRelationshipMapping)theEObject;
				T result = caseXmlSingleRelationshipMapping(xmlSingleRelationshipMapping);
				if (result == null) result = caseXmlRelationshipMapping(xmlSingleRelationshipMapping);
				if (result == null) result = caseXmlAttributeMapping(xmlSingleRelationshipMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ID:
			{
				XmlId xmlId = (XmlId)theEObject;
				T result = caseXmlId(xmlId);
				if (result == null) result = caseXmlAttributeMapping(xmlId);
				if (result == null) result = caseColumnMapping(xmlId);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ID_IMPL:
			{
				IdImpl idImpl = (IdImpl)theEObject;
				T result = caseIdImpl(idImpl);
				if (result == null) result = caseXmlId(idImpl);
				if (result == null) result = caseXmlAttributeMapping(idImpl);
				if (result == null) result = caseColumnMapping(idImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_EMBEDDED_ID:
			{
				XmlEmbeddedId xmlEmbeddedId = (XmlEmbeddedId)theEObject;
				T result = caseXmlEmbeddedId(xmlEmbeddedId);
				if (result == null) result = caseXmlAttributeMapping(xmlEmbeddedId);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.EMBEDDED_ID_IMPL:
			{
				EmbeddedIdImpl embeddedIdImpl = (EmbeddedIdImpl)theEObject;
				T result = caseEmbeddedIdImpl(embeddedIdImpl);
				if (result == null) result = caseXmlEmbeddedId(embeddedIdImpl);
				if (result == null) result = caseXmlAttributeMapping(embeddedIdImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_BASIC:
			{
				XmlBasic xmlBasic = (XmlBasic)theEObject;
				T result = caseXmlBasic(xmlBasic);
				if (result == null) result = caseXmlAttributeMapping(xmlBasic);
				if (result == null) result = caseColumnMapping(xmlBasic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.BASIC_IMPL:
			{
				BasicImpl basicImpl = (BasicImpl)theEObject;
				T result = caseBasicImpl(basicImpl);
				if (result == null) result = caseXmlBasic(basicImpl);
				if (result == null) result = caseXmlAttributeMapping(basicImpl);
				if (result == null) result = caseColumnMapping(basicImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_VERSION:
			{
				XmlVersion xmlVersion = (XmlVersion)theEObject;
				T result = caseXmlVersion(xmlVersion);
				if (result == null) result = caseXmlAttributeMapping(xmlVersion);
				if (result == null) result = caseColumnMapping(xmlVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.VERSION_IMPL:
			{
				VersionImpl versionImpl = (VersionImpl)theEObject;
				T result = caseVersionImpl(versionImpl);
				if (result == null) result = caseXmlVersion(versionImpl);
				if (result == null) result = caseXmlAttributeMapping(versionImpl);
				if (result == null) result = caseColumnMapping(versionImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MANY_TO_ONE:
			{
				XmlManyToOne xmlManyToOne = (XmlManyToOne)theEObject;
				T result = caseXmlManyToOne(xmlManyToOne);
				if (result == null) result = caseXmlSingleRelationshipMapping(xmlManyToOne);
				if (result == null) result = caseXmlRelationshipMapping(xmlManyToOne);
				if (result == null) result = caseXmlAttributeMapping(xmlManyToOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.MANY_TO_ONE_IMPL:
			{
				ManyToOneImpl manyToOneImpl = (ManyToOneImpl)theEObject;
				T result = caseManyToOneImpl(manyToOneImpl);
				if (result == null) result = caseXmlManyToOne(manyToOneImpl);
				if (result == null) result = caseXmlSingleRelationshipMapping(manyToOneImpl);
				if (result == null) result = caseXmlRelationshipMapping(manyToOneImpl);
				if (result == null) result = caseXmlAttributeMapping(manyToOneImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ONE_TO_MANY:
			{
				XmlOneToMany xmlOneToMany = (XmlOneToMany)theEObject;
				T result = caseXmlOneToMany(xmlOneToMany);
				if (result == null) result = caseXmlMultiRelationshipMapping(xmlOneToMany);
				if (result == null) result = caseXmlRelationshipMapping(xmlOneToMany);
				if (result == null) result = caseXmlAttributeMapping(xmlOneToMany);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ONE_TO_MANY_IMPL:
			{
				OneToManyImpl oneToManyImpl = (OneToManyImpl)theEObject;
				T result = caseOneToManyImpl(oneToManyImpl);
				if (result == null) result = caseXmlOneToMany(oneToManyImpl);
				if (result == null) result = caseXmlMultiRelationshipMapping(oneToManyImpl);
				if (result == null) result = caseXmlRelationshipMapping(oneToManyImpl);
				if (result == null) result = caseXmlAttributeMapping(oneToManyImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ONE_TO_ONE:
			{
				XmlOneToOne xmlOneToOne = (XmlOneToOne)theEObject;
				T result = caseXmlOneToOne(xmlOneToOne);
				if (result == null) result = caseXmlSingleRelationshipMapping(xmlOneToOne);
				if (result == null) result = caseXmlRelationshipMapping(xmlOneToOne);
				if (result == null) result = caseXmlAttributeMapping(xmlOneToOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ONE_TO_ONE_IMPL:
			{
				OneToOneImpl oneToOneImpl = (OneToOneImpl)theEObject;
				T result = caseOneToOneImpl(oneToOneImpl);
				if (result == null) result = caseXmlOneToOne(oneToOneImpl);
				if (result == null) result = caseXmlSingleRelationshipMapping(oneToOneImpl);
				if (result == null) result = caseXmlRelationshipMapping(oneToOneImpl);
				if (result == null) result = caseXmlAttributeMapping(oneToOneImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MANY_TO_MANY:
			{
				XmlManyToMany xmlManyToMany = (XmlManyToMany)theEObject;
				T result = caseXmlManyToMany(xmlManyToMany);
				if (result == null) result = caseXmlMultiRelationshipMapping(xmlManyToMany);
				if (result == null) result = caseXmlRelationshipMapping(xmlManyToMany);
				if (result == null) result = caseXmlAttributeMapping(xmlManyToMany);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.MANY_TO_MANY_IMPL:
			{
				ManyToManyImpl manyToManyImpl = (ManyToManyImpl)theEObject;
				T result = caseManyToManyImpl(manyToManyImpl);
				if (result == null) result = caseXmlManyToMany(manyToManyImpl);
				if (result == null) result = caseXmlMultiRelationshipMapping(manyToManyImpl);
				if (result == null) result = caseXmlRelationshipMapping(manyToManyImpl);
				if (result == null) result = caseXmlAttributeMapping(manyToManyImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_EMBEDDED:
			{
				XmlEmbedded xmlEmbedded = (XmlEmbedded)theEObject;
				T result = caseXmlEmbedded(xmlEmbedded);
				if (result == null) result = caseXmlAttributeMapping(xmlEmbedded);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.EMBEDDED_IMPL:
			{
				EmbeddedImpl embeddedImpl = (EmbeddedImpl)theEObject;
				T result = caseEmbeddedImpl(embeddedImpl);
				if (result == null) result = caseXmlEmbedded(embeddedImpl);
				if (result == null) result = caseXmlAttributeMapping(embeddedImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_TRANSIENT:
			{
				XmlTransient xmlTransient = (XmlTransient)theEObject;
				T result = caseXmlTransient(xmlTransient);
				if (result == null) result = caseXmlAttributeMapping(xmlTransient);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.TRANSIENT_IMPL:
			{
				TransientImpl transientImpl = (TransientImpl)theEObject;
				T result = caseTransientImpl(transientImpl);
				if (result == null) result = caseXmlTransient(transientImpl);
				if (result == null) result = caseXmlAttributeMapping(transientImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ASSOCIATION_OVERRIDE:
			{
				XmlAssociationOverride xmlAssociationOverride = (XmlAssociationOverride)theEObject;
				T result = caseXmlAssociationOverride(xmlAssociationOverride);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE:
			{
				XmlAttributeOverride xmlAttributeOverride = (XmlAttributeOverride)theEObject;
				T result = caseXmlAttributeOverride(xmlAttributeOverride);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ATTRIBUTE_OVERRIDE_IMPL:
			{
				AttributeOverrideImpl attributeOverrideImpl = (AttributeOverrideImpl)theEObject;
				T result = caseAttributeOverrideImpl(attributeOverrideImpl);
				if (result == null) result = caseXmlAttributeOverride(attributeOverrideImpl);
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
			case OrmPackage.CASCADE_TYPE_IMPL:
			{
				CascadeTypeImpl cascadeTypeImpl = (CascadeTypeImpl)theEObject;
				T result = caseCascadeTypeImpl(cascadeTypeImpl);
				if (result == null) result = caseCascadeType(cascadeTypeImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_NAMED_COLUMN:
			{
				XmlNamedColumn xmlNamedColumn = (XmlNamedColumn)theEObject;
				T result = caseXmlNamedColumn(xmlNamedColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ABSTRACT_COLUMN:
			{
				XmlAbstractColumn xmlAbstractColumn = (XmlAbstractColumn)theEObject;
				T result = caseXmlAbstractColumn(xmlAbstractColumn);
				if (result == null) result = caseXmlNamedColumn(xmlAbstractColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_COLUMN:
			{
				XmlColumn xmlColumn = (XmlColumn)theEObject;
				T result = caseXmlColumn(xmlColumn);
				if (result == null) result = caseXmlAbstractColumn(xmlColumn);
				if (result == null) result = caseXmlNamedColumn(xmlColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.COLUMN_IMPL:
			{
				ColumnImpl columnImpl = (ColumnImpl)theEObject;
				T result = caseColumnImpl(columnImpl);
				if (result == null) result = caseXmlColumn(columnImpl);
				if (result == null) result = caseXmlAbstractColumn(columnImpl);
				if (result == null) result = caseXmlNamedColumn(columnImpl);
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN:
			{
				XmlDiscriminatorColumn xmlDiscriminatorColumn = (XmlDiscriminatorColumn)theEObject;
				T result = caseXmlDiscriminatorColumn(xmlDiscriminatorColumn);
				if (result == null) result = caseXmlNamedColumn(xmlDiscriminatorColumn);
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
			case OrmPackage.XML_GENERATED_VALUE:
			{
				XmlGeneratedValue xmlGeneratedValue = (XmlGeneratedValue)theEObject;
				T result = caseXmlGeneratedValue(xmlGeneratedValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.GENERATED_VALUE_IMPL:
			{
				GeneratedValueImpl generatedValueImpl = (GeneratedValueImpl)theEObject;
				T result = caseGeneratedValueImpl(generatedValueImpl);
				if (result == null) result = caseXmlGeneratedValue(generatedValueImpl);
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
			case OrmPackage.XML_JOIN_COLUMN:
			{
				XmlJoinColumn xmlJoinColumn = (XmlJoinColumn)theEObject;
				T result = caseXmlJoinColumn(xmlJoinColumn);
				if (result == null) result = caseXmlAbstractColumn(xmlJoinColumn);
				if (result == null) result = caseXmlNamedColumn(xmlJoinColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.JOIN_COLUMN_IMPL:
			{
				JoinColumnImpl joinColumnImpl = (JoinColumnImpl)theEObject;
				T result = caseJoinColumnImpl(joinColumnImpl);
				if (result == null) result = caseXmlJoinColumn(joinColumnImpl);
				if (result == null) result = caseXmlAbstractColumn(joinColumnImpl);
				if (result == null) result = caseXmlNamedColumn(joinColumnImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_JOIN_TABLE:
			{
				XmlJoinTable xmlJoinTable = (XmlJoinTable)theEObject;
				T result = caseXmlJoinTable(xmlJoinTable);
				if (result == null) result = caseXmlAbstractTable(xmlJoinTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.JOIN_TABLE_IMPL:
			{
				JoinTableImpl joinTableImpl = (JoinTableImpl)theEObject;
				T result = caseJoinTableImpl(joinTableImpl);
				if (result == null) result = caseXmlJoinTable(joinTableImpl);
				if (result == null) result = caseXmlAbstractTable(joinTableImpl);
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
			case OrmPackage.MAP_KEY_IMPL:
			{
				MapKeyImpl mapKeyImpl = (MapKeyImpl)theEObject;
				T result = caseMapKeyImpl(mapKeyImpl);
				if (result == null) result = caseMapKey(mapKeyImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_QUERY:
			{
				XmlQuery xmlQuery = (XmlQuery)theEObject;
				T result = caseXmlQuery(xmlQuery);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_NAMED_NATIVE_QUERY:
			{
				XmlNamedNativeQuery xmlNamedNativeQuery = (XmlNamedNativeQuery)theEObject;
				T result = caseXmlNamedNativeQuery(xmlNamedNativeQuery);
				if (result == null) result = caseXmlQuery(xmlNamedNativeQuery);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_NAMED_QUERY:
			{
				XmlNamedQuery xmlNamedQuery = (XmlNamedQuery)theEObject;
				T result = caseXmlNamedQuery(xmlNamedQuery);
				if (result == null) result = caseXmlQuery(xmlNamedQuery);
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
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN:
			{
				XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = (XmlPrimaryKeyJoinColumn)theEObject;
				T result = caseXmlPrimaryKeyJoinColumn(xmlPrimaryKeyJoinColumn);
				if (result == null) result = caseXmlNamedColumn(xmlPrimaryKeyJoinColumn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_QUERY_HINT:
			{
				XmlQueryHint xmlQueryHint = (XmlQueryHint)theEObject;
				T result = caseXmlQueryHint(xmlQueryHint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ABSTRACT_TABLE:
			{
				XmlAbstractTable xmlAbstractTable = (XmlAbstractTable)theEObject;
				T result = caseXmlAbstractTable(xmlAbstractTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_TABLE:
			{
				XmlTable xmlTable = (XmlTable)theEObject;
				T result = caseXmlTable(xmlTable);
				if (result == null) result = caseXmlAbstractTable(xmlTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_SECONDARY_TABLE:
			{
				XmlSecondaryTable xmlSecondaryTable = (XmlSecondaryTable)theEObject;
				T result = caseXmlSecondaryTable(xmlSecondaryTable);
				if (result == null) result = caseXmlAbstractTable(xmlSecondaryTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_GENERATOR:
			{
				XmlGenerator xmlGenerator = (XmlGenerator)theEObject;
				T result = caseXmlGenerator(xmlGenerator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_SEQUENCE_GENERATOR:
			{
				XmlSequenceGenerator xmlSequenceGenerator = (XmlSequenceGenerator)theEObject;
				T result = caseXmlSequenceGenerator(xmlSequenceGenerator);
				if (result == null) result = caseXmlGenerator(xmlSequenceGenerator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.SEQUENCE_GENERATOR_IMPL:
			{
				SequenceGeneratorImpl sequenceGeneratorImpl = (SequenceGeneratorImpl)theEObject;
				T result = caseSequenceGeneratorImpl(sequenceGeneratorImpl);
				if (result == null) result = caseXmlSequenceGenerator(sequenceGeneratorImpl);
				if (result == null) result = caseXmlGenerator(sequenceGeneratorImpl);
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
			case OrmPackage.XML_TABLE_GENERATOR:
			{
				XmlTableGenerator xmlTableGenerator = (XmlTableGenerator)theEObject;
				T result = caseXmlTableGenerator(xmlTableGenerator);
				if (result == null) result = caseXmlGenerator(xmlTableGenerator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.TABLE_GENERATOR_IMPL:
			{
				TableGeneratorImpl tableGeneratorImpl = (TableGeneratorImpl)theEObject;
				T result = caseTableGeneratorImpl(tableGeneratorImpl);
				if (result == null) result = caseXmlTableGenerator(tableGeneratorImpl);
				if (result == null) result = caseXmlGenerator(tableGeneratorImpl);
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
	 * Returns the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEntityMappings(XmlEntityMappings object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Persistence Unit Metadata</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Persistence Unit Metadata</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlPersistenceUnitMetadata(XmlPersistenceUnitMetadata object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Persistence Unit Defaults</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Persistence Unit Defaults</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlPersistenceUnitDefaults(XmlPersistenceUnitDefaults object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractTypeMapping(AbstractTypeMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Mapped Superclass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Mapped Superclass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlMappedSuperclass(XmlMappedSuperclass object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEntity(XmlEntity object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embeddable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embeddable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddable(XmlEmbeddable object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Xml Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAttributeMapping(XmlAttributeMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Column Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Column Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColumnMapping(ColumnMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlRelationshipMapping(XmlRelationshipMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlMultiRelationshipMapping(XmlMultiRelationshipMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Single Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Single Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlSingleRelationshipMapping(XmlSingleRelationshipMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlId(XmlId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdImpl(IdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedId(XmlEmbeddedId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Embedded Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Embedded Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmbeddedIdImpl(EmbeddedIdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasic(XmlBasic object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicImpl(BasicImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVersion(XmlVersion object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Version Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Version Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVersionImpl(VersionImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToOne(XmlManyToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyToOneImpl(ManyToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToMany(XmlOneToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>One To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>One To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOneToManyImpl(OneToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToOne(XmlOneToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>One To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>One To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOneToOneImpl(OneToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToMany(XmlManyToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Many To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Many To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseManyToManyImpl(ManyToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbedded(XmlEmbedded object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Embedded Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Embedded Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmbeddedImpl(EmbeddedImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transient</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transient</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransient(XmlTransient object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transient Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transient Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransientImpl(TransientImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Association Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Association Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAssociationOverride(XmlAssociationOverride object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Attribute Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Attribute Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAttributeOverride(XmlAttributeOverride object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Override Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Override Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeOverrideImpl(AttributeOverrideImpl object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Cascade Type Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cascade Type Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCascadeTypeImpl(CascadeTypeImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Named Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Named Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlNamedColumn(XmlNamedColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Abstract Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Abstract Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAbstractColumn(XmlAbstractColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlColumn(XmlColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Column Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Column Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColumnImpl(ColumnImpl object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Xml Discriminator Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Discriminator Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlDiscriminatorColumn(XmlDiscriminatorColumn object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Xml Generated Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Generated Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlGeneratedValue(XmlGeneratedValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generated Value Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generated Value Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGeneratedValueImpl(GeneratedValueImpl object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Xml Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlJoinColumn(XmlJoinColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Join Column Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Join Column Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJoinColumnImpl(JoinColumnImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Join Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Join Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlJoinTable(XmlJoinTable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Join Table Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Join Table Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJoinTableImpl(JoinTableImpl object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Map Key Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Key Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapKeyImpl(MapKeyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlQuery(XmlQuery object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Named Native Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Named Native Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlNamedNativeQuery(XmlNamedNativeQuery object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Named Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Named Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlNamedQuery(XmlNamedQuery object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Xml Primary Key Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Primary Key Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Query Hint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Query Hint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlQueryHint(XmlQueryHint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Abstract Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Abstract Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAbstractTable(XmlAbstractTable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTable(XmlTable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Secondary Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Secondary Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlSecondaryTable(XmlSecondaryTable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlGenerator(XmlGenerator object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Sequence Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Sequence Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlSequenceGenerator(XmlSequenceGenerator object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence Generator Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence Generator Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequenceGeneratorImpl(SequenceGeneratorImpl object)
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
	 * Returns the result of interpreting the object as an instance of '<em>Xml Table Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Table Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTableGenerator(XmlTableGenerator object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Table Generator Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Table Generator Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTableGeneratorImpl(TableGeneratorImpl object)
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
