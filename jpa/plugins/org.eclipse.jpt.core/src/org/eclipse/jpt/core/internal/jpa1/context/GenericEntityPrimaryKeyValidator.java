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

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericEntityPrimaryKeyValidator
	extends AbstractPrimaryKeyValidator
{
	public GenericEntityPrimaryKeyValidator(Entity entity, PrimaryKeyTextRangeResolver textRangeResolver) {
		super(entity, textRangeResolver);
	}
	
	
	protected Entity entity() {
		return (Entity) this.typeMapping();
	}
	
	@Override
	protected IdClassReference idClassReference() {
		return entity().getIdClassReference();
	}
	
	public void validate(List<IMessage> messages, IReporter reporter) {
		// if an entity is non-root, it is not allowed to define primary keys
		if (! entity().isRoot()) {
			validatePrimaryKeyForNonRoot(messages, reporter);
		}
		else {
			validatePrimaryKeyForRoot(messages, reporter);
		}
	}
	
	protected void validatePrimaryKeyForNonRoot(List<IMessage> messages, IReporter reporter) {
		validateNonRootEntityDoesNotSpecifyIdClass(messages, reporter);
		validateNonRootEntityDoesNotSpecifyPrimaryKeyAttributes(messages, reporter);
	}
	
	protected void validatePrimaryKeyForRoot(List<IMessage> messages, IReporter reporter) {
		validatePrimaryKeyIsNotRedefined(messages, reporter);
		validateIdClassIsUsedIfNecessary(messages, reporter);
		
		// if the primary key is not defined on an ancestor, it must be defined here
		if (! definesPrimaryKey(typeMapping())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_NO_PK,
						new String[0],
						entity(),
						textRangeResolver().getTypeMappingTextRange()));
		}
		
		// if primary key is composite, it may either use an id class or embedded id, not both
		validateOneOfIdClassOrEmbeddedIdIsUsed(messages, reporter);
		// ... and only one embedded id
		validateOneEmbeddedId(messages, reporter);
		
		validateMapsIdMappings(messages, reporter);
		
		if (specifiesIdClass()) {
			validateIdClass(idClassReference().getIdClass(), messages, reporter);
		}
	}
	
	protected void validateNonRootEntityDoesNotSpecifyIdClass(List<IMessage> messages, IReporter reporter) {
		if (idClassReference().isSpecified()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_NON_ROOT_ID_CLASS_SPECIFIED,
						new String[0],
						entity(),
						textRangeResolver().getIdClassTextRange()));
		}
	}
	
	protected void validateNonRootEntityDoesNotSpecifyPrimaryKeyAttributes(List<IMessage> messages, IReporter reporter) {
		for (AttributeMapping each : getPrimaryKeyMappingsDefinedLocally(typeMapping())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ENTITY_NON_ROOT_ID_ATTRIBUTE_SPECIFIED,
						new String[0],
						each,
						textRangeResolver().getAttributeMappingTextRange(each.getName())));
		}
	}
}
