/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.List;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa1.context.GenericPersistentAttributeValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericOrmPersistentAttribute extends AbstractOrmPersistentAttribute
{
	
	public GenericOrmPersistentAttribute(OrmPersistentType parent, Owner owner, XmlAttributeMapping resourceMapping) {
		super(parent, owner, resourceMapping);
	}
	
	//****************** AccessHolder implementation *******************
	
	/**
	 * GenericOrmPersistentAttribute does not support specified access (no access element in 1.0), so we return null
	 */
	public AccessType getSpecifiedAccess() {
		return null;
	}
	
	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		throw new UnsupportedOperationException("specifiedAccess is not supported for GenericOrmPersistentAttribute"); //$NON-NLS-1$
	}
	
	@Override
	protected void validateAttribute(List<IMessage> messages, IReporter reporter) {
		super.validateAttribute(messages, reporter);
		if (this.javaPersistentAttribute != null) {
			JavaPersistentType javaPersistentType = getOwningPersistentType().getJavaPersistentType();
			if (javaPersistentType != null && javaPersistentType.getAttributeNamed(this.javaPersistentAttribute.getName()) == null) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.NORMAL_SEVERITY,
							JpaValidationMessages.PERSISTENT_ATTRIBUTE_INHERITED_ATTRIBUTES_NOT_SUPPORTED,
							new String[] {this.getName(), this.getOwningPersistentType().getMapping().getClass_()},
							this.attributeMapping, 
							this.attributeMapping.getNameTextRange()
						)
					);				
			}
		}
	}

	@Override
	protected JptValidator buildAttibuteValidator() {
		return new GenericPersistentAttributeValidator(this, getJavaPersistentAttribute(), buildTextRangeResolver());
	}
}
