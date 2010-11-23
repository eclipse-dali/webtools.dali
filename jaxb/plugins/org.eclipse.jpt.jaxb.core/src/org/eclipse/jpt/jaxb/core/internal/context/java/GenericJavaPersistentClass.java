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

import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

public class GenericJavaPersistentClass
	extends AbstractJaxbContextNode
	implements JaxbPersistentClass
{

	protected final JavaResourceType resourceType;

	protected String factoryClass;
	protected String factoryMethod;
	protected String schemaTypeName;
	protected String namespace;

	protected final PropOrderContainer propOrderContainer;

	protected JaxbPersistentClass superPersistentClass;

	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;

	protected XmlAccessOrder defaultAccessOrder;
	protected XmlAccessOrder specifiedAccessOrder;

	public GenericJavaPersistentClass(JaxbRootContextNode parent, JavaResourceType resourceType) {
		super(parent);
		this.resourceType = resourceType;
		this.factoryClass = this.getResourceFactoryClass();
		this.factoryMethod = this.getResourceFactoryMethod();
		this.schemaTypeName = this.getResourceSchemaTypeName();
		this.namespace = this.getResourceNamespace();
		this.propOrderContainer = new PropOrderContainer();
		this.superPersistentClass = this.buildSuperPersistentClass();
		this.specifiedAccessType = this.getResourceAccessType();
		this.specifiedAccessOrder = this.getResourceAccessOrder();
		this.defaultAccessType = this.buildDefaultAccessType();
		this.defaultAccessOrder = this.buildDefaultAccessOrder();
	}

	@Override
	public JaxbRootContextNode getParent() {
		return (JaxbRootContextNode) super.getParent();
	}

	protected JaxbPackageInfo getPackageInfo() {
		//TODO
		return null;
		//return this.getParent().getPackageInfo();
	}

	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		this.setFactoryClass_(this.getResourceFactoryClass());
		this.setFactoryMethod_(this.getResourceFactoryMethod());
		this.setSchemaTypeName_(this.getResourceSchemaTypeName());
		this.setNamespace_(this.getResourceNamespace());
		this.setSpecifiedAccessType_(this.getResourceAccessType());
		this.setSpecifiedAccessOrder_(this.getResourceAccessOrder());
		this.syncPropOrder();
	}

	public void update() {
		this.setSuperPersistentClass(this.buildSuperPersistentClass());
		this.setDefaultAccessType(this.buildDefaultAccessType());
		this.setDefaultAccessOrder(this.buildDefaultAccessOrder());
	}


	// ********** JaxbPersistentClass implementation **********

	public JavaResourceType getJaxbResourceType() {
		return this.resourceType;
	}
	
	public String getFullyQualifiedName() {
		return this.resourceType.getQualifiedName();
	}
	
	public String getPackageName() {
		return this.resourceType.getPackageName();
	}
	
	public String getTypeQualifiedName() {
		String packageName = getPackageName();
		return (packageName.length() == 0) ? getFullyQualifiedName() : getFullyQualifiedName().substring(packageName.length() + 1);
	}
	
	public String getSimpleName() {
		return resourceType.getName();
	}

	// ********** xml type annotation **********

	protected XmlTypeAnnotation getXmlTypeAnnotation() {
		return (XmlTypeAnnotation) this.getJaxbResourceType().getNonNullAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
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

	// ********** super persistent class **********

	public JaxbPersistentClass getSuperPersistentClass() {
		return this.superPersistentClass;
	}

	protected void setSuperPersistentClass(JaxbPersistentClass superPersistentClass) {
		JaxbPersistentClass old = this.superPersistentClass;
		this.superPersistentClass = superPersistentClass;
		this.firePropertyChanged(SUPER_PERSISTENT_CLASS_PROPERTY, old, superPersistentClass);
	}

	//TODO super persistent class
	protected JaxbPersistentClass buildSuperPersistentClass() {
		return null;
//		HashSet<JavaResourcePersistentType> visited = new HashSet<JavaResourcePersistentType>();
//		visited.add(this.resourcePersistentType);
//		PersistentType spt = this.getSuperPersistentType(this.resourcePersistentType.getSuperclassQualifiedName(), visited);
//		if (spt == null) {
//			return null;
//		}
//		if (CollectionTools.contains(spt.inheritanceHierarchy(), this)) {
//			return null;  // short-circuit in this case, we have circular inheritance
//		}
//		return spt.isMapped() ? spt : spt.getSuperPersistentType();
	}
//
//	/**
//	 * The JPA spec allows non-persistent types in a persistent type's
//	 * inheritance hierarchy. We check for a persistent type with the
//	 * specified name in the persistence unit. If it is not found we use
//	 * resource persistent type and look for *its* super type.
//	 * 
//	 * The 'visited' collection is used to detect a cycle in the *resource* type
//	 * inheritance hierarchy and prevent the resulting stack overflow.
//	 * Any cycles in the *context* type inheritance hierarchy are handled in
//	 * #buildSuperPersistentType().
//	 */
//	protected PersistentType getSuperPersistentType(String typeName, Collection<JavaResourcePersistentType> visited) {
//		if (typeName == null) {
//			return null;
//		}
//		JavaResourcePersistentType resourceType = this.getJpaProject().getJavaResourcePersistentType(typeName);
//		if ((resourceType == null) || visited.contains(resourceType)) {
//			return null;
//		}
//		visited.add(resourceType);
//		PersistentType spt = this.getPersistentType(typeName);
//		return (spt != null) ? spt : this.getSuperPersistentType(resourceType.getSuperclassQualifiedName(), visited);  // recurse
//	}
//
//	protected PersistentType getPersistentType(String typeName) {
//		return this.getPersistenceUnit().getPersistentType(typeName);
//	}

	// ********** access type **********

	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : this.defaultAccessType;
	}

	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(XmlAccessType access) {
		this.getAccessorTypeAnnotation().setValue(XmlAccessType.toJavaResourceModel(access));
		this.setSpecifiedAccessType_(access);
	}

	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}

	public XmlAccessType getDefaultAccessType() {
		return this.defaultAccessType;
	}

	protected void setDefaultAccessType(XmlAccessType access) {
		XmlAccessType old = this.defaultAccessType;
		this.defaultAccessType = access;
		this.firePropertyChanged(DEFAULT_ACCESS_TYPE_PROPERTY, old, access);
	}
	
	protected XmlAccessType getResourceAccessType() {
		return XmlAccessType.fromJavaResourceModel(this.getAccessorTypeAnnotation().getValue());
	}

	protected XmlAccessorTypeAnnotation getAccessorTypeAnnotation() {
		return (XmlAccessorTypeAnnotation) this.resourceType.getNonNullAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
	}

	/**
	 * If there is a @XmlAccessorType on a class, then it is used.
	 * Otherwise, if a @XmlAccessorType exists on one of its super classes, then it is inherited.
	 * Otherwise, the @XmlAccessorType on a package is inherited. 	
	 */
	protected XmlAccessType buildDefaultAccessType() {
		XmlAccessType superAccessType = this.getSuperPersistentClassAccessType();
		if (superAccessType != null) {
			return superAccessType;
		}
		XmlAccessType packageAccessType = getPackageAccessType();
		if (packageAccessType != null) {
			return packageAccessType;
		}
		return XmlAccessType.PUBLIC_MEMBER;
	}

	protected XmlAccessType getSuperPersistentClassAccessType() {
		JaxbPersistentClass superPersistentClass = this.getSuperPersistentClass();
		return superPersistentClass == null ? null : superPersistentClass.getSpecifiedAccessType();
	}

	protected XmlAccessType getPackageAccessType() {
		JaxbPackageInfo packageInfo = this.getPackageInfo();
		return packageInfo == null ? null : packageInfo.getAccessType();
	}


	// ********** access order **********

	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : this.defaultAccessOrder;
	}

	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(XmlAccessOrder accessOrder) {
		this.getAccessorOrderAnnotation().setValue(XmlAccessOrder.toJavaResourceModel(accessOrder));
		this.setSpecifiedAccessOrder_(accessOrder);
	}

	protected void setSpecifiedAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.specifiedAccessOrder;
		this.specifiedAccessOrder = accessOrder;
		this.firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}

	public XmlAccessOrder getDefaultAccessOrder() {
		return this.defaultAccessOrder;
	}

	protected void setDefaultAccessOrder(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.defaultAccessOrder;
		this.defaultAccessOrder = accessOrder;
		this.firePropertyChanged(DEFAULT_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}
	
	protected XmlAccessOrder getResourceAccessOrder() {
		return XmlAccessOrder.fromJavaResourceModel(this.getAccessorOrderAnnotation().getValue());
	}

	protected XmlAccessorOrderAnnotation getAccessorOrderAnnotation() {
		return (XmlAccessorOrderAnnotation) this.resourceType.getNonNullAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
	}

	/**
    * If there is a @XmlAccessorOrder on a class, then it is used.
    * Otherwise, if a @XmlAccessorOrder exists on one of its super classes, then it is inherited (by the virtue of Inherited)
    * Otherwise, the @XmlAccessorOrder on the package of the class is used, if it's there.
    * Otherwise XmlAccessOrder.UNDEFINED. 
  	*/
	protected XmlAccessOrder buildDefaultAccessOrder() {
		XmlAccessOrder superAccessOrder = this.getSuperPersistentClassAccessOrder();
		if (superAccessOrder != null) {
			return superAccessOrder;
		}
		XmlAccessOrder packageAccessOrder = getPackageAccessOrder();
		if (packageAccessOrder != null) {
			return packageAccessOrder;
		}
		return XmlAccessOrder.UNDEFINED;
	}

	protected XmlAccessOrder getSuperPersistentClassAccessOrder() {
		JaxbPersistentClass superPersistentClass = this.getSuperPersistentClass();
		return superPersistentClass == null ? null : superPersistentClass.getSpecifiedAccessOrder();
	}

	protected XmlAccessOrder getPackageAccessOrder() {
		JaxbPackageInfo packageInfo = this.getPackageInfo();
		return packageInfo == null ? null : packageInfo.getAccessOrder();
	}


	/**
	 * xml java type adapter container
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
			return GenericJavaPersistentClass.this.getResourcePropOrder();
		}
		@Override
		protected String getResourceElement(String contextElement) {
			return contextElement;
		}
	}
}
