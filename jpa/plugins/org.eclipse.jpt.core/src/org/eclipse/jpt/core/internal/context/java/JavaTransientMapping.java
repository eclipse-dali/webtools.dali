/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.resource.java.Transient;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class JavaTransientMapping extends JavaAttributeMapping
	implements IJavaTransientMapping
{
	public JavaTransientMapping(IJavaPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Transient.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}

	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
	}
	
}