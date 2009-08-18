/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.utility.Filter;

public class GenericJavaAssociationOverrideRelationshipReference extends AbstractJavaAssociationOverrideRelationshipReference
{

	public GenericJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		super(parent);
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.joinColumnJoiningStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}

}
