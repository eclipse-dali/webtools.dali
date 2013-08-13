/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractMappedSuperclassPrimaryKeyValidator
	extends AbstractPrimaryKeyValidator
{
	protected AbstractMappedSuperclassPrimaryKeyValidator(
			MappedSuperclass mappedSuperclass) {
		
		super(mappedSuperclass);
	}
	
	
	protected MappedSuperclass mappedSuperclass() {
		return (MappedSuperclass) this.typeMapping();
	}
	
	public boolean validate(List<IMessage> messages, IReporter reporter) {
		validatePrimaryKeyIsNotRedefined(messages, reporter);
		validateIdClassIsUsedIfNecessary(messages, reporter);
		
		// if primary key is composite, it may either use an id class or embedded id, not both
		validateOneOfIdClassOrEmbeddedIdIsUsed(messages, reporter);
		// ... and only one embedded id
		validateOneEmbeddedId(messages, reporter);
		// ... and not both id and embedded id
		validateOneOfEmbeddedOrIdIsUsed(messages, reporter);
		
		if (declaresIdClassInHierarchy()) {
			validateIdClass(idClassReference().getIdClass(), messages, reporter);
		}
		return true;
	}
}
