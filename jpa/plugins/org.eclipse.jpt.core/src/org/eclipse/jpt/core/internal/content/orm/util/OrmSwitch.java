/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
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
import org.eclipse.jpt.core.internal.content.orm.XmlCascade;
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
import org.eclipse.jpt.core.internal.content.orm.XmlIdClass;
import org.eclipse.jpt.core.internal.content.orm.XmlInheritance;
import org.eclipse.jpt.core.internal.content.orm.XmlJoinColumn;
import org.eclipse.jpt.core.internal.content.orm.XmlJoinTable;
import org.eclipse.jpt.core.internal.content.orm.XmlManyToMany;
import org.eclipse.jpt.core.internal.content.orm.XmlManyToOne;
import org.eclipse.jpt.core.internal.content.orm.XmlMapKey;
import org.eclipse.jpt.core.internal.content.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingForXml;
import org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal;
import org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery;
import org.eclipse.jpt.core.internal.content.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToMany;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToOne;
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
import org.eclipse.jpt.core.internal.mappings.ICascade;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage
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
	public OrmSwitch() {
		if (modelPackage == null) {
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
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case OrmPackage.XML_ROOT_CONTENT_NODE : {
				XmlRootContentNode xmlRootContentNode = (XmlRootContentNode) theEObject;
				T result = caseXmlRootContentNode(xmlRootContentNode);
				if (result == null)
					result = caseXmlEObject(xmlRootContentNode);
				if (result == null)
					result = caseIJpaRootContentNode(xmlRootContentNode);
				if (result == null)
					result = caseJpaEObject(xmlRootContentNode);
				if (result == null)
					result = caseIXmlEObject(xmlRootContentNode);
				if (result == null)
					result = caseIJpaContentNode(xmlRootContentNode);
				if (result == null)
					result = caseIJpaEObject(xmlRootContentNode);
				if (result == null)
					result = caseIJpaSourceObject(xmlRootContentNode);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL : {
				EntityMappingsInternal entityMappingsInternal = (EntityMappingsInternal) theEObject;
				T result = caseEntityMappingsInternal(entityMappingsInternal);
				if (result == null)
					result = caseXmlEObject(entityMappingsInternal);
				if (result == null)
					result = caseIJpaContentNode(entityMappingsInternal);
				if (result == null)
					result = caseEntityMappingsForXml(entityMappingsInternal);
				if (result == null)
					result = caseEntityMappings(entityMappingsInternal);
				if (result == null)
					result = caseJpaEObject(entityMappingsInternal);
				if (result == null)
					result = caseIXmlEObject(entityMappingsInternal);
				if (result == null)
					result = caseIJpaSourceObject(entityMappingsInternal);
				if (result == null)
					result = caseIJpaEObject(entityMappingsInternal);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ENTITY_MAPPINGS : {
				EntityMappings entityMappings = (EntityMappings) theEObject;
				T result = caseEntityMappings(entityMappings);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ENTITY_MAPPINGS_FOR_XML : {
				EntityMappingsForXml entityMappingsForXml = (EntityMappingsForXml) theEObject;
				T result = caseEntityMappingsForXml(entityMappingsForXml);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_TYPE_MAPPING : {
				XmlTypeMapping xmlTypeMapping = (XmlTypeMapping) theEObject;
				T result = caseXmlTypeMapping(xmlTypeMapping);
				if (result == null)
					result = caseXmlEObject(xmlTypeMapping);
				if (result == null)
					result = caseITypeMapping(xmlTypeMapping);
				if (result == null)
					result = caseJpaEObject(xmlTypeMapping);
				if (result == null)
					result = caseIXmlEObject(xmlTypeMapping);
				if (result == null)
					result = caseIJpaSourceObject(xmlTypeMapping);
				if (result == null)
					result = caseIJpaEObject(xmlTypeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_PERSISTENT_TYPE : {
				XmlPersistentType xmlPersistentType = (XmlPersistentType) theEObject;
				T result = caseXmlPersistentType(xmlPersistentType);
				if (result == null)
					result = caseXmlEObject(xmlPersistentType);
				if (result == null)
					result = caseIPersistentType(xmlPersistentType);
				if (result == null)
					result = caseJpaEObject(xmlPersistentType);
				if (result == null)
					result = caseIXmlEObject(xmlPersistentType);
				if (result == null)
					result = caseIJpaContentNode(xmlPersistentType);
				if (result == null)
					result = caseIJpaEObject(xmlPersistentType);
				if (result == null)
					result = caseIJpaSourceObject(xmlPersistentType);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MAPPED_SUPERCLASS : {
				XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) theEObject;
				T result = caseXmlMappedSuperclass(xmlMappedSuperclass);
				if (result == null)
					result = caseXmlTypeMapping(xmlMappedSuperclass);
				if (result == null)
					result = caseIMappedSuperclass(xmlMappedSuperclass);
				if (result == null)
					result = caseXmlEObject(xmlMappedSuperclass);
				if (result == null)
					result = caseITypeMapping(xmlMappedSuperclass);
				if (result == null)
					result = caseJpaEObject(xmlMappedSuperclass);
				if (result == null)
					result = caseIXmlEObject(xmlMappedSuperclass);
				if (result == null)
					result = caseIJpaSourceObject(xmlMappedSuperclass);
				if (result == null)
					result = caseIJpaEObject(xmlMappedSuperclass);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ENTITY_INTERNAL : {
				XmlEntityInternal xmlEntityInternal = (XmlEntityInternal) theEObject;
				T result = caseXmlEntityInternal(xmlEntityInternal);
				if (result == null)
					result = caseXmlTypeMapping(xmlEntityInternal);
				if (result == null)
					result = caseXmlEntityForXml(xmlEntityInternal);
				if (result == null)
					result = caseXmlEntity(xmlEntityInternal);
				if (result == null)
					result = caseXmlEObject(xmlEntityInternal);
				if (result == null)
					result = caseITypeMapping(xmlEntityInternal);
				if (result == null)
					result = caseIEntity(xmlEntityInternal);
				if (result == null)
					result = caseJpaEObject(xmlEntityInternal);
				if (result == null)
					result = caseIXmlEObject(xmlEntityInternal);
				if (result == null)
					result = caseIJpaSourceObject(xmlEntityInternal);
				if (result == null)
					result = caseIJpaEObject(xmlEntityInternal);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ENTITY_FOR_XML : {
				XmlEntityForXml xmlEntityForXml = (XmlEntityForXml) theEObject;
				T result = caseXmlEntityForXml(xmlEntityForXml);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ENTITY : {
				XmlEntity xmlEntity = (XmlEntity) theEObject;
				T result = caseXmlEntity(xmlEntity);
				if (result == null)
					result = caseIEntity(xmlEntity);
				if (result == null)
					result = caseITypeMapping(xmlEntity);
				if (result == null)
					result = caseIJpaSourceObject(xmlEntity);
				if (result == null)
					result = caseIJpaEObject(xmlEntity);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_EMBEDDABLE : {
				XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) theEObject;
				T result = caseXmlEmbeddable(xmlEmbeddable);
				if (result == null)
					result = caseXmlTypeMapping(xmlEmbeddable);
				if (result == null)
					result = caseIEmbeddable(xmlEmbeddable);
				if (result == null)
					result = caseXmlEObject(xmlEmbeddable);
				if (result == null)
					result = caseITypeMapping(xmlEmbeddable);
				if (result == null)
					result = caseJpaEObject(xmlEmbeddable);
				if (result == null)
					result = caseIXmlEObject(xmlEmbeddable);
				if (result == null)
					result = caseIJpaSourceObject(xmlEmbeddable);
				if (result == null)
					result = caseIJpaEObject(xmlEmbeddable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ATTRIBUTE_MAPPING : {
				XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) theEObject;
				T result = caseXmlAttributeMapping(xmlAttributeMapping);
				if (result == null)
					result = caseXmlEObject(xmlAttributeMapping);
				if (result == null)
					result = caseIAttributeMapping(xmlAttributeMapping);
				if (result == null)
					result = caseJpaEObject(xmlAttributeMapping);
				if (result == null)
					result = caseIXmlEObject(xmlAttributeMapping);
				if (result == null)
					result = caseIJpaSourceObject(xmlAttributeMapping);
				if (result == null)
					result = caseIJpaEObject(xmlAttributeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_NULL_ATTRIBUTE_MAPPING : {
				XmlNullAttributeMapping xmlNullAttributeMapping = (XmlNullAttributeMapping) theEObject;
				T result = caseXmlNullAttributeMapping(xmlNullAttributeMapping);
				if (result == null)
					result = caseXmlAttributeMapping(xmlNullAttributeMapping);
				if (result == null)
					result = caseXmlEObject(xmlNullAttributeMapping);
				if (result == null)
					result = caseIAttributeMapping(xmlNullAttributeMapping);
				if (result == null)
					result = caseJpaEObject(xmlNullAttributeMapping);
				if (result == null)
					result = caseIXmlEObject(xmlNullAttributeMapping);
				if (result == null)
					result = caseIJpaSourceObject(xmlNullAttributeMapping);
				if (result == null)
					result = caseIJpaEObject(xmlNullAttributeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_BASIC : {
				XmlBasic xmlBasic = (XmlBasic) theEObject;
				T result = caseXmlBasic(xmlBasic);
				if (result == null)
					result = caseXmlAttributeMapping(xmlBasic);
				if (result == null)
					result = caseIBasic(xmlBasic);
				if (result == null)
					result = caseIXmlColumnMapping(xmlBasic);
				if (result == null)
					result = caseXmlEObject(xmlBasic);
				if (result == null)
					result = caseIAttributeMapping(xmlBasic);
				if (result == null)
					result = caseIColumnMapping(xmlBasic);
				if (result == null)
					result = caseJpaEObject(xmlBasic);
				if (result == null)
					result = caseIXmlEObject(xmlBasic);
				if (result == null)
					result = caseIJpaSourceObject(xmlBasic);
				if (result == null)
					result = caseIJpaEObject(xmlBasic);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ID : {
				XmlId xmlId = (XmlId) theEObject;
				T result = caseXmlId(xmlId);
				if (result == null)
					result = caseXmlAttributeMapping(xmlId);
				if (result == null)
					result = caseIId(xmlId);
				if (result == null)
					result = caseIXmlColumnMapping(xmlId);
				if (result == null)
					result = caseXmlEObject(xmlId);
				if (result == null)
					result = caseIAttributeMapping(xmlId);
				if (result == null)
					result = caseIColumnMapping(xmlId);
				if (result == null)
					result = caseJpaEObject(xmlId);
				if (result == null)
					result = caseIXmlEObject(xmlId);
				if (result == null)
					result = caseIJpaSourceObject(xmlId);
				if (result == null)
					result = caseIJpaEObject(xmlId);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_TRANSIENT : {
				XmlTransient xmlTransient = (XmlTransient) theEObject;
				T result = caseXmlTransient(xmlTransient);
				if (result == null)
					result = caseXmlAttributeMapping(xmlTransient);
				if (result == null)
					result = caseITransient(xmlTransient);
				if (result == null)
					result = caseXmlEObject(xmlTransient);
				if (result == null)
					result = caseIAttributeMapping(xmlTransient);
				if (result == null)
					result = caseJpaEObject(xmlTransient);
				if (result == null)
					result = caseIXmlEObject(xmlTransient);
				if (result == null)
					result = caseIJpaSourceObject(xmlTransient);
				if (result == null)
					result = caseIJpaEObject(xmlTransient);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_EMBEDDED : {
				XmlEmbedded xmlEmbedded = (XmlEmbedded) theEObject;
				T result = caseXmlEmbedded(xmlEmbedded);
				if (result == null)
					result = caseXmlAttributeMapping(xmlEmbedded);
				if (result == null)
					result = caseIEmbedded(xmlEmbedded);
				if (result == null)
					result = caseXmlEObject(xmlEmbedded);
				if (result == null)
					result = caseIAttributeMapping(xmlEmbedded);
				if (result == null)
					result = caseJpaEObject(xmlEmbedded);
				if (result == null)
					result = caseIXmlEObject(xmlEmbedded);
				if (result == null)
					result = caseIJpaSourceObject(xmlEmbedded);
				if (result == null)
					result = caseIJpaEObject(xmlEmbedded);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_EMBEDDED_ID : {
				XmlEmbeddedId xmlEmbeddedId = (XmlEmbeddedId) theEObject;
				T result = caseXmlEmbeddedId(xmlEmbeddedId);
				if (result == null)
					result = caseXmlAttributeMapping(xmlEmbeddedId);
				if (result == null)
					result = caseIEmbeddedId(xmlEmbeddedId);
				if (result == null)
					result = caseXmlEObject(xmlEmbeddedId);
				if (result == null)
					result = caseIAttributeMapping(xmlEmbeddedId);
				if (result == null)
					result = caseJpaEObject(xmlEmbeddedId);
				if (result == null)
					result = caseIXmlEObject(xmlEmbeddedId);
				if (result == null)
					result = caseIJpaSourceObject(xmlEmbeddedId);
				if (result == null)
					result = caseIJpaEObject(xmlEmbeddedId);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_VERSION : {
				XmlVersion xmlVersion = (XmlVersion) theEObject;
				T result = caseXmlVersion(xmlVersion);
				if (result == null)
					result = caseXmlAttributeMapping(xmlVersion);
				if (result == null)
					result = caseIVersion(xmlVersion);
				if (result == null)
					result = caseIXmlColumnMapping(xmlVersion);
				if (result == null)
					result = caseXmlEObject(xmlVersion);
				if (result == null)
					result = caseIAttributeMapping(xmlVersion);
				if (result == null)
					result = caseIColumnMapping(xmlVersion);
				if (result == null)
					result = caseJpaEObject(xmlVersion);
				if (result == null)
					result = caseIXmlEObject(xmlVersion);
				if (result == null)
					result = caseIJpaSourceObject(xmlVersion);
				if (result == null)
					result = caseIJpaEObject(xmlVersion);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL : {
				XmlMultiRelationshipMappingInternal xmlMultiRelationshipMappingInternal = (XmlMultiRelationshipMappingInternal) theEObject;
				T result = caseXmlMultiRelationshipMappingInternal(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseXmlRelationshipMapping(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseIMultiRelationshipMapping(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseXmlMultiRelationshipMappingForXml(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseXmlMultiRelationshipMapping(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseXmlAttributeMapping(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseIRelationshipMapping(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseINonOwningMapping(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseXmlEObject(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseIAttributeMapping(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseJpaEObject(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseIXmlEObject(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseIJpaSourceObject(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = caseIJpaEObject(xmlMultiRelationshipMappingInternal);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML : {
				XmlMultiRelationshipMappingForXml xmlMultiRelationshipMappingForXml = (XmlMultiRelationshipMappingForXml) theEObject;
				T result = caseXmlMultiRelationshipMappingForXml(xmlMultiRelationshipMappingForXml);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING : {
				XmlMultiRelationshipMapping xmlMultiRelationshipMapping = (XmlMultiRelationshipMapping) theEObject;
				T result = caseXmlMultiRelationshipMapping(xmlMultiRelationshipMapping);
				if (result == null)
					result = caseIMultiRelationshipMapping(xmlMultiRelationshipMapping);
				if (result == null)
					result = caseINonOwningMapping(xmlMultiRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(xmlMultiRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(xmlMultiRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(xmlMultiRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(xmlMultiRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ONE_TO_MANY : {
				XmlOneToMany xmlOneToMany = (XmlOneToMany) theEObject;
				T result = caseXmlOneToMany(xmlOneToMany);
				if (result == null)
					result = caseXmlMultiRelationshipMappingInternal(xmlOneToMany);
				if (result == null)
					result = caseIOneToMany(xmlOneToMany);
				if (result == null)
					result = caseXmlRelationshipMapping(xmlOneToMany);
				if (result == null)
					result = caseIMultiRelationshipMapping(xmlOneToMany);
				if (result == null)
					result = caseXmlMultiRelationshipMappingForXml(xmlOneToMany);
				if (result == null)
					result = caseXmlMultiRelationshipMapping(xmlOneToMany);
				if (result == null)
					result = caseXmlAttributeMapping(xmlOneToMany);
				if (result == null)
					result = caseIRelationshipMapping(xmlOneToMany);
				if (result == null)
					result = caseINonOwningMapping(xmlOneToMany);
				if (result == null)
					result = caseXmlEObject(xmlOneToMany);
				if (result == null)
					result = caseIAttributeMapping(xmlOneToMany);
				if (result == null)
					result = caseJpaEObject(xmlOneToMany);
				if (result == null)
					result = caseIXmlEObject(xmlOneToMany);
				if (result == null)
					result = caseIJpaSourceObject(xmlOneToMany);
				if (result == null)
					result = caseIJpaEObject(xmlOneToMany);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MANY_TO_MANY : {
				XmlManyToMany xmlManyToMany = (XmlManyToMany) theEObject;
				T result = caseXmlManyToMany(xmlManyToMany);
				if (result == null)
					result = caseXmlMultiRelationshipMappingInternal(xmlManyToMany);
				if (result == null)
					result = caseIManyToMany(xmlManyToMany);
				if (result == null)
					result = caseXmlRelationshipMapping(xmlManyToMany);
				if (result == null)
					result = caseIMultiRelationshipMapping(xmlManyToMany);
				if (result == null)
					result = caseXmlMultiRelationshipMappingForXml(xmlManyToMany);
				if (result == null)
					result = caseXmlMultiRelationshipMapping(xmlManyToMany);
				if (result == null)
					result = caseXmlAttributeMapping(xmlManyToMany);
				if (result == null)
					result = caseIRelationshipMapping(xmlManyToMany);
				if (result == null)
					result = caseINonOwningMapping(xmlManyToMany);
				if (result == null)
					result = caseXmlEObject(xmlManyToMany);
				if (result == null)
					result = caseIAttributeMapping(xmlManyToMany);
				if (result == null)
					result = caseJpaEObject(xmlManyToMany);
				if (result == null)
					result = caseIXmlEObject(xmlManyToMany);
				if (result == null)
					result = caseIJpaSourceObject(xmlManyToMany);
				if (result == null)
					result = caseIJpaEObject(xmlManyToMany);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_PERSISTENT_ATTRIBUTE : {
				XmlPersistentAttribute xmlPersistentAttribute = (XmlPersistentAttribute) theEObject;
				T result = caseXmlPersistentAttribute(xmlPersistentAttribute);
				if (result == null)
					result = caseXmlEObject(xmlPersistentAttribute);
				if (result == null)
					result = caseIPersistentAttribute(xmlPersistentAttribute);
				if (result == null)
					result = caseJpaEObject(xmlPersistentAttribute);
				if (result == null)
					result = caseIXmlEObject(xmlPersistentAttribute);
				if (result == null)
					result = caseIJpaContentNode(xmlPersistentAttribute);
				if (result == null)
					result = caseIJpaEObject(xmlPersistentAttribute);
				if (result == null)
					result = caseIJpaSourceObject(xmlPersistentAttribute);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL : {
				PersistenceUnitMetadataInternal persistenceUnitMetadataInternal = (PersistenceUnitMetadataInternal) theEObject;
				T result = casePersistenceUnitMetadataInternal(persistenceUnitMetadataInternal);
				if (result == null)
					result = caseXmlEObject(persistenceUnitMetadataInternal);
				if (result == null)
					result = casePersistenceUnitMetadataForXml(persistenceUnitMetadataInternal);
				if (result == null)
					result = casePersistenceUnitMetadata(persistenceUnitMetadataInternal);
				if (result == null)
					result = caseJpaEObject(persistenceUnitMetadataInternal);
				if (result == null)
					result = caseIXmlEObject(persistenceUnitMetadataInternal);
				if (result == null)
					result = caseIJpaEObject(persistenceUnitMetadataInternal);
				if (result == null)
					result = caseIJpaSourceObject(persistenceUnitMetadataInternal);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_METADATA : {
				PersistenceUnitMetadata persistenceUnitMetadata = (PersistenceUnitMetadata) theEObject;
				T result = casePersistenceUnitMetadata(persistenceUnitMetadata);
				if (result == null)
					result = caseIXmlEObject(persistenceUnitMetadata);
				if (result == null)
					result = caseIJpaEObject(persistenceUnitMetadata);
				if (result == null)
					result = caseIJpaSourceObject(persistenceUnitMetadata);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_METADATA_FOR_XML : {
				PersistenceUnitMetadataForXml persistenceUnitMetadataForXml = (PersistenceUnitMetadataForXml) theEObject;
				T result = casePersistenceUnitMetadataForXml(persistenceUnitMetadataForXml);
				if (result == null)
					result = caseIXmlEObject(persistenceUnitMetadataForXml);
				if (result == null)
					result = caseIJpaEObject(persistenceUnitMetadataForXml);
				if (result == null)
					result = caseIJpaSourceObject(persistenceUnitMetadataForXml);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL : {
				PersistenceUnitDefaultsInternal persistenceUnitDefaultsInternal = (PersistenceUnitDefaultsInternal) theEObject;
				T result = casePersistenceUnitDefaultsInternal(persistenceUnitDefaultsInternal);
				if (result == null)
					result = caseXmlEObject(persistenceUnitDefaultsInternal);
				if (result == null)
					result = casePersistenceUnitDefaults(persistenceUnitDefaultsInternal);
				if (result == null)
					result = casePersistenceUnitDefaultsForXml(persistenceUnitDefaultsInternal);
				if (result == null)
					result = caseJpaEObject(persistenceUnitDefaultsInternal);
				if (result == null)
					result = caseIXmlEObject(persistenceUnitDefaultsInternal);
				if (result == null)
					result = caseIJpaEObject(persistenceUnitDefaultsInternal);
				if (result == null)
					result = caseIJpaSourceObject(persistenceUnitDefaultsInternal);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS : {
				PersistenceUnitDefaults persistenceUnitDefaults = (PersistenceUnitDefaults) theEObject;
				T result = casePersistenceUnitDefaults(persistenceUnitDefaults);
				if (result == null)
					result = caseIXmlEObject(persistenceUnitDefaults);
				if (result == null)
					result = caseIJpaEObject(persistenceUnitDefaults);
				if (result == null)
					result = caseIJpaSourceObject(persistenceUnitDefaults);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML : {
				PersistenceUnitDefaultsForXml persistenceUnitDefaultsForXml = (PersistenceUnitDefaultsForXml) theEObject;
				T result = casePersistenceUnitDefaultsForXml(persistenceUnitDefaultsForXml);
				if (result == null)
					result = caseIXmlEObject(persistenceUnitDefaultsForXml);
				if (result == null)
					result = caseIJpaEObject(persistenceUnitDefaultsForXml);
				if (result == null)
					result = caseIJpaSourceObject(persistenceUnitDefaultsForXml);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_TABLE : {
				XmlTable xmlTable = (XmlTable) theEObject;
				T result = caseXmlTable(xmlTable);
				if (result == null)
					result = caseAbstractXmlTable(xmlTable);
				if (result == null)
					result = caseITable(xmlTable);
				if (result == null)
					result = caseXmlEObject(xmlTable);
				if (result == null)
					result = caseIJpaSourceObject(xmlTable);
				if (result == null)
					result = caseJpaEObject(xmlTable);
				if (result == null)
					result = caseIXmlEObject(xmlTable);
				if (result == null)
					result = caseIJpaEObject(xmlTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN : {
				AbstractXmlNamedColumn abstractXmlNamedColumn = (AbstractXmlNamedColumn) theEObject;
				T result = caseAbstractXmlNamedColumn(abstractXmlNamedColumn);
				if (result == null)
					result = caseXmlEObject(abstractXmlNamedColumn);
				if (result == null)
					result = caseINamedColumn(abstractXmlNamedColumn);
				if (result == null)
					result = caseJpaEObject(abstractXmlNamedColumn);
				if (result == null)
					result = caseIXmlEObject(abstractXmlNamedColumn);
				if (result == null)
					result = caseIJpaSourceObject(abstractXmlNamedColumn);
				if (result == null)
					result = caseIJpaEObject(abstractXmlNamedColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ABSTRACT_XML_COLUMN : {
				AbstractXmlColumn abstractXmlColumn = (AbstractXmlColumn) theEObject;
				T result = caseAbstractXmlColumn(abstractXmlColumn);
				if (result == null)
					result = caseAbstractXmlNamedColumn(abstractXmlColumn);
				if (result == null)
					result = caseIAbstractColumn(abstractXmlColumn);
				if (result == null)
					result = caseXmlEObject(abstractXmlColumn);
				if (result == null)
					result = caseINamedColumn(abstractXmlColumn);
				if (result == null)
					result = caseJpaEObject(abstractXmlColumn);
				if (result == null)
					result = caseIXmlEObject(abstractXmlColumn);
				if (result == null)
					result = caseIJpaSourceObject(abstractXmlColumn);
				if (result == null)
					result = caseIJpaEObject(abstractXmlColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_COLUMN : {
				XmlColumn xmlColumn = (XmlColumn) theEObject;
				T result = caseXmlColumn(xmlColumn);
				if (result == null)
					result = caseAbstractXmlColumn(xmlColumn);
				if (result == null)
					result = caseIColumn(xmlColumn);
				if (result == null)
					result = caseAbstractXmlNamedColumn(xmlColumn);
				if (result == null)
					result = caseIAbstractColumn(xmlColumn);
				if (result == null)
					result = caseXmlEObject(xmlColumn);
				if (result == null)
					result = caseINamedColumn(xmlColumn);
				if (result == null)
					result = caseJpaEObject(xmlColumn);
				if (result == null)
					result = caseIXmlEObject(xmlColumn);
				if (result == null)
					result = caseIJpaSourceObject(xmlColumn);
				if (result == null)
					result = caseIJpaEObject(xmlColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_JOIN_COLUMN : {
				XmlJoinColumn xmlJoinColumn = (XmlJoinColumn) theEObject;
				T result = caseXmlJoinColumn(xmlJoinColumn);
				if (result == null)
					result = caseAbstractXmlColumn(xmlJoinColumn);
				if (result == null)
					result = caseIJoinColumn(xmlJoinColumn);
				if (result == null)
					result = caseAbstractXmlNamedColumn(xmlJoinColumn);
				if (result == null)
					result = caseIAbstractColumn(xmlJoinColumn);
				if (result == null)
					result = caseIAbstractJoinColumn(xmlJoinColumn);
				if (result == null)
					result = caseXmlEObject(xmlJoinColumn);
				if (result == null)
					result = caseINamedColumn(xmlJoinColumn);
				if (result == null)
					result = caseJpaEObject(xmlJoinColumn);
				if (result == null)
					result = caseIXmlEObject(xmlJoinColumn);
				if (result == null)
					result = caseIJpaSourceObject(xmlJoinColumn);
				if (result == null)
					result = caseIJpaEObject(xmlJoinColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.IXML_COLUMN_MAPPING : {
				IXmlColumnMapping iXmlColumnMapping = (IXmlColumnMapping) theEObject;
				T result = caseIXmlColumnMapping(iXmlColumnMapping);
				if (result == null)
					result = caseIColumnMapping(iXmlColumnMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MANY_TO_ONE : {
				XmlManyToOne xmlManyToOne = (XmlManyToOne) theEObject;
				T result = caseXmlManyToOne(xmlManyToOne);
				if (result == null)
					result = caseXmlSingleRelationshipMapping(xmlManyToOne);
				if (result == null)
					result = caseIManyToOne(xmlManyToOne);
				if (result == null)
					result = caseXmlRelationshipMapping(xmlManyToOne);
				if (result == null)
					result = caseISingleRelationshipMapping(xmlManyToOne);
				if (result == null)
					result = caseXmlAttributeMapping(xmlManyToOne);
				if (result == null)
					result = caseIRelationshipMapping(xmlManyToOne);
				if (result == null)
					result = caseXmlEObject(xmlManyToOne);
				if (result == null)
					result = caseIAttributeMapping(xmlManyToOne);
				if (result == null)
					result = caseJpaEObject(xmlManyToOne);
				if (result == null)
					result = caseIXmlEObject(xmlManyToOne);
				if (result == null)
					result = caseIJpaSourceObject(xmlManyToOne);
				if (result == null)
					result = caseIJpaEObject(xmlManyToOne);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ONE_TO_ONE : {
				XmlOneToOne xmlOneToOne = (XmlOneToOne) theEObject;
				T result = caseXmlOneToOne(xmlOneToOne);
				if (result == null)
					result = caseXmlSingleRelationshipMapping(xmlOneToOne);
				if (result == null)
					result = caseIOneToOne(xmlOneToOne);
				if (result == null)
					result = caseXmlRelationshipMapping(xmlOneToOne);
				if (result == null)
					result = caseISingleRelationshipMapping(xmlOneToOne);
				if (result == null)
					result = caseINonOwningMapping(xmlOneToOne);
				if (result == null)
					result = caseXmlAttributeMapping(xmlOneToOne);
				if (result == null)
					result = caseIRelationshipMapping(xmlOneToOne);
				if (result == null)
					result = caseXmlEObject(xmlOneToOne);
				if (result == null)
					result = caseIAttributeMapping(xmlOneToOne);
				if (result == null)
					result = caseJpaEObject(xmlOneToOne);
				if (result == null)
					result = caseIXmlEObject(xmlOneToOne);
				if (result == null)
					result = caseIJpaSourceObject(xmlOneToOne);
				if (result == null)
					result = caseIJpaEObject(xmlOneToOne);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_SINGLE_RELATIONSHIP_MAPPING : {
				XmlSingleRelationshipMapping xmlSingleRelationshipMapping = (XmlSingleRelationshipMapping) theEObject;
				T result = caseXmlSingleRelationshipMapping(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseXmlRelationshipMapping(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseISingleRelationshipMapping(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseXmlAttributeMapping(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseXmlEObject(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseJpaEObject(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseIXmlEObject(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(xmlSingleRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(xmlSingleRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_RELATIONSHIP_MAPPING : {
				XmlRelationshipMapping xmlRelationshipMapping = (XmlRelationshipMapping) theEObject;
				T result = caseXmlRelationshipMapping(xmlRelationshipMapping);
				if (result == null)
					result = caseXmlAttributeMapping(xmlRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(xmlRelationshipMapping);
				if (result == null)
					result = caseXmlEObject(xmlRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(xmlRelationshipMapping);
				if (result == null)
					result = caseJpaEObject(xmlRelationshipMapping);
				if (result == null)
					result = caseIXmlEObject(xmlRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(xmlRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(xmlRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_JOIN_TABLE : {
				XmlJoinTable xmlJoinTable = (XmlJoinTable) theEObject;
				T result = caseXmlJoinTable(xmlJoinTable);
				if (result == null)
					result = caseAbstractXmlTable(xmlJoinTable);
				if (result == null)
					result = caseIJoinTable(xmlJoinTable);
				if (result == null)
					result = caseXmlEObject(xmlJoinTable);
				if (result == null)
					result = caseITable(xmlJoinTable);
				if (result == null)
					result = caseJpaEObject(xmlJoinTable);
				if (result == null)
					result = caseIXmlEObject(xmlJoinTable);
				if (result == null)
					result = caseIJpaSourceObject(xmlJoinTable);
				if (result == null)
					result = caseIJpaEObject(xmlJoinTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ABSTRACT_XML_TABLE : {
				AbstractXmlTable abstractXmlTable = (AbstractXmlTable) theEObject;
				T result = caseAbstractXmlTable(abstractXmlTable);
				if (result == null)
					result = caseXmlEObject(abstractXmlTable);
				if (result == null)
					result = caseITable(abstractXmlTable);
				if (result == null)
					result = caseJpaEObject(abstractXmlTable);
				if (result == null)
					result = caseIXmlEObject(abstractXmlTable);
				if (result == null)
					result = caseIJpaSourceObject(abstractXmlTable);
				if (result == null)
					result = caseIJpaEObject(abstractXmlTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_OVERRIDE : {
				XmlOverride xmlOverride = (XmlOverride) theEObject;
				T result = caseXmlOverride(xmlOverride);
				if (result == null)
					result = caseXmlEObject(xmlOverride);
				if (result == null)
					result = caseIOverride(xmlOverride);
				if (result == null)
					result = caseJpaEObject(xmlOverride);
				if (result == null)
					result = caseIXmlEObject(xmlOverride);
				if (result == null)
					result = caseIJpaSourceObject(xmlOverride);
				if (result == null)
					result = caseIJpaEObject(xmlOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE : {
				XmlAttributeOverride xmlAttributeOverride = (XmlAttributeOverride) theEObject;
				T result = caseXmlAttributeOverride(xmlAttributeOverride);
				if (result == null)
					result = caseXmlOverride(xmlAttributeOverride);
				if (result == null)
					result = caseIAttributeOverride(xmlAttributeOverride);
				if (result == null)
					result = caseIXmlColumnMapping(xmlAttributeOverride);
				if (result == null)
					result = caseXmlEObject(xmlAttributeOverride);
				if (result == null)
					result = caseIOverride(xmlAttributeOverride);
				if (result == null)
					result = caseIColumnMapping(xmlAttributeOverride);
				if (result == null)
					result = caseJpaEObject(xmlAttributeOverride);
				if (result == null)
					result = caseIXmlEObject(xmlAttributeOverride);
				if (result == null)
					result = caseIJpaSourceObject(xmlAttributeOverride);
				if (result == null)
					result = caseIJpaEObject(xmlAttributeOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ASSOCIATION_OVERRIDE : {
				XmlAssociationOverride xmlAssociationOverride = (XmlAssociationOverride) theEObject;
				T result = caseXmlAssociationOverride(xmlAssociationOverride);
				if (result == null)
					result = caseXmlOverride(xmlAssociationOverride);
				if (result == null)
					result = caseIAssociationOverride(xmlAssociationOverride);
				if (result == null)
					result = caseXmlEObject(xmlAssociationOverride);
				if (result == null)
					result = caseIOverride(xmlAssociationOverride);
				if (result == null)
					result = caseJpaEObject(xmlAssociationOverride);
				if (result == null)
					result = caseIXmlEObject(xmlAssociationOverride);
				if (result == null)
					result = caseIJpaSourceObject(xmlAssociationOverride);
				if (result == null)
					result = caseIJpaEObject(xmlAssociationOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_DISCRIMINATOR_COLUMN : {
				XmlDiscriminatorColumn xmlDiscriminatorColumn = (XmlDiscriminatorColumn) theEObject;
				T result = caseXmlDiscriminatorColumn(xmlDiscriminatorColumn);
				if (result == null)
					result = caseAbstractXmlNamedColumn(xmlDiscriminatorColumn);
				if (result == null)
					result = caseIDiscriminatorColumn(xmlDiscriminatorColumn);
				if (result == null)
					result = caseXmlEObject(xmlDiscriminatorColumn);
				if (result == null)
					result = caseINamedColumn(xmlDiscriminatorColumn);
				if (result == null)
					result = caseJpaEObject(xmlDiscriminatorColumn);
				if (result == null)
					result = caseIXmlEObject(xmlDiscriminatorColumn);
				if (result == null)
					result = caseIJpaSourceObject(xmlDiscriminatorColumn);
				if (result == null)
					result = caseIJpaEObject(xmlDiscriminatorColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_SECONDARY_TABLE : {
				XmlSecondaryTable xmlSecondaryTable = (XmlSecondaryTable) theEObject;
				T result = caseXmlSecondaryTable(xmlSecondaryTable);
				if (result == null)
					result = caseAbstractXmlTable(xmlSecondaryTable);
				if (result == null)
					result = caseISecondaryTable(xmlSecondaryTable);
				if (result == null)
					result = caseXmlEObject(xmlSecondaryTable);
				if (result == null)
					result = caseITable(xmlSecondaryTable);
				if (result == null)
					result = caseJpaEObject(xmlSecondaryTable);
				if (result == null)
					result = caseIXmlEObject(xmlSecondaryTable);
				if (result == null)
					result = caseIJpaSourceObject(xmlSecondaryTable);
				if (result == null)
					result = caseIJpaEObject(xmlSecondaryTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN : {
				XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = (XmlPrimaryKeyJoinColumn) theEObject;
				T result = caseXmlPrimaryKeyJoinColumn(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseAbstractXmlNamedColumn(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIPrimaryKeyJoinColumn(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseXmlEObject(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseINamedColumn(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIAbstractJoinColumn(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseJpaEObject(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIXmlEObject(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIJpaSourceObject(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIJpaEObject(xmlPrimaryKeyJoinColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_GENERATED_VALUE : {
				XmlGeneratedValue xmlGeneratedValue = (XmlGeneratedValue) theEObject;
				T result = caseXmlGeneratedValue(xmlGeneratedValue);
				if (result == null)
					result = caseXmlEObject(xmlGeneratedValue);
				if (result == null)
					result = caseIGeneratedValue(xmlGeneratedValue);
				if (result == null)
					result = caseJpaEObject(xmlGeneratedValue);
				if (result == null)
					result = caseIXmlEObject(xmlGeneratedValue);
				if (result == null)
					result = caseIJpaSourceObject(xmlGeneratedValue);
				if (result == null)
					result = caseIJpaEObject(xmlGeneratedValue);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_GENERATOR : {
				XmlGenerator xmlGenerator = (XmlGenerator) theEObject;
				T result = caseXmlGenerator(xmlGenerator);
				if (result == null)
					result = caseXmlEObject(xmlGenerator);
				if (result == null)
					result = caseIGenerator(xmlGenerator);
				if (result == null)
					result = caseJpaEObject(xmlGenerator);
				if (result == null)
					result = caseIXmlEObject(xmlGenerator);
				if (result == null)
					result = caseIJpaSourceObject(xmlGenerator);
				if (result == null)
					result = caseIJpaEObject(xmlGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_SEQUENCE_GENERATOR : {
				XmlSequenceGenerator xmlSequenceGenerator = (XmlSequenceGenerator) theEObject;
				T result = caseXmlSequenceGenerator(xmlSequenceGenerator);
				if (result == null)
					result = caseXmlGenerator(xmlSequenceGenerator);
				if (result == null)
					result = caseISequenceGenerator(xmlSequenceGenerator);
				if (result == null)
					result = caseXmlEObject(xmlSequenceGenerator);
				if (result == null)
					result = caseIGenerator(xmlSequenceGenerator);
				if (result == null)
					result = caseJpaEObject(xmlSequenceGenerator);
				if (result == null)
					result = caseIXmlEObject(xmlSequenceGenerator);
				if (result == null)
					result = caseIJpaSourceObject(xmlSequenceGenerator);
				if (result == null)
					result = caseIJpaEObject(xmlSequenceGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_TABLE_GENERATOR : {
				XmlTableGenerator xmlTableGenerator = (XmlTableGenerator) theEObject;
				T result = caseXmlTableGenerator(xmlTableGenerator);
				if (result == null)
					result = caseXmlGenerator(xmlTableGenerator);
				if (result == null)
					result = caseITableGenerator(xmlTableGenerator);
				if (result == null)
					result = caseXmlEObject(xmlTableGenerator);
				if (result == null)
					result = caseIGenerator(xmlTableGenerator);
				if (result == null)
					result = caseJpaEObject(xmlTableGenerator);
				if (result == null)
					result = caseIXmlEObject(xmlTableGenerator);
				if (result == null)
					result = caseIJpaSourceObject(xmlTableGenerator);
				if (result == null)
					result = caseIJpaEObject(xmlTableGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.ABSTRACT_XML_QUERY : {
				AbstractXmlQuery abstractXmlQuery = (AbstractXmlQuery) theEObject;
				T result = caseAbstractXmlQuery(abstractXmlQuery);
				if (result == null)
					result = caseXmlEObject(abstractXmlQuery);
				if (result == null)
					result = caseIQuery(abstractXmlQuery);
				if (result == null)
					result = caseJpaEObject(abstractXmlQuery);
				if (result == null)
					result = caseIXmlEObject(abstractXmlQuery);
				if (result == null)
					result = caseIJpaSourceObject(abstractXmlQuery);
				if (result == null)
					result = caseIJpaEObject(abstractXmlQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_NAMED_QUERY : {
				XmlNamedQuery xmlNamedQuery = (XmlNamedQuery) theEObject;
				T result = caseXmlNamedQuery(xmlNamedQuery);
				if (result == null)
					result = caseAbstractXmlQuery(xmlNamedQuery);
				if (result == null)
					result = caseINamedQuery(xmlNamedQuery);
				if (result == null)
					result = caseXmlEObject(xmlNamedQuery);
				if (result == null)
					result = caseIQuery(xmlNamedQuery);
				if (result == null)
					result = caseIJpaSourceObject(xmlNamedQuery);
				if (result == null)
					result = caseJpaEObject(xmlNamedQuery);
				if (result == null)
					result = caseIXmlEObject(xmlNamedQuery);
				if (result == null)
					result = caseIJpaEObject(xmlNamedQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_NAMED_NATIVE_QUERY : {
				XmlNamedNativeQuery xmlNamedNativeQuery = (XmlNamedNativeQuery) theEObject;
				T result = caseXmlNamedNativeQuery(xmlNamedNativeQuery);
				if (result == null)
					result = caseAbstractXmlQuery(xmlNamedNativeQuery);
				if (result == null)
					result = caseINamedNativeQuery(xmlNamedNativeQuery);
				if (result == null)
					result = caseXmlEObject(xmlNamedNativeQuery);
				if (result == null)
					result = caseIQuery(xmlNamedNativeQuery);
				if (result == null)
					result = caseIJpaSourceObject(xmlNamedNativeQuery);
				if (result == null)
					result = caseJpaEObject(xmlNamedNativeQuery);
				if (result == null)
					result = caseIXmlEObject(xmlNamedNativeQuery);
				if (result == null)
					result = caseIJpaEObject(xmlNamedNativeQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_QUERY_HINT : {
				XmlQueryHint xmlQueryHint = (XmlQueryHint) theEObject;
				T result = caseXmlQueryHint(xmlQueryHint);
				if (result == null)
					result = caseXmlEObject(xmlQueryHint);
				if (result == null)
					result = caseIQueryHint(xmlQueryHint);
				if (result == null)
					result = caseJpaEObject(xmlQueryHint);
				if (result == null)
					result = caseIXmlEObject(xmlQueryHint);
				if (result == null)
					result = caseIJpaSourceObject(xmlQueryHint);
				if (result == null)
					result = caseIJpaEObject(xmlQueryHint);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_UNIQUE_CONSTRAINT : {
				XmlUniqueConstraint xmlUniqueConstraint = (XmlUniqueConstraint) theEObject;
				T result = caseXmlUniqueConstraint(xmlUniqueConstraint);
				if (result == null)
					result = caseXmlEObject(xmlUniqueConstraint);
				if (result == null)
					result = caseIUniqueConstraint(xmlUniqueConstraint);
				if (result == null)
					result = caseJpaEObject(xmlUniqueConstraint);
				if (result == null)
					result = caseIXmlEObject(xmlUniqueConstraint);
				if (result == null)
					result = caseIJpaSourceObject(xmlUniqueConstraint);
				if (result == null)
					result = caseIJpaEObject(xmlUniqueConstraint);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_CASCADE : {
				XmlCascade xmlCascade = (XmlCascade) theEObject;
				T result = caseXmlCascade(xmlCascade);
				if (result == null)
					result = caseXmlEObject(xmlCascade);
				if (result == null)
					result = caseICascade(xmlCascade);
				if (result == null)
					result = caseJpaEObject(xmlCascade);
				if (result == null)
					result = caseIXmlEObject(xmlCascade);
				if (result == null)
					result = caseIJpaSourceObject(xmlCascade);
				if (result == null)
					result = caseIJpaEObject(xmlCascade);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_ID_CLASS : {
				XmlIdClass xmlIdClass = (XmlIdClass) theEObject;
				T result = caseXmlIdClass(xmlIdClass);
				if (result == null)
					result = caseXmlEObject(xmlIdClass);
				if (result == null)
					result = caseJpaEObject(xmlIdClass);
				if (result == null)
					result = caseIXmlEObject(xmlIdClass);
				if (result == null)
					result = caseIJpaEObject(xmlIdClass);
				if (result == null)
					result = caseIJpaSourceObject(xmlIdClass);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_INHERITANCE : {
				XmlInheritance xmlInheritance = (XmlInheritance) theEObject;
				T result = caseXmlInheritance(xmlInheritance);
				if (result == null)
					result = caseXmlEObject(xmlInheritance);
				if (result == null)
					result = caseJpaEObject(xmlInheritance);
				if (result == null)
					result = caseIXmlEObject(xmlInheritance);
				if (result == null)
					result = caseIJpaEObject(xmlInheritance);
				if (result == null)
					result = caseIJpaSourceObject(xmlInheritance);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case OrmPackage.XML_MAP_KEY : {
				XmlMapKey xmlMapKey = (XmlMapKey) theEObject;
				T result = caseXmlMapKey(xmlMapKey);
				if (result == null)
					result = caseXmlEObject(xmlMapKey);
				if (result == null)
					result = caseJpaEObject(xmlMapKey);
				if (result == null)
					result = caseIXmlEObject(xmlMapKey);
				if (result == null)
					result = caseIJpaEObject(xmlMapKey);
				if (result == null)
					result = caseIJpaSourceObject(xmlMapKey);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default :
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEObject(XmlEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Root Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Root Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlRootContentNode(XmlRootContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Mappings Internal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Mappings Internal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityMappingsInternal(EntityMappingsInternal object) {
		return null;
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
	public T caseEntityMappings(EntityMappings object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Mappings For Xml</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Mappings For Xml</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityMappingsForXml(EntityMappingsForXml object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTypeMapping(XmlTypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Persistent Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Persistent Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlPersistentType(XmlPersistentType object) {
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
	public T caseXmlEmbeddable(XmlEmbeddable object) {
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
	public T caseXmlAttributeMapping(XmlAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Null Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Null Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlNullAttributeMapping(XmlNullAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Persistent Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Persistent Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlPersistentAttribute(XmlPersistentAttribute object) {
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
	public T caseXmlBasic(XmlBasic object) {
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
	public T caseXmlId(XmlId object) {
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
	public T caseXmlTransient(XmlTransient object) {
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
	public T caseXmlEmbedded(XmlEmbedded object) {
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
	public T caseXmlEmbeddedId(XmlEmbeddedId object) {
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
	public T caseXmlVersion(XmlVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping Internal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping Internal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlMultiRelationshipMappingInternal(XmlMultiRelationshipMappingInternal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping For Xml</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping For Xml</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlMultiRelationshipMappingForXml(XmlMultiRelationshipMappingForXml object) {
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
	public T caseXmlMultiRelationshipMapping(XmlMultiRelationshipMapping object) {
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
	public T caseXmlOneToMany(XmlOneToMany object) {
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
	public T caseXmlManyToMany(XmlManyToMany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Persistence Unit Metadata Internal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Persistence Unit Metadata Internal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceUnitMetadataInternal(PersistenceUnitMetadataInternal object) {
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
	public T casePersistenceUnitMetadata(PersistenceUnitMetadata object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Persistence Unit Metadata For Xml</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Persistence Unit Metadata For Xml</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceUnitMetadataForXml(PersistenceUnitMetadataForXml object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Persistence Unit Defaults Internal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Persistence Unit Defaults Internal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceUnitDefaultsInternal(PersistenceUnitDefaultsInternal object) {
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
	public T casePersistenceUnitDefaults(PersistenceUnitDefaults object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Persistence Unit Defaults For Xml</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Persistence Unit Defaults For Xml</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePersistenceUnitDefaultsForXml(PersistenceUnitDefaultsForXml object) {
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
	public T caseXmlTable(XmlTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Xml Named Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Xml Named Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractXmlNamedColumn(AbstractXmlNamedColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Xml Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Xml Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractXmlColumn(AbstractXmlColumn object) {
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
	public T caseXmlColumn(XmlColumn object) {
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
	public T caseXmlJoinColumn(XmlJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IXml Column Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IXml Column Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIXmlColumnMapping(IXmlColumnMapping object) {
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
	public T caseXmlManyToOne(XmlManyToOne object) {
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
	public T caseXmlOneToOne(XmlOneToOne object) {
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
	public T caseXmlSingleRelationshipMapping(XmlSingleRelationshipMapping object) {
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
	public T caseXmlRelationshipMapping(XmlRelationshipMapping object) {
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
	public T caseXmlJoinTable(XmlJoinTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Xml Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Xml Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractXmlTable(AbstractXmlTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOverride(XmlOverride object) {
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
	public T caseXmlAttributeOverride(XmlAttributeOverride object) {
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
	public T caseXmlAssociationOverride(XmlAssociationOverride object) {
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
	public T caseXmlDiscriminatorColumn(XmlDiscriminatorColumn object) {
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
	public T caseXmlSecondaryTable(XmlSecondaryTable object) {
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
	public T caseXmlPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn object) {
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
	public T caseXmlGeneratedValue(XmlGeneratedValue object) {
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
	public T caseXmlGenerator(XmlGenerator object) {
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
	public T caseXmlSequenceGenerator(XmlSequenceGenerator object) {
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
	public T caseXmlTableGenerator(XmlTableGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Xml Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Xml Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractXmlQuery(AbstractXmlQuery object) {
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
	public T caseXmlNamedQuery(XmlNamedQuery object) {
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
	public T caseXmlNamedNativeQuery(XmlNamedNativeQuery object) {
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
	public T caseXmlQueryHint(XmlQueryHint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Unique Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Unique Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlUniqueConstraint(XmlUniqueConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Cascade</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Cascade</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlCascade(XmlCascade object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlIdClass(XmlIdClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Inheritance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Inheritance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlInheritance(XmlInheritance object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Map Key</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Map Key</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlMapKey(XmlMapKey object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaEObject(IJpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Jpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Jpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaEObject(JpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Source Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Source Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaSourceObject(IJpaSourceObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IXml EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IXml EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIXmlEObject(IXmlEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaContentNode(IJpaContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Root Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaRootContentNode(IJpaRootContentNode object) {
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
	public T caseXmlMappedSuperclass(XmlMappedSuperclass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Entity Internal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Entity Internal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEntityInternal(XmlEntityInternal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Entity For Xml</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Entity For Xml</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEntityForXml(XmlEntityForXml object) {
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
	public T caseXmlEntity(XmlEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAttributeMapping(IAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IColumn Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IColumn Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIColumnMapping(IColumnMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPersistent Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPersistent Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPersistentAttribute(IPersistentAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITable(ITable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedColumn(INamedColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAbstract Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAbstract Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAbstractColumn(IAbstractColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IColumn</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IColumn</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIColumn(IColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAbstract Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAbstract Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAbstractJoinColumn(IAbstractJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJoin Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJoin Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJoinColumn(IJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISingle Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISingle Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISingleRelationshipMapping(ISingleRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMany To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMany To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIManyToOne(IManyToOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IOne To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IOne To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIOneToOne(IOneToOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJoin Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJoin Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJoinTable(IJoinTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IOverride</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IOverride</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIOverride(IOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAttribute Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAttribute Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAttributeOverride(IAttributeOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAssociation Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAssociation Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAssociationOverride(IAssociationOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDiscriminator Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDiscriminator Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDiscriminatorColumn(IDiscriminatorColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISecondary Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISecondary Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISecondaryTable(ISecondaryTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPrimary Key Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPrimary Key Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPrimaryKeyJoinColumn(IPrimaryKeyJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IGenerated Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IGenerated Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIGeneratedValue(IGeneratedValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IGenerator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IGenerator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIGenerator(IGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISequence Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISequence Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISequenceGenerator(ISequenceGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITable Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITable Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITableGenerator(ITableGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IQuery</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IQuery</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIQuery(IQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedQuery(INamedQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Native Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Native Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedNativeQuery(INamedNativeQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IQuery Hint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IQuery Hint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIQueryHint(IQueryHint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IUnique Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IUnique Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIUniqueConstraint(IUniqueConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ICascade</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ICascade</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseICascade(ICascade object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBasic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBasic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBasic(IBasic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IId</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IId</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIId(IId object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITransient</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITransient</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITransient(ITransient object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEmbedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEmbedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEmbedded(IEmbedded object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEmbedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEmbedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEmbeddedId(IEmbeddedId object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IVersion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IVersion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIVersion(IVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IRelationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IRelationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIRelationshipMapping(IRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INon Owning Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INon Owning Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINonOwningMapping(INonOwningMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMulti Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMulti Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIMultiRelationshipMapping(IMultiRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IOne To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IOne To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIOneToMany(IOneToMany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMany To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMany To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIManyToMany(IManyToMany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPersistent Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPersistent Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPersistentType(IPersistentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMapped Superclass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMapped Superclass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIMappedSuperclass(IMappedSuperclass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEntity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEntity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEntity(IEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEmbeddable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEmbeddable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEmbeddable(IEmbeddable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IType Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IType Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITypeMapping(ITypeMapping object) {
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
	public T defaultCase(EObject object) {
		return null;
	}
} //JpaCoreXmlSwitch
