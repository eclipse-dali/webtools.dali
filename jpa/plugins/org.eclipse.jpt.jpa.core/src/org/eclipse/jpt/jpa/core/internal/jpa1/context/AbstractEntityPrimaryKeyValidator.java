/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractEntityPrimaryKeyValidator extends
		AbstractPrimaryKeyValidator {

	protected AbstractEntityPrimaryKeyValidator(Entity entity) {
		super(entity);
	}

	protected Entity entity() {
		return (Entity) this.typeMapping();
	}
	
	public boolean validate(List<IMessage> messages, IReporter reporter) {
		// if an entity is non-root, it is not allowed to define primary keys
		if (! entity().isRootEntity()) {
			validatePrimaryKeyForNonRoot(messages, reporter);
		}
		else {
			validatePrimaryKeyForRoot(messages, reporter);
		}
		return true;
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
					ValidationMessageTools.buildValidationMessage(
						entity().getResource(),
						entity().getValidationTextRange(),
						JptJpaCoreValidationMessages.ENTITY_NO_PK
					)
				);
		}
		
		// if primary key is composite, it may either use an id class or embedded id, not both
		validateOneOfIdClassOrEmbeddedIdIsUsed(messages, reporter);
		// ... and only one embedded id
		validateOneEmbeddedId(messages, reporter);
		// ... and not both id and embedded id
		validateOneOfEmbeddedOrIdIsUsed(messages, reporter);
		
		validateMapsIdMappings(messages, reporter);
		
		if (declaresIdClassInHierarchy()) {
			validateIdClass(idClassReference().getIdClass(), messages, reporter);
		}
	}
	
	protected void validateNonRootEntityDoesNotSpecifyIdClass(List<IMessage> messages, IReporter reporter) {
		if (declaresIdClassLocally()) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						entity().getResource(),
						getIdClassRefValidationTextRange(),
						JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_CLASS_SPECIFIED
					)
				);
		}
	}
	
	protected void validateNonRootEntityDoesNotSpecifyPrimaryKeyAttributes(List<IMessage> messages, IReporter reporter) {
		for (AttributeMapping each : getPrimaryKeyMappingsDefinedLocally(typeMapping())) {
			messages.add(
					ValidationMessageTools.buildValidationMessage(
						each.getResource(),
						getAttributeMappingTextRange(each),
						JptJpaCoreValidationMessages.ENTITY_NON_ROOT_ID_ATTRIBUTE_SPECIFIED
					)
				);
		}
	}
	
	@Override
	protected TextRange getIdClassRefValidationTextRange() {
		return declaresIdClassLocally() ? super.getIdClassRefValidationTextRange() : typeMapping().getValidationTextRange();
	}
}
