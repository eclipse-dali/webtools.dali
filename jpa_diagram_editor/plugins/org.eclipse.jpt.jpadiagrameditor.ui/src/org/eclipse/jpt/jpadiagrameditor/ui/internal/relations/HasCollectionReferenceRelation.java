/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2012 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/

package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;

public class HasCollectionReferenceRelation extends HasReferanceRelation{

	public HasCollectionReferenceRelation(JavaPersistentType embeddingEntity,
			JavaPersistentType embeddable) {
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
