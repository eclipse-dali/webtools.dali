/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.Collection;
import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdClassReference;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractPrimaryKeyValidator
	implements JptValidator
{
	private final IdTypeMapping typeMapping;
	
	public static final String[] EMPTY_STRING_ARRAY = StringTools.EMPTY_STRING_ARRAY;
	
	protected AbstractPrimaryKeyValidator(IdTypeMapping typeMapping) {
		
		this.typeMapping = typeMapping;
	}
	
	
	protected IdTypeMapping typeMapping() {
		return this.typeMapping;
	}
	
	protected IdClassReference idClassReference() {
		return typeMapping().getIdClassReference();

	}

	protected TextRange getAttributeMappingTextRange(AttributeMapping attributeMapping) {
		PersistentAttribute attribute = attributeMapping.getPersistentAttribute();
		if (attribute.isVirtual()) {
			return attribute.getValidationTextRange();
		}
		return attributeMapping.getValidationTextRange();
	}

	// for JPA portability, a hierarchy must define its primary key on one class 
	// (entity *or* mapped superclass)
	protected void validatePrimaryKeyIsNotRedefined(List<IMessage> messages, IReporter reporter) {
		if (definesPrimaryKeyOnAncestor(typeMapping())) {
			if (idClassReference().isSpecified()) {
				messages.add(
						ValidationMessageTools.buildValidationMessage(
							typeMapping().getResource(),
							idClassReference().getValidationTextRange(),
							JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS
						)
					);
			}
			for (AttributeMapping each : getPrimaryKeyMappingsDefinedLocally(typeMapping())) {
				messages.add(
						ValidationMessageTools.buildValidationMessage(
							each.getResource(),
							getAttributeMappingTextRange(each),
							JptJpaCoreValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE
						)
					);
			}
			return;
		}
	}
	
	// if a primary key defining class has multiple primary keys, it must use an id class
	protected void validateIdClassIsUsedIfNecessary(List<IMessage> messages, IReporter reporter) {
		if (! specifiesIdClass() && idClassIsRequired()) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						typeMapping().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_REQUIRED
					)
				);
		}
	}
	
	// only one composite primary key strategy may be used
	protected void validateOneOfIdClassOrEmbeddedIdIsUsed(List<IMessage> messages, IReporter reporter) {
		if (idClassReference().isSpecified()
				&& IterableTools.size(typeMapping().getAllAttributeMappings(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY)) > 0) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						typeMapping().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_AND_EMBEDDED_ID_BOTH_USED
					)
				);
		}
	}
	
	//only embedded or id, both may not be used
	protected void validateOneOfEmbeddedOrIdIsUsed(List<IMessage> messages, IReporter reporter) {
		if (definesEmbeddedIdMapping(typeMapping) && definesIdMapping(typeMapping)) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						typeMapping().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_AND_EMBEDDED_ID_BOTH_USED
					)
				);
		}
	}
	
	// only one embedded id may be used
	protected void validateOneEmbeddedId(List<IMessage> messages, IReporter reporter) {
		if (IterableTools.size(getEmbeddedIdMappings(typeMapping())) > 1) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						typeMapping().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_MULTIPLE_EMBEDDED_ID
					)
				);
		}
	}
	
	protected void validateMapsIdMappings(List<IMessage> messages, IReporter reporter) {
		for (SingleRelationshipMapping2_0 mapsIdRelationshipMapping : getMapsIdMappingsDefinedLocally(typeMapping())) {
			// can't use maps id mappings with an id class
			if (definesIdClass(typeMapping())) {
				messages.add(
						ValidationMessageTools.buildValidationMessage(
							mapsIdRelationshipMapping.getResource(),
							getAttributeMappingTextRange(mapsIdRelationshipMapping),
							JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_WITH_MAPS_ID,
							mapsIdRelationshipMapping.getName()
						)
					);
			}
			
			AttributeMapping resolvedAttributeMapping = 
					mapsIdRelationshipMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getDerivedIdAttributeMapping();
			if (resolvedAttributeMapping != null 
					&& ! ClassNameTools.isAutoboxEquivalent(
						resolvedAttributeMapping.getPersistentAttribute().getTypeName(), 
						getTargetEntityPrimaryKeyTypeName(mapsIdRelationshipMapping))) {
				messages.add(
						ValidationMessageTools.buildValidationMessage(
							mapsIdRelationshipMapping.getResource(),
							getAttributeMappingTextRange(mapsIdRelationshipMapping),
							JptJpaCoreValidationMessages.TYPE_MAPPING_MAPS_ID_ATTRIBUTE_TYPE_DOES_NOT_AGREE,
							mapsIdRelationshipMapping.getName()
						)
					);
			}
		}
	}
	
	protected void validateIdClass(JavaPersistentType idClass, List<IMessage> messages, IReporter reporter) {
		// there should already be a validation error if the id class does not resolve to a class
		if (idClass == null) {
			return;
		}
		
		if (hasDerivedIdMappingMatchingIdClass(idClass)) {
			validateIdClass_derivedIdMappingMatchingIdClass(idClass, messages, reporter);
			return;
		}

		for (JavaSpecifiedPersistentAttribute idClassAttribute : this.getAllIdClassAttributes(idClass)) {
			boolean foundMatch = false;
			for (AttributeMapping attributeMapping : getAttributeMappings(typeMapping())) {
				if (idClassAttribute.getName().equals(attributeMapping.getName())) {
					foundMatch = true;
					
					// the matching attribute should be a primary key
					if (! definesPrimaryKey(attributeMapping)) {
						messages.add(
								ValidationMessageTools.buildValidationMessage(
									typeMapping().getResource(),
									idClassReference().getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY,
									idClassAttribute.getName()
								)
							);
					}
					
					// the matching attribute's type should agree
					String idClassAttributeTypeName = idClassAttribute.getTypeName(idClass);
					String attributeMappingTypeName = getTypeNameForIdClass(attributeMapping);
					if (attributeMappingTypeName != null 	// if it's null, there should be 
																// another failing validation elsewhere
							&& ! ClassNameTools.isAutoboxEquivalent(idClassAttributeTypeName, attributeMappingTypeName)) {
						messages.add(
								ValidationMessageTools.buildValidationMessage(
									typeMapping().getResource(),
									idClassReference().getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE,
									idClassAttribute.getName(),
									idClassAttributeTypeName
								)
							);
					}
				}
			}
			
			if (! foundMatch) {
				messages.add(
						ValidationMessageTools.buildValidationMessage(
							typeMapping().getResource(),
							idClassReference().getValidationTextRange(),
							JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH,
							idClassAttribute.getName()
						)
					);
			}
		}

		// This is for validating if a type mapping has extra id attributes that do not match
		// an attribute on the id class, which is a supplement of the validation right above
		for (AttributeMapping attributeMapping : getPrimaryKeyMappings(typeMapping())) {
			AccessType type = attributeMapping.getPersistentAttribute().getDeclaringPersistentType().getAccess();
			if (type == AccessType.FIELD) {
				checkMissingAttribute(idClass, attributeMapping, messages, reporter);
			} else if (type == AccessType.PROPERTY) {
				// EclipseLink does not care about the existence status of property methods,
				// but the matching field in the id class still needs to exist
				if (!IterableTools.contains(getIdClassFieldNames(idClass), attributeMapping.getName())) {
					messages.add(
							ValidationMessageTools.buildValidationMessage(
								typeMapping().getResource(),
								idClassReference().getValidationTextRange(),
								JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_DOES_NOT_EXIST,
								attributeMapping.getName()
							)
						);
				} else {
					// Validation for missing property methods is only for generic platform
					checkMissingAttributeWithPropertyAccess(idClass, attributeMapping, messages, reporter);
				}
			}
		}

		// This is for validating cases when id class has property-based access
		if (typeMapping().getPersistentType().getAccess() == AccessType.PROPERTY) {
			validateIdClassAttributesWithPropertyAccess(idClass, messages, reporter);
		}

		validateIdClassConstructor(idClass, messages, reporter);
	}

	protected void checkMissingAttribute(JavaPersistentType idClass,
			AttributeMapping attributeMapping, List<IMessage> messages, IReporter reporter) {
		if (!IterableTools.contains(getIdClassAttributeNames(idClass), attributeMapping.getName())) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						idClassReference().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_DOES_NOT_EXIST,
						attributeMapping.getName()
					)
				);
		}
	}

	protected abstract void validateIdClassAttributesWithPropertyAccess(
			JavaPersistentType idClass, List<IMessage> messages,
			IReporter reporter);

	protected void validateIdClassConstructor(JavaPersistentType idClass,
			List<IMessage> messages, IReporter reporter) {
		if (!idClass.getJavaResourceType().hasNoArgConstructor()) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(),
						idClassReference().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_NO_ARG_CONSTRUCTOR,
						idClass.getName()
					)
				);
		}
	}
	
	protected void checkMissingAttributeWithPropertyAccess(JavaPersistentType idClass,
			AttributeMapping attributeMapping, 
			List<IMessage> messages, IReporter reporter) {
		// do nothing
	}

	protected void validateIdClass_derivedIdMappingMatchingIdClass(
			JavaPersistentType idClass, List<IMessage> messages, IReporter reporter) {
		
		Collection<AttributeMapping> errorMappings = new HashBag<AttributeMapping>();
		for (AttributeMapping each 
				: IterableTools.concatenate(getIdMappings(typeMapping()), getEmbeddedIdMappings(typeMapping()))) {
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
		if (IterableTools.size(errorDerivedIdMappings) > 1) {
			for (AttributeMapping each : errorDerivedIdMappings) {
				addDuplicateIdClassAttributeMatchError(each, messages);
			}
		}
	}
	
	protected void addNoIdClassAttributeMatchError(AttributeMapping attributeMapping, List<IMessage> messages) {
		messages.add(
				ValidationMessageTools.buildValidationMessage(
					typeMapping().getResource(),
					idClassReference().getValidationTextRange(),
					JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_NO_MATCH,
					attributeMapping.getName()
				)
			);
	}
	
	protected void addDuplicateIdClassAttributeMatchError(AttributeMapping attributeMapping, List<IMessage> messages) {
		messages.add(
				ValidationMessageTools.buildValidationMessage(
					typeMapping().getResource(),
					idClassReference().getValidationTextRange(),
					JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_MAPPING_DUPLICATE_MATCH,
					attributeMapping.getName()
				)
			);
	}
	
	protected void validateIdClassPropertyMethods(
			JavaPersistentType idClass, List<IMessage> messages, IReporter reporter) {

			for (JavaSpecifiedPersistentAttribute attribute : getAllIdClassAttributes(idClass)) {
				PropertyAccessor accessor = (PropertyAccessor)attribute.getAccessor();

				// validate getter method
				JavaResourceMethod getter = accessor.getResourceGetter();
				if (getter != null) {
					validatePropertyMethod(idClass, getter.getMethodName(), messages, reporter);
				}

				// validate setter method
				JavaResourceMethod setter = accessor.getResourceSetter();
				if (setter != null) {
					validatePropertyMethod(idClass, setter.getMethodName(), messages, reporter);
				}
			}
	}

	private void validatePropertyMethod(JavaPersistentType idClass,
			String methodName, List<IMessage> messages, IReporter reporter) {

		JavaResourceMethod method = idClass.getJavaResourceType().getMethod(methodName);

		if (!method.isPublicOrProtected()) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						typeMapping().getResource(), 
						idClassReference().getValidationTextRange(),
						JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_PROPERTY_METHOD_NOT_PUBLIC,
						idClass.getJavaResourceType().getTypeBinding().getQualifiedName(),
						methodName
					)
				);
		}
	}

	protected Iterable<String> getIdClassAttributeNames(JavaPersistentType idClass) {
		return IterableTools.transform(getAllIdClassAttributes(idClass), PersistentAttribute.NAME_TRANSFORMER);
	}

	protected Iterable<JavaSpecifiedPersistentAttribute> getAllIdClassAttributes(JavaPersistentType idClass) {
		return new SubIterableWrapper<PersistentAttribute, JavaSpecifiedPersistentAttribute>(idClass.getAllAttributes());
	}

	protected Iterable<String> getIdClassFieldNames(JavaPersistentType idClass) {
		return IterableTools.transform(idClass.getJavaResourceType().getFields(), JavaResourceMember.NAME_TRANSFORMER);
	}
	
	// **************** convenience methods ********************************************************
	
	// **************** primary key overall ********************************************************
	
	/**
	 * Return whether an ancestor class has defined any aspect of the primary key
	 */
	protected boolean definesPrimaryKeyOnAncestor(TypeMapping typeMapping) {
		for (TypeMapping each : typeMapping.getInheritanceHierarchy()) {
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
				|| ! IterableTools.isEmpty(getPrimaryKeyMappings(typeMapping));
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
			return embeddedId.getPersistentAttribute().getTypeName(typeMapping.getPersistentType());
		}
		IdMapping id = getIdMapping(typeMapping);
		if (id != null) {
			return id.getPersistentAttribute().getTypeName(typeMapping.getPersistentType());
		}
		return null;
	}
	
	/**
	 * Return whether the attribute mapping has defined any aspect of the primary key
	 */
	protected boolean definesPrimaryKey(AttributeMapping attributeMapping) {
		String mappingKey = attributeMapping.getKey();
		if (IterableTools.contains(this.getIdMappingKeys(), mappingKey)) {
			return true;
		}
		if (IterableTools.contains(this.getSingleRelationshipMappingKeys(), mappingKey)) {
			SingleRelationshipMapping2_0 relationshipMapping = (SingleRelationshipMapping2_0) attributeMapping;
			return (relationshipMapping.getDerivedIdentity().usesIdDerivedIdentityStrategy()
					|| relationshipMapping.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy());
		}
		return false;
	}

	protected Iterable<String> getIdMappingKeys() {
		return ID_MAPPING_KEYS;
	}

	protected static final String[] ID_MAPPING_KEYS_ARRAY = new String[] {
		MappingKeys.ID_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY
	};
	
	protected static final Iterable<String> ID_MAPPING_KEYS = IterableTools.iterable(ID_MAPPING_KEYS_ARRAY);

	protected Iterable<String> getSingleRelationshipMappingKeys() {
		return SINGLE_RELATIONSHIP_MAPPING_KEYS;
	}

	protected static final String[] SINGLE_RELATIONSHIP_MAPPING_KEYS_ARRAY = new String[] {
		MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
		MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY
	};
	
	protected static final Iterable<String> SINGLE_RELATIONSHIP_MAPPING_KEYS = IterableTools.iterable(SINGLE_RELATIONSHIP_MAPPING_KEYS_ARRAY);

	
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
		for (TypeMapping each : typeMapping.getInheritanceHierarchy()) {
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
		for (TypeMapping each : typeMapping.getInheritanceHierarchy()) {
			if (each.getIdClass() != null) {
				return each.getIdClass();
			}
		}
		return null;
	}
	
	
	// **************** attribute mappings in general *************************
	
	/**
	 * Return all the attribute mappings of the given type  
	 * mapping with transient attribute mappings being excluded
	 */
	protected Iterable<AttributeMapping> getAttributeMappings(TypeMapping typeMapping) {
		return IterableTools.filter(typeMapping.getAllAttributeMappings(), AttributeMapping.IS_NOT_TRANSIENT);
	}
	
	/**
	 * Return all primary key mappings, defined on and above the type mapping
	 */
	protected Iterable<AttributeMapping> getPrimaryKeyMappings(TypeMapping typeMapping) {
		return IterableTools.concatenate(
				getIdMappings(typeMapping),
				getEmbeddedIdMappings(typeMapping),
				getDerivedIdMappings(typeMapping),
				getMapsIdMappings(typeMapping));
	}
	
	/**
	 * Return primary key mappings declared directly on the type mapping
	 */
	protected Iterable<AttributeMapping> getPrimaryKeyMappingsDefinedLocally(TypeMapping typeMapping) {
		return IterableTools.concatenate(
				getIdMappingsDefinedLocally(typeMapping),
				getEmbeddedIdMappingsDefinedLocally(typeMapping),
				getDerivedIdMappingsDefinedLocally(typeMapping),
				getMapsIdMappingsDefinedLocally(typeMapping));
	}
	
	protected boolean hasAnyPrimaryKeyMappings(TypeMapping typeMapping) {
		return ! IterableTools.isEmpty(getPrimaryKeyMappings(typeMapping));
	}
	
	// **************** id mappings *******************************************
	
	protected IdMapping getIdMapping(TypeMapping typeMapping) {
		Iterable<IdMapping> idMappings = getIdMappings(typeMapping);
		if (IterableTools.size(idMappings) == 1) {
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
		return ! IterableTools.isEmpty(getEmbeddedIdMappings(typeMapping));
	}
	
	/**
	 * Return whether an id is defined for this class, whether that be locally
	 * or on an ancestor
	 */
	protected boolean definesIdMapping(TypeMapping typeMapping) {
		return ! IterableTools.isEmpty(getIdMappings(typeMapping));
	}
	
	protected EmbeddedIdMapping getEmbeddedIdMapping(TypeMapping typeMapping) {
		Iterable<EmbeddedIdMapping> embeddedIdMappings = getEmbeddedIdMappings(typeMapping);
		if (IterableTools.size(embeddedIdMappings) == 1) {
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
		return IterableTools.filter(this.getAllSingleRelationshipMappings(typeMapping), TYPE_MAPPING_USES_ID_DERIVED_IDENTITY_STRATEGY);
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getAllSingleRelationshipMappings(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(this.getAllSingleRelationshipMappings_(typeMapping));
	}
	
	@SuppressWarnings("unchecked")
	protected Iterable<AttributeMapping> getAllSingleRelationshipMappings_(TypeMapping typeMapping) {
		return IterableTools.concatenate(
					typeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
					typeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)
				);
	}

	protected Iterable<SingleRelationshipMapping2_0> getDerivedIdMappingsDefinedLocally(TypeMapping typeMapping) {
		return IterableTools.filter(this.getSingleRelationshipMappings(typeMapping), TYPE_MAPPING_USES_ID_DERIVED_IDENTITY_STRATEGY);
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getSingleRelationshipMappings(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(this.getSingleRelationshipMappings_(typeMapping));
	}
	
	@SuppressWarnings("unchecked")
	protected Iterable<AttributeMapping> getSingleRelationshipMappings_(TypeMapping typeMapping) {
		return IterableTools.concatenate(
					typeMapping.getAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
					typeMapping.getAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)
				);
	}

	public static final Predicate<SingleRelationshipMapping2_0> TYPE_MAPPING_USES_ID_DERIVED_IDENTITY_STRATEGY = new TypeMappingUsesIdDerivedIdentityStrategy();
	public static class TypeMappingUsesIdDerivedIdentityStrategy
		extends Predicate.Adapter<SingleRelationshipMapping2_0>
	{
		@Override
		public boolean evaluate(SingleRelationshipMapping2_0 mapping) {
			return mapping.getDerivedIdentity().usesIdDerivedIdentityStrategy();
		}
	}

	
	// **************** maps id mappings **************************************
	
	protected Iterable<SingleRelationshipMapping2_0> getMapsIdMappings(TypeMapping typeMapping) {
		return IterableTools.filter(this.getAllSingleRelationshipMappings(typeMapping), TYPE_MAPPING_USES_ID_DERIVED_IDENTITY_STRATEGY);
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getMapsIdMappingsDefinedLocally(TypeMapping typeMapping) {
		return IterableTools.filter(this.getSingleRelationshipMappings(typeMapping), TYPE_MAPPING_USES_ID_DERIVED_IDENTITY_STRATEGY);
	}
	
	public static final Predicate<SingleRelationshipMapping2_0> TYPE_MAPPING_USES_MAPS_ID_DERIVED_IDENTITY_STRATEGY = new TypeMappingUsesMapsIdDerivedIdentityStrategy();
	public static class TypeMappingUsesMapsIdDerivedIdentityStrategy
		extends Predicate.Adapter<SingleRelationshipMapping2_0>
	{
		@Override
		public boolean evaluate(SingleRelationshipMapping2_0 mapping) {
			return mapping.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
		}
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
				IterableTools.size(getIdMappings(typeMapping()));
		if (simplePrimaryKeyMappingCount > 1) {
			return true;
		}
		for (SingleRelationshipMapping2_0 relationshipMapping : getDerivedIdMappings(typeMapping())) {
			Entity entity = relationshipMapping.getResolvedTargetEntity();
			if (entity != null) {
				if (definesComplexPrimaryKey(entity)) {
					return true;
				}
				simplePrimaryKeyMappingCount++;
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
		String mappingKey = attributeMapping.getKey();
		if (IterableTools.contains(this.getIdMappingKeys(), mappingKey)) {
			return attributeMapping.getPersistentAttribute().getTypeName(typeMapping().getPersistentType());
		}
		if (IterableTools.contains(this.getSingleRelationshipMappingKeys(), mappingKey)) {
			SingleRelationshipMapping2_0 relationshipMapping = (SingleRelationshipMapping2_0) attributeMapping;
			Entity targetEntity = relationshipMapping.getResolvedTargetEntity();
			if (targetEntity != null) {
				return getPrimaryKeyTypeName(targetEntity);
			}
		}
		return null;
	}
}
