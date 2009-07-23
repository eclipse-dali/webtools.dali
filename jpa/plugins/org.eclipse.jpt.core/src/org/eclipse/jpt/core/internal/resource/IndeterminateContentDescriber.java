/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource;

import java.io.InputStream;
import java.io.Reader;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;

/**
 * This class simply returns INDETERMINATE for any contents it receives.
 * 
 * It is used currently for org.eclipse.jpt.core.content.baseJpaContent in order
 * to make that content type act as an "abstract" content type.
 */
public class IndeterminateContentDescriber implements ITextContentDescriber
{
	public int describe(InputStream contents, IContentDescription description) {
		return INDETERMINATE;
	}
	
	public int describe(Reader contents, IContentDescription description) {
		return INDETERMINATE;
	}
	
	public QualifiedName[] getSupportedOptions() {
		return new QualifiedName[0];
	}
}
