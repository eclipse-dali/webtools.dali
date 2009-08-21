/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaOneToOneMapping;
import org.eclipse.jpt.core.jpa2.context.DerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;

public abstract class AbstractJavaOneToOneMapping2_0
	extends AbstractJavaOneToOneMapping
	implements JavaOneToOneMapping2_0
{
	protected JavaDerivedId2_0 derivedId;
	
	
	protected AbstractJavaOneToOneMapping2_0(JavaPersistentAttribute parent) {
		super(parent);
		this.derivedId = buildDerivedId();
	}
	
	
	protected JavaDerivedId2_0 buildDerivedId() {
		return new GenericJavaDerivedId2_0(this);
	}
	
	@Override
	public Iterator<String> supportingAnnotationNames() {
		return new CompositeIterator(
				super.supportingAnnotationNames(),
				JPA.ID);
	}
	
	public DerivedId2_0 getDerivedId() {
		return this.derivedId;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.derivedId.initialize();
	}
	
	@Override
	protected void update() {
		super.update();
		this.derivedId.update();
	}
}
