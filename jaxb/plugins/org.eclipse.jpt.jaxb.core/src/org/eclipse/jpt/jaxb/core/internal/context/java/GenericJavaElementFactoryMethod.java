/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbElementFactoryMethod;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;

public class GenericJavaElementFactoryMethod
		extends AbstractJaxbContextNode
		implements JaxbElementFactoryMethod {

	protected final JavaResourceMethod resourceMethod;

	protected String elementName;
	protected String defaultValue;
	protected String namespace;
	protected String scope;
	protected String substitutionHeadName;
	protected String substitutionHeadNamespace;

	public GenericJavaElementFactoryMethod(JaxbRegistry parent, JavaResourceMethod resourceMethod) {
		super(parent);
		this.resourceMethod = resourceMethod;
		this.elementName = this.getResourceElementName();
		this.defaultValue = this.getResourceDefaultValue();
		this.namespace = this.getResourceNamespace();
		this.scope = this.getResourceScope();
		this.substitutionHeadName = this.getResourceSubstitutionHeadName();
		this.substitutionHeadNamespace = this.getResourceSubstitutionHeadNamespace();
	}

	public JavaResourceMethod getResourceMethod() {
		return this.resourceMethod;
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setElementName_(this.getResourceElementName());
		this.setDefaultValue_(this.getResourceDefaultValue());
		this.setNamespace_(this.getResourceNamespace());
		this.setScope_(this.getResourceScope());
		this.setSubstitutionHeadName_(this.getResourceSubstitutionHeadName());
		this.setSubstitutionHeadNamespace_(this.getResourceSubstitutionHeadNamespace());
	}


	// ********** xml enum value annotation **********

	protected XmlElementDeclAnnotation getXmlElementDeclAnnotation() {
		return (XmlElementDeclAnnotation) this.getResourceMethod().getNonNullAnnotation(XmlElementDeclAnnotation.ANNOTATION_NAME);
	}


	// ************** JaxbElementFactoryMethod impl ***********

	public String getName() {
		return this.resourceMethod.getName();
	}

	// ********** element name **********

	public String getElementName() {
		return this.elementName;
	}

	public void setElementName(String elementName) {
		this.getXmlElementDeclAnnotation().setName(elementName);
		this.setElementName_(elementName);	
	}

	protected void setElementName_(String elementName) {
		String old = this.elementName;
		this.elementName = elementName;
		this.firePropertyChanged(ELEMENT_NAME_PROPERTY, old, elementName);
	}

	protected String getResourceElementName() {
		return this.getXmlElementDeclAnnotation().getName();
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.getXmlElementDeclAnnotation().setDefaultValue(defaultValue);
		this.setDefaultValue_(defaultValue);	
	}

	protected void setDefaultValue_(String defaultValue) {
		String old = this.defaultValue;
		this.defaultValue = defaultValue;
		this.firePropertyChanged(DEFAULT_VALUE_PROPERTY, old, defaultValue);
	}

	protected String getResourceDefaultValue() {
		return this.getXmlElementDeclAnnotation().getDefaultValue();
	}

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.getXmlElementDeclAnnotation().setNamespace(namespace);
		this.setNamespace_(namespace);	
	}

	protected void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	protected String getResourceNamespace() {
		return this.getXmlElementDeclAnnotation().getNamespace();
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.getXmlElementDeclAnnotation().setScope(scope);
		this.setScope_(scope);	
	}

	protected void setScope_(String scope) {
		String old = this.scope;
		this.scope = scope;
		this.firePropertyChanged(SCOPE_PROPERTY, old, scope);
	}

	protected String getResourceScope() {
		return this.getXmlElementDeclAnnotation().getScope();
	}

	public String getSubstitutionHeadName() {
		return this.substitutionHeadName;
	}

	public void setSubstitutionHeadName(String substitutionHeadName) {
		this.getXmlElementDeclAnnotation().setSubstitutionHeadName(substitutionHeadName);
		this.setSubstitutionHeadName_(substitutionHeadName);	
	}

	protected void setSubstitutionHeadName_(String substitutionHeadName) {
		String old = this.substitutionHeadName;
		this.substitutionHeadName = substitutionHeadName;
		this.firePropertyChanged(SUBSTIUTION_HEAD_NAME_PROPERTY, old, substitutionHeadName);
	}

	protected String getResourceSubstitutionHeadName() {
		return this.getXmlElementDeclAnnotation().getSubstitutionHeadName();
	}

	public String getSubstitutionHeadNamespace() {
		return this.substitutionHeadNamespace;
	}

	public void setSubstitutionHeadNamespace(String substitutionHeadNamespace) {
		this.getXmlElementDeclAnnotation().setSubstitutionHeadNamespace(substitutionHeadNamespace);
		this.setSubstitutionHeadNamespace_(substitutionHeadNamespace);	
	}

	protected void setSubstitutionHeadNamespace_(String substitutionHeadNamespace) {
		String old = this.substitutionHeadNamespace;
		this.substitutionHeadNamespace = substitutionHeadNamespace;
		this.firePropertyChanged(SUBSTIUTION_HEAD_NAMESPACE_PROPERTY, old, substitutionHeadNamespace);
	}

	protected String getResourceSubstitutionHeadNamespace() {
		return this.getXmlElementDeclAnnotation().getSubstitutionHeadNamespace();
	}
}
