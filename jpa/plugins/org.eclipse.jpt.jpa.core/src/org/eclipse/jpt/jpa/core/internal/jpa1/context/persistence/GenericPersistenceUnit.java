/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.internal.GenericJpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * generic <code>persistence-unit</code>
 */
public class GenericPersistenceUnit
	extends AbstractPersistenceUnit
{
	public GenericPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}

	public JpaJpqlQueryHelper createJpqlQueryHelper() {
		return new GenericJpaJpqlQueryHelper(this.getJpaPlatform().getJpqlGrammar());
	}

	@Override
	protected void validateProperties(List<IMessage> messages, IReporter reporter) {
		// do nothing
	}
}