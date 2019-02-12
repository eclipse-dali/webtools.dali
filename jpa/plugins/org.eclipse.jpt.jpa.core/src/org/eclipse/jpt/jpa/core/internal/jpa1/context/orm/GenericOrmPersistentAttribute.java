/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> persistent attribute
 */
public class GenericOrmPersistentAttribute
	extends AbstractOrmPersistentAttribute
{
	public GenericOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		super(parent, xmlMapping);
	}


	// ********** validation **********

	@Override
	protected void validateAttribute(List<IMessage> messages, IReporter reporter) {
		super.validateAttribute(messages, reporter);
		if (this.javaPersistentAttribute != null) {
			JavaPersistentType javaPersistentType = this.getDeclaringPersistentType().getJavaPersistentType();
			if ((javaPersistentType != null) && (javaPersistentType.getAttributeNamed(this.javaPersistentAttribute.getName()) == null)) {
				messages.add(
						this.buildValidationMessage(
							this.mapping,
							this.mapping.getNameTextRange(),
							JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED,
							this.getName(),
							this.getDeclaringPersistentType().getClass_()
						)
					);
			}
		}
	}

	@Override
	protected JpaValidator buildAttibuteValidator() {
		return this.getJavaPersistentAttribute().getAccessor().buildAttributeValidator(this);
	}
}
