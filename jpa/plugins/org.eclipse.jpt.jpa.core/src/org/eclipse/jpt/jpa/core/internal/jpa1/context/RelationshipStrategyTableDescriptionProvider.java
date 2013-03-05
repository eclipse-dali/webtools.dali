/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
