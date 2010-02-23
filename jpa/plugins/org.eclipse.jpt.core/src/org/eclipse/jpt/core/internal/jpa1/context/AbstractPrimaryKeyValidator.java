/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.context.PrimaryKeyValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractPrimaryKeyValidator
	implements PrimaryKeyValidator
{
	private TypeMapping typeMapping;
	
	private PrimaryKeyTextRangeResolver textRangeResolver;
	
	
	protected AbstractPrimaryKeyValidator(
			TypeMapping typeMapping, PrimaryKeyTextRangeResolver textRangeResolver) {
		
		this.typeMapping = typeMapping;
		this.textRangeResolver = textRangeResolver;
	}
	
	
	protected TypeMapping typeMapping() {
		return this.typeMapping;
	}
	
	protected abstract IdClassReference idClassReference();
	
	protected PrimaryKeyTextRangeResolver textRangeResolver() {
		return this.textRangeResolver;
	}
	
	// for JPA portability, a hierarchy must define its primary key on one class 
	// (entity *or* mapped superclass)
	protected void validatePrimaryKeyIsNotRedefined(List<IMessage> messages, IReporter reporter) {
		if (definesPrimaryKeyOnAncestor(typeMapping())) {
			if (idClassReference().isSpecified()) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS,
							new String[0],
							typeMapping(),
							textRangeResolver().getIdClassTextRange()));
			}
			for (AttributeMapping each : getPrimaryKeyMappingsDefinedLocally(typeMapping())) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE,
							new String[0],
							each,
							textRangeResolver().getAttributeMappingTextRange(each.getName())));
			}
			return;
		}
	}
	
	// if a primary key defining class has multiple primary keys, it must use an id class
	protected void validateIdClassIsUsedIfNecessary(List<IMessage> messages, IReporter reporter) {
		if (! specifiesIdClass() && idClassIsRequired()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_ID_CLASS_REQUIRED,
						new String[0],
						typeMapping(),
						textRangeResolver().getTypeMappingTextRange()));
		}
	}
	
	// only one composite primary key strategy may be used
	protected void validateOneOfIdClassOrEmbeddedIdIsUsed(List<IMessage> messages, IReporter reporter) {
		if (idClassReference().isSpecified()
				&& CollectionTools.size(typeMapping().getAllAttributeMappings(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY)) > 0) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_ID_CLASS_AND_EMBEDDED_ID_BOTH_USED,
						new String[0],
						typeMapping(),
						textRangeResolver().getTypeMappingTextRange()));
		}
	}
	
	// only one embedded id may be used
	protected void validateOneEmbeddedId(List<IMessage> messages, IReporter reporter) {
		if (CollectionTools.size(getEmbeddedIdMappings(typeMapping())) > 1) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_MULTIPLE_EMBEDDED_ID,
						new String[0],
						typeMapping(),
						textRangeResolver().getTypeMappingTextRange()));
		}
	}
	
	protected void validateMapsIdMappings(List<IMessage> messages, IReporter reporter) {
		for (SingleRelationshipMapping2_0 mapsIdRelationshipMapping : getMapsIdMappingsDefinedLocally(typeMapping())) {
			// can't use maps id mappings with an id class
			if (definesIdClass(typeMapping())) {
				messages.add(DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_ID_CLASS_WITH_MAPS_ID,
						new String[] {mapsIdRelationshipMapping.getName()},
						mapsIdRelationshipMapping,
						textRangeResolver().getAttributeMappingTextRange(mapsIdRelationshipMapping.getName())));
			}
			
			AttributeMapping resolvedAttributeMapping = 
					mapsIdRelationshipMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getResolvedAttributeMappingValue();
			if (resolvedAttributeMapping != null 
					&& ! resolvedAttributeMapping.getPersistentAttribute().getTypeName().equals(getTargetEntityPrimaryKeyTypeName(mapsIdRelationshipMapping))) {
				messages.add(DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_MAPS_ID_ATTRIBUTE_TYPE_DOES_NOT_AGREE,
						new String[] {mapsIdRelationshipMapping.getName()},
						mapsIdRelationshipMapping,
						textRangeResolver().getAttributeMappingTextRange(mapsIdRelationshipMapping.getName())));
			}
		}
	}
	
	protected void validateIdClass(JavaPersistentType idClass, List<IMessage> messages, IReporter reporter) {
		if (hasDerivedIdMappingMatchingIdClass(idClass)) {
			validateIdClass_derivedIdMappingMatchingIdClass(idClass, messages, reporter);
			return;
		}
		for (JavaPersistentAttribute idClassAttribute : 
				new SubIterableWrapper<PersistentAttribute, JavaPersistentAttribute>(
					CollectionTools.iterable(idClass.allAttributes()))) {
			boolean foundMatch = false;
			for (AttributeMapping attributeMapping : getAttributeMappings(typeMapping())) {
				if (idClassAttribute.getName().equals(attributeMapping.getName())) {
					foundMatch = true;
					
					// the matching attribute should be a primary key
					if (! definesPrimaryKey(attributeMapping)) {
						messages.add(DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY,
								new String[] {idClassAttribute.getName()},
								typeMapping(),
								textRangeResolver().getIdClassTextRange()));
					}
					
					// the matching attribute's type should agree
					String idClassAttributeTypeName = idClassAttribute.getTypeName();
					String attributeMappingTypeName = getTypeNameForIdClass(attributeMapping);
					if (attributeMappingTypeName != null 	// if it's null, there should be 
																// another failing validation elsewhere
							&& ! idClassAttributeTypeName.equals(attributeMappingTypeName)) {
						messages.add(DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE,
								new String[] {idClassAttribute.getName(), idClassAttributeTypeName},
								typeMapping(),
								textRangeResolver().getIdClassTextRange()));
					}
				}
			}
			
			if (! foundMatch) {
				messages.add(DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH,
						new String[] {idClassAttribute.getName()},
						typeMapping(),
						textRangeResolver().getIdClassTextRange()));
			}
		}
	}
	
	protected void validateIdClass_derivedIdMappingMatchingIdClass(
			JavaPersistentType idClass, List<IMessage> messages, IReporter reporter) {
		
		Collection<AttributeMapping> errorMappings = new HashBag<AttributeMapping>();
		for (AttributeMapping each 
				: new CompositeIterable<AttributeMapping>(getIdMappings(typeMapping()), getEmbeddedIdMappings(typeMapping()))) {
			errorMappings.add(each);
		}
		Collection<AttributeMapping> errorDerivedIdMappings = new HashBag<AttributeMapping>();
		for (SingleRelationshipMapping2_0 each : getDerivedIdMappings(typeMapping())) {
			if (idClass.getName().equals(getTargetEntityPrimaryKeyTypeName(each))) {
				errorDerivedIdMappings.add(each);
			}
			else {
				errorMappings.add(each);
			}
		}
		for (AttributeMapping each : errorMappings) {
			addNoIdClassAttributeMatchError(each, messages);
		}
		if (CollectionTools.size(errorDerivedIdMappings) > 1) {
			for (AttributeMapping each : errorDerivedIdMappings) {
				addDuplicateIdClassAttributeMatchError(each, messages);
			}
		}
	}
	
	protected void addNoIdClassAttributeMatchError(AttributeMapping attributeMapping, List<IMessage> messages) {
		messages.add(DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_NO_MATCH,
				new String[] {attributeMapping.getName()},
				typeMapping(),
				textRangeResolver().getIdClassTextRange()));
	}
	
	protected void addDuplicateIdClassAttributeMatchError(AttributeMapping attributeMapping, List<IMessage> messages) {
		messages.add(DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_DUPLICATE_MATCH,
				new String[] {attributeMapping.getName()},
				typeMapping(),
				textRangeResolver().getIdClassTextRange()));
	}
	
	
	// **************** convenience methods ********************************************************
	
	// **************** primary key overall ********************************************************
	
	/**
	 * Return whether an ancestor class has defined any aspect of the primary key
	 */
	protected boolean definesPrimaryKeyOnAncestor(TypeMapping typeMapping) {
		for (TypeMapping each : CollectionTools.iterable(typeMapping.inheritanceHierarchy())) {
			if (each != typeMapping && definesPrimaryKey(each)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return whether the type mapping has defined any aspect of the primary key
	 */
	protected boolean definesPrimaryKey(TypeMapping typeMapping) {
		return getIdClass(typeMapping) != null
				|| ! CollectionTools.isEmpty(getPrimaryKeyMappings(typeMapping));
	}
	
	/**
	 * Return true if the type mapping has defined any aspect of a complex primary key
	 */
	protected boolean definesComplexPrimaryKey(TypeMapping typeMapping) {
		return definesIdClass(typeMapping)
				|| getEmbeddedIdMapping(typeMapping) != null;
	}
	
	/**
	 * Return the overriding type name of the primary key for the type mapping.
	 * This may be
	 * - type of the single simple primary key (id) attribute
	 * - type of the single complex primary key (embedded id) attribute
	 * - type of the id class
	 * - null if none of the above are coherent (i.e. there are multiple possibilities, or the 
	 * 		primary key is invalid)
	 */
	protected String getPrimaryKeyTypeName(TypeMapping typeMapping) {
		JavaPersistentType idClass = getIdClass(typeMapping);
		if (idClass != null) {
			return idClass.getName();
		}
		EmbeddedIdMapping embeddedId = getEmbeddedIdMapping(typeMapping);
		if (embeddedId != null) {
			return embeddedId.getPersistentAttribute().getTypeName();
		}
		IdMapping id = getIdMapping(typeMapping);
		if (id != null) {
			return id.getPersistentAttribute().getTypeName();
		}
		return null;
	}
	
	/**
	 * Return whether the attribute mapping has defined any aspect of the primary key
	 */
	protected boolean definesPrimaryKey(AttributeMapping attributeMapping) {
		String mappingKey = attributeMapping.getKey();
		if (StringTools.stringsAreEqual(mappingKey, MappingKeys.ID_ATTRIBUTE_MAPPING_KEY)
				|| StringTools.stringsAreEqual(mappingKey, MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY)) {
			return true;
		}
		if (StringTools.stringsAreEqual(mappingKey, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)
				|| StringTools.stringsAreEqual(mappingKey, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY)) {
			SingleRelationshipMapping2_0 relationshipMapping = (SingleRelationshipMapping2_0) attributeMapping;
			return (relationshipMapping.getDerivedIdentity().usesIdDerivedIdentityStrategy()
					|| relationshipMapping.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy());
		}
		return false;
	}
	
	
	// **************** id class **********************************************
	
	protected boolean specifiesIdClass() {
		return idClassReference().isSpecified();
	}
	
	/**
	 * Return whether an id class is defined on the class
	 * NOTE: this is different from whether an id class is *specified*.  If a specified id class
	 * 		is not resolved, it is not defined.  There will be a validation error to that effect.
	 */
	protected boolean definesIdClass(TypeMapping typeMapping) {
		return getIdClass(typeMapping) != null;
	}
	
	/**
	 * Return whether an ancestor class has defined an id class
	 */
	protected boolean definesIdClassOnAncestor(TypeMapping typeMapping) {
		for (TypeMapping each : CollectionTools.iterable(typeMapping.inheritanceHierarchy())) {
			if (each != typeMapping && definesIdClass(each)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return the id class to be used for the type mapping, whether that be locally
	 * or on an ancestor
	 */
	protected JavaPersistentType getIdClass(TypeMapping typeMapping) {
		for (Iterator<TypeMapping> stream = typeMapping.inheritanceHierarchy(); stream.hasNext(); ) {
			TypeMapping next = stream.next();
			if (next.getIdClass() != null) {
				return next.getIdClass();
			}
		}
		return null;
	}
	
	
	// **************** attribute mappings in general *************************
	
	protected Iterable<AttributeMapping> getAttributeMappings(TypeMapping typeMapping) {
		return CollectionTools.collection(typeMapping.allAttributeMappings());
	}
	
	/**
	 * Return all primary key mappings, defined on and above the type mapping
	 */
	protected Iterable<AttributeMapping> getPrimaryKeyMappings(TypeMapping typeMapping) {
		return new CompositeIterable<AttributeMapping>(
				getIdMappings(typeMapping),
				getEmbeddedIdMappings(typeMapping),
				getDerivedIdMappings(typeMapping),
				getMapsIdMappings(typeMapping));
	}
	
	/**
	 * Return primary key mappings declared directly on the type mapping
	 */
	protected Iterable<AttributeMapping> getPrimaryKeyMappingsDefinedLocally(TypeMapping typeMapping) {
		return new CompositeIterable<AttributeMapping>(
				getIdMappingsDefinedLocally(typeMapping),
				getEmbeddedIdMappingsDefinedLocally(typeMapping),
				getDerivedIdMappingsDefinedLocally(typeMapping),
				getMapsIdMappingsDefinedLocally(typeMapping));
	}
	
	protected boolean hasAnyPrimaryKeyMappings(TypeMapping typeMapping) {
		return ! CollectionTools.isEmpty(getPrimaryKeyMappings(typeMapping));
	}
	
	// **************** id mappings *******************************************
	
	protected IdMapping getIdMapping(TypeMapping typeMapping) {
		Iterable<IdMapping> idMappings = getIdMappings(typeMapping);
		if (CollectionTools.size(idMappings) == 1) {
			return idMappings.iterator().next();
		}
		return null;
	}
	
	protected Iterable<IdMapping> getIdMappings(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, IdMapping>(
				typeMapping.getAllAttributeMappings(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
	}
	
	protected Iterable<IdMapping> getIdMappingsDefinedLocally(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, IdMapping>(
				typeMapping.getAttributeMappings(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
	}
	
	
	// **************** embedded id mappings **********************************
	
	/**
	 * Return whether an embedded id is defined for this class, whether that be locally
	 * or on an ancestor
	 */
	protected boolean definesEmbeddedIdMapping(TypeMapping typeMapping) {
		return ! CollectionTools.isEmpty(getEmbeddedIdMappings(typeMapping));
	}
	
	protected EmbeddedIdMapping getEmbeddedIdMapping(TypeMapping typeMapping) {
		Iterable<EmbeddedIdMapping> embeddedIdMappings = getEmbeddedIdMappings(typeMapping);
		if (CollectionTools.size(embeddedIdMappings) == 1) {
			return embeddedIdMappings.iterator().next();
		}
		return null;
	}
	
	protected Iterable<EmbeddedIdMapping> getEmbeddedIdMappings(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, EmbeddedIdMapping>(
				typeMapping.getAllAttributeMappings(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
	}
	
	protected Iterable<EmbeddedIdMapping> getEmbeddedIdMappingsDefinedLocally(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, EmbeddedIdMapping>(
				typeMapping.getAttributeMappings(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
	}
	
	
	// **************** derived id mappings ***********************************
	
	protected Iterable<SingleRelationshipMapping2_0> getDerivedIdMappings(TypeMapping typeMapping) {
		return new FilteringIterable<SingleRelationshipMapping2_0>(
				new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(
					new CompositeIterable<AttributeMapping>(
						typeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						typeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)))) {
			@Override
			protected boolean accept(SingleRelationshipMapping2_0 o) {
				return o.getDerivedIdentity().usesIdDerivedIdentityStrategy();
			}
		};
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getDerivedIdMappingsDefinedLocally(TypeMapping typeMapping) {
		return new FilteringIterable<SingleRelationshipMapping2_0>(
				new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(
					new CompositeIterable<AttributeMapping>(
						typeMapping.getAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						typeMapping.getAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)))) {
			@Override
			protected boolean accept(SingleRelationshipMapping2_0 o) {
				return o.getDerivedIdentity().usesIdDerivedIdentityStrategy();
			}
		};
	}
	
	
	// **************** maps id mappings **************************************
	
	protected Iterable<SingleRelationshipMapping2_0> getMapsIdMappings(TypeMapping typeMapping) {
		return new FilteringIterable<SingleRelationshipMapping2_0>(
				new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(
					new CompositeIterable<AttributeMapping>(
						typeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						typeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)))) {
			@Override
			protected boolean accept(SingleRelationshipMapping2_0 o) {
				return o.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
			}
		};
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getMapsIdMappingsDefinedLocally(TypeMapping typeMapping) {
		return new FilteringIterable<SingleRelationshipMapping2_0>(
				new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(
					new CompositeIterable<AttributeMapping>(
						typeMapping.getAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						typeMapping.getAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)))) {
			@Override
			protected boolean accept(SingleRelationshipMapping2_0 o) {
				return o.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
			}
		};
	}
	
	
	// **************** misc **************************************************
	
	/**
	 * Return whether an id class is required for this class
	 */
	protected boolean idClassIsRequired() {
		// An id class is required if
		// - there are multiple "simple" primary key mappings
		// - or there is at least one "complex" primary key mapping
		// A simple primary key mapping can be
		// - an id mapping
		// - a derived id relationship mapping to an entity with a simple primary key
		// A complex primary key mapping can be 
		// - a derived id relationship mapping to an entity with a complex primary key
		int simplePrimaryKeyMappingCount = 
				CollectionTools.size(getIdMappings(typeMapping()));
		if (simplePrimaryKeyMappingCount > 1) {
			return true;
		}
		for (SingleRelationshipMapping2_0 relationshipMapping : getDerivedIdMappings(typeMapping())) {
			Entity entity = relationshipMapping.getResolvedTargetEntity();
			if (entity != null) {
				if (definesComplexPrimaryKey(entity)) {
					return true;
				}
				else {
					simplePrimaryKeyMappingCount++;
				}
				if (simplePrimaryKeyMappingCount > 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean hasDerivedIdMappingMatchingIdClass(JavaPersistentType idClass) {
		for (SingleRelationshipMapping2_0 each : getDerivedIdMappings(typeMapping())) {
			String primaryKeyTypeName = getTargetEntityPrimaryKeyTypeName(each);
			if (idClass.getName().equals(primaryKeyTypeName)) {
				return true;
			}
		}
		return false;
	}
	
	protected String getTargetEntityPrimaryKeyTypeName(SingleRelationshipMapping2_0 relationshipMapping) {
		Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
		if (targetEntity != null) {
			return getPrimaryKeyTypeName(targetEntity);
		}
		return null;
	}
	
	protected String getTypeNameForIdClass(AttributeMapping attributeMapping) {
		if (StringTools.stringsAreEqual(attributeMapping.getKey(), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY)
				|| StringTools.stringsAreEqual(attributeMapping.getKey(), MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY)) {
			return attributeMapping.getPersistentAttribute().getTypeName();
		}
		if (StringTools.stringsAreEqual(attributeMapping.getKey(), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)
				|| StringTools.stringsAreEqual(attributeMapping.getKey(), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY)) {
			SingleRelationshipMapping2_0 relationshipMapping = (SingleRelationshipMapping2_0) attributeMapping;
			Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
			if (targetEntity != null) {
				return getPrimaryKeyTypeName(targetEntity);
			}
		}
		return null;
	}
}
