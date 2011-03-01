/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlType
 */
public final class BinaryXmlTypeAnnotation
	extends BinaryAnnotation
	implements XmlTypeAnnotation
{
	private String factoryClass;
	private String factoryMethod;
	private String name;
	private String namespace;
	private final Vector<String> propOrder;


	public BinaryXmlTypeAnnotation(JavaResourceAbstractType parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.factoryClass = this.buildFactoryClass();
		this.factoryMethod = this.buildFactoryMethod();
		this.name = this.buildName();
		this.namespace = this.buildNamespace();
		this.propOrder = this.buildPropOrder();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setFactoryClass_(this.buildFactoryClass());
		this.setFactoryMethod_(this.buildFactoryMethod());
		this.setName_(this.buildName());
		this.setNamespace_(this.buildNamespace());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlTypeAnnotation implementation **********

	// ***** factoryClass
	public String getFactoryClass() {
		return this.factoryClass;
	}

	public void setFactoryClass(String factoryClass) {
		throw new UnsupportedOperationException();
	}

	private void setFactoryClass_(String factoryClass) {
		String old = this.factoryClass;
		this.factoryClass = factoryClass;
		this.firePropertyChanged(FACTORY_CLASS_PROPERTY, old, factoryClass);
		this.firePropertyChanged(FULLY_QUALIFIED_FACTORY_CLASS_NAME_PROPERTY, old, factoryClass);
	}

	private String buildFactoryClass() {
		return (String) this.getJdtMemberValue(JAXB.XML_TYPE__FACTORY_CLASS);
	}

	public TextRange getFactoryClassTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified factory class name
	public String getFullyQualifiedFactoryClassName() {
		return this.factoryClass;
	}

	// ***** factoryMethod
	public String getFactoryMethod() {
		return this.factoryMethod;
	}

	public void setFactoryMethod(String factoryMethod) {
		throw new UnsupportedOperationException();
	}

	private void setFactoryMethod_(String factoryMethod) {
		String old = this.factoryMethod;
		this.factoryMethod = factoryMethod;
		this.firePropertyChanged(FACTORY_METHOD_PROPERTY, old, factoryMethod);
	}

	private String buildFactoryMethod() {
		return (String) this.getJdtMemberValue(JAXB.XML_TYPE__FACTORY_METHOD);
	}

	public TextRange getFactoryMethodTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(JAXB.XML_TYPE__NAME);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		throw new UnsupportedOperationException();
	}

	private void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	private String buildNamespace() {
		return (String) this.getJdtMemberValue(JAXB.XML_TYPE__NAMESPACE);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** prop order
	public ListIterable<String> getPropOrder() {
		return new LiveCloneListIterable<String>(this.propOrder);
	}

	public int getPropOrderSize() {
		return this.propOrder.size();
	}

	private Vector<String> buildPropOrder() {
		Object[] jdtPropOrder = this.getJdtMemberValues(JAXB.XML_TYPE__PROP_ORDER);
		Vector<String> result = new Vector<String>(jdtPropOrder.length);
		for (Object jdtProp : jdtPropOrder) {
			result.add((String) jdtProp);
		}
		return result;
	}

	public void addProp(String propOrder) {
		throw new UnsupportedOperationException();
	}

	public void addProp(int index, String propOrder) {
		throw new UnsupportedOperationException();
	}

	public void moveProp(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeProp(String propOrder) {
		throw new UnsupportedOperationException();
	}

	public void removeProp(int index) {
		throw new UnsupportedOperationException();
	}

}
