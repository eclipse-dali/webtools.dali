/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;

public interface NonOwningMapping extends RelationshipMapping
{
	String getMappedBy();
	void setMappedBy(String value);
		String MAPPED_BY_PROPERTY = "mappedByProperty";

	Iterator<String> candidateMappedByAttributeNames();

	boolean mappedByIsValid(AttributeMapping mappedByMapping);

	TextRange mappedByTextRange(CompilationUnit astRoot);
}