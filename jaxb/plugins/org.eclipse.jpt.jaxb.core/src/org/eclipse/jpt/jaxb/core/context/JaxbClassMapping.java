/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;


public interface JaxbClassMapping
		extends JaxbTypeMapping, XmlAccessTypeHolder, XmlAccessOrderHolder {
	
	// ***** XmlType.factoryClass *****
	
	String getFactoryClass();
	
	String SPECIFIED_FACTORY_CLASS_PROPERTY = "specifiedFactoryClass"; //$NON-NLS-1$
	
	/**
	 * factory class corresponds to the XmlType annotation factoryClass element
	 */
	String getSpecifiedFactoryClass();
	
	void setSpecifiedFactoryClass(String factoryClass);
	
	
	// ***** XmlType.factoryMethod *****
	
	String FACTORY_METHOD_PROPERTY = "factoryMethod"; //$NON-NLS-1$
	
	/**
	 * factory method corresponds to the XmlType annotation factoryMethod element
	 */
	String getFactoryMethod();
	
	void setFactoryMethod(String factoryMethod);
	
	
	// ***** XmlType.propOrder *****
	
	String PROP_ORDER_LIST = "propOrder"; //$NON-NLS-1$
	
	/**
	 * propOrder corresponds to the XmlType annotation propOrder element
	 */
	ListIterable<String> getPropOrder();
	
	String getProp(int index);
	
	int getPropOrderSize();
	
	void addProp(int index, String prop);
	
	void removeProp(int index);
	
	void removeProp(String prop);
	
	void moveProp(int targetIndex, int sourceIndex);
	
	
	// ***** superclass *****
	
	String SUPERCLASS_PROPERTY = "superclass"; //$NON-NLS-1$
	
	JaxbClassMapping getSuperclass();

	Transformer<JaxbClassMapping, JaxbClassMapping> SUPER_CLASS_TRANSFORMER = new SuperClassTransformer();
	class SuperClassTransformer
		extends AbstractTransformer<JaxbClassMapping, JaxbClassMapping>
	{
		@Override
		protected JaxbClassMapping transform_(JaxbClassMapping mapping) {
			return mapping.getSuperclass();
		}
	}
	
	
	// ***** attributes *****
	
	/**
	 * Return the attributes defined on this class (not its superclass)
	 */
	Iterable<JaxbPersistentAttribute> getAttributes();

	Transformer<JaxbClassMapping, Iterable<JaxbPersistentAttribute>> ATTRIBUTES_TRANSFORMER = new AttributesTransformer();
	class AttributesTransformer
		extends AbstractTransformer<JaxbClassMapping, Iterable<JaxbPersistentAttribute>>
	{
		@Override
		protected Iterable<JaxbPersistentAttribute> transform_(JaxbClassMapping mapping) {
			return mapping.getAttributes();
		}
	}
	
	int getAttributesSize();
	
	/**
	 * <i>Included</i> attributes come from any direct superclasses that are mapped as @XmlTransient.
	 * (As opposed to <i>inherited</i> attributes, which a class has by way of <i>any</i> mapped superclasses.)
	 * If there is an intervening class that is not transient, then that class will hold any
	 * included attributes from any direct superclass that are mapped as @XmlTransient.
	 * @see JaxbClassMapping#getSuperclass()
	 */
	Iterable<JaxbPersistentAttribute> getIncludedAttributes();
	
	int getIncludedAttributesSize();
	
	
	// *****  misc attributes *****
	
	/**
	 * Return all attributes that are defined by this class.  
	 * This is the combined set of #getAttributes() and #getIncludedAttributes()
	 */
	Iterable<JaxbPersistentAttribute> getAllLocallyDefinedAttributes();
	
	/**
	 * <i>Inherited</i> attributes are any attributes this class mapping has whose source
	 * is a superclass.
	 * Inherited attributes include <i>included</i> attributes.
	 */
	Iterable<JaxbPersistentAttribute> getInheritedAttributes();
	
	
	// ***** misc *****
	/**
	 * Build an included attributes container that process attribute metadata of this class
	 * with the context of the owning class
	 */
	JaxbAttributesContainer buildIncludedAttributesContainer(
			JaxbClassMapping parent, JaxbAttributesContainer.Context context);
	
	/**
	 * Return the id attribute mapping for this class mapping, if it has one.
	 * (Will return the first one it finds, if this class has more than one.)
	 */
	JaxbAttributeMapping getXmlIdMapping();
}
