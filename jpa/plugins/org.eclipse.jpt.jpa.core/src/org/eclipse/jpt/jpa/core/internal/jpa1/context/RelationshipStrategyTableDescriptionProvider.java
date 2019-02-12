/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.SpecifiedRelationshipStrategy;

public class RelationshipStrategyTableDescriptionProvider
	implements AbstractNamedColumnValidator.TableDescriptionProvider
{
	private final SpecifiedRelationshipStrategy relationshipStrategy;
	
	public RelationshipStrategyTableDescriptionProvider(SpecifiedRelationshipStrategy relationshipStrategy) {
		super();
		this.relationshipStrategy = relationshipStrategy;
	}

	public String getColumnTableDescriptionMessage() {
		return this.relationshipStrategy.getColumnTableNotValidDescription();
	}
}
