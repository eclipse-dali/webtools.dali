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

import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentType;
import org.eclipse.jpt.jaxb.core.context.XmlRootElement;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

public abstract class AbstractJavaPersistentType
		extends AbstractJavaType
		implements JaxbPersistentType {

	protected String factoryClass;
	protected String factoryMethod;
	protected String schemaTypeName;
	protected String namespace;
	protected final PropOrderContainer propOrderContainer;

	protected XmlRootElement rootElement;

	public AbstractJavaPersistentType(JaxbContextRoot parent, AbstractJavaResourceType resourceType) {
		super(parent, resourceType);
		this.factoryClass = this.getResourceFactoryClass();
		this.factoryMethod = this.getResourceFactoryMethod();
		this.schemaTypeName = this.getResourceSchemaTypeName();
		this.namespace = this.getResourceNamespace();
		this.propOrderContainer = new PropOrderContainer();
		this.rootElement = this.buildRootElement();
	}

	@Override
	public JaxbContextRoot getParent() {
		return (JaxbContextRoot) super.getParent();
	}

	protected JaxbPackageInfo getPackageInfo() {
		JaxbPackage jaxbPackage = getParent().getPackage(this.getPackageName());
		return jaxbPackage == null ? null : jaxbPackage.getPackageInfo();
	}

	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setFactoryClass_(this.getResourceFactoryClass());
		this.setFactoryMethod_(this.getResourceFactoryMethod());
		this.setSchemaTypeName_(this.getResourceSchemaTypeName());
		this.setNamespace_(this.getResourceNamespace());
		this.syncPropOrder();
		this.syncRootElement();
	}
	
	public void update() {
		//nothing yet
	}

	// ********** xml type annotation **********

	protected XmlTypeAnnotation getXmlTypeAnnotation() {
		return (XmlTypeAnnotation) this.getJavaResourceType().getNonNullAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
	}


	// ********** factory class **********

	public String getFactoryClass() {
		return this.factoryClass;
	}

	public void setFactoryClass(String factoryClass) {
		this.getXmlTypeAnnotation().setFactoryClass(factoryClass);
		this.setFactoryClass_(factoryClass);	
	}

	protected void setFactoryClass_(String factoryClass) {
		String old = this.factoryClass;
		this.factoryClass = factoryClass;
		this.firePropertyChanged(FACTORY_CLASS_PROPERTY, old, factoryClass);
	}

	protected String getResourceFactoryClass() {
		return this.getXmlTypeAnnotation().getFactoryClass();
	}

	// ********** factory method **********

	public String getFactoryMethod() {
		return this.factoryMethod;
	}

	public void setFactoryMethod(String factoryMethod) {
		this.getXmlTypeAnnotation().setFactoryMethod(factoryMethod);
		this.setFactoryMethod_(factoryMethod);	
	}

	protected void setFactoryMethod_(String factoryMethod) {
		String old = this.factoryMethod;
		this.factoryMethod = factoryMethod;
		this.firePropertyChanged(FACTORY_METHOD_PROPERTY, old, factoryMethod);
	}

	protected String getResourceFactoryMethod() {
		return this.getXmlTypeAnnotation().getFactoryMethod();
	}

	// ********** name **********

	public String getSchemaTypeName() {
		return this.schemaTypeName;
	}

	public void setSchemaTypeName(String schemaTypeName) {
		this.getXmlTypeAnnotation().setName(schemaTypeName);
		this.setSchemaTypeName_(schemaTypeName);	
	}

	protected void setSchemaTypeName_(String schemaTypeName) {
		String old = this.schemaTypeName;
		this.schemaTypeName = schemaTypeName;
		this.firePropertyChanged(SCHEMA_TYPE_NAME_PROPERTY, old, schemaTypeName);
	}

	protected String getResourceSchemaTypeName() {
		return this.getXmlTypeAnnotation().getName();
	}

	// ********** namespace **********

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.getXmlTypeAnnotation().setNamespace(namespace);
		this.setNamespace_(namespace);	
	}

	protected void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	protected String getResourceNamespace() {
		return this.getXmlTypeAnnotation().getNamespace();
	}


	// ********** prop order **********
	
	public ListIterable<String> getPropOrder() {
		return this.propOrderContainer.getContextElements();
	}

	public int getPropOrderSize() {
		return this.propOrderContainer.getContextElementsSize();
	}

	public void addProp(int index, String prop) {
		getXmlTypeAnnotation().addProp(index, prop);
		this.propOrderContainer.addContextElement(index, prop);
	}

	public void removeProp(String prop) {
		this.removeProp(this.propOrderContainer.indexOfContextElement(prop));
	}

	public void removeProp(int index) {
		this.getXmlTypeAnnotation().removeProp(index);
		this.propOrderContainer.removeContextElement(index);
	}

	public void moveProp(int targetIndex, int sourceIndex) {
		this.getXmlTypeAnnotation().moveProp(targetIndex, sourceIndex);
		this.propOrderContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncPropOrder() {
		this.propOrderContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<String> getResourcePropOrder() {
		return this.getXmlTypeAnnotation().getPropOrder();
	}

	// *************** root element *********************

	public XmlRootElement getRootElement() {
		return this.rootElement;
	}

	public boolean isRootElement() {
		return this.rootElement != null;
	}

	public XmlRootElement setRootElement(String name) {
		if (name == null) {
			this.getJavaResourceType().removeAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
			this.setRootElement_(null);
			return null;
		}
		XmlRootElementAnnotation resourceRootElement = (XmlRootElementAnnotation) getJavaResourceType().addAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		resourceRootElement.setName(name);
		XmlRootElement contextRootElement = this.buildRootElement(resourceRootElement);
		this.setRootElement_(contextRootElement);
		return contextRootElement;
	}

	protected void setRootElement_(XmlRootElement rootElement) {
		XmlRootElement old = this.rootElement;
		this.rootElement = rootElement;
		this.firePropertyChanged(ROOT_ELEMENT, old, rootElement);
	}

	protected XmlRootElement buildRootElement() {
		XmlRootElementAnnotation resourceRootElement = this.getRootElementAnnotation();
		return resourceRootElement == null ? null : this.buildRootElement(resourceRootElement);
	}

	protected XmlRootElement buildRootElement(XmlRootElementAnnotation resourceRootElement) {
		return getFactory().buildJavaXmlRootElement(this, resourceRootElement);
	}

	protected void syncRootElement() {
		XmlRootElementAnnotation resourceRootElement = this.getRootElementAnnotation();
		if (resourceRootElement != null) {
			if (this.rootElement != null) {
				this.rootElement.synchronizeWithResourceModel();
			}
			else {
				this.setRootElement_(this.buildRootElement(resourceRootElement));
			}
		}
		else if (this.rootElement != null) {
			this.setRootElement_(null);
		}
	}

	protected XmlRootElementAnnotation getRootElementAnnotation() {
		return (XmlRootElementAnnotation) this.getJavaResourceType().getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.getFullyQualifiedName());
	}



	/**
	 * xml prop order container
	 */
	protected class PropOrderContainer
		extends ListContainer<String, String>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return PROP_ORDER_LIST;
		}
		@Override
		protected String buildContextElement(String resourceElement) {
			return resourceElement;
		}
		@Override
		protected ListIterable<String> getResourceElements() {
			return AbstractJavaPersistentType.this.getResourcePropOrder();
		}
		@Override
		protected String getResourceElement(String contextElement) {
			return contextElement;
		}
	}
}