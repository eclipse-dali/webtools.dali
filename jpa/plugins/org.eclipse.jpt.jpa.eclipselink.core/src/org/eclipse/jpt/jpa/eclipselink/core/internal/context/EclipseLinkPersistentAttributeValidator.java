/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractPersistentAttributeValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkPersistentAttributeValidator
	extends AbstractPersistentAttributeValidator
{
	public EclipseLinkPersistentAttributeValidator(
		ReadOnlyPersistentAttribute persistentAttribute)
	{
		super(persistentAttribute);
	}

	/**
	 * EclipseLink does not have the same restrictions on the
	 * attribute as the JPA spec does (e.g. the spec prohibits a field from
	 * being public).
	 */
	@Override
	protected void validateMappedAttribute(List<IMessage> messages) {
		return;
	}
}
