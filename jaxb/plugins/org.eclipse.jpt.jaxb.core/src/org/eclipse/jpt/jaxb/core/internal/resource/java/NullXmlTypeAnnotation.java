/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * javax.xml.bind.annotation.XmlType
 */
public final class NullXmlTypeAnnotation
	extends NullAnnotation
	implements XmlTypeAnnotation
{
	protected NullXmlTypeAnnotation(AbstractJavaResourceType parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected XmlTypeAnnotation addAnnotation() {
		return (XmlTypeAnnotation) super.addAnnotation();
	}


	// ********** XmlTypeAnnotation implementation **********

	// ***** factory class
	public String getFactoryClass() {
		return null;
	}

	public String getFullyQualifiedFactoryClassName() {
		return null;
	}

	public void setFactoryClass(String factoryClass) {
		if (factoryClass != null) {
			this.addAnnotation().setFactoryClass(factoryClass);
		}
	}

	public TextRange getFactoryClassTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** factory method
	public String getFactoryMethod() {
		return null;
	}

	public void setFactoryMethod(String factoryMethod) {
		if (factoryMethod != null) {
			this.addAnnotation().setFactoryMethod(factoryMethod);
		}
	}

	public TextRange getFactoryMethodTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** name
	public String getName() {
		return null;
	}

	public void setName(String name) {
		if (name != null) {
			this.addAnnotation().setName(name);
		}
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return null;
	}

	public void setNamespace(String namespace) {
		if (namespace != null) {
			this.addAnnotation().setNamespace(namespace);
		}
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	
	public ListIterable<String> getPropOrder() {
		return EmptyListIterable.instance();
	}

	public int getPropOrderSize() {
		return 0;
	}

	public void addProp(int index, String prop) {
		this.addAnnotation().addProp(index, prop);
	}

	public void addProp(String prop) {
		this.addAnnotation().addProp(prop);
	}

	public void moveProp(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeProp(int index) {
		throw new UnsupportedOperationException();
	}

	public void removeProp(String prop) {
		throw new UnsupportedOperationException();
	}

}
