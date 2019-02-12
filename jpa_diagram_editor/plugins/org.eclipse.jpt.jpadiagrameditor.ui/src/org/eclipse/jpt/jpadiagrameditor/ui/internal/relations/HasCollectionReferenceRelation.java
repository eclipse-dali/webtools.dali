/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012, 2013 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/

package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;

public class HasCollectionReferenceRelation extends HasReferanceRelation{

	public HasCollectionReferenceRelation(PersistentType embeddingEntity,
			PersistentType embeddable) {
		super(embeddingEntity, embeddable);
	}
	
	public HasCollectionReferenceRelation(IJPAEditorFeatureProvider fp,
			Connection conn) {
		super(fp, conn);
	}

	@Override
	public HasReferenceType getReferenceType() {
		return HasReferenceType.COLLECTION;
	}

}
