/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
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
			extends TransformerAdapter<JaxbClassMapping, JaxbClassMapping> {
		@Override
		public JaxbClassMapping transform(JaxbClassMapping mapping) {
			return mapping.getSuperclass();
		}
	}
	
	
	// ***** attributes *****
	
	/** string associated with changes to the "attributes" collection */
	String ATTRIBUTES_COLLECTION = "attributes"; //$NON-NLS-1$
	
	/**
	 * Return the attributes defined on this class (not its superclass)
	 */
	Iterable<? extends JaxbPersistentAttribute> getAttributes();
	
	int getAttributesSize();
	
	Transformer<JaxbClassMapping, Iterable<? extends JaxbPersistentAttribute>> ATTRIBUTES_TRANSFORMER = new AttributesTransformer();
	class AttributesTransformer
		extends TransformerAdapter<JaxbClassMapping, Iterable<? extends JaxbPersistentAttribute>>
	{
		@Override
		public Iterable<? extends JaxbPersistentAttribute> transform(JaxbClassMapping mapping) {
			return mapping.getAttributes();
		}
	}
	
	/** string associated with changes to the "included attributes" collection */
	String INCLUDED_ATTRIBUTES_COLLECTION = "includedAttributes"; //$NON-NLS-1$
	
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
