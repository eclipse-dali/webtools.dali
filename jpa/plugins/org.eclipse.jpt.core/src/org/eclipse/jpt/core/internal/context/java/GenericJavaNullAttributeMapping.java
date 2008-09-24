/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;



public class GenericJavaNullAttributeMapping extends AbstractJavaAttributeMapping<JavaResourceNode>
{
	public GenericJavaNullAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return null;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}
	
	@Override
	protected JavaResourceNode getResourceMapping() {
		return null;
	}
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
	}
}
